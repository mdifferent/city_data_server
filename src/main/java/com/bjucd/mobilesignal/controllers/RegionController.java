package com.bjucd.mobilesignal.controllers;

import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import com.bjucd.mobilesignal.services.GridService;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by I015703 on 6/12/2019.
 */
@Controller
@CrossOrigin
@RequestMapping("/api/region")
public class RegionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GridService gridSvc;

    @RequestMapping("/list/{type}")
    public Set<String> getRegionList(@RequestParam(name = "city") String city,
                                     @PathVariable(name="type") String type) {
        if (type.equals("district"))
            return gridSvc.getDistrictList(city);
        else if (type.equalsIgnoreCase("hot"))
            return gridSvc.getUserRegionList(city);
        return new HashSet<>();
    }

    @RequestMapping("/grid/{type}")
    public List<Double[]> getGrids(@RequestParam(name = "city") String city,
                         @RequestParam(name = "region") String region,
                         @PathVariable(name="type") String type) {
        if (type.equals("district"))
            return gridSvc.getGrids(city, region);
        else if (type.equalsIgnoreCase("hot"))
            return gridSvc.getUserGrids(city, region);
        return new LinkedList();
    }

}
