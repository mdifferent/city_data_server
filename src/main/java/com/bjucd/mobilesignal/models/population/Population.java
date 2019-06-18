package com.bjucd.mobilesignal.models.population;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="ZZ_TJ_POPULATION")
@Cacheable(false)
@SequenceGenerator(name="population_seq")
public class Population {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="population_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="area")
    private Double area;

    @Column(name="cu_resident")
    private Integer cuResident;

    @Column(name="CU_EMPLOYMENT")
    private Integer cuEmployment;

    @Column(name="CU_TEMPORARY")
    private Integer cuTemporary;

    @Column(name="ky_resident")
    private Integer kyResident;

    @Column(name="KY_EMPLOYMENT")
    private Integer kyEmployment;

    @Column(name="KY_TEMPORARY")
    private Integer kyTemporary;

    @Column(name="version")
    @JsonIgnore
    private String version;
}
