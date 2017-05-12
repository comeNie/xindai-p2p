package com.qfw.dao.bussparams;

import java.util.List;

import com.qfw.common.dao.IBaseDAO;
import com.qfw.common.exception.BizException;
import com.qfw.jbpm.model.bo.Jbpm4GrantUser;

public interface IGrantUserDAO extends IBaseDAO {
	public List<Jbpm4GrantUser> findGrantUser(Jbpm4GrantUser searchGrantUserVO) throws BizException;
}
