package com.qfw.common.bizservice.permission;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.common.model.permission.SysRole;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.model.permission.vo.SysUserVO;
import com.qfw.platform.model.vo.LoginInfo;

public interface IUserBS extends IBaseService {	
	public List<SysUser> findUsersByVO(SysUserVO userVO);
	public List<SysUser> findUsersPagesByVO(SysUserVO userVO,int first,int pageSize);
	public int findUsersCountByVO(SysUserVO userVO);
	public SysUser findUserById(Integer id);
	public void merge(SysUser user);
	public int findUserCount(String userCode);
	public int findUserCountByTel(String tel);
	public int findUserCountByEmail(String email);
	public SysUser findUser(String userCode);
	public List<SysRole> getUserRoles(String userID);
	
	public SysUser findUserByTel(String tel);

	public void saveDept(SysUser user) throws BizException;
	

	public void saveUser(SysUser user);
	
	public void tranRegister(SysUser user);
	
	public void updateUserOfLoginOutTime(SysUser user);
	
	public SysUser findUserByUserId(Integer userId);
	/**
	 * 通过用户id删除用户
	 * @param userId
	 */
	public void deleteUserByUserId(Integer userId);
	
	/**
	 * 保存用户的角色信息
	 * @param userId
	 * @param roleId
	 * @throws BizException
	 */
	public void saveUserRole(Integer userId,Integer roleId)throws BizException;
	/**
	 * 保存用户的机构信息
	 * @param userId
	 * @param deptId
	 * @throws BizException
	 */
	public void saveUserDept(Integer userId,Integer deptId)throws BizException;
	
	/**
	 * 添加用户
	 * @param user
	 * @param deptIds
	 */
	public void addUser(SysUser user,String[] deptIds,String[] roleIds) throws BizException;
	
	/**
	 * 更新用户信息
	 * @param user
	 * @param deptIds
	 */
	public void updateUser(SysUser user,String[] deptIds,String[] roleIds) throws BizException;
	/**
	 * 保存注册用户信息 (前台使用)
	 * @param mobile
	 * @param email
	 * @param password
	 * @param refereeName
	 * @throws BizException
	 */
	public void saveRegisterUser(String mobile,String email,String custCode,String password,String refereeName) throws BizException;

	/**
	 * 保存用户信息的联动信息：客户表，账号表
	 * @param user
	 * @throws BizException
	 */
	public void saveUserLinkInfo(SysUser user) throws BizException;
	
	/**
	 * 查询身份证是否已经认证
	 * @param IdCard 
	 * @throws BizException
	 */
	public int getUserCountByIdCard(String IdCard) throws BizException;
	
	
	/**
	 * 
	 * @param name
	 * @param idCard
	 * @param loginInfo
	 * 
	 */
	public void updateUserOfIdCard(String name,String idCard,LoginInfo loginInfo) throws BizException;
	
	/**
	 * 
	 * @param status
	 * @param userCode
	 * @throws BizException
	 */
	public void updateUserOfStatus(String status, String userCode) throws BizException;
	
	/**
	 * 新增邮箱
	 * @param status
	 * @param userCode
	 * @throws BizException
	 */
	public void updateUserOfEmail(String email,String status ,String userCode) throws BizException;
	
	/**
	 * 
	 * @param Password
	 * @param userCode
	 * @throws BizException
	 */
	public void updateUserOfPassword(String Password, Integer userId) throws BizException;
	
	
	/**
	 * 更新
	 * @param phone
	 * @param userCode
	 * @throws BizException
	 */
	public void updateUserOfPhone(String Phone, Integer userId) throws BizException;
	
	
	/**
	 * 更新交易密码
	 * @param cashPasswrod
	 * @param userCode
	 * @throws BizException
	 */
	public void updateUserOfCashPassword(String cashPasswrod, Integer userId) throws BizException;
	
	
	/**
	 * 奖金发放状态置成已发放
	 * @param index 第几位
	 * @param userId 用户主键
	 * @throws BizException
	 */
	public void updateUserOfpassanswer(String passanswer,Integer userId) throws BizException;
	
	
	/**
	 * 更新推荐人等级
	 * @param index 第几位
	 * @param userId 用户主键
	 * @throws BizException
	 */
	public void updateUserOfPromotLevel(String promotLevel,Integer userId) throws BizException;
	
	
	/**
	 * 返回加密后的密码
	 * @param userCode
	 * @param password
	 * @throws BizException
	 */
	public String getMd5Password(String userCode,String password) throws BizException;
	
	
	/**
	 * 初始化所以密码
	 * @param userCode
	 * @param password
	 * @throws BizException
	 */
	public void updatePwdALL() throws BizException;
	
	/**
	 * 通过手机，登陆号或者邮箱
	 * @param userCode
	 * @param password
	 * @throws BizException
	 */
	public SysUser findUserByAll(String userCode);
	
	public int getIsAdminCount(String userCode);
	
	public void updateIdCardByMobile(String name,String idCard,String mobile)  throws BizException;
	
	public void updatePasswordByMobile(String password, String mobile)
			throws BizException;
	
	public List<SysUser> findExistUser(String cardId,String tel);
	
	public SysUser findUserByCustId(Integer custId);
	void updateUserOfIdCard(String name, String idCard, String birthDate,
			LoginInfo loginInfo) throws BizException;
}
