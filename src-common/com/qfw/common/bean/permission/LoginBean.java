package com.qfw.common.bean.permission;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import com.qfw.common.bizservice.permission.ILoginBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.bizservice.permission.impl.UserBSImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.SysUserVO;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.MessagesController;
import com.qfw.common.util.web.BaseBackingBean;
import com.qfw.common.util.web.ViewOper;

@ManagedBean(name="loginBean")
public class LoginBean extends BaseBackingBean{
	
	SysUser user = new SysUser();  //  用户登录
	private SysUser userRegister=new SysUser(); //用户注册
	SysUserVO sysUserVO = new SysUserVO(); 
	private String username;
	private String userpwd;
	private String imgCode;
	
	@ManagedProperty(value = "#{loginBS}")
	private ILoginBS loginBS;	
	@ManagedProperty(value="#{userBS}")
	private IUserBS userBS;
	@PostConstruct
	public void init(){
		if(loginBS.isLogin()){
			try {
				if("/login.jsf".equals(ViewOper.getRequest().getServletPath())){
					//FacesContext.getCurrentInstance().getExternalContext().redirect("index.jsf");
					FacesContext.getCurrentInstance().getExternalContext().dispatch("/index/main.jsf");
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 
			}
		}
	}
	public SysUser getLoginUser(){
		return (SysUser) ViewOper.getSession().getAttribute("user");
	}

	
	public String login() throws BizException {
		try {
			if (loginBS.login(user)) {
				//FacesContext.getCurrentInstance().getExternalContext().dispatch("/index/main.jsf");
				return "/index/main.jsf";
			}
		} catch (BizException e) {
			this.alert(e.getMessage());
		}catch (Exception e) {
			
		}
		return null;
	}
	public void checkImgCode(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		String rand = (String) ViewOper.getSession().getAttribute("rand");
		FacesMessage message = null;
		if(rand == null){
			message =  new FacesMessage(FacesMessage.SEVERITY_ERROR,"验证码已到期", "验证码已到期");
			throw new ValidatorException(message);
		}else{
			if(!rand.equalsIgnoreCase((String) value)){
				message =  new FacesMessage(FacesMessage.SEVERITY_ERROR,"验证码输入错误", "验证码输入错误");
				throw new ValidatorException(message);
			}
		}
	}
	
	public String loginOut(){
		ViewOper.getSession().removeAttribute("user");
		ViewOper.getSession().removeAttribute("depts");
		ViewOper.getSession().invalidate();
		/*try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(ViewOper.getRequest().getContextPath()+"/login.jsf");
		} catch (IOException e) {
			
		}
		return null;*/
		return "/login.jsf";
		
		/* Enumeration em = ViewOper.getSession().getAttributeNames();
		   while(em.hasMoreElements()){
			   String s = em.nextElement().toString();
			   //System.out.println(s);
			   ViewOper.getSession().removeAttribute(s);
		   }
*/
	}
	
	

	
	public String register() throws BizException{
		/*userRegister.setIsAdmin("0");
		loginBS.userRegister(userRegister);*/
		if(!userpwd.equals(userRegister.getPassword())){
			userpwd = "";
			userRegister.setPassword("");
			MessagesController.addInfo("密码跟确认密码不一致", "密码跟确认密码不一致");
			return "";
		}
		userRegister.setPassword(MD5Utils.getMD5Str(userRegister.getPassword()));
		userBS.save(userRegister);
		int count = userBS.findUserCount(String.valueOf(userRegister.getUserCode()));
		if(count>0){
			String sql = "DELETE FROM SYS_USER WHERE USER_ID = '"+userRegister.getUserId()+"'";
			userBS.getJdbcTemplate().execute(sql);//删除刚保存的用户。
           // FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_ERROR,"登录名"+String.valueOf(userRegister.getUserCode())+"已经存在！", "登录名"+String.valueOf(userRegister.getUserCode())+"已经存在！");
            MessagesController.addInfo("登录名"+String.valueOf(userRegister.getUserCode())+"已经存在！", "登录名"+String.valueOf(userRegister.getUserCode())+"已经存在！");
            return "";
		}
		((UserBSImpl)userBS).getJdbcTemplate().execute("INSERT INTO SYS_USER_DEPT(USER_ID,DEPT_ID) VALUES ("+userRegister.getUserId()+",'1')");
		//MessagesController.addInfo("用户"+userRegister.getUserName()+"注册成功！", "用户"+userRegister.getUserName()+"注册成功！");		
		userRegister.setPassword(userpwd);
		loginBS.login(userRegister);
		return "photoIndex";
	}
	public void checkUserCode(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		int count = userBS.findUserCount(String.valueOf(value));
		if(count>0){
            FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_ERROR,"登录名"+String.valueOf(value)+"已经存在！", "登录名"+String.valueOf(value)+"已经存在！");
			throw new ValidatorException(message);
		}
	}
	public void checkPassword(FacesContext context, UIComponent componentToValidate,Object value) throws ValidatorException {
		/*int count = userBS.findUserCount(String.valueOf(value));
		if(count>0){
            FacesMessage message =  new FacesMessage(FacesMessage.SEVERITY_ERROR,"登录名"+String.valueOf(value)+"已经存在！", "登录名"+String.valueOf(value)+"已经存在！");
			throw new ValidatorException(message);
		}*/
		//System.out.println(value);
		//System.out.println(userRegister.getPassword());
	}

	public IUserBS getUserBS() {
		return userBS;
	}

	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}
	public ILoginBS getLoginBS() {
		return loginBS;
	}
	public void setLoginBS(ILoginBS loginBS) {
		this.loginBS = loginBS;
	}	

	public SysUser getUserRegister() {
		return userRegister;
	}
	public void setUserRegister(SysUser userRegister) {
		this.userRegister = userRegister;
	}
	public String getImgCode() {
		return imgCode;
	}
	public void setImgCode(String imgCode) {
		this.imgCode = imgCode;
	}
	public SysUser getUser() {
		return user;
	}
	public void setUser(SysUser user) {
		this.user = user;
	}
	
	public boolean getIsLogin(){
		return getLoginUser() != null;
	}
	

}
