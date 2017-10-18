package com.tuniu.qms.qc.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuniu.qms.common.service.UserService;
import com.tuniu.qms.common.util.CellDataMapperHandler;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.common.util.DateUtil;
import com.tuniu.qms.common.util.HandlerResult;
import com.tuniu.qms.common.util.POIUtil;
import com.tuniu.qms.qc.dto.QcAuxiliaryBillDto;
import com.tuniu.qms.qc.dto.QcAuxiliaryTemplateDto;
import com.tuniu.qms.qc.model.QcAuxiliaryBill;
import com.tuniu.qms.qc.model.QcAuxiliaryTemplate;
import com.tuniu.qms.qc.model.QcType;
import com.tuniu.qms.qc.service.QcAuxiliaryBillService;
import com.tuniu.qms.qc.service.QcAuxiliaryTemplateService;
import com.tuniu.qms.qc.service.QcBillService;
import com.tuniu.qms.qc.service.QcTypeService;


/**
 *质检辅助表
 * @author chenhaitao
 *
 */
@Controller
@RequestMapping("/qc/auxiliaryBill")
public class QcAuxiliaryBillController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private QcBillService qcBillService;
    
    @Autowired
    private QcAuxiliaryBillService service;
    
    @Autowired
    private QcAuxiliaryTemplateService templateService;
	 
    @Autowired
    private  QcTypeService qcTypeService;
    
	private final static Logger logger = LoggerFactory.getLogger(QcAuxiliaryBillController.class);

    /**
     * 初始化研发质检列表
     * @param dto
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String list(QcAuxiliaryBillDto dto,HttpServletRequest request){
    	
		  if(!StringUtils.isEmpty(dto.getQcTypeName())){
			  
			  if(!StringUtils.isEmpty(dto.getQcTypeName().trim())){
				  
	         	QcType qcType =  qcTypeService.getTypeByFullName(dto.getQcTypeName());
	            dto.setQcType(qcType.getId());
	            
		       }
		  }
    	service.loadPage(dto);// 查询辅助表列表
        request.setAttribute("dto", dto);
        return "qc/qc_operate/auxiliaryBillList";
    }
    
    /**
     * 导出个数限制
     * @param request
     * @param qcBill
     * @return
     */
    @RequestMapping( "/checkExport")
    @ResponseBody
    public HandlerResult checkExport(QcAuxiliaryBillDto dto,HttpServletRequest request) {
        
        HandlerResult result = new HandlerResult();
        
        try {
            
        	QcType qcType =  qcTypeService.getTypeByFullName(dto.getQcTypeName());
        	if(null==qcType){
        		
        		 result.setRetCode(Constant.SYS_FAILED);
                 result.setResMsg("质检类型不存在");
                 return result;
        	}
        	dto.setQcType(qcType.getId());
        	QcAuxiliaryTemplateDto temDto = new QcAuxiliaryTemplateDto();
        	temDto.setQcType(qcType.getId());
     		QcAuxiliaryTemplate  template = templateService.getByQcType(temDto);
     		if(null==template){
     			
     			 result.setRetCode(Constant.SYS_FAILED);
                 result.setResMsg("该质检类型没有模板");
                 return result;
     		}
        	Integer count = service.exportCount(dto);
        	if(count<=5000 ){
        		
        		  result.setRetCode(Constant.SYS_SUCCESS);
        		  return result;
        		  
        	}else{
        		
        		 result.setRetCode(Constant.SYS_FAILED);
                 result.setResMsg("导出数量不能超过五千条");
                 return result;
        	}
          
            
        } catch (Exception e) {
            
            e.printStackTrace();
            result.setRetCode(Constant.SYS_FAILED);
            result.setResMsg("校验导出数量失败");
            return result;
        }
        
       
    }
    
    
    @RequestMapping("/exports")
    public String exports(QcAuxiliaryBillDto dto,HttpServletRequest request,HttpServletResponse response){
      
    	QcType qcType =  qcTypeService.getTypeByFullName(dto.getQcTypeName());
    	dto.setQcType(qcType.getId());
        List<QcAuxiliaryBill> qcList = service.exportList(dto);
        List<QcType> qcTypeList = qcTypeService.getQcTypeDataCache();
		for(QcAuxiliaryBill bill:qcList){
			
			String fullName = qcTypeService.getQtFullNameById(bill.getQcType(), qcTypeList);
			bill.setQcTypeName(fullName);
			
		}
       //导出数据
        exportData(qcList,qcType.getId(),response);
        request.setAttribute("dto", dto);
        return null;
    }
    
    private void  exportData( List<QcAuxiliaryBill> qcList,Integer qcType,HttpServletResponse response){
    		 
         try {
         		
         	  	QcAuxiliaryTemplateDto temDto = new QcAuxiliaryTemplateDto();
            	temDto.setQcType(qcType);
         		QcAuxiliaryTemplate  template = templateService.getByQcType(temDto);
         		  
                 String[] headers = getHeaders(template);
                 String[] exportsFields = getFields(template);
                 
                 String fileName = "list"+ (new java.text.SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()) + ".xls";
                 new POIUtil<QcAuxiliaryBill>().exportExcel(Arrays.asList(headers), Arrays.asList(exportsFields),fileName, qcList,
                 	     new CellDataMapperHandler() {
 							@Override
 							public String mapToCell(String fieldName,
 									Object value) {
 								String textValue = "";
 								if(value instanceof Date) {
                                    Date date = (Date) value;
                                    textValue = DateUtil.formatAsDefaultDateTime(date);
                                    return textValue;
                                    
                                }else{
                                	return value==null?"":value+"";
                                }
 							}},
 							response);
             } catch(Exception e) {
             	
             	logger.error(e.getMessage(), e);
             }
    	
    }
    
    private String[] getHeaders(QcAuxiliaryTemplate template){
    	List<String> list =new ArrayList<String>();
    	list.add("质检单号");
    	list.add("质检类型");
    	list.add("添加时间");
    	if(StringUtils.isNotEmpty(template.getFieldName1())){
    		
    		list.add(template.getFieldName1());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName2())){
    		
    		list.add(template.getFieldName2());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName3())){
    		
    		list.add(template.getFieldName3());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName4())){
    		
    		list.add(template.getFieldName4());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName5())){
    		
    		list.add(template.getFieldName5());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName6())){
    		
    		list.add(template.getFieldName6());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName7())){
    		
    		list.add(template.getFieldName7());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName8())){
    		
    		list.add(template.getFieldName8());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName9())){
    		
    		list.add(template.getFieldName9());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName10())){
    		
    		list.add(template.getFieldName10());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName11())){
    		
    		list.add(template.getFieldName11());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName12())){
    		
    		list.add(template.getFieldName12());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName13())){
    		
    		list.add(template.getFieldName13());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName14())){
    		
    		list.add(template.getFieldName14());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName15())){
    		
    		list.add(template.getFieldName15());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName16())){
    		
    		list.add(template.getFieldName16());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName17())){
    		
    		list.add(template.getFieldName17());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName18())){
    		
    		list.add(template.getFieldName18());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName19())){
    		
    		list.add(template.getFieldName19());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName20())){
    		
    		list.add(template.getFieldName20());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName21())){
    		
    		list.add(template.getFieldName21());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName22())){
    		
    		list.add(template.getFieldName22());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName23())){
    		
    		list.add(template.getFieldName23());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName24())){
    		
    		list.add(template.getFieldName24());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName25())){
    		
    		list.add(template.getFieldName25());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName26())){
    		
    		list.add(template.getFieldName26());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName27())){
    		
    		list.add(template.getFieldName27());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName28())){
    		
    		list.add(template.getFieldName28());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName29())){
    		
    		list.add(template.getFieldName29());
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName30())){
    		
    		list.add(template.getFieldName30());
    	}
    	final int size =  list.size();
		String[] headers = (String[])list.toArray(new String[size]);
		return headers;
    }
    
    
    private String[] getFields(QcAuxiliaryTemplate template){
    	List<String> list =new ArrayList<String>();
    	list.add("qcId");
    	list.add("qcTypeName");
    	list.add("addTime");
    	
    	if(StringUtils.isNotEmpty(template.getFieldName1())){
    		
    		list.add("field1");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName2())){
    		
    		list.add("field2");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName3())){
    		
    		list.add("field3");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName4())){
    		
    		list.add("field4");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName5())){
    		
    		list.add("field5");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName6())){
    		
    		list.add("field6");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName7())){
    		
    		list.add("field7");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName8())){
    		
    		list.add("field8");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName9())){
    		
    		list.add("field9");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName10())){
    		
    		list.add("field10");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName11())){
    		
    		list.add("field11");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName12())){
    		
    		list.add("field12");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName13())){
    		
    		list.add("field13");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName14())){
    		
    		list.add("field14");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName15())){
    		
    		list.add("field15");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName16())){
    		
    		list.add("field16");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName17())){
    		
    		list.add("field17");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName18())){
    		
    		list.add("field18");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName19())){
    		
    		list.add("field19");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName20())){
    		
    		list.add("field20");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName21())){
    		
    		list.add("field21");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName22())){
    		
    		list.add("field22");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName23())){
    		
    		list.add("field23");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName24())){
    		
    		list.add("field24");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName25())){
    		
    		list.add("field25");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName26())){
    		
    		list.add("field26");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName27())){
    		
    		list.add("field27");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName28())){
    		
    		list.add("field28");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName29())){
    		
    		list.add("field29");
    	}
    	if(StringUtils.isNotEmpty(template.getFieldName30())){
    		
    		list.add("field30");
    	}
    	final int size =  list.size();
		String[] fileds = (String[])list.toArray(new String[size]);
		return fileds;
    }
}
