package com.tzh.entity;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
	@Override
	public String toString() {
		return "Page [pageSize=" + pageSize + ", page=" + page + ", pageSizeCount=" + pageSizeCount + ", pageCount="
				+ pageCount + ", pt=" + pt + "]";
	}
	private int pageSize = 10;//每页多少条记录数
	private int page = 1;//当前页
	private int pageSizeCount = 0;
	private int pageCount = 0;//总页数
	private List<T> pt = new ArrayList<>();
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSizeCount() {
        return pageSizeCount;
    }
    public void setPageSizeCount(int pageSizeCount) {
        this.pageSizeCount = pageSizeCount;
    }
    public int getPageCount() {
	    if(pageSizeCount % pageSize == 0){
	        return pageSizeCount / pageSize;
	    }else{
	        return pageSizeCount / pageSize + 1;
	    }
	}
    public List<T> getPt() {
        return pt;
    }
    public void setPt(List<T> pt) {
        this.pt = pt;
    }
}
