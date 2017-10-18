package com.tuniu.qms.qs.task;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.model.Product;
import com.tuniu.qms.common.model.RtxRemind;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.RtxRemindService;
import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.qc.model.AssignConfigCmp;
import com.tuniu.qms.qc.model.QcPersonDockDepCmp;
import com.tuniu.qms.qc.service.AssignConfigCmpService;
import com.tuniu.qms.qc.task.AssignQcTask;
import com.tuniu.qms.qs.model.CmpImprove;
import com.tuniu.qms.qs.service.CmpImproveService;

/**
 * 改进报告分单，处理人配置使用投诉质检单分单的人员配置
 * @author zhangsensen
 *
 */
public class CmpImproveTask {
	
	private final static Logger logger = LoggerFactory.getLogger(AssignQcTask.class);
	
	@Autowired
	private CmpImproveService cmpImproveService;
	
	@Autowired
	private AssignConfigCmpService assignService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RtxRemindService rtxService;
	
	public void assignCmpImprove(){
		//获得有订单投诉、待分配的改进报告
		List<CmpImprove> list = cmpImproveService.listWaitDistrib();
		for (CmpImprove cmpImprove : list) {//先根据产品组查质检人，如果没有再根据产品经理所在的组来查，再没有就GG了
			Product prd = cmpImprove.getProduct();
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
						dealPeopleImprove(cmpImprove, qcPerson); // 分配处理人
					}
				}
			}
		}
		//获得无订单投诉、待分配的改进报告
		List<CmpImprove> nonOrdlist = cmpImproveService.listWaitDistribNonOrd();
		for (CmpImprove cmpImprove : nonOrdlist) {
			AssignConfigCmp qcPerson = assignService.getNoOrderQcPerson(); // 获取无订单质检员
			if (null != qcPerson) {
				dealPeopleImprove(cmpImprove, qcPerson); // 分配处理人
				qcPerson.setNooAssignTime(new Date());
				assignService.update(qcPerson); // 更新无订单分单时间
			}
		}
	}

	private void dealPeopleImprove(CmpImprove cmpImprove, AssignConfigCmp qcPerson) {
		try{
			cmpImprove.setHandlePerson(qcPerson.getQcPersonName());//未分配前没有处理人
			cmpImproveService.update(cmpImprove);
			
			RtxRemind rtx = new RtxRemind();
			rtx.setTitle("【投诉改进报告任务提醒】");
			rtx.setContent("您有新的投诉改进报告任务，请及时处理！");
			rtx.setUid(qcPerson.getQcPersonId().toString());
			rtx.setAddPerson("Robot");
			rtx.setSendTime(new Date());
			rtxService.add(rtx);
			
		}catch(Exception e){
			logger.error("投诉改进报告:" + cmpImprove.getId() + "分配给" + qcPerson.getQcPersonName() + "失败", e);
		}
	}
	
}
