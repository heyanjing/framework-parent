package com.framework.commons.core.error;

/**
 * @Description: 错误返回码接口
 */
public interface IError {

    /**
     * 返回码
     */
    int getCode();

    /**
     * 异常信息, 源自{@link Throwable#getMessage()}
     */
    String getMessage();

    void setMessage(String msg);
}
