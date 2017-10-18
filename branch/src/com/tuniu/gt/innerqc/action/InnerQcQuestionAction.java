package com.tuniu.gt.innerqc.action;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.QcQuestionClassEntity;
import com.tuniu.gt.complaint.service.IQcQuestionClassService;
import com.tuniu.gt.innerqc.entity.InnerQcQuestionEntity;
import com.tuniu.gt.innerqc.service.InnerQcQuestionService;


@Service("innerqc_action-inner_qc_question")
@Scope("prototype")
public class InnerQcQuestionAction extends FrmBaseAction<InnerQcQuestionService, InnerQcQuestionEntity> {

	private static final long serialVersionUID = 1L;
	
	public InnerQcQuestionAction() {
		setManageUrl("inner_qc_question");
	}
	
	@Autowired
	@Qualifier("innerqc_service_impl-inner_qc_question")
	public void setService(InnerQcQuestionService service) {
		this.service = service;
	};
	
	@Autowired
	@Qualifier("complaint_service_impl-qc_question_class")
	private IQcQuestionClassService questionClassService;
	
	private String resultInfo;
	
	private List<QcQuestionClassEntity> questionClasses;
	
	private List<InnerQcQuestionEntity> questionList = new ArrayList<InnerQcQuestionEntity>();
	
	public String getResultInfo() {
		return resultInfo;
	}

	public List<QcQuestionClassEntity> getQuestionClasses() {
		return questionClasses;
	}

	public void setQuestionClasses(List<QcQuestionClassEntity> questionClasses) {
		this.questionClasses = questionClasses;
	}

	public List<InnerQcQuestionEntity> getQuestionList() {
		return questionList;
	}

	public void setQuestionList(List<InnerQcQuestionEntity> questionList) {
		this.questionList = questionList;
	}

	public String execute() {
		questionList = service.getQuestionList(entity);
		return "inner_qc_question_list";
	}
	
	public String toAdd() {
		questionClasses = questionClassService.list();
		
		return "inner_qc_question_form";
	}
	
	public String add() {
		entity.setAddPersonId(user.getId());
		service.insert(entity);
		resultInfo = "Success";
		return "inner_qc_question_form";
	}
	
	public String toBill() {
		request.setAttribute("id", entity.getId());
		return "inner_qc_question_bill";
	}
	
	public String toInfo() {
		entity = (InnerQcQuestionEntity) service.get(entity.getId());
		return "inner_qc_question_info";
	}
	
	public String delete() {
		service.deleteInnerQcQuestion(entity);
		return "inner_qc_question_list";
	}
	
	public String toUpdate() {
		entity = (InnerQcQuestionEntity) service.get(entity.getId());
		questionClasses = questionClassService.list();
		return "inner_qc_question_form";
	}
	
	public String update() {
		service.update(entity);
		resultInfo = "Success";
		return "inner_qc_question_form";
	}
	
}
