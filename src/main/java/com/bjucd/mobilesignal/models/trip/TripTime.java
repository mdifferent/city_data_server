package com.bjucd.mobilesignal.models.trip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by I015703 on 5/27/2019.
 */
@Data
@Entity
@NoArgsConstructor
@Table(schema = "public", name = "CX_TJ_TRIPS_TIME")
@SequenceGenerator(name = "cx_tj_trip_time_seq")
@Cacheable(false)
public class TripTime {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cx_tj_trip_time_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="date_type")
    private String date_type;

    @Column(name="time_hour")
    private Integer time_hour;

    @Column(name="time_min")
    private Integer time_min;

    @Column(name="trip_time")
    private Integer tripTime;

    @Column(name="cu_percent")
    private Double cuPercent;

    @Column(name="ky_percent")
    private Double kyPercent;

    @Column(name="version")
    @JsonIgnore
    private String version;

}
