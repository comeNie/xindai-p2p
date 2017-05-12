package com.qfw.bean.bussparams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.bo.BizProductBO;

@ViewScoped
@ManagedBean(name = "addProductInfoBean")
public class AddProductInfoBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{productInfoBS}")
	private IProductInfoBS productInfoBS;
	private String operateFlag;
	private BizProductBO product = new BizProductBO();
	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("id");
		if(id!=null){
			try{
				product = productInfoBS.getBizProductBOById((Integer)id);
			} catch (BizException e) {
				log.error("通过ID获取产品异常：", e);
			}
		}
	}
	
	public String operate(){
		SysUser user = (SysUser) ViewOper.getSession().getAttribute("user");
		if("add".equals(operateFlag)){
			if(checkProductNum(product.getProductNum(),null)){
				alert("产品编号已经存在，请重新输入！");
				product.setProductNum("");
				return null;
			}
			if(checkProductName(product.getProductName(),null)){
				alert("产品名称已经存在，请重新输入！");
				product.setProductName("");
				return null;
			}
			product.setSysCreateTime(new Date());
			product.setSysCreateUser(user.getUserId());
			product.setSysUpdateUser(user.getUserId());
			this.productInfoBS.save(product);
//			MessagesController.addInfo("产品"+product.getProductName()+"保存成功！", "产品"+product.getProductName()+"保存成功！");
			executeJS("alert('产品信息["+product.getProductName()+"]保存成功！');closeParMainDialog();");
		}else if("edit".equals(operateFlag)){
			if(checkProductNum(product.getProductNum(),product.getId())){
				alert("产品编号已经存在，请重新输入！");
				product.setProductNum("");
				return null;
			}
			if(checkProductName(product.getProductName(),product.getId())){
				alert("产品名称已经存在，请重新输入！");
				product.setProductName("");
				return null;
			}
			product.setSysUpdateUser(user.getUserId());
			this.productInfoBS.update(product);
//			MessagesController.addInfo("产品"+product.getProductName()+"修改成功！", "产品"+product.getProductName()+"修改成功！");
			executeJS("alert('产品信息["+product.getProductName()+"]修改成功！');closeParMainDialog();");
		}
		return null;
	}
	
	public boolean checkProductName(String productName,Integer id){
		List<BizProductBO> list = new ArrayList<BizProductBO>();
		try {
			list = productInfoBS.checkProductName(productName,id);
		} catch (BizException e) {
			log.error("验证产品名称异常：", e);
			alert("验证产品名称异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkProductNum(String productNum,Integer id){
		List<BizProductBO> list = new ArrayList<BizProductBO>();
		try {
			list = productInfoBS.checkProductNum(productNum,id);
		} catch (BizException e) {
			log.error("验证产品编号异常：", e);
			alert("验证产品编号异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	public IProductInfoBS getProductInfoBS() {
		return productInfoBS;
	}

	public void setProductInfoBS(IProductInfoBS productInfoBS) {
		this.productInfoBS = productInfoBS;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public BizProductBO getProduct() {
		return product;
	}

	public void setProduct(BizProductBO product) {
		this.product = product;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
