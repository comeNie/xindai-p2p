package com.qfw.common.bizservice.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.qfw.common.bizservice.ISeqBS;
import com.qfw.common.exception.BizException;
import com.qfw.common.util.StringUtils;

@Service("seqBS")
public class SeqBSImpl implements ISeqBS {

	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
	public String getResultNum(String seqName) throws BizException {
		try {
			Map<String, Object > map= this.getSeqInfo(seqName);
			if(null == map){
				return null;
			}else{
				String result = this.installNum(map);
				String str = "UPDATE SYS_SEQ S SET S.SEQ_VALUE = S.SEQ_VALUE +1 WHERE S.SEQ_NAME = '"+seqName+"'";
				jdbcTemplate.execute(str);
				return result;
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
	public List<String> getBatchResultNum(String seqName,int count) throws BizException {
		try {
			Map<String, Object > map= this.getSeqInfo(seqName);
			if(null == map){
				return null;
			}else{
				List<String> result = new ArrayList<String>();
				Integer num = (Integer) map.get("SEQ_VALUE");
				for(int i = 0;i<count;i++){
					result.add(this.installNum(map));
					map.put("SEQ_VALUE", num+i+1);
				}
				
				String str = "UPDATE SYS_SEQ S SET S.SEQ_VALUE = S.SEQ_VALUE +"+count+" WHERE S.SEQ_NAME = '"+seqName+"'";
				jdbcTemplate.execute(str);
				return result;
			}
		} catch (Exception e) {
			throw new BizException(e);
		}
	}

	/**
	 * 锁表查询参数
	 * @param seqName
	 * @return
	 * @throws BizException
	 */
	private Map<String, Object> getSeqInfo(String seqName) throws BizException {
		if(StringUtils.isNotEmpty(seqName)){
			String sql =  "SELECT * FROM  SYS_SEQ S WHERE S.SEQ_NAME = ? for update";
			List<Map<String, Object>> list =  jdbcTemplate.queryForList(sql, seqName);
			if(null == list || list.isEmpty()){
				return null;
			}else if(list.size() > 1){
				throw new BizException("序列参数表对应的个数大于1，请维护正确数据！");
			}else{
				return list.get(0);
			}
		}
		return null;
	}

	/**
	 * 
	 * @param seqName
	 */
	private String installNum(Map<String, Object> map) throws BizException{
		try {
			Integer num = (Integer) map.get("SEQ_VALUE");
			String prefix = StringUtils.convertNull((String) map.get("PREFIX"));
			String postfix = StringUtils.convertNull((String) map.get("POSTFIX"));
			Integer size = (Integer) map.get("SIZE");
			String result = "";
			String numTemp = num.toString();
			if(prefix.length()+numTemp.length()+postfix.length()>size){
				result = prefix+numTemp.substring((prefix.length()+numTemp.length()+postfix.length()-size), numTemp.length())+postfix;
				//throw new BizException("编号封装错误：封装后长度超出上限!");
			}else{
				numTemp = String.format("%0"+(size-prefix.length()-postfix.length())+"d", num);
				result = prefix+numTemp+postfix;
			}
			return result;
		} catch (Exception e) {
			throw new BizException(e);
		}
	}
	
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("SEQ_VALUE", 987654321);
		map.put("SIZE", 5);
	}
}
