package com.bjucd.mobilesignal.models.base;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.sql.Clob;

@Entity
@Data
@Table(schema = "public", name="JC_PROVINCE")
@Cacheable(false)
public class ProvinceCoord {

    @Id
    @Column(name="id")
    private Integer id;

    @Column(name="province")
    private String province;

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