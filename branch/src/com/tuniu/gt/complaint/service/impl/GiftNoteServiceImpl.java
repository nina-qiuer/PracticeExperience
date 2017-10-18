package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.dao.impl.GiftNoteDao;
import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.GiftEntity;
import com.tuniu.gt.complaint.entity.GiftNoteEntity;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IGiftNoteService;
import com.tuniu.gt.toolkit.DateUtil;
@Service("complaint_service_complaint_impl-gift_note")
public class GiftNoteServiceImpl extends ServiceBaseImpl<GiftNoteDao> implements IGiftNoteService {
	
	@Autowired
	@Qualifier("complaint_dao_impl-gift_note")
	public void setDao(GiftNoteDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService followNoteService;

	@Override
	public void addGiftApplyToOa(ComplaintSolutionEntity csEnt) {
		List<GiftNoteEntity> giftNoteList = csEnt.getGiftInfoList();
		//数据写入订单数据库gift_order表
		for (GiftNoteEntity gnEnt : giftNoteList) {
			Map<String, Object> giftNoteMap = new HashMap<String, Object>();
			giftNoteMap.put("request_user", csEnt.getDealId()); //申请人
			giftNoteMap.put("request_time", DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss")); //申请时间
			giftNoteMap.put("get_type", gnEnt.getExpress()); //领取方式
			giftNoteMap.put("request_id", csEnt.getOrderId()); //订单号
			giftNoteMap.put("complaint_id", csEnt.getComplaintId());
			if (gnEnt.getExpress() == 2) { // 领取方式 1本人领取,2 快递领取,若本人领取,不传快递信息
				List<HashMap<String, Object>> expressTemp = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> param = new HashMap<String, Object>();
				param.put("name", gnEnt.getReceiver()); //姓名
				param.put("tel", gnEnt.getPhone());   //电话
				param.put("address", gnEnt.getAddress());   //地址
				param.put("remarks", gnEnt.getRemark());  //备注
				expressTemp.add(param);
				giftNoteMap.put("express_info", expressTemp);  //收件人信息
			}
			
			//礼品信息
			List<GiftEntity> giftList = gnEnt.getGiftList();
			List<Map<String, Object>> giftTemp = new ArrayList<Map<String, Object>>();
			for (GiftEntity gift : giftList) {
				Map<String, Object> paramGift = new HashMap<String, Object>();
				paramGift.put("id", gift.getGiftId());
				paramGift.put("num", gift.getNumber());
				giftTemp.add(paramGift);
			}
			giftNoteMap.put("items", giftTemp); //礼品信息
			Map<String, Object> sendResult = ComplaintRestClient.addGiftApplyToOa(giftNoteMap);
			if (sendResult != null) {
				//添加有效操作记录
				String noteContent = Constans.SUBMIT_GIFT_SOLUTION + "， 出库记录id：" + sendResult.get("oaId") + "， 点击以下链接查看：<a target='_blank' href='" + sendResult.get("oaUrl") + "'>" + sendResult.get("oaUrl") + "</a>";
				followNoteService.addFollowNote(csEnt.getComplaintId(), csEnt.getDealId(), csEnt.getDealName(), 
						noteContent, 0, "礼品申请");
			}
		}
	}

}
