package com.bjucd.mobilesignal.utils;

import com.bjucd.mobilesignal.models.base.CityCoord;
import com.bjucd.mobilesignal.repositoriies.base.CityCoordRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;


/**
 * Created by I015703 on 6/27/2019.
 */
@Component
@EnableScheduling
public class CsvImportUtils {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CityCoordRepository cityRepo;

    //@Scheduled(fixedRate = 30 * 60 * 1000L, initialDelay = 3000L)
    public void importCity() {
        try(FileInputStream fileInputStream = new FileInputStream("C:\\Dev\\data\\backup\\jc_city.csv")) {
            Reader reader = new InputStreamReader(fileInputStream, "utf-8");
            List<CityCoord> cities = new CsvToBeanBuilder<CityCoord>(reader)
                    .withType(CityCoord.class).build().parse();
            cityRepo.saveAll(cities);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
