package com.tuniu.gt.complaint.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.entity.GiftEntity;
import com.tuniu.gt.complaint.entity.GiftNoteEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IGiftNoteService;
import com.tuniu.gt.complaint.service.IGiftService;
import com.tuniu.gt.uc.entity.DepartmentEntity;
import com.tuniu.gt.uc.service.user.IDepartmentService;


@Service("complaint_action-gift_note")
@Scope("prototype")
public class GiftNoteAction extends FrmBaseAction<IGiftNoteService,GiftNoteEntity> { 
	
	private static final long serialVersionUID = 1L;

	public GiftNoteAction() {
		setManageUrl("gift_note");
	}
	// 引入关联礼品dao
	@Autowired
	@Qualifier("complaint_service_complaint_impl-gift")
	private IGiftService giftService;

	// 引入投诉service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
		
	@Autowired
	@Qualifier("complaint_service_complaint_impl-gift_note")
	public void setService(IGiftNoteService service) {
		this.service = service;
	};
	
	@Autowired
	@Qualifier("uc_service_user_impl-department")
	private IDepartmentService depService;
	
	//引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	@SuppressWarnings("unchecked")
	public String execute() {
		this.setPageTitle("礼品申请");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		String complaintId = request.getParameter("complaintId");
		String orderId = request.getParameter("orderId");
		request.setAttribute("complaintId", complaintId);
		request.setAttribute("orderId", orderId);
		paramMap.put("complaintId", complaintId);
		// 获取解决方案的礼品列表
		List<GiftEntity> giftList = (List<GiftEntity>) giftService.fetchListMap(paramMap);
		request.setAttribute("giftList", giftList);

		String userName = user.getRealName();
		Integer departmentId = user.getDepId();
		Integer userId = user.getId();
		
		DepartmentEntity depInfo = (DepartmentEntity) depService.fetchOne(departmentId);
		String depName = depInfo.getDepName();
		
		request.setAttribute("userName", userName);
		request.setAttribute("userId", userId);
		request.setAttribute("departmentId", departmentId);
		request.setAttribute("departmentName", depName);
		
		List<GiftNoteEntity> giftNote = (List<GiftNoteEntity>) service.fetchListMap(paramMap);
		if (giftNote != null && giftNote.size() > 0) {
			request.setAttribute("entity", giftNote.get(0));
		}
		return "gift_note";
	}

	/**
	 * 增加礼品申请 保存礼品申请记录，同时向接口发送数据（目前接口还未提供）
	 * 
	 * @return
	 */
	public String addGiftNote() {

		Date date = new Date();
		
		String giftNoteId = request.getParameter("entity.id");
		
		entity.setAddTime(date);
		entity.setUpdateTime(date);
		entity.setDelFlag(1);
		
		// 如果id有值，更新对象否则新增
		if (giftNoteId != null && !"".equals(giftNoteId)) {
			service.update(entity);
		} else {
			service.insert(entity);
		}
		
		//数据写入订单数据库gift_order表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("request_user", entity.getUserId()); //申请人
		paramMap.put("request_time", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(entity.getUpdateTime())); //申请时间
		paramMap.put("get_type", entity.getExpress()); //领取方式
		paramMap.put("request_id", entity.getOrderId()); //订单号
		paramMap.put("complaint_id", entity.getComplaintId());
		
		//领取方式 1本人领取,2 快递领取,若本人领取,不传快递信息
		if (entity.getExpress() == 2) {
			List<HashMap<String, Object>> expressTemp = new ArrayList<HashMap<String, Object>>();
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("name", entity.getReceiver()); //姓名
			param.put("tel", entity.getPhone());   //电话
			param.put("address", entity.getAddress());   //地址
			param.put("remarks", entity.getRemark());  //备注
			expressTemp.add(param);
			paramMap.put("express_info", expressTemp);  //收件人信息
		}
		
		//礼品信息
		List<GiftEntity> giftList = (List<GiftEntity>) giftService.searchByComplaintId(entity.getComplaintId());
		List<HashMap<String, Object>> giftTemp = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> paramGift = null;
		if (giftList != null && giftList.size() > 0) {
			for (int i = 0; i < giftList.size(); i++) {
				paramGift = new HashMap<String, Object>();
				paramGift.put("id", giftList.get(i).getGiftId());
				paramGift.put("num", giftList.get(i).getNumber());
				giftTemp.add(paramGift);
			}
		}
		paramMap.put("items", giftTemp); //礼品信息
		Map<String, Object> sendResult = ComplaintRestClient.addGiftApplyToOa(paramMap);
		
		if (sendResult != null) {
			//添加有效操作记录
			String noteContent = Constans.SUBMIT_GIFT_SOLUTION + "， 出库记录id：" + sendResult.get("oaId") + "， 点击以下链接查看：<a target='_blank' href='" + sendResult.get("oaUrl") + "'>" + sendResult.get("oaUrl") + "</a>";
			complaintFollowNoteService.addFollowNote(entity.getComplaintId(), user.getId(), user.getRealName(), noteContent,0,"礼品申请");
		}
		complaintService.updateComplaintUpdateTime(entity.getComplaintId());
		return "gift_note";
	}

	
}
