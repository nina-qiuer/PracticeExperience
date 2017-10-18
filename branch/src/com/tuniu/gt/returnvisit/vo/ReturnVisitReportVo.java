/**
 * 
 */
package com.tuniu.gt.returnvisit.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangye
 *
 */
public class ReturnVisitReportVo {
    private String name; // 名字
    private ReturnVisitReportData data; // 统计数据
    private List<ReturnVisitReportVo> children = new ArrayList<ReturnVisitReportVo>(); // 子vo
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ReturnVisitReportData getData() {
        return data;
    }
    public void setData(ReturnVisitReportData data) {
        this.data = data;
    }
    public List<ReturnVisitReportVo> getChildren() {
        return children;
    }
    public void setChildren(List<ReturnVisitReportVo> children) {
        this.children = children;
    }
    
}
