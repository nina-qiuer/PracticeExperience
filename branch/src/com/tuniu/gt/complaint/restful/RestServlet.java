package com.tuniu.gt.complaint.restful;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import com.tuniu.trest.TRestException;
import com.tuniu.trest.TRestServer;

public class RestServlet extends HttpServlet {

	private static final long serialVersionUID = -4007999999227115866L;
	
	private static Logger logger = Logger.getLogger(RestServlet.class);

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		logger.info("RemoteAddr is: " + req.getRemoteAddr());

		resp.setHeader("Access-Control-Allow-Origin", "*"); //方便restful工具测试，解除跨域访问限制
		TRestServer server = new TRestServer(req, resp);
		Map<String,Object> returnMp = new HashMap<String, Object>();
		try {
			String data = server.getRestData();
			RestServerHandlerInterface restServer = RestServerFactory.getRestServer(data);
			Object contentResult = restServer.execute(data);
			returnMp.put("result","success");
			returnMp.put("reason","");
			returnMp.put("content",contentResult);
			
			server.sendRestData(JSONObject.fromObject(returnMp).toString());
		} catch (TRestException e) {
			returnMp.put("result","fail");
			returnMp.put("reason","获取投诉信息失败...");
			server.sendRestData(JSONObject.fromObject(returnMp).toString());
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
