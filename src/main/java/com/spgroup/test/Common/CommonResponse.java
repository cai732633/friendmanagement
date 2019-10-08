package com.spgroup.test.Common;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
public class CommonResponse<T> {
    @JsonIgnore()
    private Integer rtn;
    private Boolean success;
    private T friends;
    private Integer count;
    private String message;

    private T recipients;



    /**
     * Construct CommonResponse with customer rtn and msg
     *
     * @param rtn
     * @param message
     */
    public CommonResponse(Integer rtn, String message) {
        this.rtn = rtn;
        this.message = message;
    }

    public CommonResponse(Boolean success) {

        this.success = success;
    }

    /**
     * Construct CommonResponse ReturnCode enumeration
     *
     * @param returnCode
     */
    public CommonResponse(ReturnCode returnCode) {
        this.rtn = returnCode.getRtn();
        this.message = returnCode.getMsgTemplate();
    }


    /**
     * Construct CommonResponse with data when request is success
     *
     * @param data
     */
    public CommonResponse(T data,Integer count) {
        this.success = true;
        this.friends = data;
        this.count=count;

    }

    public CommonResponse(T data) {
        this.success = true;
        this.recipients = data;

    }


}



