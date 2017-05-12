package com.qfw.common.gateway;

import com.qfw.common.exception.BizException;

public interface ISenderBS {
	
	public void send(SenderInfo senderInfo) throws BizException;

}
