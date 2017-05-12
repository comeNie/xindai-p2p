package com.qfw.bean.bussparams;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysCodeDict;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name = "addCodeDictBean")
public class AddCodeDictBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{codeDictBS}")
	private ICodeDictBS codeDictBS;
	private String operateFlag;
	private SysCodeDict codeDict = new SysCodeDict();
	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("id");
		if(id!=null){
			try{
				codeDict = codeDictBS.getSysCodeDictById((Integer)id);
			} catch (BizException e) {
				log.error("通过ID获取字典参数异常：", e);
			}
		}
	}
	
	public String operate(){
		SysUser user = (SysUser) ViewOper.getSession().getAttribute("user");
		if("add".equals(operateFlag)){
			codeDict.setSysCreateTime(new Date());
			codeDict.setSysUpdateTime(new Date());
			codeDict.setSysUpdateUser(user.getUserId());
			this.codeDictBS.save(codeDict);
			//MessagesController.addInfo("字典参数"+codeDict.getCodeTypeName()+"保存成功！", "字典参数"+codeDict.getCodeTypeName()+"保存成功！");
			executeJS("alert('字典参数["+codeDict.getCodeTypeName()+"]保存成功！');closeParMainDialog();");
		}else if("edit".equals(operateFlag)){
			codeDict.setSysUpdateUser(user.getUserId());
			this.codeDictBS.update(codeDict);
			//MessagesController.addInfo("字典参数"+codeDict.getCodeTypeName()+"修改成功！", "字典参数"+codeDict.getCodeTypeName()+"修改成功！");
			executeJS("alert('字典参数["+codeDict.getCodeTypeName()+"]修改成功！');closeParMainDialog();");
		}
		return null;
	}

	public ICodeDictBS getCodeDictBS() {
		return codeDictBS;
	}

	public void setCodeDictBS(ICodeDictBS codeDictBS) {
		this.codeDictBS = codeDictBS;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public SysCodeDict getCodeDict() {
		return codeDict;
	}

	public void setCodeDict(SysCodeDict codeDict) {
		this.codeDict = codeDict;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
