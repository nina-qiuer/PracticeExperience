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
import com.tuniu.qms.qs.dao.SubstdContractAppendAmtMapper;
import com.tuniu.qms.qs.dto.SubstdContractAppendAmtDto;
import com.tuniu.qms.qs.model.SubstdContractAppendAmt;
import com.tuniu.qms.qs.service.SubstdContractAppendAmtService;

@Service
public class SubstdContractAppendAmtServiceImpl implements SubstdContractAppendAmtService {
	
	@Autowired
	private SubstdContractAppendAmtMapper mapper;
	
	@Autowired
	private DataMapper dataMapper;
	
	@Override
	public void createSubstdContractAppendAmtSnapshot() {
		java.sql.Date yesterday = DateUtil.getSqlYesterday();
		mapper.deleteByAddDate(yesterday);
		
		int pageSize = 2000;
		int dataLimitStart = 0;
		while (true) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yesterday", yesterday);
			map.put("dataLimitStart", dataLimitStart);
			map.put("pageSize", pageSize);
			List<SubstdContractAppendAmt> caList = dataMapper.listContractAppendAmt(map);
			if (!caList.isEmpty()) {
				List<SubstdContractAppendAmt> scaList = new ArrayList<SubstdContractAppendAmt>();
				for (SubstdContractAppendAmt ca : caList) {
					int diffMonthFlag = getDiffMonthFlag(ca);
					ca.setDiffMonthFlag(diffMonthFlag);
					if (Constant.YES == diffMonthFlag) {
						scaList.add(ca);
					}
				}
				
				if (!scaList.isEmpty()) {
					mapper.addBatch(scaList);
				}
			} else {
				break;
			}
			dataLimitStart += pageSize;
		}
	}
	
	private int getDiffMonthFlag(SubstdContractAppendAmt ca) {
		int diffMonthFlag = Constant.NO;
		java.sql.Date signDate = ca.getSignDate();
		if (null != signDate) {
			int signMonth = DateUtil.getYearAndMonth(signDate);
			int addMonth = DateUtil.getYearAndMonth(ca.getAddDate());
			double absMTA = Math.abs(ca.getMonthTotalAmount());
			if (addMonth > signMonth && absMTA > 5000) { // 添加日期与签约日期跨月，且当月添加合同增补单金额之和的绝对值超过5000
				diffMonthFlag = Constant.YES;
			}
		}
		return diffMonthFlag;
	}

	@Override
	public void add(SubstdContractAppendAmt obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(SubstdContractAppendAmt obj) {
		mapper.update(obj);
	}

	@Override
	public SubstdContractAppendAmt get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<SubstdContractAppendAmt> list(SubstdContractAppendAmtDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(SubstdContractAppendAmtDto dto) {
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
	}

	@Override
	public List<String> getBusinessUnits(SubstdContractAppendAmtDto dto) {
		return mapper.getBusinessUnits(dto);
	}

	@Override
	public List<String> getPrdDeps(SubstdContractAppendAmtDto dto) {
		return mapper.getPrdDeps(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDepByNum(SubstdContractAppendAmtDto dto) {
		return mapper.statGraphDepByNum(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDepByTotPrice(SubstdContractAppendAmtDto dto) {
		return mapper.statGraphDepByTotPrice(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDateByNum(SubstdContractAppendAmtDto dto) {
		return mapper.statGraphDateByNum(dto);
	}

	@Override
	public List<Map<String, Object>> statGraphDateByTotPrice(SubstdContractAppendAmtDto dto) {
		return mapper.statGraphDateByTotPrice(dto);
	}

}
