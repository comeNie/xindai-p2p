package com.qfw.platform.utils;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 可逆加密
 * 
 * @author Administrator
 * 
 */
public class ApiPass {

	// 这两个key我们内部约定
	public static byte[] iv = new byte[] { 82, 98, 50, 3, -16, 124, -40, -114,
			-87, -40, 37, 23, -56, 23, -33, 75 };
	public static byte[] key1 = new byte[] { -42, 23, 67, -86, 19, 29, -11, 84,
			94, 111, 75, -104, 71, 88, 86, -21 };

	private static ApiPass aes = null;

	private ApiPass() {

	}

	public static synchronized ApiPass getInstance() {
		if (aes == null) {
			aes = new ApiPass();
		}
		return aes;
	}

	public String encrypt(String msg) throws Exception {
		String str = "";
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		SecretKey key = new SecretKeySpec(key1, "AES");
		Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
		str = asHex(ecipher.doFinal(msg.getBytes()));
		return str;
	}

	public String decrypt(String value) throws Exception {
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
		SecretKey key = new SecretKeySpec(key1, "AES");
		Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
		return new String(dcipher.doFinal(asBin(value)));
	}

	private String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");
			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}
		return strbuf.toString();
	}

	private byte[] asBin(String src) {
		if (src.length() < 1)
			return null;
		byte[] encrypted = new byte[src.length() / 2];
		for (int i = 0; i < src.length() / 2; i++) {
			int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);

			encrypted[i] = (byte) (high * 16 + low);
		}
		return encrypted;
	}
}
