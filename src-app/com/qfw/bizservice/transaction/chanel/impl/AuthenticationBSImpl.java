package com.qfw.bizservice.transaction.chanel.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.transaction.chanel.IAuthenticationBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.impl.MessageServiceBSImpl;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.DesAndBase64Utils;
import com.qfw.common.util.StringUtils;
import com.qfw.model.AppConst;
import com.qfw.model.template.RuleUtil;
import com.qfw.model.vo.transaction.CheckInfoVO;
import com.qfw.model.vo.transaction.CisReportsVO;
import com.qfw.model.vo.transaction.CreditDetailVO;
import com.qfw.model.vo.transaction.DataVO;
import com.qfw.model.vo.transaction.QueryCreditReqVO;
import com.qfw.model.vo.transaction.QueryCreditRespVO;
import com.qfw.xfire.WebServiceSingleQueryOfUnzip;
import com.qfw.xfire.WebServiceSingleQueryOfUnzipServiceClient;
import com.sun.org.apache.xml.internal.security.utils.Base64;
/**
 * 认证服务
 * @author Jie
 *
 */
@Service("authenticationBS")
public class AuthenticationBSImpl implements IAuthenticationBS {

	private Logger log = LogFactory.getInstance().getBusinessLogger();
	@Autowired
	@Qualifier("messageServiceBS")
	private MessageServiceBSImpl messageServiceBS;
	@Autowired
	@Qualifier("paramBS")
	private IParamBS paramBS;
	/**
	 * 个人认证接口
	 * @param name
	 * @param documentNo
	 * @return
	 * @throws BizException
	 */
	public CreditDetailVO personalAuth(String name, String documentNo) throws BizException {
		CreditDetailVO returnvo = new CreditDetailVO();
		QueryCreditReqVO creditReqVO = new QueryCreditReqVO();
		creditReqVO.setQueryType(paramBS.getParam("AUTH_QUERY_TYPE_P"));
		creditReqVO.setName(name);
		creditReqVO.setDocumentNo(documentNo);
		creditReqVO.setRefID(paramBS.getParam("AUTH_REF_ID_P"));
		creditReqVO.setSubreportIDs(paramBS.getParam("AUTH_SUBREPORT_IDS_P"));
		
		WebServiceSingleQueryOfUnzipServiceClient client = new WebServiceSingleQueryOfUnzipServiceClient();
		WebServiceSingleQueryOfUnzip zip = client.getWebServiceSingleQueryOfUnzip();

		String xml = messageServiceBS.generateXml(creditReqVO, "queryCreditReq.xml");
		log.debug("身份证认证请求报文："+xml);
		String resp = zip.queryReport(paramBS.getParam("AUTH_USER_ID_P"), paramBS.getParam("AUTH_PASSWORD_P"), xml, "xml");
		log.debug("身份证认证响应报文："+resp);
		if(StringUtils.isNotEmpty(resp)){
			QueryCreditRespVO qcrvo = (QueryCreditRespVO) RuleUtil.queryCreditRespVOStream.fromXML(resp);
			if(StringUtils.isNotEmpty(qcrvo.getReturnValue())){
				CisReportsVO vo = (CisReportsVO) RuleUtil.creditDetailVOStream.fromXML(qcrvo.getReturnValue());
				if(vo!=null){
					CreditDetailVO cd = vo.getCreditDetailVO();
					cd.setErrMsg(vo.getCisReport().getPoliceCheckInfo().getErrorMessage());
					return cd;
				}
			}
			
		}
		return returnvo;
	}
	public boolean personalValidator(String name, String documentNo) throws BizException {
		
		String param = name + "," + documentNo;
		String userName = paramBS.getParam("AUTH_USER_ID_P");// 用户名
		String password = paramBS.getParam("AUTH_PASSWORD_P");// 密码
//		String userName = "jssjtztest";// 用户名
//		String password = "jssjtz@20150714";// 密码
		String result = "";
		String encodeUserName = "", encodePassword = "", encodeParam = "", encodeDatasource = "";
		String datasource = paramBS.getParam("AUTH_DATA_TYPE");// 数据类型1A020202=身份证信息查询
//		String datasource = "1A020202";// 数据类型1A020202=身份证信息查询
		JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
		svr.setServiceClass(QueryValidatorServices.class);
		svr.setAddress(paramBS.getParam("AUTH_ADDRESS")); 
//		svr.setAddress("http://211.147.7.46/zcxy/services/QueryValidatorServices?wsdl"); 
		
		QueryValidatorServices service = (QueryValidatorServices) svr.create();
		
		try {
			encodeUserName = DesAndBase64Utils.encode(DesAndBase64Utils.ENCODE_KEY,userName.getBytes("UTF-8")).toString();
			encodePassword = DesAndBase64Utils.encode(DesAndBase64Utils.ENCODE_KEY,password.getBytes("UTF-8")).toString();
			encodeDatasource = DesAndBase64Utils.encode(DesAndBase64Utils.ENCODE_KEY,datasource.getBytes("UTF-8")).toString();
			encodeParam = DesAndBase64Utils.encode(DesAndBase64Utils.ENCODE_KEY,param.getBytes("GBK")).toString();
		
			result = service.querySingle(encodeUserName, encodePassword,encodeDatasource, encodeParam);
			
			if(StringUtils.isNotEmpty(result)){
				String resultXML = new String(DesAndBase64Utils.decode(DesAndBase64Utils.ENCODE_KEY,Base64.decode(result.getBytes())), "GBK");
				DataVO dataVO = (DataVO)RuleUtil.policeCheckInfoStream.fromXML(resultXML);
				if(AppConst.VALIDATOR_SUCCESS.equals(dataVO.getMessage().getStatus())){
					CheckInfoVO checkInfoVO = dataVO.getPoliceCheckInfos();
					if(AppConst.VALIDATOR_SUCCESS.equals(checkInfoVO.getPoliceCheckInfo().getMessage().getStatus())){
						if(AppConst.VALIDATOR_UNIFORMITY.equals(checkInfoVO.getPoliceCheckInfo().getCompStatus())){
							return true;
						}
					}
				}
			}
		} catch(BizException e){
			throw e;
		}catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("身份认证失败======="+e);
			throw new BizException("身份认证失败");
		}
		return false;
	}
}
