package com.tuniu.qms.qc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.qc.dto.QcTypeDto;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.QcTypeService;

@Controller
@RequestMapping("/qc/qcType")
public class QcTypeController {
	
	@Autowired
	private QcTypeService service;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		List<QcType> qcTypeList = service.list(new QcTypeDto());
		request.setAttribute("qcTypeList", qcTypeList);
		return "qc/qcTypeList";
	}
	
	@RequestMapping("/{id}/toAddChild")
	public String toAddChild(@PathVariable Integer id, HttpServletRequest request) {
		QcType qcType = new QcType();
		qcType.setPid(id);
		request.setAttribute("qcType", qcType);
		return "qc/qcTypeForm";
	}
	
	@RequestMapping("/addChild")
	@ResponseBody
	public String addChild(QcType qcType, HttpServletRequest request) {
		service.add(qcType);
		return "Success";
	}
	
	@RequestMapping("/{id}/delete")
	@ResponseBody
	public String delete(@PathVariable("id") Integer id) {
		service.delete(id);
		return "Success";
	}
	
	@RequestMapping("/{id}/toUpdate")
	public String toUpdate(@PathVariable("id") Integer id, HttpServletRequest request) {
		QcType qcType = service.get(id);
		request.setAttribute("qcType", qcType);
		return "qc/qcTypeForm";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(QcType qcType, HttpServletRequest request) {
		service.update(qcType);
		return "Success";
	}
	
	/**
	 * 获取缓存质检类型
	 */
	@RequestMapping("/getQtNames")
	@ResponseBody
	public List<String> getQtNames() {
		
		List<QcType> qpList = service.getQcTypeDataCache();
		
		if (null != qpList  && qpList.size()>0) {
			for(int i= 0;i<qpList.size();i++){
				
				int count = service.getQcTypeCount(qpList.get(i).getId());
				if(count>0){
					
					qpList.remove(i);
					i--;
				}
			}
		}
		return CommonUtil.getQtNames(qpList);
	}
	
	/**
	 * 获取缓存质检类型
	 */
	@RequestMapping("/getOperQtNames")
	@ResponseBody
	public List<String> getOperQtNames() {
		
		List<QcType> qpList = service.getQcTypeDataCache();
		if (null != qpList  && qpList.size()>0) {
			for(int i= 0;i<qpList.size();i++){
				
				if(qpList.get(i).getFullName().contains("内外部客户反馈、投诉")){
					
					qpList.remove(i);
					i--;
					continue;
				}
				int count = service.getQcTypeCount(qpList.get(i).getId());
				if(count>0){
					
					qpList.remove(i);
					i--;
				}
			}
		}
		return CommonUtil.getQtNames(qpList);
	}
	
	@RequestMapping("/getTypeFullNameList")
	@ResponseBody
	public List<String> getTypeFullNameList(){
		List<QcType> qpList = service.getTypeFullNameList();
		for(int i= 0; i<qpList.size(); i++){
			if(qpList.get(i).getFullName().contains("ROOT")){
				qpList.remove(i);
				break;
			}
		}
		
		return CommonUtil.getQtNames(qpList);
	}
	
}
