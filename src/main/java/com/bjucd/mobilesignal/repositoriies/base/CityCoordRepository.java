package com.bjucd.mobilesignal.repositoriies.base;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bjucd.mobilesignal.models.base.CityCoord;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface CityCoordRepository extends JpaRepository<CityCoord, Integer> {
    List<CityCoord> findByVersionAndCityIn(String version, List<String> cities);
    List<CityCoord> findByVersion(String version);
    CityCoord findOneByCityAndVersion(String city, String version);
}
