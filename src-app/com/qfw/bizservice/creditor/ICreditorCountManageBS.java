package com.qfw.bizservice.creditor;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.creditor.CreditorCountVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;

public interface ICreditorCountManageBS extends IBaseService {

	public List<CreditorCountVO> findInfoPagesByVO(CreditorSearchVO searchVO,
			int first, int pageSize) throws BizException;

	public int getCountByVO(CreditorSearchVO searchVO);

	

}
