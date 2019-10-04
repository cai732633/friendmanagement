package com.spgroup.test.Common;


import org.springframework.http.HttpStatus;


public abstract class FriendsException extends RuntimeException {
    private Integer rtn;

    private HttpStatus httpStatus;

    protected FriendsException() {
        super();
    }

    protected FriendsException(String message) {
        super(message);
    }

    protected FriendsException(Throwable cause) {
        super(cause);
    }

    protected FriendsException(String message, Throwable cause) {
        super(message, cause);
    }


    protected FriendsException(ReturnCode returnCode, String message) {
        super(message);
        this.httpStatus = returnCode.getHttpStatus();
        this.rtn = returnCode.getRtn();
    }

    protected FriendsException(ReturnCode returnCode, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = returnCode.getHttpStatus();
        this.rtn = returnCode.getRtn();
    }

    public Integer getRtn() {
        return rtn;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}

