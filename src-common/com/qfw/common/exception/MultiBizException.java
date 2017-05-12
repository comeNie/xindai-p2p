package com.qfw.common.exception;

import java.util.List;



public class MultiBizException extends BizException{

  private List msgList = null;//异常ID集合

  /**
   * 必须指定异常类型,所以不能用无参数的构造器
   */
  private MultiBizException(){

  }
  /**
   * 通过异常代号ｌｉｓｔ构造函数
   * @param msg ArrayList
   */
  public MultiBizException(List msg){
    super();
    this.msgList = msg;
  }
  /**
   * 得到异常代号ｌｉｓｔ
   * @return ArrayList
   */
  public List getExceptionList(){
    return this.msgList;
  }

}
