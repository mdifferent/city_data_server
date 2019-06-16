package com.bjucd.mobilesignal.repositoriies.population;

import com.bjucd.mobilesignal.models.population.MigrationInProvince;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface MigrationInProvinceRepository extends JpaRepository<MigrationInProvince, Integer> {

    List<MigrationInProvince> findByCityAndVersion(String city, String version);
}
