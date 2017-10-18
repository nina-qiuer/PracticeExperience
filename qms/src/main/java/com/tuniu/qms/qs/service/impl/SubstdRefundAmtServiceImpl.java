package com.tuniu.qms.qs.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.bi.dao.DataMapper;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qs.dao.SubstdRefundAmtMapper;
import com.tuniu.qms.qs.dto.SubstdRefundAmtDto;
import com.tuniu.qms.qs.model.SubstdRefundAmt;
import com.tuniu.qms.qs.service.SubstdRefundAmtService;

@Service
public class SubstdRefundAmtServiceImpl implements SubstdRefundAmtService {
	
	@Autowired
	private SubstdRefundAmtMapper mapper;
	
	@Autowired
	private DataMapper dataMapper;
	
	@Override
	public void createSubstdRefundAmtSnapshot() {
		java.sql.Date yesterday = DateUtil.getSqlYesterday();
		mapper.deleteByAddDate(yesterday);
		
		int pageSize = 2000;
		int dataLimitStart = 0;
		while (true) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yesterday", yesterday);
			map.put("dataLimitStart", dataLimitStart);
			map.put("pageSize", pageSize);
			List<SubstdRefundAmt> refundList = dataMapper.listRefundAmt(map);
			if (!refundList.isEmpty()) {
				List<SubstdRefundAmt> srList = new ArrayList<SubstdRefundAmt>();
				for (SubstdRefundAmt refund : refundList) {
					if (refund.getCashBeyondAmount() > 0 || refund.getOrderRefundAmount() > 0) { // 现金退款超额或总退款超额
						srList.add(refund);
					}
				}
				
				if (!srList.isEmpty()) {
					mapper.addBatch(srList);
				}
			} else {
				break;
			}
			dataLimitStart += pageSize;
		}
	}

	@Override
	public void add(SubstdRefundAmt obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(SubstdRefundAmt obj) {
		mapper.update(obj);
	}

	@Override
	public SubstdRefundAmt get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<SubstdRefundAmt> list(SubstdRefundAmtDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(SubstdRefundAmtDto dto) {
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
	}

	@Override
	public List<String> getBusinessUnits(SubstdRefundAmtDto dto) {
		return mapper.getBusinessUnits(dto);
	}

	@Override
	public List<String> getPrdDeps(SubstdRefundAmtDto dto) {
		return mapper.getPrdDeps(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDepByNum(SubstdRefundAmtDto dto) {
		return mapper.statGraphDepByNum(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDepByTotAmount(SubstdRefundAmtDto dto) {
		return mapper.statGraphDepByTotAmount(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDateByNum(SubstdRefundAmtDto dto) {
		return mapper.statGraphDateByNum(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDateByTotAmount(SubstdRefundAmtDto dto) {
		return mapper.statGraphDateByTotAmount(dto);
	}

}
