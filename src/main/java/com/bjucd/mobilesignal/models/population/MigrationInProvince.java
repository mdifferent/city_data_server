package com.bjucd.mobilesignal.models.population;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="ZZ_TJ_MIGRATION_PROVINCE")
@Cacheable(false)
@SequenceGenerator(name="migration_province_seq")
public class MigrationInProvince {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="migration_province_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="province")
    private String province;

    @Column(name="city")
    private String city;

    @Column(name="from_city")
    private String fromCity;

    @Column(name="CU_FLOAT_POP")
    private Integer cuFloatPop;

    @Column(name="KY_FLOAT_POP")
    private Integer kyFloatPop;

    @Column(name="version")
    @JsonIgnore
    private String version;

}
