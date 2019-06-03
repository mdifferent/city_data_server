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
@Table(schema = "public", name="JC_CITY")
@Cacheable(false)
public class CityCoord {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="province")
    private String province;

    @Column(name="city")
    private String city;

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
