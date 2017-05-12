/**
 * 
 */
package com.qfw.bizservice.payout.impl;

import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aipg.common.AipgReq;
import com.aipg.common.AipgRsp;
import com.aipg.common.InfoReq;
import com.aipg.common.XSUtil;
import com.aipg.rtreq.Trans;
import com.aipg.rtrsp.TransRet;
import com.aipg.transquery.QTDetail;
import com.aipg.transquery.QTransRsp;
import com.aipg.transquery.TransQueryReq;
import com.qfw.bizservice.payout.ITranxService;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.util.XmlTools;
import com.qfw.model.AppConst;
import com.qfw.model.vo.transaction.TranxCon;

/**
 */
@Service("tranxService")
public class TranxServiceImpl extends BaseServiceImpl implements ITranxService{
	
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@Autowired
	private IParamBS paramBS;
	
	/**
	 * 组装报文头部
	 * @param trxcod
	 * @return
	 *日期：Sep 9, 2012
	 */
	private InfoReq makeReq(String trxcod,TranxCon tranxCon)
	{
		  
		InfoReq info=new InfoReq();
		info.setTRX_CODE(trxcod);
		info.setREQ_SN(tranxCon.getReqSn());
		info.setUSER_NAME(paramBS.getParam("WD_USER_NAME"));
		info.setUSER_PASS(paramBS.getParam("WD_PWD"));
		info.setLEVEL("5");
		info.setDATA_TYPE("2");
		info.setVERSION("03");
		if("300000".equals(trxcod)
				||"300001".equals(trxcod)||"300003".equals(trxcod)
				||"220001".equals(trxcod)||"220003".equals(trxcod)){
			info.setMERCHANT_ID(paramBS.getParam("WD_MERCHANTID"));
		}
		return info;
	}
	
	public String sendXml(String xml,String url,boolean isFront) throws UnsupportedEncodingException, Exception{
		if(log.isDebugEnabled()){
			log.debug("======================发送报文======================：\n"+xml);
		}
		String resp=XmlTools.send(url,xml);
		if(log.isDebugEnabled()){
			log.debug("======================响应内容======================：\n"+resp);
		}
		boolean flag= this.verifyMsg(resp, paramBS.getParam("WD_TLTCERPATH"),isFront);
		if(flag){
			log.info("响应内容验证通过") ;
		}else{
			log.error("响应内容验证不通过") ;
			throw new BizException("验名验证不通过");
		}
		return resp;
	}
	
	public String sendToTlt(String xml,boolean flag,String url)throws BizException {
		try{
			if(!flag){
				xml=this.signMsg(xml);
			}else{
				xml=xml.replaceAll("<SIGNED_MSG></SIGNED_MSG>", "");
			}
			return sendXml(xml,url,flag);
		}catch(Exception e){
			if(e.getCause() instanceof ConnectException||e instanceof ConnectException){
				log.error("请求链接中断，请做交易结果查询，以确认上一笔交易是否已被通联受理，避免重复交易");
				throw new BizException("交易处理中，请勿重复提交");
			}
			throw new BizException(e.getMessage());
		}
	}
	/**
	 * 报文签名
	 * @param msg
	 * @return
	 *日期：Sep 9, 2012
	 * @throws Exception 
	 */
	public String signMsg(String xml) throws Exception{
		xml=XmlTools.signMsg(xml, paramBS.getParam("WD_PFXPATH"),paramBS.getParam("PFX_PASSWORD"), false);
		return xml;
	}
	
	/**
	 * 验证签名
	 * @param msg
	 * @return
	 *日期：Sep 9, 2012
	 * @throws Exception 
	 */
	public boolean verifyMsg(String msg,String cer,boolean isFront) throws Exception{
		 boolean flag=XmlTools.verifySign(msg, cer, false,isFront);
		 if(log.isInfoEnabled()){
			 log.info("验签结果["+flag+"]");
		 }
		return flag;
	}
	

	/**
	 * 日期：Sep 4, 2012
	 * 功能：实时单笔代收付，100011是实时代笔代收，100014是实时单笔代付
	 * @param trx_code 
	 * @throws Exception 
	 */
	public String singleTranx(String trx_code, String busicode, boolean isTLTFront,TranxCon tranxCon) 
			throws BizException {
		//设置安全提供者,注意，这一步尤为重要
		BouncyCastleProvider provider = new BouncyCastleProvider();
		XmlTools.initProvider(provider);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String xml="";
		AipgReq aipg=new AipgReq();
		InfoReq info=makeReq(trx_code,tranxCon);
		aipg.setINFO(info);
		Trans trans=new Trans();
		trans.setBUSINESS_CODE(busicode);
		trans.setMERCHANT_ID(paramBS.getParam("WD_MERCHANTID"));
		trans.setSUBMIT_TIME(df.format(new Date()));
		trans.setACCOUNT_NAME(tranxCon.getAcctName());
		trans.setACCOUNT_NO(tranxCon.getAcctNo());
		trans.setACCOUNT_PROP(tranxCon.getAccountProp());
		trans.setACCOUNT_TYPE(tranxCon.getAccountType());
		trans.setAMOUNT(tranxCon.getAmount());
		trans.setBANK_CODE(tranxCon.getBankcode());
		trans.setCURRENCY("CNY");
		trans.setCUST_USERID(tranxCon.getCustUserid());
		trans.setTEL("");
		aipg.addTrx(trans);
		
	    xml=XmlTools.buildXml(aipg,true);
		return dealRet(sendToTlt(xml,isTLTFront,AppConst.WITHDRAWAL_TRAN_URL));
		
	}
	/**
	 * @param reqsn 交易流水号
	 * @param url 通联地址
	 * @param isTLTFront 是否通过前置机
	 * @param startDate YYYYMMDDHHmmss
	 * @param endDate YYYYMMDDHHmmss
	 * 日期：Sep 4, 2012
	 * 功能：交易结果查询
	 * @throws Exception 
	 */
	public List<QTDetail> queryTradeNew(String url,TranxCon tranxCon,boolean isTLTFront) throws BizException {
	 
		String xml="";
		List<QTDetail> qtDetail = new ArrayList<QTDetail>();
		String reqsn = tranxCon.getReqSn();
		AipgReq aipgReq=new AipgReq();
		InfoReq info=makeReq(AppConst.WITHDRAWAL_QUERY_TRX_CODE,tranxCon);
		aipgReq.setINFO(info);
		TransQueryReq dr=new TransQueryReq();
		aipgReq.addTrx(dr);
		dr.setMERCHANT_ID(paramBS.getParam("WD_MERCHANTID")) ;
		dr.setQUERY_SN(reqsn);
		dr.setSTATUS(tranxCon.getQueryStatus());
		dr.setTYPE(tranxCon.getQueryType()) ;
		if(reqsn==null||"".equals(reqsn)){
			dr.setSTART_DAY(tranxCon.getStartDate());
			dr.setEND_DAY(tranxCon.getEndDate());
		}
		xml=XmlTools.buildXml(aipgReq,true);
		qtDetail = dealRetForQuery(sendToTlt(xml,isTLTFront,url));
		
		return qtDetail;
		
	}

	/**
	 * 返回报文处理逻辑
	 * @param retXml
	 */
	public String dealRet(String retXml)throws BizException{
		String trxcode = null;
		AipgRsp aipgrsp=null;
		String status = AppConst.WD_STATUS_P;
		//或者交易码
		if (retXml.indexOf("<TRX_CODE>") != -1)
		{
			int end = retXml.indexOf("</TRX_CODE>");
			int begin = end - 6;
			if (begin >= 0) trxcode = retXml.substring(begin, end);
		}
		aipgrsp=XSUtil.parseRsp(retXml);
		
		//批量代收付返回处理逻辑
		if("100001".equals(trxcode)||"100002".equals(trxcode)||"211000".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
//				System.out.println("受理成功，请在20分钟后进行10/每次的轮询");
			}else{
//				System.out.println("受理失败，失败原因："+aipgrsp.getINFO().getERR_MSG());
			}
		}else
		//交易查询处理逻辑
		if("200004".equals(trxcode)||"200005".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
//				QTransRsp qrsq=(QTransRsp) aipgrsp.getTrxData().get(0);
//				System.out.println("查询成功，具体结果明细如下:");
//				List<QTDetail> details=qrsq.getDetails();
				/*for(QTDetail lobj:details){
//					System.out.print("原支付交易批次号:"+lobj.getBATCHID()+"  ");
//					System.out.print("记录序号:"+lobj.getSN()+"  ");
//					System.out.print("账号:"+lobj.getACCOUNT_NO()+"  ");
//					System.out.print("户名:"+lobj.getACCOUNT_NAME()+"  ");
//					System.out.print("金额:"+lobj.getAMOUNT()+"  ");
//					System.out.print("返回结果:"+lobj.getRET_CODE()+"  ");
					
					if("0000".equals(lobj.getRET_CODE())){
//						System.out.println("返回说明:交易成功  ");
//						System.out.println("更新交易库状态（原交易的状态）");
					}else{
//						System.out.println("返回说明:"+lobj.getERR_MSG()+"  ");
//						System.out.println("更新交易库状态（原交易的状态）");
					}
					
				}*/
//				qtDetail = details;
				status = AppConst.WD_STATUS_S;
			}else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
//				System.out.print("返回说明:"+aipgrsp.getINFO().getRET_CODE()+"  ");
//				System.out.println("返回说明："+aipgrsp.getINFO().getERR_MSG());
//				System.out.println("该状态时，说明整个批次的交易都在处理中");
			}else if("2004".equals(aipgrsp.getINFO().getRET_CODE())){
//				System.out.println("整批交易未受理通过（最终失败）");
			}else if("1002".equals(aipgrsp.getINFO().getRET_CODE())){
//				System.out.println("查询无结果集");
			}else{
//				System.out.println("查询请求失败，请重新发起查询");
			}
		}else
		//实时交易结果返回处理逻辑(包括单笔实时代收，单笔实时代付，单笔实时身份验证)
		if("100011".equals(trxcode)||"100014".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
//				System.out.println("提交成功");
				TransRet ret=(TransRet) aipgrsp.getTrxData().get(0);
				if(log.isDebugEnabled()){
					log.debug("交易结果："+ret.getRET_CODE()+":"+ret.getERR_MSG());
				}
				if("0000".equals(ret.getRET_CODE())){
//					System.out.println("交易成功（最终结果）");
					status = AppConst.WD_STATUS_S;
				}else{
//					System.out.println("交易失败（最终结果）");
					if(log.isDebugEnabled()){
						log.debug("交易失败原因："+ret.getERR_MSG());
					}
					status = AppConst.WD_STATUS_F;
//					throw new BizException("交易失败，原因："+ret.getERR_MSG());
				}
			}else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
//				System.out.println("交易处理中或者不确定状态，需要在稍后5分钟后进行交易结果查询（轮询）");
				status = AppConst.WD_STATUS_P;
			}else if(aipgrsp.getINFO().getRET_CODE().startsWith("1")){
				String errormsg=aipgrsp.getINFO().getERR_MSG()==null?"连接异常，请重试":aipgrsp.getINFO().getERR_MSG();
//				System.out.println("交易请求失败，原因："+errormsg);
				log.error("交易请求失败，原因："+errormsg);
//				throw new BizException("交易请求失败，原因："+errormsg);
				status = AppConst.WD_STATUS_F;
			}else{
				TransRet ret=(TransRet) aipgrsp.getTrxData().get(0);
//				System.out.println("交易失败(最终结果)，失败原因："+ret.getERR_MSG());
				log.error("交易失败，失败原因："+ret.getERR_MSG());
//				throw new BizException("交易失败，失败原因："+ret.getERR_MSG());
				status = AppConst.WD_STATUS_F;
			}
		}else
		//(单笔实时身份验证结果返回处理逻辑)
		if("211003".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
//				System.out.println("提交成功");
//				ValidRet ret=(ValidRet) aipgrsp.getTrxData().get(0);
//				System.out.println("交易结果："+ret.getRET_CODE()+":"+ret.getERR_MSG());
			}else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
//				System.out.println("验证处理中或者不确定状态，需要在稍后5分钟后进行验证结果查询（轮询）");
			}else if(aipgrsp.getINFO().getRET_CODE().startsWith("1")){
//				String errormsg=aipgrsp.getINFO().getERR_MSG()==null?"连接异常，请重试":aipgrsp.getINFO().getERR_MSG();
//				System.out.println("验证请求失败，原因："+errormsg);
			}else{
//				TransRet ret=(TransRet) aipgrsp.getTrxData().get(0);
//				System.out.println("验证失败(最终结果)，失败原因："+ret.getERR_MSG());
			}
		}
		return status;
	}
	
	public List<QTDetail> dealRetForQuery(String retXml)throws BizException{
		String trxcode = null;
		AipgRsp aipgrsp=null;
		String status = AppConst.WD_STATUS_P;
		List<QTDetail> qtDetail = null;
		//或者交易码
		if (retXml.indexOf("<TRX_CODE>") != -1)
		{
			int end = retXml.indexOf("</TRX_CODE>");
			int begin = end - 6;
			if (begin >= 0) trxcode = retXml.substring(begin, end);
		}
		aipgrsp=XSUtil.parseRsp(retXml);
		
		//交易查询处理逻辑
		if("200004".equals(trxcode)||"200005".equals(trxcode)){
			if("0000".equals(aipgrsp.getINFO().getRET_CODE())){
				QTransRsp qrsq=(QTransRsp) aipgrsp.getTrxData().get(0);
//				System.out.println("查询成功，具体结果明细如下:");
				List<QTDetail> details=qrsq.getDetails();
				/*for(QTDetail lobj:details){
//					System.out.print("原支付交易批次号:"+lobj.getBATCHID()+"  ");
//					System.out.print("记录序号:"+lobj.getSN()+"  ");
//					System.out.print("账号:"+lobj.getACCOUNT_NO()+"  ");
//					System.out.print("户名:"+lobj.getACCOUNT_NAME()+"  ");
//					System.out.print("金额:"+lobj.getAMOUNT()+"  ");
//					System.out.print("返回结果:"+lobj.getRET_CODE()+"  ");
					
					if("0000".equals(lobj.getRET_CODE())){
//						System.out.println("返回说明:交易成功  ");
//						System.out.println("更新交易库状态（原交易的状态）");
					}else{
//						System.out.println("返回说明:"+lobj.getERR_MSG()+"  ");
//						System.out.println("更新交易库状态（原交易的状态）");
					}
					
				}*/
				qtDetail = details;
				status = AppConst.WD_STATUS_S;
			}
//			else if("2000".equals(aipgrsp.getINFO().getRET_CODE())
//					||"2001".equals(aipgrsp.getINFO().getRET_CODE())
//					||"2003".equals(aipgrsp.getINFO().getRET_CODE())
//					||"2005".equals(aipgrsp.getINFO().getRET_CODE())
//					||"2007".equals(aipgrsp.getINFO().getRET_CODE())
//					||"2008".equals(aipgrsp.getINFO().getRET_CODE())){
////				System.out.print("返回说明:"+aipgrsp.getINFO().getRET_CODE()+"  ");
////				System.out.println("返回说明："+aipgrsp.getINFO().getERR_MSG());
////				System.out.println("该状态时，说明整个批次的交易都在处理中");
//			}else if("2004".equals(aipgrsp.getINFO().getRET_CODE())){
////				System.out.println("整批交易未受理通过（最终失败）");
//			}else if("1002".equals(aipgrsp.getINFO().getRET_CODE())){
////				System.out.println("查询无结果集");
//			}else{
////				System.out.println("查询请求失败，请重新发起查询");
//			}
			
		}
		return qtDetail;
	}
}
