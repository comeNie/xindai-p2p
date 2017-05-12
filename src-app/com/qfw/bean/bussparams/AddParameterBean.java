package com.qfw.bean.bussparams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.bizservice.bussparams.IParameterInfoBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysParameter;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name = "addParameterBean")
public class AddParameterBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{parameterInfoBS}")
	private IParameterInfoBS parameterInfoBS;
	private String operateFlag;
	private SysParameter parameter = new SysParameter();
	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("id");
		if(id!=null){
			try{
				parameter = parameterInfoBS.getSysParameterById((Integer)id);
			} catch (BizException e) {
				log.error("通过ID获取参数异常：", e);
			}
		}
	}
	
	public String operate(){
		SysUser user = (SysUser) ViewOper.getSession().getAttribute("user");
		if("add".equals(operateFlag)){
			if(checkParameterCode(parameter.getParameterCode(),null)){
				alert("参数代码已经存在，请重新输入！");
				parameter.setParameterCode("");
				return null;
			}
//			if(checkParameterValue(parameter.getParameterValue(),null)){
//				alert("参数值已经存在，请重新输入！");
//				parameter.setParameterValue("");
//				return null;
//			}
			parameter.setSysCreateTime(new Date());
			parameter.setSysCreateUser(user.getUserId());
			parameter.setSysUpdateUser(user.getUserId());
			this.parameterInfoBS.save(parameter);
//			MessagesController.addInfo("参数"+parameter.getParameterCode()+"保存成功！", "参数"+parameter.getParameterCode()+"保存成功！");
			executeJS("alert('参数信息["+parameter.getParameterCode()+"]保存成功！');closeParMainDialog();");
		}else if("edit".equals(operateFlag)){
			if(checkParameterCode(parameter.getParameterCode(),parameter.getParameterId())){
				alert("参数代码已经存在，请重新输入！");
				parameter.setParameterCode("");
				return null;
			}
			/*if(checkParameterValue(parameter.getParameterValue(),parameter.getParameterId())){
				alert("参数值已经存在，请重新输入！");
				parameter.setParameterValue("");
				return null;
			}*/
			parameter.setSysUpdateUser(user.getUserId());
			this.parameterInfoBS.update(parameter);
//			MessagesController.addInfo("参数"+parameter.getParameterCode()+"修改成功！", "参数"+parameter.getParameterCode()+"修改成功！");
			executeJS("alert('参数信息["+parameter.getParameterCode()+"]修改成功！');closeParMainDialog();");
		}
		return null;
	}
	
	public boolean checkParameterCode(String parameterCode,Integer id){
		List<SysParameter> list = new ArrayList<SysParameter>();
		try {
			list = parameterInfoBS.checkParameterCode(parameterCode,id);
		} catch (BizException e) {
			log.error("验证参数代码异常：", e);
			alert("验证参数代码异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkParameterValue(String parameterValue,Integer id){
		List<SysParameter> list = new ArrayList<SysParameter>();
		try {
			list = parameterInfoBS.checkParameterValue(parameterValue,id);
		} catch (BizException e) {
			log.error("验证参数值异常：", e);
			alert("验证参数值异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	public IParameterInfoBS getParameterInfoBS() {
		return parameterInfoBS;
	}

	public void setParameterInfoBS(IParameterInfoBS parameterInfoBS) {
		this.parameterInfoBS = parameterInfoBS;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public SysParameter getParameter() {
		return parameter;
	}

	public void setParameter(SysParameter parameter) {
		this.parameter = parameter;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
