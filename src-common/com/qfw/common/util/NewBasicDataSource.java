package com.qfw.common.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

public class NewBasicDataSource extends BasicDataSource{
	@Override
	 public synchronized void close() throws SQLException{
	  DriverManager.deregisterDriver(DriverManager.getDriver(url));
	  super.close();
	 }
}
