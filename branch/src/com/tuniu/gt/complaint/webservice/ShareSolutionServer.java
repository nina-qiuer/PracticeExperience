package com.tuniu.gt.complaint.webservice;

import it.sauronsoftware.base64.Base64;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import tuniu.frm.service.Bean;

import com.tuniu.gt.complaint.entity.ShareSolutionEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.service.IEmployeeShareService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.complaint.service.ISupportShareService;

public class ShareSolutionServer extends ComplaintXmlrpcBase {
	// 引入分担方案service
	private IShareSolutionService shareSolutionService = (IShareSolutionService) Bean.get("complaint_service_complaint_impl-share_solution");
	//员工分担service
	private IEmployeeShareService employeeShareService = (IEmployeeShareService) Bean.get("complaint_service_complaint_impl-employee_share");
	//供应商分担sercie
	private ISupportShareService supportShareService = (ISupportShareService) Bean.get("complaint_service_complaint_impl-support_share");
	/**
	 * 提交至boss赔款单审核时改变分担方案的状态
	 * @param msg
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String refundChecked(String msg) throws UnsupportedEncodingException {

		if (!checkClient(msg)) {
			return encodeMsg();
		}

		Map<String, Object> edit = (Map<String, Object>) recvMsg.data;
		if (!edit.containsKey("id")) {
			setErrMsg(WebServiceError.paramError);
			return encodeMsg();
		}
		int id = Integer.parseInt(edit.get("id").toString());
		if (id < 1) {
			setErrMsg(WebServiceError.paramError);
			return encodeMsg();
		}
		
		ShareSolutionEntity sse = (ShareSolutionEntity) shareSolutionService.fetchOne(id);
		//sse.setCheckFlag(Integer.parseInt(edit.get("checkFlag").toString()));
		sse.setUpdateTime(new Date());
		try {
			shareSolutionService.update(sse);
			setMsg("操作成功");
		} catch (Exception ex) {
			setErrMsg(ex.getMessage());
		}
		return encodeMsg();
	}
	
	
	/**
	 * boss修改供应商分担方案时调用，反馈结果
	 * @param msg
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String agencyAssignChanged(String msg) throws UnsupportedEncodingException {
		if (!checkClient(msg)) {
			return encodeMsg();
		}

		Map<String, Object> agencyMap = (Map<String, Object>) recvMsg.data;
		
		if (!agencyMap.containsKey("id")) {
			setErrMsg(WebServiceError.paramError);
			return encodeMsg();
		}
		int id = Integer.parseInt(agencyMap.get("id").toString());
		if (id < 1) {
			setErrMsg(WebServiceError.paramError);
			return encodeMsg();
		}
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		ShareSolutionEntity ssEntity = new ShareSolutionEntity();
		SupportShareEntity sse = new SupportShareEntity();
		String option = agencyMap.get("option").toString();
		int shareId = Integer.parseInt(agencyMap.get("id").toString());
		int type = Integer.parseInt(agencyMap.get("type").toString()); //1为供应商，2为公司，4为订单利润支出，5员工
		paramMap.put("shareId", shareId);
		//更新分担方案
		if ("update".equals(option)) {
			//更新供应商分担方案
			if (type == 1) {
				//paramMap.put("name", Base64.decode(agencyMap.get("name").toString(), "UTF-8"));
				paramMap.put("code", Integer.parseInt(agencyMap.get("agencyId").toString()));
				sse = (SupportShareEntity) supportShareService.fetchOne(paramMap);
				if(sse != null){
					double oldNumber = sse.getNumber();
					double number = Double.valueOf(agencyMap.get("number").toString());
					sse.setNumber(number);
					supportShareService.update(sse);
					
					double change = oldNumber - number;
					ssEntity = (ShareSolutionEntity) shareSolutionService.fetchOne(shareId);
					ssEntity.setSupplierTotal(ssEntity.getSupplierTotal() - change);
					ssEntity.setTotal(ssEntity.getTotal() - change);
					shareSolutionService.update(ssEntity);
					
				}
			//更新其他分担方案
			} else {
				ssEntity = (ShareSolutionEntity) shareSolutionService.fetchOne(shareId);
				double number = Double.valueOf(agencyMap.get("number").toString());
				double change = 0;
				//modify by zhoupanpan 2012-05-14
				if (type == 2) {
					change = ssEntity.getSpecial() - number;
					ssEntity.setSpecial(number);
				} else if (type == 4) {
					change = ssEntity.getOrderGains() - number;
					ssEntity.setOrderGains(number);
				} else if (type == 5) {
					change = ssEntity.getEmployeeTotal() - number;
					ssEntity.setEmployeeTotal(number);
				}
				//modify end
				
				ssEntity.setTotal(ssEntity.getTotal() - change);
				shareSolutionService.update(ssEntity);
			}
			
			setMsg("操作成功");
		//新增供应商分担方案
		} else if("insert".equals(option)){
			paramMap.clear();
			paramMap.put("id", shareId);
			ShareSolutionEntity shareSolutionEntity = (ShareSolutionEntity) shareSolutionService.fetchOne(paramMap);
			if(shareSolutionEntity != null){
				double number = Double.valueOf(agencyMap.get("number").toString());
				sse.setComplaintId(shareSolutionEntity.getComplaintId());
				sse.setShareId(Integer.parseInt(agencyMap.get("id").toString()));
				sse.setName(Base64.decode(agencyMap.get("name").toString(), "UTF-8"));
				sse.setCode(Integer.parseInt(agencyMap.get("agencyId").toString()));
				sse.setNumber(number);
				sse.setDelFlag(1);
				supportShareService.insert(sse);
				
				ssEntity = (ShareSolutionEntity) shareSolutionService.fetchOne(shareId);
				ssEntity.setSupplierTotal(ssEntity.getSupplierTotal() + number);
				ssEntity.setTotal(ssEntity.getTotal() + number);
				shareSolutionService.update(ssEntity);
				
			}
			setMsg("操作成功");
		//删除供应商分担方案
		} else if("delete".equals(option)){
			double number = Double.valueOf(agencyMap.get("number").toString());
			//paramMap.put("name", Base64.decode(agencyMap.get("name").toString(), "UTF-8"));
			paramMap.put("code", Integer.parseInt(agencyMap.get("agencyId").toString()));
			paramMap.put("number", number);
			sse = (SupportShareEntity) supportShareService.fetchOne(paramMap);
			if(sse != null){
				sse.setDelFlag(0);
				supportShareService.update(sse);
				
				ssEntity = (ShareSolutionEntity) shareSolutionService.fetchOne(shareId);
				ssEntity.setSupplierTotal(ssEntity.getSupplierTotal() - number);
				ssEntity.setTotal(ssEntity.getTotal() - number);
				shareSolutionService.update(ssEntity);
			}
			setMsg("操作成功");
		}
		
		return encodeMsg();
	}

}
