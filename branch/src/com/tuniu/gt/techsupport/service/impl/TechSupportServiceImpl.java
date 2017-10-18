package com.tuniu.gt.techsupport.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import tuniu.frm.core.ServiceBaseImpl;

import com.tuniu.gt.techsupport.dao.impl.TechSupportDao;
import com.tuniu.gt.techsupport.service.ITechSupportService;
@Service("ts_service_impl-tech_support")
public class TechSupportServiceImpl extends ServiceBaseImpl<TechSupportDao> implements ITechSupportService
{
	private static Logger logger = Logger.getLogger(TechSupportServiceImpl.class);
	private Map<String ,Object>  info = new HashMap<String, Object>();
	
	@Autowired
	@Qualifier("ts_dao_impl-tech_support")
	public void setDao(TechSupportDao dao) {
		this.dao = dao;
	}

	@Override
	public void execute(String sqlParam) {
		if(preCheck(sqlParam)){
			dispatch(sqlParam);
		}
	}
	
	/**
	 * @param sqlParam
	 */
	private boolean  preCheck(String sqlParam) {
		
		int affectRows = getAffectRows(sqlParam);
		
		if(affectRows==0){
			return false;
		}
		return true;
	}

	/**
	 * @param sqlParam
	 */
	private void dispatch(String sqlParam) {
		update(sqlParam);
	}

	private void update(String sqlParam){
		dao.update(sqlParam);
	}
	
	private void delete(String sqlParam){
		
	}
	
	private int getAffectRows(String sqlParam) {
	    if(sqlParam.startsWith("insert")||sqlParam.startsWith("INSERT")) {
	        return 1;
	    }else {
	        return dao.getAffectRows(getAffectRowsSql(sqlParam));
	    }
	}
	
	private String getAffectRowsSql(String sqlParam){
		StringBuilder affectRowsSql = new StringBuilder(); 
		
		
		String[] sqlElements = sqlParam.split("[ ]+");
		String tableName = "";
		int index = sqlParam.indexOf("where");
		if(index==-1){
		    index = sqlParam.indexOf("WHERE");
		}
		String whereSql = sqlParam.substring(index);
		
		if("update".equalsIgnoreCase(sqlElements[0])){
			tableName = sqlElements[1];
		}else if("delete".equalsIgnoreCase(sqlElements[0])){
			tableName = sqlElements[2];
		}
		
		affectRowsSql.append("select 1 from ").append(tableName).append(" ").append(whereSql);
		return affectRowsSql.toString();
	}

}
