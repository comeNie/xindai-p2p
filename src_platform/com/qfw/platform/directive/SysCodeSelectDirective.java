package com.qfw.platform.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.qfw.common.bizservice.ICodeDictBS;
import com.qfw.common.model.SysCodeDict;
import com.qfw.platform.cache.CacheManager;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class SysCodeSelectDirective implements TemplateDirectiveModel{
	
	@Resource(name = "codeDictBS")
	private ICodeDictBS codeDictBS;
	
	@Resource(name = "cacheManager")
	private CacheManager cacheManager;
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		String codeType = params.get("codeType").toString();
		List<SysCodeDict> sysCodeDictList = cacheManager.getCodeList(codeType);
		if (null != sysCodeDictList && !sysCodeDictList.isEmpty()) {
			for (SysCodeDict sysCodeDict : sysCodeDictList) {
				env.getOut()
						.write("<option value=\"" + sysCodeDict.getCodeValue()
								+ "\">"+ sysCodeDict.getDisplayValue() + "</option>");
			}
		}
	}

}
