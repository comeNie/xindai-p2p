package com.qfw.platform.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qfw.common.exception.BizException;

@Controller
@RequestMapping(value = "/pmClassroomAction")
public class PMClassroomAction extends BaseAction {
   
	@RequestMapping(value = "/pmClassroom")
	public String pmClassroom(HttpServletRequest request,
			HttpServletResponse response) throws BizException{
		 setCommonData(request);
		return getResultPath(PM_CLASSROOM);
	}
	
}
