package com.bjucd.mobilesignal.models;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Data
@Table(schema = "public", name="SY_ACCESS_CITY")
@Cacheable(false)
@SequenceGenerator(name="sy_access_city_seq")
public class AccessCityInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sy_access_city_seq")
    @JsonIgnore
    @Column(name="ID")
    private Integer id;

    @Column(name="province")
    private String province;

    @Column(name="CITY")
    private String city;

    @Column(name="DISTRICT_COUNT")
    private Integer districtCount;

    @Column(name="CU_USER_COUNT")
    private Integer cuUserCount;

    @Column(name="CU_OD_COUNT")
    private Integer cuODCount;

    @Column(name="DATA_MONTH")
    private String dataMonth;

    @Column(name="version")
    @JsonIgnore
    private String version;
}