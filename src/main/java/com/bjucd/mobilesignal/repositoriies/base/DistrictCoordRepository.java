package com.bjucd.mobilesignal.repositoriies.base;

import com.bjucd.mobilesignal.models.base.DistrictCoord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface DistrictCoordRepository extends JpaRepository<DistrictCoord, Integer> {
    List<DistrictCoord> findByVersion(String version);
    List<DistrictCoord> findByVersionAndCity(String version, String city);
    List<DistrictCoord> findByVersionAndCityAndDistrictIn(String version, String city, Collection<String> districts);
}
