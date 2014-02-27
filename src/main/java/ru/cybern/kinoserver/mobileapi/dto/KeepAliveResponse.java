package ru.cybern.kinoserver.mobileapi.dto;


/**
 * Created by virtuozzo on 14.02.14.
 */

public class KeepAliveResponse {
    public static enum ResponseType {ACCESS, REGISTER, UPDATE}
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private ResponseType responseType;

    public ResponseType getResponseType() {
        return responseType;
    }

    public void setResponseType(ResponseType responseType) {
        this.responseType = responseType;
    }
    
}
