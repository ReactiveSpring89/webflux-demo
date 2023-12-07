package com.app.demo.models.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class Response {
    private Date date = new Date();
    private int outout;

    public Response(int outout) {
        this.outout = outout;
    }
}
