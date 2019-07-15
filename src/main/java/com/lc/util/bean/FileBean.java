package com.lc.util.bean;

public class FileBean {

	/*
	 *  根据实测，file 只需要后面3个属性，开发环境 Struts2+Hibernate3+Spring3
	 *  在页面上，只需要用到 path 属性即可，会自动填充  pathContentType，pathFileName 的值，估计是页面表单或者框架处理的
	 */
	private String name;
	private String path;
	private String pathContentType;
	private String pathFileName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return String.format("FileBean [name=%s, path=%s, pathContentType=%s, pathFileName=%s]", name, path, pathContentType, pathFileName);
	}

	public FileBean(String name, String path, String pathContentType, String pathFileName) {
		super();
		this.name = name;
		this.path = path;
		this.pathContentType = pathContentType;
		this.pathFileName = pathFileName;
	}

	public String getPathContentType() {
		return pathContentType;
	}

	public void setPathContentType(String pathContentType) {
		this.pathContentType = pathContentType;
	}

	public String getPathFileName() {
		return pathFileName;
	}

	public void setPathFileName(String pathFileName) {
		this.pathFileName = pathFileName;
	}

	public FileBean() {
		super();
		// TODO Auto-generated constructor stub
	}

}
