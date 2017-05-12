package com.qfw.bizservice.payout.card.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.payout.card.IRechargeCardBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.log.LogFactory;
import com.qfw.model.AppConst;

/**
 * 充值实现类
 *
 * @author kyc
 */
@Service("rechargeCardBS")
public class RechargeCardBSImpl extends BaseServiceImpl implements
		IRechargeCardBS {
	
	public static char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',  
		    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',  
		    'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; 
	
	public static Map<String, String> passMap = new HashMap<String, String>();

	@Autowired
	private ISeqBS seqBS;
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Override
	public String getPassword(int length) {
		String password = this.genRandomNum(length);
		if(passMap.containsKey(password)){
			password = this.getPassword(length);
		}else{
			passMap.put(password, password);
		}
		return password;
	}

	@Override
	public List<String> getCardNums(int count) {
		try {
			//String card =  seqBS.getResultNum(AppConst.CARDNUM);//获取充值卡卡号
			return seqBS.getBatchResultNum(AppConst.CARDNUM, count);
		} catch (Exception e) {
			log.warn("充值卡卡号获取失败", e);
		}
		return null;
	}
	
	public String getBatchCardNum() {
		try {
			String card =  seqBS.getResultNum(AppConst.BATCHCARDNUM);//获取充值卡卡号
			return card;
		} catch (Exception e) {
			log.warn("充值卡卡号获取失败", e);
		}
		return "0";
	}
	
	/** 
	  * 生成随即密码 
	  * @param pwd_len 生成的密码的总长度 
	  * @return  密码的字符串 
	  */  
	private String genRandomNum(int pwd_len){
		
		//35是因为数组是从0开始的，26个字母+10个数字  
		final int  maxNum = 36;  
		int i;  //生成的随机数  
		int count = 0; //生成的密码的长度  
		
		StringBuffer pwd = new StringBuffer("");  
		Random r = new Random();  
		while(count < pwd_len){  
				//生成随机数，取绝对值，防止生成负数，  
			i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1  
			if (i >= 0 && i < str.length) {  
				pwd.append(str[i]);  
				count ++;  
			}  
		}  
		return pwd.toString();  
	}

}
