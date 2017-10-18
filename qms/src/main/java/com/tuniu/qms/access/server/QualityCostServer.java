package com.tuniu.qms.access.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.dto.PrdLineDepInfoDto;
import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.JsonUtil;
import com.tuniu.qms.qs.model.QualityCostLedger;
import com.tuniu.qms.qs.service.QualityCostAuxAccountService;
import com.tuniu.qms.qs.service.QualityCostLedgerService;

@Controller
@RequestMapping("/access/qualityCost")
public class QualityCostServer {

	@Autowired
	private QualityCostLedgerService ledgerService;

	@Autowired
	private QualityCostAuxAccountService auxService;

	@Autowired
	private TspService tspService;

	@RequestMapping(value="/addQualityCosts", method=RequestMethod.POST)
	public void addQualityCosts(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		String restDataStr = server.getRestDataStr();
		JSONArray qcDataList = JSON.parseArray(restDataStr);
		List<QualityCostLedger> ledgerList = new ArrayList<QualityCostLedger>();
		try {
			for (int i=0; i<qcDataList.size(); i++) {
				JSONObject data = qcDataList.getJSONObject(i);
				addLedgerList(data, ledgerList);
				Thread.sleep(200);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if (ledgerList.size() > 0) {
			ledgerService.addBatch(ledgerList);
			auxService.addBatch(ledgerList);
		}
		
		ResponseDto dto = new ResponseDto();
		dto.setData("");
		server.writeResponse(dto);
	}
	
	private void addLedgerList(JSONObject data, List<QualityCostLedger> ledgerList) {
		int cmpId = JsonUtil.getIntValue(data, "cmpId");
		int prdLineId = JsonUtil.getIntValue(data, "prdLineId");
		int dealPersonId = JsonUtil.getIntValue(data, "dealPersonId");
		String dealPersonName = JsonUtil.getStringValue(data, "dealPersonName");
		String firstCostAccount = JsonUtil.getStringValue(data, "firstCostAccount");
		String twoCostAccount = JsonUtil.getStringValue(data, "twoCostAccount");
		double qualityCostAmount = JsonUtil.getDoubleValue(data, "qualityCost");
		
		QualityCostLedger ledger = new QualityCostLedger();
		PrdLineDepInfoDto depInfo = tspService.getPrdLineDepInfo(prdLineId);
		if (null != depInfo) {
			ledger.setFirstDepId(depInfo.getBusinessUnitId());
			ledger.setFirstDepName(depInfo.getBusinessUnitName());
			ledger.setTwoDepId(depInfo.getPrdDepId());
			ledger.setTwoDepName(depInfo.getPrdDepName());
			ledger.setThreeDepId(depInfo.getPrdTeamId());
			ledger.setThreeDepName(depInfo.getPrdTeamName());
			ledger.setRespPersonId(depInfo.getPrdManagerId());
			ledger.setRespPersonName(depInfo.getPrdManager());
		} else {
			ledger.setFirstDepId(0);
			ledger.setFirstDepName("");
			ledger.setTwoDepId(0);
			ledger.setTwoDepName("");
			ledger.setThreeDepId(0);
			ledger.setThreeDepName("");
			ledger.setRespPersonId(0);
			ledger.setRespPersonName("");
		}
		ledger.setIspId(0);
		ledger.setQcId(0);
		ledger.setCmpId(cmpId);
		ledger.setPrdLineId(prdLineId);
		ledger.setFirstCostAccount(firstCostAccount);
		ledger.setTwoCostAccount(twoCostAccount);
		ledger.setThreeCostAccount("");
		ledger.setFourCostAccount("");
		ledger.setJobId(16);
		ledger.setJobName("产品经理");
		ledger.setRespRate(100.0);
		ledger.setQualityCost(qualityCostAmount);
		ledger.setDealPersonId(dealPersonId);
		ledger.setDealPersonName(dealPersonName);
		ledger.setAddPerson(dealPersonName);
		
		Date auditTime = data.getDate("auditTime");
		ledger.setAddTime(auditTime);
		ledger.setYear(DateUtil.getYear(auditTime));
		ledger.setYearQuarter(DateUtil.getYearAndQuarter(auditTime));
		ledger.setYearMonth(DateUtil.getYearAndMonth(auditTime));
		ledger.setYearWeek(DateUtil.getYearAndWeek(auditTime));
		ledgerList.add(ledger);
		
		// 先删除，因为分担方案可能会修改后重新审核，之前可能提交过质量成本
		ledgerService.deleteByCmpId(cmpId);
		auxService.deleteByCmpId(cmpId);
	}
	
}
