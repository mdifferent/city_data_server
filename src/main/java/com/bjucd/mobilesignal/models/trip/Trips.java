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
@Table(name = "CX_TJ_TRIPS")
@SequenceGenerator(name = "cx_tj_trips_seq")
@Cacheable(false)
public class Trips {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cx_tj_trips_seq")
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

    @Column(name="cu_o")
    private Integer cu_o;

    @Column(name="cu_d")
    private Integer cu_d;

    @Column(name="ky_o")
    private Integer ky_o;

    @Column(name="ky_d")
    private Integer ky_d;

    @Column(name="avg_distance")
    private Integer avg_distance;

    @Column(name="avg_time")
    private Integer avg_time;

    @Column(name="version")
    @JsonIgnore
    private String version;

    public Trips(String district, long cu_o, long cu_d, long ky_o, long ky_d) {
        this.district = district;
        this.cu_o = (int)cu_o;
        this.cu_d = (int)cu_d;
        this.ky_o = (int)ky_o;
        this.ky_d = (int)ky_d;
    }

    public Trips (Integer hour, long cu_o, long cu_d, long ky_o, long ky_d) {
        this.time_hour = hour;
        this.cu_o = (int)cu_o;
        this.cu_d = (int)cu_d;
        this.ky_o = (int)ky_o;
        this.ky_d  = (int)ky_d;
    }

    public Trips(String district, long avg_distance, long avg_time) {
        this.district = district;
        this.avg_distance = (int)avg_distance;
        this.avg_time = (int)avg_time;
    }

}
