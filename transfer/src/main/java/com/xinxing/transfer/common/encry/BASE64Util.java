package com.xinxing.transfer.common.encry;

import org.apache.commons.codec.binary.Base64;

public class BASE64Util {
    
	/**
	 * base64 解码码
	 * @param bytes
	 * @return
	 */
   public static byte[] decode(final byte[] bytes) {  
       return Base64.decodeBase64(bytes);  
   }  
 
   /** 
    * 二进制数据编码为BASE64字符串 
    * 
    * @param bytes 
    * @return 
    * @throws Exception 
    */  
   public static String encode(final byte[] bytes) {  
       return new String(Base64.encodeBase64(bytes));  
   } 

}
