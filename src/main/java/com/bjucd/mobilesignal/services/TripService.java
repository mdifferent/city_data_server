package com.bjucd.mobilesignal.services;

import com.bjucd.mobilesignal.models.trip.Trips;
import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import com.bjucd.mobilesignal.repositoriies.trip.TripsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by I015703 on 5/31/2019.
 */
@Service
public class TripService {

    @Autowired
    SystemConfigRepository sysRepo;

    @Autowired
    TripsRepository tripRepo;

    @Autowired
    EntityManagerFactory factory;

    public List<Trips> getTrips(String city, String version, String dateType, String timeRange) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trips> cq = cb.createQuery(Trips.class);
        Root<Trips> root = cq.from(Trips.class);
        cq.multiselect(root.get("district"),
                cb.sum(root.get("cu_o")), cb.sum(root.get("cu_d")),
                cb.sum(root.get("ky_o")), cb.sum(root.get("ky_d")));
        cq.groupBy(root.get("district"));
        cq.where(cb.equal(root.get("city"),city),
                cb.equal(root.get("date_type"), dateType),
                cb.equal(root.get("version"), version));
        List<Trips> result = em.createQuery(cq).getResultList();
        em.close();
        return result;
    }

    public List<Trips> get24HoursDistributeForCity(String city, String version, String dateType, String cuky) {
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trips> cq = cb.createQuery(Trips.class);
        Root<Trips> root = cq.from(Trips.class);
            cq.multiselect(root.get("time_hour"),
                    cb.sum(root.get("cu_o")), cb.sum(root.get("cu_d")),
                    cb.sum(root.get("ky_o")), cb.sum(root.get("ky_d")));
        cq.groupBy(root.get("time_hour"));
        cq.where(cb.equal(root.get("city"),city),
                cb.equal(root.get("date_type"), dateType),
                cb.equal(root.get("version"), version));
        cq.orderBy(cb.asc(root.get("time_hour")));
        List<Trips> result = em.createQuery(cq).getResultList();
        em.close();
        return result;
    }


    public List<Trips> getAvgDistributeForCity(String city, String version, String dateType, String timeRange, String cuky) {
        String col = String.format("%s_o", cuky);
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trips> cq = cb.createQuery(Trips.class);
        Root<Trips> root = cq.from(Trips.class);
        cq.multiselect(root.get("district"),
                cb.quot(cb.sum(cb.prod(root.get("avg_distance"), root.get(col))), cb.sum(root.get(col))),
                cb.quot(cb.sum(cb.prod(root.get("avg_time"), root.get(col))), cb.sum(root.get(col))));
        cq.groupBy(root.get("district"));
        cq.where(cb.equal(root.get("city"),city),
                cb.equal(root.get("date_type"), dateType),
                cb.equal(root.get("version"), version));
        List<Trips> result = em.createQuery(cq).getResultList();
        em.close();
        return result;
    }

    public void getCityTripInfo(String city, String version, String dateType, String cuky) {
        String col = String.format("%s_o", cuky);
        EntityManager em = factory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Trips> cq = cb.createQuery(Trips.class);
        Root<Trips> root = cq.from(Trips.class);
        cq.multiselect(cb.sum(root.get(col)),
                cb.quot(cb.sum(cb.prod(root.get("avg_distance"), root.get(col))), cb.sum(root.get(col))),
                cb.quot(cb.sum(cb.prod(root.get("avg_time"), root.get(col))), cb.sum(root.get(col))));
        cq.where(cb.equal(root.get("city"),city),
                cb.equal(root.get("date_type"), dateType),
                cb.equal(root.get("version"), version));
        List<Trips> result = em.createQuery(cq).getResultList();

        //TODO 早高峰系数
        em.close();
        //return result;
    }

}
