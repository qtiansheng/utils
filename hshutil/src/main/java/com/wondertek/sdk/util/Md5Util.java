package com.wondertek.sdk.util;

import java.security.MessageDigest;

/**
 * md5通用加密
 * Created by wondertek on 2018/08/24.
 */
public class Md5Util
{

	/**
	 * md5通用加密
	 * @param plainStr 待加密字符串
	 * @return
	 */
	public static String md5CodeCommon(String plainStr) {
		byte[] source = plainStr.getBytes();
		String s = null;
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f'};
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();

			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}


}
