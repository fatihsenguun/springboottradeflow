package com.fatihsengun.entity;

import com.fatihsengun.handler.ApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RootResponseEntity<T> {

    private boolean result;

    private ApiError<?> errorMessage;

    private T data;

    public static <T> RootResponseEntity<T> ok(T data){
        RootResponseEntity<T> rootResponseEntity = new RootResponseEntity<>();
        rootResponseEntity.setResult(true);
        rootResponseEntity.setErrorMessage(null);
        rootResponseEntity.setData(data);
        return rootResponseEntity;
    }
    public static <T> RootResponseEntity<T> error(ApiError<?> error){
        RootResponseEntity<T> rootResponseEntity = new RootResponseEntity<>();
        rootResponseEntity.setResult(false);
        rootResponseEntity.setErrorMessage(error);
        rootResponseEntity.setData(null);
        return rootResponseEntity;
    }






}
