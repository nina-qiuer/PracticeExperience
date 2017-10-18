package com.tuniu.gt.complaint.action;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.service.IComplaintCollectService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.vo.ComplaintCollectListVo;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DataExportExcel;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.warning.common.Page;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

/**
 * 投诉质检单汇总统计
 * @author chenhaitao
 * 20150422
 */
@Service("complaint_action-complaintCollect_stat")
@Scope("prototype")
public class ComplaintCollectStatAction extends FrmBaseAction<IComplaintService, ComplaintEntity> {

	private static final long serialVersionUID = 1L;
	
	//private static Logger logger = Logger.getLogger(ComplaintCollectStatAction.class);
	
	private String buildBeginDate;//投诉时间范围
	
	private String buildEndDate;//投诉时间范围

	private String finishBeginDate;//质检完成时间范围
	
	private String finishEndDate;//质检完成时间范围
	
	private String qcPersonName;//质检人
	
	private String bigClassId;//问题大类型
	
	private String improverName;//改进人
	
	private String scoreLevel;//记分等级
	
	private Page page;
	
	public ComplaintCollectStatAction() {
		setManageUrl("complaintCollect_stat");
	}
	
	@Autowired
	private IComplaintCollectService complaintCollectService;
	
	/**
	 * 打开投诉质检汇总列表主页面
	 */
	public String execute() {
		
		if(page == null){
			page = new Page();
			page.setPageSize(30);
		}
		
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.add(Calendar.MONTH, -1);
		String m1 = ((c1.get(Calendar.MONTH) + 1) < 10 ? "0" + (c1.get(Calendar.MONTH) + 1) : String.valueOf(c1.get(Calendar.MONTH) + 1));
		String m2 = ((c2.get(Calendar.MONTH) + 1) < 10 ? "0" + (c2.get(Calendar.MONTH) + 1) : String.valueOf(c2.get(Calendar.MONTH) + 1));
		String d1 = c1.get(Calendar.DATE) < 10 ? "0" + c1.get(Calendar.DATE) : String.valueOf(c1.get(Calendar.DATE));
		String d2 = c2.get(Calendar.DATE) < 10 ? "0" + c2.get(Calendar.DATE) : String.valueOf(c2.get(Calendar.DATE));
		String defStartDate = c1.get(Calendar.YEAR) + "-" + m1 + "-" + d1;
		String defEndDate = c2.get(Calendar.YEAR) + "-" + m2 + "-" + d2;
		if (StringUtil.isEmpty(buildBeginDate) && StringUtil.isEmpty(buildEndDate)) {
			buildBeginDate = defStartDate;
			buildEndDate = defEndDate;
		}
		
		String finishStartDate = DateUtil.formatDate(c2.getTime());
		String finishEnDate = DateUtil.formatDate(c2.getTime());
		if (StringUtil.isEmpty(finishBeginDate) && StringUtil.isEmpty(finishEndDate)) {
			finishBeginDate = finishStartDate;
			finishEndDate = finishEnDate;
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("finishBeginDate", finishBeginDate +" 00:00:00");
		paramMap.put("finishEndDate", finishEndDate+" 23:59:59");
		paramMap.put("buildBeginDate", buildBeginDate+" 00:00:00");
		paramMap.put("buildEndDate", buildEndDate+" 23:59:59");
		paramMap.put("qcPersonName", qcPersonName);
		
		List<String> classIds = new ArrayList<String>();
      
		if("".equals(bigClassId)||null ==bigClassId){
			
			classIds.add("1");// 执行问题
			classIds.add("2");// 供应商问题
			classIds.add("4");// 不可抗力
			classIds.add("5");// 低满意度
		     
		}else{
			
			classIds.add(bigClassId);
			
		}
		paramMap.put("bigClassIds", classIds);
		
		
		List<String> scoreLevels = new ArrayList<String>();
		if("".equals(scoreLevel)||null==scoreLevel){
			
			scoreLevels.add("一级甲等");// 一级甲等
			scoreLevels.add("一级乙等");// 一级乙等
			scoreLevels.add("一级丙等");// 一级丙等
			scoreLevels.add("二级");// 二级
			scoreLevels.add("三级");// 三级
			scoreLevels.add("一级乙等-红线");// 一级乙等-红线
			scoreLevels.add("一级乙等-非红线");// 一级乙等-非红线
			scoreLevels.add("无");// 无的字段
			scoreLevels.add("");// 空的字段
		     
		}else{
			
			scoreLevels.add(scoreLevel);
			
		}
		paramMap.put("scoreLevels", scoreLevels);
		paramMap.put("improverName", improverName);
		paramMap.put("dataLimitStart", (page.getCurrentPage()-1)*page.getPageSize());
		paramMap.put("dataLimitEnd", page.getPageSize());
		
		List<ComplaintCollectListVo> collectList  =  complaintCollectService.getCollectList(paramMap);
		int collectCount = complaintCollectService.getCollectListCount(paramMap);
		page.setCount(collectCount);
		page.setCurrentPageCount(collectList==null?0:collectList.size());
		request.setAttribute("collectList", collectList==null?"":collectList);
		
		return "complaintCollect_list";
	}
	
	
	public String getExportDataTotal() {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("finishBeginDate", finishBeginDate +" 00:00:00");
		paramMap.put("finishEndDate", finishEndDate+" 23:59:59");
		paramMap.put("buildBeginDate", buildBeginDate+" 00:00:00");
		paramMap.put("buildEndDate", buildEndDate+" 23:59:59");
		paramMap.put("qcPersonName", qcPersonName);
		paramMap.put("improverName", improverName);
		List<String> classIds = new ArrayList<String>();
	      
		if("".equals(bigClassId)||null ==bigClassId){
			
			classIds.add("1");// 执行问题
			classIds.add("2");// 供应商问题
			classIds.add("4");// 不可抗力
			classIds.add("5");// 低满意度
		     
		}else{
			
			classIds.add(bigClassId);
			
		}
		paramMap.put("bigClassIds", classIds);
		
		List<String> scoreLevels = new ArrayList<String>();
		if("".equals(scoreLevel)||null==scoreLevel){
			
			scoreLevels.add("一级甲等");// 一级甲等
			scoreLevels.add("一级乙等");// 一级乙等
			scoreLevels.add("一级丙等");// 一级丙等
			scoreLevels.add("二级");// 二级
			scoreLevels.add("三级");// 三级
			scoreLevels.add("一级乙等-红线");// 一级乙等-红线
			scoreLevels.add("一级乙等-非红线");// 一级乙等-非红线
			scoreLevels.add("无");// 无的字段
			scoreLevels.add("");// 空的字段
		     
		}else{
			
			scoreLevels.add(scoreLevel);
			
		}
		paramMap.put("scoreLevels", scoreLevels);
		int collectCount = complaintCollectService.getCollectListCount(paramMap);
		CommonUtil.writeResponse(collectCount);
		return "complaintCollect_list";
	}
	
	/**
	 * 导出数据
	 * @return String 页面
	 */
	public String exports() {
		/* 查询数据 */
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("finishBeginDate", finishBeginDate +" 00:00:00");
		paramMap.put("finishEndDate", finishEndDate+" 23:59:59");
		paramMap.put("buildBeginDate", buildBeginDate+" 00:00:00");
		paramMap.put("buildEndDate", buildEndDate+" 23:59:59");
		paramMap.put("qcPersonName", qcPersonName);
		paramMap.put("improverName", improverName);
		List<String> classIds = new ArrayList<String>();
	      
		if("".equals(bigClassId)||null ==bigClassId){
			
			classIds.add("1");// 执行问题
			classIds.add("2");// 供应商问题
			classIds.add("4");// 不可抗力
			classIds.add("5");// 低满意度
		     
		}else{
			
			classIds.add(bigClassId);
			
		}
		paramMap.put("bigClassIds", classIds);
		
		List<String> scoreLevels = new ArrayList<String>();
		if("".equals(scoreLevel)||null==scoreLevel){
			
			scoreLevels.add("一级甲等");// 一级甲等
			scoreLevels.add("一级乙等");// 一级乙等
			scoreLevels.add("一级丙等");// 一级丙等
			scoreLevels.add("二级");// 二级
			scoreLevels.add("三级");// 三级
			scoreLevels.add("一级乙等-红线");// 一级乙等-红线
			scoreLevels.add("一级乙等-非红线");// 一级乙等-非红线
			scoreLevels.add("无");// 无的字段
			scoreLevels.add("");// 空的字段
		     
		}else{
			
			scoreLevels.add(scoreLevel);
			
		}
		paramMap.put("scoreLevels", scoreLevels);
		List<ComplaintCollectListVo> collectList  =  complaintCollectService.getCollectList(paramMap);
		List<String> complaintIds = new ArrayList<String>();
		for(int i=0;i<collectList.size();i++){
			ComplaintCollectListVo vo =collectList.get(i);
			complaintIds.add(vo.getComplaintId());
		}
		
		Map<String, Object> map= new HashMap<String, Object>();
		map.put("flag", 1);
		map.put("complaintIds", complaintIds);
		List<ComplaintCollectListVo>  descriptList =new ArrayList<ComplaintCollectListVo>();
		if(complaintIds.size()>0){
			
			 descriptList = complaintCollectService.queryDescript(map);
		}
		
		List<ComplaintCollectListVo> list =new ArrayList<ComplaintCollectListVo>();
		for(int i=0;i<collectList.size();i++){
			
			ComplaintCollectListVo vo =collectList.get(i);
			for(int j=0;j<descriptList.size();j++){
				ComplaintCollectListVo descriptVo =descriptList.get(j);
				if(vo.getComplaintId().equals(descriptVo.getComplaintId())){
					
					vo.setTypeDescript(descriptVo.getTypeDescript());
				}
				
			}
			list.add(vo);
			
		}
		
		//获取责任人
		List<String> questionIds = new ArrayList<String>();
		for(int i=0;i<collectList.size();i++){
			ComplaintCollectListVo vo =collectList.get(i);
			questionIds.add(vo.getQuestionId());
		}    
		
		Map<String, Object> qMap= new HashMap<String, Object>();
		qMap.put("flag", 0);
		qMap.put("questionIds", questionIds);
		List<ComplaintCollectListVo>  trackertList =new ArrayList<ComplaintCollectListVo>();
		if(questionIds.size()>0){
			
			trackertList = complaintCollectService.queryTracker(qMap);
		}
		List<ComplaintCollectListVo> qlist =new ArrayList<ComplaintCollectListVo>();
		for(int m=0;m<list.size();m++){
			
			ComplaintCollectListVo vo = list.get(m);
			for(int n=0;n<trackertList.size();n++){
				ComplaintCollectListVo trackerVo = trackertList.get(n);
				if(vo.getQuestionId().equals(trackerVo.getQuestionId())){
					
					vo.setPosition(trackerVo.getPosition()==null?"":trackerVo.getPosition());
				  	vo.setImproverName(trackerVo.getImproverName()==null?"":trackerVo.getImproverName());
				  	vo.setResponsiblePerson(trackerVo.getResponsiblePerson()==null?"":trackerVo.getResponsiblePerson());
					vo.setResponsibilityName(trackerVo.getResponsibilityName()==null?"":trackerVo.getResponsibilityName());
					vo.setRespSecondName(trackerVo.getRespSecondName()==null?"":trackerVo.getRespSecondName());
					vo.setRespThirdName(trackerVo.getRespThirdName()==null?"":trackerVo.getRespThirdName());
					vo.setResponsibility(trackerVo.getResponsibility()==null?"":trackerVo.getResponsibility());
				}
				
			}
			qlist.add(vo);
			
		}
		
		
		List<Map<String, Object>> data = transListToMap(qlist);
		
		HttpServletResponse response = ServletActionContext.getResponse();
		String fileName = "质检责任单统计" + DateUtil.formatDate(new Date(), "yyyyMMddhhmmss") + ".xls";
		DataExportExcel.exportExcelCollect(getColumnList(), data, fileName, response);
		return "complaintCollect_list";
	}
		
	/**
	 * 设置列标题
	 * @param row
	 * @param cellStyle
	 */
	private List<String> getColumnList() {
        
		List<String> listColumn = new ArrayList<String>();
		listColumn.add("质检完成日期");
		listColumn.add("订单号");
		listColumn.add("投诉时间");
		listColumn.add("投诉完成时间");
		listColumn.add("投诉号");
		listColumn.add("产品类型");
		listColumn.add("投诉等级");
		listColumn.add("归来日期");
		listColumn.add("出发地");
		listColumn.add("线路名称");
		listColumn.add("产品经理");
		listColumn.add("产品专员");
		listColumn.add("客服经理");
		listColumn.add("反映问题");
		listColumn.add("对客解决方案");
		listColumn.add("质检结论");
		listColumn.add("责任人");
		listColumn.add("责任归属一级部门");
		listColumn.add("责任归属二级部门");
		listColumn.add("责任归属三级部门");
		listColumn.add("执行岗位");
		listColumn.add("改进人");
		listColumn.add("问题大类型");
		listColumn.add("问题小类型");
		listColumn.add("记分等级");
		listColumn.add("记分对象1");
		listColumn.add("记分值1");
		listColumn.add("记分对象2");
		listColumn.add("记分值2");
		listColumn.add("总记分");
		//listColumn.add("损失金额");
		listColumn.add("质检人");
		listColumn.add("处理岗");
		listColumn.add("处理人");
		listColumn.add("质检报告核实情况");
		return listColumn;
	}
	
	
	
  private 	List<Map<String, Object>>  transListToMap(List<ComplaintCollectListVo> collectList){
		
	  List<Map<String, Object>> data =new ArrayList<Map<String,Object>>();
	  for( ComplaintCollectListVo vo : collectList){
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("质检完成日期", vo.getFinishDate()==null?"": vo.getFinishDate());
			map.put("投诉时间", vo.getBuildDate()==null?"":vo.getBuildDate());
			map.put("投诉完成时间", vo.getCfinishDate()==null?"":vo.getCfinishDate());
			map.put("订单号", vo.getOrderId()==null?"":vo.getOrderId());
			map.put("投诉号", vo.getComplaintId()==null?"":vo.getComplaintId());
			map.put("产品类型", vo.getNiuLineFlag()==null?"":vo.getNiuLineFlag());
			map.put("投诉等级", vo.getLevel());
			map.put("归来日期", vo.getBackTime()==null?"":vo.getBackTime());
			map.put("出发地", vo.getStartCity()==null?"":vo.getStartCity());
			map.put("线路名称", vo.getRoute()==null?"":vo.getRoute());
			map.put("产品经理", vo.getProductLeader()==null?"":vo.getProductLeader());
			map.put("产品专员", vo.getProducter()==null?"":vo.getProducter());
			map.put("客服经理", vo.getCustomerLeader()==null?"":vo.getCustomerLeader());
			map.put("反映问题", vo.getTypeDescript()==null?"":vo.getTypeDescript());
			map.put("对客解决方案", vo.getCdescript()==null?"":vo.getCdescript());
			map.put("质检结论", vo.getConclusion()==null?"":vo.getConclusion());
			map.put("责任人", vo.getResponsiblePerson()==null?"":vo.getResponsiblePerson());
			map.put("责任归属一级部门", vo.getResponsibilityName()==null?"":vo.getResponsibilityName());
			map.put("责任归属二级部门", vo.getRespSecondName()==null?"":vo.getRespSecondName());
			map.put("责任归属三级部门", vo.getRespThirdName()==null?"":vo.getRespThirdName());
			if("0".equals(vo.getPosition())){
				map.put("执行岗位","");
			}else if("1".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("1"));
			}else if("2".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("2"));
			}else if("3".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("3"));
			}else if("4".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("4"));
			}else if("5".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("5"));
			}else if("6".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("6"));
			}else if("7".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("7"));
			}else if("8".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("8"));
			}else if("9".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("9"));
			}else if("10".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("10"));
			}else if("11".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("11"));
			}else if("12".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("12"));
			}else if("13".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("13"));
			}else if("14".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("14"));
			}else if("15".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("15"));
			}else if("16".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("16"));
			}else if("17".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("17"));
			}else if("18".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("18"));
			}else if("19".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("19"));
			}else if("20".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("20"));
			}else if("21".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("21"));
			}else if("22".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("22"));
			}else if("23".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("23"));
			}else if("24".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("24"));
			}else if("25".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("25"));
			}else if("26".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("26"));
			}else if("27".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("27"));
			}else if("28".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("28"));
			}else if("29".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("29"));
			}else if("30".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("30"));
			}else if("31".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("31"));
			}else if("32".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("32"));
			}else if("33".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("33"));
			}else if("34".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("34"));
			}else if("35".equals(vo.getPosition())){
				map.put("执行岗位", CommonUtil.getPositionMap().get("35"));
			}
			
			map.put("改进人", vo.getImproverName()==null?"":vo.getImproverName());
			//map.put("bigClassId", vo.getBigClassId()==null?"":vo.getBigClassId());
			//map.put("smallClassId", vo.getSmallClassId()==null?"":vo.getSmallClassId());
			map.put("问题大类型", vo.getBigClassName()==null?"":vo.getBigClassName());
			map.put("问题小类型", vo.getSmallClassName()==null?"":vo.getSmallClassName());
			map.put("记分等级", vo.getScoreLevel()==null?"":vo.getScoreLevel());
			map.put("记分对象1", vo.getScoreTarget1()==null?"":vo.getScoreTarget1());
			map.put("记分值1", vo.getScoreValue1());
			map.put("记分对象2", vo.getScoreTarget2()==null?"": vo.getScoreTarget2());
			map.put("记分值2", vo.getScoreValue2());
			map.put("总记分", vo.getScoreValue());
			//map.put("损失金额", vo.getSpecial());
			map.put("质检人", vo.getQcPersonName()==null?"":vo.getQcPersonName());
			map.put("处理岗", vo.getDealDepart()==null?"":vo.getDealDepart());
			map.put("处理人", vo.getDealName()==null?"":vo.getDealName());
			map.put("质检报告核实情况", vo.getVerify()==null?"":vo.getVerify());
			data.add(map);
		}
	 
		return data;
	}
 


	public String getBuildBeginDate() {
	return buildBeginDate;
}


public void setBuildBeginDate(String buildBeginDate) {
	this.buildBeginDate = buildBeginDate;
}


public String getBuildEndDate() {
	return buildEndDate;
}


public void setBuildEndDate(String buildEndDate) {
	this.buildEndDate = buildEndDate;
}


public String getFinishBeginDate() {
	return finishBeginDate;
}


public void setFinishBeginDate(String finishBeginDate) {
	this.finishBeginDate = finishBeginDate;
}


public String getFinishEndDate() {
	return finishEndDate;
}


public void setFinishEndDate(String finishEndDate) {
	this.finishEndDate = finishEndDate;
}


	public String getQcPersonName() {
		return qcPersonName;
	}

	public void setQcPersonName(String qcPersonName) {
		this.qcPersonName = qcPersonName;
	}

	public String getBigClassId() {
		return bigClassId;
	}

	public void setBigClassId(String bigClassId) {
		this.bigClassId = bigClassId;
	}

	public String getImproverName() {
		return improverName;
	}

	public void setImproverName(String improverName) {
		this.improverName = improverName;
	}

	public String getScoreLevel() {
		return scoreLevel;
	}

	public void setScoreLevel(String scoreLevel) {
		this.scoreLevel = scoreLevel;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
