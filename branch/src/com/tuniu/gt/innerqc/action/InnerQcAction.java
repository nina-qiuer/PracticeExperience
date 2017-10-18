package com.tuniu.gt.innerqc.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.AppointManagerEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IAppointManagerService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.innerqc.entity.InnerQcAttachEntity;
import com.tuniu.gt.innerqc.entity.InnerQcDutyEntity;
import com.tuniu.gt.innerqc.entity.InnerQcEntity;
import com.tuniu.gt.innerqc.entity.InnerQcQuestionEntity;
import com.tuniu.gt.innerqc.entity.InnerQcTypeEntity;
import com.tuniu.gt.innerqc.service.InnerQcAttachService;
import com.tuniu.gt.innerqc.service.InnerQcService;
import com.tuniu.gt.innerqc.service.InnerQcTypeService;
import com.tuniu.gt.innerqc.vo.InnerQcPage;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.toolkit.RTXSenderThread;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;


@Service("innerqc_action-inner_qc")
@Scope("prototype")
public class InnerQcAction extends FrmBaseAction<InnerQcService, InnerQcEntity> {

	private static final long serialVersionUID = 1L;
	
	private InnerQcPage page = new InnerQcPage();
	
	private String resultInfo;
	
	private List<UserEntity> users;
	
	private List<InnerQcTypeEntity> typeList;
	
	private List<File> iqcAttach;
	
	private List<String> iqcAttachFileName;
	
	private List<Integer> attachDelIds;
	
	private static Logger logger = Logger.getLogger(InnerQcAction.class);
	

	public List<Integer> getAttachDelIds() {
		return attachDelIds;
	}

	public void setAttachDelIds(List<Integer> attachDelIds) {
		this.attachDelIds = attachDelIds;
	}

	public List<File> getIqcAttach() {
		return iqcAttach;
	}

	public void setIqcAttach(List<File> iqcAttach) {
		this.iqcAttach = iqcAttach;
	}

	public List<String> getIqcAttachFileName() {
		return iqcAttachFileName;
	}

	public void setIqcAttachFileName(List<String> iqcAttachFileName) {
		this.iqcAttachFileName = iqcAttachFileName;
	}

	public List<InnerQcTypeEntity> getTypeList() {
		return typeList;
	}

	public List<UserEntity> getUsers() {
		return users;
	}

	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	public InnerQcPage getPage() {
		return page;
	}

	public void setPage(InnerQcPage page) {
		this.page = page;
	}

	public InnerQcAction() {
		setManageUrl("inner_qc");
	}
	
	@Autowired
	@Qualifier("innerqc_service_impl-inner_qc")
	public void setService(InnerQcService service) {
		this.service = service;
	};
	
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-appoint_manager")
	private IAppointManagerService amService;
	
	@Autowired
	@Qualifier("innerqc_service_impl-inner_qc_type")
	private InnerQcTypeService typeService;
	
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	
	@Autowired
	@Qualifier("innerqc_service_impl-inner_qc_attach")
	private InnerQcAttachService attachService;
	
	private boolean isDevPerson(UserEntity user) {
		boolean flag = false;
		List<AppointManagerEntity> devList = amService.getListByType(AppointManagerEntity.DEV_OFFICER); // 研发负责人
		for (AppointManagerEntity ame : devList) {
			if (user.getId().intValue() == ame.getUserId().intValue()) {
				flag = true;
				break;
			}
		}
		return flag;
	}
	
	public String execute() {
		int userFlag =1;
		users = userService.getCmpUsers();
		
		typeList = typeService.getIqcTypeList();
		
		request.setAttribute("user", user);
		request.setAttribute("typeList", typeList);
		
		boolean isDevPerson = isDevPerson(user);
		request.setAttribute("isDevPerson", isDevPerson);
		//---顾问管理部/质量改进组开通内部质检列表的查看权限
		int showFlag = 0; 
		if(user.getDepId() == 2584){
			
			page.setState("3");
			isDevPerson = true;
			showFlag = 1;
		}
		request.setAttribute("showFlag", showFlag);
		//
		//----begin---内部质检蒋飞配置权限
		int userId =user.getId();
		if(userId == 8726){
			
			isDevPerson = true;
			userFlag = 0;
		}
		//-----end-----------
		if (!isDevPerson && 2966!=user.getId()) {
			String personIdStr = String.valueOf(user.getId());
			if (2 <= user.getPositionId()) {
				List<UserEntity> userList = userService.getAllSameGroupUsers(user);
				StringBuffer sb = new StringBuffer();
				for (UserEntity ue : userList) {
					sb.append(ue.getId()).append(",");
				}
				personIdStr = sb.toString().substring(0, sb.length()-1);
			}
			page.setPersonIdStr(personIdStr);
		}
		request.setAttribute("userFlag", userFlag);
		service.getInnerQcPage(page);
		return "inner_qc_list";
	}
	
	public String toAdd() {
		typeList = typeService.getIqcTypeList();
		request.setAttribute("typeList", typeList);
		
		return "inner_qc_form";
	}
	
	public String add() {
		entity.setAddPersonId(user.getId());
		service.insert(entity);
		addAttach(entity.getId());
		
		return "inner_qc_form";
	}
	
	private void addAttach(Integer iqcId) {
		if (null != iqcAttachFileName && iqcAttachFileName.size() > 0) {
			for (int i=0; i<iqcAttachFileName.size(); i++) {
				String fileName = iqcAttachFileName.get(i);
				File file = iqcAttach.get(i);
				attachService.addAttach(iqcId, file, fileName);
			}
		}
	}
	
	public String toBill() {
		
		entity = getInnerQc(entity.getId());
		String emailTitle = entity.getQcEventSummary() + " —— 质检报告";
		request.setAttribute("id", entity.getId());
		request.setAttribute("ccEmails", getCcEmails(entity.getAddPersonId(),entity.getDealPersonId()));
		request.setAttribute("reEmails", getReEmails(entity.getAddPersonId()));
		request.setAttribute("emailTitle", emailTitle);
		return "inner_qc_bill";
	}
	
	private String getCcEmails(Integer addPersonId, Integer dealPersonId) {
		StringBuffer sb = new StringBuffer();
		sb.append("changjingyong@tuniu.com;").append("yaochen@tuniu.com;").append("hanqiang@tuniu.com;").append("yanghuifan@tuniu.com;");
		UserEntity user1 = userService.getUserByID(addPersonId); // 申请人
		if (null != user1) {
			sb.append(user1.getEmail()).append(";");
			
			UserEntity reporter = ComplaintRestClient.getReporter(user1.getRealName());
			Integer rid = reporter.getId();
			if (null != rid && rid.intValue() > 0) {
				reporter = userService.getUserByID(rid);
				sb.append(reporter.getEmail()).append(";");
			}
		}
		
		//若当前抄送人列表中不包含质检员则增加质检员
		UserEntity dealUser =  userService.getUserByID(dealPersonId);
		if(null != dealUser){
			String dealUserEmail = dealUser.getEmail() + ";";
			if(-1==sb.indexOf(dealUserEmail))
			{
				sb.append(dealUserEmail);
			}
		}
		
		return sb.toString();
	}
	
	private String getReEmails(Integer addPersonId){
		StringBuffer sb = new StringBuffer();
		UserEntity addUser = userService.getUserByID(addPersonId); // 申请人
		if (null != addUser) {
			sb.append(addUser.getEmail()).append(";");
		}
		return sb.toString();
		
	}
	
	@SuppressWarnings("unchecked")
	public String toInfo() {
		entity = (InnerQcEntity) service.get(entity.getId());
		if (null != entity) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("iqcId", entity.getId());
			List<InnerQcAttachEntity> attachList = (List<InnerQcAttachEntity>) attachService.fetchList(params);
			entity.setAttachList(attachList);
		}
		
		return "inner_qc_info";
	}
	
	public String delete() {
		service.deleteInnerQc(entity);
		return "inner_qc_list";
	}
	
	@SuppressWarnings("unchecked")
	public String toUpdate() {
		typeList = typeService.getIqcTypeList();
		request.setAttribute("typeList", typeList);
		
		entity = (InnerQcEntity) service.get(entity.getId());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("iqcId", entity.getId());
		List<InnerQcAttachEntity> attachList = (List<InnerQcAttachEntity>) attachService.fetchList(params);
		entity.setAttachList(attachList);
		
		return "inner_qc_form";
	}
	
	public String update() {
		service.update(entity);
		
		if (null != attachDelIds && attachDelIds.size() > 0) {
			for (Integer attachId : attachDelIds) {
				attachService.delete(attachId);
			}
		}
		
		addAttach(entity.getId());
		
		return "inner_qc_form";
	}
	
	public String toBack() {
		entity = (InnerQcEntity) service.get(entity.getId());
		
		return "inner_qc_back";
	}
	
	public String back() {
		service.update(entity);
		
		String title = "【质检退回提醒】";
		String content = "质检单号：" + entity.getId() + "\n" + 
			"质检事宜概述：" + entity.getQcEventSummary();
		new RTXSenderThread(entity.getAddPersonId(), "", title, content).start();
		
		return "inner_qc_back";
	}
	
	@SuppressWarnings("unchecked")
	public String toReport() {
		entity = getInnerQc(entity.getId());
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("iqcId", entity.getId());
		List<InnerQcAttachEntity> attachList = (List<InnerQcAttachEntity>) attachService.fetchList(params);
		entity.setAttachList(attachList);
		
		return "inner_qc_report";
	}
	
	private InnerQcEntity getInnerQc(int id) {
		InnerQcEntity iqcEntity = service.getInnerQc(id);
		return iqcEntity;
	}
	
	public String sendEmail() {
		entity = getInnerQc(entity.getId());
		
		String title = request.getParameter("title");
		String reEmails = request.getParameter("reEmails");
		String ccEmails = request.getParameter("ccEmails");
		tspService.sendMail(new MailerThread(getEmailArr(reEmails), getEmailArr(ccEmails), title, getEmailContent(entity)));
		
		entity = (InnerQcEntity) service.get(entity.getId());
		entity.setState(Constans.FINISHED);
		service.update(entity);
		
		resultInfo = "Success";
		return "inner_qc_bill";
	}
	
	private String getEmailContent(InnerQcEntity ent) {
		StringBuffer sb = new StringBuffer();
		sb.append("<html><head><style type='text/css'>")
		.append(".common-box {border: 1px solid #8CBFDE;margin:0 0 10px 0;}")
		.append(".common-box-hd {margin-top: 1px;padding-left: 10px;background: #C6E3F1;color: #005590;font-size: 14px;font-weight: bold;height: 25px;line-height: 25px;border-bottom: 1px solid #8CBFDE;position: relative;}")
		.append(".common-box-hd span.title2 {cursor: pointer;display: inline-block;line-height: 25px;margin-right: 15px;}")
		.append(".datatable {border:1px solid #fff;border-collapse:collapse;font-size:13px;}.datatable th{border:1px solid #fff;color:#355586;background:#DFEAFB; padding:2px;width:8%}.datatable td{padding:2px;background-color: #F9F5F2;border: 1px solid #fff;}</style></head><body>");
		sb.append("<div class='common-box'><div class='common-box-hd'><span class='title2'>质检单号：").append(ent.getId())
		  .append("</span></div><table width='100%' class='datatable'><tr><th align='right'>关联单据类型：</th><td>");
		if (0 == ent.getRelBillType()) {
			sb.append("无关联");
		} else if (1 == ent.getRelBillType()) {
			sb.append("订单");
		} else if (2 == ent.getRelBillType()) {
			sb.append("产品");
		} else if (3 == ent.getRelBillType()) {
			sb.append("Jira");
		}
		sb.append("</td><th align='right'>关联单据编号：</th><td>").append(ent.getRelBillNum())
		.append("</td><th align='right'>公司损失：</th><td>").append(ent.getLossAmount())
		.append(" 元</td></tr><tr><th align='right'>质检事宜类型：</th><td>").append(ent.getTypeName())
		.append("</td><th align='right'>质检事宜概述：</th><td colspan='3'>")
		.append(ent.getQcEventSummary()).append("</td></tr><tr><th align='right'>质检事宜详述：</th><td colspan='5'>")
		.append(ent.getQcEventDesc()).append("</td></tr><tr><th align='right'>备注：</th><td colspan='5'>")
		.append(ent.getRemark()).append("</td></tr><tr><th align='right'>申请人：</th><td>")
		.append(ent.getAddPersonName()).append("</td><th align='right'>申请时间：</th><td>")
		.append(DateUtil.formatDate(ent.getAddTime(), DateUtil.DATE_TIME_PAT)).append("</td><th align='right'>质检员：</th><td>")
		.append(ent.getDealPersonName()).append("</td></tr><tr><td colspan='6' style='padding-left: 15px;'>");
		for (InnerQcQuestionEntity question : ent.getQuestionList()) {
			sb.append("<div class='common-box-hd'><span class='title2'>质量问题编号：").append(question.getId())
			.append("</span></div><table width='100%' class='datatable'><tr><th align='right'>问题等级：</th><td>")
			.append(question.getQuestionLevel()).append("</td><th align='right'>问题类型：</th><td>")
			.append(question.getBigClassName() + "&nbsp;—&nbsp;" + question.getSmallClassName()).append("</td></tr><tr><th align='right'>问题描述：</th><td colspan='5'>")
			.append(question.getDescription()).append("</td></tr><tr><th align='right'>核实依据：</th><td colspan='5'>")
			.append(question.getVerifyBasis()).append("</td></tr><tr><th align='right'>质检结论：</th><td colspan='5'>")
			.append(question.getConclusion()).append("</td></tr><tr><td colspan='4' style='padding-left: 15px;'><div class='common-box-hd'><span class='title2'>责任单</span></div>")
			.append("<table width='100%' class='datatable'><tr><th>编号</th><th>一级责任部门</th><th>二级责任部门</th><th>责任岗位</th><th>责任人</th>")
			.append("<th>记分值</th><th>罚款金额</th></tr>");
			for (InnerQcDutyEntity duty : question.getDutyList()) {
				sb.append("<tr align='center'><td>").append(duty.getId()).append("</td><td>").append(duty.getDepName1())
				.append("</td><td>").append(duty.getDepName2()).append("</td><td>").append(duty.getPositionName())
				.append("</td><td>").append(duty.getRespPersonName()).append("</td><td>").append(duty.getScoreValue())
				.append("</td><td>").append(duty.getFineAmount()).append("&nbsp;元</td></tr>");
			}
			sb.append("</table></td></tr></table>");
		}
		sb.append("</td></tr></table></div></body></html>");
		
		logger.info("内部质检邮件"+sb.toString());
		
		return sb.toString();
	}
	
	private String[] getEmailArr(String emails) {
		String[] emailArr = emails.trim().split(";");
		List<String> emailList = new ArrayList<String>();
		for (String email : emailArr) {
			if (StringUtil.isNotEmpty(email)) {
				emailList.add(email);
			}
		}
		return emailList.toArray(new String[emailList.size()]);
	}
	
	public String assignDealPerson() {
		String[] ids = page.getIqcIds();
		for (String id : ids) {
			InnerQcEntity iqc = new InnerQcEntity();
			iqc.setId(Integer.valueOf(id));
			iqc.setDealPersonId(Integer.valueOf(page.getDealPersonId()));
			iqc.setState(Constans.PROCESSING);
			service.update(iqc);
		}
		
		page.setDealPersonId("");
		page.setDealPersonName("");
		return execute();
	}
	
}
