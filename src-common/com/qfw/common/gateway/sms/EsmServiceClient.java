package com.qfw.common.gateway.sms;
import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class EsmServiceClient {
	private static final String NAMESPACE = "http://tempuri.org/";
	private static final String URL = "http://esm.chaotang.com/webservice/esmsendservice.asmx?WSDL";

	// private String SOAP_ACTION;

	/**
	 * @author 批量发送带扩展的方法 九维科技
	 * @param comCode
	 *            公司编码
	 * @param loginName
	 *            登录名
	 * @param pswd
	 *            登录密码
	 * @param mobs
	 *            号码列表
	 * @param msg
	 *            消息内容
	 * @param extension
	 *            扩展码
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static void  BatchExtSendMsg(String comCode, String loginName,
			String pswd, String mobs, String msg, String extension)
			throws IOException, XmlPullParserException {
		SoapObject rpc = new SoapObject(NAMESPACE, "javaBatchExtSendMsg");
		rpc.addProperty("comCode", comCode);
		rpc.addProperty("loginName", loginName);
		rpc.addProperty("pswd", pswd);
		rpc.addProperty("mobs", mobs);
		rpc.addProperty("msg", msg);
		rpc.addProperty("extension", extension);
		String soapAction = "http://tempuri.org/javaBatchExtSendMsg";
		opration(rpc,soapAction);
	}

	/**
	 * @author 批量发送定时带扩展的方法 九维科技
	 * @param comCode
	 *            公司编码
	 * @param loginName
	 *            登录名
	 * @param pswd
	 *            登录密码
	 * @param mobs
	 *            号码列表
	 * @param msg
	 *            消息内容
	 * @param dt
	 *            发送时间格式yyyy-MM-dd HH:mm:ss
	 * @param extension
	 *            扩展码
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static void  BatchExtSendPlanMsg(String comCode, String loginName,
			String pswd, String mobs, String msg, String dt, String extension)
			throws IOException, XmlPullParserException {
		SoapObject rpc = new SoapObject(NAMESPACE, "javaBatchExtSendPlanMsg");
		rpc.addProperty("comCode", comCode);
		rpc.addProperty("loginName", loginName);
		rpc.addProperty("pswd", pswd);
		rpc.addProperty("mobs", mobs);
		rpc.addProperty("msg", msg);
		rpc.addProperty("dt", dt);
		rpc.addProperty("extension", extension);
		String soapAction = "http://tempuri.org/javaBatchExtSendPlanMsg";
		opration(rpc,soapAction);
	}

	/**
	 * @author 批量发送的方法 九维科技
	 * @param comCode
	 *            公司编码
	 * @param loginName
	 *            登录名
	 * @param pswd
	 *            登录密码
	 * @param mobs
	 *            号码列表
	 * @param msg
	 *            消息内容
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static void  BatchSendMsg(String comCode, String loginName, String pswd,
			String mobs, String msg) throws IOException, XmlPullParserException {
		SoapObject rpc = new SoapObject(NAMESPACE, "javaBatchSendMsg");
		rpc.addProperty("comCode", comCode);
		rpc.addProperty("loginName", loginName);
		rpc.addProperty("pswd", pswd);
		rpc.addProperty("mobs", mobs);
		rpc.addProperty("msg", msg);
		String soapAction = "http://tempuri.org/javaBatchSendMsg";
		opration(rpc,soapAction);
	}

	/**
	 * @author 批量发送定时的方法 九维科技
	 * @param comCode
	 *            公司编码
	 * @param loginName
	 *            登录名
	 * @param pswd
	 *            登录密码
	 * @param mobs
	 *            号码列表
	 * @param dt
	 *            发送时间格式yyyy-MM-dd HH:mm:ss
	 * @param msg
	 *            消息内容
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static void  BatchSendPlanMsg(String comCode, String loginName,
			String pswd, String mobs, String msg, String dt)
			throws IOException, XmlPullParserException {
		SoapObject rpc = new SoapObject(NAMESPACE, "javaBatchSendPlanMsg");
		rpc.addProperty("comCode", comCode);
		rpc.addProperty("loginName", loginName);
		rpc.addProperty("pswd", pswd);
		rpc.addProperty("mobs", mobs);
		rpc.addProperty("msg", msg);
		rpc.addProperty("dt", dt);
		String soapAction = "http://tempuri.org/javaBatchSendPlanMsg";
		opration(rpc,soapAction);
	}

	/**
	 * @author 取得余额的方法 九维科技
	 * @param comCode
	 *            公司编码
	 * @param loginName
	 *            登录名
	 * @param pswd
	 *            登录密码
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static int GetCompanyESMCount(String comCode, String loginName, String pswd)
			throws IOException, XmlPullParserException {
		SoapObject rpc = new SoapObject(NAMESPACE, "GetCompanyESMCount");
		rpc.addProperty("comCode", comCode);
		rpc.addProperty("loginName", loginName);
		rpc.addProperty("pswd", pswd);
		String soapAction = "http://tempuri.org/GetCompanyESMCount";
		return Integer.parseInt(opration(rpc,soapAction).toString());
	}

	/**
	 * @author 取得上行短信方法 九维科技
	 * @param comCode
	 *            公司编码
	 * @param loginName
	 *            登录名
	 * @param pswd
	 *            登录密码
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static void GetReply(String comCode, String loginName, String pswd)
			throws IOException, XmlPullParserException {
		SoapObject rpc = new SoapObject(NAMESPACE, "GetReply");
		rpc.addProperty("comCode", comCode);
		rpc.addProperty("loginName", loginName);
		rpc.addProperty("pswd", pswd);
		String soapAction = "http://tempuri.org/GetReply";
		opration(rpc,soapAction);
	}

	/**
	 * @author 发送短信的方法 九维科技
	 * @param comCode
	 *            公司编码
	 * @param loginName
	 *            登录名
	 * @param pswd
	 *            登录密码
	 * @param mob
	 *            号码
	 * @param msg
	 *            消息内容
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static void SendMsg(String comCode, String loginName, String pswd,
			String mob, String msg) throws IOException, XmlPullParserException {
		SoapObject rpc = new SoapObject(NAMESPACE, "SendMsg");
		rpc.addProperty("comCode", comCode);
		rpc.addProperty("loginName", loginName);
		rpc.addProperty("pswd", pswd);
		rpc.addProperty("mob", mob);
		rpc.addProperty("msg", msg);
		String soapAction = "http://tempuri.org/SendMsg";
		opration(rpc,soapAction);
	}

	/**
	 * @author 发送定时短信的方法 九维科技
	 * @param comCode
	 *            公司编码
	 * @param loginName
	 *            登录名
	 * @param pswd
	 *            登录密码
	 * @param mob
	 *            号码
	 * @param msg
	 *            消息内容
	 * @param dt
	 *            发送时间格式yyyy-MM-dd HH:mm:ss
	 * @throws XmlPullParserException
	 * @throws IOException
	 */
	public static void SendPlanMsg(String comCode, String loginName, String pswd,
			String mob, String msg, String dt) throws IOException,
			XmlPullParserException {
		SoapObject rpc = new SoapObject(NAMESPACE, "SendPlanMsg");
		rpc.addProperty("comCode", comCode);
		rpc.addProperty("loginName", loginName);
		rpc.addProperty("pswd", pswd);
		rpc.addProperty("mob", mob);
		rpc.addProperty("msg", msg);
		rpc.addProperty("dt", dt);
		String soapAction = "http://tempuri.org/SendPlanMsg";
		opration(rpc,soapAction);
	}

	private static Object opration(SoapObject _Rpc,String soapAction) throws IOException,
			XmlPullParserException {
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = _Rpc;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(_Rpc);

		HttpTransportSE ht = new HttpTransportSE(URL);
		ht.debug = true;
		ht.call(soapAction, envelope);
		SoapObject result = (SoapObject) envelope.bodyIn;
		return (Object) result;
	}

}
