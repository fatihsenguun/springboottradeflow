package com.fatihsengun.controller;

import com.fatihsengun.entity.RootResponseEntity;
import com.fatihsengun.handler.ApiError;

public class RestRootResponseController {

    public <T> RootResponseEntity<T> ok(T payload){
        return RootResponseEntity.ok(payload);
    }
    public <T> RootResponseEntity<T> error(ApiError<?> error){
        return RootResponseEntity.error(error);
    }
}
