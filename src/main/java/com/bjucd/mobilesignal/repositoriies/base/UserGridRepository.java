package com.bjucd.mobilesignal.repositoriies.base;

import com.bjucd.mobilesignal.models.base.UserGrid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by I015703 on 5/27/2019.
 */
public interface UserGridRepository extends JpaRepository<UserGrid, Integer> {
    List<UserGrid> findByCity(String city);
    List<UserGrid> findByCityAndUdZoneName(String city, String name);
}
