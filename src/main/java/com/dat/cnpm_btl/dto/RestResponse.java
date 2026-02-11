package com.dat.cnpm_btl.dto;

import lombok.Data;

@Data
public class RestResponse<T> {
    private int statusCode;
    private String error;

    private Object message;
    private T data;
}
