package com.fatihsengun.handler;

import com.fatihsengun.exception.BaseException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;


@ControllerAdvice
public class GlobalExceptionHandler {


    private final HttpServletResponse httpServletResponse;

    public GlobalExceptionHandler(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }


    @ExceptionHandler(value ={IOException.class, BaseException.class, AuthenticationException.class})
    public ResponseEntity<ApiError<?>> handleBaseException(BaseException exception, WebRequest request){
        return ResponseEntity.badRequest().body(createApiError(exception.getMessage(),request,null));
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError<?>> handleValidationException(MethodArgumentNotValidException exception, WebRequest request){
        Map<String, List<String>> errorsMap = new HashMap<>();

        for (ObjectError objectError : exception.getBindingResult().getAllErrors()){
            String fieldname = ((FieldError)objectError).getField();
            if (errorsMap.containsKey(fieldname)){
                errorsMap.put(fieldname,addList(errorsMap.get(fieldname),objectError.getDefaultMessage()));
            }else {
                errorsMap.put(fieldname,addList(new ArrayList<>(),objectError.getDefaultMessage()));
            }
        }
        return ResponseEntity.badRequest().body(createApiError(errorsMap,request,null));

    }

    public List<String> addList(List<String> list, String newValue) {
        list.add(newValue);
        return list;
    }

    public String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException("hostname not found" + e);
        }
    }


    public <E> ApiError<E> createApiError(E message, WebRequest request, Integer statusCode) {
        ApiError<E> apiError = new ApiError<>();

        apiError.setStatus(statusCode);
        if (statusCode == null) {
            apiError.setStatus((HttpServletResponse.SC_BAD_REQUEST));
        }

        Exception<E> exceptionResponse = new Exception<>();

        exceptionResponse.setHostName(getHostName());
        exceptionResponse.setPath(request.getDescription(false));
        exceptionResponse.setCreateTime(new Date());
        exceptionResponse.setMessage(message);

        apiError.setException(exceptionResponse);
        return apiError;


    }
}
