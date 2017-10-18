package com.tuniu.qms.common.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.tuniu.qms.common.dao.MailTaskMapper;
import com.tuniu.qms.common.dto.MailTaskDto;
import com.tuniu.qms.common.model.MailTask;
import com.tuniu.qms.common.service.MailTaskService;

import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("mailTaskService")
public class MailTaskServiceImpl implements MailTaskService {
	
	@Autowired
    private MailTaskMapper mapper;
	
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;

	@Override
	public void add(MailTask obj) {
		mapper.add(obj);
	}
	
	@Override
	public void addTask(MailTaskDto dto) {
		MailTask mail = new MailTask();
		mail.setReAddrs(dto.getReAddrs());
		mail.setCcAddrs(dto.getCcAddrs());
		mail.setSubject(dto.getSubject());
		mail.setAddPersonRoleId(dto.getAddPersonRoleId());
		mail.setAddPerson(dto.getAddPerson());
		
		String content = getMailText(dto.getTemplateName(), dto.getDataMap());
		mail.setContent(content);
		mapper.add(mail);
	}
	
	/** 通过模板构造邮件内容 */
	public String getMailText(String templateName, Map<String, Object> dataMap) {
		String htmlText = "";
		try {
			Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templateName); // 通过指定模板名获取FreeMarker模板实例
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(template, dataMap); // 解析模板并替换动态数据
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return htmlText;
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(MailTask obj) {
		mapper.update(obj);
	}

	@Override
	public MailTask get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<MailTask> list(MailTaskDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(MailTaskDto dto) {
		
	}

	@Override
	public void increSendTimes(Integer mailId) {
		mapper.increSendTimes(mailId);
	}
	
}
