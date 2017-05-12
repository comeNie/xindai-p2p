package com.qfw.platform.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qfw.platform.service.IHtmlService;

/**
 * 生成静态页面
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/platform")
public class BuildHtmlController {

	@Resource(name = "htmlService")
	private IHtmlService htmlService;

	/**
	 * 生成所有的静态页面
	 */
	@RequestMapping(value = "/buildAll")
	public void buildAll(HttpServletRequest request,
			HttpServletResponse response) {
		htmlService.indexBuildHtml();
	}
}