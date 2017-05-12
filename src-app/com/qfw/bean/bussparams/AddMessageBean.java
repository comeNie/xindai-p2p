package com.qfw.bean.bussparams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import com.qfw.bizservice.bussparams.IMessageInfoBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysMessageTemplate;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name = "addMessageBean")
public class AddMessageBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value = "#{messageInfoBS}")
	private IMessageInfoBS messageInfoBS;
	private String operateFlag;
	private SysMessageTemplate message = new SysMessageTemplate();
	private Logger log = LogFactory.getInstance().getPlatformLogger();

	@PostConstruct
	public void init() {
		operateFlag = ViewOper.getRequestParameter("operateFlag");
		Object id = ViewOper.getSessionTmpAttr("id");
		if(id!=null){
			try{
				message = messageInfoBS.getSysMessageTemplateById((Integer)id);
			} catch (BizException e) {
				log.error("通过ID获取短信配置异常：", e);
			}
		}
	}
	
	public String operate(){
		SysUser user = (SysUser) ViewOper.getSession().getAttribute("user");
		if("add".equals(operateFlag)){
			if(checkMessageCode(message.getMsgTmpCode(),null)){
				alert("模板代码已经存在，请重新输入！");
				message.setMsgTmpCode("");
				return null;
			}
			if(checkMessageName(message.getMsgTmpName(),null)){
				alert("模板名称已经存在，请重新输入！");
				message.setMsgTmpName("");
				return null;
			}
			message.setSysCreateTime(new Date());
			message.setSysCreateUser(user.getUserId());
			message.setSysUpdateUser(user.getUserId());
			this.messageInfoBS.save(message);
//			MessagesController.addInfo("短信模板"+message.getMsgTmpName()+"保存成功！", "短信模板"+message.getMsgTmpName()+"保存成功！");
			executeJS("alert('短信信息["+message.getMsgTmpName()+"]保存成功！');closeParMainDialog();");
		}else if("edit".equals(operateFlag)){
			if(checkMessageCode(message.getMsgTmpCode(),message.getId())){
				alert("模板代码已经存在，请重新输入！");
				message.setMsgTmpCode("");
				return null;
			}
			if(checkMessageName(message.getMsgTmpName(),message.getId())){
				alert("模板名称已经存在，请重新输入！");
				message.setMsgTmpName("");
				return null;
			}
			message.setSysUpdateUser(user.getUserId());
			this.messageInfoBS.update(message);
//			MessagesController.addInfo("短信模板"+message.getMsgTmpName()+"修改成功！", "短信模板"+message.getMsgTmpName()+"修改成功！");
			executeJS("alert('短信信息["+message.getMsgTmpName()+"]修改成功！');closeParMainDialog();");
		}
		return null;
	}
	
	public boolean checkMessageName(String MsgTmpName,Integer id){
		List<SysMessageTemplate> list = new ArrayList<SysMessageTemplate>();
		try {
			list = messageInfoBS.checkMessageName(MsgTmpName,id);
		} catch (BizException e) {
			log.error("验证模板名称异常：", e);
			alert("验证模板名称异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean checkMessageCode(String MsgTmpCode,Integer id){
		List<SysMessageTemplate> list = new ArrayList<SysMessageTemplate>();
		try {
			list = messageInfoBS.checkMessageCode(MsgTmpCode,id);
		} catch (BizException e) {
			log.error("验证模板代码异常：", e);
			alert("验证模板代码异常："+e);
		}
		if(list != null && list.size() > 0){
			return true;
		}else{
			return false;
		}
	}

	public IMessageInfoBS getMessageInfoBS() {
		return messageInfoBS;
	}

	public void setMessageInfoBS(IMessageInfoBS messageInfoBS) {
		this.messageInfoBS = messageInfoBS;
	}

	public String getOperateFlag() {
		return operateFlag;
	}

	public void setOperateFlag(String operateFlag) {
		this.operateFlag = operateFlag;
	}

	public SysMessageTemplate getMessage() {
		return message;
	}

	public void setMessage(SysMessageTemplate message) {
		this.message = message;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

}
