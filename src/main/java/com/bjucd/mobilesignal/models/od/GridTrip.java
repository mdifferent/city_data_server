package com.bjucd.mobilesignal.models.od;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by I015703 on 5/27/2019.
 */
@Data
@Entity
@NoArgsConstructor
@Table(schema = "public", name = "CX_WG_TRIPS")
@SequenceGenerator(name="cx_wg_trips_seq")
@Cacheable(false)
public class GridTrip {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cx_wg_trips_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="CITY")
    private String city;

    @Column(name="GRID_ID")
    private String gridId;

    @Column(name="DATE_TYPE")
    private String dateType;

    @Column(name="TIME_HOUR")
    private Integer timeHour;

    @Column(name="TIME_MIN")
    private Integer timeMinute;

    @Column(name="CU_O")
    private Integer cuo;

    @Column(name="CU_D")
    private Integer cud;

    @Column(name="KY_O")
    private Integer kyo;

    @Column(name="KY_D")
    private Integer kyd;

    @Column(name="AVG_DISTANCE")
    private Integer avgDistance;

    @Column(name="AVG_TIME")
    private Integer avgTime;

    @Column(name="VERSION")
    private String version;


}
