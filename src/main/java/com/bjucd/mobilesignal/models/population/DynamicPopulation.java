package com.bjucd.mobilesignal.models.population;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by I015703 on 5/27/2019.
 */
@Entity
@Data
@Table(schema = "public", name="ZZ_WG_DYNAMIC_POPULATION")
@Cacheable(false)
@SequenceGenerator(name="dynamic_population_seq")
public class DynamicPopulation {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="dynamic_population_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="city")
    private String city;

    @Column(name="lng")
    private Double lng;

    @Column(name="lat")
    private Double lat;

    @Column(name="DATE_TYPE")
    private String dateType;

    @Column(name="TIME_HOUR")
    private int timeHour;

    @Column(name="CU_POP")
    private Double cuPop;

    @Column(name="KY_POP")
    private Double kyPop;

    @Column(name="version")
    @JsonIgnore
    private String version;
}
