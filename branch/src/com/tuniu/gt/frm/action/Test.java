package com.tuniu.gt.frm.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.tuniu.gt.complaint.entity.ComplaintEntity;
import com.tuniu.gt.complaint.entity.OrderEntity;
import com.tuniu.gt.complaint.vo.ComplaintReasonVo;
import com.tuniu.gt.frm.entity.UserEntity;
import com.tuniu.gt.toolkit.DateUtil;
import com.tuniu.operation.platform.hogwarts.util.StringUtil;

import tuniu.frm.core.webservice.WebServiceClientBase;
import tuniu.frm.service.PHPSerializer;

@Service("frm-test")
@Scope("prototype")
public class Test {
	private WebServiceClientBase cb = new WebServiceClientBase();
	
	private Logger logger = Logger.getLogger(getClass());
	public String execute() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("w1", "王小全");
//		String xString = cb.http("http://localhost/test.php?x=test",paramMap).toString();
//		System.out.println(xString);
//		Vector<String> params = new Vector<String>();
//		params.add("测试啊");
//		String rpcString = cb.xmlrpc("http://localhost/tuniu/crmst/new_frm/xmlrpc/test.server.php", "TuniuBoss.t", params).toString();
//		System.out.println(rpcString); 
		
		
		String string = new String(PHPSerializer.serialize(paramMap));
		System.out.println(string); 
		String us = "a:2:{s:2:\"w1\";s:9:\"王小全\";s:2:\"w2\";a:2:{s:2:\"id\";i:1;s:4:\"type\";s:6:\"体育\";}}";
		Map<String, Object> map  = null;
		try {
			map = (Map<String, Object>)PHPSerializer.unserialize(us.getBytes()); 
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
		}
		System.out.println(string); 
		return "success";
	}
	 public static void main(String[] args) {
		 
		 
		 String na="忍受保险会死";
		 
		 if(na.contains("保险")){
			 
			 System.out.println(999);
		 }
		 
		 
		 
		 
		 
			String customer = "null";
			String customerLeader = "null";
			String serviceManager = "null";
			if(StringUtil.isNotEmpty(customer)&& !"null".equals(customer)){
				
				System.out.println(222);
			}
			
			
			if ( (StringUtil.isNotEmpty(customer) && !"null".equals(customer))
					|| (StringUtil.isNotEmpty(customerLeader) && !"null".equals(customerLeader))
					|| (StringUtil.isNotEmpty(serviceManager) && !"null".equals(serviceManager))) {
		 
				System.out.println(111111111);
			}
		 
		 
		 ComplaintReasonVo vo =new ComplaintReasonVo();
		 System.out.println(vo.getSecondType());
		 
		int hh = DateUtil.getWeek(new Date());
		 
		/* String dealName="";
		 Integer  dealPeople =0;
		 UserEntity user = null;
		 String  names[] = { "222", "22", "" };
			for (int i = 0; i < names.length; i++) {
				if (!"".equals(names[i])) {
					
					if (null!= user  && 0 == user.getDelFlag()) {
						dealName = names[i];
						dealPeople = user.getId();
						break;
					}
				}
			}*/
		 
		 
		 
		 
		 
		 
		 
		 
		 
		int i =7;
		 
		 
		 do{
			    
			    System.out.println(i);
			    i++;
			    if(i==8){
			    	break;
			    	
			    }
			    }while(i<10);
			       
		 
		 
		 
		 
		 
		 
		 
		 
		 
    	try {
   		 String responseData = new String("ewogICJzdWNjZXNzIjogdHJ1ZSwKICAibXNnIjogbnVsbCwKICAiZXJyb3JDb2RlIjogMCwKICAiZGF0YSI6IHsKICAgICJ1c2VySWQiOiAyNjc4OTI3NiwKICAgICJhZHVsdE51bSI6IDIsCiAgICAiY2hpbGRyZW5OdW0iOiAwLAogICAgInN0YXJ0Q2l0eSI6ICLljJfkuqwiLAogICAgImVuZENpdHkiOiAi5pSA54mZLeeUsuexsy3mma7lkInluIIt55Sy57GzIiwKICAgICJyb3V0ZSI6ICJb5pil6IqCXSZsdDvmma7lkIkt55Sy57GzLeaWr+exs+WFsDbmiJY35pel5ri4Jmd0OzLmmZrlm73pmYXkupTmmJ/phZLlupfvvIwx5pma5rOz5rGg5Yir5aKF77yM55u06aOeIiwKICAgICJyb3V0ZUlkIjogIjMyNjQzOSIsCiAgICAib3JkZXJUeXBlIjogIui3n+WboiIsCiAgICAic3RhcnRUaW1lIjogIjIwMTUtMTItMjkiLAogICAgImJhY2tUaW1lIjogIjIwMTYtMDEtMDMiLAogICAgIm9yZGVySWQiOiA3MDY0NDA5LAogICAgImNvbnRhY3RMaXN0IjogWwogICAgICB7CiAgICAgICAgImlkIjogMjM1MjUxNTkyLAogICAgICAgICJvcmRlcklkIjogNzA2NDQwOSwKICAgICAgICAiY29udGFjdElkIjogNDM5ODg5NjcsCiAgICAgICAgImNvbnRhY3ROYW1lIjogIuael+WyqSIsCiAgICAgICAgInRlbCI6ICIxMzkxMDE3NzMwMCIsCiAgICAgICAgImVtYWlsIjogIjE5NzkwMzA5bHlAMTYzLmNvbSIsCiAgICAgICAgInBlcnNvblR5cGUiOiAxCiAgICAgIH0sCiAgICAgIHsKICAgICAgICAiaWQiOiAyMzUyNTE1OTMsCiAgICAgICAgIm9yZGVySWQiOiA3MDY0NDA5LAogICAgICAgICJjb250YWN0SWQiOiA0NDAzMzgxMywKICAgICAgICAiY29udGFjdE5hbWUiOiAi5p6X5bKpIiwKICAgICAgICAidGVsIjogIjEzOTEwMTc3MzAwIiwKICAgICAgICAiZW1haWwiOiAiIiwKICAgICAgICAicGVyc29uVHlwZSI6IDIKICAgICAgfSwKICAgICAgewogICAgICAgICJpZCI6IDIzNTI1MTU5NCwKICAgICAgICAib3JkZXJJZCI6IDcwNjQ0MDksCiAgICAgICAgImNvbnRhY3RJZCI6IDQ0MDMzODE2LAogICAgICAgICJjb250YWN0TmFtZSI6ICLliJjlkJvlm70iLAogICAgICAgICJ0ZWwiOiAiIiwKICAgICAgICAiZW1haWwiOiAiIiwKICAgICAgICAicGVyc29uVHlwZSI6IDIKICAgICAgfQogICAgXSwKICAgICJhaXJGbGFnIjogMCwKICAgICJmbGlnaHRQcmljZSI6IDAsCiAgICAic2lnbkNpdHlOYW1lIjogIuWMl+S6rCIsCiAgICAic2lnbkNpdHlDb2RlIjogIjIwMCIsCiAgICAic291cmNlIjogIuacquefpeiuouWNlSIsCiAgICAicHJpY2UiOiAxMTM3NiwKICAgICJhZ2VuY3lMaXN0IjogWwogICAgICB7CiAgICAgICAgImFnZW5jeUlkIjogMTY4OSwKICAgICAgICAiYWdlbmN5TmFtZSI6ICLlhajmma/ml4XmuLjvvIjljJfkuqzvvIkiLAogICAgICAgICJvcmRlcklkIjogNzA2NDQwOQogICAgICB9LAogICAgICB7CiAgICAgICAgImFnZW5jeUlkIjogMTY4OSwKICAgICAgICAiYWdlbmN5TmFtZSI6ICLlhajmma/ml4XmuLjvvIjljJfkuqzvvIkiLAogICAgICAgICJvcmRlcklkIjogNzA2NDQwOQogICAgICB9LAogICAgICB7CiAgICAgICAgImFnZW5jeUlkIjogODQxOCwKICAgICAgICAiYWdlbmN5TmFtZSI6ICLlubPlronlhbvogIHkv53pmanogqHku73mnInpmZDlhazlj7jmsZ/oi4/liIblhazlj7giLAogICAgICAgICJvcmRlcklkIjogNzA2NDQwOQogICAgICB9CiAgICBdLAogICAgImFnZW50TGlzdCI6IFsKICAgICAgewogICAgICAgICJhZ2VudElkIjogNzkzOCwKICAgICAgICAiYWdlbnROYW1lIjogIuiSi+aipuS6kSIsCiAgICAgICAgImFnZW50VHlwZSI6IDEsCiAgICAgICAgIm9yZGVySWQiOiA3MDY0NDA5LAogICAgICAgICJhZ2VudE1hbmFnZXJJZCI6IDIzNjUsCiAgICAgICAgImFnZW50TWFuYWdlck5hbWUiOiAi6ZmG5pWPIgogICAgICB9LAogICAgICB7CiAgICAgICAgImFnZW50SWQiOiAxMzI3NiwKICAgICAgICAiYWdlbnROYW1lIjogIueOi+e6ouaihTMiLAogICAgICAgICJhZ2VudFR5cGUiOiAzLAogICAgICAgICJvcmRlcklkIjogNzA2NDQwOSwKICAgICAgICAiYWdlbnRNYW5hZ2VySWQiOiAxMTAyNiwKICAgICAgICAiYWdlbnRNYW5hZ2VyTmFtZSI6ICLlrZnlqbflqbciCiAgICAgIH0sCiAgICAgIHsKICAgICAgICAiYWdlbnRJZCI6IDgzNDQsCiAgICAgICAgImFnZW50TmFtZSI6ICLlvpDlrabmlY8iLAogICAgICAgICJhZ2VudFR5cGUiOiAyLAogICAgICAgICJvcmRlcklkIjogNzA2NDQwOSwKICAgICAgICAiYWdlbnRNYW5hZ2VySWQiOiAzNzM1LAogICAgICAgICJhZ2VudE1hbmFnZXJOYW1lIjogIumDreWwj+WNjiIKICAgICAgfQogICAgXSwKICAgICJjbGllbnRUeXBlIjogMjAwMDAsCiAgICAiaGFzRGV2b2x2IjogMQogIH0KfQ==");

			String responseDetail = new String(Base64.decodeBase64(responseData.getBytes("utf-8")));
			System.out.println(responseDetail);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
		 
	Integer a=	 DateUtil.getField(Calendar.YEAR);
		 System.out.println(a);
			Map<String, Object> paramMap1 = new HashMap<String, Object>();
			List<Integer> list =new ArrayList<Integer>();
			list.add(1);
			list.add(2);
			list.add(3);
			list.add(4);
			paramMap1.put("list", list);
			System.out.println(paramMap1);
	}
	
}
