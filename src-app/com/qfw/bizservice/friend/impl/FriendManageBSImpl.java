package com.qfw.bizservice.friend.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.bizservice.custinfo.account.ITransferAccountsBS;
import com.qfw.bizservice.friend.IFriendManageBS;
import com.qfw.bizservice.income.IIncomeBS;
import com.qfw.common.bizservice.IParamBS;
import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.DateUtils;
import com.qfw.common.util.web.ViewOper;
import com.qfw.dao.friend.IFriendManageDAO;
import com.qfw.model.AppConst;
import com.qfw.model.bo.BizAutoTenderConfigBO;
import com.qfw.model.bo.BizCreditorRightBO;
import com.qfw.model.bo.BizIncomeDetailBO;
import com.qfw.model.bo.BizRepayDetailBO;
import com.qfw.model.vo.friend.FriendManageVO;
import com.qfw.platform.model.json.Pager;

/**
 * 好友管理
 * @author jiyb
 *
 */
@Service("friendBS")
public class FriendManageBSImpl extends BaseServiceImpl implements IFriendManageBS {

	@Autowired
	@Qualifier("baseDAO")
	private IBaseDAO baseDAO;
	
	@Autowired
	@Qualifier("friendManageDAO")
	private IFriendManageDAO friendManageDAO;
	
	public List<FriendManageVO> findInfoPagesByVO(Integer userId,String friendName,
			int first, int pageSize) throws BizException{
		
		return friendManageDAO.findInfoPagesByVO(userId, friendName, first, pageSize);
	}
	
	public Pager findInfoPages(Integer userId,String friendName,
			int first, int pageSize) throws BizException{
		
		return findPager(friendManageDAO.findInfoPagesByVO(userId, friendName, first, pageSize),pageSize);
	}
	
	
	public Pager findStrangerPages(Integer userId,
			String friendName, int first, int pageSize) throws BizException{
		
		return findPager(friendManageDAO.findStrangerPagesByVO(userId, friendName, first, pageSize),pageSize);
	}
	
	
	private Pager findPager(List<FriendManageVO> list,int pageSize){
		Pager pager = new Pager();
		int count = list.size();
		pager.setTotalCount(count);
		pager.setPageSize(pageSize);
		if(count > 0){
			pager.setList(list);
		}
		return pager;
	}

}
