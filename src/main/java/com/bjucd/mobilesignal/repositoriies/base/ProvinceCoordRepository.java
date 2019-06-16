package com.bjucd.mobilesignal.repositoriies.base;

import com.bjucd.mobilesignal.models.base.ProvinceCoord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface ProvinceCoordRepository extends JpaRepository<ProvinceCoord, Integer> {
    List<ProvinceCoord> findByVersionAndProvinceIn(String version, List<String> provinces);
    List<ProvinceCoord> findByProvinceAndVersion(String province, String version);
}
