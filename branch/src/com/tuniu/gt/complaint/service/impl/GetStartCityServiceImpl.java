package com.tuniu.gt.complaint.service.impl;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;
import tuniu.frm.core.webservice.WebServiceClientBase;

import com.tuniu.gt.complaint.dao.impl.ComplaintDao;
import com.tuniu.gt.complaint.service.IGetStartCityService;
import com.tuniu.gt.complaint.Constans;

/**
* @ClassName: ComplaintServiceImpl
* @Description:complaint接口实现
* @author Ocean Zhong
* @date 2012-1-20 下午5:04:51
* @version 1.0
* Copyright by Tuniu.com
*/
@Service("complaint_service_complaint_impl-get_start_city")
public class GetStartCityServiceImpl extends ServiceBaseImpl<ComplaintDao> implements IGetStartCityService {
	@Autowired
	@Qualifier("complaint_dao_impl-complaint")
	public void setDao(ComplaintDao dao) {
		this.dao = dao;
	}

	private WebServiceClientBase xBase=new WebServiceClientBase();
	

	/* (non-Javadoc)
	 * @see com.tuniu.gt.complaint.service.IComplaintService#getOrderInfo(java.lang.String)
	 */
	@Override
	public List<Map> getAllStartCityInfo() {
		

//		String url = Constans.DEPARTURE_URL;// 接口地址
//		Vector<String> params = new Vector<String>();
//		String result = "false";
//		
//		result=xBase.xmlrpc(url, "TuniuBoss.getOrderInfo", params).toString();
////		result = xmlrpc.execute("TuniuBoss.getOrderInfo", params).toString();
//		if(!"false".equals(result)){
//			try {
//				return (List<Map>) analyticJson(result);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
		return null;
		
	}
	
	/**
	 * @return 
	 * @throws ParseException 
	*
	* 处理json数据，
	* 将数据放到complaintEntity对象中
	* @param jsonString
	* @return    
	* ComplaintEntity    
	* @throws
	*/
	public List<Map> analyticJson(String jsonString) throws ParseException{
		
		List<Map> res_return = null;
		//新建投诉对象，将投诉内容放入对象内
		try{
			//JSON格式数据解析对象 
			JSONObject jb=JSONObject.fromObject(jsonString);
			for (int i=0 ; i < jb.size() ; i++ ) {
				res_return.add((Map) jb.get(i));
			}
			return res_return;
		}catch (JSONException e){
			
			return null;
		}
		
	}
}
