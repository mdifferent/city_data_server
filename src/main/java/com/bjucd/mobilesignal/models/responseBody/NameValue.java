package com.bjucd.mobilesignal.models.responseBody;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by I015703 on 5/30/2019.
 */
@Data
@AllArgsConstructor
public class NameValue<T> {

    private String name;
    private T value;
}
