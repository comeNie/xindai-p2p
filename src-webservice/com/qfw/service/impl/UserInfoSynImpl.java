package com.qfw.service.impl;

import javax.annotation.Resource;
import javax.jws.WebService;

import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.util.DesAndBase64Utils;
import com.qfw.model.UserInfo;
import com.qfw.service.IUserInfoSyn;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@WebService(endpointInterface="com.qfw.service.IUserInfoSyn",serviceName="userInfoSynService") 
public class UserInfoSynImpl implements IUserInfoSyn {
	@Resource(name = "userBS")
	private IUserBS userBS;
	
	public String[] userInfoSyn(UserInfo userInfo) {
		String[] result = new String[2];
		result[0] = "1";
		result[1] = "成功";
		if(userInfo == null || userInfo.getOperate() == null
				|| "".equals(userInfo.getOperate())){
			result[1] = "获取不到有效信息[operate]";
			return result;
		}
		if("1".equals(userInfo.getOperate())){//注册
			try {
				//加密
//				String pwd = DesAndBase64Utils.encode(DesAndBase64Utils.ENCODE_KEY,userInfo.getPassword().getBytes("UTF-8")).toString();
				//解密
				String pwd = new String(DesAndBase64Utils.decode(DesAndBase64Utils.ENCODE_KEY,Base64.decode(userInfo.getPassword().getBytes())), "UTF-8");
				userBS.saveRegisterUser(userInfo.getMobile(), userInfo.getMail(), userInfo.getLoginName(),
						pwd, null);
			} catch (Exception e) {
				result[0] = "0";
				result[1] = "同步过程中发生异常";
			}
			
		}else if("2".equals(userInfo.getOperate())){
			
		}else if("3".equals(userInfo.getOperate())){
			
		}else if("4".equals(userInfo.getOperate())){
			
		}
		return result;
	}

	public IUserBS getUserBS() {
		return userBS;
	}

	public void setUserBS(IUserBS userBS) {
		this.userBS = userBS;
	}
	  
}
