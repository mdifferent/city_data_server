package com.bjucd.mobilesignal.models.responseBody;

import lombok.Builder;
import lombok.Data;

/**
 * Created by I015703 on 6/25/2019.
 */
@Data
@Builder
public class GridPopulationResponse {

    private String gridId;
    private Double[] coords = new Double[2];
    private Double[] populations = new Double[3];

}
