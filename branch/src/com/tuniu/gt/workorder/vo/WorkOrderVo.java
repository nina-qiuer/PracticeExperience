/**
 * 
 */
package com.tuniu.gt.workorder.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tuniu.gt.workorder.entity.WorkOrderConfig;

/**
 * @author jiangye
 *
 */
public class WorkOrderVo {
	private Integer id; // 工单号
	private String addTimeBgn; // 添加时间开始
	private String addTimeEnd; // 添加时间结束
	private String department; // 项目组
	private String businessClass; // 业务分类
    private Integer state; // 工单状态
	private Integer menuId; // 菜单id
	private String dealPerson; // 处理人
	private Integer configId;// 配置id
	private List<WorkOrderConfig> responsibleConfig; //负责的配置列表
	private String assignerName; // 项目分配人
	private String phone; // 联系电话
	private Integer sonConfigId;
	private String addPersonName;
	
	public Map toMap(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", this.id);
		paramMap.put("addTimeBgn", this.addTimeBgn);
		paramMap.put("addTimeEnd", this.addTimeEnd);
		paramMap.put("state", this.state);
		paramMap.put("dealPerson", this.dealPerson);
		paramMap.put("configId", this.configId);
		paramMap.put("responsibleConfig", this.responsibleConfig);
		paramMap.put("assignerName", this.assignerName);
		paramMap.put("phone", this.phone);
		paramMap.put("businessClass", this.businessClass);
		paramMap.put("sonConfigId", this.sonConfigId);
		paramMap.put("addPersonName", this.addPersonName);
		return paramMap;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAddTimeBgn() {
		return addTimeBgn;
	}

	public void setAddTimeBgn(String addTimeBgn) {
		this.addTimeBgn = addTimeBgn;
	}

	public String getAddTimeEnd() {
		return addTimeEnd;
	}

	public void setAddTimeEnd(String addTimeEnd) {
		this.addTimeEnd = addTimeEnd;
	}

	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}

	public String getDealPerson() {
		return dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}
	
    public Integer getConfigId() {
        return configId;
    }

    public void setConfigId(Integer configId) {
        this.configId = configId;
    }

    public List<WorkOrderConfig> getResponsibleConfig() {
        return responsibleConfig;
    }

    public void setResponsibleConfig(List<WorkOrderConfig> responsibleConfig) {
        this.responsibleConfig = responsibleConfig;
    }

    public String getAssignerName() {
        return assignerName;
    }

    public void setAssignerName(String assignerName) {
        this.assignerName = assignerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinessClass() {
        return businessClass;
    }

    public void setBusinessClass(String businessClass) {
        this.businessClass = businessClass;
    }

	public Integer getSonConfigId() {
		return sonConfigId;
	}

	public void setSonConfigId(Integer sonConfigId) {
		this.sonConfigId = sonConfigId;
	}

	public String getAddPersonName() {
		return addPersonName;
	}

	public void setAddPersonName(String addPersonName) {
		this.addPersonName = addPersonName;
	}
}
