/*
 *********************************************
 * Copyright (C), 2006-2014, www.tuniu.com
 * FileName: QcworkAction.java
 * Author:   yangjian3
 * Date:     2014-4-11 下午04:46:06
 *********************************************
 */
package com.tuniu.gt.complaint.action;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;

import com.tuniu.gt.complaint.QcConstans;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.service.IQcworkService;
import com.tuniu.gt.toolkit.DataExportExcel;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

/**
 * 〈质检人员工作量综合统计Action〉<br>
 * 
 * @author yangjian3
 */
@Service("complaint_action-qcwork")
@Scope("prototype")
public class QcworkAction extends FrmBaseAction<IQcworkService, QcEntity> {

    private static final long serialVersionUID = 352871178813220531L;

    private static Logger logger = Logger.getLogger(QcworkAction.class);

    // 一级责任归属
    private Map<String, Object> officerDept = new LinkedHashMap<String, Object>();

    // 质检工作统计返回页面
    private static final String QC_WORK_LIST = "qc_work_list";

    // 搜索条件回传页面
    private Map<String, String> search = new HashMap<String, String>();

    private List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

    @Autowired
    @Qualifier("complaint_service_impl-qcwork")
    public void setService(IQcworkService service) {
        this.service = service;
    }

    @Autowired
    @Qualifier("uc_service_user_impl-department")
    private IDepartmentService departmentService;

    /**
     * 查询质检人员工作量报表<br>
     * 入参：起始时间、结束时间
     */
    public String execute() {
        this.search = Common.getSqlMap(getRequestParam(), "search.");
        String startDate = search.get("startTime");
        String endDate = search.get("endTime");
        if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
        	logger.info("查询客服工作量报表，开始时间" + startDate + "结束时间" + endDate);
            resultList = service.queryQcwork(startDate, endDate);
            MemcachesUtil.set("qcwork" + user.getId(), resultList, 60 * 60 * 1000l);
        }
        return QC_WORK_LIST;
    }

    /**
     * 查询质检人员工作量报表<br>
     * 入参：起始时间、结束时间
     */
    @SuppressWarnings("unchecked")
	public String export() {
        this.search = Common.getSqlMap(getRequestParam(), "search.");
        String startDate = search.get("startTime");
        String endDate = search.get("endTime");
        if (StringUtil.isNotEmpty(startDate) && StringUtil.isNotEmpty(endDate)) {
        	logger.info("查询客服工作量报表，开始时间" + startDate + "结束时间" + endDate);
            resultList = (List<Map<String, Object>>) MemcachesUtil.getObj("qcwork" + user.getId());
            exportData(startDate, endDate);
        }
        return QC_WORK_LIST;
    }

    private void exportData(String startTime, String endTime) {
        HttpServletResponse response = ServletActionContext.getResponse();
        List<List<Object>> data = new ArrayList<List<Object>>();
        List<Object> list_column = new ArrayList<Object>();
        list_column.add("人员");
        list_column.add("总完成质检订单数");
        list_column.add("应完成质检订单数");
        list_column.add("及时完成订单数");
        list_column.add("执行问题");
        list_column.add("供应商问题");
        list_column.add("系统、流程问题");
        list_column.add("上班天数");
        list_column.add("日均工作量（单）");
        list_column.add("及时率");
        list_column.add("完成率");
        list_column.add("日均发现问题点数");
        data.add(list_column);// 设置标题
        // 获取执行岗位和责任归属中文显示
        if (this.resultList.size() > 0) {
            for (Map<String, Object> map : this.resultList) {
                List<Object> list_data = new ArrayList<Object>();
                list_data.add(map.get("pername"));
                list_data.add(map.get("alldoaccount"));
                list_data.add(map.get("allaccount"));
                list_data.add(map.get("timeaccount"));
                list_data.add(map.get("qesa"));
                list_data.add(map.get("qesb"));
                list_data.add(map.get("qesc"));
                list_data.add(map.get("workday"));
                list_data.add(map.get("everydaycount"));
                list_data.add(map.get("timely"));
                list_data.add(map.get("finishly"));
                list_data.add(map.get("everydayqccount"));
                data.add(list_data);// 添加数据
            }
        }
        // 导出数据
        String filename = "qcwork" + (new java.text.SimpleDateFormat("yyyyMMddhhmmss")).format(new Date()) + ".xls";
        DataExportExcel dee = new DataExportExcel(data, filename, startTime + "-" + endTime + "质检人员工作量统计", response);
        dee.createExcelFromList();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public String changeWorkDay() {
        List<Map> result = new ArrayList<Map>();
        String personId = request.getParameter("personId");
        String newdays = request.getParameter("newdays");
        try {
            DecimalFormat df = new DecimalFormat();
            String style = "0.00%";// 百分数格式化
            df.applyPattern(style);
            DecimalFormat df1 = new DecimalFormat();
            String style1 = "0.0";// 一位小数
            df1.applyPattern(style1);
            this.resultList = (List<Map<String, Object>>) MemcachesUtil.getObj("qcwork" + user.getId());
            Long workDay = 0L;
            for (Map map : resultList) {
                if (personId.equals(map.get("person").toString())) {
                    map.put("workday", Long.valueOf(newdays));
                    // 日均工作量
                    map.put("everydaycount",
                            df1.format(((Long) map.get("alldoaccount") + 0.0) / (Long) map.get("workday")));
                    // 日均问题数
                    map.put("everydayqccount",
                            df1.format(((Long) map.get("qesa") + (Long) map.get("qesb") + (Long) map.get("qesc") + 0.0)
                                    / (Long) map.get("workday")));
                    result.add(map);
                }
                if (!"0".equals(map.get("person").toString())) {
                    workDay += (Long) map.get("workday");
                }
            }
            for (Map map1 : resultList) {
                if ("0".equals(map1.get("person").toString())) {
                    map1.put("workday", workDay);
                    map1.put("everydaycount",
                            df1.format(((Long) map1.get("alldoaccount") + 0.0) / (Long) map1.get("workday")));
                    map1.put("everydayqccount", df1.format(((Long) map1.get("qesa") + (Long) map1.get("qesb")
                            + (Long) map1.get("qesc") + 0.0)
                            / (Long) map1.get("workday")));
                    result.add(map1);
                }
            }
            // 推新缓存
            MemcachesUtil.set("qcwork" + user.getId(), this.resultList, 60 * 60 * 1000l);
            HttpServletResponse response = ServletActionContext.getResponse();
            response.setContentType("text/plain; charset=utf-8");

            PrintWriter pw;
            pw = response.getWriter();
            pw.write(JSONArray.fromObject(result).toString());
            pw.close();
        } catch (Exception e) {
            logger.error("Exception:{}", e);
        }
        return null;
    }

    public String showDetail() {
        Map<String, String> search = Common.getSqlMap(getRequestParam(), "search.");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("startTime", search.get("startTime").toString() + " 00:00:00");
        params.put("endTime", search.get("endTime").toString() + " 23:59:59");
        params.put("qcPerson", search.get("qcPerson"));
        if (StringUtil.isNotEmpty(search.get("questions"))) {
            params.put("questions", search.get("questions"));
        }
        if (StringUtil.isNotEmpty(search.get("timeoutflag"))) {
            params.put("timeoutflag", search.get("timeoutflag"));
        }
        
        List<Map<String, Object>> result = service.queryQcDetail(params);
        
        if (Integer.valueOf(search.get("status").toString()) == 0) {
        	params.put("endDateStr", search.get("endTime").toString());
            List<Map<String, Object>> resultUndone = service.queryQcDetailUndone(params);
            if (resultUndone != null && resultUndone.size() > 0) { // 超时未完成
                result.addAll(resultUndone);
            }
        }
        
        exportDetailData(result, search);
        return QC_WORK_LIST;
    }

    private void exportDetailData(List<Map<String, Object>> result, Map<String, String> sear) {
        HttpServletResponse response = ServletActionContext.getResponse();
        List<List<Object>> data = new ArrayList<List<Object>>();
        List<Object> list_column = new ArrayList<Object>();
        list_column.add("分配时间");
        list_column.add("投诉完成时间");
        list_column.add("质检完成时间");
        list_column.add("归来时间");
        list_column.add("订单号");
        list_column.add("线路");
        list_column.add("投诉级别");
        list_column.add("产品经理");
        list_column.add("客服经理");
        list_column.add("产品专员");
        list_column.add("投诉说明");
        list_column.add("质检结论");
        list_column.add("责任归属");
        list_column.add("执行岗位");
        list_column.add("责任人");
        list_column.add("改进人");
        list_column.add("问题大类型");
        list_column.add("问题小类型");
        list_column.add("质检人");
        list_column.add("发起人");
        list_column.add("投诉发起时间");
        data.add(list_column);// 设置标题
        if (result.size() > 0) {
            officerDept = responsibilityAll();// 获取执行岗位和责任归属中文显示
            for (Map<String, Object> map : result) {
                List<Object> list_data = new ArrayList<Object>();
                list_data.add(map.get("distribution_date"));
                list_data.add(map.get("ct_finish_date"));
                list_data.add(map.get("finish_date"));
                list_data.add(map.get("back_time"));
                list_data.add(map.get("order_id"));
                list_data.add(map.get("route"));
                list_data.add(map.get("level"));
                list_data.add(map.get("product_leader"));
                list_data.add(map.get("customer_leader"));
                list_data.add(map.get("producter"));
                list_data.add(map.get("descript"));
                list_data.add(map.get("conclusion"));
                list_data.add(getresponsibilities(map));
                if (!"0".equals(map.get("position"))) {
                    list_data.add(QcConstans.positionMap.get(map.get("position")));
                } else {
                    list_data.add("无");
                }
                list_data.add(map.get("responsible_person"));
                list_data.add(map.get("improver"));
                list_data.add(map.get("big_class_name"));
                list_data.add(map.get("small_class_name"));
                list_data.add(map.get("qc_person_name"));
                list_data.add(map.get("owner_name"));
                list_data.add(map.get("build_date"));

                data.add(list_data);// 添加数据
            }
        }
        // 导出数据
        String filename = result.get(0).get("qc_person_name") + getFileName(sear);
        DataExportExcel dee = new DataExportExcel(data, filename, "质检人员工作量统计", response);
        dee.createExcelFromList();
    }

    private String getFileName(Map<String, String> sear) {
        StringBuilder result = new StringBuilder();
        String type = sear.get("nametype");
        result.append(search.get("startTime")).append("-").append(search.get("endTime"));
        if ("1".equals(type)) {
            result.append("-总完成质检订单明细");
        } else if ("2".equals(type)) {
            result.append("-应完成质检订单明细");
        } else if ("3".equals(type)) {
            result.append("-及时完成订单明细");
        } else if ("4".equals(type)) {
            result.append("-执行问题订单明细");
        } else if ("5".equals(type)) {
            result.append("-供应商问题订单明细");
        } else if ("6".equals(type)) {
            result.append("-系统、流程问题订单明细");
        } else if ("7".equals(type)) {
            result.append("-超时完成明细");
        }
        result.append(".xls");
        return result.toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private String getresponsibilities(Map map) {
        String responsibility = "";// 一级责任归属
        String respSecond = "";// 二级责任归属
        String respThird = "";// 三级责任归属
        if (map.get("responsibility") != null && !"0".equals(map.get("responsibility").toString())) {
            if (!getOldRecord(map.get("responsibility").toString()).equals("")) {// 判断是否是老数据
                responsibility += getOldRecord(map.get("responsibility").toString());
            } else {
                responsibility += (String) officerDept.get(map.get("responsibility").toString());
                if (map.get("resp_second") != null && !"0".equals(map.get("resp_second").toString())) {
                    map.put("resp_second", getDepartmentNameById(Integer.valueOf(map.get("resp_second").toString())));
                    respSecond = map.get("resp_second").toString();
                    responsibility += "/" + respSecond;
                    if (map.get("resp_third") != null && !"0".equals(map.get("resp_third").toString())) {
                        map.put("resp_third", getDepartmentNameById(Integer.valueOf(map.get("resp_third").toString())));
                        respThird = map.get("resp_third").toString();
                        responsibility += "/" + respThird;
                    }
                }
            }
            if (!responsibility.equals("")) {
                responsibility += " ";
            } else {
                responsibility += "无 ";
            }
        } else {
            responsibility += "无 ";
        }
        return responsibility;
    }

    private String getOldRecord(String mapId) {
        String returnStr = "";
        if (Integer.valueOf(mapId) > 0 && Integer.valueOf(mapId) < 14) {
            returnStr = QcConstans.officerDeptOld.get(mapId);
        }
        return returnStr;
    }

    private String getDepartmentNameById(int depId) {
        DepartmentEntity department = departmentService.getDepartmentById(depId);
        if (department != null) {
            return department.getDepName();
        } else {
            return "";
        }
    }

    /**
     * 获取一级责任归属
     * 
     * @return
     */
    private Map<String, Object> responsibilityAll() {
        Map<String, Object> responsibility = new LinkedHashMap<String, Object>();
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("fatherId", 1);
        // 获取一级部门
        @SuppressWarnings("unchecked")
        List<DepartmentEntity> departments = (List<DepartmentEntity>) departmentService.fetchList(paramMap);
        for (DepartmentEntity department : departments) {
            if (department.getFatherId() == 1 && !department.getDepName().equals("其它人员")) {
                responsibility.put(String.valueOf(department.getId()), department.getDepName());
            }
        }
        responsibility.put("1", "不可抗力");
        responsibility.put("2", "系统/流程问题");
        responsibility.put("14", "其他");
        return responsibility;
    }

    public Map<String, String> getSearch() {
        return search;
    }

    public void setSearch(Map<String, String> search) {
        this.search = search;
    }

    public List<Map<String, Object>> getResultList() {
        return resultList;
    }

    public void setResultList(List<Map<String, Object>> resultList) {
        this.resultList = resultList;
    }

}
