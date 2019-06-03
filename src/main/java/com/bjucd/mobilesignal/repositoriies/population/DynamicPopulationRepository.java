package com.bjucd.mobilesignal.repositoriies.population;

import com.bjucd.mobilesignal.models.population.DynamicPopulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface DynamicPopulationRepository extends JpaRepository<DynamicPopulation, Integer> {

    List<DynamicPopulation> findByCityAndDateTypeAndTimeHourAndVersion(String city, String dateType, Integer time, String verion);
}
