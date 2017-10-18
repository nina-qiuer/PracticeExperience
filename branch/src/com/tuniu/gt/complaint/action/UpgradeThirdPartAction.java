package com.tuniu.gt.complaint.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.common.BaseEntity;
import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintThirdPartEntity;
import com.tuniu.gt.complaint.enums.DataDictionaryConfigEnum;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IComplaintThirdPartService;
import com.tuniu.gt.complaint.service.IDataDictionaryConfigService;
import com.tuniu.gt.toolkit.JsonUtil;

import tuniu.frm.core.FrmBaseAction;

@Service("complaint_action-upgrade_third_part")
@Scope("prototype")
public class UpgradeThirdPartAction extends FrmBaseAction<IComplaintThirdPartService, ComplaintThirdPartEntity> {

	private static Logger logger = Logger.getLogger(UpgradeThirdPartAction.class);

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_third_part")
	public void setService(IComplaintThirdPartService service) {
		this.service = service;
	};

	@Autowired
	@Qualifier("complaint_service_complaint_impl-data_dictionary_config")
	private IDataDictionaryConfigService dataDictionaryConfigService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;

	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService followNoteService;

	public String execute() {
		Integer complaint_id = Integer.valueOf(request.getParameter("complaintId"));
		Map<String, Object> thirdPartMap = new HashMap<String, Object>();
		thirdPartMap.put("complaint_id", complaint_id);
		Integer thirdPartCount = service.getComplaintThirdPartCount(thirdPartMap);
		if (thirdPartCount == 0) {
			List<BaseEntity> thirdPartTypes = dataDictionaryConfigService
					.getDictionaryConfigLists(DataDictionaryConfigEnum.UPGRADE_THIRD_PART_COMEFROM.getValue());
			request.setAttribute("thirdPartTypes", thirdPartTypes);
			entity.setComplaint_id(complaint_id);
			return "upgrade_bill";
		} else {
			request.setAttribute("complaint_id", complaint_id);
			return "upgrade_list";
		}
	}

	public String getComplaintThirdPartLists() {
		Integer complaint_id = Integer.valueOf(request.getParameter("complaint_id"));
		Map<String, Object> thirdPartMap = new HashMap<String, Object>();
		thirdPartMap.put("complaint_id", complaint_id);
		List<ComplaintThirdPartEntity> thirdPartLists = service.getComplaintThirdPartLists(thirdPartMap);
		JsonUtil.writeListResponse(thirdPartLists);
		return "upgrade_list";
	}

	public String getComplaintThirdPart() {
		String id = request.getParameter("id");
		List<BaseEntity> thirdPartTypes = dataDictionaryConfigService
				.getDictionaryConfigLists(DataDictionaryConfigEnum.UPGRADE_THIRD_PART_COMEFROM.getValue());
		request.setAttribute("thirdPartTypes", thirdPartTypes);
		if (id != null) {
			entity = (ComplaintThirdPartEntity) service.get(id);
			Integer thirdTypeId = entity.getThird_type();
			if (thirdTypeId != 0) {
				Integer fatherConfigId = DataDictionaryConfigEnum.UPGRADE_THIRD_PART_CITY.getValue();
				if (DataDictionaryConfigEnum.UPGRADE_THIRD_MEDIA.getValue().equals(thirdTypeId)) {
					fatherConfigId = DataDictionaryConfigEnum.UPGRADE_THIRD_PART_MEDIA.getValue();
				}
				List<BaseEntity> thirdPartSecondTypes = dataDictionaryConfigService
						.getDictionaryConfigLists(fatherConfigId);
				request.setAttribute("thirdPartSecondTypes", thirdPartSecondTypes);
			}
		} else {
			String complaint_id = request.getParameter("complaint_id");
			if (complaint_id != null) {
				entity.setComplaint_id(Integer.valueOf(complaint_id));
			}
		}
		return "upgrade_bill";
	}

	public String getThirdPartNextType() {
		try {
			Integer type_id = Integer.valueOf(request.getParameter("type_id"));
			Integer fatherConfigId = DataDictionaryConfigEnum.UPGRADE_THIRD_PART_CITY.getValue();
			if (DataDictionaryConfigEnum.UPGRADE_THIRD_MEDIA.getValue().equals(type_id)) {
				fatherConfigId = DataDictionaryConfigEnum.UPGRADE_THIRD_PART_MEDIA.getValue();
			}
			List<BaseEntity> thirdPartSecondTypes = dataDictionaryConfigService
					.getDictionaryConfigLists(fatherConfigId);
			JsonUtil.writeListResponse(thirdPartSecondTypes);
		} catch (Exception e) {
			JsonUtil.writeErrorResponse();
		}
		return "upgrade_bill";
	}

	public String submitThirdPart() {
		try {
			if (entity.getId() != null) {
				entity.setUpdate_id(user.getId());
				service.update(entity);
				followNoteService.addFollowNote(entity.getComplaint_id(), user.getId(), user.getRealName(),
						Constans.MERGE_UPGRADE_THIRD_PART, 0, Constans.UPGRADE_THIRD_PART);
			} else {
				getInfoFromComplaint(entity);
				entity.setAdd_id(user.getId());
				service.insert(entity);
				followNoteService.addFollowNote(entity.getComplaint_id(), user.getId(), user.getRealName(),
						Constans.ADD_UPGRADE_THIRD_PART, 0, Constans.UPGRADE_THIRD_PART);
			}
			JsonUtil.writeResponse("success");
		} catch (Exception e) {
			logger.error("submitThirdPart error" + e.getMessage(), e);
			JsonUtil.writeResponse("failed");
		}
		return "upgrade_bill";
	}

	public String delThirdPart() {
		try {
			Integer third_part_id = Integer.valueOf(request.getParameter("third_part_id"));
			entity = (ComplaintThirdPartEntity) service.get(third_part_id);
			entity.setUpdate_id(user.getId());
			entity.setDel_flag(1);// 设置已删除
			service.update(entity);
			followNoteService.addFollowNote(entity.getComplaint_id(), user.getId(), user.getRealName(),
					Constans.DELETE_UPGRADE_THIRD_PART, 0, Constans.UPGRADE_THIRD_PART);
			JsonUtil.writeResponse("success");
		} catch (Exception e) {
			logger.error("delThirdPart error" + e.getMessage(), e);
			JsonUtil.writeResponse("failed");
		}
		return "upgrade_bill";
	}

	private void getInfoFromComplaint(ComplaintThirdPartEntity entity) {
		ComplaintEntity complaint = (ComplaintEntity) complaintService.get(entity.getComplaint_id());
		entity.setDeal_depart(complaint.getDealDepart());
		entity.setDeal_id(complaint.getDeal());
		entity.setDeal_name(complaint.getDealName());
	}
}
