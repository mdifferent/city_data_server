package com.bjucd.mobilesignal.repositoriies.base;

import com.bjucd.mobilesignal.models.base.DistrictPolygon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/29/2019.
 */
public interface DistrictPolygonRepository extends JpaRepository<DistrictPolygon, Integer> {
    List<DistrictPolygon> findByProvinceAndCityAndVersion(String province, String city, String version);
    DistrictPolygon findOneByProvinceAndCityAndDistrictAndVersion(String province, String city, String district, String version);
}
