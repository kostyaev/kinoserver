package ru.cybern.kinoserver.mobileapi.dto;

public class ErrorResponse implements MyResponse {

    private static final long serialVersionUID = -5661811593089858771L;

    public static final ErrorResponse LIMIT_EXCEEDED = new ErrorResponse(401, "Limit should be less than 100");
    public static final ErrorResponse INTERNAL_ERROR = new ErrorResponse(500, "Internal server error");
    public static final ErrorResponse WRONG_ARGUMENT_FORMAT = new ErrorResponse(501, "Wrong argument format");

    private Integer errorCode;
    private String message;

    public ErrorResponse() {
    }

    public ErrorResponse(Integer errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
