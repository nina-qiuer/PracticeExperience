package com.tuniu.gt.score.action;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.entity.QcQuestionClassEntity;
import com.tuniu.gt.complaint.service.IQcQuestionClassService;
import com.tuniu.gt.score.entity.ScoreRecordEntity;
import com.tuniu.gt.score.entity.ScoreTypeEntity;
import com.tuniu.gt.score.entity.SrQcTeamEntity;
import com.tuniu.gt.score.service.ScoreRecordService;
import com.tuniu.gt.score.service.ScoreTypeService;
import com.tuniu.gt.score.service.SrQcTeamService;
import com.tuniu.gt.score.util.ScoreRecordPage;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DataExportExcel;
import com.tuniu.gt.toolkit.ExcelImportUtil;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;

import tuniu.frm.core.FrmBaseAction;


@Service("score_action-score_record")
@Scope("prototype")
public class ScoreRecordAction extends FrmBaseAction<ScoreRecordService, ScoreRecordEntity> {

	private static final long serialVersionUID = 1L;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	@Qualifier("complaint_service_impl-qc_question_class")
	private IQcQuestionClassService questionClassService;
	
	@Autowired
	@Qualifier("score_service_impl-score_type")
	private ScoreTypeService typeService;
	
	@Autowired
	@Qualifier("score_service_impl-sr_qc_team")
	private SrQcTeamService qcTeamService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentService;
	
	// 姚琛：1858，薛桂霞：2966，刘亚军：3631，冷静2：2928，姚玲：5325，陈志明：3364，陈鑫：8063，沈伟：3812，黄艳丽2：6096
	private static final int[] managers = new int[]{1858,2966,6096,3631,2928,5325,3364,8063,3812};
	
	private ScoreRecordPage page = new ScoreRecordPage();
	
	private int isManager;
	
	private List<QcQuestionClassEntity> questionClasses;
	
	private List<ScoreTypeEntity> scoreTypes;
	
	private List<DepartmentEntity> departments;
	
	private List<SrQcTeamEntity> qcTeams;
	
	private File excelFile; //上传的文件 
	
	private String excelFileFileName;
	
	public int getIsManager() {
		return isManager;
	}

	public void setIsManager(int isManager) {
		this.isManager = isManager;
	}

	public List<SrQcTeamEntity> getQcTeams() {
		return qcTeams;
	}

	public void setQcTeams(List<SrQcTeamEntity> qcTeams) {
		this.qcTeams = qcTeams;
	}

	private List<Map<ScoreTypeEntity, List<ScoreTypeEntity>>> scoreTypeList;
	
	public List<Map<ScoreTypeEntity, List<ScoreTypeEntity>>> getScoreTypeList() {
		return scoreTypeList;
	}

	public void setScoreTypeList(
			List<Map<ScoreTypeEntity, List<ScoreTypeEntity>>> scoreTypeList) {
		this.scoreTypeList = scoreTypeList;
	}

	public String getExcelFileFileName() {
		return excelFileFileName;
	}

	public void setExcelFileFileName(String excelFileFileName) {
		this.excelFileFileName = excelFileFileName;
	}

	public File getExcelFile() {
		return excelFile;
	}

	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
	}

	public List<DepartmentEntity> getDepartments() {
		return departments;
	}

	@SuppressWarnings("unchecked")
	public void setDepartments() {
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

	public List<ScoreTypeEntity> getScoreTypes() {
		return scoreTypes;
	}

	public void setScoreTypes(List<ScoreTypeEntity> scoreTypes) {
		this.scoreTypes = scoreTypes;
	}

	private Map<String, Object> positionMap = CommonUtil.getPositionMap();
	
	public Map<String, Object> getPositionMap() {
		return positionMap;
	}

	public void setPositionMap(Map<String, Object> positionMap) {
		this.positionMap = positionMap;
	}

	public ScoreRecordPage getPage() {
		return page;
	}

	public void setPage(ScoreRecordPage page) {
		this.page = page;
	}

	public List<QcQuestionClassEntity> getQuestionClasses() {
		return questionClasses;
	}

	public void setQuestionClasses(List<QcQuestionClassEntity> questionClasses) {
		this.questionClasses = questionClasses;
	}

	public ScoreRecordAction() {
		setManageUrl("score_record");
	}
	
	@Autowired
	@Qualifier("score_service_impl-score_record")
	public void setService(ScoreRecordService service) {
		this.service = service;
	};
	
	@SuppressWarnings("unchecked")
	public String execute() {
		setDepartments();
		qcTeams = (List<SrQcTeamEntity>) qcTeamService.fetchList();
		
		String teamId = page.getQcTeamId();
		if (null != teamId && !"".equals(teamId)) {
			SrQcTeamEntity team = (SrQcTeamEntity) qcTeamService.get(teamId);
			String[] persons = team.getPersons().split(",");
			StringBuffer qcPersons = new StringBuffer();
			for (int i=0; i<persons.length; i++) {
				String person = persons[i];
				qcPersons.append("'").append(person).append("'");
				if (i < persons.length-1) {
					qcPersons.append(",");
				}
			}
			page.setQcPersons(qcPersons.toString());
		}
		service.getScoreRecordPage(page);
		
		for (int i=0; i<managers.length; i++) {
			if (user.getId() == managers[i]) {
				isManager = 1;
				break;
			}
		}
		
		return "score_record_list";
	}
	
	@SuppressWarnings("unchecked")
	public String toAdd() {
		questionClasses = questionClassService.list();
		
		scoreTypes = (List<ScoreTypeEntity>) typeService.fetchList();
		
		setDepartments();
		
		return "score_record_form";
	}
	
	public String add() {
		service.insert(entity);
		return "score_record_form";
	}
	
	public String toInfo() {
		entity = (ScoreRecordEntity) service.get(entity.getId());
		String position = (String) CommonUtil.getPositionMap().get(String.valueOf(entity.getPositionId()));
		entity.setPositionName(position);
		switch (entity.getDepId1()) {
		case 1:
			entity.setDepName1("不可抗力");
			break;
		case 2:
			entity.setDepName1("系统/流程问题");
			break;
		case 14:
			entity.setDepName1("其他");
			break;
		default:
			break;
		}
		String description = CommonUtil.replaceEnter(entity.getDescription());
		entity.setDescription(description);
		
		return "score_record_info";
	}
	
	public String delete() {
		service.delete(entity.getId());
		CommonUtil.writeResponse(null);
		return "score_record_list";
	}
	
	public String batchDel() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idStr", page.getSrIdStr());
		service.batchDel(params);
		CommonUtil.writeResponse(null);
		return "score_record_list";
	}
	
	@SuppressWarnings("unchecked")
	public String toUpdate() {
		questionClasses = questionClassService.list();
		
		scoreTypes = (List<ScoreTypeEntity>) typeService.fetchList();
		
		setDepartments();
		
		entity = (ScoreRecordEntity) service.get(entity.getId());
		String position = (String) CommonUtil.getPositionMap().get(String.valueOf(entity.getPositionId()));
		entity.setPositionName(position);
		
		return "score_record_form";
	}
	
	public String update() {
		service.update(entity);
		return "score_record_form";
	}
	
	public String toImport() {
		return "score_record_import";
	}
	
	public String doImport() {
		int succFlag = 0;
		Workbook book = ExcelImportUtil.createWorkBook(excelFile, excelFileFileName);
		try {
			List<ScoreRecordEntity> srList = getScoreRecordList(book);
			service.batchInsert(srList);
			succFlag = 1;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		CommonUtil.writeResponse(succFlag);
		return "score_record_import";
	}
	
	@SuppressWarnings("unchecked")
	private List<ScoreRecordEntity> getScoreRecordList(Workbook book) throws Exception {
		questionClasses = questionClassService.list();
		setDepartments();
		Map<String, Object> positionMap = CommonUtil.getPositionMap();
		scoreTypes = (List<ScoreTypeEntity>) typeService.fetchList();
		
		List<ScoreRecordEntity> list = new ArrayList<ScoreRecordEntity>();
		//book.getNumberOfSheets();  判断Excel文件有多少个sheet
        Sheet sheet =  book.getSheetAt(0);
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            ScoreRecordEntity srEnt = new ScoreRecordEntity();
            srEnt.setQcDate(new java.sql.Date(row.getCell(2).getDateCellValue().getTime()));
            srEnt.setQcPerson(row.getCell(15).getStringCellValue());
            Cell c3 = row.getCell(3);
            if (Cell.CELL_TYPE_BLANK == c3.getCellType()) {
            	srEnt.setOrderId(0);
            	srEnt.setRouteId(0);
            	srEnt.setJiraNum("");
            } else if (Cell.CELL_TYPE_NUMERIC == c3.getCellType()) {
            	int orderId = (int) c3.getNumericCellValue();
            	srEnt.setOrderId(orderId);
            	srEnt.setRouteId(0);
            	srEnt.setJiraNum("");
            } else {
            	String numStr = c3.getStringCellValue();
            	if(numStr.endsWith("R")) {
                	int routeId = Integer.valueOf(numStr.substring(0, numStr.length()-1));
                	srEnt.setOrderId(0);
                	srEnt.setRouteId(routeId);
                	srEnt.setJiraNum("");
                } else {
                	srEnt.setOrderId(0);
                	srEnt.setRouteId(0);
                	srEnt.setJiraNum(numStr);
                }
            }
            srEnt.setComplaintId((int) row.getCell(16).getNumericCellValue());
            String className1 = row.getCell(19).getStringCellValue();
            int classId1 = getQuestionId(className1, 0);
            String className2 = row.getCell(20).getStringCellValue();
            srEnt.setQuestionClassId(getQuestionId(className2, classId1));
            srEnt.setResponsiblePerson(row.getCell(17).getStringCellValue());
            srEnt.setImprover(row.getCell(18).getStringCellValue());
            String depName1 = row.getCell(4).getStringCellValue();
            int depId1 = getDepId(depName1, 1);
            srEnt.setDepId1(depId1);
            String depName2 = row.getCell(5).getStringCellValue();
            srEnt.setDepId2(getDepId(depName2, depId1));
            String positionName = row.getCell(8).getStringCellValue();
            srEnt.setPositionId(getPositionId(positionMap, positionName));
            srEnt.setScoreTarget1(row.getCell(9).getStringCellValue());
            srEnt.setScoreTarget2(row.getCell(11).getStringCellValue());
            srEnt.setScoreValue1((int) row.getCell(10).getNumericCellValue());
            srEnt.setScoreValue2((int) row.getCell(12).getNumericCellValue());
            String scoreTypeName1 = row.getCell(6).getStringCellValue();
            int scoreTypeId1 = getScoreTypeId(scoreTypeName1, 0);
            String scoreTypeName2 = row.getCell(7).getStringCellValue();
            srEnt.setScoreTypeId(getScoreTypeId(scoreTypeName2, scoreTypeId1));
            srEnt.setDescription(row.getCell(14).getStringCellValue());
            list.add(srEnt);
        }
		return list;
	}
	
	private int getQuestionId(String className, int fatherId) {
		int id = 0;
		for (QcQuestionClassEntity cla : questionClasses) {
			if (className.equals(cla.getDescription()) && cla.getParentId() == fatherId) {
				id = cla.getId();
				break;
			}
		}
		return id;
	}
	
	private int getDepId(String depName, int fatherId) {
		int id = 0;
		for (DepartmentEntity dep : departments) {
			if (depName.equals(dep.getDepName()) && dep.getFatherId() == fatherId) {
				id = dep.getId();
				break;
			}
		}
		return id;
	}
	
	private int getPositionId(Map<String, Object> positionMap, String positionName) {
		int id = 0;
		Set<String> keys = positionMap.keySet();
		for (String key : keys) {
			String value = (String) positionMap.get(key);
			if (value.equals(positionName)) {
				id = Integer.valueOf(key);
				break;
			}
		}
		return id;
	}
	
	private int getScoreTypeId(String scoreTypeName, int fatherId) {
		int id = 0;
		for (ScoreTypeEntity type : scoreTypes) {
			if (scoreTypeName.equals(type.getName()) && type.getParentId() == fatherId) {
				id = type.getId();
				break;
			}
		}
		return id;
	}

	public String toMyScoreRecord() {
		page.setMyName(user.getRealName());
		service.getScoreRecordPage(page);
		return "my_score_record";
	}
	
	public String exports() {
		List<List<Object>> data = new ArrayList<List<Object>>();
		data.add(getColumnTitles()); // 设置标题
		
		page.setStart(-1);
		List<ScoreRecordEntity> srList = service.getScoreRecordList(page);
		setDataList(srList, data); // 设置数据
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String filename = "ScoreRecord_" + (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
		DataExportExcel dee = new DataExportExcel(data, filename, "", response);
		dee.createExcelFromList();
		return "score_record_list";
	}
	
	private List<Object> getColumnTitles() {
		List<Object> ctList = new ArrayList<Object>();
		ctList.add("记分单号");
		ctList.add("订单号 /线路号/jira号");
		if (user.getDepId()==973 || user.getId()==5175 || user.getId()==7167 || user.getId()==4405) {
			ctList.add("投诉单号");
			ctList.add("问题类型一");
			ctList.add("问题类型二");
			ctList.add("责任人");
			ctList.add("改进人");
		}
		ctList.add("一级部门");
		ctList.add("二级部门");
		ctList.add("记分性质");
		ctList.add("记分类型");
		ctList.add("记分对象1");
		ctList.add("记分值1");
		ctList.add("记分对象2");
		ctList.add("记分值2");
		ctList.add("总分");
		ctList.add("质检人");
		ctList.add("质检日期");
		return ctList;
	}
	
	private void setDataList(List<ScoreRecordEntity> srList, List<List<Object>> data) {
		if (null != srList && srList.size() > 0) {
			for (ScoreRecordEntity sr : srList) {
				List<Object> dataList = new ArrayList<Object>();
				dataList.add(sr.getId());
				int orderId = sr.getOrderId();
				int routeId = sr.getRouteId();
				if (orderId > 0) {
					dataList.add(orderId);
				} else if (routeId > 0) {
					dataList.add(routeId);
				} else {
					dataList.add(sr.getJiraNum());
				}
				if (user.getDepId()==973 || user.getId()==5175 || user.getId()==7167 || user.getId()==4405) {
					dataList.add(sr.getComplaintId());
					
					String bigClassName = sr.getBigClassName();
					dataList.add(null == bigClassName ? "" : bigClassName);
					
					String smallClassName = sr.getSmallClassName();
					dataList.add(null == smallClassName ? "" : smallClassName);
					
					dataList.add(sr.getResponsiblePerson());
					dataList.add(sr.getImprover());
				}
				
				String depName1 = sr.getDepName1();
				dataList.add(null == depName1 ? "" : depName1);
				
				String depName2 = sr.getDepName2();
				dataList.add(null == depName2 ? "" : depName2);
				
				String scoreTypeName1 = sr.getScoreTypeName1();
				dataList.add(null == scoreTypeName1 ? "" : scoreTypeName1);
				
				String scoreTypeName2 = sr.getScoreTypeName2();
				dataList.add(null == scoreTypeName2 ? "" : scoreTypeName2);
				
				dataList.add(sr.getScoreTarget1());
				dataList.add(sr.getScoreValue1());
				dataList.add(sr.getScoreTarget2());
				dataList.add(sr.getScoreValue2());
				dataList.add(sr.getScoreValue1() + sr.getScoreValue2());
				dataList.add(sr.getQcPerson());
				dataList.add(sr.getQcDate());
				data.add(dataList);
			}
		}
	}
	
}
