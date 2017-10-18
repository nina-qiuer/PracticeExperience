package com.tuniu.gt.complaint.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import tuniu.frm.core.FrmBaseAction;

import com.tuniu.gt.complaint.entity.ComplaintPointEntity;
import com.tuniu.gt.complaint.entity.ReasonTypeEntity;
import com.tuniu.gt.complaint.service.IComplaintPointService;
import com.tuniu.gt.complaint.service.IComplaintService;
import com.tuniu.gt.complaint.service.IReasonTypeDetailService;
import com.tuniu.gt.complaint.service.IReasonTypeService;
import com.tuniu.gt.complaint.vo.ComplaintVo;
import com.tuniu.gt.toolkit.lang.CollectionUtil;


/**
* @author Jiang sir
*/

@Service("complaint_action-complaint_point")
@Scope("prototype")
public class ComplaintPointAction extends FrmBaseAction<IComplaintPointService,ComplaintPointEntity> { 
	
    private static Logger logger = Logger.getLogger(ComplaintPointAction.class);
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-reason_type")
    private IReasonTypeService reasonTypeService;
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-complaint")
    private IComplaintService complaintService;
    
    @Autowired
    @Qualifier("complaint_service_complaint_impl-reason_type_detail")
    private IReasonTypeDetailService reasonTypeDetailService;
    
    private List<ReasonTypeEntity> allFirstReasonTypeList = new ArrayList<ReasonTypeEntity>();
    private String complaintId;

    private List<ComplaintPointEntity> pointList;
    
    public ComplaintPointAction() {
		setManageUrl("complaint_point");
	}
	
	@Autowired
	@Qualifier("complaint_service_complaint_reason_impl-complaint_point")
	public void setService(IComplaintPointService service) {
		this.service = service;
	}
	
	public String execute(){

	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("complaintId", complaintId);
	    List<ComplaintPointEntity> pointList = (List<ComplaintPointEntity>) service.fetchInitList(paramMap);
	    request.setAttribute("dataList", pointList);
//	    paramMap.clear();
//	    paramMap.put("fatherIds", "1001,1002,1003,1004");
//	    
//	    allFirstReasonTypeList = (List<ReasonTypeEntity>) reasonTypeService.fetchList(paramMap);
//	    
//	    String parentTypeName = "";
//	    List<String> subRessonTypes;
//	    JSONObject jo = new JSONObject();
//	    for(ReasonTypeEntity reasonType : allFirstReasonTypeList) {
//	        paramMap.clear();
//	        parentTypeName = reasonType.getName();
//	        subRessonTypes= reasonTypeService.getChilderByParentName(parentTypeName);
//	        jo.put(parentTypeName, subRessonTypes);
//        }
//	    
//	    request.setAttribute("reasonTypes", jo);
	    
	    List<JSONObject> result=reasonTypeDetailService.buildDetailTree();
	    request.setAttribute("reasonTypes", result);
	    
	    //实际赔付总额
	    ComplaintVo  cmpVo = complaintService.getCmpBillInfo(Integer.valueOf(complaintId));
	    request.setAttribute("actualTotalPayout", cmpVo.getIndemnifyAmount());
	    request.setAttribute("finalConclution", cmpVo.getFinalConclution());
	    
	    return "complaint_point_edit";
	}
	
	public String save(){
	    //先删除该投诉id下所有的投诉点，再新增
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("complaintId", complaintId);
	    service.delete(paramMap);
	    
	    List<ComplaintPointEntity> affectivePointList = new ArrayList<ComplaintPointEntity>();
	    if(CollectionUtil.isNotEmpty(pointList)){
	        for(ComplaintPointEntity pointEntity : pointList) {
	            if(pointEntity!=null){
	            	pointEntity.setMainType("无");
	            	pointEntity.setSecondType("无");
	            	pointEntity.setTheoryPayout(pointEntity.getTheoryPayoutLaw()+pointEntity.getTheoryPayoutQuality());
	                pointEntity.setComplaintId(Integer.valueOf(complaintId));
	                affectivePointList.add(pointEntity);
	            }
	        }
	    }
	    
	    if(CollectionUtil.isNotEmpty(affectivePointList)){
	        service.batchInsert(affectivePointList);
	    }
	    
	    String finalConclution = request.getParameter("finalConclution");
	    paramMap.clear();
	    paramMap.put("id", complaintId);
	    paramMap.put("finalConclution", finalConclution);
	    complaintService.update(paramMap);
	    
	    return execute();
	}
	
	public String preview(){
	    Map<String, Object> paramMap = new HashMap<String, Object>();
	    paramMap.put("complaintId", complaintId);
        List<ComplaintPointEntity> pointList = (List<ComplaintPointEntity>) service.fetchInitList(paramMap);
        request.setAttribute("dataList", pointList);

        //实际赔付总额
	    ComplaintVo  cmpVo = complaintService.getCmpBillInfo(Integer.valueOf(complaintId));
	    request.setAttribute("actualTotalPayout", cmpVo.getIndemnifyAmount());
	    request.setAttribute("finalConclution", cmpVo.getFinalConclution());

	    //理论赔付总额
	    if(CollectionUtil.isNotEmpty(pointList)){
	    	Double totalTheoryPayout = 0.00;
	        for(ComplaintPointEntity a : pointList) {
	            if(a.getTheoryPayout()!=null){
	                totalTheoryPayout+=a.getTheoryPayout();
	            }
            }
	        request.setAttribute("totalTheoryPayout", totalTheoryPayout);
	    }
        
	    return "complaint_point_preview";
	}

    public List<ReasonTypeEntity> getAllFirstReasonTypeList() {
        return allFirstReasonTypeList;
    }

    public void setAllFirstReasonTypeList(List<ReasonTypeEntity> allFirstReasonTypeList) {
        this.allFirstReasonTypeList = allFirstReasonTypeList;
    }
    
    public String getComplaintId() {
        return complaintId;
    }

    public void setComplaintId(String complaintId) {
        this.complaintId = complaintId;
    }
    
    public List<ComplaintPointEntity> getPointList() {
        return pointList;
    }

    public void setPointList(List<ComplaintPointEntity> pointList) {
        this.pointList = pointList;
    }

}
