package com.qfw.common.gateway.sms;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.ISenderBS;
import com.qfw.common.gateway.SenderInfo;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.StringUtils;
import com.qfw.model.AppConst;

@Service("smsSenderBS")
public class SmsSenderBS implements ISenderBS {
	
	private static Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Autowired
	@Qualifier("paramBS")
	private IParamBS paramBS;
	@Override
	public void send(SenderInfo senderInfo) throws BizException {
		String to = senderInfo.getTo();
		if(to == null || to.isEmpty()){
			throw new BizException("接收方号码不能为空");
		}
		if(StringUtils.isEmpty(senderInfo.getContent())){
			throw new BizException("短信内容不能为空");
		}
		if(StringUtils.isEmpty(senderInfo.getMsService())){
			throw new BizException("短信类型不能为空");
		}
		try{
/*			String smsPlatform = paramBS.getParam("smsPlatform");
			if("1".equals(smsPlatform)){
				String smsComCode = paramBS.getParam("smsComCode");//公司编码
				String smsLoginName = paramBS.getParam("smsLoginName");//登录名
				String smsPswd = paramBS.getParam("smsPswd");//密码
				boolean isPlanSend = isPlanSend(senderInfo);
				if(to.split(",").length>1){//批量发送
					if(isPlanSend){//定时发送
						EsmServiceClient.BatchSendPlanMsg(smsComCode, smsLoginName, smsPswd, to, senderInfo.getContent(),DateUtils.getYmdhms(((SmsSenderInfo) senderInfo).getSendTime()));
					}else{
						EsmServiceClient.BatchSendMsg(smsComCode, smsLoginName, smsPswd, to, senderInfo.getContent());
					}
					
					
				}else{
					if(isPlanSend){//定时发送
						EsmServiceClient.SendPlanMsg(smsComCode, smsLoginName, smsPswd, to, senderInfo.getContent(),DateUtils.getYmdhms(((SmsSenderInfo) senderInfo).getSendTime()));
					}else{
						EsmServiceClient.SendMsg(smsComCode, smsLoginName, smsPswd, to, senderInfo.getContent());
						SendSMS.sendMsg(smsComCode, smsLoginName, smsPswd, to, senderInfo.getContent());
					}
				}
			}else{
				String smsUrl =  paramBS.getParam("smsUrl");//登录名
				String smsAccount = paramBS.getParam("smsAccount");//登录名
				String smsPassword = paramBS.getParam("smsPassword");//密码
				Map<String, String> param = new HashMap<String, String>();
				param.put("account", smsAccount);
				param.put("password", smsPassword);
				param.put("mobile", to);
				param.put("content",  senderInfo.getContent());
				String s = HttpEndPointBS.send(smsUrl, param, null);
				SmsResponseVO vo = (SmsResponseVO) RuleUtil.smsResponseVOStream.fromXML(s);
				if(vo == null){
					throw new BizException("短信平台返回信息为空");
				}else{
					if(!AppConst.SMS_STATUS_SUCCESS.equals(vo.getResult())){
						throw new BizException(vo.getMessage());
					}
				}
			}*/
			//add by yangjj 天元短信
			if(AppConst.MS_SERVICE_YUN.equals(senderInfo.getMsService())){
				//add by yangjj 20150714 for 云通讯短信
				String smsComCode = paramBS.getParam("smsComCode");
				String smsLoginName = paramBS.getParam("smsLoginName");
				String smsPswd = paramBS.getParam("smsPswd");
				String[] content = senderInfo.getContent().split("\\|");
				SendSMS.sendMsg(smsComCode, smsLoginName, smsPswd, to,content,senderInfo.getMsgCode());
			}else if(AppConst.MS_SERVICE_SUN.equals(senderInfo.getMsService())){
				MSClient.mdsmssend(to, senderInfo.getContent(), paramBS.getParam("SUN_MS_URL"), "", "", "");
			}
			
			
		}catch(Exception e){
			log.error("短信发送失败========================="+e.getMessage());
			throw new BizException("短信发送失败",e);
		}
		
	}
	/**
	 * 判断是否定时发送
	 * @param senderInfo
	 * @return
	 */
	private boolean isPlanSend(SenderInfo senderInfo){
		if(senderInfo instanceof SmsSenderInfo){
			Date sendTime = ((SmsSenderInfo) senderInfo).getSendTime();
			return sendTime != null && sendTime.compareTo(new Date()) > 0;
		}
		return false;
	}

}
