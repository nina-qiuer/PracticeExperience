/**
 * 
 */
package com.tuniu.gt.complaint.enums;

/**
 * @author jiangye
 *
 */
public enum DealDepartEnum {
    BEFORE_TRAVLE("售前组"),
    SPECIAL_BEFORE_TRAVEL("出游前客户服务"),
    IN_TRAVLE("售后组"),
    AFTER_TRAVLE("资深组"),
    AIR_TICKIT("机票组"),
    HOTEL("酒店组"),
    DISTRIBUTE("分销组"),
    TRAFFIC("交通组"),
    CUST_DEPART("会员事业部"),
    STAR_CUST_DEPART("途致事业部"),
    PREDETERMINE_DEPART("预订中心 "),
    CUSTOMER_DEPART("客户事业部"),
    VIP_DEPART("会员顾问");
    
    private String value;
    DealDepartEnum(String value){
        this.value = value;
    }
    
    public String getValue(){
        return this.value;
    }
}
