package com.bjucd.mobilesignal.models.responseBody;

import com.bjucd.mobilesignal.models.base.CityCoord;
import lombok.Data;

/**
 * Created by I015703 on 5/28/2019.
 */
@Data
public class CityCoordWithValue {

    private String name;
    private Double[] value;

    public CityCoordWithValue(CityCoord coord) {
        this.name = coord.getCity();
        this.value = new Double[3];
        this.value[0] = coord.getLng();
        this.value[1] = coord.getLat();
    }

    public void setValue(Integer value) {
        if (this.value == null)
            this.value = new Double[3];
        this.value[2] = (double)value;
    }
}
