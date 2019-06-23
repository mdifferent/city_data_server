package com.bjucd.mobilesignal.services;

import com.bjucd.mobilesignal.models.base.Grid;
import com.bjucd.mobilesignal.models.base.UserGrid;
import com.bjucd.mobilesignal.repositoriies.base.GridRepository;
import com.bjucd.mobilesignal.repositoriies.base.UserGridRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 6/17/2019.
 */
@Service
public class GridService {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    GridRepository gridRepo;

    @Autowired
    UserGridRepository userGridRepo;

    public Set<String> getDistrictList(String city) {
        List<Grid> grids = gridRepo.findByCity(city);
        Set<String> districtNames = grids.parallelStream().map(grid -> grid.getDistrict()).collect(Collectors.toSet());
        return districtNames;
    }


    public List<Double[]> getGrids(String city, String district) {
        List<Grid> grids = gridRepo.findByCityAndDistrict(city, district);
        return grids.stream().map(g -> new Double[]{g.getLng(), g.getLat()}).collect(Collectors.toList());
    }

    public Set<String> getUserRegionList(String city) {
        List<UserGrid> grids = userGridRepo.findByCity(city);
        Set<String> districtNames = grids.parallelStream().map(grid -> grid.getUdZoneName()).collect(Collectors.toSet());
        return districtNames;
    }

    public List<Double[]> getUserGrids(String city, String name) {
        List<UserGrid> grids = userGridRepo.findByCityAndUdZoneName(city, name);
        return grids.stream().map(g -> new Double[]{g.getLng(), g.getLat()}).collect(Collectors.toList());
    }
}
