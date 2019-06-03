package com.bjucd.mobilesignal.repositoriies.base;

import com.bjucd.mobilesignal.models.base.CityPolygon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by I015703 on 5/29/2019.
 */
public interface CityPolygonRepository extends JpaRepository<CityPolygon, Integer> {

    CityPolygon findOneByProvinceAndCityAndVersion(String province, String city, String version);

}
