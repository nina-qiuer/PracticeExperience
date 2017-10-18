package com.tuniu.qms.common.init;

import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

public class Config {
	
	private static Properties props;
	
	public static void init() {
		try {
			Resource resource = new ClassPathResource("/config.properties");
			props = PropertiesLoaderUtils.loadProperties(resource);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		if (null == props) {
			init();
		}
		return props.getProperty(key);
	}

}
