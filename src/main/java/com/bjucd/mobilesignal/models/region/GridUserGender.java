package com.bjucd.mobilesignal.models.region;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by I015703 on 6/24/2019.
 */
@Data
@Entity
@Table(name="zz_wg_user_gender")
@Cacheable(false)
@SequenceGenerator(name="zz_wg_user_gender_seq")
public class GridUserGender {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="zz_wg_user_gender_seq")
    @JsonIgnore
    @Column(name="id")
    private Integer id;

    @Column(name="city")
    private String city;

    @Column(name="grid_id")
    private String gridId;

    @Column(name="gender")
    private Integer gender;

    @Column(name="cu_pop")
    private Double cuPop;

    @Column(name="ky_pop")
    private Double kyPop;

    @Column(name="version")
    @JsonIgnore
    private String version;

    public GridUserGender(Integer gender, Double cuPop, Double kyPop) {
        this.gender = gender;
        this.cuPop = cuPop;
        this.kyPop = kyPop;
    }

}
