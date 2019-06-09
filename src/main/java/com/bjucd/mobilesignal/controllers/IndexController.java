package com.bjucd.mobilesignal.controllers;

import com.bjucd.mobilesignal.models.AccessCityInfo;
import com.bjucd.mobilesignal.models.base.DistrictCoord;
import com.bjucd.mobilesignal.models.responseBody.CityCoordWithValue;
import com.bjucd.mobilesignal.models.responseBody.CityList;
import com.bjucd.mobilesignal.models.responseBody.NameValue;
import com.bjucd.mobilesignal.repositoriies.AccessCityInfoRepository;
import com.bjucd.mobilesignal.repositoriies.ChartInfoRepository;
import com.bjucd.mobilesignal.repositoriies.MainPageInfoRepository;
import com.bjucd.mobilesignal.repositoriies.base.CityCoordRepository;
import com.bjucd.mobilesignal.repositoriies.base.DistrictCoordRepository;
import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bjucd.mobilesignal.models.MainPageInfo;
import com.bjucd.mobilesignal.models.ChartInfo;
import com.bjucd.mobilesignal.models.base.CityCoord;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 5/28/2019.
 */
@RestController
@RequestMapping("/index")
@CrossOrigin
public class IndexController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemConfigRepository sysRepo;

    @Autowired
    MainPageInfoRepository mainPageRepo;

    @Autowired
    ChartInfoRepository chartRepo;

    @Autowired
    CityCoordRepository cityRepo;

    @Autowired
    DistrictCoordRepository disRepo;

    @Autowired
    AccessCityInfoRepository accessCityRepo;

    @Autowired
    EntityManagerFactory factory;

    @RequestMapping("/")
    public MainPageInfo getMainPageInfo() {
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            return mainPageRepo.findOneByVersion(version);
        }
        return null;
    }

    @RequestMapping("/chart")
    public List<ChartInfo> getMainPageChart(@RequestParam("city") String city,
                                            @RequestParam("chart") String chart) {
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            return chartRepo.findByCityAndTableNameAndVersion(city, chart, version);
        }
        return null;
    }

    @RequestMapping("/city")
    @Transactional
    public List<CityList> getCityList() {
        List<CityList> response = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            logger.info("{}", version);
            List<CityCoord> citys = cityRepo.findByVersion(version);
            logger.info("{}", citys.size());
            Map<String, List<CityCoord>> citiesInProvince = citys.parallelStream()
                    .collect(Collectors.groupingBy(city -> city.getProvince()));
            logger.info("{}", citiesInProvince.size());
            for (Map.Entry<String, List<CityCoord>> province: citiesInProvince.entrySet()) {
                CityList cityList = new CityList(province.getKey());
                logger.info(province.getKey());
                for (CityCoord city : province.getValue()) {
                    cityList.appendChild(city.getCity());
                }
                response.add(cityList);
            }
        }
        return response;
    }

    @RequestMapping("/district")
    @Transactional
    public List<CityList> getDistrictList() {
        List<CityList> response = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<DistrictCoord> coordList = disRepo.findByVersion(version);

            //Group by province
            Map<String, List<DistrictCoord>> byProvince = coordList.parallelStream()
                    .collect(Collectors.groupingByConcurrent(city -> city.getProvince()));
            //Get city list of each province
            for (Map.Entry<String, List<DistrictCoord>> province : byProvince.entrySet()) {
                CityList provinceAndCities = new CityList(province.getKey());
                List<DistrictCoord> recordOfEachProvince = province.getValue();
                //Group by city
                Map<String, List<DistrictCoord>> byCity =
                        recordOfEachProvince.parallelStream().collect(Collectors.groupingByConcurrent(city
                                -> city.getCity()));
                List<CityList> cityList = new LinkedList<>();
                //Append district list in every city
                for (Map.Entry<String, List<DistrictCoord>> city: byCity.entrySet()) {
                    CityList cityAndDistricts = new CityList(city.getKey());
                    for (DistrictCoord district : city.getValue()) {
                        cityAndDistricts.appendChild(district.getDistrict());
                    }
                    provinceAndCities.appendChild(cityAndDistricts);
                }
                response.add(provinceAndCities);
            }
        } else {
            logger.error("Invalid version");
        }
        return response;
    }


    @RequestMapping("/cityInfo")
    @Transactional
    public List<CityCoordWithValue> getAccessCityInfo() {
        List<CityCoordWithValue> result = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<AccessCityInfo> accessCityInfos = accessCityRepo.findByVersion(version);
            List<String> cities = accessCityInfos.stream().map(info -> info.getCity()).collect(Collectors.toList());
            List<CityCoord> coords = cityRepo.findByVersionAndCityIn(version, cities);
            coords.parallelStream().map(coord -> new CityCoordWithValue(coord))
                    .forEach( coord -> {
                        Integer value = accessCityInfos.stream().filter(info -> info.getCity().equals(coord.getName())).findFirst().get().getCuUserCount();
                        coord.setValue(value);
                        result.add(coord);
                    });
        }
        return result;
    }

    @RequestMapping("/gender")
    public List<NameValue<Double>> getGenderData(@RequestParam("city") String city) {
        List<NameValue<Double>> result = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<ChartInfo> chartInfos = chartRepo.findByCityAndTableNameAndVersion(city, "性别", version);
            result = chartInfos.stream().map(info -> new NameValue<Double>(info.getXIndex(), info.getYValue())).collect(Collectors.toList());
        }
        return result;
    }

    @RequestMapping("/age")
    public List<NameValue<Double>> getAgeData(@RequestParam("city") String city) {
        List<NameValue<Double>> result = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<ChartInfo> chartInfos = chartRepo.findByCityAndTableNameAndVersion(city, "年龄", version);
            result = chartInfos.stream().map(info -> new NameValue<Double>(info.getXIndex(), info.getYValue())).collect(Collectors.toList());
        }
        return result;
    }



}
