/*

 * Id: BizException.java

 * Type Name: com.ccb.cclbm.common.BizException

 * Create Date: 2005-3-15

 * Author: robert.luo

 * 

 *

 * Project: CCLBM

 *

 *

 *

 */

package com.qfw.common.exception;

import java.io.Serializable;
import java.util.MissingResourceException;

import javax.faces.context.FacesContext;

import com.qfw.common.util.SysMessage;


public class BizException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	protected SysMessage msg;

	public BizException() {

		super();

	}


	public BizException(String message) {

		super(message);

		this.msg = new SysMessage(message);

	}

	/**
	 * @param message
	 * @param cause
	 */

	public BizException(String message, Throwable cause) {

		super(message, cause);

		this.msg = new SysMessage(message);

	}

	/**
	 * @param cause
	 */

	public BizException(Throwable cause) {

		super(cause);

	}

	/**
	 * @param message
	 */

	public BizException(SysMessage msg) {

		super();

		this.msg = msg;

	}

	/**
	 * zhangfeng V1.2
	 * @param msg
	 * @param message
	 * @param cause
	 */

	public BizException(SysMessage msg, String message, Throwable cause) {

		super(message, cause);

		this.msg = msg;

	}

	/**
	 * @param message
	 * @param cause
	 */

	public BizException(SysMessage msg, Throwable cause) {

		super(cause);

		this.msg = msg;

	}

	/**
	 * 通过异常代号和异常代号的参数构造函数
	 * @param id String
	 * @param parm String
	 */

	public BizException(String id, String parm) {

		Object[] parms = { parm };

		this.msg = new SysMessage(id, parms);

	}

	/**
	 * 通过异常代号和异常代号的参数数组构造函数
	 * @param id String
	 * @param parm Object[]
	 */

	public BizException(String id, Object[] parm) {

		this.msg = new SysMessage(id, parm);

	}

	public BizException(String errCode, String errDesc, BizException e) {
		
		super(errDesc, e.fillInStackTrace());
		
		this.msg = e.getSysMsg();
	}

	
	/**
	 * 得到异常代号
	 * @return String
	 */

	public String getExceptionID() {

		if (this.msg != null)

			return this.msg.getKey();

		return "";

	}

	public SysMessage getSysMsg() {

		return this.msg;

	}

	/**
	 * 得到异常代号的参数
	 * @return String
	 */

	public String getExceptionParm() {

		if (this.msg != null && this.msg.getParams().length > 0)

			return (String) this.msg.getParams()[0];

		return null;

	}

	/**
	 * 得到异常代号的参数数组
	 * @return Object[]
	 */

	public Object[] getExceptionParms() {

		if (this.msg != null)

			return this.msg.getParams();

		return null;

	}

	/**
	 * 如果异常使用了带参多语言，则将其转换为实际的多语言消息，否则不变
	 * @return String
	 */
	public String getMessage() {
		if (this.getSysMsg() != null) {
			String id = this.getSysMsg().getKey();
			Object[] obj = this.getSysMsg().getParams();
			if (id != null) {
				FacesContext fc = FacesContext.getCurrentInstance();
				if (fc != null) {
					String bundlename = fc.getExternalContext().getInitParameter("exceptionMessageDir");
					if (bundlename == null) {
						// logger.error("初始化参数尚未定义，请在Web.xml中定义初始化参数：exceptionMessageDir。");
						return "初始化参数尚未定义，请在Web.xml中定义初始化参数：exceptionMessageDir。";
					}

					String message = null;
					try {
						ExceptionMsg exceptionMsg = new ExceptionMsg();
						message = exceptionMsg.getString(bundlename, id, obj);
					} catch (MissingResourceException e) {
						// logger.error("请检查Web.xml中定义初始化参数exceptionMessageDir所对应的文件" + bundlename
						// + ".properties是否存在。");
						// facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mainMsg,
						// null);
						// FacesContext.getCurrentInstance().addMessage(null,facesMessage);
						return "请检查Web.xml中定义初始化参数exceptionMessageDir所对应的文件" + bundlename
								+ ".properties是否存在。";
					}
					// if (message == null) {
					// message = getString(bundlename, id);
					// }
					if (message == null) {
						// logger.error(id + "的配置有误，在配置文件" + bundlename + "中不存在。");
						return id;
					}
					return message;
				} else { // 未得到FacesContext，手工拼装多语言，多语言参数加在多语言代号后面
					StringBuffer buff = new StringBuffer(200); // 消息缓存，在这里拼装字符串
					buff.append(id); // 多语言代号
					if (obj != null) { // 这个是多语言的参数，不为空的时候拼装
						for (int i = 0, n = obj.length; i < n; ++i) {
							buff.append("|"); // 使用'|'分隔多语言代号和参数
							buff.append(obj[i]); // 一个一个地拼接多语言参数
						}
					}
					return buff.toString();
				} // end of if (fc != null) { } else
			} // end of if (id != null) { }
		} // end of if (this.getSysMsg() != null) { }
		// if (this.getSysMsg() == null || this.getSysMsg().getKey()==null) 就返回父类处理结果
		return super.getMessage();
	}

	public SysMessage getMsg() {
		return msg;
	}

	public void setMsg(SysMessage msg) {
		this.msg = msg;
	}

	
	public  String getDecision(){
		return "类名["+getClassName()+"]-方法名["+getFunctionName()+"]";
	}
	
	public  String getClassName(){
		if(this.getStackTrace()!=null && this.getStackTrace().length>=3){
			//方法1：通过Throwable的方法getStackTrace()  
			return this.getStackTrace()[2].getClassName();  
		}else if(Thread.currentThread().getStackTrace().length>=4){
			//方法2：通过Thread的方法getStackTrace()  
			return Thread.currentThread().getStackTrace()[3].getClassName();  
		}else{
			return "";
		}
	}
	
	public  String getFunctionName(){
		if(this.getStackTrace()!=null && this.getStackTrace().length>=3){
			// 方法1：通过Throwable的方法getStackTrace()
	        return  this.getStackTrace()[2].getMethodName();  
		}else if(Thread.currentThread().getStackTrace().length>=4){
			 //方法2：通过Thread的方法getStackTrace()  
		    return  Thread.currentThread().getStackTrace()[3].getMethodName();  
		}else{
			return "";
		}
	}
	
}
