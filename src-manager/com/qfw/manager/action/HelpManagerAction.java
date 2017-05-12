package com.qfw.manager.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qfw.common.exception.BizException;


@Controller
@RequestMapping("/helpManager")
public class HelpManagerAction extends BaseAction{
	/**
	 * 帮助
	 * @author huangji
	 */
  @RequestMapping(value = "/help")  
  public String help(HttpServletRequest request,HttpServletResponse response)throws BizException{ 
	  setCommonData(request);
	  return getResultPath(HELP);
  }
  
  @RequestMapping(value = "/mcjs")
  public String mcjs(HttpServletRequest request,HttpServletResponse response)throws BizException{ 
	  setCommonData(request);
	  return getResultPath(MCJS);
  }
  
  @RequestMapping(value = "/ptjs")
  public String ptjs(HttpServletRequest request,HttpServletResponse response)throws BizException{ 
	  setCommonData(request);
	  return getResultPath(PTJS);
  }
  
  @RequestMapping(value = "/wyjk")
  public String wyjk(HttpServletRequest request,HttpServletResponse response)throws BizException{ 
	  setCommonData(request);
	  return getResultPath(WYJK);
  }
  
  @RequestMapping(value = "/wylc")
  public String wylc(HttpServletRequest request,HttpServletResponse response)throws BizException{ 
	  setCommonData(request);
	  return getResultPath(WYLC);
  }
  
  @RequestMapping(value = "/lxhfy")
  public String lxhfy(HttpServletRequest request,HttpServletResponse response)throws BizException{ 
	  setCommonData(request);
	  return getResultPath(LXHFY);   
  }
  
  @RequestMapping(value = "/zxzh")
  public String zxzh(HttpServletRequest request,HttpServletResponse response)throws BizException{ 
	  setCommonData(request);
	  return getResultPath(ZXZH);
  }
  
  
  
}
