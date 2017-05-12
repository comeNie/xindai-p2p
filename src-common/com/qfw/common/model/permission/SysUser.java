package com.qfw.common.model.permission;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

import com.qfw.common.model.BaseBO;
import com.qfw.model.AppConst;

/**
 * SysUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_USER")
public class SysUser extends BaseBO implements java.io.Serializable {
	
	public static final String LOGIN_MEMBER_ID_SESSION_NAME = "loginUserId";// 保存登录会员ID的Session名称
	public static final String LOGIN_MEMBER_SESSION_CUST_ID = "custId";// 保存会员ID的Session名称
	public static final String LOGIN_MEMBER_USERNAME_COOKIE_NAME = "loginUsername";// 保存登录会员用户名的Cookie名称

	// Fields

	private Integer userId;
	private String userCode;
	private String userName;
	private String password;
	private String userReferee;
	private String tradePassword;
	private String isAdmin;
	private String sex;
	private Date birthDate;
	private String tel;
	private String status;
	private String cardid;
	private String email;
	//private Set<SysRole> sysRoles = new HashSet<SysRole>(0);
	//private Set<SysDept> sysDepts = new HashSet<SysDept>(0);
	private String qq;			//QQ
	private String msn;			//MSN
	private String introduction="0";  //推荐个数
	private String passquestion = AppConst.PROMOT_LEVEL0;	//【密码问题】改成推广等级
	private String passanswer = AppConst.PROMOT_PUTOUT_STATUS;//【密码答案】改成推广奖金是否发放
	private String isAllowFriend = "0";//是否允许加为好友。
	private String headPath = "myfile/noface.jpg";//头像路径
	private Date sysCreateTime = new Date();
	private Date sysUpdateTime;
	private String sysUpdateUser;
	private String remark;
	private Date loginTime;  // 登录时间
	private Date logoutTime;  // 退出时间
	private Integer errorCount = 0;  // 登录错误次数
	private Integer successCount = 0;//登录成功次数
	private Date lastLoginDate;//上次登录时间不跟数据库映射

	@Column(name="passquestion",length=32)
	public String getPassquestion() {
		return passquestion;
	}


	public void setPassquestion(String passquestion) {
		this.passquestion = passquestion;
	}

    @Column(name="passanswer",length=32)
	public String getPassanswer() {
		return passanswer;
	}


	public void setPassanswer(String passanswer) {
		this.passanswer = passanswer;
	}
	// Constructors

	@Column(name="QQ",length=32)
	public String getQq() {
		return qq;
	}


	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name="MSN",length=32)
	public String getMsn() {
		return msn;
	}


	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name="introduction",length=522)
	public String getIntroduction() {
		return introduction;
	}


	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}


	/** default constructor */
	public SysUser() {
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY,generator="user_seq")  
	//@SequenceGenerator(name="user_seq", sequenceName="seq_sys_user",allocationSize = 1,initialValue = 10000)
	@GenericGenerator(name = "user_seq", strategy = "identity")
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "USER_CODE", length = 32)
	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@Column(name = "USER_NAME", length = 32)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "PASSWORD", length = 128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "IS_ADMIN", length = 1)
	public String getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "SEX", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTH_DATE", length = 7)
	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "TEL", length = 11)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "STATUS", length = 1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CARDID", length = 20)
	public String getCardid() {
		return this.cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	@Column(name = "EMAIL", length = 32)
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	/*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_USER_ROLE",joinColumns = { @JoinColumn(name = "USER_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID", updatable = false) })
	public Set<SysRole> getSysRoles() {
		return this.sysRoles;
	}

	public void setSysRoles(Set<SysRole> sysRoles) {
		this.sysRoles = sysRoles;
	}*/

	/*@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "SYS_USER_DEPT", joinColumns = { @JoinColumn(name = "USER_ID", updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "DEPT_ID", updatable = false) })
	public Set<SysDept> getSysDepts() {
		return this.sysDepts;
	}

	public void setSysDepts(Set<SysDept> sysDepts) {
		this.sysDepts = sysDepts;
	}*/
	
	@Column(name = "USER_REFEREE", length = 32)
	public String getUserReferee() {
		return userReferee;
	}


	public void setUserReferee(String userReferee) {
		this.userReferee = userReferee;
	}

	@Column(name = "TRADE_PASSWORD", length = 128)
	public String getTradePassword() {
		return tradePassword;
	}


	public void setTradePassword(String tradePassword) {
		this.tradePassword = tradePassword;
	}


	@Column(name = "IS_ALLOW_FRIEND")
	public String getIsAllowFriend() {
		return isAllowFriend;
	}
	public void setIsAllowFriend(String isAllowFriend) {
		this.isAllowFriend = isAllowFriend;
	}	
	@Override
	@Transient
	public String getRowKey() {
		return String.valueOf(userId);
	}

	@Column(name = "HEAD_PATH", length = 255)
	public String getHeadPath() {
		return headPath;
	}


	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}	
	
    @Column(name="SYS_CREATE_TIME", nullable=false, updatable=false)   
    public Date getSysCreateTime() {
		return sysCreateTime;
	}

	public void setSysCreateTime(Date sysCreateTime) {
		this.sysCreateTime = sysCreateTime;
	}

    @Column(name="SYS_UPDATE_TIME")
//    @Version
	public Date getSysUpdateTime() {
		return sysUpdateTime;
	}
	public void setSysUpdateTime(Date sysUpdateTime) {
		this.sysUpdateTime = sysUpdateTime;
	}
	
	@Column(name="SYS_UPDATE_USER")   
	public String getSysUpdateUser() {
		return sysUpdateUser;
	}
	public void setSysUpdateUser(String sysUpdateUser) {
		this.sysUpdateUser = sysUpdateUser;
	}
	
	@Column(name="REMARK") 
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Column(name="LOGIN_TIME") 
	public Date getLoginTime() {
		return loginTime;
	}


	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}


	@Column(name="LOGOUT_TIME") 
	public Date getLogoutTime() {
		return logoutTime;
	}


	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}


	@Column(name="ERROR_COUNT") 
	public Integer getErrorCount() {
		return errorCount;
	}


	public void setErrorCount(Integer errorCount) {
		this.errorCount = errorCount;
	}

	@Column(name="SUCCESS_COUNT") 
	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}

	@Transient
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}
	
}