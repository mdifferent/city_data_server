package com.bjucd.mobilesignal.repositoriies;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bjucd.mobilesignal.models.ChartInfo;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface ChartInfoRepository extends JpaRepository<ChartInfo, Integer> {
    List<ChartInfo> findByTableNameAndVersion(String tableName, String version);
    List<ChartInfo> findByCityAndTableNameAndVersion(String city, String tableName, String version);
}
