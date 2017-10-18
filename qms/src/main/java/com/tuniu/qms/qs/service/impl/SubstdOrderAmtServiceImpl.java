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
import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.qs.dao.SubstdOrderAmtMapper;
import com.tuniu.qms.qs.dto.SubstdOrderAmtDto;
import com.tuniu.qms.qs.model.SubstdOrderAmt;
import com.tuniu.qms.qs.service.SubstdOrderAmtService;
import com.tuniu.qms.qs.util.QsConstant;

@Service
public class SubstdOrderAmtServiceImpl implements SubstdOrderAmtService {
	
	@Autowired
	private SubstdOrderAmtMapper mapper;
	
	@Autowired
	private DataMapper dataMapper;
	
	@Override
	public void createSubstdOrderAmtSnapshot() {
		java.sql.Date yesterday = DateUtil.getSqlYesterday();
		//mapper.deleteByStatisticDate(yesterday);
		
		int pageSize = 2000;
		int dataLimitStart = 0;
		while (true) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yesterday", yesterday);
			map.put("dataLimitStart", dataLimitStart);
			map.put("pageSize", pageSize);
			List<SubstdOrderAmt> ordList = dataMapper.listOrderAmt(map);
			if (!ordList.isEmpty()) {
				List<SubstdOrderAmt> soList = new ArrayList<SubstdOrderAmt>();
				for (SubstdOrderAmt ord : ordList) {
					double nonCollectionAmount = MathUtil.sub(ord.getValidAmount(), ord.getCollectionAmount());
					ord.setNonCollectionAmount(nonCollectionAmount);
					int collectTimeoutFlag = getCollectTimeoutFlag(ord);
					String signDateStr = ord.getSignDate().toString();
					String yesterdayStr = DateUtil.getSqlYesterday().toString();
					if ((ord.getContractAmount() <= 0 && yesterdayStr.equals(signDateStr)) // 合同价小于等于0
							|| collectTimeoutFlag == Constant.YES) { // 订单收款超时
						soList.add(ord);
					}
				}
				
				if (!soList.isEmpty()) {
					mapper.addBatch(soList);
				}
			} else {
				break;
			}
			dataLimitStart += pageSize;
		}
	}
	
	private int getCollectTimeoutFlag(SubstdOrderAmt ord) {
		int collectTimeoutFlag = Constant.NO;
		java.sql.Date backDate = ord.getBackDate();//20151207  backDate改为出游日期 其他不变
		if (null != backDate && ord.getNonCollectionAmount() > 0) {

			String back_date = DateUtil.formatAsDefaultDate(backDate);
			String exit_date =DateUtil.formatAsDefaultDate( DateUtil.addSqlDates(DateUtil.getSqlYesterday(), 7));//出境+7天
			String domestic_date =DateUtil.formatAsDefaultDate( DateUtil.addSqlDates(DateUtil.getSqlYesterday(), 3));//团队国内+3天
			String other_date =DateUtil.formatAsDefaultDate( DateUtil.addSqlDates(DateUtil.getSqlYesterday(), 1));//其他+1天
			//统计日期+7天 是出游日期的 出境数据
			if(ord.getDestName().contains(QsConstant.DEP_NAME_EXIT) &&
					back_date.equals(exit_date)){
				
				collectTimeoutFlag = Constant.YES;
			}else if(ord.getProductType().equals(QsConstant.PRODUCT_TYPE)&&
					  ord.getDestName().contains(QsConstant.DEP_NAME_DOMESTIC) &&
					     back_date.equals(domestic_date)){
				
				collectTimeoutFlag = Constant.YES;
				
			}else{
				
			 if(!ord.getDestName().contains(QsConstant.DEP_NAME_EXIT) && back_date.equals(other_date) ){
				 
				 if(ord.getProductType().equals(QsConstant.PRODUCT_TYPE)&&
						  ord.getDestName().contains(QsConstant.DEP_NAME_DOMESTIC) ){
					 
				 }else{
					 
					 collectTimeoutFlag = Constant.YES;
				 }
				 
			 }
			
			}
			
		}
		
		return collectTimeoutFlag;
	}

	@Override
	public void add(SubstdOrderAmt obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(SubstdOrderAmt obj) {
		mapper.update(obj);
	}

	@Override
	public SubstdOrderAmt get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<SubstdOrderAmt> list(SubstdOrderAmtDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(SubstdOrderAmtDto dto) {
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
	}

	@Override
	public List<String> getBusinessUnits(SubstdOrderAmtDto dto) {
		return mapper.getBusinessUnits(dto);
	}

	@Override
	public List<String> getPrdDeps(SubstdOrderAmtDto dto) {
		return mapper.getPrdDeps(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDepByNum(SubstdOrderAmtDto dto) {
		return mapper.statGraphDepByNum(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDepByTotAmount(SubstdOrderAmtDto dto) {
		return mapper.statGraphDepByTotAmount(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDateByNum(SubstdOrderAmtDto dto) {
		return mapper.statGraphDateByNum(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDateByTotAmount(SubstdOrderAmtDto dto) {
		return mapper.statGraphDateByTotAmount(dto);
	}

	/**
	 * 获取前一天所有的数据 按照未付金额倒序排列
	 * @param dto
	 * @return
	 */
	public List<SubstdOrderAmt> listAll(SubstdOrderAmtDto dto) {
		
		return mapper.listAll(dto);
	}

}
