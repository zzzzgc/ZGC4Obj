package com.xinxing.o.boss.business.provider.other.xcheng.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
/**
 * 此Class为上游提供,用于xcheng接口
 */
public class AESUtil {
	static Logger log = Logger.getLogger(AESUtil.class);

	public final static String DEFAULT_CHARSET = "UTF-8";

	 /**
	*AES加密
	*
	*@param content
	*@param password
	*@return
	*@throws Exception 
	*/
	 public static String encrypt(String content, String password) throws Exception{
	    String secretMd5 = MD5Util.strToMd5(password, DEFAULT_CHARSET); 
	    password = MD5Util.strToMd5(password + secretMd5, DEFAULT_CHARSET);
	    SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
	    Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	    byte[] byteContent = content.getBytes(DEFAULT_CHARSET); 
	    cipher.init(Cipher.ENCRYPT_MODE, key); 
	    byte[] result = cipher.doFinal(byteContent); 
	    System.out.println(byteContent.length);
	    if ((result != null) && (result.length > 0)){
	       return new String(Base64.encodeBase64(result)); 
	    } 
	    return null;
	 }
	 /**
	 * AES解密
	 * 
	 * @param content 
	 * @param password 
	 * @return 
	 * @throws Exception 
	 */ 
	 public static String decrypt(String content, String password) throws Exception{ 
		 String secretMd5 = MD5Util.strToMd5(password, DEFAULT_CHARSET); 
		 password = MD5Util.strToMd5(password + secretMd5, DEFAULT_CHARSET); 
		 SecretKeySpec key = new SecretKeySpec(password.getBytes(),"AES");
		 Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding"); 
		 cipher.init(Cipher.DECRYPT_MODE,key);
		 byte[] result = cipher.doFinal(Base64.decodeBase64(content));
		 if ((result != null) && (result.length > 0)) {
			 log.debug("[AESUtils][decrypt][result.length]:" + result.length);
			 log.debug("[AESUtils][decrypt][result]:" + new String(result, DEFAULT_CHARSET));
			 return new String(result, DEFAULT_CHARSET);
		 }else{
			log.debug("[AESUtils][decrypt][result] is null");
			return null;
		}
	 }
}