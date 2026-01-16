package com.fatihsengun.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.aspectj.bridge.Message;

@Getter
@NoArgsConstructor
public enum MessageType {

    NO_RECORD_EXIST("1001", "No Record Exist!"),
    GENERAL_EXCEPTION("9999", "A General Error Occured");

    private String code;

    private String message;

    private MessageType(String code,String message){
        this.code=code;

        this.message=message;

    }


}
