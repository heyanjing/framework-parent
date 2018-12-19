package com.framework.commons.core.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Description: 异常基类
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class BaseException extends RuntimeException {

    private ErrorCode error;
    private Object data = "";
    private Throwable cause;

    public BaseException(ErrorCode error) {
        this.error = error;
    }

    public BaseException(ErrorCode error, Throwable cause, String msg) {
        super(msg);
        this.error = error;
        this.data = msg;
        this.cause = cause;
    }

    public BaseException(ErrorCode error, String msg) {
        super(msg);
        this.error = error;
        this.data = msg;
    }

    public ErrorCode getError() {
        return error;
    }

    public Object getData() {
        return data;
    }
}
