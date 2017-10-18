package com.tuniu.gt.complaint.tsp;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.tuniu.operation.platform.base.json.JsonFormatter;

/**
 * restful结果类
 * 
 * @author yufushan
 * @param <T>
 */
public class RestfulResult<T> {

    private Boolean success = true;

    private String msg = "operation successful.";

    private Integer errorCode = 110000;

    private T data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * @deprecated Use {@link #successStrNew()}
     * @param t
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public String successStr(T t) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorCode", errorCode);
        map.put("data", data);
        map.put("message", "");
        map.put("success", true);
        try
        {
            return JsonFormatter.toJsonString(map);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String successStrNew() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorCode", 110000);
        map.put("data", data);
        map.put("msg", "operation successful.");
        map.put("success", true);
        try
        {
            return JsonFormatter.toJsonString(map);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * @deprecated Use {@link #responseStrNew()}
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public String responseStr() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorCode", errorCode);
        map.put("data", data);
        map.put("message", msg);
        map.put("success", success);
        try
        {
            return JsonFormatter.toJsonString(map);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String responseStrNew() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorCode", errorCode);
        map.put("data", data);
        map.put("msg", msg);
        map.put("success", success);
        try
        {
            return JsonFormatter.toJsonString(map);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    /**
     * @deprecated Use {@link #errorStrNew()}
     * @return
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    public String errorStr() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorCode", errorCode);
        map.put("data", data);
        map.put("message", msg);
        map.put("success", false);
        try
        {
            return JsonFormatter.toJsonString(map);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public String errorStrNew() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("errorCode", errorCode);
        map.put("data", data);
        map.put("msg", msg);
        map.put("success", false);
        try
        {
            return JsonFormatter.toJsonString(map);
        }
        catch(Exception e)
        {
            return null;
        }
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    // @modifier baomingfeng
    public static <T> RestfulResult<T> create() {
        return new RestfulResult<T>();
    }

    // @modifier baomingfeng
    public RestfulResult<T> code(Integer errorCode) {
        this.setErrorCode(errorCode);
        return this;
    }

    // @modifier baomingfeng
    public RestfulResult<T> status(boolean status) {
        this.setSuccess(status);
        return this;
    }

    // @modifier baomingfeng
    public RestfulResult<T> ok() {
        this.setSuccess(true);
        return this;
    }

    // @modifier baomingfeng
    public RestfulResult<T> ok(String message) {
        this.setSuccess(true);
        this.setMsg(message);
        return this;
    }

    // @modifier baomingfeng
    public RestfulResult<T> fail() {
        this.setSuccess(false);
        return this;
    }

    // @modifier baomingfeng
    public RestfulResult<T> fail(String message) {
        this.setSuccess(false);
        this.setMsg(message);
        return this;
    }

    // @modifier baomingfeng
    public RestfulResult<T> fail(int errorCode, String message) {
        this.setErrorCode(errorCode);
        this.setSuccess(false);
        this.setMsg(message);
        return this;
    }

    // @modifier baomingfeng
    public RestfulResult<T> message(String message) {
        this.setMsg(message);
        return this;
    }

    // @modifier baomingfeng
    public RestfulResult<T> data(T data) {
        this.setData(data);
        return this;
    }
}
