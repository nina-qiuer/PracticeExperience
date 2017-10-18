package com.tuniu.gt.complaint;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 质检常量类
 * 
 * @author yangjian3
 */
public final class QcConstans {

    // 执行岗
    public static final Map<String, Object> positionMap = new LinkedHashMap<String, Object>();
    static {
        positionMap.put("1", "供应商");
        positionMap.put("2", "售前客服");
        positionMap.put("3", "客服经理");
        positionMap.put("4", "产品专员");
        positionMap.put("5", "产品经理");
        positionMap.put("6", "售后客服");
        positionMap.put("7", "出游中客服");
        positionMap.put("8", "售后经理");
        positionMap.put("9", "签证专员");
        positionMap.put("10", "签证经理");
        positionMap.put("11", "预订员");
        positionMap.put("12", "资金专员");
        positionMap.put("13", "结算专员");
        positionMap.put("14", "门市前台");
        positionMap.put("15", "机票专员");
        positionMap.put("16", "计调专员");
        positionMap.put("17", "酒店门票预定专员");
        positionMap.put("18", "采购专家");
        positionMap.put("19", "确认专员");
        positionMap.put("20", "直采导游");
        positionMap.put("21", "项目负责人");
        positionMap.put("22", "软件工程师");
        positionMap.put("23", "研发主管");
    }

    // 责任归属（老）
    public static final Map<String, String> officerDeptOld = new LinkedHashMap<String, String>();
    static {
        officerDeptOld.put("1", "不可抗力");
        officerDeptOld.put("2", "系统/流程问题");
        officerDeptOld.put("3", "客服中心");
        officerDeptOld.put("4", "运营中心");
        officerDeptOld.put("5", "营销中心");
        officerDeptOld.put("6", "财务法务中心");
        officerDeptOld.put("7", "人力资源中心");
        officerDeptOld.put("8", "华东事业部");
        officerDeptOld.put("9", "华北事业部");
        officerDeptOld.put("10", "南方事业部");
        officerDeptOld.put("11", "团队事业部");
        officerDeptOld.put("12", "自助游事业部");
        officerDeptOld.put("13", "新产品事业部");
        officerDeptOld.put("14", "其他");
    }
}
