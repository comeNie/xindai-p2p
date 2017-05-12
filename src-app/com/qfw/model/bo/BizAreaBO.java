package com.qfw.model.bo;

import static javax.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BizCost entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "xx_area")
public class BizAreaBO implements java.io.Serializable {

	// Fields

	private Long id;
	private Long parent;
	private Integer orders;
	private Integer status;
	private String fullName;
	private String name;
	private String treePath;
	private String code;
	private String ziCode; 	
	private String telCode;
	private BigDecimal locationX;
	private BigDecimal locationY;
	private Date createDate;
	private Date modifyDate;
	

	// Constructors

	/** default constructor */
	public BizAreaBO() {
	}


	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "PARENT")
	public Long getParent() {
		return parent;
	}


	public void setParent(Long parent) {
		this.parent = parent;
	}

	@Column(name = "ORDERS")
	public Integer getOrders() {
		return orders;
	}


	public void setOrders(Integer orders) {
		this.orders = orders;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}



	@Column(name = "CODE")
	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}



	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "MODIFY_DATE")
	public Date getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Column(name = "FULL_NAME")
	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "TREE_PATH")
	public String getTreePath() {
		return treePath;
	}


	public void setTreePath(String treePath) {
		this.treePath = treePath;
	}

	@Column(name = "ZIP_CODE")
	public String getZiCode() {
		return ziCode;
	}


	public void setZiCode(String ziCode) {
		this.ziCode = ziCode;
	}

	@Column(name = "TEL_CODE")
	public String getTelCode() {
		return telCode;
	}


	public void setTelCode(String telCode) {
		this.telCode = telCode;
	}

	@Column(name = "LOCATION_X")
	public BigDecimal getLocationX() {
		return locationX;
	}


	public void setLocationX(BigDecimal locationX) {
		this.locationX = locationX;
	}

	@Column(name = "LOCATION_Y")
	public BigDecimal getLocationY() {
		return locationY;
	}


	public void setLocationY(BigDecimal locationY) {
		this.locationY = locationY;
	}

}