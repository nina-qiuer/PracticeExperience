package com.tuniu.config.db.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.gt.complaint.entity.DataDictionaryConfigEntity;
import com.tuniu.gt.complaint.service.IDataDictionaryConfigService;
import com.tuniu.gt.frm.entity.UserEntity;

@Controller
@RequestMapping("/data_dictionary")
public class DataDictionaryController {

	@Autowired
	@Qualifier("complaint_service_complaint_impl-data_dictionary_config")
	private IDataDictionaryConfigService dataDictionaryConfigService;

	@RequestMapping("/dictionaryList")
	public String dictionaryList(HttpServletRequest requset) {
		return "data_dictionary/dictionary_list";
	}

	@RequestMapping("/getDictionaryData")
	@ResponseBody
	public List<DataDictionaryConfigEntity> getDictionaryData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String fatherIdStr = request.getParameter("father_id");
		if (fatherIdStr != null) {
			paramMap.put("father_id", Integer.valueOf(fatherIdStr));
		}
		List<DataDictionaryConfigEntity> dataDictionaryLists = (List<DataDictionaryConfigEntity>) dataDictionaryConfigService
				.fetchList(paramMap);
		return dataDictionaryLists;
	}

	@RequestMapping("/addOrUpdateDictionaryData")
	@ResponseBody
	public void addDictionaryData(HttpServletRequest request, DataDictionaryConfigEntity dataDictionaryConfigEntity) {
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		if (dataDictionaryConfigEntity.getId() != null) {
			dataDictionaryConfigEntity.setAdd_id(user.getId());
			dataDictionaryConfigService.update(dataDictionaryConfigEntity);
		} else {
			dataDictionaryConfigEntity.setUpdate_id(user.getId());
			dataDictionaryConfigService.insert(dataDictionaryConfigEntity);
		}
	}

	@RequestMapping("/deleteDictionaryData")
	@ResponseBody
	public void deleteDictionaryData(HttpServletRequest request, HttpServletResponse response) {
		UserEntity user = (UserEntity) request.getSession().getAttribute("user");
		Integer dictionary_id = Integer.valueOf(request.getParameter("dictionary_id"));
		DataDictionaryConfigEntity dataDictionaryConfigEntity = new DataDictionaryConfigEntity();
		dataDictionaryConfigEntity.setId(dictionary_id);
		dataDictionaryConfigEntity.setDel_flag(1);
		dataDictionaryConfigEntity.setUpdate_id(user.getId());;
		dataDictionaryConfigService.update(dataDictionaryConfigEntity);
	}
}
