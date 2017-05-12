package com.qfw.model.vo.bussparams;

import com.qfw.model.bo.BizProductBO;

public class ProductInfoVO extends BizProductBO{

	private static final long serialVersionUID = 1L;
	
	public ProductInfoVO(){}
	
	public ProductInfoVO(BizProductBO bo){
		this.setId(bo.getId());
		this.setLeastRateYear(bo.getLeastRateYear());
		this.setMostRateYear(bo.getMostRateYear());
		this.setProductDesc(bo.getProductDesc());
		this.setProductImgUrl(bo.getProductImgUrl());
		this.setProductName(bo.getProductName());
		this.setProductNum(bo.getProductNum());
		this.setSysCreateTime(bo.getSysCreateTime());
		this.setSysCreateUser(bo.getSysCreateUser());
		this.setSysUpdateTime(bo.getSysUpdateTime());
		this.setSysUpdateUser(bo.getSysUpdateUser());
	}
	
}
