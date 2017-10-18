package com.tuniu.gt.frm.webservice.uc;

import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcServer;




enum XmlrpcServers {
    UserServer,
    DepartmentServer	
}

public class XmlRpcServlet extends HttpServlet  {

	private static final long serialVersionUID = 6000171707315959989L;
	
	private Logger logger = Logger.getLogger(getClass());
	
	public void doPost(HttpServletRequest request,HttpServletResponse response ) {
		
		String prefix = "TuniuComplaint";
		XmlRpcServer xmlrpc = new XmlRpcServer ();
		String pathString = request.getPathInfo().substring(1);	
		//XmlrpcServers server = XmlrpcServers.valueOf(pathString);
		
		if ("User.server.php".equals(pathString)) {
			xmlrpc.addHandler (prefix, new UserServer());
		} else if ("Department.server.php".equals(pathString)) {
			xmlrpc.addHandler (prefix, new DepartmentServer());
		} else if ("Job.server.php".equals(pathString)) {
			xmlrpc.addHandler (prefix, new JobServer());
		} else if ("Position.server.php".equals(pathString)) {
			xmlrpc.addHandler (prefix, new PositionServer());
		}
//		switch(server) {
//		case UserServer:
//			xmlrpc.addHandler (prefix, new UserServer());  
//			break;
//		case DepartmentServer:
//			xmlrpc.addHandler (prefix, new DepartmentServer());
//		break;
//		
//		
//		default:
//			break;
//		
//		}
		

		byte[] result;
		try {
			result = xmlrpc.execute(request.getInputStream ());
			response.setContentType ("text/xml");
			response.setContentLength (result.length);
			OutputStream out = response.getOutputStream();
			out.write (result);
			out.flush ();
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		
     }
}
