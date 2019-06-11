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
@Table(schema = "public", name = "CX_WG_OD")
@SequenceGenerator(name="cx_wg_od_seq")
@Cacheable(false)
public class GridOD {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cx_wg_od_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="CITY")
    private String city;

    @Column(name="DATE_TYPE")
    private String dateType;

    @Column(name="TIME_HOUR")
    private int timeHour;

    @Column(name="TIME_MIN")
    private int timeMinute;

    @Column(name="O_GRID")
    private String originGrid;

    @Column(name="D_GRID")
    private String destinationGrid;

    @Column(name="CU_OD")
    private Double cuod;

    @Column(name="KY_OD")
    private Double kyod;

    @Column(name="VERSION")
    private String version;

    public GridOD(String originGrid, String destinationGrid, Double cuod, Double kyod) {
        this.originGrid = originGrid;
        this.destinationGrid = destinationGrid;
        this.cuod = cuod;
        this.kyod = kyod;
    }

}
