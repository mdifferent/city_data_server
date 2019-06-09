package com.bjucd.mobilesignal.repositoriies.od;

import com.bjucd.mobilesignal.models.od.DistrictOD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface DistrictOdRepository extends JpaRepository<DistrictOD, String> {

    @Query(value = "SELECT * from public.CX_TJ_OD WHERE city = ?1 AND date_type = ?2 AND version = ?3 " +
            "AND O_DISTRICT != D_DISTRICT ORDER BY CU_OD DESC LIMIT 10", nativeQuery = true)
    List<DistrictOD> findTop10CuInterDistrictOd(String city, String DateType, String Version);

    @Query(value = "SELECT * from public.CX_TJ_OD WHERE city = ?1 AND date_type = ?2 AND version = ?3 " +
                  "AND O_DISTRICT != D_DISTRICT ORDER BY KY_OD DESC LIMIT 10", nativeQuery = true)
    List<DistrictOD> findTop10KyInterDistrictOd(String city, String DateType, String Version);
}
