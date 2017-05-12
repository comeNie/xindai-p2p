package com.qfw.dao.creditor;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.creditor.CreditorCountVO;
import com.qfw.model.vo.creditor.CreditorSearchVO;

public interface ICreditorCountManageDAO extends IBaseDAO {

	public List<CreditorCountVO> findInfoPagesByVO(CreditorSearchVO searchVO,
			int first, int pageSize) throws BizException;

	public int getCountByVO(CreditorSearchVO searchVO);

}
