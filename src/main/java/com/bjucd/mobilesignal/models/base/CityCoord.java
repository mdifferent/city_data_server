package com.bjucd.mobilesignal.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Clob;

/**
 * Created by I015703 on 5/27/2019.
 */
@Entity
@Data
@Table(name="jc_city")
@Cacheable(false)
@NoArgsConstructor
@SequenceGenerator(name="jc_city_seq")
public class CityCoord {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="jc_city_seq")
    @JsonIgnore
    @Column(name="guid")
    private Integer guid;

    @Column(name="id")
    @CsvBindByName
    private Integer id;

    @Column(name="province")
    @CsvBindByName
    private String province;

    @Column(name="city")
    @CsvBindByName
    private String city;

    @Column(name="lng")
    @CsvBindByName
    private Double lng;

    @Column(name="lat")
    @CsvBindByName
    private Double lat;

    @Column(name="area")
    @CsvBindByName
    private Double area;

    @Column(name="version")
    @JsonIgnore
    @CsvBindByName
    private String version;
}
