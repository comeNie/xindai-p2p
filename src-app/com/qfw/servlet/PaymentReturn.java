package com.qfw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.qfw.common.exception.BizException;
import com.qfw.common.gateway.impl.HttpEndPointBS;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.MD5Utils;
import com.qfw.common.util.StringUtils;

/**
 * Servlet implementation class PaymentReturn
 */
public class PaymentReturn extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Logger log = Logger.getLogger(HttpEndPointBS.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentReturn() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doPost(request, response);
		Map<String, String> hearder = new HashMap<String, String>();
		Enumeration et = request.getHeaderNames();
		while(et.hasMoreElements()){
			String name = (String) et.nextElement();
			hearder.put(name, request.getHeader(name));
		}
		Map<String, String> param = new TreeMap<String, String>();
		String merNo = "19464";
		String billNo = "Td0100d0101";
		String amount = "10.00";
		String returnURL = "http://qiangfanwan.f3322.org:8080/p2p/paymentReturn.do";
		String md5key = "R[Qjw^zN";
		String md5 = MD5Utils.getMD5Str(merNo+"&"+billNo+"&"+amount+"&"+returnURL+"&"+md5key);
		param.put("MerNo", merNo);
		param.put("BillNo", billNo);
		param.put("Amount", amount);
		param.put("ReturnURL", returnURL);
		param.put("AdviceURL", "http://qiangfanwan.f3322.org:8080/p2p/paymentReturn.do");
		param.put("SignInfo", md5.toUpperCase());
		param.put("orderTime", DateUtils.getDateString("yyyyMMddHHmmss", new Date()));
		param.put("defaultBankNumber", "CCB");
		param.put("Remark", "10");
		param.put("products", "可乐2L");
		try {
			
			String s = HttpEndPointBS.send("https://pay.ecpss.cn/sslpayment", param, null);
			//System.out.println(s);
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		}
		response.sendRedirect("paysubmit.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String merNo = request.getParameter("MerNo"); 
		if(StringUtils.isEmpty(merNo)){
			printStr(response,"商户号不能为空");
			return;
		}
		if(!"19464".equals(merNo)){
			printStr(response,"商户号不能匹配");
			return;
		}
		
		String billNo = request.getParameter("BillNo");
		if(StringUtils.isEmpty(billNo)){
			printStr(response,"订单号不能为空");
			return;
		}
		//判断订单是否存在
		
		String amount = request.getParameter("Amount"); 
		if(StringUtils.isEmpty(amount)){
			printStr(response,"金额不能为空");
			return;
		}
		
		String returnURL = request.getParameter("ReturnURL"); 
		
		if(StringUtils.isEmpty(returnURL)){
			printStr(response,"页面跳转同步通知页面不能为空");
			return;
		}
		
		String adviceURL = request.getParameter("AdviceURL"); 
		
		if(StringUtils.isEmpty(adviceURL)){
			printStr(response,"服务器异步通知路径不能为空");
			return;
		}
		
		String signInfo = request.getParameter("SignInfo"); 
		if(StringUtils.isEmpty(signInfo)){
			printStr(response,"签名信息不能为空");
			return;
		}
		
		String md5key = "R[Qjw^zN";
		String md5 = MD5Utils.getMD5Str(merNo+"&"+billNo+"&"+amount+"&"+returnURL+"&"+md5key);
		if(!signInfo.equals(md5)){
			printStr(response,"签名信息不匹配");
			return;
		}
		request.getParameter("orderTime"); 
		request.getParameter("defaultBankNumber"); 
		request.getParameter("Remark"); 
		request.getParameter("products");
		printStr(response, "ok");
	}
	private void printStr(HttpServletResponse response,String msg){
		PrintWriter write = null;
		try {
			write = response.getWriter();
			write.write(msg);
			log.info(msg);
		} catch (IOException e) {
			 
		}
		if(write!=null){
			write.flush();
			write.close();
		}
		
	}

}
