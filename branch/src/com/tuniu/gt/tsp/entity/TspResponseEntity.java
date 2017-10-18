/**
 * 
 */
package com.tuniu.gt.tsp.entity;

import java.util.List;

import com.tuniu.gt.toolkit.JsonUtil;

import net.sf.json.JSONObject;

/**
 * @author Jiang Sir
 *
 */
public class TspResponseEntity {
    private boolean success;
    private String msg;
    private Integer errorCode;
    private JSONObject data;
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public Integer getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
    public JSONObject getData() {
        return data;
    }
    public void setData(JSONObject data) {
        this.data = data;
    }
    
    public static TspResponseEntity fromJSONObject(JSONObject result){
        TspResponseEntity entity = new TspResponseEntity();
        entity.setSuccess(result.getBoolean("success"));
        entity.setErrorCode(result.getInt("errorCode"));
        entity.setMsg(result.getString("msg"));
        entity.setData(result.getJSONObject("data"));
        return entity;
    }
    @Override
    public String toString() {
        return "TspResponseEntity [success=" + success + ", msg=" + msg + ", errorCode=" + errorCode + ", data=" + data + "]";
    }
}
