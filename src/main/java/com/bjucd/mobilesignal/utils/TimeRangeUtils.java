package com.bjucd.mobilesignal.utils;

import com.bjucd.mobilesignal.models.config.CityConfig;
import com.bjucd.mobilesignal.repositoriies.config.CityConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.invoke.MethodHandles;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by I015703 on 6/9/2019.
 */
@Service
public class TimeRangeUtils {

    static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static List<Integer[]> getTimePairs(CityConfig cityConfig, String timeRangeType) {
        boolean isMorning = timeRangeType.equals("1");
        Integer startHour = isMorning ? cityConfig.getMorningStartHour() : cityConfig.getNightStartHour();
        Integer startMin = isMorning ? cityConfig.getMorningStartMin() : cityConfig.getNightStartMin();
        Integer endHour = isMorning ? cityConfig.getMorningEndHour() : cityConfig.getNightEndHour();
        Integer endMin = isMorning ? cityConfig.getMorningEndMin() : cityConfig.getNightEndMin();

        List<Integer[]> result = new LinkedList<>();
        Integer currentHour = startHour;
        Integer currentMin = startMin;
        do {
            result.add(new Integer[]{currentHour, currentMin});
            if (currentMin == 0)
                currentMin = 30;
            else {
                currentHour++;
                currentMin = 0;
            }
        } while (currentHour != endHour || currentMin != endMin);
        return result;
    }

    public Predicate createWhereCondition(CriteriaBuilder cb, Root root, List<Integer[]> times, String[] colNames) {
        int size = times.size();
        Predicate[] predicates = new Predicate[size];
        for (int i = 0; i < size; ++i) {
            Integer[] time = times.get(i);
            predicates[i] = cb.and(cb.equal(root.get(colNames[0]), time[0]), cb.equal(root.get(colNames[1]), time[1]));
        }
        return cb.or(predicates);
    }

}
