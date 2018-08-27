package com.wondertek.sdk.util;

import java.io.UnsupportedEncodingException;

/**
 * 一种简单的可逆的加解密算法
 * 
 * Created by wondertek on 2018/08/24.
 * 
 */
public class EasyEncrypt {
	private static String key = "wirelesscity";
	private static String hexStr = "0123456789ABCDEF";

	/**
	 * 加密
	 * 
	 * @param src
	 * @return
	 */
	public static String encrypt(String src) {
		try {
			byte[] srcBytes = src.getBytes("UTF-8");
			byte[] keyBytes = key.getBytes("UTF-8");

			// 将src的每个字节的ascII码加i
			for (int i = 0; i < srcBytes.length; i++) {
				srcBytes[i] = (byte) (srcBytes[i] + i);
			}

			// 将密钥的每个字节的ascII码减i
			for (int i = 0; i < keyBytes.length; i++) {
				keyBytes[i] = (byte) (keyBytes[i] - i);

				// 针对加减后的byte，设置与密钥关联
				// srcBytes[i] = srcBytes[i] - keyBytes[i]
				// 如果src比较长，那么就不管超过密钥长度的字节了
				if (i < srcBytes.length) {
					srcBytes[i] = (byte) (srcBytes[i] - keyBytes[i]);
				}
			}

			// 将二进制以十六进制字符串形式返回
			return binaryToHexString(srcBytes);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * 
	 * @param src
	 * @return
	 */
	public static String decrypt(String src) {
		try {
			byte[] srcBytes = hexStringToBinary(src);
			byte[] keyBytes = key.getBytes("UTF-8");

			// 将密钥的每个字节的ascII码减i
			for (int i = 0; i < keyBytes.length; i++) {
				keyBytes[i] = (byte) (keyBytes[i] - i);

				// 针对加减后的byte，设置与密钥关联
				// srcBytes[i] = srcBytes[i] - keyBytes[i]
				// 如果src比较长，那么就不管超过密钥长度的字节了
				if (i < srcBytes.length) {
					srcBytes[i] = (byte) (srcBytes[i] + keyBytes[i]);
				}
			}

			// 将src的每个字节的ascII码减i
			for (int i = 0; i < srcBytes.length; i++) {
				srcBytes[i] = (byte) (srcBytes[i] - i);
			}

			return new String(srcBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param bytes
	 * @return 将二进制转换为十六进制字符输出
	 */
	private static String binaryToHexString(byte[] bytes) {
		String result = "";
		String hex = "";
		for (int i = 0; i < bytes.length; i++) {
			// 字节高4位
			hex = String.valueOf(hexStr.charAt((bytes[i] & 0xF0) >> 4));
			// 字节低4位
			hex += String.valueOf(hexStr.charAt(bytes[i] & 0x0F));
			result += hex;
		}
		return result;
	}

	/**
	 * 
	 * @param hexString
	 * @return 将十六进制转换为字节数组
	 */
	private static byte[] hexStringToBinary(String hexString) {
		// hexString的长度对2取整，作为bytes的长度
		int len = hexString.length() / 2;
		byte[] bytes = new byte[len];
		byte high = 0;// 字节高四位
		byte low = 0;// 字节低四位

		for (int i = 0; i < len; i++) {
			// 右移四位得到高位
			high = (byte) ((hexStr.indexOf(hexString.charAt(2 * i))) << 4);
			low = (byte) hexStr.indexOf(hexString.charAt(2 * i + 1));
			bytes[i] = (byte) (high | low);// 高地位做或运算
		}
		return bytes;
	}
	
}
