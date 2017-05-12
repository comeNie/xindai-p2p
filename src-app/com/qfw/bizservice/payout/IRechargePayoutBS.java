package com.qfw.bizservice.payout;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizPaymentInfoBO;
import com.qfw.model.bo.BizRechargeCardBO;
import com.qfw.model.vo.payout.RechargeCardResponseVO;
import com.qfw.model.vo.payout.RechargeCardVO;
import com.qfw.model.vo.payout.RechargeVO;

/**
 * 充值bs
 *
 * @author kyc
 */
public interface IRechargePayoutBS extends IBaseService{

	/**
	 * 充值
	 * @param vo
	 * @throws BizException
	 */
	public void transRechargePayou(RechargeVO vo) throws BizException;
	
	
	/**
	 * 充值
	 * @param vo
	 * @throws BizException
	 */
	public void rechargeCard(RechargeVO vo ,int custId) throws BizException;
	
	
	public BizRechargeCardBO findCardBOById(Integer id) throws BizException;
	
	/**
	 * 获取总数
	 * @param cardVO
	 * @return
	 * @throws BizException
	 */
	public int findCardCountByVO(RechargeCardVO cardVO)throws BizException;
	
	public List<RechargeCardResponseVO> findCardBOPagesByVO(RechargeCardVO cardVO, int first, int pageSize) throws BizException;

	public RechargeCardResponseVO findCardInfoByID(Integer rechargeCardID);
	
	/**
	 * 更改充值卡状态
	 * @param status
	 * @param cardList
	 * @throws BizException
	 */
	public void updateRechargeCardStatus(String status,List<String> cardList) throws BizException; 
	
	/**
	 * 生成充值信息
	 * @param bank 银行
	 * @param amount 金额
	 * @param custId 客户id
	 * @return
	 * @throws BizException
	 */
	public BizPaymentInfoBO createPaymentInfo(String bank,String amount,Integer custId,HttpServletRequest request
			) throws BizException;


	/**
	 * 更新充值订单信息
	 * @param billNo 订单号
	 * @param status 状态
	 * @throws BizException
	 */
	public void updatePaymentInfo(String billNo,String status) throws BizException;
}
