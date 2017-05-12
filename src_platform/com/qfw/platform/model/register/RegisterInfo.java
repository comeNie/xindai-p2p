package com.qfw.platform.model.register;

import java.io.Serializable;

/**
 * 临时存放注册信息
 * 
 * @author Administrator
 * 
 */
public class RegisterInfo implements Serializable {

	private static final long serialVersionUID = 8012973772210312213L;
	
	private String tel;
	
	private String username;
	
	private String password;
	
	private String recommender;

	public RegisterInfo() {

	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRecommender() {
		return recommender;
	}

	public void setRecommender(String recommender) {
		this.recommender = recommender;
	}
}