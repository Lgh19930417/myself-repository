package com.lxw.framework.exception;

import com.lxw.framework.model.response.ResultCode;


public class CustomException extends RuntimeException{
    private ResultCode resultCode;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public CustomException(ResultCode resultCode) {
        this.resultCode = resultCode;
    }

    public CustomException(String message, ResultCode resultCode) {
        super(message);
        this.resultCode = resultCode;
    }

    public CustomException(String message, Throwable cause, ResultCode resultCode) {
        super(message, cause);
        this.resultCode = resultCode;
    }

    public CustomException(Throwable cause, ResultCode resultCode) {
        super(cause);
        this.resultCode = resultCode;
    }

    public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, ResultCode resultCode) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.resultCode = resultCode;
    }
}
