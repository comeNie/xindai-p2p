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
 * CMS-导航管理
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "cms_navigation")
public class CmsNavigation extends BaseBO implements Serializable {

	private static final long serialVersionUID = -8978833825777066844L;

	private Integer id;// ID
	private String name;// 导航名称
	private String url;// 导航URL
	private Integer sort;// 排序
	private Integer position;// 位置
	private Integer isblanktarget;// 是否新窗口打开
	private Integer isvisible;// 是否显示

	public CmsNavigation() {

	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Column(name = "position")
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}

	@Column(name = "isblanktarget")
	public Integer getIsblanktarget() {
		return isblanktarget;
	}

	public void setIsblanktarget(Integer isblanktarget) {
		this.isblanktarget = isblanktarget;
	}

	@Column(name = "isvisible")
	public Integer getIsvisible() {
		return isvisible;
	}

	public void setIsvisible(Integer isvisible) {
		this.isvisible = isvisible;
	}

	@Override
	@Transient
	public String getRowKey() {
		return String.valueOf(id);
	}

	public static final int NAV_POSITION_TOP = 0;
	public static final int NAV_POSITION_MIDDLE = 1;
	public static final int NAV_POSITION_BOTTOM = 2;
	
	@Transient
	public String getPositionStr() {
		switch (position) {
		case NAV_POSITION_TOP:
			return "顶部";

		case NAV_POSITION_MIDDLE:
			return "中间";

		case NAV_POSITION_BOTTOM:
			return "底部";
		default:
			return "顶部";
		}
	}
}