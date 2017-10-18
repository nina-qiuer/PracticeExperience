package com.tuniu.gt.complaint.dao.impl;

import java.util.List;
import java.util.Map;

import com.tuniu.gt.complaint.entity.ComplaintSolutionEntity;
import com.tuniu.gt.complaint.entity.GiftNoteEntity;
import com.tuniu.gt.complaint.entity.SolutionTourticketEntity;
import com.tuniu.gt.complaint.entity.SolutionVoucherEntity;
import com.tuniu.gt.complaint.dao.sqlmap.imap.IComplaintSolutionMap; 

import tuniu.frm.service.Constant;
import tuniu.frm.core.DaoBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository("complaint_dao_impl-complaint_solution")
public class ComplaintSolutionDao extends DaoBase<ComplaintSolutionEntity, IComplaintSolutionMap>  implements IComplaintSolutionMap {
	public ComplaintSolutionDao() {  
		tableName = Constant.appTblPreMap.get("complaint") + "complaint_solution";		
	}
	
	@Autowired
	@Qualifier("complaint_dao_impl-solution_tourticket")
	private SolutionTourticketDao tourticketDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-solution_voucher")
	private SolutionVoucherDao voucherDao;
	
	@Autowired
	@Qualifier("complaint_dao_impl-gift_note")
	private GiftNoteDao giftNoteDao;
	
	@Autowired
	@Qualifier("complaint_dao_sqlmap-complaint_solution")
	public void setMapper(IComplaintSolutionMap mapper) {
		this.mapper = mapper;
	}
	
	public ComplaintSolutionEntity getByComplaintId(Integer complaintId) {
		return mapper.getByComplaintId(complaintId);
	}
	
	public void insertComplaintSolution(ComplaintSolutionEntity entity) {
		super.insert(entity);
		tourticketDao.insertList(entity.getId(), entity.getTourticketList());
		voucherDao.insertList(entity.getId(), entity.getVoucherList());
		giftNoteDao.insertList(entity.getId(), entity.getGiftInfoList());
	}
	
	public void logicDel(ComplaintSolutionEntity entity) {
		List<SolutionTourticketEntity> stList = entity.getTourticketList();
		if (null != stList) { // 删除旅游券相关数据
			for (SolutionTourticketEntity st : stList) {
				st.setDelFlag(1);
				tourticketDao.update(st);
			}
		}
		
		List<SolutionVoucherEntity> svList = entity.getVoucherList();
		if (null != svList) { // 删除抵用券相关数据
			for (SolutionVoucherEntity sv : svList) {
				sv.setDelFlag(1);
				voucherDao.update(sv);
			}
		}
		
		List<GiftNoteEntity> gnList = entity.getGiftInfoList();
		if (null != gnList) {
			for (GiftNoteEntity gn : gnList) {
				giftNoteDao.logicDel(gn);
			}
		}
		entity.setDelFlag(0);
		super.update(entity);
	}
	
	public List<ComplaintSolutionEntity> getNeedRepairList(int scope) {
		return mapper.getNeedRepairList(scope);
	}
	
	public List<ComplaintSolutionEntity> getCmpSolutionByCmpId(Map<String, Object> map){
		
		return mapper.getCmpSolutionByCmpId(map);
	}
	
	public List<ComplaintSolutionEntity> getNeedSynchSolution(Map<String, Object> map){
		return mapper.getNeedSynchSolution(map);
	}
	
	@Override
	public void updateCardUniqueId(Map<String, Object> map){
		mapper.updateCardUniqueId(map);
	}
	
	@Override
	public List<ComplaintSolutionEntity> getComplaintIdByReFundId(Map<String, Object> paramMap){
		return mapper.getComplaintIdByReFundId(paramMap);
	}
}
