package com.bjucd.mobilesignal.models.responseBody;

import lombok.Builder;
import lombok.Data;

/**
 * Created by I015703 on 6/11/2019.
 */
@Data
@Builder
public class MigrationResponse {

    private String sourceName;
    private Double[] sourceCoord;
    private Integer amount;
    private String targetName;
    private Double[] targetCoord;

}
