package com.qfw.common.tag;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.converter.BooleanConverter;
import com.qfw.common.converter.SelectItemConverter;
import com.qfw.common.converter.StringValueConverter;
import com.qfw.common.dao.ICommonQuery;
import com.qfw.common.util.ApplicationContextUtil;
import com.qfw.common.util.CollectionUtil;
import com.qfw.common.util.web.SelectItemApp;
import com.qfw.common.util.web.SelectItemUtil;
import com.qfw.common.util.web.ViewOper;
import com.qfw.common.validator.AmtValidator;

public class TagFunction {
	
	private static Logger log = Logger.getLogger(TagFunction.class);
	public static Object getSelectItems(final String codeType) {
		Map<String, List<SelectItem>> selectItems = SelectItemApp.selectItems;
		List<SelectItem> list = selectItems.get(codeType);
		if(list == null || list.isEmpty()){	
			ICodeDictBS codeDictBS = (ICodeDictBS)ApplicationContextUtil.getContext().getBean("codeDictBS");
			list = codeDictBS.getSelectItems(codeType);
			selectItems.put(codeType, list);
		}		
		return list;
	}	
	public static Object getSelectItemConverter(final String codeType) {
		return new SelectItemConverter(codeType);
	}
	public static Object getStringValueConverter(final String value){
		return new StringValueConverter(value);
	}
	public static Object amtValidate(final String label,final int len){
		return new AmtValidator(label, len); 
	}
	public static Object getBooleanConverter(final String trueValue,final String falseValue){
		return new BooleanConverter(trueValue, falseValue);
	}
	public static Object findSelectItems(final String sql){
		log.debug(sql);
		Map<String, List<SelectItem>> selectItems = SelectItemApp.selectItems;
		List<SelectItem> list = selectItems.get(sql);
		if(list == null || list.isEmpty()){	
			JdbcTemplate jdbcTemplate = (JdbcTemplate)ApplicationContextUtil.getContext().getBean("jdbcTemplate");
			List<Map<String, Object>> valueList = jdbcTemplate.queryForList(sql);//(sql.replaceAll("@\\|@", "'"));
			String valueFlag = "VALUE";
			String lableFlag = "LABEL";
			if(valueList != null && !valueList.isEmpty()){
				list = new ArrayList<SelectItem>();
				for (Map<String, Object> map : valueList) {
					list.add(new SelectItem(map.get(valueFlag), String.valueOf(map.get(lableFlag))));
				}
				
			}
			selectItems.put(sql, list);
		}		
		return list;
	}
	public static Object findSelectItemsNotCache(final String sql){
		log.debug(sql);
		List list = new ArrayList<SelectItem>();
		JdbcTemplate jdbcTemplate = (JdbcTemplate)ApplicationContextUtil.getContext().getBean("jdbcTemplate");
		List<Map<String, Object>> valueList = jdbcTemplate.queryForList(sql);//(sql.replaceAll("@\\|@", "'"));
		String valueFlag = "VALUE";
		String lableFlag = "LABEL";
		if(valueList != null && !valueList.isEmpty()){
			for (Map<String, Object> map : valueList) {
				list.add(new SelectItem(map.get(valueFlag), String.valueOf(map.get(lableFlag))));
			}
			
		}	
		return list;
	}
	/**
	 * 获取list大小
	 * @param list
	 * @return
	 */
	public static int getListSize(List list){
		if(list!=null && !list.isEmpty()){
			return list.size();
		}
		return 0;
	}
	public static boolean hasFun(String funCode){
		return ViewOper.hasAccess(funCode);
	}
}
