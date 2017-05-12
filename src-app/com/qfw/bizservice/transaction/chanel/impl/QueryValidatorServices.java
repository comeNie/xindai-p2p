package com.qfw.bizservice.transaction.chanel.impl;

import javax.jws.WebService;

@WebService(targetNamespace="http://zcxyws.ztzx.com/")
public interface QueryValidatorServices extends java.rmi.Remote {
	public java.lang.String querySingle(java.lang.String userName_, java.lang.String password_, java.lang.String type_, java.lang.String param_) throws java.rmi.RemoteException;
	public java.lang.String queryBatch(java.lang.String userName_, java.lang.String password_, java.lang.String type_, java.lang.String param_) throws java.rmi.RemoteException;
	}

