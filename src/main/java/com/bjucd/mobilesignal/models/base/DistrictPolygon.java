package com.bjucd.mobilesignal.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.sql.Clob;

/**
 * Created by I015703 on 5/29/2019.
 */
@Entity
@Data
@Table(schema = "public", name="JC_DISTRICT_POLYGON")
@Cacheable(false)
public class DistrictPolygon {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="province")
    private String province;

    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="wkt", columnDefinition = "text")
    private String wkt;

    @Column(name="polygon", columnDefinition = "text")
    private String polygon;

    @Column(name="version")
    @JsonIgnore
    private String version;

}
