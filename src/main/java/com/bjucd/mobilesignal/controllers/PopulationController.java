package com.bjucd.mobilesignal.controllers;

import com.bjucd.mobilesignal.models.population.DynamicPopulation;
import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import com.bjucd.mobilesignal.repositoriies.population.DynamicPopulationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 6/3/2019.
 */
@RestController
@RequestMapping("/pop")
@CrossOrigin
public class PopulationController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemConfigRepository sysRepo;

    @Autowired
    DynamicPopulationRepository dynaRepo;


    @RequestMapping("/dynamic")
    public List<Double[]> getDynamicDistribution(@RequestParam(name = "city") String city,
                                       @RequestParam(name = "date") String date,
                                       @RequestParam(name = "time") Integer time,
                                       @RequestParam(name = "cuky") String cuky) {
        List<Double[]> result = new LinkedList<>();
        boolean isCu = cuky.equals("cu");
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<DynamicPopulation> data = dynaRepo.findByCityAndDateTypeAndTimeHourAndVersion(city, date, time, version);
            result = data.stream().map( data1 ->
                    new Double[] {data1.getLng(), data1.getLat(),
                            isCu ? data1.getCuPop() : data1.getKyPop()}).collect(Collectors.toList());
        }
        return result;
    }

    @RequestMapping("/density")
    public void getPopulationDensityData(@RequestParam(name = "city") String city) {

    }

    @RequestMapping("/{type}")
    public void getPopulationOfCity(@RequestParam(name = "city") String city,
                                    @PathVariable("type") String type) {

    }
}
