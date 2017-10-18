package com.tuniu.gt.warning.common;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 */
public class Page implements Serializable {

    private static final long serialVersionUID = -8123039538589235324L;

    /** 当前页 */
    protected int currentPage = 1;
    
    protected int currentPageCount;
    
    protected int begin;
    
    protected int end;
    
    public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	protected String url;

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/** 每页记录数，默认10条 */
    protected int pageSize = 10;

    /** 记录总数 */
    protected long count;

    /** 页数 */
    protected int pages;

    public int getCurrentPageCount() {
		return currentPageCount;
	}

	public void setCurrentPageCount(int currentPageCount) {
		this.currentPageCount = currentPageCount;
	}

	/** 当前页的索引 */
    protected int index;

    /**
     * 获取当前页
     * 
     * @return 当前页
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * 设置当前页
     * 
     * @param currentPage 当前页
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * 获取每页记录数
     * 
     * @return 每页记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     * 
     * @param pageSize 每页记录数
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取记录总数
     * 
     * @return 记录总数
     */
    public long getCount() {
        return count;
    }

    /**
     * 设置记录总数
     * 
     * @param count 记录总数
     */
    public void setCount(long count) {
        this.count = count;

        if (count > 0) {
            // 计算页数
            this.pages = (int) (this.count / this.pageSize);

            if (this.count % this.pageSize > 0) {
                this.pages++;
            }

            // 调整当前页
            if (this.currentPage > this.pages) {
                this.currentPage = this.pages;
            }

            // 计算当前页的索引
            this.index = (this.currentPage - 1) * this.pageSize;
        }
    }

    /**
     * 获取页数
     * 
     * @return 页数
     */
    public int getPages() {
        return pages;
    }

    /**
     * 获取当前页的索引
     * 
     * @return 当前页的索引
     */
    public int getIndex() {
        return index;
    }

    public PageBar getPageBar() {
        return new PageBar();
    }

    public class PageBar {

        private static final int BLOCK_LENGTH = 5;

        private List<Integer> clickBlocks;

        /**
         * 分页工具条的开始页码
         */
        private int start;

        /**
         * 分页工具条的结束页码
         */
        private int end;

        PageBar() {
            if (pages == 0) {
                clickBlocks = Collections.emptyList();
            }
            this.start = currentPage - BLOCK_LENGTH / 2 < 1 ? 1 : currentPage - BLOCK_LENGTH / 2;
            this.end = start + BLOCK_LENGTH - 1;
            // 调整结束页
            if (this.end > pages) {
                this.end = pages;
                this.start = end - BLOCK_LENGTH + 1;
            }
            // 调整开始页
            if (start < 1) {
                this.start = 1;
            }
            clickBlocks = new LinkedList<Integer>();
            for (int i = start; i <= end; i++) {
                clickBlocks.add(i);
            }
        }

        public List<Integer> getClickBlocks() {
            return clickBlocks;
        }

        public boolean hasPreviousBar() {
            if (pages > 0 && start > 1) {
                return true;
            }
            return false;
        }

        public boolean hasNextBar() {
            if (pages > 0 && end < pages) {
                return true;
            }
            return false;
        }
    }
}
