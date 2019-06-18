package com.bjucd.mobilesignal.models.config;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by I015703 on 5/27/2019.
 */
@Entity
@Data
@Table(name="SYSTEM_CONFIG")
@Cacheable(false)
public class SystemConfig {

    @Id
    @Column(name="ID")
    private Integer id;

    @Column(name="ACTIVE_VERSION")
    private String activeVersion;
}
