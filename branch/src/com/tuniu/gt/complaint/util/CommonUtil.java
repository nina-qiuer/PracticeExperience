package com.tuniu.gt.complaint.util;

import org.apache.log4j.Logger;

import com.tuniu.trest.TRestClient;
import com.tuniu.trest.TRestException;
import com.tuniu.trest.TRestMethod;

import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import tuniu.frm.service.Constant;

public class CommonUtil {
	
	private static Logger logger = Logger.getLogger(CommonUtil.class);
	
	public static boolean isStatus(String extension) {
		return isStatus(extension, "");
	}
	
	/**
	 * 查询IVR系统客服在线状态
	 * @param userId
	 * @return
	 */
	public static boolean isStatus(String extension, String dealDepart) {
		//以下代码不能上到生产环境  请注意
		if(extension.equals("11111")||extension.equals("22222")||extension.equals("33333")||extension.equals("44444")){
			return true;
		}
		if(extension.equals("99999")){
			return false;
		}
		//以上代码不能上到生产环境  请注意
		
		if (Constant.CONFIG.getProperty("sendFlag").equals("0")) {
			return true; // 如果是测试环境，默认在线
		}
		boolean flag = false;
		try {
			TRestClient trestClient = new TRestClient(Constant.CONFIG.getProperty("CS_INFO")+extension, TRestMethod.GET, "");
			String execute = trestClient.execute();
			JSONObject fromObject = JSONObject.fromObject(execute);
			boolean isSucc = fromObject.getBoolean("success");
            if (isSucc) {
            	Object data = fromObject.get("data");
				if (!(data instanceof JSONNull)) {
					JSONObject jsonData = JSONObject.fromObject(data);
					Object agentid = jsonData.get("agentid");
					if (!(agentid instanceof JSONNull)) {
                        int status = jsonData.getInt("status");
                        if(status == 0) {
                            flag = true;
                        }
                    }
                }
            }
		} catch (TRestException e) {
			logger.error("isStatus failed extension:"+extension+"error:"+ e.getMessage(),e);
		}
		return flag;
	}
	
    public static String toHtml(String s)
    {
        if(s != null && s.length() > 0)
        {
            s = s.replaceAll("(\r|\n|\r\n|\n\r)", " ");
            s = s.replaceAll("\"", "\\\\" + "\"");
            s = s.replaceAll("\'", "\\\\" + "\'");
            return s;
        }
        else
        {
            return "";
        }
    }
}
