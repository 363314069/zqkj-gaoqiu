package com.zqkj.utils;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * 
 */
public class PageUtil<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//总记录数
	private long count;
	//当前页数
	private int page;
	//每页记录数
	private int limit = 10;
	//总页数
	private int countPage;
	//列表数据
	private List<T> list;
	//排序
	private String orderBy;
	public PageUtil(){};
	/**
	 * 分页
	 * @param list        列表数据
	 * @param total  总记录数
	 * @param pageSize    每页记录数
	 * @param pageNum    当前页数
	 */
	public PageUtil(List<T> list, long count, int limit, int page) {
		this.list = list;
		this.count = count;
		this.limit = limit;
		this.page = page;
		this.countPage = (int)Math.ceil((double)count/limit);
	}
	public PageUtil(long count, int limit, int page) {
		this.count = count;
		this.limit = limit;
		this.page = page;
		this.countPage = (int)Math.ceil((long)count/limit);
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public int getCountPage() {
		return countPage;
	}
	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
}
