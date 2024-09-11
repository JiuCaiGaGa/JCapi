package com.jcgg.jcapi_test.exception;

public class JCapiException extends Exception{
    private int code;

    public JCapiException(int code, String message) {
        super(message);
        this.code = code;
    }

    public JCapiException(String message, Throwable cause) {
        super(message, cause);
    }

    public JCapiException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public JCapiException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
