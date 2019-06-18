package com.bjucd.mobilesignal.models;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Data
@Table(name="SY_INDEX")
@Cacheable(false)
@SequenceGenerator(name="sy_index_seq")
public class MainPageInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sy_index_seq")
    @JsonIgnore
    @Column(name="ID")
    private Integer id;

    @Column(name="CITY_COUNT")
    private Integer cityCount;

    @Column(name="DISTRICT_COUNT")
    private Integer districtCount;

    @Column(name="CU_USER_COUNT")
    private Integer cuUserCount;

    @Column(name="CU_OD_COUNT")
    private Integer cuODCount;

    @Column(name="INDEX_COUNT")
    private Integer indexCount;

    @Column(name="version")
    @JsonIgnore
    private String version;
}