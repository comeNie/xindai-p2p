package com.qfw.dao.friend;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.model.vo.friend.FriendManageVO;
import com.qfw.model.vo.income.IncomeDetailVO;
import com.qfw.model.vo.income.IncomeSearchVO;

public interface IFriendManageDAO extends IBaseDAO {

	
	/**
	 * 查询好友列表
	 * 
	 * 
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<FriendManageVO> findInfoPagesByVO(Integer userId,String friendName,
			int first, int pageSize) throws BizException;
	
	/**
	 * 查询陌生人列表
	 * @param first
	 * @param pageSize
	 * @return
	 * @throws BizException
	 */
	public List<FriendManageVO> findStrangerPagesByVO(Integer userId,
			String friendName, int first, int pageSize) throws BizException;

	
}
