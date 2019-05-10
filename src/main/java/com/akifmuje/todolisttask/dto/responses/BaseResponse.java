package com.akifmuje.todolisttask.dto.responses;

public class BaseResponse {

    public boolean result;
    public String message;

    public BaseResponse(){}

    public BaseResponse(String message, boolean result){
        this.result = result;
        this.message = message;
    }
}
