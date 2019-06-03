package com.bjucd.mobilesignal.repositoriies;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import com.bjucd.mobilesignal.models.MainPageInfo;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface MainPageInfoRepository extends JpaRepository<MainPageInfo, Integer> {

    MainPageInfo findOneByVersion(String version);
}
