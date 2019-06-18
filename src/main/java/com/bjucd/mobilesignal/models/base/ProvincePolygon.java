package com.bjucd.mobilesignal.models.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by I015703 on 5/29/2019.
 */
@Entity
@Data
@Table(name="JC_PROVINCE_POLYGON")
@Cacheable(false)
public class ProvincePolygon {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="province")
    private String province;

    @Column(name="wkt", columnDefinition = "text")
    private String wkt;

    @Column(name="version")
    @JsonIgnore
    private String version;
}
