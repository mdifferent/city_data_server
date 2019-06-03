package com.bjucd.mobilesignal.models.population;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(schema = "public", name="ZZ_TJ_MIGRATION_CHINA")
@Cacheable(false)
@SequenceGenerator(name="migration_china_seq")
public class MigrationInChina {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="migration_china_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="province")
    private String province;

    @Column(name="city")
    private String city;

    @Column(name="from_province")
    private String fromProvince;

    @Column(name="CU_FLOAT_POP")
    private Integer cuFloatPop;

    @Column(name="KY_FLOAT_POP")
    private Integer kyFloatPop;

    @Column(name="version")
    @JsonIgnore
    private String version;
}
