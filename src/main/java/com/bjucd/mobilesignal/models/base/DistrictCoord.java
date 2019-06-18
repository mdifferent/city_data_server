package com.bjucd.mobilesignal.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Clob;

/**
 * Created by I015703 on 5/27/2019.
 */
@Entity
@Data
@Table(name="JC_DISTRICT")
@Cacheable(false)
public class DistrictCoord {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="province")
    private String province;

    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="lng")
    private Double lng;

    @Column(name="lat")
    private Double lat;

    @Column(name="area")
    private Double area;


    @Column(name="version")
    @JsonIgnore
    private String version;

}
