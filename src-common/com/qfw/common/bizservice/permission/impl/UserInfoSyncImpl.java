package com.qfw.common.bizservice.permission.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.permission.IUserInfoSyncBS;
import com.qfw.common.http.HttpClientUtil;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.StringUtils;
import com.qfw.platform.model.vo.UserInfo;
import com.sun.org.apache.xml.internal.security.utils.Base64;
@Service("userInfoSync")
public class UserInfoSyncImpl implements IUserInfoSyncBS{
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	public void userInfoSync(UserInfo userInfo){
		if(userInfo == null){
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("operate", userInfo.getOperate());
		params.put("loginName", userInfo.getLoginName());
		params.put("mobile", userInfo.getMobile());
		params.put("mail", userInfo.getMail());
		params.put("refereeName", userInfo.getRefereeName());
		params.put("idCard", userInfo.getIdCard());
		params.put("age", userInfo.getAge());
		params.put("birthday", userInfo.getBirthday());
		params.put("custName", userInfo.getCustName());
		params.put("cultural", userInfo.getCultural());
		params.put("telephone", userInfo.getTelephone());
		params.put("sex", userInfo.getSex());
		params.put("marital", userInfo.getMarital());
		params.put("haveChild", userInfo.getHaveChild());
		params.put("nativeAddr", userInfo.getNativeAddr());
		params.put("city", userInfo.getCity());
		if(StringUtils.isNotEmpty(userInfo.getPassword())){
//			userInfo.setPassword(Base64.encode(userInfo.getPassword().getBytes()));
			params.put("password", Base64.encode(userInfo.getPassword().getBytes()));
		}
		//TODO 用户数据同步
//		 try {
//			HttpClientUtil.UserInfoSync("http://123.57.204.46:8889/century/api/sys/user/synUser", params);
//		} catch (IOException e) {
//			log.error("用户数据同步失败：mobile"+userInfo.getMobile());
//		}
	}
}
