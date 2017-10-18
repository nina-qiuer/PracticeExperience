package com.tuniu.qms.qc.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.util.CommonUtil;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.qc.dto.QualityProblemTypeDto;
import com.tuniu.qms.qc.model.QualityProblemType;
import com.tuniu.qms.qc.service.QualityProblemTypeService;

@Controller
@RequestMapping("/qc/qualityProblemType")
public class QualityProblemTypeController {
	
	@Autowired
	private QualityProblemTypeService service;
	
	@RequestMapping("/list")
	public String list(HttpServletRequest request) {
		List<QualityProblemType> qpTypeList = service.list(new QualityProblemTypeDto());
		for (QualityProblemType qpType : qpTypeList) {
			StringBuffer sb = new StringBuffer();
			sb.append(qpType.getName());
			if (!(0 == qpType.getCmpQcUse() && 0 == qpType.getOperQcUse() && 0 == qpType.getDevQcUse()&& 0 == qpType.getInnerQcUse())) {
				sb.append(" - ");
				if (1 == qpType.getCmpQcUse()) {
					sb.append("[C]"); // 投诉质检
				}
				if (1 == qpType.getOperQcUse()) {
					sb.append("[O]"); // 运营质检
				}
				if (1 == qpType.getDevQcUse()) {
					sb.append("[D]"); // 研发质检
				}
				if (1 == qpType.getInnerQcUse()) {
					sb.append("[I]"); // 内部质检
				}
			}
			qpType.setName(sb.toString());
		}
		request.setAttribute("qpTypeList", qpTypeList);
		return "qc/qualityProblemTypeList";
	}
	
	@RequestMapping("/{id}/toAddChild")
	public String toAddChild(@PathVariable Integer id, HttpServletRequest request) {
		QualityProblemType qpType = new QualityProblemType();
		qpType.setPid(id);
		qpType.setTouchRedFlag(Constant.NO);
		request.setAttribute("qpType", qpType);
		return "qc/qualityProblemTypeForm";
	}
	
	@RequestMapping("/addChild")
	@ResponseBody
	public String addChild(QualityProblemType qpType, HttpServletRequest request) {
		service.add(qpType);
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
		QualityProblemType qpType = service.get(id);
		request.setAttribute("qpType", qpType);
		return "qc/qualityProblemTypeForm";
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String update(QualityProblemType qpType, HttpServletRequest request) {
		if (null == qpType.getCmpQcUse()) {
			qpType.setCmpQcUse(Constant.NO);
		}
		if (null == qpType.getOperQcUse()) {
			qpType.setOperQcUse(Constant.NO);
		}
		if (null == qpType.getDevQcUse()) {
			qpType.setDevQcUse(Constant.NO);
		}
		if (null == qpType.getInnerQcUse()) {
			qpType.setInnerQcUse(Constant.NO);
		}
		service.update(qpType);
		return "Success";
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getQualityProblemType")
	public String getQualityProblemType(HttpServletRequest request) {
		List<QualityProblemType> qpTypeList = service.list(new QualityProblemTypeDto());
		for (QualityProblemType qpType : qpTypeList) {
			StringBuffer sb = new StringBuffer();
			sb.append(qpType.getName());
			if (!(0 == qpType.getCmpQcUse() && 0 == qpType.getOperQcUse() && 0 == qpType.getDevQcUse()&& 0 == qpType.getInnerQcUse())) {
				sb.append(" - ");
				if (1 == qpType.getCmpQcUse()) {
					sb.append("[C]"); // 投诉质检
				}
				if (1 == qpType.getOperQcUse()) {
					sb.append("[O]"); // 运营质检
				}
				if (1 == qpType.getDevQcUse()) {
					sb.append("[D]"); // 研发质检
				}
				if (1 == qpType.getInnerQcUse()) {
					sb.append("[I]"); //内部质检
				}
			}
			qpType.setName(sb.toString());
		}
		request.setAttribute("qpTypeList", qpTypeList);
		return "qc/qc_qualityProblem_type";
	}
	
	
	/**
	 * 获取缓存质量问题
	 */
	@RequestMapping("/getCmpQpTyppNames")
	@ResponseBody
	public List<String> getCmpQpTyppNames() {
		
		return CommonUtil.getQpTypeNames(service.getQueryQpTypeByFlag(Constant.QC_COMPLAINT));
	}
}
