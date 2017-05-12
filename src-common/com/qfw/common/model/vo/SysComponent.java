package com.qfw.common.model.vo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sys_component database table.
 * 
 */
@Entity
@Table(name="sys_component")
public class SysComponent implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="COMP_ID")
	private String compId;
	
	@Column(name="COMP_PARENT_ID")
	private String compParentId;

	@Column(name="COMP_TPYE")
	private String compTpye;

	@Column(name="FUN_ID")
	private int funId;

	@Column(name="IS_VALID")
	private String isValid;

	@Column(name="PAGE_TEMPLATE")
	private String pageTemplate;

	private int position;

	@Column(name="SPLIT_FLAG")
	private String splitFlag;

	@Column(name="VALUE_EXPRESSION")
	private String valueExpression;

	@Column(name="METHOD_EXPRESSION")
	private String methodExpression;
	
	public SysComponent() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}

	public String getCompParentId() {
		return this.compParentId;
	}

	public void setCompParentId(String compParentId) {
		this.compParentId = compParentId;
	}

	public String getCompTpye() {
		return this.compTpye;
	}

	public void setCompTpye(String compTpye) {
		this.compTpye = compTpye;
	}

	public int getFunId() {
		return this.funId;
	}

	public void setFunId(int funId) {
		this.funId = funId;
	}

	public String getIsValid() {
		return this.isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getPageTemplate() {
		return this.pageTemplate;
	}

	public void setPageTemplate(String pageTemplate) {
		this.pageTemplate = pageTemplate;
	}

	public int getPosition() {
		return this.position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getSplitFlag() {
		return this.splitFlag;
	}

	public void setSplitFlag(String splitFlag) {
		this.splitFlag = splitFlag;
	}

	public String getValueExpression() {
		return this.valueExpression;
	}

	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}

	public String getMethodExpression() {
		return methodExpression;
	}

	public void setMethodExpression(String methodExpression) {
		this.methodExpression = methodExpression;
	}
	

}