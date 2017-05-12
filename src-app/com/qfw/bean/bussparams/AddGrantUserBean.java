package com.qfw.bean.bussparams;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.bizservice.bussparams.IGrantUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;
import com.qfw.jbpm.model.bo.Jbpm4GrantUser;

@ViewScoped
@ManagedBean(name = "addGrantUserBean")
public class AddGrantUserBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{grantUserBS}")
	private IGrantUserBS grantUserBS;
	private String operateFlag;
	private Jbpm4GrantUser grant = new Jbpm4GrantUser();
	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("id");
		if(id!=null){
			try{
				grant = grantUserBS.getJbpm4GrantUserById((Integer)id);
			} catch (BizException e) {
				log.error("通过ID获取授权信息异常：", e);
			}
		}
	}
	
	public String operate(){
		SysUser user = (SysUser) ViewOper.getSession().getAttribute("user");
		if("add".equals(operateFlag)){
//			if(checkProductNum(product.getProductNum(),null)){
//				alert("产品编号已经存在，请重新输入！");
//				product.setProductNum("");
//				return null;
//			}
//			if(checkProductName(product.getProductName(),null)){
//				alert("产品名称已经存在，请重新输入！");
//				product.setProductName("");
//				return null;
//			}
//			grant.setGrantUserId(grant.getGrantUserId());
//			grant.setGrantedUserId(grant.getGrantedUserId());
//			grant.setFlowKey(grant.getFlowKey());
//			grant.setStartDate(grant.getStartDate());
//			grant.setEndDate(grant.getEndDate());
			grant.setSysCreateTime(new Timestamp(new Date().getTime()));
			grant.setSysCreateUser(user.getUserId());
			grant.setSysUpdateUser(user.getUserId());
			this.grantUserBS.save(grant);
//			MessagesController.addInfo("授权信息保存成功！", "授权信息保存成功！");
			executeJS("alert('授权信息保存成功！');closeParMainDialog();");
		}else if("edit".equals(operateFlag)){
//			if(checkProductNum(product.getProductNum(),product.getId())){
//				alert("产品编号已经存在，请重新输入！");
//				product.setProductNum("");
//				return null;
//			}
//			if(checkProductName(product.getProductName(),product.getId())){
//				alert("产品名称已经存在，请重新输入！");
//				product.setProductName("");
//				return null;
//			}
			grant.setSysUpdateUser(user.getUserId());
			this.grantUserBS.update(grant);
//			MessagesController.addInfo("授权信息修改成功！", "授权信息修改成功！");
			executeJS("alert('授权信息修改成功！');closeParMainDialog();");
		}
		return null;
	}

	public IGrantUserBS getGrantUserBS() {
		return grantUserBS;
	}

	public void setGrantUserBS(IGrantUserBS grantUserBS) {
		this.grantUserBS = grantUserBS;
	}

	public Jbpm4GrantUser getGrant() {
		return grant;
	}

	public void setGrant(Jbpm4GrantUser grant) {
		this.grant = grant;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}
	
//	public boolean checkProductName(String productName,Integer id){
//		List<BizProductBO> list = new ArrayList<BizProductBO>();
//		try {
//			list = productInfoBS.checkProductName(productName,id);
//		} catch (BizException e) {
//			log.error("验证产品名称异常：", e);
//			alert("验证产品名称异常："+e);
//		}
//		if(list != null && list.size() > 0){
//			return true;
//		}else{
//			return false;
//		}
//	}
	
//	public boolean checkProductNum(String productNum,Integer id){
//		List<BizProductBO> list = new ArrayList<BizProductBO>();
//		try {
//			list = productInfoBS.checkProductNum(productNum,id);
//		} catch (BizException e) {
//			log.error("验证产品编号异常：", e);
//			alert("验证产品编号异常："+e);
//		}
//		if(list != null && list.size() > 0){
//			return true;
//		}else{
//			return false;
//		}
//	}

}
