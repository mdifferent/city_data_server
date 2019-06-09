package com.bjucd.mobilesignal.models.config;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by I015703 on 6/9/2019.
 */
@Entity
@Data
@Table(schema = "public", name="city_config")
@SequenceGenerator(name="city_config_seq")
@Cacheable(false)
public class CityConfig {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="city_config_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="province")
    private String province;

    @Column(name="city")
    private String city;

    @Column(name="morning_peak_start_hour")
    private Integer morningStartHour;
    @Column(name="morning_peak_start_min")
    private Integer morningStartMin;
    @Column(name="morning_peak_end_hour")
    private Integer morningEndHour;
    @Column(name="morning_peak_end_min")
    private Integer morningEndMin;

    @Column(name="night_peak_start_hour")
    private Integer nightStartHour;
    @Column(name="night_peak_start_min")
    private Integer nightStartMin;
    @Column(name="night_peak_end_hour")
    private Integer nightEndHour;
    @Column(name="night_peak_end_min")
    private Integer nightEndMin;

    @Column(name="north_grid_count")
    private Integer northGridCount;

    @Column(name="south_grid_count")
    private Integer southGridCount;

    @Column(name="east_grid_count")
    private Integer eastGridCount;

    @Column(name="west_grid_count")
    private Integer westGridCount;

}
