package com.bjucd.mobilesignal.controllers;

import com.bjucd.mobilesignal.models.responseBody.GridPopulationResponse;
import com.bjucd.mobilesignal.services.GridService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by I015703 on 6/12/2019.
 */
@RestController
@RequestMapping("/api/region")
@CrossOrigin
public class RegionController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GridService gridSvc;

    @RequestMapping("/list/{type}")
    public Set<String> getRegionList(@RequestParam(name = "city") String city,
                                     @PathVariable(name = "type") String type) {
        if (type.equalsIgnoreCase("district"))
            return gridSvc.getDistrictList(city);
        else if (type.equalsIgnoreCase("hot"))
            return gridSvc.getHotRegionList(city);
        return new HashSet<>();
    }

    @RequestMapping("/population/{type}")
    public List<GridPopulationResponse> getRegionPopulations(@RequestParam(name = "city") String city,
                                                             @RequestParam(name = "region") String region,
                                                             @PathVariable(name = "type") String type,
                                                             @RequestParam(name = "cuky") String cuky) {
        boolean isCu = cuky.equalsIgnoreCase("cu");
        if (type.equalsIgnoreCase("district"))
            return gridSvc.getGrids(city, region, isCu);
        else if (type.equalsIgnoreCase("hot"))
            return gridSvc.getUserGrids(city, region, isCu);
        return new LinkedList();
    }

    @RequestMapping("/age/{type}")
    public Map<Integer, Double> getRegionAge(@PathVariable(name = "type") String type,
                                             @RequestParam(name = "city") String city,
                                             @RequestParam(name = "region") String region,
                                             @RequestParam(name = "cuky") String cuky) {
        boolean isCu = cuky.equalsIgnoreCase("cu");
        if (type.equalsIgnoreCase("district"))
            return gridSvc.getDistrictRegionAge(city, region, isCu);
        else if (type.equalsIgnoreCase("hot"))
            return gridSvc.getHotRegionAge(city, region, isCu);
        return new HashMap<>();
    }

    @RequestMapping("/gender/{type}")
    public Map<Integer, Double> getRegionGender(@PathVariable(name = "type") String type,
                                                @RequestParam(name = "city") String city,
                                                @RequestParam(name = "region") String region,
                                                @RequestParam(name = "cuky") String cuky) {
        boolean isCu = cuky.equalsIgnoreCase("cu");
        if (type.equalsIgnoreCase("district"))
            return gridSvc.getDistrictGender(city, region, isCu);
        else if (type.equalsIgnoreCase("hot"))
            return gridSvc.getHotGender(city, region, isCu);
        return new HashMap<>();
    }

    @RequestMapping("/od")
    public void getRegionOd(@RequestParam(name = "city") String city,
                            @RequestParam(name = "region") String region,
                            @RequestParam(name = "cuky") String cuky,
                            @RequestParam(name = "od") String od) {
        boolean isCu = cuky.equalsIgnoreCase("cu");

    }


}
