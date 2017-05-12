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
 * CMS-栏目分类
 * @author Administrator
 *
 */
@Entity
@Table(name = "cms_catalog_type")
public class CmsCatalogType extends BaseBO implements Serializable {

	private static final long serialVersionUID = -8978833825777066844L;

	private Integer id; // ID
	private String name;// 栏目分类名称
	private Integer sort;// 排序
	private String ftlfilepath;// 模板的地址

	public CmsCatalogType() {

	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
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

	@Column(name = "sort")
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	@Column(name = "ftlFilePath")
	public String getFtlfilepath() {
		return ftlfilepath;
	}

	public void setFtlfilepath(String ftlfilepath) {
		this.ftlfilepath = ftlfilepath;
	}
	
	@Override
	@Transient
	public String getRowKey() {
		return String.valueOf(id);
	}
}