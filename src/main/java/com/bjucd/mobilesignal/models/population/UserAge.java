package com.bjucd.mobilesignal.models.population;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="ZZ_TJ_USER_AGE")
@Cacheable(false)
@SequenceGenerator(name="user_age_seq")
public class UserAge {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_age_seq")
    @JsonIgnore
    @Column(name="id")
    private String id;

    @Column(name="city")
    private String city;

    @Column(name="district")
    private String district;

    @Column(name="age")
    private Integer age;

    @Column(name="CU_POP")
    private Integer cuPop;

    @Column(name="KY_POP")
    private Integer kyPop;

    @Column(name="VERSION")
    private String version;
}
