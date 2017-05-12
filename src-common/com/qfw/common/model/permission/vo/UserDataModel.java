package com.qfw.common.model.permission.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.common.bizservice.permission.IUserBS;
import com.qfw.common.model.permission.SysUser;

@SuppressWarnings("serial")
public class UserDataModel extends LazyDataModel<SysUser> {
	
	private SysUserVO userVO;
	private IUserBS userBS;
	private List<SysUser> users;
	public UserDataModel(){
		users = new ArrayList<SysUser>();
		setRowCount(0);
	}
	public UserDataModel(SysUserVO userVO,IUserBS userBS){
		this.userVO = userVO;
		this.userBS = userBS;
		
	}
	@Override
	public List<SysUser> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		if(userBS != null){
			users = userBS.findUsersPagesByVO(userVO, first, pageSize);
			setRowCount(userBS.findUsersCountByVO(userVO));
		}
		return users;
	}
	@Override
	public SysUser getRowData(String rowKey) {
		for(SysUser user : users){
			if(String.valueOf(user.getUserId()).equals(rowKey))
				return user;
		}
		return userBS.findUserById(Integer.valueOf(rowKey));
	}
	@Override
	public Object getRowKey(SysUser user) {
		return user.getUserId();
	}
	
	

}
