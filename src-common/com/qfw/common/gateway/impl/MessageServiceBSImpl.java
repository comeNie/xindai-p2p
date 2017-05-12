package com.qfw.common.gateway.impl;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.qfw.common.exception.BizException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("messageServiceBS")
public class MessageServiceBSImpl {
	
	private Logger log = Logger.getLogger(MessageServiceBSImpl.class);
	
	@Autowired
	@Qualifier("freeMarkerConfiguration")
	private Configuration configuration;
	//private String TEMPLATE_SUFFIX = ".ftl";
	
	/**
	 * 经对象按模板生产xml数据
	 * @param obj
	 * @param templateName
	 * @return
	 * @throws BizException
	 */
	public String generateXml(Object obj,String templateName) throws BizException{
		Template template = getTemplateByModel(templateName);
		StringWriter writer = new StringWriter();
		try {
			template.process(obj, writer);
			String xml = writer.toString();
			writer.close();
			return xml;
		} catch (TemplateException e) {
			log.error("合并模型时出错!", e);
			throw new BizException("合并模型时出错!");
		} catch (IOException e) {
			log.error("读取模板出错!", e);
			throw new BizException("读取模板出错!");
		}catch (Exception e) {
			throw new BizException("生成xml未知异常!");
		}
	}
	public Template getTemplateByModel(String templateName) {
		// 模板定义
		Template template = null;
		try {
			// 获取模板
			template = configuration.getTemplate(templateName);
		} catch (IOException e) {
			log.error("加载模板时发生错误!模板名称为:" + templateName, e);
		}
		return template;
	}
}
