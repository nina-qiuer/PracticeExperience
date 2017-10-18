package com.tuniu.qms.qc.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.common.util.Page;
import com.tuniu.qms.qc.dao.RespBillMapper;
import com.tuniu.qms.qc.model.RespBill;
import com.tuniu.qms.qc.service.RespBillService;

@Service
public class RespBillServiceImpl implements RespBillService {
	
	private final static Logger logger = LoggerFactory.getLogger(RespBillServiceImpl.class);

	@Autowired
	private  RespBillMapper  respBillMapper;
	
	    /**
	     * 查询责任单数据
	     * @param queryParams
	     * @return
	     */
	   public Page queryRespBillList(Map<String, Object> map)
	    {
		   Page pageAll = new Page();
		   try {
			   
			   int rows = (Integer) map.get("rows");//每页显示行数
			   int page = (Integer) map.get("page");//当前页
			   int count = respBillMapper.queryRespBillCount(map);//总条数
			   int totalPage = count % rows== 0 ? (count/rows) : (count/rows + 1); //总记录数
			   int start = (page - 1) * rows; // 开始记录数
			   int end = rows + start;//结束记录数
			   map.put("start", start);
			   map.put("end", end);
			   List<RespBill> list = respBillMapper.queryRespBillList(map);
		       pageAll.setRecords(list);
		       pageAll.setTotalRecords(count);
		       pageAll.setCurrPage(page);
		       pageAll.setTotalPages(totalPage);
		       return pageAll;
		       
		} catch (Exception e) {
			 
			   logger.info(e.getMessage()+"查询失败");
			   pageAll.setRecords(null);
		       pageAll.setTotalRecords(0);
		       pageAll.setCurrPage(1);
		       pageAll.setTotalPages(0);
			   return pageAll;
		}
		  
	  }

	   /**
	    * 删除责任单
	    */
		public int deleteResp(Map<String, Object> map) {
			
			
			try {
				
				respBillMapper.deleteResp(map);
				return 0;
				
			} catch (Exception e) {
				
				return 1;
			}
			
		}

}
