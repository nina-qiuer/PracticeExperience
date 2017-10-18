package com.tuniu.qms.access.server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tuniu.qms.access.dto.ResponseDto;
import com.tuniu.qms.common.dto.DepartmentJobDto;
import com.tuniu.qms.common.dto.DepartmentUserDto;
import com.tuniu.qms.common.model.Department;
import com.tuniu.qms.common.service.DepartmentService;

@Controller
@RequestMapping("/access/department")
public class DepartmentServer {
	
	@Autowired
	private DepartmentService service;
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public void add(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		
		try {
			
			Department dep = server.getRestData(Department.class);
			service.add(dep);
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
			
			Department dep = server.getRestData(Department.class);
			service.update(dep);
			server.writeResponseDefault();
			
		} catch (Exception e) {
			
			ResponseDto dto =new ResponseDto();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
			dto.setErrorCode(12001);
			server.writeResponse(dto);
		}
	}
	
	@RequestMapping(value="/addDepUser", method=RequestMethod.POST)
	public void addDepUser(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		
		try {
			
			DepartmentUserDto dto = server.getRestData(DepartmentUserDto.class);
			service.addDepUser(dto);
			server.writeResponseDefault();
			
		} catch (Exception e) {
		
			ResponseDto dto =new ResponseDto();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
			dto.setErrorCode(12001);
			server.writeResponse(dto);
		}
	}
	
	@RequestMapping(value="/updateDepUser", method=RequestMethod.POST)
	public void updateDepUser(HttpServletRequest request, HttpServletResponse response) {
		
		RestServer server = new RestServer(request, response);
		try {
			
			DepartmentUserDto dto = server.getRestData(DepartmentUserDto.class);
			service.updateDepUser(dto);
			server.writeResponseDefault();
			
		} catch (Exception e) {
		
			ResponseDto dto =new ResponseDto();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
			dto.setErrorCode(12001);
			server.writeResponse(dto);
		}
	}
	
	@RequestMapping(value="/deleteDepUser", method=RequestMethod.POST)
	public void deleteDepUser(HttpServletRequest request, HttpServletResponse response) {
		
		RestServer server = new RestServer(request, response);
		try {
			
			DepartmentUserDto dto = server.getRestData(DepartmentUserDto.class);
			service.deleteDepUser(dto.getId());
			server.writeResponseDefault();
			
		} catch (Exception e) {
		
			ResponseDto dto =new ResponseDto();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
			dto.setErrorCode(12001);
			server.writeResponse(dto);
		}
		
	}
	
	@RequestMapping(value="/addDepJob", method=RequestMethod.POST)
	public void addDepJob(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		
		try {
					
			DepartmentJobDto dto = server.getRestData(DepartmentJobDto.class);
			service.addDepJob(dto);
			server.writeResponseDefault();
			
		} catch (Exception e) {
		
			ResponseDto dto =new ResponseDto();
			dto.setSuccess(false);
			dto.setMsg(e.getMessage());
			dto.setErrorCode(12001);
			server.writeResponse(dto);
		}
	}
	
	@RequestMapping(value="/updateDepJob", method=RequestMethod.POST)
	public void updateDepJob(HttpServletRequest request, HttpServletResponse response) {
		RestServer server = new RestServer(request, response);
		
		try {
			
			DepartmentJobDto dto = server.getRestData(DepartmentJobDto.class);
			service.updateDepJob(dto);
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
