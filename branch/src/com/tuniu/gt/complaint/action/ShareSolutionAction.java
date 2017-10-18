package com.tuniu.gt.complaint.action;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;
import tuniu.frm.service.Constant;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.AgencyDto;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.OrderEntity;
import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.complaint.service.IQualityToolService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.BasicHttp;
import com.tuniu.gt.toolkit.ExportPdfUtil;
import com.tuniu.gt.toolkit.lang.CollectionUtil;


/**
 * 分担方案后台处理action
 */
@Service("complaint_action-share_solution")
@Scope("prototype")
public class ShareSolutionAction extends FrmBaseAction<IShareSolutionService, ShareSolutionEntity> {
	
	private static final long serialVersionUID = 1L;

	private static Logger logger = Logger.getLogger(ShareSolutionAction.class);
	
	private int succFlag;
	
	private int adjustFlag;

	public int getSuccFlag() {
		return succFlag;
	}

	public void setSuccFlag(int succFlag) {
		this.succFlag = succFlag;
	}
	
	public int getAdjustFlag() {
		return adjustFlag;
	}

	public void setAdjustFlag(int adjustFlag) {
		this.adjustFlag = adjustFlag;
	}

	//引入投诉事宜service
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_reason")
	private IComplaintReasonService reasonService;
	
	//投诉解决方案service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_solution")
	private IComplaintSolutionService compSolutionService;
	
	public ShareSolutionAction() {
		setManageUrl("share_solution");
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-share_solution")
	public void setService(IShareSolutionService service) {
		this.service = service;
	};
	
	//用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	//引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;

	//质量工具service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-quality_tool")
	private IQualityToolService qualityToolService;
	
	@Autowired
    @Qualifier("tspService")
    private IComplaintTSPService tspService;
	
	public String execute() {
		this.setPageTitle("分担方案");

		Integer complaintId = Integer.valueOf(request.getParameter("complaintId"));
		Integer orderId = Integer.valueOf(request.getParameter("orderId"));
		Integer createType = Integer.valueOf(request.getParameter("createType"));
		
		entity = service.getShareSolution(complaintId);
		if (null != entity) {
			if (1 == entity.getSubmitFlag()) {
				jspTpl = "share_solution_info";
			} else { // 未提交，显示修改页面
				jspTpl = "share_solution_update";
			}
		} else { // 不存在，显示新增页面
			jspTpl = "share_solution_add";
		}
		
		if (adjustFlag > 0) {
			jspTpl = "share_solution_update";
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complaintId", complaintId);
		ComplaintSolutionEntity compEnt = (ComplaintSolutionEntity) compSolutionService.fetchOne(params);
		double customerTotal = 0;
		if (null != compEnt && 1 == compEnt.getSubmitFlag()) {
			customerTotal = compEnt.getCash() + compEnt.getTouristBook();
		}
		request.setAttribute("customerTotal", customerTotal);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", complaintId);
		ComplaintEntity complaint = (ComplaintEntity) complaintService.fetchOne(param);
		request.setAttribute("complaint", complaint);
		
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("orderId", orderId);
		request.setAttribute("userId", user.getId());
		request.setAttribute("supportShareListFirst", getAgencyInfo(orderId)); //从boss中取出采购单对应供应商信息,团队游不读取供应商信息
		request.setAttribute("toolList", qualityToolService.getListInUse()); //所有启用中的质量工具
		
		return jspTpl;
	}
	
	private List<SupportShareEntity> getAgencyInfo(int orderId) {
        OrderEntity orderEntity = complaintService.queryNewOrderInfo(orderId+"");
        List<AgencyDto>  agencyList = orderEntity.getAgencyList();
        List<SupportShareEntity> sEList = new ArrayList<SupportShareEntity>();
        if(CollectionUtil.isNotEmpty(agencyList)){
            for(AgencyDto agencyDto : agencyList) {
                SupportShareEntity supportShareEntity = new SupportShareEntity();
                supportShareEntity.setCode(agencyDto.getAgencyId());
                supportShareEntity.setName(agencyDto.getAgencyName());
                sEList.add(supportShareEntity);
            }
        }
        
        return sEList;
    }
	
	public String addShare() {
		logger.info("addShare Begin, Order Id is " + entity.getOrderId() + ", Complaint Id is " + entity.getComplaintId());
		
		service.saveShareSolution(entity);
		
		if (1 == entity.getSaveOrSubmit()) {
			request.setAttribute("pageInfo", "submitConfirm");
			jspTpl = "share_solution_info";
		} else {
			succFlag = 1;
			jspTpl = "share_solution_add";
		}
		
		complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), 
				Constans.SAVE_SHARE_SOLUTION, 0, Constans.SAVE_SHARE_SOLUTION);
		
		logger.info("addShare End, Order Id is " + entity.getOrderId() + ", Complaint Id is " + entity.getComplaintId());
		
		return jspTpl;
	}
	
	public String updateShare() {
		logger.info("updateShare Begin, Order Id is " + entity.getOrderId() + ", Complaint Id is " + entity.getComplaintId());
		
		service.updateShareSolution(entity);
		
		if (1 == entity.getSaveOrSubmit()) {
			request.setAttribute("pageInfo", "submitConfirm");
			jspTpl = "share_solution_info";
		} else {
			succFlag = 1;
			jspTpl = "share_solution_update";
		}
		
		complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), 
				Constans.SAVE_SHARE_SOLUTION, 0, Constans.SAVE_SHARE_SOLUTION);
		
		logger.info("updateShare End, Order Id is " + entity.getOrderId() + ", Complaint Id is " + entity.getComplaintId());
		
		return jspTpl;
	}
	
	public String submitShare() {
		logger.info("submitShare Begin, Complaint Id is " + entity.getComplaintId());
		ShareSolutionEntity sse = (ShareSolutionEntity) service.get(entity.getId());
		sse.setSubmitFlag(entity.getSubmitFlag());
		sse.setAuditFlag(entity.getAuditFlag());
		sse.setDealId(user.getId());
		sse.setDealName(user.getRealName());
		sse.setSubmitTime(new Date());
		service.submitShareSolution(sse);
		
		complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), 
				Constans.SUBMIT_SHARE_SOLUTION, 0, Constans.SUBMIT_SHARE_SOLUTION);
		
		succFlag = 1;
		
		logger.info("submitShare End, Complaint Id is " + entity.getComplaintId());
		
		return "share_solution_info";
	}
	
	public String adjustShare() {
		logger.info("adjustShare Begin, Order Id is " + entity.getOrderId() + ", Complaint Id is " + entity.getComplaintId());
		entity.setDealId(user.getId());
		entity.setDealName(user.getRealName());
		entity.setSubmitFlag(1);
		entity.setAuditFlag(4);
		entity.setSubmitTime(new Date());
		service.adjustShareSolution(entity, user.getId());
		
		complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), 
				Constans.ADJUST_SHARE_SOLUTION, 0, Constans.ADJUST_SHARE_SOLUTION);
		
		succFlag = 1;
		
		logger.info("adjustShare End, Order Id is " + entity.getOrderId() + ", Complaint Id is " + entity.getComplaintId());
		
		entity = null;
		
		return "share_solution_update";
	}
	
	/**
	 * 执行确认 根据条件进行搜索
	 * 
	 * @return
	 */
	public String confirm() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		String agencyName = request.getParameter("search.agencyName");
		if(agencyName != null && !agencyName.equals("")){
			//从ct_share_solution表获取该供应商对应的share_id
			//String ids = supportShareService.getShareIdsByAgencyName(agencyName);
			//paramMap.put("ids",ids);
		}
		paramMap.putAll(search);
		String confirm = request.getParameter("search.confirm");
		if(confirm == null){
			paramMap.put("confirm","2");
		}
		request.setAttribute("search", search);
		// 调入分页，继承父类的默认方法即可
		jspTpl = "confirm";
		super.setDefaultTpl(jspTpl);
		return super.execute(paramMap);
	}
	
	/**
	 * 点击确认按钮触发的ajax时间后台处理
	 * 其中type 1表示处理人员确定， 2表示供应商确认，3表示员工确认
	 * @return 1
	 */
	public String userConfirm() {

		String id = request.getParameter("id");
		String type = request.getParameter("type");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String noteContent = "";
		int typeInt = 0;
		if (type != null) {
			typeInt = Integer.valueOf(type);
		}

		switch (typeInt) {
		case 0:
			break;
		case 1:
			paramMap.put("confirm", 1);
			noteContent = Constans.COMPLAINT_SHARE_CONFIRM; 
			break;
		case 2:
			paramMap.put("supplierMark", 1);
			noteContent = Constans.SUPPLY_SHARE_CONFIRM;
			break;
		case 3:
			paramMap.put("employee", 1);
			noteContent = Constans.EMPLOYEE_SHARE_CONFIRM;
			break;
		}

		if (typeInt > 0 && id != null) {
			paramMap.put("id", id);
			paramMap.put("updateTime", new Date());
			service.update(paramMap);
			
			//添加有效操作记录
			complaintFollowNoteService.addFollowNote(Integer.parseInt(id), user.getId(), user.getRealName(), noteContent,0,"确认分担");
		}
		
		return "share_solution_add";
	}

	/**
	 * 显示供应商赔偿通知单模板页面
	 * @return
	 */
	public String refundNoticeView(){
		this.setPageTitle("赔款通知单");
		String complaintId = request.getParameter("complaintId");
		String agencyId = request.getParameter("agencyId");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("complaintId", complaintId);
		paramMap.put("agencyId", agencyId);
		Map<String, Object> payoutDetailMap = service.getAgencyPayInfoDetail(paramMap);
		
		request.setAttribute("payoutDetailMap", payoutDetailMap);
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("agencyId", agencyId);
		
		return "refund_notice";
	}
	
	/**
	 * 根据供应商赔偿通知单模板生成并下载pdf格式供应商赔偿通知单
	 */
	public void refundNoticeDownload(){
		
		String complaintId = request.getParameter("complaintId");
		String agencyId = request.getParameter("agencyId");
		
		String pdfUrlScheme = Constant.CONFIG.getProperty("pdf_url_scheme");
		String pdfUrlHost = Constant.CONFIG.getProperty("pdf_url_host");
		String downloadRefundUrl = Constant.CONFIG.getProperty("download_refund");
		int pdfPort = Integer.parseInt(Constant.CONFIG.getProperty("pdf_port"));
		String pdfServiceUrlPath = Constant.CONFIG.getProperty("pdf_service_url_path");
		
		String sendUrl = downloadRefundUrl+"complaintId="+complaintId+"&agencyId="+agencyId;
		//String sendUrl = "http://localhost:8080/ssi/complaint/action/share_solution-refundNoticeView?complaintId="+complaintId+"&agencyId="+agencyId;
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("sendUrl", sendUrl);
		String returnStr = "";
		try {
			returnStr = BasicHttp.execute(map, pdfUrlScheme, pdfUrlHost, pdfPort, pdfServiceUrlPath);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		JSONObject returnJson = null;
		String pdfUrl = "";
		if(!"fail".equals(returnStr)&&returnStr!=null) {
			returnJson = JSONObject.fromObject(returnStr);
		}
		int retFlag=returnJson.has("retFlag")?returnJson.getInt("retFlag"):-1;
		if(retFlag==0) {
			pdfUrl = returnJson.getString("pdfUrl") ;			
		}
		ExportPdfUtil.download(ServletActionContext.getResponse(), pdfUrl);
	}
	
	/**
	 * 根据jsp模板替换参数，生成html
	 * @param map 需要替换的参数
	 * @param templatePath  jsp模板路径
	 * @return 生成的html文件名
	 * @throws IOException
	 */
	private String jspToHtml(Map<String, Object> map, String templatePath) throws IOException{
		// 模板路径
		String filePath = templatePath
				+ "download/jsp/refund_notice_template.jsp";
		//System.out.print(filePath);
		String templateContent = "";
		FileInputStream fileinputstream = new FileInputStream(filePath);// 读取模板文件
		int lenght = fileinputstream.available();
		byte bytes[] = new byte[lenght];
		fileinputstream.read(bytes);
		fileinputstream.close();
		templateContent = new String(bytes, "UTF-8");
		//System.out.print(templateContent);

		Set<String> key = map.keySet();
		for (Iterator<String> it = key.iterator(); it.hasNext();) {
			String index = (String) it.next();
			templateContent = templateContent.replaceAll(index, map.get(index)
					.toString());// 替换掉模板中相应的地方
		}

		//System.out.print(templateContent);

		// 根据时间得文件名
		String fileName = "refund_notice_"
				+ (new java.text.SimpleDateFormat("yyyyMMddHHmmss"))
						.format(new Date()) + ".html";
		FileOutputStream fileoutputstream = new FileOutputStream(templatePath
				+ "download/" + fileName);// 建立文件输出流
		Writer out = new OutputStreamWriter(fileoutputstream, "utf-8");
		//System.out.print("文件输出路径:");
		//System.out.print(templatePath + "download/" + fileName);
		//byte tag_bytes[] = templateContent.getBytes();
		out.write(templateContent);
		out.close();
		fileoutputstream.close();
		
		
		return fileName;

	}
	
	/**
	 * 根据html生成pdf，并提供下载
	 * @param fileName  html文件名
	 */
	private void htmlToPdf(String fileName){
		String pdfUrlScheme = Constant.CONFIG.getProperty("pdf_url_scheme");
		String pdfUrlHost = Constant.CONFIG.getProperty("pdf_url_host");
		int pdfPort = Integer.parseInt(Constant.CONFIG.getProperty("pdf_port"));
		String pdfServiceUrlPath = Constant.CONFIG.getProperty("pdf_service_url_path");
		String sendUrl = "http://"+ Constant.CONFIG.getProperty("file_url") + "download/" + fileName;
		//String sendUrl = "http://192.168.80.44:8080/ssi/download/" + fileName;
		Map<String,String> map = new LinkedHashMap<String, String>();
		map.put("sendUrl", sendUrl);
		String returnStr = BasicHttp.execute(map, pdfUrlScheme, pdfUrlHost, pdfPort, pdfServiceUrlPath);
		JSONObject returnJson = null;
		String pdfUrl="";
		if(!"fail".equals(returnStr)&&returnStr!=null){
			returnJson = JSONObject.fromObject(returnStr);
		}
		int retFlag=returnJson.has("retFlag")?returnJson.getInt("retFlag"):-1;
		if(retFlag==0){
			pdfUrl = returnJson.getString("pdfUrl") ;			
		}
		ExportPdfUtil.download(ServletActionContext.getResponse(), pdfUrl);
	}
	
	/**
	 * 根据供应商品牌名检查供应商是否存在，若存在，返回id
	 * @return
	 */
	public String checkSupplier(){
		JSONObject result=new JSONObject();
		String agencyName = request.getParameter("name").replaceAll("<>", "&");
		Integer complaintId = new Integer(request.getParameter("complaintId"));
		
		//查询投诉单，判断是否为新分销，来区分调用的TSP服务，0 正常    1分销
		int agencyId = tspService.checkAgencyInfo(agencyName, complaintId);
		
		if(agencyId>0) {
			result = tspService.getAgencyInfo(String.valueOf(agencyId));
		}
		result.put("agencyId", agencyId);
		try {
			 //获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图    
	        HttpServletResponse response = ServletActionContext.getResponse();    
	        //设置字符集    
	        response.setContentType("text/plain");//设置输出为文字流  
	        response.setCharacterEncoding("UTF-8");    
	        PrintWriter out;
			out = response.getWriter();
			 //直接输出响应的内容    
	        out.println(result.toString());    
	        out.flush();    
	        out.close();    
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return "share_solution_add";
	}
	
	public String getNbFlag() {
		int agencyId = Integer.parseInt(request.getParameter("agencyId"));
		Integer complaintId = new Integer(request.getParameter("complaintId"));
		
		int nbFlag = complaintService.getNbFlag(agencyId, complaintId);
		
		JSONObject result = new JSONObject();
		result.put("nbFlag", nbFlag);
		try {
			 //获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图    
	        HttpServletResponse response = ServletActionContext.getResponse();    
	        //设置字符集    
	        response.setContentType("text/plain");//设置输出为文字流  
	        response.setCharacterEncoding("UTF-8");    
	        PrintWriter out = response.getWriter();
			 //直接输出响应的内容    
	        out.println(result.toString());    
	        out.flush();    
	        out.close();    
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
		return "share_solution_add";
	}
	
    /**
     * 判断添加的员工承担责任人是否存在，若存在，返回1,不存在返回0。
     * @return
     */
    public String checkEmploy(){
        String employName = request.getParameter("name");
        int isExists = userService.checkEmployInfo(employName);
        try {
            //获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图    
            HttpServletResponse response = ServletActionContext.getResponse();
            //设置字符集    
            response.setContentType("text/plain");//设置输出为文字流  
            response.setCharacterEncoding("UTF-8");
            PrintWriter out;
            out = response.getWriter();
            //直接输出响应的内容    
            out.println(isExists);
            out.flush();
            out.close();
        }
        catch (IOException e) {
        	logger.error(e.getMessage(), e);
        }

        return "share_solution_add";
    }
    
    public String getComplaintInfo(){
    	
        String complaintId = request.getParameter("complaintId");
        Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("complaintId", complaintId);
		List<ComplaintReasonEntity> complaintReason = (List<ComplaintReasonEntity>) reasonService.fetchList(paramMap);
        try {
            //获取原始的PrintWriter对象,以便输出响应结果,而不用跳转到某个试图    
            HttpServletResponse response = ServletActionContext.getResponse();
            //设置字符集    
            response.setContentType("text/plain");//设置输出为文字流  
            response.setCharacterEncoding("UTF-8");
            PrintWriter out;
            out = response.getWriter();
            //直接输出响应的内容    
            List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
            for (ComplaintReasonEntity comReason : complaintReason) {
				String complaintInfo = comReason.getTypeDescript()+";"+comReason.getDescript();
				Map<String, Object> smap = new HashMap<String, Object>();
				smap.put("complaintInfo", complaintInfo);
				list.add(smap);
			}
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("json", list);
            out.println(JSONObject.fromObject(map));
            
            out.flush();
            out.close();
        }
        catch (IOException e) {
        	logger.error(e.getMessage(), e);
        }

        return "share_solution_add";
    }
    
}
