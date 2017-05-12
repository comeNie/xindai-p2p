package com.qfw.platform.utils;

import java.security.MessageDigest;

public class MsgDigest {

	public final static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes("UTF-8"));
			return getMd5String(md.digest());
		} catch (Exception e) {
			return null;
		}
	}

	private static String getMd5String(byte[] bytes) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		int j = bytes.length;
		char str[] = new char[j * 2];
		for (int i = 0, k = 0; i < j; i++) {
			byte byte0 = bytes[i];
			str[k++] = hexDigits[byte0 >>> 4 & 0xf];
			str[k++] = hexDigits[byte0 & 0xf];
		}

		return new String(str);
	}
}