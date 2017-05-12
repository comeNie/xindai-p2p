package com.qfw.platform.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qfw.bizservice.bussparams.IProductInfoBS;
import com.qfw.bizservice.loan.ILoanManageBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.MobileUtil;
import com.qfw.model.bo.BizProductBO;

/**
 * 首页
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping(value = "/")
public class IndexAction extends BaseAction {

	@Resource(name = "loanManageBS")
	private ILoanManageBS loanManageBS;
	
	@Resource(name = "productInfoBS")
	private IProductInfoBS productInfoBS;    //产品信息

	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		setCommonData(request);
		BizProductBO bizProductBO = new BizProductBO();// TODO
		try {
			bizProductBO = productInfoBS.findProductInfoById(1);
		} catch (BizException e1) {
			e1.printStackTrace();
		}
		
		setAttribute(request,"leastRateYear",bizProductBO.getLeastRateYear());
		setAttribute(request,"mostRateYear",bizProductBO.getMostRateYear());
		if(MobileUtil.JudgeIsMoblie(request)){
			return getResultPath(MOBILE_INDEX);
		}
		return getResultPath("INDEX");
		
	}
}
