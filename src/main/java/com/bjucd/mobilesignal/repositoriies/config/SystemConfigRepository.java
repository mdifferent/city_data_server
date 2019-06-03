package com.bjucd.mobilesignal.repositoriies.config;

import com.bjucd.mobilesignal.models.config.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface SystemConfigRepository extends JpaRepository<SystemConfig, Integer> {
}
