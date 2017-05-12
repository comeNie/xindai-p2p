package com.qfw.common.bizservice.permission.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultTreeNode;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.common.bizservice.permission.ILoginBS;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.permission.IUserDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysDept;
import com.qfw.common.model.permission.SysFunction;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.SysUserVO;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.StringUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.model.bo.BizCustomerBO;

public class LoginBSImpl implements ILoginBS {

	Logger logger = LogFactory.getInstance().getBusinessLogger();
	
	private ICommonQuery commonQuery;
	private IUserBS userBS;
	private ICustInfoBS custInfoBS;
	private String QUERY_USER_FUN ="select distinct fun.* from sys_function fun,sys_role rl,sys_role_function rf,sys_user u,sys_user_role ur where fun.fun_status = '1' and fun.fun_id = rf.fun_id and rl.role_id = rf.role_id and rl.role_id = ur.role_id and u.user_id = ur.user_id and u.user_id = ? order by fun.fun_level,fun.sort";
	
	SysUserVO sysUserVO = new SysUserVO(); 
	
	@SuppressWarnings("unused")
	@Override
	public boolean login(SysUser user) throws BizException{
		if(StringUtils.isEmpty(user.getPassword())){
			throw new BizException("密码不能为空");
		}
		SysUser sysUser = userBS.findUser(user.getUserCode());
		if(sysUser == null) {
			throw new BizException("用户名不存在");
		}

		if(!"1".equals(sysUser.getIsAdmin())){
			throw new BizException("非后台用户不能登录后台，请与管理员联系。");  
		}
		Date now = new Date();
		//在一天之内且登陆错误失败次数大于等于5次，不再用户登陆。
		if(DateUtils.addDay(sysUser.getSysUpdateTime(), 1).compareTo(now) == 1 && sysUser.getErrorCount() >= 5){
			user.setErrorCount(sysUser.getErrorCount());
			throw new BizException("您登录密码错误次数超过5次，请过24小时后重试","您登录密码错误次数超过5次，请过24小时后重试");  
		} else {
			if (MD5Utils.getMD5Str(user.getPassword())	.equals(sysUser.getPassword())) {
				sysUser.setErrorCount(0);
				sysUser.setSuccessCount(sysUser.getSuccessCount()+1);
				sysUser.setLastLoginDate(sysUser.getLoginTime());
				sysUser.setLoginTime(now);
				userBS.update(sysUser);
				sysUser.setPassanswer(null);
				HttpSession session = ViewOper.getSession();
				session.setAttribute("user", sysUser);
				//查找会员信息开始
				BizCustomerBO custBO = custInfoBS.findCustByUserId(sysUser.getUserId());
				if(custBO!=null){
					session.setAttribute("cust", custBO);
				}
				//查找会员信息结束
				String deptSql = "SELECT * FROM SYS_DEPT T WHERE EXISTS (SELECT 1 FROM SYS_USER_DEPT UD WHERE T.DEPT_ID = UD.DEPT_ID AND UD.USER_ID = '"+sysUser.getUserId()+"')";
				List<SysDept> depts = commonQuery.findObjects(deptSql, SysDept.class);
				session.setAttribute("depts", depts);
				LoginInfo.loginSession.put(session.getId(), session);
				//获取登录菜单开始
				session.setAttribute("userFuns", createTreeRoot());
				//获取登录菜单结束
				return true;
			} else {
				if(sysUser.getErrorCount() >= 5){
					sysUser.setErrorCount(1);//过了当天重新计数
				}else{
					sysUser.setErrorCount(sysUser.getErrorCount()+1);
				}
				user.setErrorCount(sysUser.getErrorCount());
				userBS.update(sysUser);
				throw new BizException("密码错误，您当天还有"+(5-sysUser.getErrorCount())+"次登陆机会");
			}
		}
	
	}
	
	public DefaultTreeNode createTreeRoot(){
		SysUser user = (SysUser)ViewOper.getSession().getAttribute("user");
    	List<SysFunction> funs = null;
    	SysFunction topMenu = null;
    	if(user != null){
    		//funs = userDAO.getFuns(user);
    		funs = commonQuery.findObjects(QUERY_USER_FUN,new String[]{user.getUserId().toString()}, SysFunction.class);
    		////System.out.println(lst.size());
		}
    	if(CollectionUtil.isNotEmpty(funs)){
    		DefaultTreeNode root = new DefaultTreeNode("root", null);
    		List<DefaultTreeNode> rootChildren = wrapTreeRootChildren(funs, root);
    		for (DefaultTreeNode rootChild : rootChildren) {
    			createTreeChildren(funs, rootChild);
			}
    		return root;
    	}
		return null;
	}
	
	/**
	 * 获取第一级菜单功能
	 * @param funs
	 * @param root
	 * @return
	 */
	private List<DefaultTreeNode> wrapTreeRootChildren(List<SysFunction> funs,DefaultTreeNode root){
    	List<SysFunction> funChildren = new ArrayList<SysFunction>();
    	List<DefaultTreeNode> treeChildren  = new ArrayList<DefaultTreeNode>();
    	DefaultTreeNode treeNode = null;
    	for (SysFunction sysFunction : funs) {
			if(sysFunction.getParentFunId() == null || sysFunction.getParentFunId()==0){
				funChildren.add(sysFunction);
				treeNode = new DefaultTreeNode(sysFunction, root);
				treeChildren.add(treeNode);
			}
		}
    	funs.removeAll(funChildren);
		return treeChildren;
    	
    }
	/**
     * 创建非第一级菜单功能
     * @param funs
     * @param treeNode
     */
    private void createTreeChildren(List<SysFunction> funs,DefaultTreeNode treeNode){
    	List<DefaultTreeNode> treeChildren = wrapTreeChildren(funs, treeNode);
    	if(CollectionUtil.isNotEmpty(treeChildren)){
    		for (DefaultTreeNode defaultTreeNode : treeChildren) {
    			createTreeChildren(funs, defaultTreeNode);
			}
    	}
    }
    
    /**
	 * 获取非第一级菜单功能
	 * @param funs
	 * @param parentNode
	 * @return
	 */
    private List<DefaultTreeNode> wrapTreeChildren(List<SysFunction> funs,DefaultTreeNode parentNode){
    	List<SysFunction> funChildren = new ArrayList<SysFunction>();
    	List<DefaultTreeNode> treeChildren  = new ArrayList<DefaultTreeNode>();
    	SysFunction parent = (SysFunction) parentNode.getData();
    	DefaultTreeNode treeNode = null;
    	for (SysFunction sysFunction : funs) {
			if(parent.getFunId().equals(sysFunction.getParentFunId())){
				funChildren.add(sysFunction);
				treeNode = new DefaultTreeNode(sysFunction, parentNode);
				treeChildren.add(treeNode);
			}
		}
    	funs.removeAll(funChildren);
		return treeChildren;
    	
    }
	public void loginOut(){
		LoginInfo.loginSession.remove(ViewOper.getSession().getId());
		SysUser user = (SysUser) ViewOper.getSession().getAttribute("user");
		ViewOper.getSession().invalidate();
		if(user!=null){
			userBS.updateUserOfLoginOutTime(user);
		}
	}
	
	private IUserDAO userDAO;

	public IUserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(IUserDAO userDAO) {
		this.userDAO = userDAO;
	}

	public ICommonQuery getCommonQuery() {
		return commonQuery;
	}

	public void setCommonQuery(ICommonQuery commonQuery) {
		this.commonQuery = commonQuery;
	}

	@Override
	public SysUser userRegister(SysUser user) {
		// TODO Auto-generated method stub
		return userDAO.registerUser(user);
	}

	@Override
	public boolean isLogin() {
		return ViewOper.getSession().getAttribute("user") != null;
	}
	
	public IUserBS getUserBS() {
		return userBS;
	}

	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}

	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}

	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

}
