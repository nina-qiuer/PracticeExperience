package com.tuniu.qms.common.util;


import java.io.Serializable;
import java.util.List;



public class Page implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 总的记录条数
     */
    private int totalRecords;
    /**
     * 当前页
     */
    private int currPage; 
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 记录列表
     */
    private List<?> records;
    
    

    public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public List<?> getRecords()
    {
        return records;
    }

    public void setRecords(List<?> records)
    {
        this.records = records;
    }
    
}
