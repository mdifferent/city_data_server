package com.bjucd.mobilesignal.controllers;

import com.bjucd.mobilesignal.models.responseBody.NameValue;
import com.bjucd.mobilesignal.models.trip.Trips;
import com.bjucd.mobilesignal.repositoriies.config.SystemConfigRepository;
import com.bjucd.mobilesignal.services.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by I015703 on 5/31/2019.
 */
@RestController
@RequestMapping("/trip")
@CrossOrigin
public class TripController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    SystemConfigRepository sysRepo;

    @Autowired
    TripService tripSvc;

    @RequestMapping("/cu")
    public List<NameValue<Integer>> getCuValues(@RequestParam(name = "city") String city,
                                                @RequestParam(name = "date") String date,
                                                @RequestParam(name = "time") String time,
                                                @RequestParam(name = "od") String od) {
        List<NameValue<Integer>> result = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<Trips> trips = tripSvc.getTrips(city, version, date, time);
            result = trips.stream().map(trip ->
                    new NameValue<Integer>(trip.getDistrict(), od.equals("o") ? trip.getCu_o() : trip.getCu_d()))
                    .collect(Collectors.toList());
        }
        return result;
    }

    @RequestMapping("/ky")
    public List<NameValue<Integer>> getKyValues(@RequestParam(name = "city") String city,
                                                @RequestParam(name = "date") String date,
                                                @RequestParam(name = "time") String time,
                                                @RequestParam(name = "od") String od) {
        List<NameValue<Integer>> result = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<Trips> trips = tripSvc.getTrips(city, version, date, time);
            result = trips.stream().map(trip ->
                    new NameValue<Integer>(trip.getDistrict(), od.equals("o") ? trip.getKy_o() : trip.getKy_d()))
                    .collect(Collectors.toList());
        }
        return result;
    }
/*
    @RequestMapping("/avg")
    public List<NameValue<Integer[]>> getAvgValues(@RequestParam(name = "city") String city) {
        List<NameValue<Integer[]>> result = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<Trips> trips = tripRepo.findByCityAndVersion(city, version);
            result = trips.stream().map(trip ->
                    new NameValue<Integer[]>(trip.getDistrict(), new Integer[]{trip.getAvg_distance(), trip.getAvg_time()}))
                    .collect(Collectors.toList());
        }
        return result;
    }*/

    @RequestMapping("/distribute/24hours")
    public List<NameValue<Double[]>> getTripAmount24Hours(@RequestParam(name = "city") String city,
                                                           @RequestParam(name = "date") String date,
                                                           @RequestParam(name = "cuky") String cuky) {
        List<NameValue<Double[]>> result = new LinkedList<>();
        boolean isCu = cuky.equals("cu");
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<Trips> trips = tripSvc.get24HoursDistributeForCity(city, version, date);
            Integer dSum = trips.stream().map(trips1 -> isCu ? trips1.getCu_d() : trips1.getKy_d())
                    .reduce((a, b) -> a + b).get();
            Integer oSum = trips.stream().map(trips1 -> isCu ? trips1.getCu_o() : trips1.getKy_o())
                    .reduce((a, b) -> a + b).get();
            logger.info("{}", dSum);
            logger.info("{}", oSum);
            result = trips.stream().map(trip ->
                    new NameValue<Double[]>(trip.getTime_hour().toString(), isCu
                            ? new Double[]{(double)trip.getCu_o() / oSum, (double)trip.getCu_d() / dSum}
                            : new Double[]{(double)trip.getKy_o() / oSum, (double)trip.getKy_d() / dSum}))
                    .collect(Collectors.toList());
        }
        return result;
    }

    @RequestMapping("/distribute/district")
    public List<NameValue<Integer[]>> getTripDistrict(@RequestParam(name = "city") String city,
                                                      @RequestParam(name = "date") String date,
                                                      @RequestParam(name = "time") String time,
                                                      @RequestParam(name = "cuky") String cuky) {
        List<NameValue<Integer[]>> result = new LinkedList<>();
        boolean isCu = cuky.equals("cu");
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<Trips> trips = tripSvc.getTrips(city, version, date, time);
            result = trips.stream().map(trip ->
                    new NameValue<Integer[]>(trip.getDistrict().toString(), isCu
                            ? new Integer[]{trip.getCu_o(), trip.getCu_d()}
                            : new Integer[]{trip.getKy_o(), trip.getKy_d()}))
                    .collect(Collectors.toList());
        }
        return result;
    }

    @RequestMapping("/distribute/avg")
    public List<NameValue<Integer[]>> getTripAvg(@RequestParam(name = "city") String city,
                                                      @RequestParam(name = "date") String date,
                                                      @RequestParam(name = "time") String time,
                                                      @RequestParam(name = "cuky") String cuky) {
        List<NameValue<Integer[]>> result = new LinkedList<>();
        if (sysRepo.existsById(1)) {
            String version = sysRepo.findById(1).get().getActiveVersion();
            List<Trips> trips = tripSvc.getAvgDistributeForCity(city, version, date, time, cuky);
            result = trips.stream().map(trip ->
                    new NameValue<Integer[]>(trip.getDistrict(),
                            new Integer[]{trip.getAvg_distance(), trip.getAvg_time()}))
                    .collect(Collectors.toList());
        }
        return result;
    }

}
