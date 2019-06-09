package com.bjucd.mobilesignal.repositoriies.od;

import com.bjucd.mobilesignal.models.od.GridOD;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface GridOdRepository extends JpaRepository<GridOD, Integer> {

    List<GridOD> findByCityAndDateTypeAndVersion(String city, String dateType, String version);
}
