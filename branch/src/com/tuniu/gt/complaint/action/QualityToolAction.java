package com.tuniu.gt.complaint.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.entity.QualityToolEntity;
import com.tuniu.gt.complaint.service.IQualityToolService;

import tuniu.frm.core.FrmBaseAction;


/**
* @ClassName: QualityToolAction
* @Description:质量工具action
* @author zhoupanpan 2012-5-3
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_action-quality_tool")
@Scope("prototype")
public class QualityToolAction extends FrmBaseAction<IQualityToolService, QualityToolEntity>{ 
	
	private static final long serialVersionUID = -3506876348602833106L;
	private Logger logger = Logger.getLogger(getClass());
	int type;
	int level;
	String name;
	String remark;
	int useFlag;
	
	public QualityToolAction(){
		setManageUrl("quality_tool");
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-quality_tool")
	public void setService(IQualityToolService service){
		this.service = service;
	};
	
	public int getType(){
		return type;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getLevel(){
		return level;
	}

	public void setLevel(int level){
		this.level = level;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getRemark(){
		return remark;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public int getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(int useFlag) {
		this.useFlag = useFlag;
	}

	/** 
	 * 基础配置-质量工具页面
	 */
	public String execute(){
		this.setPageTitle("基础配置-质量工具");
		List<QualityToolEntity> toolList = service.getList();
		request.setAttribute("toolList", toolList);
		return "quality_tool_list";
	}
	
	/**
	 * 添加|编辑质量工具页面
	 */
	public String add(){
		if (id != null && id > 0) {
			request.setAttribute("e", service.fetchOne(id));
		}
		return "quality_tool_edit";
	}
	/**
	 * 插入|编辑数据库保存记录
	 */
	public String doAdd(){
		//检查是否已经存在相同的质量工具
		Map<String, Object> selMap = new HashMap<String, Object>();
		selMap.put("level", level);
		selMap.put("type", type);
		selMap.put("name", name);
		int result = 1;
		List<QualityToolEntity> toolList = (List<QualityToolEntity>) service.fetchList(selMap);
		int tLength = toolList.size();
		if (tLength > 0 && (id == null || id == 0)) {
			result = 0;
		} else if (tLength > 0) {
			for (int i=0; i<tLength; i++) {
				if (toolList.get(i).getId() != id && name.equals(toolList.get(i).getName())) {
					result = 0;
					break;
				}
			}
		}
		
		if (result == 1) {
			if (id != null && id > 0) {
				entity = (QualityToolEntity) service.fetchOne(id);
			}
			entity.setLevel(level);
			entity.setType(type);
			entity.setName(name);
			entity.setRemark(remark);
			entity.setUseFlag(useFlag);
			
			if (id != null && id > 0) {
				service.update(entity);
			} else {
				service.insert(entity);
			}
		}
		
		
		try {
			 //获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图    
	        HttpServletResponse response = ServletActionContext.getResponse();    
	        //设置字符集    
	        response.setContentType("text/plain");//设置输出为文字流  
	        response.setCharacterEncoding("UTF-8");    
	        PrintWriter out;
			out = response.getWriter();
			 //直接输出响应的内容    
	        out.println(result);    
	        out.flush();    
	        out.close();    
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return this.execute();
	}

}
