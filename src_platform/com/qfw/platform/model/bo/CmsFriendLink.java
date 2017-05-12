package com.qfw.platform.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.qfw.common.model.BaseBO;

/**
 * 实体类 - 友情链接
 * ============================================================================
 */

@Entity
@Table(name = "cms_friendlink")
public class CmsFriendLink extends BaseBO implements Serializable {

	private static final long serialVersionUID = 3019642557500517628L;

	private Integer id;// ID
	private String name;// 名称
	private String logo;// Logo
	private String url;// 链接地址
	private Integer sort;// 排序
	private String type;//类型 0:底部链接 1:广告轮询播放图片
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "logo")
	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	@Column(name = "url", nullable = false)
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	@Transient
	public String getRowKey() {
		return String.valueOf(id);
	}

	@Column(name = "type")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}