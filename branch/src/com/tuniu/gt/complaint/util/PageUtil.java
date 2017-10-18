package com.tuniu.gt.complaint.util;

public class PageUtil {
	
	public static final int PAGE_SIZE = 30;
	
	// 获取查询开始位置
	public static int getStartPosition(int pageNo) {
		return 0 + PAGE_SIZE * (pageNo - 1);
	}
	
	// 计算总页数
	public static int getTotalPages(int totalRecords) {
		return (totalRecords + PAGE_SIZE - 1) / PAGE_SIZE;
	}
	
	public static int processPageNo(int pageNo, int totalPages) {
		
		if (pageNo > totalPages) {
			pageNo = totalPages;
		}
		
		if (pageNo < 1) {
			pageNo = 1;
		}
		
		return pageNo;
	}

}
