package com.br.cybertrending.clientApi.Controller.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class StandardError  implements Serializable {
    private Integer status;
    private String msg;
    private long timeStamp;
}