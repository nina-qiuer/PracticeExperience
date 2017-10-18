package com.tuniu.qms.qs.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.bi.dao.OrderExtMapper;
import com.tuniu.bi.dto.OrderExtDto;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.qs.dao.SubstdOrderSnapshotMapper;
import com.tuniu.qms.qs.dto.SubstdOrderSnapshotDto;
import com.tuniu.qms.qs.model.DestcateStandard;
import com.tuniu.qms.qs.model.SubstdOrderSnapshot;
import com.tuniu.qms.qs.service.DestcateStandardService;
import com.tuniu.qms.qs.service.SubstdOrderSnapshotService;

@Service
public class SubstdOrderSnapshotServiceImpl implements SubstdOrderSnapshotService {

	@Autowired
	private SubstdOrderSnapshotMapper mapper;
	
	@Autowired
	private OrderExtMapper ordExtMapper;
	
	@Autowired
	private DestcateStandardService stdService;
	
	private List<DestcateStandard> dsList;
	
	private DestcateStandard getStandard(String destCate) {
		for (DestcateStandard std : dsList) {
			if (destCate.equals(std.getDestCate())) {
				return std;
			}
		}
		return null;
	}
	
	@Override
	public void syncOrderExt() {
		mapper.deleteAllOrdExts();
		
		int pageSize = 1000;
		int dataLimitStart = 0;
		while (true) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yesterday", DateUtil.getSqlYesterday());
			map.put("dataLimitStart", dataLimitStart);
			map.put("pageSize", pageSize);
			List<OrderExtDto> ordExtList = ordExtMapper.list(map);
			if (null != ordExtList && !ordExtList.isEmpty()) {
				mapper.addOrdExts(ordExtList);
			} else {
				break;
			}
			dataLimitStart += pageSize;
		}
	}
	
	@Override
	public void createSubstdOrderSnapshot() {
		java.sql.Date statisticDate = DateUtil.getSqlYesterday();
		mapper.deleteByStatisticDate(statisticDate);
		
		dsList = stdService.getDestStdCache();
		
		int pageSize = 2000;
		int dataLimitStart = 0;
		while (true) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dataLimitStart", dataLimitStart);
			params.put("pageSize", pageSize);
			List<SubstdOrderSnapshot> ordList = mapper.listFromOrderExt(params);
			if (!ordList.isEmpty()) {
				List<SubstdOrderSnapshot> substdOrdList = new ArrayList<SubstdOrderSnapshot>();
				for (SubstdOrderSnapshot ord : ordList) {
					int timeOutDays = getTimeOutDays(ord, statisticDate);
					if (timeOutDays > 0) { // 超时
						ord.setStatisticDate(statisticDate); // 设置统计日期
						ord.setTimeOutDays(timeOutDays); // 设置超时天数
						substdOrdList.add(ord);
					}
				}
				
				if (!substdOrdList.isEmpty()) {
					mapper.addBatch(substdOrdList);
				}
			} else {
				break;
			}
			dataLimitStart += pageSize;
		}
	}
	
	/** 计算超时天数 */
	private int getTimeOutDays(SubstdOrderSnapshot ord, java.sql.Date statisticDate) {
		int timeOutDays = 0;
		DestcateStandard std = getStandard(ord.getDestCate());
		if (null != std) {
			java.sql.Date sendDate = ord.getNoticeSendDate();
			if (null == sendDate) {
				sendDate = statisticDate;
			}
			int days = DateUtil.getDaysBetween(sendDate, ord.getDepartDate());
			timeOutDays = std.getNoticeTimeLimit() - days;
		}
		return timeOutDays;
	}
	
	@Override
	public void createDepOrdNumSnapshot() {
		java.sql.Date statisticDate = DateUtil.getSqlYesterday();
		mapper.deleteDepOrdNumSnapshot(statisticDate);
		mapper.addDepOrdNumSnapshot(statisticDate);
	}
	
	@Override
	public void add(SubstdOrderSnapshot obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(SubstdOrderSnapshot obj) {
		mapper.update(obj);
	}

	@Override
	public SubstdOrderSnapshot get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<SubstdOrderSnapshot> list(SubstdOrderSnapshotDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(SubstdOrderSnapshotDto dto) {
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
	}
	
	@Override
	public List<String> getBusinessUnits(SubstdOrderSnapshotDto dto) {
		return mapper.getBusinessUnits(dto);
	}

	@Override
	public List<String> getPrdDeps(SubstdOrderSnapshotDto dto) {
		return mapper.getPrdDeps(dto);
	}

	@Override
	public List<Map<String, Object>> statDepNoticeTimeOutRate(SubstdOrderSnapshotDto dto) {
		List<Map<String, Object>> dataList = mapper.statDepNoticeTimeOutRate(dto);
		putTimeOutRate(dataList);
		return dataList;
	}

	@Override
	public List<Map<String, Object>> statDateNoticeTimeOutRate(SubstdOrderSnapshotDto dto) {
		List<Map<String, Object>> dataList = mapper.statDateNoticeTimeOutRate(dto);
		putTimeOutRate(dataList);
		return dataList;
	}
	
	private void putTimeOutRate(List<Map<String, Object>> dataList) {
		for (Map<String, Object> map : dataList) {
			BigDecimal noticeTimeOutOrdNumTotal = (BigDecimal) map.get("noticeTimeOutOrdNumTotal");
			BigDecimal signedOrdNumTotal = (BigDecimal) map.get("signedOrdNumTotal");
			Double rate = MathUtil.div(noticeTimeOutOrdNumTotal.doubleValue(), signedOrdNumTotal.doubleValue(), 3);
			map.put("rate", MathUtil.mul(rate, 100));
		}
	}

}
