package com.bjucd.mobilesignal.repositoriies.population;

import com.bjucd.mobilesignal.models.population.Population;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface PopulationRepository extends JpaRepository<Population, Integer> {
    List<Population> findByCityAndVersion(String city, String version);
}
