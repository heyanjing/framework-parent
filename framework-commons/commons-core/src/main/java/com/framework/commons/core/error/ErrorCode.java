package com.framework.commons.core.error;


public enum ErrorCode implements IError {

    /**
     *
     */
    SUCCESS(0, "操作成功!"),
    REQ_ERROR(400, "请求错误!"),
    ERROR(500, "服务器繁忙,请稍后再试!"),
    SERVICE_ERROR(500, "内部服务错误!"),
    AUTH_FAIL(401, "授权失败或超时,请登录访问!"),
    NO_AUTH(403, "无授权!"),
    NO_ALL_AUTH(503, "授权不足!"),
    TIMEOUT(408, "请求超时!"),
    REQ_PARAMS_ERROR(406, "请求参数错误!"),
    TRANSACTION_INVALID(501, "交易失效!"),
    ONE_LOGIN(420, "您已经在另一台手机登录"),
    DEFAULT_ERROR(500, "内部服务错误!");
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误消息
     */
    private String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ErrorCode(Integer code) {
        this.code = code;
    }

    public static String getMsg(int code) {
        for (ErrorCode errorCode : ErrorCode.values()) {
            if (code == errorCode.getCode()) {
                return errorCode.getMessage();
            }
        }
        return null;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String msg) {
        this.message = msg;
    }

}
