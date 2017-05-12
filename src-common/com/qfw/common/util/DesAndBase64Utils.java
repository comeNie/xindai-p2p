package com.qfw.common.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.codehaus.xfire.util.Base64;

public class DesAndBase64Utils {

	public static final String ALGORITHM_DES = "DES/CBC/PKCS5Padding";
	public static final String ENCODE_KEY = "12345678";
	public static byte[] decode(String key, byte[] data) throws Exception {
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key 的长度不能够小于 8 位字节
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
			IvParameterSpec iv = new IvParameterSpec(ENCODE_KEY.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			return cipher.doFinal(data);
		} catch (Exception e) {
//			 
			throw new Exception(e);
		}
	}
	
	public static String encode(String key, byte[] data) throws Exception {
		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		// key的长度不能够小于8位字节
		Key secretKey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM_DES);
		IvParameterSpec iv = new IvParameterSpec(ENCODE_KEY.getBytes());// 向量
		AlgorithmParameterSpec paramSpec = iv;
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
		byte[] bytes = cipher.doFinal(data);
		return Base64.encode(bytes);
	}
	
}
