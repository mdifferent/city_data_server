package com.bjucd.mobilesignal.services;

import com.bjucd.mobilesignal.models.base.DistrictCoord;
import com.bjucd.mobilesignal.models.od.DistrictOD;
import com.bjucd.mobilesignal.models.od.GridOD;
import com.bjucd.mobilesignal.models.responseBody.LinesData;
import com.bjucd.mobilesignal.models.responseBody.NameValue;
import com.bjucd.mobilesignal.models.trip.Trips;
import com.bjucd.mobilesignal.repositoriies.base.DistrictCoordRepository;
import com.bjucd.mobilesignal.repositoriies.od.DistrictOdRepository;
import com.bjucd.mobilesignal.repositoriies.od.GridOdRepository;
import com.bjucd.mobilesignal.utils.TimeRangeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 6/6/2019.
 */
@Service
public class OdService {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    DistrictCoordRepository coordRepo;

    @Autowired
    DistrictOdRepository districtRepo;

    @Autowired
    GridOdRepository gridRepo;

    @Autowired
    EntityManagerFactory factory;

    @Autowired
    TimeRangeUtils timeUtil;

    public List<NameValue<Double[]>> getDistrictPointOdData(String city, String date, String version, List<Integer[]> times, boolean isCu) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DistrictOD> cq = cb.createQuery(DistrictOD.class);
        Root<DistrictOD> root = cq.from(DistrictOD.class);
        cq.multiselect(root.get("originDistrict"), cb.sumAsDouble(root.get("cuod")), cb.sumAsDouble(root.get("kyod")));
        cq.groupBy(root.get("originDistrict"));
        cq.where(cb.equal(root.get("city"),city),
                cb.equal(root.get("dateType"), date),
                cb.equal(root.get("version"), version),
                cb.equal(root.get("originDistrict"), root.get("destinationDistrict")),
                timeUtil.createWhereCondition(cb, root, times, new String[] {"timeHour", "timeMinute"})
                );
        List<DistrictOD> ods = em.createQuery(cq).getResultList();
        em.close();

        List<String> districtNames = ods.stream().map(od -> od.getOriginDistrict()).collect(Collectors.toList());
        logger.info("{}", districtNames);
        List<DistrictCoord> coords = coordRepo.findByVersionAndCityAndDistrictIn(version, city, districtNames);
        Map<String, Double[]> coordMap = new HashMap<>();
        coords.stream().forEach(coord -> coordMap.put(coord.getDistrict(), new Double[]{coord.getLng(), coord.getLat()}));
        return ods.stream().map(od -> {
            String district = od.getOriginDistrict();
            if (coordMap.containsKey(od.getOriginDistrict())) {
                return new NameValue<Double[]>(od.getOriginDistrict(), new Double[]{
                        coordMap.get(district)[0], coordMap.get(district)[1], isCu ? od.getCuod() : od.getKyod()
                });
            } else {
                return new NameValue<Double[]>(od.getOriginDistrict(), new Double[]{
                        0.0, 0.0, isCu ? od.getCuod() : od.getKyod()
                });
            }
        }).collect(Collectors.toList());
    }

    public List<LinesData> getInterOdData(String city, String date, String version, List<Integer[]> times, boolean isCu) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DistrictOD> cq = cb.createQuery(DistrictOD.class);
        Root<DistrictOD> root = cq.from(DistrictOD.class);
        cq.multiselect(root.get("originDistrict"), root.get("destinationDistrict"), cb.sumAsDouble(root.get("cuod")), cb.sumAsDouble(root.get("kyod")));
        cq.groupBy(root.get("originDistrict"), root.get("destinationDistrict"));
        cq.where(cb.equal(root.get("city"),city),
                cb.equal(root.get("dateType"), date),
                cb.equal(root.get("version"), version),
                cb.notEqual(root.get("originDistrict"), root.get("destinationDistrict")),
                timeUtil.createWhereCondition(cb, root, times, new String[] {"timeHour", "timeMinute"})
        );
        List<DistrictOD> ods = em.createQuery(cq).getResultList();
        em.close();

        Set<String> originDistrictNames = ods.stream().map(od -> od.getOriginDistrict()).collect(Collectors.toSet());
        Set<String> destinationDistrictNames = ods.stream().map(od -> od.getDestinationDistrict()).collect(Collectors.toSet());
        originDistrictNames.addAll(destinationDistrictNames);
        List<DistrictCoord> coords = coordRepo.findByVersionAndCityAndDistrictIn(version, city, originDistrictNames);
        Map<String, Double[]> coordMap = new HashMap<>();
        coords.stream().forEach(coord -> coordMap.put(coord.getDistrict(), new Double[]{coord.getLng(), coord.getLat()}));
        return ods.stream().map(od -> {
            String oName = od.getOriginDistrict();
            String dName = od.getDestinationDistrict();
            return LinesData.builder().dName(dName).dCoord(coordMap.get(dName))
                    .oName(oName).oCoord(coordMap.get(oName))
                    .value(isCu ? od.getCuod() : od.getKyod()).build();
        }).collect(Collectors.toList());
    }

    public List<NameValue<Double>> getTop10(String city, String date, String version, boolean isCu) {
        List<DistrictOD> ods = isCu
                ? districtRepo.findTop10CuInterDistrictOd(city, date, version)
                : districtRepo.findTop10KyInterDistrictOd(city, date, version);
        return ods.stream().map(od ->
                new NameValue<Double>(od.getOriginDistrict() + '-' + od.getDestinationDistrict(), isCu ? od.getCuod() : od.getKyod()))
                .collect(Collectors.toList());
    }

    public List<DistrictOD> getInterPercent(String city, String date, String version, boolean isAll) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<DistrictOD> cq = cb.createQuery(DistrictOD.class);
        Root<DistrictOD> root = cq.from(DistrictOD.class);
        cq.multiselect(root.get("originDistrict"), cb.sumAsDouble(root.get("cuod")), cb.sumAsDouble(root.get("kyod")));
        cq.groupBy(root.get("originDistrict"));
        cq.where(cb.equal(root.get("city"),city),
                cb.equal(root.get("dateType"), date),
                cb.equal(root.get("version"), version));
        if (isAll)
            cq.where(cb.notEqual(root.get("originDistrict"), root.get("destinationDistrict")));
        List<DistrictOD> result = em.createQuery(cq).getResultList();
        em.close();
        return result;
    }

    public List<LinesData> getGridData(String city, String date, String version, boolean isCu) {
        List<GridOD> datas = gridRepo.findByCityAndDateTypeAndVersion(city, date, version);
        return datas.parallelStream().map(data ->
                new LinesData(parseCoord(data.getOriginGrid()), parseCoord(data.getDestinationGrid()),
                    null, null, isCu ? data.getCuod() : data.getKyod())).collect(Collectors.toList());
    }

    private Double[] parseCoord(String coordStr) {
        String[] coordStrs = coordStr.split("_");
        String lngStr = coordStrs[0];
        StringBuilder sb = new StringBuilder(lngStr);
        lngStr = sb.insert(3, '.').toString();
        Double lng = Double.parseDouble(lngStr);

        String latStr = coordStrs[1];
        sb = new StringBuilder(latStr);
        latStr = sb.insert(2, '.').toString();
        Double lat = Double.parseDouble(latStr);

        return new Double[] {lng, lat};
    }
}
