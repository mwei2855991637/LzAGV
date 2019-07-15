package com.lc.util.bean;

import java.util.List;

/**
 * 辅助实现hibernate 分页效果
 * 
 * @author lenovo
 * @param <T>
 * 
 */
public class Page<T> {
	private int currPage; // 当前页码
	private int sizeInPage; // 每页多少条记录
	private int totalPage; // 总页数
	private int totalRecord; // 总记录数
	private List<T> result; // 返回的list数据

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getSizeInPage() {
		return sizeInPage;
	}

	public void setSizeInPage(int sizeInPage) {
		this.sizeInPage = sizeInPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<T> getResult() {
		return result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "Page [currPage=" + currPage + ", sizeInPage=" + sizeInPage + ", totalPage=" + totalPage
				+ ", totalRecord=" + totalRecord + "]";
	}

	public Page(int currPage, int sizeInPage, int totalPage, int totalRecord, List<T> result) {
		super();
		this.currPage = currPage;
		this.sizeInPage = sizeInPage;
		this.totalPage = totalPage;
		this.totalRecord = totalRecord;
		this.result = result;
	}

	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Page(int currPage, int sizeInPage) {
		super();
		this.currPage = currPage;
		this.sizeInPage = sizeInPage;
	}

}
