package com.tuniu.gt.complaint.scheduling;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IShareSolutionService;
import com.tuniu.gt.complaint.vo.ComplaintReasonVo;
import com.tuniu.gt.complaint.vo.ComplaintVo;
import com.tuniu.gt.toolkit.DateUtil;

/**
 * 咨询单发起质检
 * @author chenhaitao
 */
@Service("consultToQms")
public class ConsultToComplaintTask {
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-complaint")
	private IComplaintService complaintService;
	
	@Autowired
	@Qualifier("complaint_service_complaint_impl-share_solution")
	private IShareSolutionService  shareSolutionService;
	
	private static Logger logger = Logger.getLogger(ConsultToComplaintTask.class);
	
	//对客赔付金额不为0的咨询单，进入质检列表 
	//存在理论赔付的投诉单, 进入质检列表
	//去除其中 1、对客赔付金额全部为质量工具承担（2）投诉事宜分类为“旅行前咨询—不成团” 的咨询单(3)投诉事宜分类为“点评—满意度小于等于百分之50”
	public void sendConsultToQms() {
		
		try{
			Map<String, Object> map =new HashMap<String, Object>();
			map.put("submitTimeBgn", DateUtil.getBeforeDay()+" 00:00:00");//获取前一天时间 DateUtil.getBeforeDay()
			map.put("submitTimeEnd", DateUtil.getBeforeDay()+" 23:59:59");//
			
			List<Integer> list  =  shareSolutionService.getShareSolution(map);//查询咨询单赔付金额不为0  和 存在理论赔付的投诉单
			
			if(null != list && list.size()>0){
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("cmpIds", list);
				List<ComplaintVo> cmpList =  complaintService.getReasonList(params);//投诉事宜
				
				for(ComplaintVo cmpVo : cmpList){
					
					if(cmpVo.getReasonList() != null){
						
						int noGroupCount =  0 , commmentCount = 0;
						for(ComplaintReasonVo reasonVo : cmpVo.getReasonList()){
							if("旅行前咨询".equals(reasonVo.getType()) && "不成团".equals(reasonVo.getSecondType())){
								noGroupCount++;
							}else if("点评".equals(reasonVo.getType()) && "满意度小于等于百分之50".equals(reasonVo.getSecondType())){
								commmentCount++;
							}
						}
						
						int size = cmpVo.getReasonList().size();
						//去除投诉事宜分类为“旅行前咨询—不成团” 的咨询单  或者 投诉事宜都是低满意度点评的
						if((noGroupCount == size  && noGroupCount > 0) || (commmentCount == size && commmentCount > 0)){
							list.remove(cmpVo.getId());
						}
					}
				}
			}
			
			//第前21天发起投诉单、并且未完结发质检
			String someDay = DateUtil.formatDate(DateUtil.getSomeDay(new Date(), -21));
			map.put("someDayBgn", someDay +" 00:00:00");
			map.put("someDayEnd", someDay +" 23:59:59");
			
			List<Integer> unFinishIds = complaintService.getHandingComplaintIds(map);
			if(null != unFinishIds && unFinishIds.size() > 0){
				list.addAll(unFinishIds);
			}
			
			complaintService.sendToQms(list);
			logger.info("sendConsultToQms success " + list );
			
		} catch (Exception e) {
			logger.error("sendConsultToQms fail", e);
		}
	}
}
