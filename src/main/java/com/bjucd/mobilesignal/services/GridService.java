package com.bjucd.mobilesignal.services;

import com.bjucd.mobilesignal.models.base.Grid;
import com.bjucd.mobilesignal.models.base.UserGrid;
import com.bjucd.mobilesignal.models.region.GridPopulation;
import com.bjucd.mobilesignal.models.region.GridUserAge;
import com.bjucd.mobilesignal.models.region.GridUserGender;
import com.bjucd.mobilesignal.models.responseBody.GridPopulationResponse;
import com.bjucd.mobilesignal.models.responseBody.NameValue;
import com.bjucd.mobilesignal.repositoriies.base.GridRepository;
import com.bjucd.mobilesignal.repositoriies.base.UserGridRepository;
import com.bjucd.mobilesignal.repositoriies.region.GridPopulationRepository;
import com.bjucd.mobilesignal.repositoriies.region.GridUserAgeRepository;
import com.bjucd.mobilesignal.repositoriies.region.GridUserGenderRepository;
import com.bjucd.mobilesignal.utils.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 6/17/2019.
 */
@Service
public class GridService {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    SystemUtils systemUtil;

    @Autowired
    GridRepository gridRepo;

    @Autowired
    UserGridRepository userGridRepo;

    @Autowired
    GridPopulationRepository popRepo;

    @Autowired
    GridUserAgeRepository ageRepo;

    @Autowired
    GridUserGenderRepository genderRepo;

    @Autowired
    EntityManagerFactory factory;

    public Set<String> getDistrictList(String city) {
        List<Grid> grids = gridRepo.findByCity(city);
        Set<String> districtNames = grids.parallelStream().map(grid -> grid.getDistrict()).collect(Collectors.toSet());
        return districtNames;
    }


    public List<GridPopulationResponse> getGrids(String city, String district, boolean isCu) {
        List<Grid> grids = gridRepo.findByCityAndDistrict(city, district);
        logger.info("grid siez:{}", grids.size());
        List<String> gridIds = grids.stream().map(g -> g.getGridId()).collect(Collectors.toList());
        Map<String, Double[]> populations = this.getGridPopulation(city, gridIds, isCu);
        logger.info("populations siez:{}", populations.size());

        List<GridPopulationResponse> result = grids.stream().map(g ->
                GridPopulationResponse.builder().gridId(g.getGridId()).coords(new Double[]{g.getLng(), g.getLat()}).build())
                .collect(Collectors.toList());
        for (GridPopulationResponse g : result) {
            String gridId = g.getGridId();
            if (populations.containsKey(gridId)) {
                Double[] pop = populations.get(gridId);
                g.setPopulations(pop);
            } else {
                g.setPopulations(new Double[]{0.0,0.0,0.0});
            }
        }
        return result;
    }

    public Set<String> getUserRegionList(String city) {
        List<UserGrid> grids = userGridRepo.findByCity(city);
        Set<String> districtNames = grids.parallelStream().map(grid -> grid.getUdZoneName()).collect(Collectors.toSet());
        return districtNames;
    }

    public List<GridPopulationResponse> getUserGrids(String city, String name, boolean isCu) {
        List<UserGrid> grids = userGridRepo.findByCityAndUdZoneName(city, name);
        List<GridPopulationResponse> result = grids.stream().map(g ->
                GridPopulationResponse.builder().gridId(g.getGridId()).coords(new Double[]{g.getLng(), g.getLat()}).build())
                .collect(Collectors.toList());
        List<String> gridIds = result.stream().map(g -> g.getGridId()).collect(Collectors.toList());
        Map<String, Double[]> populations = this.getGridPopulation(city, gridIds, isCu);
        for (GridPopulationResponse g : result) {
            String gridId = g.getGridId();
            if (populations.containsKey(gridId)) {
                Double[] pop = populations.get(gridId);
                g.setPopulations(pop);
            } else {
                g.setPopulations(new Double[]{0.0,0.0,0.0});
            }

        }
        return result;
    }

    public Map<String, Double[]> getGridPopulation(String city, List<String> gridIds, boolean isCu) {
        String version = systemUtil.getActiveVersion();
        List<GridPopulation> populations = popRepo.findByCityAndGridIdInAndVersion(city, gridIds, version);
        Map<String, Double[]> result = new HashMap<>();
        populations.stream().forEach(pop ->
                result.put(pop.getGridId(), new Double[]{
                        isCu ? pop.getCuResident() : pop.getKyResident(),
                        isCu ? pop.getCuEmployment() : pop.getKyEmployment(),
                        isCu ? pop.getCuTemporary() : pop.getKyTemporary()
                }));
        return result;
    }

    public Map<Integer, Double> getDistrictRegionAge(String city, String region, boolean isCu) {
        String version = systemUtil.getActiveVersion();
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GridUserAge> query = cb.createQuery(GridUserAge.class);
        Root<GridUserAge> table1 =  query.from(GridUserAge.class);
        query.multiselect(table1.get("age"), cb.sumAsDouble(table1.get("cuPop")), cb.sumAsDouble(table1.get("kyPop")));
        query.groupBy(table1.get("age"));

        Subquery<Grid> subquery = query.subquery(Grid.class);
        Root<Grid> table2 = subquery.from(Grid.class);
        subquery.select(table2);

        List<Predicate> subQueryPredicates = new ArrayList<>();
        subQueryPredicates.add(cb.equal(table1.get("gridId"), table2.get("gridId")));
        subQueryPredicates.add(cb.equal(table2.get("city"), city));
        subQueryPredicates.add(cb.equal(table2.get("district"), region));
        subquery.where(subQueryPredicates.toArray(new Predicate[]{}));

        List<Predicate> mainQueryPredicates = new ArrayList<>();
        mainQueryPredicates.add(cb.equal(table1.get("city"),city));
        mainQueryPredicates.add(cb.equal(table1.get("version"), version));
        mainQueryPredicates.add(cb.exists(subquery));
        query.where(mainQueryPredicates.toArray(new Predicate[]{}));

        TypedQuery<GridUserAge> typedQuery =  em.createQuery(query);
        List<GridUserAge> resultList = typedQuery.getResultList();
        em.close();

        Map<Integer, Double> result = new HashMap<>();
        resultList.stream().forEach(r -> result.put(r.getAge(), isCu ? r.getCuPop() : r.getKyPop()));
        return result;
    }

    public Map<Integer, Double> getHotRegionAge(String city, String region, boolean isCu) {
        String version = systemUtil.getActiveVersion();
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GridUserAge> query = cb.createQuery(GridUserAge.class);
        Root<GridUserAge> table1 =  query.from(GridUserAge.class);
        query.multiselect(table1.get("age"), cb.sumAsDouble(table1.get("cuPop")), cb.sumAsDouble(table1.get("kyPop")));
        query.groupBy(table1.get("age"));

        Subquery<UserGrid> subquery = query.subquery(UserGrid.class);
        Root<UserGrid> table2 = subquery.from(UserGrid.class);
        subquery.select(table2);

        List<Predicate> subQueryPredicates = new ArrayList<>();
        subQueryPredicates.add(cb.equal(table1.get("gridId"), table2.get("gridId")));
        subQueryPredicates.add(cb.equal(table2.get("city"), city));
        subQueryPredicates.add(cb.equal(table2.get("udZoneName"), region));
        subquery.where(subQueryPredicates.toArray(new Predicate[]{}));

        List<Predicate> mainQueryPredicates = new ArrayList<>();
        mainQueryPredicates.add(cb.equal(table1.get("city"),city));
        mainQueryPredicates.add(cb.equal(table1.get("version"), version));
        mainQueryPredicates.add(cb.exists(subquery));
        query.where(mainQueryPredicates.toArray(new Predicate[]{}));

        TypedQuery<GridUserAge> typedQuery =  em.createQuery(query);
        List<GridUserAge> resultList = typedQuery.getResultList();
        em.close();

        Map<Integer, Double> result = new HashMap<>();
        resultList.stream().forEach(r -> result.put(r.getAge(), isCu ? r.getCuPop() : r.getKyPop()));
        return result;
    }

    public Map<Integer, Double> getDistrictGender(String city, String region, boolean isCu) {
        String version = systemUtil.getActiveVersion();
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GridUserGender> query = cb.createQuery(GridUserGender.class);
        Root<GridUserGender> table1 =  query.from(GridUserGender.class);
        query.multiselect(table1.get("gender"), cb.sumAsDouble(table1.get("cuPop")), cb.sumAsDouble(table1.get("kyPop")));
        query.groupBy(table1.get("gender"));

        Subquery<Grid> subquery = query.subquery(Grid.class);
        Root<Grid> table2 = subquery.from(Grid.class);
        subquery.select(table2);

        List<Predicate> subQueryPredicates = new ArrayList<>();
        subQueryPredicates.add(cb.equal(table1.get("gridId"), table2.get("gridId")));
        subQueryPredicates.add(cb.equal(table2.get("city"), city));
        subQueryPredicates.add(cb.equal(table2.get("district"), region));
        subquery.where(subQueryPredicates.toArray(new Predicate[]{}));

        List<Predicate> mainQueryPredicates = new ArrayList<>();
        mainQueryPredicates.add(cb.equal(table1.get("city"),city));
        mainQueryPredicates.add(cb.equal(table1.get("version"), version));
        mainQueryPredicates.add(cb.exists(subquery));
        query.where(mainQueryPredicates.toArray(new Predicate[]{}));

        TypedQuery<GridUserGender> typedQuery =  em.createQuery(query);
        List<GridUserGender> resultList = typedQuery.getResultList();
        em.close();

        Map<Integer, Double> result = new HashMap<>();
        resultList.stream().forEach(r -> result.put(r.getGender(), isCu ? r.getCuPop() : r.getKyPop()));
        return result;
    }

    public Map<Integer, Double> getHotGender(String city, String region,  boolean isCu) {
        String version = systemUtil.getActiveVersion();
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GridUserGender> query = cb.createQuery(GridUserGender.class);
        Root<GridUserGender> table1 =  query.from(GridUserGender.class);
        query.multiselect(table1.get("gender"), cb.sumAsDouble(table1.get("cuPop")), cb.sumAsDouble(table1.get("kyPop")));
        query.groupBy(table1.get("gender"));

        Subquery<UserGrid> subquery = query.subquery(UserGrid.class);
        Root<UserGrid> table2 = subquery.from(UserGrid.class);
        subquery.select(table2);

        List<Predicate> subQueryPredicates = new ArrayList<>();
        subQueryPredicates.add(cb.equal(table1.get("gridId"), table2.get("gridId")));
        subQueryPredicates.add(cb.equal(table2.get("city"), city));
        subQueryPredicates.add(cb.equal(table2.get("udZoneName"), region));
        subquery.where(subQueryPredicates.toArray(new Predicate[]{}));

        List<Predicate> mainQueryPredicates = new ArrayList<>();
        mainQueryPredicates.add(cb.equal(table1.get("city"),city));
        mainQueryPredicates.add(cb.equal(table1.get("version"), version));
        mainQueryPredicates.add(cb.exists(subquery));
        query.where(mainQueryPredicates.toArray(new Predicate[]{}));

        TypedQuery<GridUserGender> typedQuery =  em.createQuery(query);
        List<GridUserGender> resultList = typedQuery.getResultList();
        em.close();

        Map<Integer, Double> result = new HashMap<>();
        resultList.stream().forEach(r -> result.put(r.getGender(), isCu ? r.getCuPop() : r.getKyPop()));
        return result;
    }
}
