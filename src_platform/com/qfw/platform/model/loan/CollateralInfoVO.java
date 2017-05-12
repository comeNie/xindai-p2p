package com.qfw.platform.model.loan;


/**
 * 借款人保证信息
 * @author Think
 *
 */
public class CollateralInfoVO {

	private Integer custId;
	private String collateralAtt1;// 图片1
	private String collateralAtt2;// 图片2
	private String collateralAtt3;// 图片3
	
	
	public Integer getCustId() {
		return custId;
	}
	public void setCustId(Integer custId) {
		this.custId = custId;
	}
	public String getCollateralAtt1() {
		return collateralAtt1;
	}
	public void setCollateralAtt1(String collateralAtt1) {
		this.collateralAtt1 = collateralAtt1;
	}
	public String getCollateralAtt2() {
		return collateralAtt2;
	}
	public void setCollateralAtt2(String collateralAtt2) {
		this.collateralAtt2 = collateralAtt2;
	}
	public String getCollateralAtt3() {
		return collateralAtt3;
	}
	public void setCollateralAtt3(String collateralAtt3) {
		this.collateralAtt3 = collateralAtt3;
	}

}