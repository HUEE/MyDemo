package com.example.hwj.mydemo.utils.encryption.RSA;


import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


public abstract class RSAUtils extends Coder {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    //解密数据
    public static final String STR_DATA_RSA_PRIVSTE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBANGmqwMsUxKYZSTYhhgXp1Zdx7OD1+755C7fBijf5cdRu5jpJQGeOSpi1vfWzRgU7VGRwMoF0XDwarUY8ZeMuTwpXCmP3amYENm+srMteck3i5C9vfYXyo/HC3LU57pZKj6Cg5ah2RoZ4hpKZAuSulX//vwW6eaE6T9UwK7IL8mDAgMBAAECgYBAB9dTMMZud3zss/TAhvjkt9+2RB4/LrMyXYtUEI3tQlk5anuS8vAise1V5JTfNgUFVK69BOnixIrSpLukvaYU25Ut81UJHKrtarmqz07nNLPOK/Cu9AUIldF6TI950qgu5GlimMwUHvdhg34MKIHYnelp2deAZm52KsXkoRB6gQJBAP/6tCx4Ix5E98aiH+eM6jAUnFlC/oiGa2fdD+nJiToPw6HmqS8yRRvFJfx+bql5O516r6wgSsRhPf8TuaY4iRUCQQDRqwF0jD89lOlDfCSnngC/fxp1RnDZSySkbJgmIf+Sfh44gDBWBQENhLLi4LTFDyhNnjAivP6eKrwMq7zEyn43AkBZDc9A6UrObRAGesC+vbd7orACHdyPgrQ7Hh07KHIlUPaujKCvWr3QO3ZLsXCuZQ7oapZTO6jHSU1YQHSF74VBAkASGEgP3uq2Tduzz0r6jFBUNov6AxJKLooDBrw6zkRYSDojdiy1v8jPedxWaD4xo5U+3aAZ32W6EMYZ8dhgw3kRAkEA67klUbgUo8C92MuVSUgWatL4kpffpuH3hnB6tmM9b6ki/Esi7AZwE2FBq/pBrBqKBcqkDHWOAxEB/H3zPxXKLQ==";
    //加密pw
    public static final String RSA_PUBLIC_KEY_FOR_DECODE = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCUj7gqMlna1GLOJt53wKO56XZl5L/tErNnoQmCdO3fRSiX6lQwKPx34/HtBZ03LGW59l7iWjm8Zbbj+ePC+n3MYxlfuptwZY9ni+3PFgIGINPIsZnl0qPX3VI+eelj1vYLnyPa2ElbpcYzvGDYZi9xPljthWk+6qgIwjOCK1K0NQIDAQAB";

    public static String RSA_PRIVSTE_KEY_FOR_IOS = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAJbkxpuowl7o/Y/8Di/d7oqVl/O7ED2ng4LJa+f8LjAZHJ02kdkL74TcPwAlVwvt3tm99cmHVfRLVcf56fBKuX+9fou0cZQ2ctiTBjEWQhvlVNtoxxFa7mAQqQ+f4REBnvwz7qfvwaUGkgw8PBQc6V24AhnN8kTwTTkppx++08yvAgMBAAECgYBklo1vJsirzFV7rmMmtaNW7vIkoAbrksewX+V2+D0yAwt4IfCH59T7lHbK+hlU8nUkYeV/OqCqAa5laMvyS13sK61S1567GPpw+YsOYJxEEfVRcHHGYq58J8rBOJ6CXjC3qfmfB4Fb5qTH3UpDsbTeZA7CtlObU/DVo5P1vRuUgQJBAMdTnokbto9I3pZa9hWwqg0LkxpLi15y4BPlJhsC6uGLS6KBx+65IISzlb4Q1MrrVfCKPbgpDiLGrvHz+zvPUDkCQQDBy93nrnL17uYiYcX5PV2CodyYJ9pvHfxFSq4a4OWwBU+P3uxHiQln8QkFWiqTtjaCzw9K9JD/nRQzPKyvizQnAkA2SiYx/eRZXmWR2jCQc8+9QdvNG6pYDT3O7WjVjpPe3ou5NVP5R17+BdPRMtBifHqpy11IHZaGDkt0Bxn0jmCxAkBzVj2vESfiLlhQxhfpChiJEoQTGkTqNNJa+uM7o/WPGNoNKgM9X9V1QwK3aL5GQv+WQl07oupY9sd65vVvVE2pAkB4EPENBbOHMEO2AqY93Ddia66KggoPZENQDXh5fJFDycZlwodd4olsU46OymLYBsO2VWOxMloWJKCsoTPqWPuu";

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data       加密数据
     * @param privateKey 私钥
     * @return
     * @throws Exception
     */
    public static String sign (byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥
        byte[] keyBytes = decryptBASE64(privateKey);

        // 构造PKCS8EncodedKeySpec对象
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);

        return encryptBASE64(signature.sign());
    }

    /**
     * 校验数字签名
     *
     * @param data      加密数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return 校验成功返回true 失败返回false
     * @throws Exception
     */
    public static boolean verify (byte[] data, String publicKey, String sign)
            throws Exception {

        // 解密由base64编码的公钥
        byte[] keyBytes = decryptBASE64(publicKey);

        // 构造X509EncodedKeySpec对象
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常
        return signature.verify(decryptBASE64(sign));
    }

    /**
     * 解密<br>
     * 用私钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey (byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }

    /**
     * 解密<br>
     * 用公钥解密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey (byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用公钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey (byte[] data, String key)
            throws Exception {
        // 对公钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    /**
     * 加密<br>
     * 用私钥加密
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey (byte[] data, String key)
            throws Exception {
        // 对密钥解密
        byte[] keyBytes = decryptBASE64(key);

        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(data);
    }


    /**
     * 解密字符串RSA方式，encodeStr为base64加密后字符串
     * @param encodeStr
     * @return
     * @throws Exception
     *//*
    public static String decodeByPriveteKey(String encodeStr) throws Exception{
		if (StringUtils.isBlank(encodeStr)){
			return null;
		}
		byte[] tmp = Base64.decodeBase64(encodeStr);
		byte[] decodedData = RSAUtils.decryptByPrivateKey(tmp,  
        		PicassoConstant.RSA_PRIVATE_KEY);  
  
        String outputStr = new String(decodedData);  
        return outputStr;
	}*/
}
