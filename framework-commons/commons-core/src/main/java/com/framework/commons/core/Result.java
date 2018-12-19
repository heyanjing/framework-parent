package com.framework.commons.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.framework.commons.core.error.ErrorCode;
import com.framework.commons.core.error.IError;
import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result {

    /**
     * code 0 - 正确； 非0为错误码
     */
    private int code = 0;
    private String message = "";
    private Object data;

    public Result(IError errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }

    public Result(IError errorCode, Object data) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.data = data;
    }

    public Result(Object data) {
        this.data = data;
    }

    public Result() {
    }

    public Result(int code) {
        this(code, ErrorCode.getMsg(code), null);
    }

    public Result(int code, String message) {
        this(code, message, null);
    }


    public Result(int code, String message, Object data) {
        super();
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(int code, Object data) {
        this(code, null, data);
    }

    /**
     * 根据code返回错误编码
     *
     * @param code
     * @return
     */
    public static Result error(int code) {
        return new Result(code);
    }

    public static Result error(String message) {
        if (message.endsWith("failed and fallback failed.")) {
            message = "系统繁忙,请稍后重试!";
        }
        Result result = new Result(500, message);
        return result;
    }

    public static Result error(Throwable t) {
        String message = t.getMessage();
        int index = message.lastIndexOf(":");
        if (index > 0) {
            message = message.substring(index + 1);
        }
        return new Result(500, message);
    }

    public static Result badRequest() {
        Result result = new Result(400, "");
        return result;
    }

    public static Result error(int code, String message) {
        return new Result(code, message);
    }

    public static Result success(Object data) {
        Result result = success();
        result.setData(data);
        return result;
    }

    public static Result success() {
        return new Result(ErrorCode.SUCCESS);
    }

    public static Result error(Map<String, Object> errors) {
        Result result = new Result(ErrorCode.REQ_PARAMS_ERROR);
        result.setData(errors);
        return result;
    }

    public static Result error() {
        return new Result(ErrorCode.ERROR);
    }

    public static Result errorNoAuth() {
        return new Result(ErrorCode.NO_AUTH);
    }

    public static Result errorNoAllAuth() {
        return new Result(ErrorCode.NO_ALL_AUTH);
    }

    public static Result errorNoAllAuth(String message) {
        Result result = errorNoAllAuth();
        result.setMessage(message);
        return result;
    }

    public static Result errorNoAuth(String message) {
        Result result = errorNoAuth();
        result.setMessage(message);
        return result;
    }

    public static Result validate(BindingResult result) {
        if (result.hasErrors()) {
            return validate(result.getAllErrors());
        } else {
            return Result.success();
        }
    }

    private static Result validate(List<ObjectError> objectErrorsList) {
        Map<String, Object> errors = null;
        if (objectErrorsList != null && objectErrorsList.size() > 0) {
            errors = new HashMap(1);
            for (ObjectError error : objectErrorsList) {
                String key = (error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName());
                errors.put(key, error.getDefaultMessage());
            }
            return error(errors);
        }
        return success();
    }

    public boolean isSuccess() {
        return code == 0;
    }
}
