package com.qfw.bizservice.bussparams.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qfw.bizservice.bussparams.IParameterInfoBS;
import com.qfw.common.bizservice.impl.BaseServiceImpl;
import com.qfw.common.exception.BizException;
import com.qfw.common.log.LogFactory;
import com.qfw.common.model.SysParameter;
import com.qfw.common.util.StringUtils;
import com.qfw.dao.bussparams.IParameterInfoDAO;

@Service("parameterInfoBS")
public class ParameterInfoBSImpl extends BaseServiceImpl implements IParameterInfoBS {
	
	@Autowired
	private IParameterInfoDAO parameterInfoDAO;
	private Logger log = LogFactory.getInstance().getBusinessLogger();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysParameter> findParameterInfoPagesByVO (SysParameter searchParameterVO, 
			int first, int pageSize) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select s.PARAMETER_ID,s.PARAMETER_CODE,s.PARAMETER_VALUE,s.REMARK,");
			sb.append(" s.PARAMETER_TYPE_CD,s.PARAMETER_TYPE_NAME,s.PARAMETER_DISPLAY_NAME,s.PARAMETER_SORT");
			sb.append(" from SYS_PARAMETER s");
			sb.append(" where 1=1");
			if (StringUtils.isNotEmpty(searchParameterVO.getParameterCode())) {
				sb.append(" and s.PARAMETER_CODE like '%").append(searchParameterVO.getParameterCode()).append("%'");
			}
			if (StringUtils.isNotEmpty(searchParameterVO.getParameterValue())) {
				sb.append(" and s.PARAMETER_VALUE like '%").append(searchParameterVO.getParameterValue()).append("%'");
			}
			return commonQuery.findBySQLByPages(sb.toString(), first, pageSize, SysParameter.class);
		} catch (Exception e) {
			 
		}
		return null;
	}

	@Override
	public int findParameterInfoCountByVO (SysParameter searchParameterVO) {
		StringBuilder sb = new StringBuilder();
		sb.append("select count(1)");
		sb.append(" from SYS_PARAMETER s");
		sb.append(" where 1=1");
		if (StringUtils.isNotEmpty(searchParameterVO.getParameterCode())) {
			sb.append(" and s.PARAMETER_CODE like '%").append(searchParameterVO.getParameterCode()).append("%'");
		}
		if (StringUtils.isNotEmpty(searchParameterVO.getParameterValue())) {
			sb.append(" and s.PARAMETER_VALUE like '%").append(searchParameterVO.getParameterValue()).append("%'");
		}
		return this.commonQuery.findCountBySQL(sb, null);
	}
	
	@SuppressWarnings("unchecked")
	public List<SysParameter> checkParameterCode(String parameterCode,Integer id) throws BizException {
		if(parameterCode != null && !"".equals(parameterCode)){
			StringBuilder sb = new StringBuilder("from SysParameter where ");
			sb.append(" parameterCode = '").append(parameterCode).append("'");
			if(id!=null && !id.equals(Integer.valueOf(0))){
				sb.append(" and id != '").append(id).append("'");
			}
			List<SysParameter> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}
	
	}
	
	@SuppressWarnings("unchecked")
	public List<SysParameter> checkParameterValue(String parameterValue,Integer id)throws BizException {
		if(parameterValue != null && !"".equals(parameterValue)){
			StringBuilder sb = new StringBuilder("from SysParameter where ");
			sb.append(" parameterValue = '").append(parameterValue).append("'");
			if(id!=null && !id.equals(Integer.valueOf(0))){
				sb.append(" and id != '").append(id).append("'");
			}
			List<SysParameter> list = baseDAO.findByHQL(sb.toString());
			return list;
		}else{
			return null;
		}
	}
	
	public SysParameter findParameterInfoById(Integer id) throws BizException {
		this.parameterInfoDAO.findById(SysParameter.class, id);
		return null;
	}
	
	@Override
	public SysParameter getSysParameterById(Integer id) throws BizException{
		return (SysParameter) parameterInfoDAO.findById(SysParameter.class, id);
	}
	
	public IParameterInfoDAO getParameterInfoDAO() {
		return parameterInfoDAO;
	}

	public void setParameterInfoDAO(IParameterInfoDAO parameterInfoDAO) {
		this.parameterInfoDAO = parameterInfoDAO;
	}

	public Logger getLog() {
		return log;
	}

	public void setLog(Logger log) {
		this.log = log;
	}
	
}
