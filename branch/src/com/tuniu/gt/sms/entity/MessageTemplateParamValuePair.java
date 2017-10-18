/**
 * 
 */
package com.tuniu.gt.sms.entity;

/**
 * @author jiangye
 */
public class MessageTemplateParamValuePair {
    /** paramKey */
    private String paramKey;

    /** paramValue */
    private Object paramValue;
    
    public MessageTemplateParamValuePair(String paramKey, Object paramValue) {
        this.paramKey = paramKey;
        this.paramValue = paramValue;
    }

    public String getParamKey() {
        return paramKey;
    }

    public void setParamKey(String paramKey) {
        this.paramKey = paramKey;
    }

    public Object getParamValue() {
        return paramValue;
    }

    public void setParamValue(Object paramValue) {
        this.paramValue = paramValue;
    }
    
}
