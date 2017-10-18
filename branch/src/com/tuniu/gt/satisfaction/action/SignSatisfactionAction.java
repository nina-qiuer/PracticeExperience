package com.tuniu.gt.satisfaction.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;
import tuniu.frm.service.Common;

import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionEntity;
import com.tuniu.gt.satisfaction.entity.SignSatisfactionMsgReplyEntity;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionMsgReplyService;
import com.tuniu.gt.satisfaction.service.ISignSatisfactionService;
import com.tuniu.gt.toolkit.DataExportExcel;

@Service("complaint_action-signSatisfaction")
@Scope("prototype")
public class SignSatisfactionAction extends
		FrmBaseAction<ISignSatisfactionService, SignSatisfactionEntity> {

	public SignSatisfactionAction() {
		setManageUrl("signSatisfaction");
	}

	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-signSatisfactionMsgReply")
	private ISignSatisfactionMsgReplyService signSatisfactionMsgReplyService;
	
	//用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("satisfaction_service_satisfaction_impl-signSatisfaction")
	public void setService(ISignSatisfactionService service) {
		this.service = service;
	};
	
	private List<UserEntity> productManagers = new ArrayList<UserEntity>(); // 产品经理列表
	
	/**
	 * 返回主页面
	 * 
	 * @see tuniu.frm.core.FrmBaseAction#execute()
	 */
	public String execute() {
	    //投诉处理列表每页显示个数为30
        this.perPage = 30;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap = paramSearch();
		// 调用父类方法返回页面列表
		super.execute(paramMap);
		List<SignSatisfactionEntity> getDatatList = (List<SignSatisfactionEntity>)request.getAttribute("dataList");
		
		request.setAttribute("dataList", getDatatList);
		request.setAttribute("pageTitle", "上门/门市签约客服满意度管理");
		
		//获取预订城市信息
		List<String> bookCityList = service.getDistinctBookCityList();
		request.setAttribute("bookCityList", bookCityList);
		
		//获取出发城市信息
		List<String> startCityList = service.getDistinctStartCityList();
		request.setAttribute("startCityList", startCityList);
		
		//获取目的地信息  由于团队游目的地信息不可控，比如目的地是 亚洲-中国，故暂不提供目的地信息的搜索功能 modify by hanxuliang 2014-05-07
//		List<String> desCityList = service.getDistinctDesCityList();
//		request.setAttribute("desCityList", desCityList);
		
		String res = "sign_satisfaction_list";
		return res;
	}
	
	public List<String> getBookCityList(List<SignSatisfactionEntity> list){
		List<String> result = new ArrayList<String>();
		result = service.getDistinctBookCityList();
		return result;
	}
	
	
	
	/**
	 * 封装查询条件
	 * @return
	 */
	public Map<String, Object> paramSearch(){
		
		final String productManagerJobIds = "16,19,30,39,41,149,176";//产品经理id、高级产品经理id、产品主管id、大客户产品经理id、大客户产品经理助理id、度假产品经理id、度假产品经理助理id。
		productManagers = userService.getUsersByJob(productManagerJobIds);//获取产品经理列表
		request.setAttribute("productManagers", productManagers);
				
		Map<String, Object> paramMap = new HashMap<String, Object>();  //search使用的map
		Map<String, String> search = Common.getSqlMap(getRequestParam(),"search.");
		paramMap.putAll(search);// 放入search内容
	
		//搜索条件客服经理
		Set<UserEntity> customLeaderSearch = new HashSet<UserEntity>(); 
		String jobIds = "10,13,15,21";
		List<UserEntity> listOne = userService.getUsersByJob(jobIds);
		customLeaderSearch.addAll(listOne);
        
		request.setAttribute("customLeaderSearch", customLeaderSearch);
		
		request.setAttribute("search", search);
		
		return paramMap;
	}
	
	
	/**
	 * 导出数据
	 * @return String 页面
	 */
	public String exports(){
		
		List<List<Object>> data = new ArrayList<List<Object>>();
		
		//设置标题	
		List<Object> listColumn = new ArrayList<Object>();
		listColumn.add("订单编号");
		listColumn.add("门市/上门签约人员id");
		listColumn.add("门市/上门签约人员姓名");
		listColumn.add("门市/上门签约人员服务满意度");
		listColumn.add("签约方式");
		listColumn.add("预订城市编号");
		listColumn.add("预订城市");
		listColumn.add("出发城市编号");
		listColumn.add("出发城市");
		listColumn.add("目的地编号");
		listColumn.add("目的地");
		listColumn.add("客服经理id");
		listColumn.add("客服经理");
		listColumn.add("产品经理id");
		listColumn.add("产品经理");
		listColumn.add("订单类型");
		listColumn.add("线路编号");
		listColumn.add("出游日期");
		listColumn.add("归来日期");
		
		data.add(listColumn);
		
		//查询数据
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap = paramSearch();
		List<SignSatisfactionEntity> signSatisfactionList = (List<SignSatisfactionEntity>)service.fetchList(paramMap);
		
		//封装数据
		if(signSatisfactionList.size() > 0){
			for(SignSatisfactionEntity signSatisfactionEntity : signSatisfactionList){
				List<Object> listData = new ArrayList<Object>();
				listData.add(signSatisfactionEntity.getOrderId());
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getFaceSaleId()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getFaceSaleName()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getFaceSaleSatisfaction()));
				String signType = getExportSignType(signSatisfactionEntity.getSignType());
				listData.add(SatisfactionUtil.isNull(signType));
				
				listData.add(signSatisfactionEntity.getBookCityCode());
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getBookCity()));
				listData.add(signSatisfactionEntity.getStartCityCode());
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getStartCity()));
				listData.add(signSatisfactionEntity.getDesCityCode());
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getDesCity()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getCustomerLeaderId()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getCustomerLeader()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getProductLeaderId()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getProductLeader()));
				String orderType = signSatisfactionEntity.getOrderType();
				if(orderType!=null&&!"".equals(orderType)){
					if("0".equals(orderType)){
						listData.add("跟团");
					}else if("1".equals(orderType)){
						listData.add("自助游");
					}else if("2".equals(orderType)){
						listData.add("团队");
					}
				}
				listData.add(signSatisfactionEntity.getRouteId());
				if(signSatisfactionEntity.getOutDate()!=null){
					listData.add((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(signSatisfactionEntity.getOutDate()));
				}else{
					listData.add(signSatisfactionEntity.getOutDate());
				}
				if(signSatisfactionEntity.getBackDate()!=null){
					listData.add((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(signSatisfactionEntity.getBackDate()));
				}else{
					listData.add(signSatisfactionEntity.getBackDate());
				}
				
				// 添加数据
				data.add(listData);
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String filename = "signSatisfaction_" + (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
		DataExportExcel dee = new DataExportExcel(data, filename, "", response);
		dee.createExcelFromList();

		String res = "sign_satisfaction_list";
		return res;
	}
	
	private String getExportSignType(String value){
		String result = "";
			if(value!=null&&!"".equals(value)){
				//flag=0表示是跟团订单，flag=1表示是自助游订单
					if("0".equals(value)){
						result = "上门签约";
					}else if("1".equals(value)){
						result = "门市签约";
					}
		}
		return result;
	}
	
	/**
	 * 导出今日数据
	 * @return String 页面
	 */
	public String exportsToday(){
		
		List<List<Object>> data = new ArrayList<List<Object>>();
		
		//设置标题	
		List<Object> listColumn = new ArrayList<Object>();
		listColumn.add("订单编号");
		listColumn.add("门市/上门签约人员id");
		listColumn.add("门市/上门签约人员姓名");
		listColumn.add("门市/上门签约人员服务满意度");
		listColumn.add("签约方式");
		listColumn.add("预订城市编号");
		listColumn.add("预订城市");
		listColumn.add("出发城市编号");
		listColumn.add("出发城市");
		listColumn.add("目的地编号");
		listColumn.add("目的地");
		listColumn.add("客服经理id");
		listColumn.add("客服经理");
		listColumn.add("产品经理id");
		listColumn.add("产品经理");
		listColumn.add("订单类型");
		listColumn.add("线路编号");
		listColumn.add("出游日期");
		listColumn.add("归来日期");
		
		data.add(listColumn);
		
		//查询数据
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String lastModifyTime = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
		paramMap.put("lastModifyTime", lastModifyTime);
		List<SignSatisfactionEntity> signSatisfactionList = (List<SignSatisfactionEntity>)service.fetchList(paramMap);
		
		//封装数据
		if(signSatisfactionList.size() > 0){
			for(SignSatisfactionEntity signSatisfactionEntity : signSatisfactionList){
				List<Object> listData = new ArrayList<Object>();
				listData.add(signSatisfactionEntity.getOrderId());
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getFaceSaleId()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getFaceSaleName()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getFaceSaleSatisfaction()));
				String signType = getExportSignType(signSatisfactionEntity.getSignType());
				listData.add(SatisfactionUtil.isNull(signType));
				
				listData.add(signSatisfactionEntity.getBookCityCode());
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getBookCity()));
				listData.add(signSatisfactionEntity.getStartCityCode());
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getStartCity()));
				listData.add(signSatisfactionEntity.getDesCityCode());
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getDesCity()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getCustomerLeaderId()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getCustomerLeader()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getProductLeaderId()));
				listData.add(SatisfactionUtil.isNull(signSatisfactionEntity.getProductLeader()));
				String orderType = signSatisfactionEntity.getOrderType();
				if(orderType!=null&&!"".equals(orderType)){
					if("0".equals(orderType)){
						listData.add("跟团");
					}else if("1".equals(orderType)){
						listData.add("自助游");
					}else if("2".equals(orderType)){
						listData.add("团队");
					}
				}
				listData.add(signSatisfactionEntity.getRouteId());
				if(signSatisfactionEntity.getOutDate()!=null){
					listData.add((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(signSatisfactionEntity.getOutDate()));
				}else{
					listData.add(signSatisfactionEntity.getOutDate());
				}
				if(signSatisfactionEntity.getBackDate()!=null){
					listData.add((new java.text.SimpleDateFormat("yyyy-MM-dd")).format(signSatisfactionEntity.getBackDate()));
				}else{
					listData.add(signSatisfactionEntity.getBackDate());
				}
				
				// 添加数据
				data.add(listData);
			}
		}
		HttpServletResponse response = ServletActionContext.getResponse();
		String filename = "signSatisfaction_" + (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
		DataExportExcel dee = new DataExportExcel(data, filename, "", response);
		dee.createExcelFromList();

		String res = "sign_satisfaction_list";
		return res;
	}
	
	public String fixSignSatisfactionPage(){

		String res = "fix_sign_satisfaction";
		return res;
	}
	
	public String fixSignSatisfaction(){
		List<SignSatisfactionMsgReplyEntity> list = (List<SignSatisfactionMsgReplyEntity>) signSatisfactionMsgReplyService.fetchList();
		Map map = new HashMap();
		for(SignSatisfactionMsgReplyEntity entity : list){
			
			String msg = entity.getMsg();
			String socre = null;
			if(isNum(msg)){
				socre = calSocreByNum(msg);
			}else{
				socre = calSocreByStr(msg);
			}
			if(socre!=null){
				map.put("id", entity.getUpdateSatisId());
				map.put("faceSaleSatisfaction", socre);
				signSatisfactionMsgReplyService.fixSatisfactionSocre(map);
			}
		}
		return null;
	}
	
	private boolean isNum(String msg){
		msg = msg.replace(" ", "");
		boolean flag = true;
		if(!Character.isDigit(msg.charAt(0))){
			flag = false;
		}
		return flag;
	}
	
	private String calSocreByNum(String msg){
		msg = msg.replace(" ", "");
		float value = 0;
		String result = null;
		boolean flag = false;
		String socre = msg.substring(0,1);
		if ("1".equals(socre)) {
			value = 3;
			flag = true;
		} else if ("2".equals(socre)) {
			value = 2;
			flag = true;
		} else if ("3".equals(socre)) {
			value = 1;
			flag = true;
		} else if ("4".equals(socre)) {
			value = 0;
			flag = true;
		} 
		if(flag){
			result = Math.round(value/3*100)+"%";
		}
		return result;
	}
	
	private String calSocreByStr(String msg){
		msg = msg.replace(" ", "");
		float value = 0;
		String result = null;
		boolean flag = false;
		if(msg.indexOf("非常满意")>=0){
			value = 3;
			flag = true;
		}else if (msg.indexOf("不满意")>=0) {
			value = 0;
			flag = true;
		} else if (msg.indexOf("满意")>=0) {
			value = 2;
			flag = true;
		} else if (msg.indexOf("一般")>=0) {
			value = 1;
			flag = true;
		} 
		if(flag){
			result = Math.round(value/3*100)+"%";
		}
		return result;
	}
	
	
}
