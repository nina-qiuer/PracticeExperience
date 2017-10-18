package com.tuniu.gt.complaint.restful;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tuniu.gt.complaint.entity.AgencyToChatEntity;
import com.tuniu.gt.complaint.entity.SupportShareEntity;
import com.tuniu.gt.complaint.webservice.ComplaintWSClient;
import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;
import com.tuniu.trest.TRestMethod;

public class TestClient {

	public static void main(String[] args) throws TRestException, UnsupportedEncodingException {
		
//		List<SupportShareEntity> sEList = new ArrayList<SupportShareEntity>();
//		sEList = ComplaintWSClient.getAgencyInfo(4368188);
//		System.out.println(sEList);
		//-------建立群组---------------
//		
//		TRestClient trestClient = new TRestClient("http://172.30.57.30:9090/plugins/nbooking/createnewmucgroup");
//		trestClient.setMethod("GET");
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("username", "tuniu-7579");
//		data.put("agencyId", "1169");
//		data.put("complaintID", "168486");
//		data.put("orderID", "400085");
//		data.put("productID", "17821");
//		data.put("productName", "<上海都市1日>游东方明珠，乘黄浦江游船，感受时尚，繁华，魅力");
//		data.put("ticketType",1);
//		String resStr = JSONObject.fromObject(data).toString();
//		trestClient.setData(resStr);
//		String execute = trestClient.execute();
//		System.out.println(execute);  //roomID":84
		//--------------------------
		
		//------------------发起会话-----------
//		TRestClient trestClient = new TRestClient("http://172.30.57.30:9090/plugins/nbooking/sendmessage");
//		trestClient.setMethod("GET");
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("userType", "0");
//		data.put("roomID", "85" );
//		data.put("contentType", "1");
//		data.put("username", "tuniu-8324");
//		Map<String,Object> contentMap =new HashMap<String, Object>();
//		contentMap.put("text","今天是个好天气");
//		 data.put("content", contentMap);
//		String resStr = JSONObject.fromObject(data).toString();
//		trestClient.setData(resStr);
//		String execute = trestClient.execute();
//		System.out.println(execute); 
	//----------------------------------------------------------
		
		//-------------------------查询投诉单所有房间列表-------------------------
//		TRestClient trestClient = new TRestClient("http://172.30.57.30:9090/plugins/nbooking/querycomplaintlist");
//		List<String> list =new ArrayList<String>();
//		list.add("1");
//		list.add("2");
//		list.add("3");
//		trestClient.setMethod("GET");
//		Map<String, Object> data = new HashMap<String, Object>();
//		//data.put("complaintId", 213407);
//		data.put("pageindex", 1);
//		//data.put("status",list );
//		data.put("pagelimit", 1000);
//		data.put("username", "tuniu-wangmingfang");
//		String resStr = JSONObject.fromObject(data).toString();
//		trestClient.setData(resStr);
//		String execute = trestClient.execute();
//		System.out.println(execute); 
//		
		
//		{
//		    "success":true,
//		    "msg":"请求正常",
//		    "errorCode":21000,
//		    "data":{
//		        "pagecount":1,
//		        "pageindex":1,
//		        "pagelimit":10,
//		        "total":1,
//		        "list":[
//		            {
//		                "orderID":4373605,
//		                "status":0,
//		                "remark":"",
//		                "msgSendTime":"2015-05-28 19:45:38",
//		                "complaintID":213852,
//		                "agencyID":"tuniu-8324",
//		                "productID":287094,
//		                "roomID":85,
//		                "id":37,
//		                "creationDate":"2015-05-28 19:44:37",
//		                "ticketType":1,
//		                "roomName":"nbMucRoom_85",
//		                "msgSenderType":0,
//		                "msgContent":{
//		                    "text":"今天是个好天气"
//		                },
//		                "msgContentType":1,
//		                "modificationDate":"",
//		                "msgSender":"tuniu-8324",
//		                "productName":"[春节]<巴厘岛4晚6日游>海底漫步，水上发呆亭，热石按摩",
//		                "delFlag":0
//		            }
//		        ]
//		    }
//		}
		
		
		//----------------查询咨询投诉沟通单据的群组聊天记录---------
//		TRestClient trestClient = new TRestClient("http://172.30.57.30:9090/plugins/nbooking/queryhistorybypage");
//		trestClient.setMethod("GET");
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("roomID", 146);
//		data.put("pageindex", 1);
//		data.put("pagelimit", 1000);
//		String resStr = JSONObject.fromObject(data).toString();
//		trestClient.setData(resStr);
//		String execute = trestClient.execute();
//		System.out.println(execute); 
		
//		{
//		    "success":true,
//		    "msg":"请求正常",
//		    "errorCode":21000,
//		    "data":{
//		        "pagecount":1,
//		        "pageindex":1,
//		        "pagelimit":10,
//		        "total":1,
//		        "list":[
//		            {
//		                "id":137,
//		                "content":{
//		                    "text":"今天是个好天气"
//		                },
//		                "creationDate":"2015-05-28 19:45:38",
//		                "username":"tuniu-8324",
//		                "contentType":1,
//		                "modificationDate":"2015-05-28 19:45:38",
//		                "userType":0,
//		                "roomID":85
//		            }
//		        ]
//		    }
//		}
//---------------------------更改聊天室状态和类型---------------------------
		
//		
//		TRestClient trestClient = new TRestClient("http://172.30.57.30:9090/plugins/nbooking/updatemucroomdetail");
//		trestClient.setMethod("GET");
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("roomID", 85);
//		//data.put("ticketType", 2);
//		data.put("status", 4);
//		String resStr = JSONObject.fromObject(data).toString();
//		trestClient.setData(resStr);
//		String execute = trestClient.execute();
//		System.out.println(execute); 
//		
//-------------------------定时任务
		

//		Map<String, Object> map =new HashMap<String, Object>();
//		map.put("offsetMinutes", 15);
//		String responseStr= JSONObject.fromObject(map).toString();
//		TRestClient client = new TRestClient("http://172.30.57.30:9090/plugins/nbooking/reply-timeout-list", TRestMethod.GET, responseStr);
//		String ret = client.execute();
//		System.out.println(ret);
//		JSONObject jsonObject = JSONObject.fromObject(ret);
//		System.out.println(jsonObject);
//		
		//-----------------------------------------
		
//		TRestClient trestClient = new TRestClient("http://localhost:8080/ssi/restfulserver/complaint/queryComplaintDetail");
//		trestClient.setMethod("GET");
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("complaintId", 213407);
//		data.put("agencyId", 1169);
//		data.put("roomId", 152);
//		String resStr = JSONObject.fromObject(data).toString();
//		trestClient.setData(resStr);
//		String execute = trestClient.execute();
//		System.out.println(execute);
//		
		
//		TRestClient trestClient = new TRestClient(
//				"http://172.30.57.30:9090/plugins/nbooking/sendmessage");
//		TRestClient trestClient = new TRestClient(
//				"http://172.30.57.30:9090/plugins/nbooking/queryhistorybypage");
		// 
//		trestClient.setMethod("GET");
//		Map<String, Object> data = new HashMap<String, Object>();
//    //	String newStr = new String(aaa.getBytes("gb2312"),"utf-8");
//		//1281313 多条投诉信息
//		data.put("roomID", "56");
//		data.put("pageindex", "1");
//		data.put("pagelimit", "10");
//		data.put("userType", "0");
//		data.put("roomID", "56" );
//		data.put("contentType", "1");
//		data.put("username", "789");
//		    Map<String,Object> contentMap =new HashMap<String, Object>();
//		    contentMap.put("text","测试赛聊天");
//		    data.put("content", contentMap);
		
//		String resStr = JSONObject.fromObject(data).toString();
		
//	    byte[] aa = Base64.decodeBase64(resStr); 
//	    System.out.println(new String(aa));
//		JSONObject jsonData = JSONObject.fromObject(new String(aa));
//		System.out.println(jsonData);
//		trestClient.setData(resStr);
//		String execute = trestClient.execute();
//		System.out.println(new String(execute.getBytes(),"UTF-8"));
//		System.out.println(execute);
//		JSONObject aa =JSONObject.fromObject(execute);
//		int errorCode =  aa.getInt("errorCode");
//		if(errorCode!=230000){
//			
//			JSONObject ja = aa.getJSONObject("data");
//			String bb =	ja.getString("complaintId"); // 客人姓名
//			System.out.println(bb);
//		}
//		JSONObject ja = aa.getJSONObject("data");
//		JSONArray temp = ja.getJSONArray("list");
//		for (int i = 0; i < temp.size(); i++) {
//			AgencyToChatEntity at = new AgencyToChatEntity();
//			at.setDescript( temp.getJSONObject(i).getJSONObject("content").getString("text"));
//			at.setDealName( temp.getJSONObject(i).getString("username"));
//			at.setFlag( temp.getJSONObject(i).getString("userType"));
//			at.setContentType( temp.getJSONObject(i).getString("contentType"));
//			at.setCommitTime( temp.getJSONObject(i).getString("creationDate"));
//			at.setRoomId( temp.getJSONObject(i).getString("roomID"));
//		}
//		Map<String, Object> resMap = new HashMap<String, Object>();
//		JSONObject jsonInput = new JSONObject();
//		JSONObject jsonParam = new JSONObject();
//		jsonInput.put(Integer.toString(0), 17934165);
//		jsonParam.accumulate("func", "getTouristById");
//		jsonParam.accumulate("params", jsonInput);
//		try {
//			TRestClient client = new TRestClient("http://fab.mkt.tuniu.org/interface/tourist", TRestMethod.GET, jsonParam.toString());
//			//TRestClient client = new TRestClient("http://fab.tuniu.org/interface/tourist", TRestMethod.GET, jsonParam.toString());
//			String ret = client.execute();
//			JSONObject jsonObject = JSONObject.fromObject(ret);	
//			Boolean isSuccess = jsonObject.getBoolean("res");
//			if(isSuccess) {
//				JSONObject info = jsonObject.getJSONObject("0");
//				if (null != info && !info.isNullObject()) {
//					resMap.put("name", info.getString("name"));
//					resMap.put("tel", info.getString("tel"));
//					resMap.put("email", info.getString("email"));
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
