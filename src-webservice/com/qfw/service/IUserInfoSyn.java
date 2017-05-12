package com.qfw.service;


import javax.jws.WebService;

import com.qfw.model.UserInfo;
@WebService  
public interface IUserInfoSyn {
	    public String[] userInfoSyn(UserInfo userInfo); 
	    
}
