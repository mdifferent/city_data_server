package com.bjucd.mobilesignal.repositoriies.population;

import com.bjucd.mobilesignal.models.population.MigrationInChina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface MigrationInChinaRepository extends JpaRepository<MigrationInChina, Integer> {
    List<MigrationInChina> findByCityAndVersion(String city, String version);
}
