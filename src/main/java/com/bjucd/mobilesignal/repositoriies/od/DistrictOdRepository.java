package com.bjucd.mobilesignal.repositoriies.od;

import com.bjucd.mobilesignal.models.od.DistrictOD;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface DistrictOdRepository extends JpaRepository<DistrictOD, String> {

    List<DistrictOD> findByCityAndDateTypeAndVersion(String city, String DateType, String Version);
}
