package com.qfw.common.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sys_page_template database table.
 * 
 */
@Entity
@Table(name="sys_page_template")
public class SysPageTemplate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="COMP_ID")
	private String compId;

	private String page;

	private String remark;

	private String template;

	public SysPageTemplate() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompId() {
		return this.compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getPage() {
		return this.page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}