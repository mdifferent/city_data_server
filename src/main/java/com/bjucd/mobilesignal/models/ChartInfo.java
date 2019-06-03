package com.bjucd.mobilesignal.models;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;

@Entity
@Data
@Table(schema = "public", name="SY_TABLE")
@Cacheable(false)
@SequenceGenerator(name="sy_table_seq")
public class ChartInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sy_table_seq")
    @JsonIgnore
    @Column(name="ID")
    private Integer id;

    @Column(name="CITY")
    private String city;

    @Column(name="TABLE_NAME")
    private String tableName;

    @Column(name="X_INDEX")
    private String xIndex;

    @Column(name="Y_VALUE")
    private Double yValue;

    @Column(name="VERSION")
    private String version;
}
