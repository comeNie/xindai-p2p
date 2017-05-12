package com.qfw.test;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qfw.bizservice.custinfo.ICustInfoBS;
import com.qfw.common.bizservice.IMsgTemplateBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.impl.HttpEndPointBS;
import com.qfw.common.gateway.impl.MessageServiceBSImpl;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.StringUtils;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizCustomerBO;
import com.qfw.model.vo.transaction.QueryCreditReqVO;
import com.qfw.model.vo.transaction.QueryOrderVO;
import com.qfw.xfire.WebServiceSingleQueryOfUnzip;
import com.qfw.xfire.WebServiceSingleQueryOfUnzipServiceClient;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:WebContent/WEB-INF/applicationContext.xml")
public class JunitTest extends AbstractJUnit4SpringContextTests{

	@Autowired
	@Qualifier("messageServiceBS")
	private MessageServiceBSImpl messageServiceBS;
	@Autowired
	@Qualifier("mailSender")
	private JavaMailSenderImpl sender;
	@Autowired
	@Qualifier("seqBS")
	private ISeqBS seqBS;
	
	@Autowired
	private IMsgTemplateBS msgTemplateBS;
	@Autowired
	private ICustInfoBS custInfoBS;
	
	//@Test
    public void saveTest1() throws BizException {
		for(int i = 0; i<10; i++){
			String s = seqBS.getResultNum("TEST");
			System.out.println(s);
			/*s = seqBS.getResultNum("TEST1");
			System.out.println(s);*/
		}
		
	}
	
    //测试订单查询
	//@Test
    public void saveTest() throws BizException, MessagingException, UnsupportedEncodingException{
		QueryOrderVO order = new QueryOrderVO();
		order.setTx("1001");
		order.setMerCode("19464");
		order.setOrderNumber("T00100d0101");
		order.setSign(MD5Utils.getMD5Str("19464"+"R[Qjw^zN").toUpperCase());
		String xml = messageServiceBS.generateXml(order, "queryOrder.xml");
		String s = StringUtils.getBASE64(xml);
		System.out.println(xml);
		System.out.println(s);
		Map param = new HashMap();
		param.put("requestDomain", s);
		String result = HttpEndPointBS.send("https://merchant.ecpss.cn/merchantBatchQueryAPI", param, null);
		System.out.println(result);
	}
    //@Test
	public void testQueryCredit() throws BizException{
		QueryCreditReqVO creditReqVO = new QueryCreditReqVO();
		creditReqVO.setQueryType("25121");
		creditReqVO.setName("张三");
		creditReqVO.setDocumentNo("430521199202176866");
		creditReqVO.setRefID("1");
		creditReqVO.setSubreportIDs("10602");
		
		WebServiceSingleQueryOfUnzipServiceClient client = new WebServiceSingleQueryOfUnzipServiceClient();
		WebServiceSingleQueryOfUnzip zip = client.getWebServiceSingleQueryOfUnzip();

		String xml = messageServiceBS.generateXml(creditReqVO, "queryCreditReq.xml");
		System.out.println(xml);
		String s = StringUtils.getBASE64(xml);
		System.out.println(s);
		String resp = zip.queryReport("pmwsquery", "/o8FTDxgnhqP4/7SBdwEBw==", xml, "xml");
		System.out.println(resp);
	}
	
	@Test
	public void test() throws BizException{
		Map dataMap = new HashMap();
		//您在${date}为账号${accno}充值${amt}元已到账，详咨XXXXXXXXXXXX
		dataMap.put("date", DateUtils.getYmdhms(new Date()));//对应模板的${date}
		dataMap.put("amt", "1000");//对应模板的${amt}
		dataMap.put("accno", "lushijie");
		BizCustomerBO cust = custInfoBS.findCustById(61);
		msgTemplateBS.sendMsg(AppConst.TRADE_TYPE_RECHARGE, cust, dataMap);
	}
}
