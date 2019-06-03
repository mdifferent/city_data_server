package com.bjucd.mobilesignal.controllers;

import com.bjucd.mobilesignal.repositoriies.od.DistrictOdRepository;
import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bjucd.mobilesignal.models.od.DistrictOD;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
@RestController
@RequestMapping("/od")
@CrossOrigin
public class ODControllers {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemConfigRepository sysRepo;

    @Autowired
    DistrictOdRepository districtRepo;

    @RequestMapping("/district")
    public List<DistrictOD> getDistrictODs(@RequestParam("city") String city,
                                           @RequestParam("dateType") String dateType) {
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<DistrictOD> ods = districtRepo.findByCityAndDateTypeAndVersion(city, dateType, version);
            return ods;
        }
        return null;
    }
}
