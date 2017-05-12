package com.qfw.bizservice.creditor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizAutoTenderConfigBO;
import com.qfw.model.bo.BizCreditorRightTranBO;
import com.qfw.model.vo.creditor.CreditorManageVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;
import com.qfw.platform.model.transfer.TransferSearchVo;
import com.qfw.platform.model.transfer.TransferVo;

public interface IAutoTenderConfigBS extends IBaseService {


	/**
	 * 获取自动投标配置信息
	 */
	public BizAutoTenderConfigBO findAutoTenderConfigBOByCustId(Integer custId,Integer isAuto);
	
	/**
	 * 获取自动投标配置信息
	 */
	public BizAutoTenderConfigBO findAutoTenderConfigBOByCustId(Integer custId);
	
}
