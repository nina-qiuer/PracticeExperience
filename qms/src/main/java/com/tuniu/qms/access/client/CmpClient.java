package com.tuniu.qms.access.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.init.Config;
import com.tuniu.qms.common.model.AgencyPayout;
import com.tuniu.qms.common.model.ComplaintBill;
import com.tuniu.qms.common.model.ComplaintSolution;
import com.tuniu.qms.common.model.FollowUpRecord;
import com.tuniu.qms.common.util.Constant;
import com.tuniu.qms.qc.model.QcBill;
import com.tuniu.qms.qs.model.Remark;

public class CmpClient {
	
	private static String serverURL = Config.get("cmp.url");
	private static String contentURL = Config.get("content.url"); 
	private final static Logger logger = LoggerFactory.getLogger(CmpClient.class);
	/** 查询投诉相关数据 */
	public static ComplaintBill getComplaintInfo(Integer cmpId) {
		ComplaintBill cmp = new ComplaintBill();
		try {
			if (null != cmpId && cmpId > 0) {
				Map<String, Object> reqParam = new HashMap<String, Object>();
				reqParam.put("cmpId", cmpId);
				String url = serverURL + "/ssi/restfulserver/complaint/getCmpBillInfo";
				RestClient rc = new RestClient(url, "GET", JSON.toJSON(reqParam));
				ResponseDto result = rc.execute2();
				if (null!=result && result.isSuccess()) {
					JSONObject data = (JSONObject) result.getData();
					cmp = JSON.parseObject(data.toJSONString(), ComplaintBill.class);
				}
			}
			
		} catch (Exception e) {
			logger.error("查询投诉单失败，ID： " + cmpId, e);
		}
		
		return cmp;
	}
	
	
	public static QcBill getQcInfo(Integer cmpId) {
		QcBill qcBill = null;
		try {
			if (null != cmpId && cmpId > 0) {
				Map<String, Object> reqParam = new HashMap<String, Object>();
				reqParam.put("cmpId", cmpId);
				String url = serverURL + "/ssi/restfulserver/complaint/getQcInfo";
				RestClient rc = new RestClient(url, "GET", JSON.toJSON(reqParam));
				ResponseDto result = rc.execute2();
				logger.info("getQcInfo result:" + result.getData());
				if (null!=result && result.isSuccess()) {
					JSONObject data = (JSONObject) result.getData();
					
					qcBill = JSON.parseObject(data.toJSONString(), QcBill.class);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return qcBill;
	}
	
	/**
	 * 修改投诉等级
	 * @param map
	 * @return
	 */
	public static int updateCmpLevel(Map<String, Object> map) {
		
		try {
			
			String url = serverURL + "/ssi/restfulserver/complaint/updateCmpLevel";
			RestClient rc = new RestClient(url, "GET", JSON.toJSON(map));
			ResponseDto result = rc.execute2();
			if (null!=result && result.isSuccess()) {
				
				return Constant.YES;
				
			}else{
				
				return Constant.NO ;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return Constant.NO ;
		}
		
	}
	
	/**
	 * 推送记分数据到投诉质检
	 * @param list
	 * @return
	 */
	public static int pushPunishData(Map<String, Object> map) {
		
		try {
			
			String url = serverURL + "/ssi/restfulserver/complaint/pushPunishData";
			RestClient rc = new RestClient(url, "GET", JSON.toJSON(map));
			ResponseDto result = rc.execute2();
			if (null!=result && result.isSuccess()) {
				
				return Constant.YES;
				
			}else{
				
				return Constant.NO ;
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return Constant.NO ;
		}
		
	}
	
	/**
	 * 推送触红数据到投诉质检
	*/
    public static int pushTouchRedData(Map<String,Object> touchRedDataMap) {

        try {
            String url = serverURL + "/ssi/restfulserver/complaint/pushTouchRedData";
            Object requestStr = JSON.toJSON(touchRedDataMap);
            logger.info("pushTouchRedData BEGIN:"+requestStr);
            RestClient rc = new RestClient(url, "GET", requestStr);
            ResponseDto result = rc.execute2();
            logger.info("pushTouchRedData END:"+JSON.toJSON(result));
            if (null!=result && result.isSuccess()) {
                return Constant.YES;
            } else {
                return Constant.NO;
            }
        } catch(Exception e) {
            logger.error("推送触红数据失败："+JSON.toJSON(touchRedDataMap));
            e.printStackTrace();
            return Constant.NO;
        }

    }

	/**
	 * 查询供应商赔付信息
	 */
	public static List<AgencyPayout> getAgencyPayoutInfo(Integer cmpId) {
		List<AgencyPayout> agencyList = new ArrayList<AgencyPayout>();
		try {
			if (null != cmpId && cmpId > 0) {
				Map<String, Object> reqParam = new HashMap<String, Object>();
				reqParam.put("complaintId", cmpId);
				reqParam.put("agencyId", 0);
				reqParam.put("processStatus", -1);
				reqParam.put("orderId", 0);
				reqParam.put("routeId", 0);
				reqParam.put("startCity", "");
				reqParam.put("complaintDateBgn", "");
				reqParam.put("complaintDateEnd", "");
				reqParam.put("startDateBgn", "");
				reqParam.put("startDateEnd", "");
				reqParam.put("start", 0);
				reqParam.put("limit", 200);
				String url = serverURL + "/ssi/restfulserver/complaint/getAgencyPayoutList";
				RestClient rc = new RestClient(url, "GET", JSON.toJSON(reqParam));
				ResponseDto result = rc.execute2();
				if (null!=result && result.isSuccess()) {
					JSONObject data = (JSONObject) result.getData();
					JSONArray temp = data.getJSONArray("rows");
					for (int i=0; i<temp.size(); i++) {
						AgencyPayout agency = JSON.parseObject(temp.getJSONObject(i).toJSONString(), AgencyPayout.class);
						String claimBasis = getClaimBasis(cmpId, agency.getAgencyId());
						agency.setClaimBasis(claimBasis);
						agencyList.	add(agency);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return agencyList;
	}
	
	private static String getClaimBasis(Integer cmpId, Integer agencyId) {
		String claimBasis = "";
		try {
			Map<String, Object> reqParam = new HashMap<String, Object>();
			reqParam.put("complaintId", cmpId);
			reqParam.put("agencyId", 0);
			String url = serverURL + "/ssi/restfulserver/complaint/getAgencyPayoutDetail";
			RestClient rc = new RestClient(url, "GET", JSON.toJSON(reqParam));
			ResponseDto result = rc.execute2();
			if (null!=result && result.isSuccess()) {
				JSONObject data = (JSONObject) result.getData();
				JSONArray cpList = data.getJSONArray("cpList");
				JSONObject cp = cpList.getJSONObject(0);
				claimBasis = cp.getString("payBase");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return claimBasis;
	}

	/**
	 * 查询跟进记录
	 * @param cmpId
	 * @return
	 */
	public static List<FollowUpRecord> getRecordInfo(Integer cmpId){
		
		List<FollowUpRecord> recordList  = null;
		
		try {
			
			if (null != cmpId && cmpId > 0) {
				Map<String, Object> reqParam = new HashMap<String, Object>();
				reqParam.put("complaintId", cmpId);
				String url = serverURL + "/ssi/restfulserver/complaint/getRecordList";
				RestClient rc = new RestClient(url, "GET", JSON.toJSON(reqParam));
				ResponseDto result = rc.execute2();
				if (null!=result && result.isSuccess()) {
					recordList =new ArrayList<FollowUpRecord>();
					JSONArray temp = (JSONArray) result.getData();
					for(int i=0;i<temp.size();i++){
						
						FollowUpRecord record  = JSON.parseObject(temp.getJSONObject(i).toJSONString(), FollowUpRecord.class);
						recordList.add(record);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return recordList;
		
	}
	
	/**
	 * 查询对客解决方案
	 */
	public static List<ComplaintSolution> getCmpSolution(Map<String, Object> map) {
		List<ComplaintSolution> solutionList = new ArrayList<ComplaintSolution>();
		try {
				String url = serverURL + "/ssi/restfulserver/complaint/getCmpSolution";
				RestClient rc = new RestClient(url, "GET", JSON.toJSON(map));
				ResponseDto result = rc.execute2();
				if (null!=result && result.isSuccess()) {
					JSONArray temp = (JSONArray) result.getData();
					logger.error("getCmpSolution result : "+ temp);
					for (int i=0; i<temp.size(); i++) {
						ComplaintSolution solution =  new ComplaintSolution();
						solution.setCmpId(temp.getJSONObject(i).getInteger("complaintId"));
						solution.setSolution(temp.getJSONObject(i).getString("descript"));
						solution.setOrderId(temp.getJSONObject(i).getInteger("orderId"));
						solution.setCmpStateName(temp.getJSONObject(i).getString("cmpState"));
						solutionList.add(solution);
					}
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return solutionList;
	}
	
	
	public static List<Map<String, Object>> getCmpSpecial(List<Integer> list) {
	
		List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
		try { 
			
				Map<String, Object> reqParam = new HashMap<String, Object>();
				reqParam.put("cmpIds", list);
				String url = serverURL + "/ssi/restfulserver/complaint/getCmpSpecial";
				RestClient rc = new RestClient(url, "GET", JSON.toJSON(reqParam));
				logger.info("getCmpSpecial send : "+  JSON.toJSON(reqParam));
				ResponseDto result = rc.execute2();
				if (null!=result && result.isSuccess()) {
					
					JSONArray dataList = (JSONArray) result.getData();
					logger.error("getCmpSpecial result : "+ dataList);
					if(null!=dataList && dataList.size()>0){
						
						for (int i=0; i<dataList.size(); i++) {
							
							Map<String, Object> map = new HashMap<String, Object>();
							map.put("cmpId", dataList.getJSONObject(i).getString("complaintId"));
							map.put("special", dataList.getJSONObject(i).getString("special"));
							listResult.add(map);
						}
						
					}
				}
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		return listResult;
	}
	
	/**
	 * #统计信息查询接口-按产品统计
	 * 暂时只实现返回第一个产品id对应产品的点评总数功能
	 * @param productIds
	 * @return
	 */
	public static Integer getStatProductByProductIds(List<Integer> productIds) {
		logger.info("getStatProductByProductIds Begin: productIds is "
				+ productIds);
		
		Integer remarkAmount = 0;//点评次数
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productIds", productIds);
		logger.info("getStatProductByProductIds send : "+  JSON.toJSON(paramMap));
		try {
			
			RestClient rc = new RestClient(contentURL+"/getStatProductByProductIds", "GET", JSON.toJSON(paramMap));
			logger.info("getStatProductByProductIds Begin: param is "+ JSON.toJSON(paramMap));
			ResponseDto result = rc.execute2();
			if (null!=result && result.isSuccess()) {
				JSONObject data = (JSONObject) result.getData();
				logger.info("getStatProductByProductIds result : "+ data);
				int totalItems = data.getInteger("totalItems");
				if(totalItems==1){
					JSONArray statSpecs = data.getJSONArray("statSpecs");
					JSONObject statSpec = statSpecs.getJSONObject(0);
					remarkAmount = statSpec.getInteger("remarkAmount");
				}
				
			}
				
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return remarkAmount;
	}
	
	/**
	 * 点评详情接口
	 * 
	 * @param paramMap  包括productId,点评时间范围开始：remarkTimeStart,点评时间范围结束remarkTimeEnd
	 * @return
	 */
	public static List<Remark> getContentList(Map<String,Object> paramMap) {
	    paramMap.put("orderByRemarkTime", 1);
	    paramMap.put("hasRemark", 1);
	    paramMap.put("pageLimit", 99);
		List<Remark> remarkList = null;
		
		try {
			Object json = JSON.toJSON(paramMap);
        	logger.info("getContentList Begin data : " + json.toString());
        	logger.info("getContentList url : " + contentURL);
        	
			RestClient rc = new RestClient(contentURL + "/getContentList", "GET", json);
			
			ResponseDto result = rc.execute2();
			
			if (null != result && result.isSuccess()) {
				JSONObject data = (JSONObject) result.getData();
				logger.info("getStatProductByProductIds result totalItems : " + data.getIntValue("totalItems"));
				
				remarkList = convertToRemarkEntity(data);
			}
				
		} catch (Exception e) {
			logger.error("获得点评详情失败", e.getMessage(), e);
		}
		
		return null == remarkList ? new ArrayList<Remark>() : remarkList;
	}
	
	/**
	 * 获取产品ID累计的点评数
	 * 
	 * @param routeId 
	 * @return
	 */
    public static Integer getRemarkAmount(Long routeId) {
    	int totalItems = 0;
       
    	try {
        	Map<String, Object> paramMap = new HashMap<String, Object>();
        	
        	paramMap.put("productId", routeId);
        	paramMap.put("hasRemark", 1);//已点评过订单信息
        	paramMap.put("ignoreDetail", 1);//忽略图片及子项评分和评价内容
        	
        	Object json = JSON.toJSON(paramMap);
        	logger.info("getContentAmount Begin data : " + json.toString());
        	logger.info("getContentAmount url : " + contentURL);
        	
        	RestClient rc = new RestClient(contentURL + "/getContentList", "GET", json);
        	
        	ResponseDto result = rc.execute2();
        	if (null !=result && result.isSuccess()) {
				
				JSONObject data = (JSONObject) result.getData();
				logger.info("getContentAmount totalItems : " + data.getIntValue("totalItems"));
			    
				totalItems = data.getInteger("totalItems");
			    if(totalItems == 0) { //规避有评论但是totalItems显示为0的方案
	            	
                   JSONArray contents = data.getJSONArray("contents");
                   totalItems = contents.size();
	             }
			}
        	
            logger.info("getContentAmount End["+routeId+"]:点评数为" + totalItems + "条");
        } catch(Exception e) {
        	
            logger.error(e.getMessage(), e);
            logger.error("获取点评数出错", e);
        }
    	
        return totalItems;
    }
    
    private static List<Remark> convertToRemarkEntity(JSONObject data) {
    	List<Remark> remarkList = new ArrayList<Remark>();
		
    	JSONArray contents = data.getJSONArray("contents");
		for (int i = 0; i < contents.size(); i++) {
			JSONObject content = contents.getJSONObject(i);
			
			Remark remark = new Remark();
			remark.setRouteId(content.getInteger("productId"));
			remark.setOrderId(content.getInteger("orderId"));
			remark.setSatisfaction(content.getInteger("satisfaction"));
			remark.setRemarkTime(content.getString("remarkTime"));
			remark.setStartTime(content.getString("startTime"));
			remark.setCompTextContent(content.getJSONObject("compTextContent").getString("dataSvalue"));
			
			StringBuilder sb = new StringBuilder();
			JSONArray subTextContents = content.getJSONArray("subTextContents");
			for (int j = 0; j < subTextContents.size(); j++) {
				JSONObject subTextContent = subTextContents.getJSONObject(j);
				String notes = subTextContent.getString("notes");
				String dataSvalue = subTextContent.getString("dataSvalue");
				sb.append(notes).append(":").append(dataSvalue).append("\r");
			}
			
			remark.setSubTextContent(sb.toString());
			
			remarkList.add(remark);
		}
		Collections.reverse(remarkList);//接口获取的数据按时间倒叙排列，页面展示需要按照正向排列
		
		return remarkList;
	}
	
    
    
	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(getComplaintInfo(19223)));
		System.out.println(JSON.toJSONString(getAgencyPayoutInfo(563998)));
	}

	/**
	 * 投诉质检单是否可以撤销         对于无赔偿、非低满意度、无申请质检的质检单进行筛分，系统统一执行撤销操作
	 * 撤销判断：
	 *	1. 投诉单是否有赔偿
	 *	2. 投诉单是否为低满意度投诉  根据投诉单来源判断  投诉来源为“点评” true
	 *	3. 投诉单是否申请质检点     ct_qc_point 表中来判断
	 * @param cmpId
	 * @return
	 */
	public static boolean getComplaintQcCancelState(Integer cmpId) {
		
		JSONObject obj = new JSONObject();
		obj.put("complaint_id", cmpId);
		logger.info("投诉质检单是否可以撤销   投诉单号:" + cmpId);
		
		String result = new RestClient(serverURL + "/ssi/restfulserver/complaint/getComplaintCancelState", "GET", obj).execute();
		
		logger.info("投诉质检单是否可以撤销   result:" + result);
		
		boolean flag = false;
		if( null != result ) {
			flag = JSON.parseObject(result).getBoolean("isCancel");
		}
		
		return flag;
	}

}
