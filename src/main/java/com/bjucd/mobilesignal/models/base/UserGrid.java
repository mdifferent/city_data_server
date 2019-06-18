package com.bjucd.mobilesignal.models.base;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Created by I015703 on 5/27/2019.
 */
@Entity
@Data
@Table(name = "JC_USER_ZONE")
@Cacheable(false)
@SequenceGenerator(name="jc_user_zone_seq")
public class UserGrid {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="jc_user_zone_seq")
    @Column(name="id")
    @JsonIgnore
    private Integer id;

    @Column(name="province")
    private String province;

    @Column(name="city")
    private String city;

    @Column(name="ud_zone_name")
    private String udZoneName;

    @Column(name="grid_id")
    private String gridId;

    @Column(name="lng")
    private Double lng;

    @Column(name="lat")
    private Double lat;

}
