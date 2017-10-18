package com.tuniu.gt.complaint.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.JSONUtil;

import com.tuniu.gt.toolkit.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author chenhaitao
 *
      "callNumber": "15530927772",
      "calledNumber": "68000",
      "callTime": "2016-02-24 01:14:26",
      "holdTimeLength": 68,
      "stopTime": "2016-02-24 01:15:34"
      "recordPath": "http://boss.tuniu.org/PHP/IVR/ivr_record.php?path\u003dcid%3D00100109281456247651%26extention%3D68000%26date%3D02%2F24%2F2016",
    },
 */
public class AgentStatusLog implements Serializable {

	/**
	 * IVR 通话记录
	 */
	private static final long serialVersionUID = 1L;
	
    private String callNumber;// 主叫号码

    private String calledNumber;// 被叫号码

    private String callTime;// 通话时间开始

    private String stopTime;// 通话时间结束

    private Integer holdTimeLength;// 通话时长
    
    private String recordPath; // 通话录音路径
	
	private String customerName; // 客服姓名
	
	
	/**
     * @param data 
     * @return
     */
    public static List<AgentStatusLog> fromObject(Object data) {
        List<AgentStatusLog> agentStatusLogList = new ArrayList<AgentStatusLog>();

        if(data instanceof JSONArray){
            agentStatusLogList = fromJSONArray((JSONArray)data);
        }
        return agentStatusLogList;
    }

    /**
     * @param data 
     * @return
     */
    private static List<AgentStatusLog> fromJSONArray(JSONArray datas) {
        List<AgentStatusLog> agentStatusLogList = new ArrayList<AgentStatusLog>();
        JSONObject data;
        for(int i = 0; i < datas.size(); i++) {
            data = datas.getJSONObject(i);
            AgentStatusLog agentStatusLog = parseFromJSONObject(data);
            agentStatusLogList.add(agentStatusLog);
        }
        return agentStatusLogList;
    }

    /**
     * @param data
     * @return
     */
    private static AgentStatusLog parseFromJSONObject(JSONObject data) {
        AgentStatusLog agentStatusLog = new AgentStatusLog();
        agentStatusLog.setCallNumber(JsonUtil.getStringValue(data, "callNumber"));
        agentStatusLog.setCalledNumber(JsonUtil.getStringValue(data, "calledNumber"));
        agentStatusLog.setCallTime(JsonUtil.getStringValue(data, "callTime"));
        agentStatusLog.setStopTime(JsonUtil.getStringValue(data, "stopTime"));
        agentStatusLog.setHoldTimeLength(JsonUtil.getIntValue(data, "holdTimeLength"));
        agentStatusLog.setRecordPath(JsonUtil.getStringValue(data, "recordPath"));
        return agentStatusLog;
    }
    
	public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getCalledNumber() {
        return calledNumber;
    }

    public void setCalledNumber(String calledNumber) {
        this.calledNumber = calledNumber;
    }

    public String getCallTime() {
        return callTime;
    }

    public void setCallTime(String callTime) {
        this.callTime = callTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public Integer getHoldTimeLength() {
        return holdTimeLength;
    }

    public void setHoldTimeLength(Integer holdTimeLength) {
        this.holdTimeLength = holdTimeLength;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

}
