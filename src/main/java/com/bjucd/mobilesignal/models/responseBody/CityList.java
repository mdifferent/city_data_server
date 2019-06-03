package com.bjucd.mobilesignal.models.responseBody;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class CityList {
    private String label;
    private String value;
    private List<CityList> children;

    public CityList(String label) {
        this.label = label;
        this.value = label;
    }

    public void appendChild(String value) {
        if (this.children == null)
            this.children = new LinkedList<>();
        CityList newCity = new CityList(value);
        this.children.add(newCity);
    }
}