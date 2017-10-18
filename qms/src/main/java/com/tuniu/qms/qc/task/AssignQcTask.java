package com.tuniu.qms.qc.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.OperationLog;
import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.OperationLogService;
import com.tuniu.qms.common.service.RtxRemindService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.qc.model.AssignConfigCmp;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcPersonDockDepCmp;
import com.tuniu.qms.qc.service.AssignConfigCmpService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.util.QcConstant;


/**
 * 质检自动分单
 * @author chenhaitao
 */
public class AssignQcTask {
	
	private final static Logger logger = LoggerFactory.getLogger(AssignQcTask.class);
	
	@Autowired
	private QcBillService qcBillService;
	
	@Autowired
	private AssignConfigCmpService assignService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RtxRemindService rtxService;
	
	@Autowired
	private OperationLogService operLogService;

	public void assignQc() {
		List<QcBill> list = qcBillService.listWaitDistrib();
		for (QcBill qcBill : list) {
			Product prd = qcBill.getProduct();
			if (null != prd) {
				QcPersonDockDepCmp depCmp = null;
				Integer prdTeamId = prd.getPrdTeamId();
				if (null != prdTeamId && prdTeamId != 0){//产品组不为空或0，取产品组配置
					depCmp = assignService.getDock(prdTeamId);
				}
				if(null == depCmp && prd.getPrdManagerId() != null){//如果产品组配置为空并且产品经理不为空，取产品经理所在组为产品组Id
					User user = userService.get(prd.getPrdManagerId());
					if (null != user) {
						prdTeamId = user.getDepId();
					}
				}
				
				if(prdTeamId != null && prdTeamId != 0){
					AssignConfigCmp qcPerson = assignService.getDepDockQcPerson(prdTeamId); // 获取产品组ID对应的质检人员
					if (null != qcPerson) {
						dealPeopleQc(qcBill, qcPerson); // 分配处理人
					}
				}
			}
		}
		
		List<QcBill> nonOrdlist = qcBillService.listWaitDistribNonOrd();
		for (QcBill qcBill : nonOrdlist) {
			AssignConfigCmp qcPerson = assignService.getNoOrderQcPerson(); // 获取无订单质检员
			if (null != qcPerson) {
				dealPeopleQc(qcBill, qcPerson); // 分配质检员
				qcPerson.setNooAssignTime(new Date());
				assignService.update(qcPerson); // 更新无订单分单时间
			}
		}

	}
	
	private void dealPeopleQc(QcBill qcBill, AssignConfigCmp qcPerson) {
		try {
			qcBill.setQcPersonId(qcPerson.getQcPersonId());
			qcBill.setQcPerson(qcPerson.getQcPersonName());
			qcBill.setDistribTime(new Date());
			qcBill.setUpdateTime(new Date());
			qcBill.setState(QcConstant.QC_BILL_STATE_QCBEGIN); // 分配后，转换为处理中状态
			qcBillService.update(qcBill);
			
			OperationLog log = new OperationLog();
			log.setDealPeople(0);
			log.setDealPeopleName(QcConstant.ROBOT);
			log.setQcId(qcBill.getId());
			log.setFlowName("分配质检单");
			log.setContent("质检单："+qcBill.getId()+"，分配给："+qcBill.getQcPerson()+"，质检状态变更为质检中");
			operLogService.add(log);
			
			if(qcBill.getCmpId() > 0 && isEffectiveQc(qcBill)){
				RtxRemind rtx = new RtxRemind();
				rtx.setTitle("【质检任务提醒】");
				rtx.setContent("您有新的质检任务，请及时处理！");
				rtx.setUid(qcPerson.getQcPersonId().toString());
				rtx.setAddPerson("Robot");
				rtx.setSendTime(new Date());
				rtxService.add(rtx);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("质检单:" + qcBill.getId() + "分配给" + qcPerson.getQcPersonName() + "失败", e.getMessage());
		}
	}
	
	private boolean isEffectiveQc(QcBill qc) {
		boolean isEffective = false;
		ComplaintBill cmp = qc.getComplaintBill();
		if ((cmp.getIndemnifyAmount()-cmp.getClaimAmount()-cmp.getQualityToolAmount()) > 0
				|| cmp.getClaimAmount() > 0
				|| (1 == cmp.getCmpLevel() || 2 == cmp.getCmpLevel())
				|| (cmp.getRepVoucherAmount()+cmp.getGiftAmount()) > 0) {
			isEffective = true;
		}
		return isEffective;
	}
	
}
