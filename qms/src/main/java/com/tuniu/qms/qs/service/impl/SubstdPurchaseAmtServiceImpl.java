package com.tuniu.qms.qs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.bi.dao.DataMapper;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dao.SubstdPurchaseAmtMapper;
import com.tuniu.qms.qs.dto.SubstdPurchaseAmtDto;
import com.tuniu.qms.qs.model.SubstdPurchaseAmt;
import com.tuniu.qms.qs.service.SubstdPurchaseAmtService;

@Service
public class SubstdPurchaseAmtServiceImpl implements SubstdPurchaseAmtService {
	
	@Autowired
	private SubstdPurchaseAmtMapper mapper;
	
	@Autowired
	private DataMapper dataMapper;
	
	@Override
	public void createSubstdPurchaseAmtSnapshot() {
		java.sql.Date yesterday = DateUtil.getSqlYesterday();
		//mapper.deleteByAddDate(yesterday);
		
		int pageSize = 2000;
		int dataLimitStart = 0;
		while (true) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yesterday", yesterday);
			map.put("dataLimitStart", dataLimitStart);
			map.put("pageSize", pageSize);
			List<SubstdPurchaseAmt> purchaseList = dataMapper.listPurchaseAmt(map);
			if (!purchaseList.isEmpty()) {
				List<SubstdPurchaseAmt> spList = new ArrayList<SubstdPurchaseAmt>();
				for (SubstdPurchaseAmt purchase : purchaseList) {
					int timeoutFlag = getTimeoutFlag(purchase);
					purchase.setTimeOutFlag(timeoutFlag);
					
					int diffMonthFlag = getDiffMonthFlag(purchase);
					purchase.setDiffMonthFlag(diffMonthFlag);
					
					boolean isSubstdNonpay = isSubstdNonpay(purchase);
					
					double totalAmount = purchase.getTotalAmount();
					
					double contractAmount = purchase.getContractAmount();
					
					if (1 == timeoutFlag // 添加超时
							|| 1 == diffMonthFlag // 跨月添加
							|| true == isSubstdNonpay // 酒店、门票非现付采购单金额小于0
							|| totalAmount < 0 // 订单负采购
							|| totalAmount > contractAmount) { // 订单负利润
						spList.add(purchase);
					}
				}
				
				if (!spList.isEmpty()) {
					mapper.addBatch(spList);
				}
			} else {
				break;
			}
			dataLimitStart += pageSize;
		}
	}
	
	private int getTimeoutFlag(SubstdPurchaseAmt purchase) {
		int timeoutFlag = Constant.NO;
		java.sql.Date backDate = purchase.getBackDate();
		java.sql.Date addDate = purchase.getAddDate();
		String type = purchase.getType();
		if ("地接".equals(type) || "导服费".equals(type)) {
			int backYear = DateUtil.getYear(backDate);
			int addYear = DateUtil.getYear(addDate);
			int backMonth = DateUtil.getMonth(backDate);
			int addMonth = DateUtil.getMonth(addDate);
			int backDay = DateUtil.getDay(backDate);
			
			if (addYear > backYear) { // 跨年必定跨月
				if(addYear - backYear ==1 && backMonth==12 && backDay>25){ //排除跨年12月 25之后的情况
					
				}else{
					
					timeoutFlag = Constant.YES;
				}
			
			}
			
			if (backDay > 25) { // 25日后出游归来，应采购月向后推一个月
				
				backMonth += 1;
			}
			
			if (addYear == backYear && addMonth > backMonth) {
				timeoutFlag = Constant.YES;
			}
		} else { // 非导服、地接，出游归来三日内未添加采购单的订单
			int days = DateUtil.getDaysBetween(backDate, addDate);
			if (days > 3) {
				timeoutFlag = Constant.YES;
			}
		}
		return timeoutFlag;
	}
	
	private int getDiffMonthFlag(SubstdPurchaseAmt purchase) {
		int diffMonthFlag = Constant.NO;
		java.sql.Date signDate = purchase.getSignDate();
		if (null != signDate) {
			int signYear = DateUtil.getYear(signDate);
			int addYear = DateUtil.getYear(purchase.getAddDate());
			int signMonth = DateUtil.getMonth(signDate);
			int addMonth = DateUtil.getMonth(purchase.getAddDate());
			double absMTA = Math.abs(purchase.getMonthTotalAmount());
			if (addYear > signYear && absMTA > 5000) { // 跨年必定跨月
				diffMonthFlag = Constant.YES;
			}
			if (addYear == signYear) {
				if (addMonth > signMonth && absMTA > 5000) { // 采购日期与签约日期跨月，且当月添加采购单金额之和的绝对值超过5000
					diffMonthFlag = Constant.YES;
				}
			}
		}
		return diffMonthFlag;
	}
	
	private boolean isSubstdNonpay(SubstdPurchaseAmt purchase) {
		boolean isSubstdNonpay = false;
		String type = purchase.getType();
		if (("酒店".equals(type) || "频道酒店".equals(type) || "门票".equals(type)) && 
				purchase.getPrice() < 0 && 0 == purchase.getPayFlag()) {
			isSubstdNonpay = true;
		}
		return isSubstdNonpay;
	}

	@Override
	public void add(SubstdPurchaseAmt obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(SubstdPurchaseAmt obj) {
		mapper.update(obj);
	}

	@Override
	public SubstdPurchaseAmt get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<SubstdPurchaseAmt> list(SubstdPurchaseAmtDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(SubstdPurchaseAmtDto dto) {
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
	}

	@Override
	public List<String> getBusinessUnits(SubstdPurchaseAmtDto dto) {
		return mapper.getBusinessUnits(dto);
	}

	@Override
	public List<String> getPrdDeps(SubstdPurchaseAmtDto dto) {
		return mapper.getPrdDeps(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDepByNum(SubstdPurchaseAmtDto dto) {
		return mapper.statGraphDepByNum(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDepByTotPrice(SubstdPurchaseAmtDto dto) {
		return mapper.statGraphDepByTotPrice(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDateByNum(SubstdPurchaseAmtDto dto) {
		return mapper.statGraphDateByNum(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDateByTotPrice(SubstdPurchaseAmtDto dto) {
		return mapper.statGraphDateByTotPrice(dto);
	}

}
