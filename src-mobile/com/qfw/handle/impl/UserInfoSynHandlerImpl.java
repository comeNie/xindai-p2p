package com.qfw.handle.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.credit.ICreditLimitApplyBS;
import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.bizservice.transaction.chanel.IAuthenticationBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.permission.SysUser;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.StringUtils;
import com.qfw.handle.IHandler;
import com.qfw.model.AppConst;
import com.qfw.model.HandlerResponse;
import com.qfw.model.UserInfoSynrResponse;
import com.qfw.model.bo.BizCustomerBO;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@Service("userInfoSyn")
public class UserInfoSynHandlerImpl extends BaseServiceImpl implements IHandler {
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Resource
	private IUserBS userBS;
	@Resource
	private ICreditLimitApplyBS creditLimitApplyBS;
	@Resource
	private ICustInfoBS custInfoBS;
	
	@Resource(name = "authenticationBS")
	private IAuthenticationBS authenticationBS;
	@Override
	public void doHandler(HttpServletRequest request,
			HttpServletResponse response, HandlerResponse handlerResponse)
			throws Exception {
		String operate = request.getParameter("operate");
		String loginName = request.getParameter("loginName");
		if(StringUtils.isNotEmpty(loginName)){
			loginName = new String(loginName.getBytes("UTF-8"));
		}
		String mobile = request.getParameter("mobile");
		String mail = request.getParameter("mail");
		String password = request.getParameter("password");
		String refereeName = request.getParameter("refereeName");
		if(StringUtils.isNotEmpty(refereeName)){
			refereeName = new String(refereeName.getBytes("UTF-8"));
		}
		String idCard = request.getParameter("idCard");
		String age = request.getParameter("age");
		String birthday = request.getParameter("birthday");
		String custName = request.getParameter("custName");
		if(StringUtils.isNotEmpty(custName)){
			custName = new String(custName.getBytes("UTF-8"));
		}
		String cultural = request.getParameter("cultural");
		String telephone = request.getParameter("telephone");
		String sex = request.getParameter("sex");
		String marital = request.getParameter("marital");
		String haveChild = request.getParameter("haveChild");
		
		if (StringUtils.isEmpty(operate)) { // 
			handlerResponse.setResponseCode(AppConst.USERINFO_SYN_FAIL);
			handlerResponse.setResponseMessage("操作类型为空");
			return;
		}
		if(AppConst.USERINFO_SYN_REGISTER.equals(operate)){//注册
			String pwd = new String(Base64.decode(password),"UTF-8");
			if(StringUtils.isEmpty(mobile) || StringUtils.isEmpty(pwd)
					|| StringUtils.isEmpty(loginName)){
				handlerResponse.setResponseCode(AppConst.USERINFO_SYN_FAIL);
				handlerResponse.setResponseMessage("必传字段为空");
				return;
			}
			userBS.saveRegisterUser(mobile, mail, loginName, pwd, refereeName);
		}else if(AppConst.USERINFO_SYN_MOD.equals(operate)){//修改基本信息
			if (StringUtils.isEmpty(mobile)) { // 
				handlerResponse.setResponseCode(AppConst.USERINFO_SYN_FAIL);
				handlerResponse.setResponseMessage("手机号为空");
				return;
			}
			
			List<BizCustomerBO> customer = custInfoBS.findCustomer(mobile);
			BizCustomerBO cust = customer.get(0);
			if(customer == null || customer.size() ==0){
				log.error("操作类型：修改基本信息，同步失败。获取不到客户信息");
				handlerResponse.setResponseCode(AppConst.USERINFO_SYN_FAIL);
				handlerResponse.setResponseMessage("同步失败");
				return;
			}
			if(!StringUtils.isEmpty(birthday)){
				cust.setBirthDate(DateUtils.getDateByFormat("yyyymmdd",birthday));
			}
			if(!StringUtils.isEmpty(age)){
				cust.setAge(Integer.valueOf(age));
			}
			if(StringUtils.isNotEmpty(idCard)){
				cust.setCertificateNum(idCard);//身份证号
				cust.setCertificateTypeCd(AppConst.CERTIFICATE_TYPE_CD_SF);
			}
			if(StringUtils.isNotEmpty(custName)){
				cust.setCustName(custName);
			}
			if(StringUtils.isNotEmpty(cultural)){
				//文化程度（学历）
				cust.setEducationCd(cultural);//code 
			}
			if(StringUtils.isNotEmpty(mail)){
				cust.setEmail(mail);
			}
			if(StringUtils.isNotEmpty(telephone)){
				cust.setTelephone(telephone);
			}
			if(StringUtils.isNotEmpty(sex)){
				cust.setSex(sex);
			}
			if(StringUtils.isNotEmpty(mobile)){
				cust.setMobileTelephone(mobile);
			}
			if(StringUtils.isNotEmpty(marital)){
				cust.setMaritalStatusCd(marital);
			}
//			cust.setSysUpdateUser("dateSyn");
//			cust.setCityCd(cityCd);
//			cust.setCountyCd(countyCd);
//			cust.setCreditRate(creditRate);
//			cust.setCustManagerId(custManagerId);
//			cust.setCustManagerName(custManagerName);
//			cust.setCustTypeCd(custTypeCd);
//			cust.setDeptId(deptId);
//			cust.setDeptName(deptName);
//			cust.setDomicile(domicile);
//			cust.setFax(fax);
//			cust.setId(id);
//			cust.setLiveCityCd(liveCityCd);
//			cust.setZipNum(zipNum);
//			cust.setWorkItemId(workItemId);
//			cust.setWebsite(website);
//			cust.setUserId(userId);
//			cust.setSysCreateTime(sysCreateTime);
//			cust.setSysCreateUser(sysCreateUser);
//			cust.setSysUpdateTime(sysUpdateTime);
//			cust.setStreetAddress(streetAddress);
//			cust.setScore(score);
//			cust.setRefereeName(refereeName);
//			cust.setQq(qq);
//			cust.setProvinceCd(provinceCd);
//			cust.setNationalityCd(nationalityCd);

//			cust.setLiveStreetAddress(liveStreetAddress);
//			cust.setLiveNationalityCd(liveNationalityCd);

			update(cust);
			updateUserByCust(cust);
		}else if(AppConst.USERINFO_SYN_AUTH.equals(operate)){//实名认证
			if(StringUtils.isEmpty(idCard) || StringUtils.isEmpty(custName)
					|| StringUtils.isEmpty(mobile)){
				handlerResponse.setResponseCode(AppConst.USERINFO_SYN_FAIL);
				handlerResponse.setResponseMessage("必传字段为空");
				return;
			}
			boolean success = authenticationBS.personalValidator(custName, idCard);
			if(success){
				userBS.updateIdCardByMobile(custName, idCard, mobile);
			}else{
				handlerResponse.setResponseCode(AppConst.USERINFO_SYN_FAIL);
				handlerResponse.setResponseMessage("实名认证不通过");
				return;
			}
			
		}else if(AppConst.USERINFO_SYN_CHG.equals(operate)){//修改密码
			if(StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)){
				handlerResponse.setResponseCode(AppConst.USERINFO_SYN_FAIL);
				handlerResponse.setResponseMessage("必传字段为空");
				return;
			}
			String pwd = new String(Base64.decode(password),"UTF-8");
			
			userBS.updatePasswordByMobile(MD5Utils.getMD5Str(pwd), mobile);
		}
		
		UserInfoSynrResponse userInfoSynrResponse = new UserInfoSynrResponse();
		userInfoSynrResponse.setResponseCode(AppConst.USERINFO_SYN_SUCESS);
		userInfoSynrResponse.setResponseMessage("同步成功！");

		handlerResponse.setResponseCode(userInfoSynrResponse.getResponseCode());
		handlerResponse.setResponseMessage(userInfoSynrResponse.getResponseMessage());
		handlerResponse.setResponseObj(userInfoSynrResponse);
	}
	/**
	 * 联动更新用户信息
	 * @param cust
	 */
	private void updateUserByCust(BizCustomerBO cust){
		SysUser user = this.userBS.findUserById(cust.getUserId());
//		user.setEmail(cust.getEmail());			//邮箱
		user.setUserName(cust.getCustName());	//用户名
		user.setTel(cust.getMobileTelephone()); //电话
		user.setSex(cust.getSex());				//性别
		user.setCardid(cust.getCertificateNum());//身份证
		user.setQq(cust.getQq());				// qq
		update(user);
	}
	public IUserBS getUserBS() {
		return userBS;
	}
	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}
	public ICreditLimitApplyBS getCreditLimitApplyBS() {
		return creditLimitApplyBS;
	}
	public void setCreditLimitApplyBS(ICreditLimitApplyBS creditLimitApplyBS) {
		this.creditLimitApplyBS = creditLimitApplyBS;
	}
	public ICustInfoBS getCustInfoBS() {
		return custInfoBS;
	}
	public void setCustInfoBS(ICustInfoBS custInfoBS) {
		this.custInfoBS = custInfoBS;
	}

}
