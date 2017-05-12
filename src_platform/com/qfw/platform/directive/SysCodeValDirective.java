package com.qfw.platform.directive;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.qfw.platform.cache.CacheManager;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 根据Code获取对应的value标签
 * 
 * @author Administrator
 * 
 */
public class SysCodeValDirective implements TemplateDirectiveModel {

	@Resource(name = "cacheManager")
	private CacheManager cacheManager;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		String codeType = params.get("codeType").toString();
		String codeValue = params.get("codeValue").toString();

		String displayValue = cacheManager.getDisPlayNameByCodeTypeAndVal(
				codeType, codeValue);
		if (!StringUtils.isEmpty(displayValue)) {
			env.getOut().write(displayValue);
		}
	}
}