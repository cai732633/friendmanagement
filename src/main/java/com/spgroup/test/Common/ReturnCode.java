package com.spgroup.test.Common;




import org.springframework.http.HttpStatus;


public enum ReturnCode {
    /**
     * Request success
     */
    SUCCESS(200, "OK", HttpStatus.OK),


    CREATE_FRIEND_ERROR(5001, "Create friend error, internal process error!", HttpStatus.INTERNAL_SERVER_ERROR);



    private Integer rtn;
    private String msgTemplate;

    private HttpStatus httpStatus;

    ReturnCode(Integer rtn, String msgTemplate, HttpStatus httpStatus) {
        this.rtn = rtn;
        this.msgTemplate = msgTemplate;
        this.httpStatus = httpStatus;
    }

    public Integer getRtn() {
        return rtn;
    }

    public String getMsgTemplate() {
        return msgTemplate;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
