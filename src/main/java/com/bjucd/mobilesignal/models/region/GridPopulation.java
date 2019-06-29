package com.bjucd.mobilesignal.models.region;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by I015703 on 6/24/2019.
 */
@Data
@Entity
@Table(name="ZZ_WG_POPULATION")
@Cacheable(false)
@SequenceGenerator(name="dynamic_population_seq")
public class GridPopulation {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="dynamic_population_seq")
    @JsonIgnore
    @Column(name="id")
    private Integer id;

    @Column(name="city")
    private String city;

    @Column(name="grid_id")
    private String gridId;

    @Column(name="cu_resident")
    private Double cuResident;

    @Column(name="cu_employment")
    private Double cuEmployment;

    @Column(name="cu_temporary")
    private Double cuTemporary;

    @Column(name="ky_resident")
    private Double kyResident;

    @Column(name="ky_employment")
    private Double kyEmployment;

    @Column(name="ky_temporary")
    private Double kyTemporary;

    @Column(name="version")
    @JsonIgnore
    private String version;

}
