package com.bjucd.mobilesignal.controllers;

import com.bjucd.mobilesignal.models.config.CityConfig;
import com.bjucd.mobilesignal.models.responseBody.LinesData;
import com.bjucd.mobilesignal.models.responseBody.NameValue;
import com.bjucd.mobilesignal.repositoriies.config.CityConfigRepository;
import com.bjucd.mobilesignal.repositoriies.od.DistrictOdRepository;
import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import com.bjucd.mobilesignal.services.OdService;
import com.bjucd.mobilesignal.utils.TimeRangeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.bjucd.mobilesignal.models.od.DistrictOD;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 5/27/2019.
 */
@RestController
@RequestMapping("/od")
@CrossOrigin
public class ODControllers {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OdService odSvc;

    @Autowired
    SystemConfigRepository sysRepo;

    @Autowired
    CityConfigRepository cityConfigRepo;

    @RequestMapping("/district/intra")
    public List<NameValue<Double[]>> getIntraDistrictODs(@RequestParam("city") String city,
                                                @RequestParam("date") String date,
                                                         @RequestParam("time") String time,
                                                @RequestParam("cuky") String cuky) {
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            boolean isCu = cuky.equalsIgnoreCase("cu");
            CityConfig cityConfig = cityConfigRepo.findOneByCity(city);
            if (cityConfig == null) {
                logger.error("未找到当前城市配置信息");
                return new LinkedList<>();
            }
            List<Integer[]> times = TimeRangeUtils.getTimePairs(cityConfig, time);
            return odSvc.getDistrictPointOdData(city, date, version, times, isCu);
        }
        return new LinkedList<>();
    }

    @RequestMapping("/district/inter")
    public List<LinesData> getInterDistrict(@RequestParam("city") String city,
                                            @RequestParam("date") String date,
                                            @RequestParam("time") String time,
                                            @RequestParam("cuky") String cuky) {
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            boolean isCu = cuky.equalsIgnoreCase("cu");
            CityConfig cityConfig = cityConfigRepo.findOneByCity(city);
            if (cityConfig == null) {
                logger.error("未找到当前城市配置信息");
                return new LinkedList<>();
            }
            List<Integer[]> times = TimeRangeUtils.getTimePairs(cityConfig, time);
            return odSvc.getInterOdData(city, date, version, times, isCu);
        }
        return new LinkedList<>();
    }

    @RequestMapping("/top10")
    public List<NameValue<Double>> getTop10Od(@RequestParam("city") String city,
                                              @RequestParam("date") String date,
                                              @RequestParam("cuky") String cuky) {
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            boolean isCu = cuky.equalsIgnoreCase("cu");
            return odSvc.getTop10(city, date, version, isCu);
        }
        return new LinkedList<>();
    }

    @RequestMapping("/percent")
    public List<NameValue<Double>> getInterPercent(@RequestParam("city") String city,
                                                   @RequestParam("date") String date,
                                                   @RequestParam("cuky") String cuky) {
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            boolean isCu = cuky.equalsIgnoreCase("cu");
            List<DistrictOD> numeratorList = odSvc.getInterPercent(city, date, version, false);
            Map<String, Double> numeratorMap = new HashMap<>();
            numeratorList.stream().forEach(numerator ->
                    numeratorMap.put(numerator.getOriginDistrict(), isCu ? numerator.getCuod() : numerator.getKyod()));
            List<DistrictOD> denominator = odSvc.getInterPercent(city, date, version, true);
            return denominator.stream().map(result -> {
                String districtName = result.getOriginDistrict();
                Double numVal = numeratorMap.get(districtName);
                return new NameValue<Double>(districtName,
                        isCu ?  numVal / result.getCuod()  : numVal / result.getKyod());
            }).collect(Collectors.toList());
        }
        return new LinkedList<>();
    }

    @RequestMapping("/grid")
    public List<LinesData> getGrid(@RequestParam("city") String city,
                        @RequestParam("date") String date,
                                   @RequestParam("time") String time,
                        @RequestParam("cuky") String cuky) {
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            boolean isCu = cuky.equalsIgnoreCase("cu");
            CityConfig cityConfig = cityConfigRepo.findOneByCity(city);
            if (cityConfig == null) {
                logger.error("未找到当前城市配置信息");
                return new LinkedList<>();
            }
            List<Integer[]> times = TimeRangeUtils.getTimePairs(cityConfig, time);
            return odSvc.getGridData(city, date, version, times, isCu);
        }
        return new LinkedList<>();
    }
}
