package com.qfw.bizservice.friend;

import java.util.Date;
import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.model.bo.BizRepayDetailBO;
import com.qfw.model.vo.friend.FriendManageVO;
import com.qfw.platform.model.json.Pager;

/**
 * 好友管理
 * @author jiyb
 *
 */
public interface IFriendManageBS extends IBaseService{
	/**
	 * 好友管理
	 * @throws BizException
	 */
	public List<FriendManageVO> findInfoPagesByVO(Integer userId,String friendName,
			int first, int pageSize) throws BizException;
	
	public Pager findInfoPages(Integer userId,String friendName,
			int first, int pageSize) throws BizException;
	
	public Pager findStrangerPages(Integer userId,
			String friendName, int first, int pageSize) throws BizException ;
	
}
