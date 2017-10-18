package com.tuniu.gt.toolkit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import tuniu.frm.service.Constant;

public class FreemarkerUtil {
	
	private static Logger logger = Logger.getLogger(FreemarkerUtil.class);
	
	/**
	 * 通过freemarker末班获取内容
	 * @param templateFileName 模板名称  放在src/ftl下
	 * @param vo ftl模板命令对象
	 * @return 经过编译后的内容
	 * @throws Exception
	 */
	public static String getContent(String templateFileName, Object vo) throws Exception{
		Template template = getTemplate(templateFileName);
		Map<String,Object> root = new HashMap<String, Object>();
		root.put("vo", vo);
		root.put("APP_URL", Constant.CONFIG.get("app_url"));
		String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
		return content;
	}
	
	private static Template getTemplate(String name) {  
        Template temp = null;  
        try {  
            // 通过Freemarker的Configuration读取相应的Ftl  
            Configuration cfg = new Configuration();  
            // 设定去哪里读取相应的ftl模板  
            cfg.setClassForTemplateLoading(FreemarkerUtil.class, "/ftl");  
            // 在模板文件目录中寻找名称为name的模板文件  
            temp = cfg.getTemplate(name);  
        } catch (IOException e) {  
            logger.error(e.getMessage(), e);  
        }  
        return temp;  
    }
}
