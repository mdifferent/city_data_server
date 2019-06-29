package com.bjucd.mobilesignal.repositoriies.region;

import com.bjucd.mobilesignal.models.region.GridUserAge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by I015703 on 6/24/2019.
 */
public interface GridUserAgeRepository extends JpaRepository<GridUserAge, Integer> {
    List<GridUserAge> findByGridIdAndVersion(String gridId, String veriosn);
    List<GridUserAge> findByGridIdInAndVersion(Collection<String> gridId, String veriosn);
}
