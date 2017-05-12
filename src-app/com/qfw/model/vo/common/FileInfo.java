package com.qfw.model.vo.common;

public class FileInfo {
	private String title;
	private String name;
	private String path;
	
	
	public FileInfo() {
		super();
	}
	public FileInfo(String title,String name, String path) {
		this.title = title;
		this.name = name;
		this.path = path;
	}
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
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
