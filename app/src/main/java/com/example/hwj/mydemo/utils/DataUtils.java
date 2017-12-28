package com.example.hwj.mydemo.utils;

import com.example.hwj.mydemo.utils.encryption.AES.AESUtils;
import com.example.hwj.mydemo.utils.encryption.Base64.Base64;
import com.example.hwj.mydemo.utils.encryption.RSA.RSAUtils;

import java.util.Map;


public class DataUtils {

    /**
     * 解密response数据
     */
    public static String decryptData(String key, String content) {
        AESUtils aes = new AESUtils();
        byte[] keyByteArr = Base64.decodeBase64(key);
        byte[] data = null;
        try {
            data = RSAUtils.decryptByPrivateKey(keyByteArr, RSAUtils.STR_DATA_RSA_PRIVSTE_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] contentByteArr = Base64.decodeBase64(content);
        return new String(aes.decrypt(contentByteArr, data));
    }

    /**
     * 加密登陆密码
     */
    public static Map encryptionPassWord(Map<String, String> map) {
        String pw = "";
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (entry.getKey().equals("passWord")) {
                byte[] bytes = new byte[0];
                try {
                    bytes = RSAUtils.encryptByPublicKey(entry.getValue().toString().getBytes(), RSAUtils.RSA_PUBLIC_KEY_FOR_DECODE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                pw = Base64.encodeBase64String(bytes);
                map.put("passWord", pw);
            }
        }

        return map;
    }


}
