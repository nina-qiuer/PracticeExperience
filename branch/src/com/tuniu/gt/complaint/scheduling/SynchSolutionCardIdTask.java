package com.tuniu.gt.complaint.scheduling;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.service.IComplaintSolutionService;
import com.tuniu.gt.complaint.service.IComplaintTSPService;

public class SynchSolutionCardIdTask {

	private static Logger logger = Logger.getLogger(SynchSolutionCardIdTask.class);
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_solution")
	private IComplaintSolutionService complaintSolutionService;
	
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;
	
	public void synchCardUniqueIdAuditing(){
		Map<String, Object> params = new HashMap<String, Object>();
		Integer[] audit_flag_list={0,1,2,3,5,9};
		params.put("audit_flag_list",audit_flag_list );
		params.put("payment", 1);
		synchCardUniqueId(params);
	}
	
	public void synchCardUniqueIdOne(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audit_flag", 4);
		params.put("payment", 2);
		params.put("idEnd", 100000);
		synchCardUniqueId(params);
	}
	
	public void synchCardUniqueIdTwo(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audit_flag", 4);
		params.put("payment", 2);
		params.put("idBgn", 100000);
		params.put("idEnd", 200000);
		synchCardUniqueId(params);
	}

	public void synchCardUniqueIdThree(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audit_flag", 4);
		params.put("payment", 2);
		params.put("idBgn", 200000);
		params.put("idEnd", 300000);
		synchCardUniqueId(params);
	}
	
	public void synchCardUniqueIdFour(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audit_flag", 4);
		params.put("payment", 2);
		params.put("idBgn", 300000);
		params.put("idEnd", 400000);
		synchCardUniqueId(params);
	}
	
	public void synchCardUniqueIdFive(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audit_flag", 4);
		params.put("payment", 2);
		params.put("idBgn", 400000);
		params.put("idEnd", 600000);
		synchCardUniqueId(params);
	}
	
	public void synchCardUniqueIdSix(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audit_flag", 4);
		params.put("payment", 2);
		params.put("idBgn", 600000);
		params.put("idEnd", 900000);
		synchCardUniqueId(params);
	}
	
	public void synchCardUniqueIdSeven(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audit_flag", 4);
		params.put("payment", 2);
		params.put("idBgn", 900000);
		params.put("idEnd", 1200000);
		synchCardUniqueId(params);
	}
	
	public void synchCardUniqueIdEight(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audit_flag", 4);
		params.put("payment", 2);
		params.put("idBgn", 1200000);
		synchCardUniqueId(params);
	}
	
	public void synchCardUniqueIdUnit(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("audit_flag", 4);
		params.put("payment", 3);
		synchCardUniqueId(params);
	}
	
	public void synchCardUniqueId(Map<String, Object> params){
		List<ComplaintSolutionEntity> complaintSolutionList=complaintSolutionService.getNeedSynchSolution(params);
		for (int i = 0; i < complaintSolutionList.size(); i++) {
			ComplaintSolutionEntity complaintSolutionEntity=complaintSolutionList.get(i);
			if(complaintSolutionEntity.getCardUniqueId().equals("")){
				try {
					String cardUniqueId=tspService.addInCompleteCard(complaintSolutionEntity);
					if(cardUniqueId!=null&&!cardUniqueId.equals("")){
						Map<String, Object> tempparam = new HashMap<String, Object>();
						tempparam.put("id", complaintSolutionEntity.getId());
						tempparam.put("cardUniqueId", cardUniqueId);
						complaintSolutionService.updateCardUniqueId(tempparam);
					}
				} catch (Exception e) {
					logger.error(e.getMessage()+"同步对客解决方案失败 投诉单号:"+complaintSolutionEntity.getComplaintId());
				}
			}else{
				logger.info("存在银行卡账号不同步 投诉单号:"+complaintSolutionEntity.getComplaintId());
			}
			
		}
	}
}
