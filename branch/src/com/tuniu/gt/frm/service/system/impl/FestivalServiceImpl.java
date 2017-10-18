package com.tuniu.gt.frm.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.frm.dao.impl.FestivalDao;
import com.tuniu.gt.frm.entity.FestivalEntity;
import com.tuniu.gt.frm.service.system.IFestivalService;
import com.tuniu.gt.toolkit.DateUtil;
@Service("frm_service_system_impl-festival")
public class FestivalServiceImpl extends ServiceBaseImpl<FestivalDao> implements IFestivalService {

	@Autowired
	@Qualifier("frm_dao_impl-festival")
	public void setDao(FestivalDao dao) {
		this.dao = dao;
	}
	
	@Autowired
    @Qualifier("tspService")
	private IComplaintTSPService tspService;

	@Override
	public List<String> getFYearList() {
		return dao.getFYearList();
	}

	@Override
	public List<FestivalEntity> getFesNameList() {
		return dao.getFesNameList();
	}

	@Override
	public void batchInsert(List<FestivalEntity> entList) {
		dao.batchInsert(entList);
	}

	@Override
	public void delFes(Map<String, Object> params) {
		dao.delFes(params);
	}

	@Override
	public Date getWorkDaysEndTime(int dateNum) {
		return getWorkDaysEndTime(dateNum, new Date());
	}

	@Override
	public Date getWorkDaysEndTime(int dateNum, Date bgnTime) {
		if(tspService.getRestdayType(bgnTime).equals(1)||tspService.getRestdayType(bgnTime).equals(2)){
			bgnTime = DateUtil.getNexDateNine(bgnTime);
		}
		
		Date endTime = new Date();
		for (int i=0; i<=dateNum; i++) {
			endTime = DateUtil.getSomeDay(bgnTime, i);
            if (tspService.getRestdayType(endTime).equals(1)||tspService.getRestdayType(endTime).equals(2)) { // 如果是休息日，则往后推一天
            	dateNum++;
            }
		}
		return endTime;
	}
	
	private List<java.sql.Date> getFestivalList(List<FestivalEntity> festivalList, int type) {
		List<java.sql.Date> list = new ArrayList<java.sql.Date>();
		for (FestivalEntity ent : festivalList) {
			if (type == ent.getDateType()) {
				list.add(ent.getFestivalDate());
			}
		}
		return list;
	}

}
