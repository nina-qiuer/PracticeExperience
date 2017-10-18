package com.tuniu.qms.tecksupport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuniu.qms.tecksupport.dao.TechSupportMapper;
import com.tuniu.qms.tecksupport.dto.TechSupportDto;
import com.tuniu.qms.tecksupport.entity.TechSupport;
import com.tuniu.qms.tecksupport.service.ITechSupportService;

@Service
public class TechSupportServiceImpl implements ITechSupportService {
	
	private TechSupportMapper mapper;
	
	public TechSupportMapper getMapper() {
        return mapper;
    }
	
	@Autowired
    public void setMapper(TechSupportMapper mapper) {
	    System.out.println("haha");
	    System.out.println(mapper);
        this.mapper = mapper;
    }

    public TechSupportServiceImpl(){
	    System.out.println("service init");
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
		update2(sqlParam);
	}

	private void update2(String sqlParam){
	    mapper.update(sqlParam);
	}
	
	private int getAffectRows(String sqlParam) {
		
		 if(sqlParam.startsWith("insert")||sqlParam.startsWith("INSERT")) {
		        return 1;
		   }else {
			   
		    	return mapper.getAffectRows(getAffectRowsSql(sqlParam));
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


    @Override
    public void add(TechSupport obj) {
    }


    @Override
    public void delete(Integer id) {
        
    }


    @Override
    public void update(TechSupport obj) {
        
    }


    @Override
    public TechSupport get(Integer id) {
        return null;
    }


    @Override
    public List<TechSupport> list(TechSupportDto dto) {
        return null;
    }


    @Override
    public void loadPage(TechSupportDto dto) {
        
    }

}
