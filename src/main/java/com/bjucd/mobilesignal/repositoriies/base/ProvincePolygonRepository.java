package com.bjucd.mobilesignal.repositoriies.base;

import com.bjucd.mobilesignal.models.base.ProvincePolygon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by I015703 on 5/29/2019.
 */
public interface ProvincePolygonRepository extends JpaRepository<ProvincePolygon, Integer> {

    ProvincePolygon findOneByProvinceAndVersion(String province, String version);
}
