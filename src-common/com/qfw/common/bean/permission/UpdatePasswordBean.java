package com.qfw.common.bean.permission;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name="updatePasswordBean")
public class UpdatePasswordBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{userBS}")
	private IUserBS userBS;
	private SysUser selectUser;
	private SysUser user ;	
	private String oldPassWord;//旧密码
	private String newPassWord;//新密码
	private String newPassWord2;//确认密码
	
	@PostConstruct
    public void init(){
		Object userid = ViewOper.getSessionTmpAttr("userid");
		if (userid != null) {
			user = userBS.findUserById((Integer) userid);
		}
	} 
	
	/**
	 * 修改密码
	 */
	public String updatePassWord(){
		if(null == user){
			this.alert("用户信息获取失败!");
			return null;
		}
		SysUser userTemp =  userBS.findUserByUserId(user.getUserId());
		String oldPassWordMd5 = MD5Utils.getMD5Str(oldPassWord);
		if(oldPassWordMd5.equals(userTemp.getPassword())){
			if(StringUtils.isEmpty(newPassWord)){
				this.alert("新密码不能为空，请重新输入");
				return null;
			}
			if(StringUtils.isEmpty(newPassWord2)){
				this.alert("确认密码不能为空，请重新输入");
				return null;
			}
			if(!newPassWord.equals(newPassWord2)){
				this.alert("新密码与确认密码不一致，请重新输入");
				newPassWord2="";
				return null;
			}
			if(newPassWord.length() < 6 || newPassWord.length() > 16){
				this.alert("请重新输入新密码长度介于6~16之间的长度");
				newPassWord2="";
				return null;
			}
			String newPassWordMd5 = MD5Utils.getMD5Str(newPassWord);
			userTemp.setPassword(newPassWordMd5);
			userBS.update(userTemp);
			executeJS("alert('密码修改成功');closeParMainDialog();");
			return null;
		}else{
			if(StringUtils.isEmpty(oldPassWord)){
				this.alert("输入旧密码不能为空，请重新输入");
				return null;
			}else{
				this.alert("旧密码不正确，请重新输入");
				oldPassWord="";	
				return null;
			}
		}
		
	}
	
	public IUserBS getUserBS() {
		return userBS;
	}
	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}
	
	public SysUser getSelectUser() {
		return selectUser;
	}

	public void setSelectUser(SysUser selectUser) {
		this.selectUser = selectUser;
	}
	public String getNewPassWord() {
		return newPassWord;
	}
	public void setNewPassWord(String newPassWord) {
		this.newPassWord = newPassWord;
	}
	public String getNewPassWord2() {
		return newPassWord2;
	}
	public void setNewPassWord2(String newPassWord2) {
		this.newPassWord2 = newPassWord2;
	}
	public String getOldPassWord() {
		return oldPassWord;
	}
	public void setOldPassWord(String oldPassWord) {
		this.oldPassWord = oldPassWord;
	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	
}
