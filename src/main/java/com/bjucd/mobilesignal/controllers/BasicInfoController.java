package com.bjucd.mobilesignal.controllers;

import com.bjucd.mobilesignal.models.base.CityCoord;
import com.bjucd.mobilesignal.models.base.DistrictCoord;
import com.bjucd.mobilesignal.models.base.DistrictPolygon;
import com.bjucd.mobilesignal.models.responseBody.NameValue;
import com.bjucd.mobilesignal.repositoriies.base.CityCoordRepository;
import com.bjucd.mobilesignal.repositoriies.base.DistrictCoordRepository;
import com.bjucd.mobilesignal.repositoriies.base.DistrictPolygonRepository;
import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import com.bjucd.mobilesignal.utils.PolygonUtils;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 5/28/2019.
 */
@RestController
@RequestMapping("/basic")
@CrossOrigin
public class BasicInfoController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemConfigRepository sysRepo;

    @Autowired
    CityCoordRepository cityRepo;

    @Autowired
    DistrictCoordRepository districtCoordRepo;

    @Autowired
    DistrictPolygonRepository districtPolygonRepo;

    @RequestMapping("/cityCoords")
    public List<CityCoord> getCityCoords(@RequestParam(name = "city", required = false) String cities) {

        List<CityCoord> result = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            result = StringUtils.isNotBlank(cities)
                    ? cityRepo.findByVersionAndCityIn(version, Arrays.asList(cities.split(",")))
                    : cityRepo.findByVersion(version);
        }
        return result;
    }

    @RequestMapping("/districtCoords")
    public List<NameValue<Double[]>> getDistrictCoords(@RequestParam(name = "city") String city) {
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<DistrictCoord> coords = districtCoordRepo.findByVersionAndCity(version, city);
            return coords.parallelStream().map(coord ->
                    new NameValue<Double[]>(coord.getDistrict(), new Double[]{coord.getLng(), coord.getLat()})).collect(Collectors.toList());
        }
        return new LinkedList<>();
    }

    @RequestMapping("/districtPolygons")
    @Transactional
    public Map<String, String> getDistrictPolygonsOfCity(@RequestParam(name = "province") String province,
                                                     @RequestParam(name = "city") String city) {
        Map<String, String> result = new HashMap<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<DistrictPolygon> polygons = districtPolygonRepo.findByProvinceAndCityAndVersion(province, city, version);
            for (DistrictPolygon polygon: polygons) {
                String polygonStr = polygon.getPolygon();
                if (StringUtils.isBlank(polygonStr)) {
                    polygonStr = PolygonUtils.polygon2CoordList(polygon.getWkt());
                    polygon.setPolygon(polygonStr);
                    districtPolygonRepo.save(polygon);
                }
                result.put(polygon.getDistrict(), polygonStr);
            }
        }
        return result;
    }
}
