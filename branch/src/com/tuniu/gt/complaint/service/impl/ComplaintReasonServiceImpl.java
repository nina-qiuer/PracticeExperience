package com.tuniu.gt.complaint.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.Constans;
import com.tuniu.gt.complaint.dao.impl.ComplaintDao;
import com.tuniu.gt.complaint.dao.impl.ComplaintReasonDao;
import com.tuniu.gt.complaint.dao.impl.QcDao;
import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.ComplaintReasonEntity;
import com.tuniu.gt.complaint.entity.FollowNoteThEntity;
import com.tuniu.gt.complaint.entity.QcEntity;
import com.tuniu.gt.complaint.entity.ReasonSynchCrmEntity;
import com.tuniu.gt.complaint.enums.DealDepartEnum;
import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintFollowNoteService;
import com.tuniu.gt.complaint.service.IComplaintReasonService;
import com.tuniu.gt.complaint.service.IFollowNoteThService;
import com.tuniu.gt.complaint.service.IQcService;
import com.tuniu.gt.complaint.util.CommonUtil;
import com.tuniu.gt.complaint.util.ComplaintUtil;
import com.tuniu.gt.complaint.vo.QcVo;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.frm.service.system.IUserService;
import com.tuniu.gt.toolkit.RTXSenderThread;

@Service("complaint_service_complaint_reason_impl-complaint_reason")
public class ComplaintReasonServiceImpl extends ServiceBaseImpl<ComplaintReasonDao> implements IComplaintReasonService {
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint_reason")
	public void setDao(ComplaintReasonDao dao) {
		this.dao = dao;
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-complaint")
	private ComplaintDao compDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-qc")
	private QcDao qcDao;
	
	// qc service
	@Autowired
	@Qualifier("complaint_service_impl-qc")
	private IQcService qcService;
	
	// 用户service
	@Autowired
	@Qualifier("frm_service_system_impl-user")
	private IUserService userService;
	
	@Autowired
	@Qualifier("complaint_service_impl-follow_note_th")
	private IFollowNoteThService noteThService;
	
	//引入跟进记录service
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint_follow_note")
	private IComplaintFollowNoteService complaintFollowNoteService;
	
	public List<Map> queryReasonById(Integer complaintId) {
		return dao.queryReasonById(complaintId);
	}

	public Integer queryReasonCountById(Integer complaintId) {
		return dao.queryReasonCountById(complaintId);
	}

	@Override
	public List<ComplaintReasonEntity> getReasonAndQualitycostList(Map<String, Object> paramMap) {
		return dao.getReasonAndQualitycostList(paramMap);
	}

	@Override
	public void insertReasonList (int userId, String userName, int compId, String compCity, List<ComplaintReasonEntity> reasonList,Integer specialEventFlag) throws Exception{
		dao.insertReasonList(compId, reasonList);
		
		ComplaintEntity compEnt = (ComplaintEntity) compDao.fetchOne(compId);
		compEnt.setComplaintNum(compEnt.getComplaintNum() + 1);
		compEnt.setRepeateTime(new Date());
		if (2 == compEnt.getState()) { // 在处理中才加标记
			if (2 == compEnt.getIsSticky() || 3 == compEnt.getIsSticky()) {
				compEnt.setIsSticky(3); // 重复投诉新增投诉事宜
			} else {
				compEnt.setIsSticky(1); // 普通投诉新增投诉事宜
			}
			sendAddReasonRTXAlert(compEnt);
		}
		
		/* 插入投诉事宜到中间层 */
		if (null != compCity && !"".equals(compCity)) {
			String noteContent = reasonList.get(0).getTypeDescript();
			FollowNoteThEntity noteThEnt = new FollowNoteThEntity();
			noteThEnt.setComplaintId(compId);
			noteThEnt.setPersonId(userId);
			noteThEnt.setPersonName(userName);
			noteThEnt.setFlowName(Constans.SUBMIT_COMPLAINT_REASON);
			noteThEnt.setContent(noteContent);
			noteThService.insert(noteThEnt);
			
			compEnt.setCompCity(compCity);
			compEnt.setCompTimeTh(new Date());
			compEnt.setThFinishFlag(0);
		}
		if(specialEventFlag!=-1){//已发起的投诉单新增投诉事宜
			compEnt.setSpecial_event_flag(specialEventFlag);
		}
		compDao.update(compEnt);
		
		//更新咨询单状态为，0：投诉单；1：咨询单';
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("complaintId", compId);
		QcEntity qc = (QcEntity) qcDao.get(params);
		if (qc!=null && 1 == qc.getConsultation()) {
			Integer isConsultation = ComplaintUtil.judgeConsultation(reasonList, compEnt.getRouteType());
//			if (0 == isConsultation) { // 原来是咨询单，新增投诉事宜后非咨询单，则将质检单改为非咨询单
//				qc.setConsultation(isConsultation);
//				qcService.assignQcPerson(qc, compEnt);
//				qcDao.update(qc);
//				/* 非咨询单则发起质检到质量管理系统 */
//				QcVo qcVo = new QcVo();
//				qcVo.setPrdId(compEnt.getRouteId());
//				qcVo.setGroupDate(compEnt.getStartTime().getTime());
//				qcVo.setOrdId(compEnt.getOrderId());
//				qcVo.setCmpId(compEnt.getId());
//				qcVo.setQcAffairDesc(getQcAffairDesc(compEnt.getReasonList()));
//				ComplaintRestClient.addQcBill(qcVo);
//			}
		}
		
		//添加有效操作记录
		String noteContent = Constans.SUBMIT_COMPLAINT_REASON;
		complaintFollowNoteService.addFollowNote(compId, userId, userName, noteContent, 0, Constans.SUBMIT_COMPLAINT_REASON);
	}
	
	private String getQcAffairDesc(List<ComplaintReasonEntity> reasonList) {
		StringBuffer sb = new StringBuffer();
		for (ComplaintReasonEntity r : reasonList) {
			sb.append(r.getType()).append("-").append(r.getSecondType())
			.append("：").append(r.getTypeDescript()).append("<br>");
		}
		return sb.toString();
	}
	/**
	 * 新增投诉事宜提醒
	 * @param compEnt
	 */
	private void sendAddReasonRTXAlert(ComplaintEntity compEnt) {
		String title = "新增投诉事宜提醒";
		String content = "订单[" + compEnt.getOrderId() + "]-投诉单[" + compEnt.getId() + "]新增投诉事宜\n" + 
							"处理人：" + compEnt.getDealName() + "\n交接人：" + compEnt.getAssociaterName() + "\n";
		Integer dealId = compEnt.getDeal();
		if (null != dealId && dealId > 0) {
			if (DealDepartEnum.IN_TRAVLE.getValue().equals(compEnt.getDealDepart()) 
			        || DealDepartEnum.AFTER_TRAVLE.getValue().equals(compEnt.getDealDepart()) 
			        || DealDepartEnum.AIR_TICKIT.getValue().equals(compEnt.getDealDepart())) {
				if (!CommonUtil.isStatus(userService.getExtensionByUserId(dealId))) { // 如果当前处理人不在班，发送给交接人
					int assoId = compEnt.getAssociater();
					if (0 == assoId || assoId==-1|| !CommonUtil.isStatus(userService.getExtensionByUserId(assoId))) { // 如果交接人不存在或不在班，发送处理人全组
						UserEntity userEnt = userService.getUserByID(dealId);
						if (null != userEnt) {
							List<UserEntity> sgUsers = new ArrayList<UserEntity>();
							UserEntity  guiyang = userService.getUserByID(9368); // 桂洋
							if(guiyang != null) {
							    sgUsers.add(guiyang);
							}
							if (DealDepartEnum.IN_TRAVLE.getValue().equals(compEnt.getDealDepart())) {
								sgUsers = userService.getUsersByDepartmentId(748); // 售后组
							} else if(DealDepartEnum.AFTER_TRAVLE.getValue().equals(compEnt.getDealDepart())){
							    Integer userDepId = userEnt.getDepId();
                                if(userDepId.equals(213)||userDepId.equals(5926)||userDepId.equals(1392)){ //一组、二组、外联跨组提醒
                                    sgUsers = userService.getUsersByDepartmentId(213);
                                    sgUsers.addAll(userService.getUsersByDepartmentId(5926));
                                    sgUsers.addAll(userService.getUsersByDepartmentId(1392));
                                }else{ //其他组的只提醒自己组
                                    sgUsers = userService.getSameGroupUsers(userEnt);
                                }
                                
                                UserEntity user = userService.getUserByID(1076);//戴周锋
                                sgUsers.add(user);
							} else {
							    sgUsers = userService.getSameGroupUsers(userEnt);
							}
							for (UserEntity user : sgUsers) {
								new RTXSenderThread(user.getId(), user.getUserName(), title, content).start();
							}
						}
					} else {
						String assoName = userService.getUserByID(assoId).getUserName();
						new RTXSenderThread(assoId, assoName, title, content).start();
					}
				} else {
					String dealUserName = userService.getUserByID(dealId).getUserName();
					new RTXSenderThread(dealId, dealUserName, title, content).start();
				}
			} else {
				String dealUserName = userService.getUserByID(dealId).getUserName();
				new RTXSenderThread(dealId, dealUserName, title, content).start(); // 发送给处理人
				
				int deptId = userService.getUserByID(dealId).getDepId();
				List<UserEntity> managerList = userService.getManageByDepartmentId(deptId);
				if (!managerList.isEmpty()) { // 发送给主管
					for (UserEntity manager : managerList) {
						if (260 != manager.getId()) { // 过滤孟锐丽
							new RTXSenderThread(manager.getId(), manager.getUserName(), title, content).start();
						}
					}
				}
				
				if (null != compEnt.getAssociater() && compEnt.getAssociater() > 0) {
					String assUserName = userService.getUserByID(compEnt.getAssociater()).getUserName();
					new RTXSenderThread(compEnt.getAssociater(), assUserName, title, content).start(); // 发送给交接人
				}
			}
		}
	}

	@Override
	public List<Map<String, Object>> getDistDesc(int complaintId) {
		return dao.getDistDesc(complaintId);
	}

    @Override
    public boolean isSameGroupComplaintWarningReason(ComplaintReasonEntity entity) {
        boolean isSameGroupComplaintWarningReason = false;
        String type = entity.getType(); //一级分类
        String secondType = entity.getSecondType();//二级分类
        if("导游领队".equals(type)){
            if(Arrays.asList("增加强迫购物".split(",")).contains(secondType)){
                isSameGroupComplaintWarningReason = true;
            }
        }else if("交通".equals(type)){
            if(Arrays.asList("事故特殊事件".split(",")).contains(secondType)){
                isSameGroupComplaintWarningReason = true;
            }
        }
        return isSameGroupComplaintWarningReason;
    }
	
    @Override
    public List<ReasonSynchCrmEntity> getCrmComplaintReason(Map<String, Object> paramMap){
    	return dao.getCrmComplaintReason(paramMap);
    }
    
    @Override
	public Integer getCrmComplaintReasonCount(Map<String, Object> paramMap){
		return dao.getCrmComplaintReasonCount(paramMap);
	}
}
