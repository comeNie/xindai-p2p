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
 * CMS-栏目管理
 * 
 * @author Administrator
 * 
 */
@Entity
@Table(name = "cms_catalog")
public class CmsCatalog extends BaseBO implements Serializable {

	private static final long serialVersionUID = -8978833825777066844L;

	private Integer id; // ID
	private String title;// 标题
	private String content;// 内容
	private Integer type;// 分类
	private Integer sort;// 排序
	private Integer ispublication;// 是否发布
	private String htmlfilepath;// 静态页面路径

	public CmsCatalog() {

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

	@Column(name = "title", nullable = false)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content", nullable = false)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "type")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Column(name = "isPublication")
	public Integer getIspublication() {
		return ispublication;
	}

	public void setIspublication(Integer ispublication) {
		this.ispublication = ispublication;
	}

	@Column(name = "htmlFilePath")
	public String getHtmlfilepath() {
		return htmlfilepath;
	}

	public void setHtmlfilepath(String htmlfilepath) {
		this.htmlfilepath = htmlfilepath;
	}

	@Override
	@Transient
	public String getRowKey() {
		return String.valueOf(id);
	}
}