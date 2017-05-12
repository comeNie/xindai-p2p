package com.qfw.common.bean.permission;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.SysUserVO;
import com.qfw.common.model.permission.vo.UserDataModel;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ViewScoped
@ManagedBean(name="userManageBean")
public class UserManageBean extends BaseBackingBean{

	private static final long serialVersionUID = 1L;
	@ManagedProperty(value="#{userBS}")
	private IUserBS userBS;
	private SysUserVO userVO = new SysUserVO();//用于页面强求vo用
	private LazyDataModel<SysUser> lazyModel;
	private SysUser selectUser;
	private SysUser user;	
	@ManagedProperty(value = "#{paramBS}")
	private IParamBS paramBS;
	private String oldPassWord;//旧密码
	private String newPassWord;//新密码
	private String newPassWord2;//确认密码
	
	@PostConstruct
    public void init(){
		user = (SysUser)ViewOper.getSession().getAttribute("user");
		String isAdmin = ViewOper.getParameter("isAdmin");
		if(lazyModel==null){
			userVO.setIsAdmin(isAdmin);
			lazyModel = new UserDataModel(userVO, userBS);
		}
	} 
	public void search(){
		super.search();
		if(lazyModel==null){
			lazyModel = new UserDataModel(userVO, userBS);
		}
		
	}
	
	public void delete(){
		//System.out.println(getSelectUser());		
	}
	public void operate(){
		String operateFlag = ViewOper.getParameter("operateFlag");
		if("view".equalsIgnoreCase(operateFlag)||"edit".equals(operateFlag)){
			ViewOper.getSession().setAttribute("userid", selectUser.getUserId());
		}else if("delete".equalsIgnoreCase(operateFlag)){
			userBS.deleteUserByUserId(selectUser.getUserId());
			lazyModel.setRowCount(lazyModel.getRowCount()-1);			
			MessagesController.addInfo("用户"+selectUser.getUserCode()+"删除成功", "用户"+selectUser.getUserCode()+"删除成功");			
			selectUser = null;
			//search();
		}else if("modifyPassword".equals(operateFlag)){
			selectUser.setPassword(MD5Utils.getMD5Str(paramBS.getParam("defaultPassword")));
			userBS.update(selectUser);
			MessagesController.addInfo("用户"+selectUser.getUserName()+"密码还原成功", "用户"+selectUser.getUserName()+"密码还原成功");
		}else if("addRole".equals(operateFlag)){
			ViewOper.getSession().setAttribute("userid", selectUser.getUserId());
		}else if("updatePassword".equals(operateFlag)){
			ViewOper.getSession().setAttribute("userid", selectUser.getUserId());
		}
	}
	
	/**
	 * 修改密码
	 */
	public String updatePassWord(){
		selectUser = (SysUser)ViewOper.getSession().getAttribute("user");
		SysUser user =  userBS.findUserByUserId(selectUser.getUserId());
		String oldPassWordMd5 = MD5Utils.getMD5Str(oldPassWord);
		if(oldPassWordMd5.equals(user.getPassword())){
			if(StringUtils.isEmpty(newPassWord)){
				MessagesController.addInfo("新密码不能为空，请重新输入","新密码不能为空，请重新输入");
				return "";
			}
			if(StringUtils.isEmpty(newPassWord2)){
				MessagesController.addInfo("确认密码不能为空，请重新输入","确认密码不能为空，请重新输入");
				return "";
			}
			if(!newPassWord.equals(newPassWord2)){
				MessagesController.addInfo("新密码与确认密码不一致，请重新输入","新密码与确认密码不一致，请重新输入");
				newPassWord2="";
				return "";
			}
			if(newPassWord.length() < 6 || newPassWord.length() > 16){
				MessagesController.addInfo("请重新输入新密码长度介于6~16之间的长度","请重新输入新密码长度介于6~16之间的长度");
				newPassWord2="";
				return "";
			}
			String newPassWordMd5 = MD5Utils.getMD5Str(newPassWord);
			user.setPassword(newPassWordMd5);
			userBS.update(user);
			MessagesController.addInfo("密码修改成功","密码修改成功");
			return "";
		}else{
			if(StringUtils.isEmpty(oldPassWord)){
				MessagesController.addInfo("输入旧密码不能为空，请重新输入","输入旧密码不能为空，请重新输入");
				return "";
			}else{
				MessagesController.addInfo("旧密码不正确，请重新输入","旧密码不正确，请重新输入");
				oldPassWord="";	
				return "";
			}
		}
		
	}
	
	/**
	 * 重置密码 
	 * @return
	 */
	public String initPassword(){
		SysUser user =  userBS.findUserByUserId(selectUser.getUserId());
		String newPassWordMd5 = MD5Utils.getMD5Str(paramBS.getParam("defaultPassword"));
		user.setPassword(newPassWordMd5);
		userBS.update(user);
		this.alertInfo("密码重置成功");
		return null;
	}
	
	/**
	 * 修改用户资料
	 */
	public void updateUserInfo(){
		if(user != null){
			userBS.update(user);
//			MessagesController.addInfo("修改成功","修改成功");
			executeJS("alert('用户信息["+user.getUserName()+"]修改成功！');closeParMainDialog();");
			return;
		}
		MessagesController.addInfo("修改失败","修改失败");
	}
	public IUserBS getUserBS() {
		return userBS;
	}
	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}
	
	public SysUserVO getUserVO() {
		return userVO;
	}

	public void setUserVO(SysUserVO userVO) {
		this.userVO = userVO;
	}
	
	public SysUser getSelectUser() {
		return selectUser;
	}

	public void setSelectUser(SysUser selectUser) {
		this.selectUser = selectUser;
	}
	public LazyDataModel<SysUser> getLazyModel() {
		return lazyModel;
	}
	public void setLazyModel(LazyDataModel<SysUser> lazyModel) {
		this.lazyModel = lazyModel;
	}
	public IParamBS getParamBS() {
		return paramBS;
	}
	public void setParamBS(IParamBS paramBS) {
		this.paramBS = paramBS;
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
