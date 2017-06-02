package com.xinxing.o.boss.business.provider.other.xcheng.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 * 此Class为上游提供,用于xcheng接口
 */
public class MD5Util{
  static Logger log = Logger.getLogger(MD5Util.class);
  public static String strToMd5(String str, String charSet) {
    String md5Str = null; 
    if (str != null && str.length() != 0)
    {
    try{
       MessageDigest md = MessageDigest.getInstance("MD5");
       md.update(str.getBytes(charSet));
       byte b[] = md.digest();
       int i;
       StringBuffer buf = new StringBuffer("");
       for (int offset = 0; offset < b.length; offset++)
       {
    	   i = b[offset];
    	   if (i < 0) 
    		   i+=256;
    	   if (i < 16) 
    		   buf.append("0");
    	   		buf.append(Integer.toHexString(i));
       }
       md5Str = buf.toString();

		}
    	catch (NoSuchAlgorithmException e)
		{
    		log.error("MD5 加密发生异常。加密串："+ str);
		}
		catch (UnsupportedEncodingException e2)
		{
			log.error("MD5 加密发生异常。加密串："+str);
		}
    }
    return md5Str;
  }
		
 /**
 * 拼接参数
 *
 *@param params
 *@return
 */ 
 public static String getSign(String... params) 
		{
		StringBuilder sb = new StringBuilder();
		 for (String param : params) 
		 { 
		 sb.append(param); 
		 } 
		  return sb.toString(); 
		 } 
		 
 /**
 *MD5加密调用
 */ 
 public static String getSignAndMD5(String... params){ 
	 String sign = getSign(params); 
	 return strToMd5(sign, "utf-8"); 
 		} 

	}