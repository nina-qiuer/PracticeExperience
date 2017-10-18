package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.AttachEntity;
import com.tuniu.gt.complaint.entity.CardUniqueEntity;
import com.tuniu.gt.complaint.entity.ClaimAuditHistory;
import com.tuniu.gt.complaint.entity.ClaimsAuditEntity;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintFollowNoteEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.FollowUpRecordEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.ClaimAuditHistoryService;
import com.tuniu.gt.complaint.service.ClaimsAuditService;
import com.tuniu.gt.complaint.service.IAttachService;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IFollowUpRecordService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.frm.action.DBConfigManager;
import com.tuniu.gt.warning.common.Page;
import com.tuniu.gt.toolkit.JsonUtil;
import com.tuniu.gt.toolkit.MemcachesUtil;
import com.tuniu.gt.uc.datastructure.TreeNode;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;

@Service("complaint_action-claims_audit")
@Scope("prototype")
public class ClaimsAuditAction extends FrmBaseAction<ClaimsAuditService, ClaimsAuditEntity> {
	
	private static final long serialVersionUID = 1L;
	
	/**待审核  0*/
	private  static final Integer UNTRIAL = 0;
	/**已初审  1*/
	private  static final Integer PASS_FIRST_TRIAL = 1;
	/*复审一 2*/
	private  static final Integer RETRIAL_ONE = 2;
	/*复审二 3*/
	private  static final Integer RETRIAL_TWO = 3;
	/*复审三 5*/
	private  static final Integer RETRIAL_THREE = 5;
	/**审核完成  4*/
	private  static final Integer PASS_TRIAL = 4;
	
	
	private static Logger logger = Logger.getLogger(ClaimsAuditAction.class);
	
	private Page page;
	
	private String complaintId;
	
	private int createType;
	
	private int orderId;
	
	private ClaimsAuditEntity claimsAuditEntity;
	
	private List<String> authoritys = new ArrayList<String>();
	
	private Map<String,Object> info;
	
	private List<String> dealDepartments;
	
	private List<TreeNode<DepartmentEntity>> claimsAuditGroups;
	
	public ClaimsAuditAction() {
		setManageUrl("complaint");
		info = new HashMap<String, Object>();
	}

	@Autowired
	@Qualifier("complaint_service_complaint_impl-claims_audit")
	public void setService(ClaimsAuditService service) {
		this.service = service;
	};
	
    // 引入跟进记录service
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint_follow_note")
    private IComplaintFollowNoteService complaintFollowNoteService;

    // 引入跟进记录service
    @Autowired
    @Qualifier("complaint_service_complaint_follow_up_record_impl-follow_up_record")
    private IFollowUpRecordService followUpRecordService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	// 上传文件
	@Autowired
	@Qualifier("complaint_service_impl-attach")
	private IAttachService attachService;
	
	// 引入解决方案service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_solution")
	private IComplaintSolutionService complaintSolutionService;

	// 引入分担方案service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService shareSolutionService;
	
	//引入赔款审核历史记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-claim_audit_history")
	private ClaimAuditHistoryService claimAuditHistoryService;
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService departmentServiceImpl;

	
	private String tab_flag;
	
	public ClaimsAuditEntity getClaimsAuditEntity() {
		return claimsAuditEntity;
	}

	public void setClaimsAuditEntity(ClaimsAuditEntity claimsAuditEntity) {
		this.claimsAuditEntity = claimsAuditEntity;
	}

	public List<String> getAuthoritys() {
		return authoritys;
	}

	public void setAuthoritys(List<String> authoritys) {
		this.authoritys = authoritys;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getCreateType() {
		return createType;
	}

	public void setCreateType(int createType) {
		this.createType = createType;
	}

	public String getTab_flag() {
		return tab_flag;
	}

	public void setTab_flag(String tab_flag) {
		this.tab_flag = tab_flag;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getComplaintId() {
		return complaintId;
	}

	public void setComplaintId(String complaintId) {
		this.complaintId = complaintId;
	}

	/**
	 * 返回主页面
	 * 
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String execute() {
		if(page == null){
			page = new Page();
			page.setPageSize(10);
		}
		if(tab_flag!=null){
			Map<String, String> search = Common.getSqlMap(getRequestParam(), "claimsAuditEntity.");
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.putAll(search);
			if("menu_1".equals(tab_flag)){
				paramMap.put("audit_flag", "0,1,2,3,4,5");
			}
			if("menu_2".equals(tab_flag)){
				paramMap.put("audit_flag", UNTRIAL);
			}
			if("menu_3".equals(tab_flag)){
				paramMap.put("audit_flag", PASS_FIRST_TRIAL);
			}
			if("menu_4".equals(tab_flag)){
				paramMap.put("audit_flag", RETRIAL_ONE);
			}
			if("menu_5".equals(tab_flag)){
				paramMap.put("audit_flag", RETRIAL_TWO);
			}
			if("menu_6".equals(tab_flag)){
				paramMap.put("audit_flag", RETRIAL_THREE);
			}
			if("menu_7".equals(tab_flag)){
				paramMap.put("audit_flag", PASS_TRIAL);
			} else {
				paramMap.put("auditStartTime", null);
				paramMap.put("auditStartEnd", null);
			}
			
			if("menu_2".equals(tab_flag)||"menu_3".equals(tab_flag)){
				//claimsAuditGroups = departmentServiceImpl.getDepartTreeByDeptId(2581);
				List<TreeNode<DepartmentEntity>> postSaleDepTree = departmentServiceImpl.getDepartTreeByDeptId(2581);
				List<TreeNode<DepartmentEntity>> vipSalerDepTree = departmentServiceImpl.getDepartTreeByDeptId(2580);
				List<TreeNode<DepartmentEntity>> guoneiDepTree = departmentServiceImpl.getDepartTreeByDeptId(6196);
				claimsAuditGroups = new ArrayList<TreeNode<DepartmentEntity>>();
				claimsAuditGroups.addAll(postSaleDepTree);
				claimsAuditGroups.addAll(vipSalerDepTree);
				claimsAuditGroups.addAll(guoneiDepTree);
			} else{
				paramMap.put("depId", null);
				paramMap.put("groupId", null);
			}
			
			paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
			paramMap.put("dataLimitEnd", page.getPageSize());
			List<ClaimsAuditEntity> claimsAuditEntitys = service.queryClaimsAuditList(paramMap, tab_flag);
			Integer queryClaimsAuditListCount = service.queryClaimsAuditListCount(paramMap);
			page.setCount(queryClaimsAuditListCount==null?0:queryClaimsAuditListCount);
			page.setCurrentPageCount(claimsAuditEntitys.size());
			request.setAttribute("claimsAuditEntitys", claimsAuditEntitys);
			
			checkAuthority(user.getId()+"");
		}
		this.setPageTitle("赔款审核列表");
		
		String res = "reparation_audit";
		return res;
	}
	
	public String getClaimsAuditGroupsByAjax(){
		//claimsAuditGroups = departmentServiceImpl.getDepartTreeByDeptId(2581);
		List<TreeNode<DepartmentEntity>> postSaleDepTree = departmentServiceImpl.getDepartTreeByDeptId(2581);
        List<TreeNode<DepartmentEntity>> vipSalerDepTree = departmentServiceImpl.getDepartTreeByDeptId(2580);
        List<TreeNode<DepartmentEntity>> guoneiDepTree = departmentServiceImpl.getDepartTreeByDeptId(6196);
        claimsAuditGroups = new ArrayList<TreeNode<DepartmentEntity>>();
        claimsAuditGroups.addAll(postSaleDepTree);
        claimsAuditGroups.addAll(vipSalerDepTree);
        claimsAuditGroups.addAll(guoneiDepTree);
		JsonUtil.writeResponse(claimsAuditGroups);
		return "reparation_audit";
	}
	
	/**
	 * 返回处理页面
	 * 
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String doAudit() {
		this.setPageTitle("赔款审核处理");
		String res = "reparation_audit_bill";
		
		List<String> authorityUserIds = DBConfigManager.getConfigAsList("authority.audit.base");
		if(!authorityUserIds.contains(user.getId()+"")){
			request.setAttribute("no","no");
			return res;
		}
		
		checkAuthority(user.getId()+"");
		//页面跟进记录数据
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		List<FollowUpRecordEntity> followUpRecordList = (List<FollowUpRecordEntity>) followUpRecordService.fetchList(paramMap);
		request.setAttribute("followUpRecordList", followUpRecordList);
		
		List<AttachEntity> attachList = attachService.getAttachList(Integer.parseInt(complaintId));
		request.setAttribute("attachList", attachList);
		
		/* 查询历史赔付信息 */
		ComplaintEntity comp = (ComplaintEntity) complaintService.get(complaintId);
		Map<String, Object> queryConMap = new HashMap<String, Object>();
		queryConMap.put("orderId", comp.getOrderId());
		queryConMap.put("contactPhone", comp.getContactPhone());
		
		//查询该投诉单下相关赔付信息,对客或分担金额任意不为0 1. 有订单投诉，根据订单号查询    2.无订单投诉，根据手机号查询
		List<Map<String, Object>> payInfoList = complaintService.getHistoryPayInfo(queryConMap);
		/*if (orderId > 0) {
			payInfoList = complaintService.getPayInfoOnOrder(orderId);
		} else {
			payInfoList = complaintService.getPayInfoOnTel(comp.getContactPhone());
		}*/
		request.setAttribute("payInfoList", payInfoList);
		
		//页面对客解决方案数据
		ComplaintSolutionEntity complaintSolutionEntity = complaintSolutionService.getComplaintSolution(Integer.parseInt(complaintId));
		complaintSolutionEntity=complaintSolutionService.setCardInfo(complaintSolutionEntity);	
		request.setAttribute("complaintSolutionEntity", complaintSolutionEntity);
		//页面对客解决方案赔款审核历史
		paramMap.clear();
		if (complaintSolutionEntity != null) {
			paramMap.put("claimType", ClaimAuditHistory.COMPLAINT_TYPE);
			paramMap.put("foreignId", complaintSolutionEntity.getId());
			String ctSolClaimAuditHistory = claimAuditHistoryService.getHistoryString(paramMap);
			request.setAttribute("ctSolClaimAuditHistory",	ctSolClaimAuditHistory);
		}
		
		//判断收款人是否是出游人
		Map<String, Object> touristMap =new HashMap<String, Object>();
		touristMap.put("orderId", orderId);
		touristMap.put("func", "getTouristInfoByOrder");
		String jsonStr =JSONObject.fromObject(touristMap).toString();
		int flag = 0;
		if(null!=complaintSolutionEntity){
			String receiver="";
			if(complaintSolutionEntity.getPayment()==1){
				receiver= complaintSolutionEntity.getReceiver();//收款人
			}else if(complaintSolutionEntity.getPayment()==2){
				receiver= complaintSolutionEntity.getCollectionUnit();//收款人
			}
			if(receiver!=null){
				List<String> touristList = ComplaintRestClient.queryTourCustomer(jsonStr);
				for(String tourist :touristList){
					if(tourist.equals(receiver.trim())){
						
						flag = 1;
						break;
					}
				}
			}
		}else{
			
			flag = 1;
		}
			
		request.setAttribute("touristFlag", flag);
		//分担方案数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complaintId", complaintId);
		params.put("orderId", orderId);
		ShareSolutionEntity shareSolutionEntity = shareSolutionService.getShareSolution(Integer.parseInt(complaintId));
		request.setAttribute("shareSolutionEntity", shareSolutionEntity);
		//页面分担方案赔款审核历史
		if(shareSolutionEntity != null){
			paramMap.clear();
			paramMap.put("claimType", ClaimAuditHistory.SHARE_TYPE);
			paramMap.put("foreignId", shareSolutionEntity.getId());
			String shareSolClaimAuditHistory = claimAuditHistoryService.getHistoryString(paramMap);
			request.setAttribute("shareSolClaimAuditHistory", shareSolClaimAuditHistory);
		}
		return res;
	}
	
	/**
	 * 审核
	 */
	public String auditPass() {
		
		String flag = request.getParameter("flag");
		Integer complaintId = Integer.valueOf(request.getParameter("complaintNum"));
		int auditFlag = request.getParameter("auditFlag")!=null?Integer.parseInt(request.getParameter("auditFlag")):-1;
		String tFlag = request.getParameter("tFlag");
		Integer num = 0;
		checkAuthority(user.getId()+"");
		if("menu_2".equals(tFlag) && auditFlag==UNTRIAL && !authoritys.contains("1")){
			num=-1;
		}
		if("menu_3".equals(tFlag) && auditFlag==PASS_FIRST_TRIAL && !authoritys.contains("2")){
			num=-1;
		}
		if("menu_4".equals(tFlag) && auditFlag==RETRIAL_ONE && !authoritys.contains("3")){
			num=-1;
		}
		if("menu_5".equals(tFlag) && auditFlag==RETRIAL_TWO && !authoritys.contains("5")){
			num=-1;
		}
		if("menu_6".equals(tFlag) && auditFlag==RETRIAL_THREE && !authoritys.contains("4")){
			num=-1;
		}
		if(num >= 0){
		    info.put("success", true);
		    info.put("msg", "操作成功");
		    
		    String key = flag + complaintId;//防重加锁
		    String value = flag;
		    logger.info("审核操作加锁 key: " + key);
		    boolean lockFlag = MemcachesUtil.add(key, value, 10);
		    
		    if(lockFlag){//没有锁,正常执行
		    	try{
		    		String noteContent = "";
					if ("complaint".equals(flag)) {
						ComplaintSolutionEntity csEntity = complaintSolutionService.getComplaintSolution(complaintId);
						double custTotal = csEntity.getCash() + csEntity.getTouristBook();
						//重复审核校验
						Integer oldAuditFlag = csEntity.getAuditFlag();
						csEntity.setAuditFlag(computeAuditFlag(auditFlag, tFlag, custTotal));
						
						if(oldAuditFlag == csEntity.getAuditFlag()){
						    info.put("success", false);
				            info.put("msg", "对客方案该环节已由他人审核通过，请勿重复提交！");
						}else if(csEntity.getSubmitFlag()!=1){
							info.put("success", false);
				            info.put("msg", "对客方案该环节已由他人退回！");
							return "info";
						}else{
							csEntity.setAuditName(user.getRealName());
							complaintSolutionService.auditComplaintSolution(csEntity,info);
							if(((Boolean) info.get("success"))){
								ClaimAuditHistory auditHistory  = new ClaimAuditHistory();
								auditHistory.setClaimType(ClaimAuditHistory.COMPLAINT_TYPE);
								auditHistory.setForeignId(csEntity.getId());
								auditHistory.setClaimTime(new Date());
								auditHistory.setAssessor(user.getRealName());
								auditHistory.setPhrase(mapAuditFlagToPhrase(auditFlag));
								claimAuditHistoryService.insert(auditHistory);
								
								noteContent = "对客解决方案审核通过";
							}
						}
					} else if ("share".equals(flag)) {
						ShareSolutionEntity ssEntity = shareSolutionService.getShareSolution(complaintId);
						double shareTotal = ssEntity.getTotal();
						//防重复提交校验
						Integer oldAuditFlag = ssEntity.getAuditFlag();
						ssEntity.setAuditFlag(computeAuditFlag(auditFlag, tFlag, shareTotal));
						if(oldAuditFlag == ssEntity.getAuditFlag()){
						    info.put("success", false);
		                    info.put("msg", "分单方案该环节已由他人审核通过，请勿重复提交！");
						}else{
							shareSolutionService.auditShareSolution(ssEntity, user.getId());
							
							ClaimAuditHistory auditHistory  = new ClaimAuditHistory();
							auditHistory.setClaimType(ClaimAuditHistory.SHARE_TYPE);
							auditHistory.setForeignId(ssEntity.getId());
							auditHistory.setClaimTime(new Date());
							auditHistory.setAssessor(user.getRealName());
							auditHistory.setPhrase(mapAuditFlagToPhrase(auditFlag));
							claimAuditHistoryService.insert(auditHistory);
							
							noteContent = "分担方案审核通过";
						}
					} else if ("returnComplaint".equals(flag)) {
						String backMsg = request.getParameter("backMsg"); // 退回原因
						ComplaintSolutionEntity csEntity = complaintSolutionService.getComplaintSolution(complaintId);
						complaintSolutionService.returnComplaintSolution(complaintId, Constans.AUDIT_BACK, backMsg);
						
						ClaimAuditHistory auditHistory  = new ClaimAuditHistory();
						auditHistory.setClaimType(ClaimAuditHistory.COMPLAINT_TYPE);
						auditHistory.setForeignId(csEntity.getId());
						auditHistory.setClaimTime(new Date());
						auditHistory.setAssessor(user.getRealName());
						auditHistory.setPhrase(mapAuditFlagToPhrase(auditFlag));
						claimAuditHistoryService.insert(auditHistory);
						
						noteContent = "对客解决方案退回，退回原因："+backMsg;
					} else if ("returnShare".equals(flag)) {
						ShareSolutionEntity ssEntity = shareSolutionService.getShareSolution(complaintId);
						Integer oldSsEntityId = ssEntity.getId();
						String backMsg = request.getParameter("backMsg"); // 退回原因
						shareSolutionService.returnShareSolution(ssEntity, backMsg);
						
						ClaimAuditHistory auditHistory  = new ClaimAuditHistory();
						auditHistory.setClaimType(ClaimAuditHistory.SHARE_TYPE);
						auditHistory.setForeignId(oldSsEntityId);
						auditHistory.setClaimTime(new Date());
						auditHistory.setAssessor(user.getRealName());
						auditHistory.setPhrase(mapAuditFlagToPhrase(auditFlag));
						claimAuditHistoryService.insert(auditHistory);
						
						noteContent = "分担方案退回："+backMsg;
					}
					
					if(!"".equals(noteContent)){
						complaintFollowNoteService.addFollowNote(complaintId, user.getId(), user.getRealName(), noteContent,0,"审核");
						logger.info("auditPass() invoked {complaintId="+complaintId+",operator="+user.getRealName()+",noteContent="+noteContent+"}");
					}
				}catch (Exception e) {
					logger.error("审核失败", e);
					info.put("success", false);
                    info.put("msg", "操作失败！");
				}finally{
					logger.info("删除key: " + key);
					MemcachesUtil.delete(key);
				}
		    }else{
	    		info.put("success", false);
                info.put("msg", "方案该环节已由他人审核通过，请勿重复提交！");
		    }
		} else {
		    info.put("success", false);
		    info.put("msg", "权限不足");
		}

		return "info";
	}

	/**
	 * @param auditFlag
	 * @return
	 */
	private Integer mapAuditFlagToPhrase(int auditFlag) {
		Integer phrase = 0 ;
		phrase = auditFlag+1;
		if(auditFlag ==5){
			phrase = auditFlag;
		}
		return phrase;
	}

	private int computeAuditFlag(int auditFlag, String tFlag, double total) {

		if ("menu_2".equals(tFlag) && total <= 5000) {
			auditFlag = PASS_TRIAL;
		} else if ("menu_3".equals(tFlag) && total <= 20000) {
			auditFlag = PASS_TRIAL;
		} else if ("menu_4".equals(tFlag) && total <= 100000) {
			auditFlag = PASS_TRIAL;
//		} else if ("menu_5".equals(tFlag) && total <= 1000000) {
//			auditFlag = PASS_TRIAL;
		} else if ("menu_6".equals(tFlag) && total > 100000) {
			auditFlag = PASS_TRIAL;
		}else {
			if("menu_4".equals(tFlag)){
				auditFlag = RETRIAL_THREE;
			}else{
				auditFlag += 1;
			}
		}
		return auditFlag;
	}
	
	/**
	 * 返回处理页面
	 * 
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String queryMoney() {
		this.setPageTitle("赔款审核处理");
		String res = "amount_summary";
		//页面跟进记录数据
		List<ComplaintFollowNoteEntity> followNoteList = complaintFollowNoteService.getNoteReByComplaintId(complaintId);
		request.setAttribute("followNoteList", followNoteList);
		//页面对客解决方案数据
		ComplaintSolutionEntity complaintSolutionEntity = complaintSolutionService.getComplaintSolutionBycompalintId(Integer.parseInt(complaintId));
		request.setAttribute("complaintSolutionEntity", complaintSolutionEntity);
		//分担方案数据
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complaintId", complaintId);
		ShareSolutionEntity shareSolutionEntity = shareSolutionService.getShareSolution(Integer.parseInt(complaintId));
		request.setAttribute("shareSolutionEntity", shareSolutionEntity);
		double sum = 0.0;
		if(shareSolutionEntity != null){
			for(SupportShareEntity entity : shareSolutionEntity.getSupportShareList()){
				sum+=entity.getNumber();
			}
		}
		request.setAttribute("sum", sum);
		return res;
	}
	
	//判断权限
	private List<String> checkAuthority(String id) {
	    List<String> step1List = DBConfigManager.getConfigAsList("authority.audit.step1");// 初审权限
	    List<String> step21List = DBConfigManager.getConfigAsList("authority.audit.step21");//复审一
	    List<String> step22List = DBConfigManager.getConfigAsList("authority.audit.step22");//复审二
	    List<String> step23List = DBConfigManager.getConfigAsList("authority.audit.step23");//复审3
	    List<String> step3List = DBConfigManager.getConfigAsList("authority.audit.step3");//终审
	    List<String> superList = DBConfigManager.getConfigAsList("authority.audit.super");//超级
	    
	    List<String> ajustShareList = DBConfigManager.getConfigAsList("authority.agency.confirm");
	    
		if (step1List.contains(id)) { // 9406 张静18,5158 陆晓怡 ,5538 王程 ,1220 曹烨,1908 马奔 ,5730薛琦,318 姚爱玲,9368 桂洋,8945 张韡5,11738 玄志凯,8388 丁琛2,12826 祁慧
			authoritys.add("1");// 初审权限
		}
		if (step21List.contains(id)) { //5148 王双宁, 186 陈长庆,1076 戴周峰,12116 方勇2
			authoritys.add("2");// 复审1权限
		}
		if (step22List.contains(id)) {  // 14552 金光
			authoritys.add("3");// 复审2权限
		}
		if (step23List.contains(id)) { // 2762常静勇
			authoritys.add("5");// 复审3权限
		}
		if (step3List.contains(id)) { // 16于敦德,19严海峰
			authoritys.add("4");// 终审权限
		}
		
		if(superList.contains(id)){ //超级权限
		    authoritys.addAll(Arrays.asList("1 2 3 5 4".split(" ")));
		}
		return authoritys;
	}

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public List<String> getDealDepartments() {
        return DBConfigManager.getConfigAsList("sys.dealDepart");
    }

    public void setDealDepartments(List<String> dealDepartments) {
        this.dealDepartments = dealDepartments;
    }

	public List<TreeNode<DepartmentEntity>> getClaimsAuditGroups() {
		return claimsAuditGroups;
	}

	public void setClaimsAuditGroups(List<TreeNode<DepartmentEntity>> claimsAuditGroups) {
		this.claimsAuditGroups = claimsAuditGroups;
	}
}
