package com.qfw.dao.friend.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.dao.impl.BaseDAOImpl;
import com.qfw.common.exception.BizException;
import com.qfw.dao.friend.IFriendManageDAO;
import com.qfw.dao.income.IIncomeDAO;
import com.qfw.model.vo.friend.FriendManageVO;
import com.qfw.model.vo.income.IncomeDetailVO;
import com.qfw.model.vo.income.IncomeSearchVO;

@Repository("friendManageDAO")
public class FriendManageDAOImpl extends BaseDAOImpl implements
		IFriendManageDAO {

	@Autowired
	private ICommonQuery commonQuery;

	@SuppressWarnings("unchecked")
	@Override
	public List<FriendManageVO> findInfoPagesByVO(Integer userId,
			String friendName, int first, int pageSize) throws BizException {
		return this.commonQuery.findBySQLByPages(this.assembleMyFriendSQL(userId,
				friendName), first*pageSize, pageSize, FriendManageVO.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FriendManageVO> findStrangerPagesByVO(Integer userId,
			String friendName, int first, int pageSize) throws BizException {
		return this.commonQuery.findBySQLByPages(this.assembleMyStrangerSQL(userId,
				friendName), first*pageSize, pageSize, FriendManageVO.class);
	}
	

	/**
	 * 组装查询我的好友sql
	 * 
	 * @param userId,friendName
	 * @return
	 */
	private String assembleMyFriendSQL(Integer userId, String friendName) {
		StringBuffer str = new StringBuffer();
		str.append(" select ");
		str.append(" f.id,f.userId as user_id,u.user_code as user_name, ");
		str.append(" f.friendId as friend_Id, fu.user_code as friend_name,fu.HEAD_PATH as friend_photo ");
		str.append(" from biz_friend_manage f  ");
		str.append(" left join sys_user u on f.userId=u.USER_ID ");
		str.append(" left join sys_user fu on f.friendId=fu.USER_ID ");
		str.append(" where f.userId=").append(userId).append("");

		if (null != friendName && !"".equals(friendName)) {
			str.append(" and fu.USER_CODE like '%").append(friendName).append(
					"%'");
		}

		str.append(" order by f.userId  ");
		return str.toString();
	}
	
	/**
	 * 组装查询陌生人
	 * 
	 * @param userId,friendName
	 * @return
	 */
	private String assembleMyStrangerSQL(Integer userId, String friendName) {
		StringBuffer str = new StringBuffer();
		str.append(" select ");
		str.append(" u.user_id as id,0 as user_id,'' as user_name, ");
		str.append(" u.user_id as friend_Id, u.user_code as friend_name,u.HEAD_PATH as friend_photo ");
		str.append(" from sys_user u  ");
		str.append(" where u.user_id not in ( select friendId from biz_friend_manage where userId=" + userId + " ) ");
		str.append(" and u.user_id<>"+userId+" ");
		if (null != friendName && !"".equals(friendName)) {
			str.append(" and u.USER_CODE like '%").append(friendName).append(
					"%'");
		}

		str.append(" order by u.user_id  ");
		return str.toString();
	}

}
