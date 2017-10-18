/*
 *********************************************
 * Copyright (C), 2006-2014, www.tuniu.com
 * FileName: IQcworkServiceImpl.java
 * Author:   yangjian3
 * Date:     2014-4-11 下午05:36:08
 *********************************************
 */
package com.tuniu.gt.complaint.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.dao.impl.QcworkDao;
import com.tuniu.gt.complaint.service.IQcworkService;

/**
 * 〈质检人员工作量综合统计Service实现〉<br>
 * 
 * @author yangjian3
 */
@Service("complaint_service_impl-qcwork")
public class QcworkServiceImpl implements IQcworkService {

    @Autowired
    @Qualifier("complaint_dao_impl-qcwork")
    private QcworkDao dao;

    public List<Map<String, Object>> queryQcwork(String startDate, String endDate) {
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("start", startDate + " 00:00:00");
        condition.put("end", endDate + " 23:59:59");
        condition.put("endDate", endDate);
        List<Map<String, Object>> qcResult = dao.queryQcworkCount(condition);
        List<Map<String, Object>> dayResult = dao.queryQcworkDayCount(condition);
        List<Map<String, Object>> qcNotDoResult = dao.queryQcworkTimeOutNotDo(condition);
        
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00%");
        DecimalFormat df1 = new DecimalFormat();
        df1.applyPattern("0.0");
        if (null != qcResult && qcResult.size() > 0) {
            boolean flag = true;
            boolean flag1 = true;
            Map<String, Object> totle = new HashMap<String, Object>();
            Long workday = 0L;
            Long timeoutnotdocount = 0L;
            Long allaccount = 0L;
            Long timeaccount = 0L;
            Long alldoaccount = 0L;
            Long timeoutdocount = 0L;
            Long qesa = 0L;
            Long qesb = 0L;
            Long qesc = 0L;
            Long qesd = 0L;

            for (int i = 0; i < qcResult.size(); i++) {
                flag1 = true;
                flag = true;// 用于循环迅速判断提升效率
                Map<String, Object> map = new HashMap<String, Object>();
                // 包含质检人ID，质检人姓名，质检总完成单数，“执行问题”、“供应商问题”、“系统、流程问题”、“不可抗力因素”，“低满意度” 八项统计指标
                map.putAll(qcResult.get(i));
                // 根据质检人id匹配set其余指标：工作天数 、日均工作量 、日均问题数
                for (int j = 0; j < dayResult.size(); j++) {
                    if (flag && ((Integer) qcResult.get(i).get("person")).equals((Integer) dayResult.get(j).get("person"))) {
                        // 工作天数
                        map.put("workday", (Long) dayResult.get(j).get("workday"));
                        // 日均工作量
                        map.put("everydaycount",
                                df1.format(((Long) qcResult.get(i).get("alldoaccount") + 0.0)
                                        / (Long) dayResult.get(j).get("workday")));
                        // 日均问题数
                        map.put("everydayqccount",
                                df1.format(((Long) qcResult.get(i).get("qesa") + (Long) qcResult.get(i).get("qesb")
                                        + (Long) qcResult.get(i).get("qesc") + (Long) qcResult.get(i).get("qesd") + 0.0)
                                        / (Long) dayResult.get(j).get("workday")));
                        flag = false;
                    }
                }

                if (flag) {
                    // 工作天数
                    map.put("workday", 0);
                    // 日均工作量
                    map.put("everydaycount", 0.0);
                    // 日均问题数
                    map.put("everydayqccount", 0.0);
                }

                // 根据质检人id匹配set指标：未完成超時質檢單
                for (int m = 0; m < qcNotDoResult.size(); m++) {
                    if (flag1 && ((Integer) qcResult.get(i).get("person")).equals((Integer) qcNotDoResult.get(m).get("person"))) {
                        // 未完成超時質檢單
                        map.put("timeoutnotdocount", (Long) qcNotDoResult.get(m).get("timeoutnotdocount"));
                        // 应完成总量
                        map.put("allaccount", (Long) qcResult.get(i).get("alldoaccount") + (Long) qcNotDoResult.get(m).get("timeoutnotdocount"));
                        flag1 = false;
                    }
                }

                if (flag1) {
                    // 未完成超時質檢單
                    map.put("timeoutnotdocount", 0);
                    // 应完成总量
                    map.put("allaccount", (Long) qcResult.get(i).get("alldoaccount"));
                }

                // 计算及时量
                map.put("timeaccount",
                        (((Long) qcResult.get(i).get("alldoaccount") - (Long) qcResult.get(i).get("timeoutdocount"))));
                // 计算及时率
                map.put("timely",
                        df.format((((Long) qcResult.get(i).get("alldoaccount") - (Long) qcResult.get(i).get(
                                "timeoutdocount")) + 0.00)
                                / ((Long) map.get("allaccount"))));
                // 计算完成率
                map.put("finishly",
                        df.format((((Long) qcResult.get(i).get("alldoaccount")) + 0.00) / (Long) map.get("allaccount")));
                result.add(map);

                workday += Long.valueOf(map.get("workday").toString());
                // everydaycount += (Long) map.get("everydaycount");
                // everydayqccount += (Long) map.get("everydayqccount");
                timeoutnotdocount += Long.valueOf(map.get("timeoutnotdocount").toString());
                allaccount += Long.valueOf(map.get("allaccount").toString());
                timeaccount += Long.valueOf(map.get("timeaccount").toString());
                alldoaccount += Long.valueOf(map.get("alldoaccount").toString());
                timeoutdocount += Long.valueOf(map.get("timeoutdocount").toString());
                qesa += Long.valueOf(map.get("qesa").toString());
                qesb += Long.valueOf(map.get("qesb").toString());
                qesc += Long.valueOf(map.get("qesc").toString());
                qesd += Long.valueOf(map.get("qesd").toString());
            }
            totle.put("person", 0);
            totle.put("pername", "合计");
            totle.put("qesa", qesa);
            totle.put("qesb", qesb);
            totle.put("qesc", qesc);
            totle.put("qesd", qesd);
            totle.put("workday", workday);
            totle.put("alldoaccount", alldoaccount);
            // totle.put("everydaycount", everydaycount);
            // totle.put("everydayqccount", everydayqccount);
            totle.put("timeoutnotdocount", timeoutnotdocount);
            totle.put("timeoutdocount", timeoutdocount);
            totle.put("allaccount", allaccount);
            totle.put("timeaccount", timeaccount);
            totle.put("everydaycount", df1.format((alldoaccount + 0.0) / workday));
            totle.put("everydayqccount", df1.format((qesa + qesb + qesc + qesd + 0.0) / workday));
            totle.put("timely", df.format((alldoaccount - timeoutdocount + 0.00) / allaccount));
            totle.put("finishly", df.format((alldoaccount + 0.00) / allaccount));
            result.add(totle);
        }
        return result;
    }

    public List<Map<String, Object>> queryQcDetail(Map<String, Object> paramMap) {
        return dao.queryQcDetail(paramMap);
    }

	@Override
	public List<Map<String, Object>> queryQcDetailUndone(Map<String, Object> paramMap) {
		return dao.queryQcDetailUndone(paramMap);
	}
	
}
