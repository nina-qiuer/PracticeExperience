package com.tuniu.qms.access.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.dto.UserDepjobDto;
import com.tuniu.qms.common.model.User;
import com.tuniu.qms.common.service.UserService;

@Controller
@RequestMapping("/access/user")
public class UserServer {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) {
		
		RestServer server = new RestServer(request, response);
		try {
			
			User user = server.getRestData(User.class);
			service.add(user);
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
			
			User user = server.getRestData(User.class);
			service.update(user);
			server.writeResponseDefault();
			
		} catch (Exception e) {
			
			ResponseDto dto =new ResponseDto();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
			dto.setErrorCode(12001);
			server.writeResponse(dto);
		}
	}
	
	@RequestMapping(value="/addUserDepjob", method=RequestMethod.POST)
	public void addUserDepjob(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		
		try {
			
			UserDepjobDto dto = server.getRestData(UserDepjobDto.class);
			service.addUserDepjob(dto);
			server.writeResponseDefault();
			
		} catch (Exception e) {
			
			ResponseDto dto =new ResponseDto();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
			dto.setErrorCode(12001);
			server.writeResponse(dto);
		}
	}
	
	@RequestMapping(value="/updateUserDepjob", method=RequestMethod.POST)
	public void updateUserDepjob(HttpServletRequest request, HttpServletResponse response) {
		
		RestServer server = new RestServer(request, response);
		
		try {
			
			UserDepjobDto dto = server.getRestData(UserDepjobDto.class);
			service.updateUserDepjob(dto);
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
