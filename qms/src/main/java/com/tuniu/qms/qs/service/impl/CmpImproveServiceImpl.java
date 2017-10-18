package com.tuniu.qms.qs.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.access.client.CmpClient;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.ComplaintBillService;
import com.tuniu.qms.common.service.DepartmentService;
import com.tuniu.qms.common.service.OrderBillService;
import com.tuniu.qms.common.service.ProductService;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dto.QcPointAttachDto;
import com.tuniu.qms.qc.service.QcPointAttachService;
import com.tuniu.qms.qs.dao.CmpImproveMapper;
import com.tuniu.qms.qs.dto.CmpImproveDto;
import com.tuniu.qms.qs.model.CmpImprove;
import com.tuniu.qms.qs.service.CmpImproveService;

@Service
public class CmpImproveServiceImpl implements CmpImproveService {
	
	private static final Logger logger = LoggerFactory.getLogger(CmpImproveServiceImpl.class);
	
	@Autowired
	private CmpImproveMapper cmpImproveMapper;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private OrderBillService orderBillService;
	
	@Autowired
	private TspService tspService;
	
	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QcPointAttachService qcPointAttachService;
	
	@Autowired
	private ComplaintBillService complaintBillService;
	
	@Override
	public void add(CmpImprove obj) {
		cmpImproveMapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		cmpImproveMapper.delete(id);
	}

	@Override
	public void update(CmpImprove obj) {
		cmpImproveMapper.update(obj);
	}

	@Override
	public CmpImprove get(Integer id) {
		return cmpImproveMapper.get(id);
	}

	@Override
	public List<CmpImprove> list(CmpImproveDto dto) {
		return cmpImproveMapper.list(dto);
	}

	@Override
	public void loadPage(CmpImproveDto dto) {
		int count = cmpImproveMapper.count(dto);
		
		dto.setTotalRecords(count);
		dto.setDataList(cmpImproveMapper.loadPage(dto));
	}

	@Override
	public List<CmpImprove> listByCmpId(Integer cmpId) {
		return cmpImproveMapper.listByCmpId(cmpId);
	}

	@Override
	public List<CmpImprove> getOutHandleEndTime() {
		return cmpImproveMapper.getOutHandleEndTime();
	}
	
	@Override
	public String localSubmitImpBill(CmpImprove obj) {
		String errorMsg = this.fillImproveBillInfo(obj, true);
		
		if(("").equals(errorMsg) && !Constant.IMPROVE_HANDLE_SYSTEM.equals(obj.getImpPerson())){
			errorMsg = this.sendImproveBill(obj) ? "" : "改进报告发起失败";
		}
		
		return errorMsg;
	}
	
	@Override
	public boolean cmpSubmitImpBill(CmpImprove cmpImprove) {
		boolean isSuccess = false;
		
		if(Constant.IMPROVE_HANDLE_SYSTEM.equals(cmpImprove.getImpPerson())){
			cmpImprove.setState(Constant.IMPROVE_STATE_WAIT_FIX_RESP);//待定责状态
			cmpImprove.setHandlePerson(Constant.IMPROVE_DEV_HANDLE_PERSON);
			cmpImprove.setDelFlag(Constant.NO);
			
			this.add(cmpImprove);
			
			isSuccess = true;
		}else{
			String errorMsg = this.fillImproveBillInfo(cmpImprove, false);
			
			if(("").equals(errorMsg)){
				this.add(cmpImprove);
				
				isSuccess = this.sendImproveBill(cmpImprove);
				
				if(!isSuccess){
					logger.info("改进报告发送失败");
					this.delete(cmpImprove.getId());
				}
			}
		}
		
		return isSuccess;
	}

	/**
	 * 发送改进报告
	 * @param obj
	 * @return
	 */
	private boolean sendImproveBill(CmpImprove obj) {
		boolean returnflag = false;
		
		String oaId = tspService.sendImproveBill(obj);
		if(!("").equals(oaId)){//提交成功，更新改进报告  oa 表单Id 回写
			obj.setOaId(oaId);
			this.update(obj);
			
			syncData(obj);
			
			returnflag = true;
		}
		
		return returnflag;
	}
	
	/**
	 * 同步订单、产品、投诉单信息
	 * @param obj
	 */
	private void syncData(CmpImprove obj) {
		productService.addPrdSyncTask(obj.getPrdId());
	
		orderBillService.addOrdSyncTask(obj.getPrdId());
	
		complaintBillService.addCmpSyncTask(obj.getCmpId());
	}

	
	/**
	 * 改进报告数据验证填充
	 * @param obj
	 * @param isLocalSubmit 是否本地提交
	 * @return
	 */
	private String fillImproveBillInfo(CmpImprove obj, boolean isLocalSubmit) {
		
		if(isLocalSubmit){
			if(fillComplaintDetails(obj)){
				return "改进报告发起失败，投诉单不存在！";
			}
			
			if(fillImpPersonDetails(obj)){
				return "改进报告发起失败，改进人不存在！";
			}
			
			fillAttachDetails(obj);
		}
		
		if(fillHandleEndTime(obj)){
			return "改进报告发起失败，获取工作日接口失败！";
		}
		
		fillDepartmentDetails(obj);
		
		obj.setState(Constant.IMPROVE_STATE_HANDLE);//处理中状态
		obj.setHandlePerson(obj.getImpPerson());//处理人是责任人
		obj.setDelFlag(Constant.NO);
		
		return "";
	}
	
	/**
	 * 设置改进人信息
	 * @param obj
	 * @return
	 */
	private boolean fillImpPersonDetails(CmpImprove obj) {
		User impPerson = userService.getUserByRealName(obj.getImpPerson());//根据姓名获得员工
		
		if(null == impPerson){
			return true;
		}
		
		obj.setImpPersonId(impPerson.getId());
		obj.setImpPerDepId(impPerson.getDepId());
		return false;
	}
	
	/**
	 * 设置改进报告到期日期
	 * @param cmpImprove
	 * @return
	 */
	private boolean fillHandleEndTime(CmpImprove cmpImprove) {
		Date endTime = DateUtil.getFinishDate(new Date(), 5);
		if(null == endTime){
			return true;
		}
		
		cmpImprove.setHandleEndTime(endTime);//流程处理到期时间  当前时间加5个工作日
		return false;
	}
	
	/**
	 * 填充投诉单相关信息
	 * @param obj
	 * @return
	 */
	private boolean fillComplaintDetails(CmpImprove obj) {
		ComplaintBill cmp = CmpClient.getComplaintInfo(obj.getCmpId());//投诉单信息
		if(null == cmp){
			return true;
		}
		
		obj.setOrdId(cmp.getOrdId());//订单号
		obj.setPrdId(cmp.getRouteId());//产品号
		obj.setRouteName(cmp.getRouteName());//产品名称
		obj.setAgencyName(cmp.getAgencyName());//供应商名称
		obj.setProducter(cmp.getProducter());//产品专员
		obj.setProductLeader(cmp.getProductLeader());//产品经理
		obj.setProductManager(cmp.getProductManager());//产品总监
		obj.setCustomer(cmp.getCustomer());//客服专员
		obj.setCustomerLeader(cmp.getCustomerLeader());//客服经理
		obj.setCustomerManager(cmp.getServiceManager());//客服总监
		
		return false;
	}
	
	/**
	 * 填充附件相关信息
	 * @param obj
	 */
	private void fillAttachDetails(CmpImprove obj) {
		QcPointAttachDto attachDto = new QcPointAttachDto();//附件信息
		attachDto.setQcId(obj.getId());
		attachDto.setBillType(Constant.ATTACH_BILL_TYPE_IMPROVE);
		
		obj.setAttachList(qcPointAttachService.listImproveAttach(attachDto));
	}
	
	/**
	 * 填充部门相关信息
	 * @param obj
	 */
	private void fillDepartmentDetails(CmpImprove obj) {
		Stack<Department> stackData = departmentService.getDepartmentList(obj.getImpPerDepId());
		
		while(!stackData.isEmpty()){//添加改进人部门
			Department department = stackData.pop();
			if(department.getDepth() == 2){//一级部门
				obj.setFirstDepId(department.getId());
				obj.setFirstDep(department.getName());
				continue;
			}
			if(department.getDepth() == 3){//二级部门，直接结束
				obj.setSecondDepId(department.getId());
				obj.setSecondDep(department.getName());
				break;
			}
		}
	}

	@Override
	public List<CmpImprove> listWaitDistrib() {
		return cmpImproveMapper.listWaitDistrib();
	}

	@Override
	public List<CmpImprove> listWaitDistribNonOrd() {
		return cmpImproveMapper.listWaitDistribNonOrd();
	}
	
	@Override
	public void updateHandlePerson(List<Integer> impBillIds, String assignTo, User user) {
		
		for(Integer impId : impBillIds){
			CmpImprove cmpImprove = new CmpImprove();
			cmpImprove.setId(impId);
			cmpImprove.setHandlePerson(assignTo);
			cmpImprove.setUpdatePerson(user.getRealName());
			cmpImproveMapper.update(cmpImprove);
		}
	}

}
