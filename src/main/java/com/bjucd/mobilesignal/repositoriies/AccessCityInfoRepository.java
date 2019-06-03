package com.bjucd.mobilesignal.repositoriies;

import com.bjucd.mobilesignal.models.AccessCityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface AccessCityInfoRepository extends JpaRepository<com.bjucd.mobilesignal.models.AccessCityInfo, Integer> {

    List<AccessCityInfo> findByVersion(String version);
}
