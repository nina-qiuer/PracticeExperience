package com.tuniu.gt.warning.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.tuniu.gt.complaint.restful.ComplaintRestClient;
import com.tuniu.gt.complaint.service.IComplaintTSPService;
import com.tuniu.gt.toolkit.CommonUtil;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.gt.toolkit.MailUtil;
import com.tuniu.gt.toolkit.MailerThread;
import com.tuniu.gt.warning.common.EarlyWarningOrderPage;
import com.tuniu.gt.warning.entity.EarlyWarningEntity;
import com.tuniu.gt.warning.entity.EarlyWarningOrderEntity;
import com.tuniu.gt.warning.entity.EwOrderAgencyEntity;
import com.tuniu.gt.warning.entity.EwOrderFlightEntity;
import com.tuniu.gt.warning.entity.WetherCodeEntity;
import com.tuniu.gt.warning.service.IEarlyWarningOrderService;
import com.tuniu.gt.warning.service.IEarlyWarningService;
import com.tuniu.gt.warning.service.IWetherCodeService;

public class EarlyWarningTask {

    private static Logger logger = Logger.getLogger(EarlyWarningTask.class);
    
    @Autowired
	@Qualifier("warning_service_impl-wether_code")
	private IWetherCodeService wetherCodeService;
    
    @Autowired
	@Qualifier("warning_service_impl-early_warning")
	private IEarlyWarningService ewService;
    
    @Autowired
	@Qualifier("warning_service_impl-early_warning_order")
	private IEarlyWarningOrderService ewoService;
    
    @Resource
	private IComplaintTSPService tspService;
	
    @SuppressWarnings("unchecked")
	public void addWetherWarning() {
        logger.info("addWetherWarning Begin...");
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("keyWordFlag", 1);
        List<WetherCodeEntity> wcList = (List<WetherCodeEntity>) wetherCodeService.fetchList(paramMap);
        
        List<Map<String, Object>> emailData = new ArrayList<Map<String,Object>>();
        for (WetherCodeEntity wc : wcList) {
        	String[] citys = tspService.getWetherAlarmCitys(wc.getCode());
        	
        	List<String> cityList = new ArrayList<String>();
        	if (0 == citys.length) {
        		cityList.add("");
        	}
        	
        	int size = 0;
        	for (String city : citys) {
        		List<EarlyWarningOrderEntity> ewoList = new ArrayList<EarlyWarningOrderEntity>();
        		EarlyWarningOrderPage page1 = new EarlyWarningOrderPage();
        		page1.setFlightDcitys(city);
        		page1.setFlightDtBegin(DateUtil.getTomorrowStr() + " 00:00:00");
        		page1.setFlightDtEnd(DateUtil.getTomorrowStr() + " 23:59:59");
        		ComplaintRestClient.queryOrderBatch(page1);
        		if (null != page1.getEwoList()) {
        			ewoList.addAll(page1.getEwoList()); // 明天从某个城市起飞的航班涉及到的订单
        		}
        		
        		EarlyWarningOrderPage page2 = new EarlyWarningOrderPage();
        		page2.setFlightLcitys(city);
        		page2.setFlightLtBegin(DateUtil.getTomorrowStr() + " 00:00:00");
        		page2.setFlightLtEnd(DateUtil.getTomorrowStr() + " 23:59:59");
        		ComplaintRestClient.queryOrderBatch(page2);
        		if (null != page2.getEwoList()) {
        			ewoList.addAll(page2.getEwoList()); // 明天从抵达某个城市的航班涉及到的订单
        		}
        		
        		if (!ewoList.isEmpty()) {
        			size = ewoList.size();
        			// 发起预警
            		EarlyWarningEntity ew = new EarlyWarningEntity();
            		ew.setWarningType(1);
            		ew.setWarningLv(4);
            		String wetherName = wc.getName();
            		if (!CommonUtil.isNumeric(wc.getCode()) && !"微风".equals(wetherName)) {
            			wetherName = "风" + wetherName;
            		}
            		ew.setContent("明天（" + DateUtil.getTomorrowStr() + "）" + city + "地区有" + wetherName);
            		ew.setAddPerson("robot");
            		ewService.insert(ew);
            		
            		for (EarlyWarningOrderEntity ewo : ewoList) {
            			ewo.setEwId(ew.getId());
            			ewoService.insert(ewo);
            			for (EwOrderFlightEntity flight : ewo.getFlightList()) {
            				flight.setEwOrderId(ewo.getId());
            				ewoService.insertFlight(flight);
            			}
            			for (EwOrderAgencyEntity agency : ewo.getAgencyList()) {
            				agency.setEwOrderId(ewo.getId());
            				ewoService.insertAgency(agency);
            			}
            		}
        		}
        		String sizeStr = String.valueOf(size);
        		if (size > 0) {
        			sizeStr = "<span style='color: red;'>" + size + "</span>";
        		}
        		String cityInfo = city + "（" + sizeStr + "）";
        		cityList.add(cityInfo);
        	}
        	/* 设置预警邮件数据信息 */
    		Map<String, Object> emailMap = new HashMap<String, Object>();
        	emailMap.put("keyWordName", wc.getName());
    		emailMap.put("cityList", cityList);
    		emailData.add(emailMap);
        }
        // 发送邮件
    	String[] res = new String[]{"chenchangqing@tuniu.com", "maben@tuniu.com"};
    	String[] ccs = new String[]{"fangyouming@tuniu.com"};
    	String title = "【" + DateUtil.formatDate(new Date()) + "】天气预警日报";
    	String content = getEmailContent(emailData);
    	tspService.sendMail(new MailerThread(res, ccs, title, content));
    	
        logger.info("addWetherWarning End...");
    }
    
    @SuppressWarnings("unchecked")
	private String getEmailContent(List<Map<String, Object>> emailData) {
    	StringBuffer sb = new StringBuffer();
    	sb.append("<html><head>");
    	sb.append("<style type='text/css'>.datatable {border:1px solid #fff;border-collapse:collapse;font-size:13px;}.datatable th{border:1px solid #fff;color:#355586;background:#DFEAFB; padding:2px;}.datatable td{padding:2px;background-color: #F9F5F2;border: 1px solid #fff;}</style>");
    	sb.append("</head><body>");
    	sb.append("<table width='300' class='datatable'>");
    	sb.append("<tr><th align='center' width='150'>天气关键词</th><th align='center' width='150'>城市（订单数）</th></tr>");
    	for (Map<String, Object> map : emailData) {
    		String keyWordName = (String) map.get("keyWordName");
    		List<String> cityList = (List<String>) map.get("cityList");
    		
    		sb.append("<tr><td align='center' rowspan='").append(cityList.size()).append("'>").append(keyWordName).append("</td>");
    		for (int i=0; i<cityList.size(); i++) {
    			if (0 == i) {
    				sb.append("<td>").append(cityList.get(i)).append("</td></tr>");
    			} else {
    				sb.append("<tr><td>").append(cityList.get(i)).append("</td></tr>");
    			}
    		}
    	}
    	sb.append("</table></body></html>");
    	return sb.toString();
    }
    
}
