package com.tuniu.gt.complaint.scheduling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintFollowNoteEntity;
import com.tuniu.gt.complaint.enums.DealDepartEnum;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.frm.action.DBConfigManager;

public class ComplaintOvertimeTask {
	private static Logger logger = Logger.getLogger(ComplaintOvertimeTask.class);

	// 引入投诉sercie
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	public void upgradeVipDepartList(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Integer overtime_frame =DBConfigManager.getConfig("vipdepart.overtime.frame", Integer.class)==null?30:DBConfigManager.getConfig("vipdepart.overtime.frame", Integer.class);
		paramMap.put("overtime_frame", overtime_frame);
		paramMap.put("deal_depart", DealDepartEnum.VIP_DEPART.getValue());
		String upgradeDealDepart = DealDepartEnum.IN_TRAVLE.getValue();
		List<ComplaintEntity> complaintLists = complaintService.getOvertimeNotCallbackLists(paramMap);
		List<ComplaintFollowNoteEntity> followNoteLists = new ArrayList<ComplaintFollowNoteEntity>();
		List<Integer> complaintIdList = new ArrayList<Integer>();
		for (ComplaintEntity complaint : complaintLists) {
			complaintIdList.add(complaint.getId());
			complaint.setDealDepart(upgradeDealDepart);
			followNoteLists.add(fillFollowNoteByComplaint(complaint));
		}
		if(complaintIdList.size()>0){
			Map<String, Object> complaintMap = new HashMap<String, Object>();
			complaintMap.put("deal_depart", upgradeDealDepart);
			complaintMap.put("complaintIdList", complaintIdList);
			complaintMap.put("state", 1);
			complaintService.upgradeComplaintByIdList(complaintMap);
		}
		
		if(followNoteLists.size()>0){
			Map<String, Object> followNoteMap = new HashMap<String, Object>();
			followNoteMap.put("followNotList", followNoteLists);
			complaintFollowNoteService.insertList(followNoteMap);
		}
	}
	
	private ComplaintFollowNoteEntity fillFollowNoteByComplaint(ComplaintEntity complaint){
		ComplaintFollowNoteEntity complaintFollowNote =new ComplaintFollowNoteEntity();
		complaintFollowNote.setComplaintId(complaint.getId());
		complaintFollowNote.setDealDept(complaint.getDealDepart());
		complaintFollowNote.setIsSys(1);
		complaintFollowNote.setNotePeople(0);
		complaintFollowNote.setPeopleName(Constans.SYSTEM_ADMINISTRATOR_ACCOUNT);
		complaintFollowNote.setFlowName(Constans.UPGRADE_COMPLAINT_INFO);
		complaintFollowNote.setContent(Constans.OVERTIMEUPGRADE);
		return complaintFollowNote;
	}
}
