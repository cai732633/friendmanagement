package com.spgroup.test.Common;


import com.spgroup.test.Common.FriendsException;

public class InvalidRequestParamsException extends FriendsException {

    public InvalidRequestParamsException(ReturnCode returnCode) {
        super(returnCode, returnCode.getMsgTemplate());
    }

    public InvalidRequestParamsException(ReturnCode returnCode, String message) {
        super(returnCode, message);
    }

    public InvalidRequestParamsException(ReturnCode returnCode, String message, Throwable cause) {
        super(returnCode, message, cause);
    }

}

