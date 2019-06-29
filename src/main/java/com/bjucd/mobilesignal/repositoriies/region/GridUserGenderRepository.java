package com.bjucd.mobilesignal.repositoriies.region;

import com.bjucd.mobilesignal.models.region.GridUserGender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by I015703 on 6/24/2019.
 */
public interface GridUserGenderRepository extends JpaRepository<GridUserGender, Integer> {
    List<GridUserGender> findByGridIdAndVersion(String gridId, String veriosn);
    List<GridUserGender> findByGridIdInAndVersion(Collection<String> gridId, String veriosn);
}
