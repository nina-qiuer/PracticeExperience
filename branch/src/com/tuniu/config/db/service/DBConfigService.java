package com.tuniu.config.db.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tuniu.config.db.model.DbConfig;
import com.tuniu.gt.frm.dao.sqlmap.imap.IDBConfigMap;
import com.tuniu.gt.toolkit.MemcachesUtil;

@Service
public class DBConfigService{
    private Logger logger = Logger.getLogger(getClass());
    
    private static final String MEM_PRE = "dbconfigCache_";
    
    @Autowired
    private IDBConfigMap mapper;

    public int delete(int id) {
        DbConfig entity = mapper.get(id);
        String dataId = entity.getDataId();
        logger.info("配置删除["+dataId+"]:原值："+entity.getContent());
        int delCount = mapper.delete(id);
        refreshCache(dataId);
        return delCount;
    }
    
    public void update(DbConfig dbConfig) {
        logger.info("配置更新["+dbConfig.getDataId()+"]:"+dbConfig.toString());
        mapper.update(dbConfig);
        refreshCache(dbConfig.getDataId());
    }
    
    public int insert(DbConfig dbConfig) {
        String dataId = dbConfig.getDataId();
        int id = mapper.insert(dbConfig);
        logger.info("配置新增["+dataId+"]:"+dbConfig.toString());
        refreshCache(dataId);
        return id;
    }
    
    public DbConfig get(int id){
        return mapper.get(id); 
    }
    
    public List<DbConfig> fetchList(Map<String,Object> param){
        return mapper.fetchList(param);
    }
    
    public DbConfig fetchOne(Map<String,Object> param){
        
        if(!CollectionUtils.isEmpty(mapper.fetchList(param))){
            return mapper.fetchList(param).get(0);
        }
        
        return null;
    }

    /**
     * @param dataId
     */
    private String refreshCache(String dataId) {
            String content = "";
        
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("dataId", dataId);
            DbConfig dbConfig = fetchOne(paramMap);
            if(dbConfig != null) {
                content = dbConfig.getContent();
                MemcachesUtil.set(MEM_PRE+dataId, content);
            }else{
                MemcachesUtil.delete(MEM_PRE+dataId);
            }
            
            logger.info("缓存刷新["+dataId+"]:"+dbConfig);
            
            return content;
    }

    public String getConfig(String key) {
        String content = MemcachesUtil.get(MEM_PRE+key);
        if(StringUtils.isEmpty(content)) {
            content = refreshCache(key);
        } 
        return content;
    }

}
