package com.wondertek.sdk.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;


/**
 * RSA
 * Created by wondertek on 2018/08/24.
 */

public class RSAUtil {

    public static final String ENCRYPTION_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    
//    static{
//    	System.loadLibrary("lqsd");
//    }
//
//	public static native byte[] encrypt(String data);

    /**
     * 生成RSA的公钥和私钥
     */
    public static Map<String, Object> initKey() throws Exception{
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);  //512-65536 & 64的倍数
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put("PublicKey", publicKey);
        keyMap.put("PrivateKey", privateKey);

        return keyMap;
    }

    /**
     * 获得公钥
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("PublicKey");
        
        return Base64.encode(key.getEncoded());
    }

    /**
     * 获得私钥
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get("PrivateKey");
        return Base64.encode(key.getEncoded());
    }


    /**
     * 公钥加密
     */
    public static byte[] encrypt(byte[] data, String keyString, String algorithm)  {
    	
    	
    	byte[] keyBytes = Base64.decode(keyString);
        
        
    	try{
    		KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
    		
    		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
    		Key key = keyFactory.generatePublic(x509KeySpec);
        
	        //Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
			Cipher cipher = Cipher.getInstance(algorithm);
	
	        cipher.init(Cipher.ENCRYPT_MODE, key);
	
	        return cipher.doFinal(data);
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    		return null;
    	}
    }

    /**
     * 私钥解密
     */
    public static byte[] decrypt(byte[] data, String keyString, boolean isPublic) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, isPublic);
        KeyFactory keyFactory = RSAUtil.getKeyFactory(keyAndFactoryMap);
        Key key = RSAUtil.getKey(keyAndFactoryMap);

        //Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        return cipher.doFinal(data);
    }

    /**
     * 生成钥匙
     */
    public static Map<String, Object> generateKeyAndFactory(String keyString, boolean isPublic) throws Exception {
        byte[] keyBytes = Base64.decode(keyString);
        
        KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        Key key = null;
        if (isPublic) {
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            key = keyFactory.generatePublic(x509KeySpec);
        } else {
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            key = keyFactory.generatePrivate(pkcs8KeySpec);
        }
        
        Map<String, Object> keyAndFactoryMap = new HashMap<String, Object>(2);
        keyAndFactoryMap.put("key", key);
        keyAndFactoryMap.put("keyFactory", keyFactory);
        
        return keyAndFactoryMap;
    }

    /**
     * 从指定对象中获取钥匙
     */
    public static Key getKey(Map<String, Object> map) {
        if (map.get("key") == null) {
            return null;
        }
        return (Key)map.get("key");
    }

    /**
     * 从指定对象中获取钥匙工厂
     */
    public static KeyFactory getKeyFactory(Map<String, Object> map) {
        if (map.get("keyFactory") == null) {
            return null;
        }
        return (KeyFactory)map.get("keyFactory");
    }

    /**
     * 对信息生成数字签名（用私钥）
     */
    public static String sign(byte[] data, String keyString) throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, false);
        Key key = RSAUtil.getKey(keyAndFactoryMap);
        
        PrivateKey privateKey = (PrivateKey)key;

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(data);

        return Base64.encode(signature.sign());
    }

    /**
     * 校验数字签名（用公钥）
     */
   /* public static boolean verify(byte[] data, String keyString, String sign)
            throws Exception {
        Map<String, Object> keyAndFactoryMap = RSAUtil.generateKeyAndFactory(keyString, true);
        Key key = RSAUtil.getKey(keyAndFactoryMap);

        PublicKey publicKey = (PublicKey)key;

        // 取公钥匙对象
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(Base64Util.decryptBASE64(sign));
    }*/


}