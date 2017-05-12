package com.qfw.common.model.vo;

import java.io.Serializable;

import com.qfw.common.util.FileUtils;

public class ImageVO implements Serializable {
	private String smallImageUrl;//小图名称
	private String normalImageUrl;//正常大小图片
	private String originalImageUrl;//原图大小
	private String title;
	private String alt;
	
	
	public ImageVO(String originalImageName) {
		this.originalImageUrl = originalImageName;
		String ext = FileUtils.getExt(originalImageName);
		String prefixName = FileUtils.getPrefixName(originalImageName);
		this.smallImageUrl = prefixName+"_small."+ext;
		this.normalImageUrl = prefixName+"_normal."+ext;
	}
	
	public ImageVO(String smallImageName, String normalImageName,
			String originalImageName) {
		this.smallImageUrl = smallImageName;
		this.normalImageUrl = normalImageName;
		this.originalImageUrl = originalImageName;
	}

	public String getSmallImageUrl() {
		return smallImageUrl;
	}

	public void setSmallImageUrl(String smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	public String getNormalImageUrl() {
		return normalImageUrl;
	}

	public void setNormalImageUrl(String normalImageUrl) {
		this.normalImageUrl = normalImageUrl;
	}

	public String getOriginalImageUrl() {
		return originalImageUrl;
	}

	public void setOriginalImageUrl(String originalImageUrl) {
		this.originalImageUrl = originalImageUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAlt() {
		return alt;
	}

	public void setAlt(String alt) {
		this.alt = alt;
	}

	public ImageVO(){
		
	}
	

}
