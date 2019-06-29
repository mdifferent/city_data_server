package com.bjucd.mobilesignal.repositoriies.region;

import com.bjucd.mobilesignal.models.region.GridPopulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 6/24/2019.
 */
public interface GridPopulationRepository extends JpaRepository<GridPopulation, Integer> {
    List<GridPopulation> findByCityAndVersion(String city, String version);
    List<GridPopulation> findByCityAndGridIdInAndVersion(String city, List<String> gridIds, String version);
}
