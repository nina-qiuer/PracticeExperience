package com.tuniu.gt.complaint.webservice;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.xmlrpc.XmlRpcServer;




enum XmlrpcServers {
	ShareSolutionServer
}

public class ComplaintXmlRpcServlet extends HttpServlet  {
	
	private Logger logger = Logger.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = -5226851066241949621L;

	public void doPost(HttpServletRequest request,HttpServletResponse response ) {
		
		String prefix = "TuniuComplaint";
		XmlRpcServer xmlrpc = new XmlRpcServer ();
		String pathString = request.getPathInfo().substring(1);	
		XmlrpcServers server = XmlrpcServers.valueOf(pathString);
		switch(server) {
		case ShareSolutionServer:
			xmlrpc.addHandler (prefix, new ShareSolutionServer());  
			break;
		
		default:
			break;
		
		}
		

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
