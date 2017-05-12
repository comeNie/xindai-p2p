package com.qfw.model.template;

import com.qfw.model.vo.transaction.CheckInfoDetailVO;
import com.qfw.model.vo.transaction.CheckInfoVO;
import com.qfw.model.vo.transaction.CisReportsVO;
import com.qfw.model.vo.transaction.CisReportsVO.CisReportVO;
import com.qfw.model.vo.transaction.CisReportsVO.CisReportVO.PoliceCheckInfoVO;
import com.qfw.model.vo.transaction.CisReportsVO.CisReportVO.QueryConditionsVO;
import com.qfw.model.vo.transaction.CisReportsVO.CisReportVO.QueryConditionsVO.QueryCreditReqRtnVO;
import com.qfw.model.vo.transaction.CreditDetailVO;
import com.qfw.model.vo.transaction.DataVO;
import com.qfw.model.vo.transaction.OrderDetailVO;
import com.qfw.model.vo.transaction.OrderVO;
import com.qfw.model.vo.transaction.QueryCreditRespVO;
import com.qfw.model.vo.transaction.ResultMessageVO;
import com.qfw.model.vo.transaction.SmsResponseVO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XppDriver;

public class RuleUtil {

	public static XStream orderVOStream = new XStream(new DomDriver());
	public static XStream queryCreditRespVOStream = new XStream(new DomDriver());
	public static XStream creditDetailVOStream = new XStream(new DomDriver()); 
	public static XStream smsResponseVOStream = new XStream(new DomDriver());
	public static XStream policeCheckInfoStream = new XStream(new DomDriver());
	
	static{
		//订单信息查询列表开始
		orderVOStream.alias("root", OrderVO.class);
		orderVOStream.aliasField("merCode", OrderVO.class, "merCode");
		orderVOStream.aliasField("beginDate", OrderVO.class, "beginDate");
		orderVOStream.aliasField("endDate", OrderVO.class, "endDate");
		orderVOStream.aliasField("resultCount", OrderVO.class, "resultCount");
		orderVOStream.aliasField("pageIndex", OrderVO.class, "pageIndex");
		orderVOStream.aliasField("pageSize", OrderVO.class, "pageSize");
		orderVOStream.aliasField("resultCode", OrderVO.class, "resultCode");
		orderVOStream.aliasField("lists",OrderVO.class, "orderDetails");
		orderVOStream.alias("list", OrderDetailVO.class);
		orderVOStream.aliasField("orderNumber", OrderDetailVO.class, "orderNumber");
		orderVOStream.aliasField("orderDate", OrderDetailVO.class, "orderDate");
		orderVOStream.aliasField("orderAmount", OrderDetailVO.class, "orderAmount");
		orderVOStream.aliasField("orderStatus", OrderDetailVO.class, "orderStatus");
		orderVOStream.aliasField("gouduiStatus", OrderDetailVO.class, "gouduiStatus");
		orderVOStream.aliasField("refundStatus", OrderDetailVO.class, "refundStatus");
		//订单信息查询列表结束
		
		//身份证认证信息开始
		queryCreditRespVOStream.alias("result", QueryCreditRespVO.class);
		queryCreditRespVOStream.aliasField("status", QueryCreditRespVO.class, "status");
		queryCreditRespVOStream.aliasField("returnValue", QueryCreditRespVO.class, "returnValue");
		
		//身份证认证详细信息开始
		creditDetailVOStream.alias("cisReports", CisReportsVO.class);
		creditDetailVOStream.aliasField("cisReport", CisReportsVO.class, "cisReport");
		creditDetailVOStream.alias("cisReport", CisReportVO.class);
		creditDetailVOStream.addImplicitCollection(QueryConditionsVO.class,"creditReqRtnVOs","item",QueryCreditReqRtnVO.class);
		creditDetailVOStream.aliasField("policeCheckInfo", CisReportVO.class, "policeCheckInfo");
		
		creditDetailVOStream.alias("policeCheckInfo", PoliceCheckInfoVO.class);
		creditDetailVOStream.aliasAttribute(PoliceCheckInfoVO.class, "subReportType", "subReportType");
		creditDetailVOStream.aliasAttribute(PoliceCheckInfoVO.class, "subReportTypeCost", "subReportTypeCost");
		creditDetailVOStream.aliasAttribute(PoliceCheckInfoVO.class, "treatResult", "treatResult");
		creditDetailVOStream.aliasAttribute(PoliceCheckInfoVO.class, "errorMessage", "errorMessage");
		
		creditDetailVOStream.aliasField("item", PoliceCheckInfoVO.class, "creditDetailVO");
		//身份证认证详细信息结束
		
		smsResponseVOStream.alias("response", SmsResponseVO.class);
		smsResponseVOStream.aliasField("result", SmsResponseVO.class, "result");
		smsResponseVOStream.aliasField("message", SmsResponseVO.class, "message");
		
		//add by yangjj for 中诚信源  实名认证  
		policeCheckInfoStream.alias("data", DataVO.class);
		policeCheckInfoStream.aliasField("message", DataVO.class, "message");
		policeCheckInfoStream.aliasField("policeCheckInfos", DataVO.class, "policeCheckInfos");
		
		policeCheckInfoStream.alias("message", ResultMessageVO.class);
		policeCheckInfoStream.aliasField("status", ResultMessageVO.class, "status");
		policeCheckInfoStream.aliasField("value", ResultMessageVO.class, "value");
		policeCheckInfoStream.aliasField("querySeq", ResultMessageVO.class, "querySeq");
		
		policeCheckInfoStream.alias("policeCheckInfos", CheckInfoVO.class);
		policeCheckInfoStream.aliasField("policeCheckInfo", CheckInfoVO.class, "policeCheckInfo");
		policeCheckInfoStream.aliasAttribute(CheckInfoVO.class, "name", "name");
		policeCheckInfoStream.aliasAttribute(CheckInfoVO.class, "id", "id");
		
		policeCheckInfoStream.alias("policeCheckInfo", CheckInfoDetailVO.class);
		policeCheckInfoStream.aliasField("message", CheckInfoDetailVO.class, "message");
		policeCheckInfoStream.aliasField("name", CheckInfoDetailVO.class, "name");
		policeCheckInfoStream.aliasField("identitycard", CheckInfoDetailVO.class, "identitycard");
		policeCheckInfoStream.aliasField("compStatus", CheckInfoDetailVO.class, "compStatus");
		policeCheckInfoStream.aliasField("compResult", CheckInfoDetailVO.class, "compResult");
		policeCheckInfoStream.aliasField("region", CheckInfoDetailVO.class, "region");
		policeCheckInfoStream.aliasField("birthday", CheckInfoDetailVO.class, "birthday");
		policeCheckInfoStream.aliasField("sex", CheckInfoDetailVO.class, "sex");
		policeCheckInfoStream.aliasField("no", CheckInfoDetailVO.class, "no");
		
		policeCheckInfoStream.alias("message", ResultMessageVO.class);
		policeCheckInfoStream.aliasField("status", ResultMessageVO.class, "status");
		policeCheckInfoStream.aliasField("value", ResultMessageVO.class, "value");
		
	}
	
	public static void main(String[] args) {
		String data ="<?xml version=\"1.0\" encoding=\"gbk\"  ?>"
				+"\n<cisReports  batNo=\"2014051113000001\" unitName=\"厦门平台投资管理有限公司\" subOrgan=\"技术开发部\" queryUserID=\"pmwsquery\" queryCount=\"1\" receiveTime=\"20140511 13:53:30\"> "
				+"\n<cisReport reportID=\"2014051113000001\" buildEndTime=\"2014-05-11 13:53:32\" queryReasonID=\"99\" subReportTypes=\"10602\" treatResult=\"0\" subReportTypesShortCaption=\"1、身份认证 \" refID=\"1\" hasSystemError=\"false\" isFrozen=\"false\">"
				+"\n	<queryConditions>                                                                                                                                                                                                                               "
				+"\n		<item>                                                                                                                                                                                                                                        "
				+"\n			<name>name</name>                                                                                                                                                                                                                           "
				+"\n			<caption>被查询者姓名</caption>                                                                                                                                                                                                             "
				+"\n			<value>张三</value>                                                                                                                                                                                                                         "
				+"\n		</item>                                                                                                                                                                                                                                       "
				+"\n		<item>                                                                                                                                                                                                                                        "
				+"\n			<name>documentNo</name>                                                                                                                                                                                                                     "
				+"\n			<caption>被查询者证件号码</caption>                                                                                                                                                                                                         "
				+"\n			<value>430521199202176866</value>                                                                                                                                                                                                           "
				+"\n		</item>                                                                                                                                                                                                                                       "
				+"\n	</queryConditions>                                                                                                                                                                                                                              "
				+"\n	<policeCheckInfo subReportType=\"10602\" subReportTypeCost=\"10602\" treatResult=\"2\" errorMessage=\"不是有效的中国居民身份证(不包括港澳台身份证)\">                                                                                           "
				+"\n		<item>                                                                                                                                                                                                                                        "
				+"\n			<name>张三</name>                                                                                                                                                                                                                           "
				+"\n			<documentNo>430521199202176866</documentNo>                                                                                                                                                                                                 "
				+"\n			<result>3</result>                                                                                                                                                                                                                          "
				+"\n			<photo></photo>                                                                                                                                                                                                                             "
				+"\n		</item>                                                                                                                                                                                                                                       "
				+"\n	</policeCheckInfo>                                                                                                                                                                                                                              "
				+"\n</cisReport>                                                                                                                                                                                                                                      "
				+"\n</cisReports>     ";
		
		
		//System.out.println(data);
		XStream stream = creditDetailVOStream;
		//stream.setMode(XStream.XPATH_ABSOLUTE_REFERENCES);
		CisReportsVO vo =  (CisReportsVO) stream.fromXML(data);
		//System.out.println(vo.getCreditDetailVO().getName());
		//System.out.println(stream.toXML(vo));
	}
}
