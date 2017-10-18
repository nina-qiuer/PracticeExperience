package com.tuniu.qms.qs.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.service.TspService;
import com.tuniu.qms.qs.dao.PreSaleReturnVisitMapper;
import com.tuniu.qms.qs.dto.PreSaleReturnVisitDto;
import com.tuniu.qms.qs.model.MessageParamEntity;
import com.tuniu.qms.qs.model.PreSaleReturnVisit;
import com.tuniu.qms.qs.service.PreSaleReturnVisitService;
import com.tuniu.qms.qs.util.PreSaleReturnTypeEnum;
import com.tuniu.qms.qs.util.QsConstant;

@Service
public class PreSaleReturnVisitServiceImpl implements PreSaleReturnVisitService {

	@Autowired
	private PreSaleReturnVisitMapper mapper;
	@Autowired
	private TspService tspService;
	
	@Override
	public void add(PreSaleReturnVisit obj) {

		mapper.add(obj);
		
	}

	@Override
	public void delete(Integer id) {
		
		mapper.delete(id);
		
	}

	@Override
	public void update(PreSaleReturnVisit obj) {
		
		mapper.update(obj);
		
	}

	@Override
	public PreSaleReturnVisit get(Integer id) {
		
		return mapper.get(id);
	}

	@Override
	public List<PreSaleReturnVisit> list(PreSaleReturnVisitDto dto) {
	
		return mapper.list(dto);
	}

	@Override
	public void loadPage(PreSaleReturnVisitDto dto) {
		
		int totalRecords = mapper.count(dto);
		dto.setTotalRecords(totalRecords);
		dto.setDataList(this.list(dto));
		
	}

	 /**
     * 处理短信平台回调接口
     * 
     * @param tel
     *            回复号码
     * @param receiveTime
     *            接收到回复短信时间
     * @param prefix
     *            回复前缀
     * @param choosedItem
     *            回复选项
     */
	public int dealWithMsgCallBack(String tel, String receiveTime,Integer choosedItem) {
		
		    int resultCode = 0;
	        boolean legal = preCheckParamFromMsgPlatForm(choosedItem);
	        if(legal) {
	            Map<String, Object> paramMap = new HashMap<String, Object>();
	            paramMap.put("tel", tel);
	            paramMap.put("receiveTime", receiveTime);
	     
	            if(Arrays.asList("1,2,3,4,5".split(",")).contains(choosedItem + "")) {
	                paramMap.put("returnType", "satisfaction");
	                PreSaleReturnVisit entity  = mapper.getValidVistByParam(paramMap);
	               
	                if(null != entity) {
	                    Integer score = PreSaleReturnTypeEnum.getScoreBykey(choosedItem);
	                    if(score != -1) {
	                    	
	                        entity.setScore(score);
	                        mapper.update(entity);
	                        
	                        if(QsConstant.SATISFACTION_DISSATISFIED_SCORE == score){ // 如果回复不满意，则触发不满意原因回访短息
	                            MessageParamEntity mpe = new MessageParamEntity();
	                            mpe.setTemplateId(MessageParamEntity.TEMPLATE_UNSATISFIED_REASON);
	                            mpe.setMobileNum(Arrays.asList(new String[]{entity.getTel()}));
	                            mpe.setOrderId(entity.getOrdId());
	                            
	                            tspService.sendMessage(mpe);
	                        }
	                    }
	                }
	            } else if(Arrays.asList("6,7,8,9".split(",")).contains(choosedItem + "")) {
	                paramMap.put("returnType", "reason");
	                PreSaleReturnVisit entity  = mapper.getValidVistByParam(paramMap);
	                if( null != entity) {
	                	
	                    entity.setUnsatisfyReason(choosedItem); // 回复不满意原因内容与数据库中字段一致，直接设置
	                    mapper.update(entity);
	                }
	            }
	        }else{
	            resultCode = 240001; // 入参格式错误
	        }
	        
	        return resultCode;
	}


	 /**
     * 短信回调参数检查
     * 
     * @param tel
     * @param receiveTime
     * @param prefix
     * @param choosedItem
     */
    private boolean preCheckParamFromMsgPlatForm(Integer choosedItem) {
        boolean result = true;

        // 回复内容校验(choosedItem)
        if(choosedItem != null) {
        	
             if(!Arrays.asList("1,2,3,4,5,6,7,8,9".split(",")).contains(choosedItem + "")) {
                    result = false;
               }

        }else {
        	
            result = false;
        }

        return result;
    }
	
	
}
