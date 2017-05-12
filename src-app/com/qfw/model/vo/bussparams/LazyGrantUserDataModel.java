package com.qfw.model.vo.bussparams;

import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.qfw.bizservice.bussparams.IGrantUserBS;
import com.qfw.common.util.StringUtils;
import com.qfw.jbpm.model.bo.Jbpm4GrantUser;

public class LazyGrantUserDataModel extends LazyDataModel<Jbpm4GrantUser>{
	
	private static final long serialVersionUID = 1L;
	private List<Jbpm4GrantUser> grantUserList;	
	private IGrantUserBS grantUserBS;
	private Jbpm4GrantUser searchGrantUserVO;
	
	public LazyGrantUserDataModel(Jbpm4GrantUser searchGrantUserVO, IGrantUserBS grantUserBS){
		this.searchGrantUserVO = searchGrantUserVO;
		this.grantUserBS = grantUserBS;
	}

	@Override
	public List<Jbpm4GrantUser> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		try {
			grantUserList = this.grantUserBS.findGrantUserPagesByVO(searchGrantUserVO, first, pageSize);
			setRowCount(this.grantUserBS.findGrantUserCountByVO(searchGrantUserVO));
		} catch (Exception e) {
			 
		}
		return grantUserList;
	}
	
	@Override
	public Jbpm4GrantUser getRowData(String rowKey) {
		if(StringUtils.isEmpty(rowKey)){
			return null;
		}
		for(Jbpm4GrantUser grantUserVO : grantUserList){
			if(String.valueOf(grantUserVO.getId()).equals(rowKey))
				return grantUserVO;
		}
		Jbpm4GrantUser grantUserVO = new Jbpm4GrantUser();
		try {
			grantUserVO = grantUserBS.findGrantUserById(Integer.valueOf(rowKey));
		} catch (Exception e) {
		}
		return grantUserVO;
	}
	
	@Override
	public Object getRowKey(Jbpm4GrantUser grantUserVO){
		return grantUserVO.getId();
	}

	public List<Jbpm4GrantUser> getGrantUserList() {
		return grantUserList;
	}

	public void setGrantUserList(List<Jbpm4GrantUser> grantUserList) {
		this.grantUserList = grantUserList;
	}

	public IGrantUserBS getGrantUserBS() {
		return grantUserBS;
	}

	public void setGrantUserBS(IGrantUserBS grantUserBS) {
		this.grantUserBS = grantUserBS;
	}

	public Jbpm4GrantUser getSearchGrantUserVO() {
		return searchGrantUserVO;
	}

	public void setSearchGrantUserVO(Jbpm4GrantUser searchGrantUserVO) {
		this.searchGrantUserVO = searchGrantUserVO;
	}

}
