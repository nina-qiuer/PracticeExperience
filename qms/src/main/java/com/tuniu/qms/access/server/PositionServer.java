package com.tuniu.qms.access.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.model.Position;
import com.tuniu.qms.common.service.PositionService;

@Controller
@RequestMapping("/access/position")
public class PositionServer {
	
	@Autowired
	private PositionService service;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		try {
			
			Position pos = server.getRestData(Position.class);
			service.add(pos);
			server.writeResponseDefault();
			
		} catch (Exception e) {
		
			ResponseDto dto =new ResponseDto();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
			dto.setErrorCode(12001);
			server.writeResponse(dto);
		}
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public void update(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		
		try {
			
			Position pos = server.getRestData(Position.class);
			service.update(pos);
			server.writeResponseDefault();
			
		} catch (Exception e) {
			
			ResponseDto dto =new ResponseDto();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
			dto.setErrorCode(12001);
			server.writeResponse(dto);
		}
	}

}
