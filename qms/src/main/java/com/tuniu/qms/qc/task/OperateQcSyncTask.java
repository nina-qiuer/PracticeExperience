package com.tuniu.qms.qc.task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuniu.qms.common.dao.OrderBillMapper;
import com.tuniu.qms.common.dao.ProductMapper;
import com.tuniu.qms.common.service.MailTaskService;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.qc.dao.QcBillMapper;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcTypeService;
import com.tuniu.qms.qc.util.QcConstant;
import com.tuniu.qms.qs.dto.SubstdOrderAmtDto;
import com.tuniu.qms.qs.model.SubstdOrderAmt;
import com.tuniu.qms.qs.service.SubstdOrderAmtService;


/**
 * 运营质检自动发起质检
 * @author chenhaitao
 */
public class OperateQcSyncTask {
	
	private final static Logger logger = LoggerFactory.getLogger(OperateQcSyncTask.class);
	@Autowired
	private QcBillService qcService;
	
	@Autowired
	private SubstdOrderAmtService orderAmtService;
	
	@Autowired
	private MailTaskService mailTaskService;
	
	@Autowired
	private QcTypeService qcTypeService;
	
	@Autowired
	private QcBillMapper qcMapper;
	
	@Autowired
	private OrderBillMapper orderBillMapper;
	
	@Autowired
	private ProductMapper productMapper;
	/**
	 * 发起收款超时质检
	 */
	public void launchTimeOutQc() {

		try {
			
			List<QcBill> qcList = new ArrayList<QcBill>();
			int  count = 0;
			String yesterdayStr = DateUtil.getSqlYesterday().toString();
			SubstdOrderAmtDto dto = new SubstdOrderAmtDto();
			dto.setAddTime(yesterdayStr);
		    List<SubstdOrderAmt>  list =   orderAmtService.listAll(dto);
			for(SubstdOrderAmt amt : list){
				
				Map<String, Object> map =new HashMap<String, Object>();
				map.put("orderId", amt.getOrdId());
				int num = qcService.getOrderIsExist(map);
				if(num<=0){
					
					if(count==10){
						
						break;
						
					}else{
						
						QcBill	qcBill = getBill(amt);
						qcList.add(qcBill);
						count++;
					}
				}
				
			}
			if(null!=qcList&&qcList.size()>0){
				
				qcMapper.addOperateBatch(qcList);
				orderBillMapper.addAttachOrdSyncTask(qcList);//订单批量插入同步订单数据
				productMapper.addAttachPrdSyncTask(qcList);//产品批量同步产品数据
			}
		    
		} catch (Exception e) {
			
			logger.error("launchTimeOutQc error：" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	

	private QcBill getBill(SubstdOrderAmt amt){
		
		 QcBill qcBill  = new QcBill();
	     qcBill.setGroupFlag(Constant.QC_OPERATE);//运营质检
	     qcBill.setState(QcConstant.QC_BILL_STATE_WAIT);//待分配
	     qcBill.setVerification("尾款收取超时质检");
	     qcBill.setQcAffairSummary("尾款收取超时质检");
	     qcBill.setQcAffairDesc("尾款收取超时质检"); 
	     qcBill.setOrdId(amt.getOrdId());
	     qcBill.setPrdId(amt.getPrdId());
	     qcBill.setRemark("");
	     QcType qcType = qcTypeService.getTypeName(QcConstant.FINALPAY_TYPE);
	     if(null!= qcType){
	        		
	          qcBill.setQcTypeId(qcType.getId());
	       }
	     
		return qcBill;
		
	}
}
