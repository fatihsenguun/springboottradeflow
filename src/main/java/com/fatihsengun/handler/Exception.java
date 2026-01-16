package com.fatihsengun.handler;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class Exception<E> {
    private  String hostName;

    private String path;

    private Date createTime;

    private E message;



}
