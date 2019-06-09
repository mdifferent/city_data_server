package com.bjucd.mobilesignal.repositoriies.config;

import com.bjucd.mobilesignal.models.config.CityConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by I015703 on 6/9/2019.
 */
public interface CityConfigRepository extends JpaRepository<CityConfig, String> {
    CityConfig findOneByCity(String city);
}
