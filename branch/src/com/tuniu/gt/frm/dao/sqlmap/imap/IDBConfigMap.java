package com.tuniu.gt.frm.dao.sqlmap.imap;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.tuniu.config.db.model.DbConfig;

@Repository
public interface IDBConfigMap{ 
    
    List<DbConfig> fetchList(Map<String,Object> map);

   int insert(DbConfig dbConfig);

   DbConfig get(int id);

    int update(DbConfig dbConfig);

    int delete(int id);
}
