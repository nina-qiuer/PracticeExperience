package com.tuniu.gt.toolkit.lang;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;

public class CollectionUtil extends CollectionUtils {
	
	private CollectionUtil(){
	}
	
	public static boolean isNotEmpty(Collection c) {
		return c!=null&&c.size()!=0;
	};
	
	public static boolean isEmpty(Collection c) {
		return c==null || c.isEmpty();
	}


}
