package com.qfw.bizservice.bussparams;

import java.util.List;

import com.qfw.common.bizservice.IBaseService;
import com.qfw.common.exception.BizException;
import com.qfw.jbpm.model.bo.Jbpm4GrantUser;

public interface IGrantUserBS extends IBaseService {
	
	public List<Jbpm4GrantUser> findGrantUser (Jbpm4GrantUser searchGrantUserVO) throws BizException;
	
	public List<Jbpm4GrantUser> findGrantUserPagesByVO(
			Jbpm4GrantUser searchGrantUserVO, int first, int pageSize);
	
	public int findGrantUserCountByVO(Jbpm4GrantUser searchGrantUserVO);
	
	public Jbpm4GrantUser findGrantUserById(Integer id) throws BizException;
	
	public Jbpm4GrantUser getJbpm4GrantUserById(Integer id) throws BizException;
}
