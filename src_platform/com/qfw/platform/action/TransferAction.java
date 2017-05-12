package com.qfw.platform.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qfw.bizservice.creditor.ICreditorManageBS;
import com.qfw.bizservice.creditor.ICreditorRightBS;
import com.qfw.bizservice.loan.ILoanBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.DateUtils;
import com.qfw.model.AppConst;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorRightVO;
import com.qfw.platform.cache.CacheManager;
import com.qfw.platform.model.json.Pager;
import com.qfw.platform.model.json.Transfer;
import com.qfw.platform.model.loan.CreditReportDetailVO;
import com.qfw.platform.model.loan.CreditorRightDetailVO;
import com.qfw.platform.model.loan.CustomerDetailVO;
import com.qfw.platform.model.loan.LoanDetailVO;
import com.qfw.platform.model.loan.LoanRepayInfoVo;
import com.qfw.platform.model.loan.RepayPlanDtlVO;
import com.qfw.platform.model.transfer.TransferSearchVo;
import com.qfw.platform.model.transfer.TransferVo;
import com.qfw.platform.model.vo.CustAccountVO;
import com.qfw.platform.model.vo.LoginInfo;
import com.qfw.platform.service.ILoanService;

/**
 * 债权转让
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/transfer")
public class TransferAction extends BaseAction {

	@Resource(name = "creditorManageBS")
	private ICreditorManageBS creditorManageBS;

	@Resource(name = "loanService")
	private ILoanService loanService;

	@Resource(name = "creditorRightBS")
	private ICreditorRightBS creditorRightBS;

	@Resource(name = "loanBS")
	private ILoanBS loanBS;

	@Resource(name = "cacheManager")
	private CacheManager cacheManager;

	/**
	 * 债权转让列表查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response) {
		setCommonData(request);
		return getResultPath(Transfer_LIST);
	}

	@ResponseBody
	@RequestMapping(value = "/ajaxQuery")
	public Pager transferAjaxQuery(@RequestParam("loanType") String loanType,
			@RequestParam("term") String term,
			@RequestParam("rate") String rate,
			@RequestParam("creditLevel") String creditLevel,
			@RequestParam("requestPage") int requestPage,
			@RequestParam("pageSize") int pageSize) {

		Pager pager = new Pager();

		TransferSearchVo SearchVO = new TransferSearchVo();

		// //System.out.println("112314234324234");
		// 标的类型
		if (!"-1".equals(loanType)) {
			SearchVO.setLoanType(loanType);
		}

		// 剩余期限
		if (!"-1".equals(term)) {
			SearchVO.setRemainTerm(term);
		}

		// //System.out.println("112314234324234");
		// 年华率
		if (!"-1".equals(rate)) {
			SearchVO.setLoanRate(rate);
		}

		// 信用等级
		if (!"-1".equals(creditLevel)) {
			SearchVO.setCreditRate(creditLevel);
		}

		try {
			int totalCount = creditorManageBS.getTransCountByVO(SearchVO);
			// //System.out.println("totalCount===="+totalCount);
			List<TransferVo> transferVOList = creditorManageBS
					.findListPagesByVO(SearchVO, (requestPage - 1) * pageSize,
							pageSize);
			changeCodeVal(transferVOList);
			// //System.out.println("53535");
			pager.setTotalCount(totalCount);
			pager.setList(transferVOList);

		} catch (BizException e) {
			 
		}

		return pager;
	}

	private void changeCodeVal(List<TransferVo> transferVOList) {
		if (null == transferVOList || transferVOList.isEmpty())
			return;
		for (TransferVo transferVO : transferVOList) {
			transferVO.setRepayTypeCdStr(cacheManager
					.getDisPlayNameByCodeTypeAndVal("repayTypeCd",
							transferVO.getRepayTypeCd()));
		}
	}

	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value = "/detail")
	public String transferDetail(@RequestParam("transferId") int transferId,
			HttpServletRequest request, HttpServletResponse response) {
		// 查询条件类
		TransferSearchVo SearchVO = new TransferSearchVo();
		SearchVO.setTransferId(transferId);

		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 债权转让详情
		TransferVo transferVo = new TransferVo();
		// 账户信息
		CustAccountVO custAccountVO = new CustAccountVO();
		// 账户信息
		CustAccountVO custAccountVO1 = new CustAccountVO();
		// 投标记录
		// List creditorRightDetailVOList = new ArrayList();
		// 客户号
		Integer customerId = null;
		// 个人信息
		CustomerDetailVO customerDetailVO = new CustomerDetailVO();
		// 信用报告
		CreditReportDetailVO creditReportDetailVO = new CreditReportDetailVO();
		// 认证信息
		List authDetailVOList = new ArrayList();
		// 还款计划
		List repayPlanDetailVOList = new ArrayList();
		// 转让记录
		List tranVoList = new ArrayList();
		//还款信息
		LoanRepayInfoVo loanRepayInfoVo = null;
		//标的详情
		LoanDetailVO loanDetailVO = new LoanDetailVO();     //自己加的

		String isLogin = "true"; 
		
		// 转让详情
		try {

			transferVo = creditorManageBS.findListPagesByVO(SearchVO, 0, 1)
					.get(0);
			

		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		}
		// 发布编号
		Integer LoanApproveId = LoanApproveId = transferVo.getLoanApproveId();
		
		/**
		 * 为了让loanDetail.ftl页面可以取到 loanDetailVO对象，从而取到里头的“剩余时间”字段
		   自己加的
		 */
		try {
			loanDetailVO = loanService.getLoanDetail(LoanApproveId);
		} catch (BizException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		

		// 获取登陆信息
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(
				AppConst.LOGIN_INFO_SESSION);

		
		if (transferVo.getCrtStatusCd().equals("2")) {
			try {
				if(loginInfo!=null){
					// 账户信息
					custAccountVO1 = loanService.getCustAccount(loginInfo
							.getCustId());
					// ////System.out.println("11111");
					custAccountVO = setMaxBuy(custAccountVO1, transferVo);
					// ////System.out.println("2222");
					// 投标记录
					// creditorRightDetailVOList =
					// loanService.getCreditorRight(loanApproveId,false);

					// 还款记录
					repayPlanDetailVOList = loanService
							.getRepayPlanDetail(LoanApproveId);

					if (null != transferVo) {
						customerId = transferVo.getLoanCustId();
						// 个人信息
						customerDetailVO = loanService
								.getCustomerDetail(customerId);
						// 信用报告
						creditReportDetailVO = loanService
								.getCreditReport(customerId);
						// 认证信息
						authDetailVOList = loanService.getAuthDetail(customerId);
						// //System.out.println("3333");
						// 设置参数
						dataMap.put("customerDetailVO", customerDetailVO);
						dataMap.put("creditReportDetailVO", creditReportDetailVO);
						dataMap.put("authDetailVOList", authDetailVOList);
					}
				}else{
					isLogin = "false";
				}
				dataMap.put("isLogin", isLogin);
				dataMap.put("transferVo", transferVo);
				dataMap.put("repayPlanDetailVOList", repayPlanDetailVOList);
				dataMap.put("custAccountVO", custAccountVO);
                
				dataMap.put("loanDetailVO", loanDetailVO);  //自己加的
				// //System.out.println("4444");
				setAttributes(request, dataMap);
				setCommonData(request);
			} catch (BizException e) {
				 
			}
			return getResultPath(Transfer_DETAIL);

			// 满标，跳转到查询页面
		} else {
			try {
				
				loanRepayInfoVo = loanService.getLoanRepayInfo(LoanApproveId);
				loanRepayInfoVo.setLoanApproveId(LoanApproveId);
				loanRepayInfoVo.setNextRepayDate(DateUtils.addMonth(new Date(), 1));;
				loanRepayInfoVo.setRestPeriods(loanDetailVO.getLoanTerm()); 
				BigDecimal rate =loanDetailVO.getLoanRate().divide(new BigDecimal(100),8,BigDecimal.ROUND_HALF_UP);
				//System.out.println("rate=="+rate);
				BigDecimal sumAmt = loanDetailVO.getLoanAmt().multiply(rate).multiply(new BigDecimal(loanDetailVO.getLoanTerm())).divide(new BigDecimal(12),2,BigDecimal.ROUND_HALF_UP).setScale(2, BigDecimal.ROUND_HALF_UP);
				loanRepayInfoVo.setSumRepayAmt(sumAmt.add(loanDetailVO.getLoanAmt()));
				dataMap.put("loanRepayInfoVo", loanRepayInfoVo);
				//loanRepayInfo							     
				
 
				
				// //System.out.println("5555");
				// 投标记录
				// creditorRightDetailVOList =
				// loanService.getCreditorRight(loanApproveId,false);
				// 还款记录
			  if(loginInfo!=null){
				  repayPlanDetailVOList = loanService
							.getRepayPlanDetail(transferVo.getLoanApproveId());
					// 转让记录
					tranVoList = loanService.getCreditorRight(LoanApproveId, true);
					// //System.out.println("666666");

					if (null != transferVo) {
						customerId = transferVo.getLoanCustId();
						// 个人信息
						customerDetailVO = loanService
								.getCustomerDetail(customerId);
						// 信用报告
						creditReportDetailVO = loanService
								.getCreditReport(customerId);
						// 认证信息
						authDetailVOList = loanService.getAuthDetail(customerId);
						
						// 设置参数
						dataMap.put("customerDetailVO", customerDetailVO);
						dataMap.put("creditReportDetailVO", creditReportDetailVO);
						dataMap.put("authDetailVOList", authDetailVOList);
						dataMap.put("repayPlanDetailVOList", repayPlanDetailVOList);
						
					}
			  }else{
				  isLogin = "false";
			  }
				
				// //System.out.println("777777");
			    dataMap.put("isLogin", isLogin);
				dataMap.put("transferVo", transferVo);
				dataMap.put("custAccountVO", custAccountVO);
				// dataMap.put("creditorRightDetailVOList",
				// creditorRightDetailVOList);
				dataMap.put("tranVoList", tranVoList);
				
				dataMap.put("loanDetailVO", loanDetailVO); //自己加的

				setAttributes(request, dataMap);

				setCommonData(request);
			} catch (BizException e) {
				 
			}
			setCommonData(request);
			return getResultPath(Transfer_DETAIL_NO);
		}
	}

	//转让记录 
	@ResponseBody
	@RequestMapping(value = "/ajaxTranList")
	public Pager AjaxTranList(
			@RequestParam("transferId") int transferId) {
		Pager pager = new Pager();
		////System.out.println("huan是否打手犯规");
		List<CreditorRightDetailVO> tranVoList = null;
		try {
			tranVoList = loanService.getCreditorRight(transferId,true);
			for (CreditorRightDetailVO creditorRightDetailVO : tranVoList) {
				creditorRightDetailVO.setTenderTypeCd(cacheManager
						.getDisPlayNameByCodeTypeAndVal("tenderTypeCd",
								creditorRightDetailVO.getTenderTypeCd()));
				
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		}
		pager.setPageSize(1);
		pager.setTotalCount(tranVoList==null?0:tranVoList.size());
		pager.setList(tranVoList);
		
		return pager;
	}
	
	//还款记录 
	@ResponseBody
	@RequestMapping(value = "/ajaxRepayPlanDetail")
	public Pager AjaxRepayPlanDetail(
			@RequestParam("transferId") int transferId) {
		////System.out.println("hfskhfks");
		Pager pager = new Pager();
		List<RepayPlanDtlVO> repayPlanDetailVOList = null;
		try {
			repayPlanDetailVOList = loanService.getRepayPlanDetail(transferId);
			for (RepayPlanDtlVO repayPlanDetailVO : repayPlanDetailVOList) {
				repayPlanDetailVO.setRepayStatusCd(cacheManager
						.getDisPlayNameByCodeTypeAndVal("repayStatusCd",
								repayPlanDetailVO.getRepayStatusCd()));
				
			}
		} catch (BizException e) {
			// TODO Auto-generated catch block
			 
		}
		pager.setPageSize(1);
		pager.setTotalCount(repayPlanDetailVOList==null?0:repayPlanDetailVOList.size());
		pager.setList(repayPlanDetailVOList);
		
		return pager;
	}
	
	
	@RequestMapping(value = "/trans")
	public String trans(
			@RequestParam("usableBuyCount") Integer usableBuyCount,
			@RequestParam("buyCount") int buyCount,
			@RequestParam("limitAmt") double limitAmt,
			@RequestParam("remainCount") int remainCount,
			@RequestParam("loanApproveId") int loanApproveId,
			@RequestParam("creditorId") int creditorId,
			HttpServletRequest request, HttpServletResponse response) {

		//System.out.println("loanApproveId===" + loanApproveId + "-----limitAmt=" + limitAmt);
		//System.out.println("buyCount===" + buyCount + "-----usableBuyCount=" + usableBuyCount);
		//System.out.println("remainCount===" + remainCount + "-----remainCount=" + remainCount);
		CreditorRightVO creditorRightVO = new CreditorRightVO();
		String errMes = "";
		String sucMes = "投标成功";
		// 获取登陆信息
		LoginInfo loginInfo = (LoginInfo) request.getSession().getAttribute(
				AppConst.LOGIN_INFO_SESSION);
		BigDecimal crAmt = new BigDecimal(buyCount * limitAmt);

		if (buyCount <= 0) {
			errMes = "购买份数必须大于零！";
		} else if (buyCount > remainCount) {
			errMes = "购买份数不能多于剩余份数！";
		} else if (buyCount > usableBuyCount) {
			errMes = "购买份数不能多于可购买份数！";
		} else if (loginInfo == null) {
			errMes = "未登陆无法投标";
		} else if (loanApproveId < 0) {
			errMes = "无借款编号";
		} else {
			creditorRightVO.setCustId(loginInfo.getCustId());// 投资人编号
			creditorRightVO.setCreditorRightTranId(creditorId); // 发布表信息ID
			creditorRightVO.setCrAmt(crAmt);// 投资金额
			creditorRightVO.setTurnoverCount(buyCount);// 投资份数
			creditorRightVO.setTenderTypeCd("1"); // 投资类型,自动投标0\手动投标1
			// 是否满标标识
			Boolean isFullTender;
			try {
				isFullTender = this.creditorRightBS
						.submitTrenderTran(creditorRightVO);
			} catch (BizException e) {
				errMes = e.getMessage();
				if (errMes.length() > 60) {
					errMes = "系统繁忙,请稍后再操作!";
				}
				 
			}
		}

		setAttribute(request, "errMes", errMes);
		setAttribute(request, "sucMes", sucMes);
		setCommonData(request);
		return transferDetail(creditorId,request,response);//"redirect:/transfer/detail.do?loanApproveId=" + loanApproveId;

	}

	private CustAccountVO setMaxBuy(CustAccountVO custAccountVO,
			TransferVo transferVo) {

		BigDecimal buyCount = custAccountVO.getAvaiBal().divide(
				transferVo.getLimitAmt(), 0, BigDecimal.ROUND_HALF_DOWN);

		if (buyCount.compareTo(new BigDecimal(transferVo.getRemainCount())) == 1) {
			buyCount = new BigDecimal(transferVo.getRemainCount());
		}

		custAccountVO.setBuyCount(buyCount.intValue());
		return custAccountVO;

	}

	/**
	 * 数据库格式的转换成json对象
	 * 
	 * @param creditorManageVOList
	 * @param totalCount
	 * @return
	 */
	private Pager vo2JsonCreditor(List<CreditorManageVO> creditorManageVOList,
			int totalCount) {
		Pager pager = new Pager();
		if (null == creditorManageVOList || creditorManageVOList.isEmpty())
			return null;

		List<Transfer> transferList = new ArrayList<Transfer>();
		for (CreditorManageVO creditorManageVO : creditorManageVOList) {
			Transfer transfer = new Transfer();
			transfer.setCreditorId(creditorManageVO.getCreditorId());
			transfer.setLoanName(creditorManageVO.getLoanName());
			// TODO 信用等级
			transfer.setCreditLevel("AA");
			transfer.setLoanRate(creditorManageVO.getLoanRate());
			transfer.setTranBalAmt(creditorManageVO.getTakeBalAmt());
			transfer.setTakeAmt(creditorManageVO.getTakeAmt());
			transfer.setSurplusPeriod(creditorManageVO.getSurplusPeriod());
			transfer.setCompleteness(creditorManageVO.getCompleteness());
			transfer.setRepayTypeCd(cacheManager
					.getDisPlayNameByCodeTypeAndVal("repayTypeCd",
							creditorManageVO.getRepayTypeCd()));
			transferList.add(transfer);
		}
		pager.setList(transferList);
		pager.setTotalCount(totalCount);
		return pager;
	}

}
