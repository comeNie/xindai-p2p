package com.qfw.platform.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.payout.IRechargePayoutBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.GopayUtils;
import com.qfw.manager.service.IUserIndexService;
import com.qfw.model.AppConst;

@Controller
@RequestMapping(value = "/payment")
public class PaymentAction extends BaseAction  {

	@Resource(name = "rechargePayoutBS")
	private IRechargePayoutBS rechargePayoutBS;
	
	@Resource(name = "paramBS")
	private IParamBS paramBS;
	
	@Resource(name = "userIndexService")
	private IUserIndexService userIndexService;

	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@ResponseBody
	@RequestMapping(value = "/adviceBack")
	public String paymentBack(HttpServletRequest request){
		try {
			boolean paySuccess = false;
			String merOrderNum = "";
			String respCode = "";
			String returnCode = "";
			
			if(AppConst.PAY_SERVICE_TL.equals(paramBS.getParam("PAY_SERVICE"))){
				request.setCharacterEncoding("UTF-8");
				
				//接收Server返回的支付结果
				String merchantId=request.getParameter("merchantId");
				String version=request.getParameter("version");
				String language=request.getParameter("language");
				String signType=request.getParameter("signType");
				String payType=request.getParameter("payType");
				String issuerId=request.getParameter("issuerId");
				String paymentOrderId=request.getParameter("paymentOrderId");
				String orderNo=request.getParameter("orderNo");
				String orderDatetime=request.getParameter("orderDatetime");
				String orderAmount=request.getParameter("orderAmount");
				String payDatetime=request.getParameter("payDatetime");
				String payAmount=request.getParameter("payAmount");
				String ext1=request.getParameter("ext1");
				String ext2=request.getParameter("ext2");
				String payResult=request.getParameter("payResult");
				String errorCode=request.getParameter("errorCode");
				String returnDatetime=request.getParameter("returnDatetime");
				String signMsg=request.getParameter("signMsg");
				
				merOrderNum = orderNo;
			
				//验签是商户为了验证接收到的报文数据确实是支付网关发送的。
				//构造订单结果对象，验证签名。
				com.allinpay.ets.client.PaymentResult paymentResult = new com.allinpay.ets.client.PaymentResult();
				paymentResult.setMerchantId(merchantId);
				paymentResult.setVersion(version);
				paymentResult.setLanguage(language);
				paymentResult.setSignType(signType);
				paymentResult.setPayType(payType);
				paymentResult.setIssuerId(issuerId);
				paymentResult.setPaymentOrderId(paymentOrderId);
				paymentResult.setOrderNo(orderNo);
				paymentResult.setOrderDatetime(orderDatetime);
				paymentResult.setOrderAmount(orderAmount);
				paymentResult.setPayDatetime(payDatetime);
				paymentResult.setPayAmount(payAmount);
				paymentResult.setExt1(ext1);
				paymentResult.setExt2(ext2);
				paymentResult.setPayResult(payResult);
				paymentResult.setErrorCode(errorCode);
				paymentResult.setReturnDatetime(returnDatetime);
				//signMsg为服务器端返回的签名值。
				paymentResult.setSignMsg(signMsg); 
				//signType为"1"时，必须设置证书路径。
				if(AppConst.TL_SIGNTYPE.equals(paramBS.getParam("SIGN_TYPE"))){
					paymentResult.setCertPath(paramBS.getParam("CERT_PATH")); 
				}else{
					paymentResult.setKey(paramBS.getParam("PAY_MD5_KEY"));
				}
				
				
				//验证签名：返回true代表验签成功；否则验签失败。
				boolean verifyResult = paymentResult.verify();
				
				//验签成功，还需要判断订单状态，为"1"表示支付成功。
				paySuccess = verifyResult && payResult.equals("1");
//				paySuccess = payResult.equals("1");
				if(paySuccess){
					respCode = AppConst.PAY_SUCCESS;
					returnCode = "1";
				}

			}else if(AppConst.PAY_SERVICE_GFB.equals(paramBS.getParam("PAY_SERVICE"))){
				/*String md5key = paramBS.getParam("PAY_MD5_KEY");
				String BillNo = request.getParameter("BillNo");
				String Amount = request.getParameter("Amount");
				String Succeed = request.getParameter("Succeed");
				String SignMD5info = request.getParameter("SignMD5info");
				log.info(BillNo+"&"+Amount+"&"+Succeed+"&"+SignMD5info);
				if(SignMD5info == null || !SignMD5info.equals(MD5Utils.getMD5Str(BillNo+"&"+Amount+"&"+Succeed+"&"+md5key).toUpperCase())){
					log.error("订单"+BillNo+"数字签名错误");
					throw new BizException("数字签名错误");
				}*/
				//add by yangjj 国付宝支付
			   request.setCharacterEncoding("GBK");
			   String version = request.getParameter("version");
			  /* String charset = request.getParameter("charset");
			   String language = request.getParameter("language");
			   String signType = request.getParameter("signType");*/
			   String tranCode = request.getParameter("tranCode");
			   String merchantID = request.getParameter("merchantID");
			   merOrderNum = request.getParameter("merOrderNum");
			   String tranAmt = request.getParameter("tranAmt");
			   String feeAmt = request.getParameter("feeAmt");
			   String frontMerUrl = request.getParameter("frontMerUrl");
			   String backgroundMerUrl = request.getParameter("backgroundMerUrl");
			   String tranDateTime = request.getParameter("tranDateTime");
			   String tranIP = request.getParameter("tranIP");
			   respCode = request.getParameter("respCode");
//			   String msgExt = request.getParameter("msgExt");
			   String orderId = request.getParameter("orderId");
			   String gopayOutOrderId = request.getParameter("gopayOutOrderId");
			 /*  String bankCode = request.getParameter("bankCode");
			   String tranFinishTime = request.getParameter("tranFinishTime");
			   String merRemark1 =  request.getParameter("merRemark1");
			   String merRemark2 =  request.getParameter("merRemark2");*/
//			   String VerficationCode = ResourceBundle.getBundle("mpi").getString("VerficationCode");
			   String VerficationCode =  AppConst.PAYMENT_VERFICATIONCODE;
			   String signValueFromGopay = request.getParameter("signValue");
				
				String plain = "version=[" + version + "]"
						+ "tranCode=[" + tranCode + "]"
						+ "merchantID=[" + merchantID + "]"
						+ "merOrderNum=[" + merOrderNum + "]"
						+ "tranAmt=[" + tranAmt + "]"
						+ "feeAmt=[" + feeAmt+ "]"
						+ "tranDateTime=[" + tranDateTime + "]"
						+ "frontMerUrl=[" + frontMerUrl + "]"
						+ "backgroundMerUrl=[" + backgroundMerUrl + "]"
						+ "orderId=[" + orderId + "]"
						+ "gopayOutOrderId=[" + gopayOutOrderId + "]"
						+ "tranIP=[" + tranIP + "]"
						+ "respCode=[" + respCode + "]"
						+ "gopayServerTime=[]"
						+ "VerficationCode=[" + VerficationCode + "]";
				if(log.isDebugEnabled()){
					log.debug("返回明文串============"+plain);
				}
				String signValue = GopayUtils.md5(plain);
				
				if(signValue.equals(signValueFromGopay)){
					paySuccess = true;
				}else{
					log.error("订单"+merOrderNum+"数字签名错误");
					throw new BizException("数字签名错误");
				}
			}
			if(paySuccess){
				rechargePayoutBS.updatePaymentInfo(merOrderNum, respCode);
				returnCode = respCode;
				setAttribute(request, "sucMes", "充值成功");
			}
			
			return returnCode;
		} catch (Exception e) {
			return e.getMessage();
		}
	}
}
