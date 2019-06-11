package com.bjucd.mobilesignal.models.responseBody;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by I015703 on 6/6/2019.
 */
@Data
@AllArgsConstructor
@Builder
public class LinesData {

    private String fromName;
    private String toName;
    private Double[][] coords;
    private Double value;
}
