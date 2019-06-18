package com.bjucd.mobilesignal.models.population;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="ZZ_TJ_USER_GENDER")
@Cacheable(false)
@SequenceGenerator(name="user_gender_seq")
public class UserGender {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_gender_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="gender")
    private Integer gender;

    @Column(name="CU_POP")
    private Integer cuPop;

    @Column(name="KY_POP")
    private Integer kyPop;

    @Column(name="VERSION")
    private String version;
}
