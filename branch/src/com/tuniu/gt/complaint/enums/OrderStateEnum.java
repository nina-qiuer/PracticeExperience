/**
 * 
 */
package com.tuniu.gt.complaint.enums;

/**
 * @author Jiang Sir
 *
 */
public enum OrderStateEnum {
    BEFORE_TRAVEL("出游前"),
    IN_TRAVEL("出游中"),
    AFTER_TRAVEL("出游后");
    
    private String value;
    
    OrderStateEnum(String value){
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
}
