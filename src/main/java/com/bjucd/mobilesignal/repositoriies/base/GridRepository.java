package com.bjucd.mobilesignal.repositoriies.base;

import com.bjucd.mobilesignal.models.base.Grid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface GridRepository extends JpaRepository<Grid, Integer> {
    List<Grid> findByCity(String city);
    List<Grid> findByCityAndDistrict(String city, String district);
}
