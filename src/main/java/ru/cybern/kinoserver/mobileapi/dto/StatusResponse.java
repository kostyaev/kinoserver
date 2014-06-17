package ru.cybern.kinoserver.mobileapi.dto;

public class StatusResponse implements MyResponse {

    public static enum ResponseStatus {OK, FAIL}

    private ResponseStatus responseStatus;

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }


}