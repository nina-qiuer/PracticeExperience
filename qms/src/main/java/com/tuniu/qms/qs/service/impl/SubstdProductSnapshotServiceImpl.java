package com.tuniu.qms.qs.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.bi.dao.ProductExtMapper;
import com.tuniu.bi.dto.ProductExtDto;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.MathUtil;
import com.tuniu.qms.qs.dao.SubstdProductSnapshotMapper;
import com.tuniu.qms.qs.dto.SubstdProductSnapshotDto;
import com.tuniu.qms.qs.model.DestcateStandard;
import com.tuniu.qms.qs.model.SubstdProductSnapshot;
import com.tuniu.qms.qs.service.DestcateStandardService;
import com.tuniu.qms.qs.service.SubstdProductSnapshotService;

@Service
public class SubstdProductSnapshotServiceImpl implements SubstdProductSnapshotService {

	@Autowired
	private SubstdProductSnapshotMapper mapper;
	
	@Autowired
	private ProductExtMapper prdExtMapper;
	
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
	public void syncProductExt() {
		mapper.deleteAllPrdExts();
		
		int pageSize = 1000;
		int dataLimitStart = 0;
		while (true) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("yesterday", DateUtil.getSqlYesterday());
			map.put("dataLimitStart", dataLimitStart);
			map.put("pageSize", pageSize);
			List<ProductExtDto> prdExtList = prdExtMapper.list(map);
			if (null != prdExtList && !prdExtList.isEmpty()) {
				mapper.addPrdExts(prdExtList);
			} else {
				break;
			}
			dataLimitStart += pageSize;
		}
	}

	@Override
	public void createSubstdProductSnapshot() {
		java.sql.Date statisticDate = DateUtil.getSqlYesterday();
		mapper.deleteByStatisticDate(statisticDate);
		
		dsList = stdService.getDestStdCache();

		int pageSize = 2000;
		int dataLimitStart = 0;
		while (true) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("dataLimitStart", dataLimitStart);
			params.put("pageSize", pageSize);
			List<SubstdProductSnapshot> prdList = mapper.listFromProductExt(params);
			if (!prdList.isEmpty()) {
				List<SubstdProductSnapshot> substdPrdList = new ArrayList<SubstdProductSnapshot>();
				for (SubstdProductSnapshot prd : prdList) {
					int splDifValue = prd.getStdSalesPeriodLength() - prd.getSalesPeriodLength();
					double groupRichness = MathUtil.div(prd.getSurplusGroupNum().doubleValue(), prd.getSalesPeriodLength().doubleValue(), 3);
					double stdGroupRichness = 0;
					DestcateStandard std = getStandard(prd.getDestCate());
					if (null != std) {
						stdGroupRichness = std.getGroupRichness();
					}
					double grDifValue = stdGroupRichness - groupRichness;
					prd.setStatisticDate(statisticDate); // 设置统计日期
					prd.setSplDifValue(splDifValue);
					prd.setGroupRichness(groupRichness);
					prd.setStdGroupRichness(stdGroupRichness);
					prd.setGrDifValue(grDifValue);
					if (splDifValue > 0 // 销售期长不合格
							|| grDifValue > 0 // 团期丰富度不合格
							|| Constant.NO == prd.getAloneGroupFlag()) { // 非独立成团
						substdPrdList.add(prd);
					}
				}
				
				if (!substdPrdList.isEmpty()) {
					mapper.addBatch(substdPrdList);
				}
			} else {
				break;
			}
			dataLimitStart += pageSize;
		}
	}

	@Override
	public void createDepPrdNumSnapshot() {
		java.sql.Date statisticDate = DateUtil.getSqlYesterday();
		mapper.deleteDepPrdNumSnapshot(statisticDate);
		mapper.addDepPrdNumSnapshot(statisticDate);
	}
	
	@Override
	public void add(SubstdProductSnapshot obj) {
		mapper.add(obj);
	}

	@Override
	public void delete(Integer id) {
		mapper.delete(id);
	}

	@Override
	public void update(SubstdProductSnapshot obj) {
		mapper.update(obj);
	}

	@Override
	public SubstdProductSnapshot get(Integer id) {
		return mapper.get(id);
	}

	@Override
	public List<SubstdProductSnapshot> list(SubstdProductSnapshotDto dto) {
		return mapper.list(dto);
	}

	@Override
	public void loadPage(SubstdProductSnapshotDto dto) {
		dto.setTotalRecords(mapper.count(dto));
		dto.setDataList(mapper.list(dto));
	}
	
	@Override
	public List<String> getBusinessUnits(SubstdProductSnapshotDto dto) {
		return mapper.getBusinessUnits(dto);
	}

	@Override
	public List<String> getPrdDeps(SubstdProductSnapshotDto dto) {
		return mapper.getPrdDeps(dto);
	}

	@Override
	public List<Map<String, Object>> statDepSplRate(SubstdProductSnapshotDto dto) {
		List<Map<String, Object>> dataList = mapper.statDepSplRate(dto);
		putSplRate(dataList);
		return dataList;
	}
	
	@Override
	public List<Map<String, Object>> statDateSplRate(SubstdProductSnapshotDto dto) {
		List<Map<String, Object>> dataList = mapper.statDateSplRate(dto);
		putSplRate(dataList);
		return dataList;
	}
	
	private void putSplRate(List<Map<String, Object>> dataList) {
		for (Map<String, Object> map : dataList) {
			BigDecimal substdSplPrdNumTotal = (BigDecimal) map.get("substdSplPrdNumTotal");
			BigDecimal onsellPrdNumTotal = (BigDecimal) map.get("onsellPrdNumTotal");
			Double rate = MathUtil.div(substdSplPrdNumTotal.doubleValue(), onsellPrdNumTotal.doubleValue(), 3);
			map.put("rate", MathUtil.mul(rate, 100));
		}
	}

	@Override
	public List<Map<String, Object>> statDepGrRate(SubstdProductSnapshotDto dto) {
		List<Map<String, Object>> dataList = mapper.statDepGrRate(dto);
		putGrRate(dataList);
		return dataList;
	}
	
	@Override
	public List<Map<String, Object>> statDateGrRate(SubstdProductSnapshotDto dto) {
		List<Map<String, Object>> dataList = mapper.statDateGrRate(dto);
		putGrRate(dataList);
		return dataList;
	}
	
	private void putGrRate(List<Map<String, Object>> dataList) {
		for (Map<String, Object> map : dataList) {
			BigDecimal substdGrPrdNumTotal = (BigDecimal) map.get("substdGrPrdNumTotal");
			BigDecimal onsellPrdNumTotal = (BigDecimal) map.get("onsellPrdNumTotal");
			Double rate = MathUtil.div(substdGrPrdNumTotal.doubleValue(), onsellPrdNumTotal.doubleValue(), 3);
			map.put("rate", MathUtil.mul(rate, 100));
		}
	}

	@Override
	public List<Map<String, Object>> statDepUnAgRate(SubstdProductSnapshotDto dto) {
		List<Map<String, Object>> dataList = mapper.statDepUnAgRate(dto);
		putUnAgRate(dataList);
		return dataList;
	}
	
	@Override
	public List<Map<String, Object>> statDateUnAgRate(SubstdProductSnapshotDto dto) {
		List<Map<String, Object>> dataList = mapper.statDateUnAgRate(dto);
		putUnAgRate(dataList);
		return dataList;
	}
	
	private void putUnAgRate(List<Map<String, Object>> dataList) {
		for (Map<String, Object> map : dataList) {
			BigDecimal unAgPrdNumTotal = (BigDecimal) map.get("unAgPrdNumTotal");
			BigDecimal onsellPrdNumTotal = (BigDecimal) map.get("onsellPrdNumTotal");
			Double rate = MathUtil.div(unAgPrdNumTotal.doubleValue(), onsellPrdNumTotal.doubleValue(), 3);
			map.put("rate", MathUtil.mul(rate, 100));
		}
	}

}
