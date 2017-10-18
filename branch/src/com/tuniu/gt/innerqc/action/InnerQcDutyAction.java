package com.tuniu.gt.innerqc.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.innerqc.entity.InnerQcDutyEntity;
import com.tuniu.gt.innerqc.entity.InnerQcEntity;
import com.tuniu.gt.innerqc.entity.InnerQcQuestionEntity;
import com.tuniu.gt.innerqc.service.InnerQcDutyService;
import com.tuniu.gt.innerqc.service.InnerQcQuestionService;
import com.tuniu.gt.innerqc.service.InnerQcService;
import com.tuniu.gt.score.entity.ScoreRecordEntity;
import com.tuniu.gt.score.service.ScoreRecordService;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;


@Service("innerqc_action-inner_qc_duty")
@Scope("prototype")
public class InnerQcDutyAction extends FrmBaseAction<InnerQcDutyService, InnerQcDutyEntity> {

	private static final long serialVersionUID = 1L;
	
	public InnerQcDutyAction() {
		setManageUrl("inner_qc_duty");
	}
	
	@Autowired
	@Qualifier("innerqc_service_impl-inner_qc_duty")
	public void setService(InnerQcDutyService service) {
		this.service = service;
	};
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("score_service_impl-score_record")
	private ScoreRecordService scoreRecordService;
	
	@Autowired
	@Qualifier("innerqc_service_impl-inner_qc_question")
	private InnerQcQuestionService questionService;
	
	@Autowired
	@Qualifier("innerqc_service_impl-inner_qc")
	private InnerQcService iqcService;
	
	private List<InnerQcDutyEntity> dutyList = new ArrayList<InnerQcDutyEntity>();
	
	private List<DepartmentEntity> departments;
	
	private List<UserEntity> users;
	
	private Map<String, Object> positionMap = CommonUtil.getPositionMap();
	
	private String resultInfo;
	
	public String getResultInfo() {
		return resultInfo;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public Map<String, Object> getPositionMap() {
		return positionMap;
	}

	public void setPositionMap(Map<String, Object> positionMap) {
		this.positionMap = positionMap;
	}

	public List<InnerQcDutyEntity> getDutyList() {
		return dutyList;
	}

	public void setDutyList(List<InnerQcDutyEntity> dutyList) {
		this.dutyList = dutyList;
	}

	public List<DepartmentEntity> getDepartments() {
		return departments;
	}

	public String execute() {
		dutyList = service.getDutyList(entity);
		return "inner_qc_duty_list";
	}
	
	public String toAdd() {
		setDepartments();
		users = userService.getCmpUsers();
		return "inner_qc_duty_form";
	}
	
	@SuppressWarnings("unchecked")
	private void setDepartments() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("depth", 1);
		List<DepartmentEntity> departments1 = (List<DepartmentEntity>) departmentService.fetchList(paramMap);
		for (DepartmentEntity department : departments1) {
			if (department.getDepName().equals("其它人员")) {
				departments1.remove(department);
			}
		}
		DepartmentEntity department1 = new DepartmentEntity();
		department1.setId(1);
		department1.setDepName("不可抗力");
		department1.setFatherId(1);
		DepartmentEntity department2 = new DepartmentEntity();
		department2.setId(2);
		department2.setDepName("系统/流程问题");
		department2.setFatherId(1);
		DepartmentEntity department3 = new DepartmentEntity();
		department3.setId(14);
		department3.setDepName("其他");
		department3.setFatherId(1);
		departments1.add(department1);
		departments1.add(department2);
		departments1.add(department3);
		
		paramMap.clear();
		paramMap.put("depth", 2);
		List<DepartmentEntity> departments2 = (List<DepartmentEntity>) departmentService.fetchList(paramMap);
		
		departments = departments1;
		departments.addAll(departments2);
	}
	
	public String add() {
		entity.setAddPersonId(user.getId());
		service.insert(entity);
		resultInfo = "Success";
		
		insertScoreRecord();
		
		return "inner_qc_duty_form";
	}
	
	private void insertScoreRecord() {
		InnerQcQuestionEntity question =  (InnerQcQuestionEntity) questionService.get(entity.getQuestionId());
		InnerQcEntity iqc = (InnerQcEntity) iqcService.get(question.getIqcId());
		
		ScoreRecordEntity sr = new ScoreRecordEntity();
		sr.setQcDate(DateUtil.getSqlToday());
		sr.setQcPerson(user.getRealName());
		String relBillNum = iqc.getRelBillNum();
		if (1 == iqc.getRelBillType()) {
			sr.setOrderId(Integer.valueOf(relBillNum));
		} else if (2 == iqc.getRelBillType()) {
			sr.setRouteId(Integer.valueOf(relBillNum));
		} else if (3 == iqc.getRelBillType()) {
			sr.setJiraNum(relBillNum);
		}
		
		sr.setQuestionClassId(question.getQuestionTypeId());
		sr.setResponsiblePerson(entity.getRespPersonName());
		sr.setImprover(entity.getRespPersonName());
		sr.setDepId1(entity.getDepId1());
		sr.setDepId2(entity.getDepId2());
		sr.setPositionId(entity.getPositionId());
		sr.setScoreTarget1(entity.getRespPersonName());
		sr.setScoreValue1(entity.getScoreValue());
		sr.setScoreTarget2("");
		
		sr.setScoreTypeId(24); // 24：内部申请质检单
		sr.setDescription(question.getConclusion());
		scoreRecordService.insert(sr);
	}
	
	public String toBill() {
		request.setAttribute("id", entity.getId());
		return "inner_qc_duty_bill";
	}
	
	public String toInfo() {
		entity = (InnerQcDutyEntity) service.get(entity.getId());
		return "inner_qc_duty_info";
	}
	
	public String delete() {
		service.delete(entity.getId());
		return "inner_qc_duty_list";
	}
	
	public String toUpdate() {
		setDepartments();
		users = userService.getCmpUsers();
		entity = (InnerQcDutyEntity) service.get(entity.getId());
		return "inner_qc_duty_form";
	}
	
	public String update() {
		service.update(entity);
		resultInfo = "Success";
		return "inner_qc_duty_form";
	}
	
}
