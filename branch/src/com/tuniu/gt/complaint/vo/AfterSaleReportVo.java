/**
 * 
 */
package com.tuniu.gt.complaint.vo;

import java.util.Map;

/**
 * @author jiangye
 *
 */
public class AfterSaleReportVo {
    private String dealName;
    
    private String statisticsField; // 统计字段
    
    private Integer count; //统计数量

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getStatisticsField() {
        return statisticsField;
    }

    public void setStatisticsField(String statisticsField) {
        this.statisticsField = statisticsField;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
    /*public Map<String,Map<String,Integer>>  toMap(){
        
    }*/
}
