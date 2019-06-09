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
@Table(schema = "public", name = "CX_TJ_OD")
@SequenceGenerator(name="cx_tj_od_seq")
@Cacheable(false)
public class DistrictOD {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="cx_tj_od_seq")
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

    @Column(name="O_DISTRICT")
    private String originDistrict;

    @Column(name="D_DISTRICT")
    private String destinationDistrict;

    @Column(name="CU_OD")
    @JsonProperty("cu_od")
    private Double cuod;

    @Column(name="KY_OD")
    @JsonProperty("ky_od")
    private Double kyod;

    @Column(name="VERSION")
    private String version;

    public DistrictOD(String originDistrict, Double cuod, Double kyod) {
        this.originDistrict = originDistrict;
        this.cuod = cuod;
        this.kyod = kyod;
    }

    public DistrictOD(String originDistrict, String destinationDistrict, Double cuod, Double kyod) {
        this.originDistrict = originDistrict;
        this.destinationDistrict = destinationDistrict;
        this.cuod = cuod;
        this.kyod = kyod;
    }
}
