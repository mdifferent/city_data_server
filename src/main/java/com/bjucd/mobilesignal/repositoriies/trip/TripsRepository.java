package com.bjucd.mobilesignal.repositoriies.trip;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bjucd.mobilesignal.models.trip.Trips;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface TripsRepository extends JpaRepository<Trips, Integer> {

    List<Trips> findByCityAndVersion(String city, String version);
}
