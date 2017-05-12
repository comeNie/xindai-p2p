package com.qfw.common.bizservice;

import java.util.List;

import com.qfw.common.exception.BizException;
import com.qfw.common.model.SysSeq;

public interface ISeqBS {
	
	/**
	 * 获得封装好的数据
	 * @param seqName
	 * @return
	 */
	public String getResultNum(String seqName) throws BizException;

	/**
	 * 获取批次编号
	 * @param seqName
	 * @param count
	 * @return
	 * @throws BizException
	 */
	public List<String> getBatchResultNum(String seqName, int count) throws BizException; 

}
