package com.xinxing.o.boss.business.provider.other.hquan.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Hex;

import com.xinxing.o.boss.business.provider.other.hquan.util.RSAUtil.MD5Util;
import com.xinxing.o.boss.common.encry.MD5_HexUtil;
import com.xinxing.o.boss.common.resource.Constants;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class RSAUtil {

    public static final String RSA = "RSA";

    /**
     * 生成RSA密钥
     * 
     * @return
     * @throws Exception
     */
    public static RSADTO generateRSA() throws Exception {
        RSADTO rsa = null;

        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            keyPairGenerator.initialize(1024);

            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            PrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            rsa = new RSADTO();
            String publicString = keyToString(publicKey);
            String privateString = keyToString(privateKey);
            rsa.setPublicKey(publicString);
            rsa.setPrivateKey(privateString);

        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return rsa;
    }

    /**
     * BASE64 String 转换为 PublicKey
     * 
     * @param publicKeyString
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyString) throws Exception {
        PublicKey publicKey = null;
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            byte[] keyByteArray = base64Decoder.decodeBuffer(publicKeyString);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyByteArray);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            publicKey = keyFactory.generatePublic(x509KeySpec);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return publicKey;
    }

    /**
     * BASE64 String 转换为 PrivateKey
     * 
     * @param privateKeyString
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateKeyString) throws Exception {
        PrivateKey privateKey = null;
        BASE64Decoder base64Decoder = new BASE64Decoder();

        try {
            byte[] keyByteArray = base64Decoder.decodeBuffer(privateKeyString);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyByteArray);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }

        return privateKey;

    }

    /**
     * RSA 加密返回byte[]
     * 
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encodeBytePrivate(byte[] content, PrivateKey privateKey) throws Exception {
        byte[] encodeContent = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            encodeContent = cipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return encodeContent;
    }

    /**
     * RSA 加密返回 Hex
     * 
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String encodeStringPrivate(byte[] content, PrivateKey privateKey) throws Exception {
        byte[] encodeContent = encodeBytePrivate(content, privateKey);
        String stringContent = Hex.encodeHexString(encodeContent);
        return stringContent;
    }

    /**
     * 解密返回byte[]
     * 
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decodeBytePublic(byte[] content, PublicKey publicKey) throws Exception {
        byte[] decodeContent = null;
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            decodeContent = cipher.doFinal(content);
        } catch (NoSuchAlgorithmException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (NoSuchPaddingException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (InvalidKeyException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (IllegalBlockSizeException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        } catch (BadPaddingException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return decodeContent;
    }

    /**
     * 解密 Hex字符串
     * 
     * @param content
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decodeStringPublic(String content, PublicKey publicKey) throws Exception {
        byte[] decodeContent = null;
        try {
            byte[] sourceByteArray = Hex.decodeHex(content.toCharArray());
            decodeContent = decodeBytePublic(sourceByteArray, publicKey);
        } catch (IOException e) {
        	e.printStackTrace();
            throw new Exception(e.getMessage());
        }
        return decodeContent;
    }

    /**
     * 将Key转为String
     * 
     * @param key
     * @return
     */
    private static String keyToString(Key key) {
        byte[] keyByteArray = key.getEncoded();
        BASE64Encoder base64 = new BASE64Encoder();
        String keyString = base64.encode(keyByteArray);
        return keyString;
    }
    
    public static class RSADTO {
        /**
         * 公钥
         */
        private String publicKey;

        /**
         * 私钥
         */
        private String privateKey;

		public String getPublicKey() {
			return publicKey;
		}

		public void setPublicKey(String publicKey) {
			this.publicKey = publicKey;
		}
		public String getPrivateKey() {
			return privateKey;
		}

		public void setPrivateKey(String privateKey) {
			this.privateKey = privateKey;
		}
    }
    public static class MD5Util {
        /**
         * MD5加密
         *
         * @param s
         * @return
         */
        public static String MD5(String s) {
            char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
            try {
                byte[] btInput = s.getBytes("utf8");
                // 获得MD5摘要算法的 MessageDigest 对象
                MessageDigest mdInst = MessageDigest.getInstance("MD5");
                // 使用指定的字节更新摘要
                mdInst.update(btInput);
                // 获得密文
                byte[] md = mdInst.digest();
                // 把密文转换成十六进制的字符串形式
                int j = md.length;
                char str[] = new char[j * 2];
                int k = 0;
                for (int i = 0; i < j; i++) {
                    byte byte0 = md[i];
                    str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                    str[k++] = hexDigits[byte0 & 0xf];
                }
                return new String(str);
            } catch (Exception e) {
            	e.printStackTrace();
                return null;
            }
        }
    }
    

    public static void main(String[] args) throws Exception {
      	/*RSADTO dto = generateRSA();
    		System.out.println("私钥 :" + dto.getPrivateKey());
    		System.out.println("公钥 :" + dto.getPublicKey());
    		
    		String paramStr = "a=1&b=2";
    		String privateKey = dto.getPrivateKey();
    		String sign = RSAUtil.encodeStringPrivate(MD5Util.MD5(paramStr).getBytes(), RSAUtil.getPrivateKey(privateKey));
    		System.out.println(sign);*/
    		
    		
    		String paramStr = "tjm=233&lily=888";
    		String privateKeyString = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIvyhvd0j0eYiTrPJw72YtEif1RfypSkk0nB2yt1ne+ulnhS82TuNjlXTzrWhHm20zH5BVQYrg4skew0DlzttIrN+8fpICvcjHg7NvNW17u40RUM5MKmlQKcu2rgMwm5Oc0Gtznrb+oLpnMzCAv7C1xZJrr/6gGnBDETJZFKn9etAgMBAAECgYBaMvbKh1XRz36jaODDxAnswU9QEm1/YJON9mRMnoIs+VoCM3SsmNxaGOv4AEtiJ3P3wYitqzqD6DYlB2g6fPrffdzbr61A/4yIAluAly2vdBOQ7EtqxyG/8cSy3QT1XcJP46NMC8dpRVREIBgsQxeZ/N5DxXp11ipgjjr5jfAsAQJBAPvnX7XWMoUnGNYE6aEF/hjVjOSTRFmsHCqQ4j3nJNuB5x9nNIP+H5ELxgakkMaACCQs7PJgmdMs6uwhSsk5ZJcCQQCOORnF2luXUGGh4JDF1GpqDJF3qWbqUAh2BEyyP8IibF93rl/h5XH3INNBjOjPSNNOn2zd1p8xgykbNqcIhFpbAkBBVMeg3FWr7PDnCM0i4u/8tt3cpkkSzK1daMgUb+9CMtWjclvPY8gCEUChlmqp4Ki74R7u2ZjriPnAlf3UZFsvAkB+wSNYM9FXnXs2U7eTqUoiNAtd06v2ftfbnt9gKfTyyxWWy+GGqgCVWTsO++/t9iDlvK7BU+vTq+rx5xadP6BrAkEAvFYSK/V7B8rQymC/4nXEh9CNR0vd7zL9F02/4ZMuZpE3pw2C9CzvPqIJ4aXWAlzC7nfnzpLZ8M+4FPm8nb3rvA==";
    		String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCL8ob3dI9HmIk6zycO9mLRIn9UX8qUpJNJwdsrdZ3vrpZ4UvNk7jY5V0861oR5ttMx+QVUGK4OLJHsNA5c7bSKzfvH6SAr3Ix4OzbzVte7uNEVDOTCppUCnLtq4DMJuTnNBrc562/qC6ZzMwgL+wtcWSa6/+oBpwQxEyWRSp/XrQIDAQAB";
    		String oldSign = "486ecc58cfd182e4eff105578449a457af6083d74eb34273fdeec647aa1bc6c9b21b2f56b91072c0117a78678c5444593a5ef08ee12a7e3a328dfe54da168311c600e7c5decffd08dc656fb81d239c24c250547253e046f33ad89d15b186de9de0be476ba165a558e21635ed9f35b22f9cc5091b0512ffc2a9f7a4718bdf104a";
    		String newSign = RSAUtil.encodeStringPrivate(MD5Util.MD5(paramStr).getBytes(), RSAUtil.getPrivateKey(privateKeyString));

    		System.out.println("0--------"+oldSign);
    		System.out.println("1--------"+newSign);
    		PublicKey publicKey = getPublicKey(publicKeyString);
    		byte[] bytes = decodeStringPublic(newSign,publicKey);
    		String str = new String(bytes);
    		System.out.println("2--------"+str);
    		System.out.println("3--------"+MD5_HexUtil.md5Hex(paramStr));
		
	}

}
