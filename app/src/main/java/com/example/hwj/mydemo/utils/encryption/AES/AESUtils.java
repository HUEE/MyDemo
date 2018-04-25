package com.example.hwj.mydemo.utils.encryption.AES;


import com.example.hwj.mydemo.utils.encryption.Base64.Base64;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES128 算法
 * <p>
 * CBC 模式
 * <p>
 * PKCS7Padding 填充模式
 * <p>
 * CBC模式需要添加一个参数iv
 * <p>
 * 介于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
 * 要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
 */
public class AESUtils {
    // 算法名称
    final String KEY_ALGORITHM = "AES";
    // 加解密算法/模式/填充方式
    final String algorithmStr = "AES/CBC/PKCS7Padding";
    //
    private Key key;
    private Cipher cipher;
    byte[] keybytes;

    byte[] iv = {0x2a, 0x50, 0x69, 0x63, 0x61, 0x73, 0x73, 0x6f, 0x49, 0x76, 0x56, 0x61, 0x6c, 0x75, 0x65, 0x26};

    private void init (byte[] keyBytes) {

        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
        try {
            // 初始化cipher
            cipher = Cipher.getInstance(algorithmStr, "BC");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密方法
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @return
     * @throws InvalidAlgorithmParameterException
     * @throws InvalidKeyException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public byte[] encrypt (byte[] content, byte[] keyBytes) throws InvalidKeyException, InvalidAlgorithmParameterException,
            IllegalBlockSizeException, BadPaddingException {
        byte[] encryptedText = null;
        init(keyBytes);
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
        encryptedText = cipher.doFinal(content);
        return encryptedText;
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @return
     */
    public byte[] decrypt (byte[] encryptedData, byte[] keyBytes) {

        byte[] encryptedText = null;
        init(keyBytes);

        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    public String getRandomString (int length) {
        //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ!@#$%^&*()";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    /*
     * @description  提供给外部加密的接口
     * @parames 所需加密的内容
     * @return 加解密的key 加密后的内容
     */
    public static List<String> enCodeByAES (String content) {
        List<String> resultList = new ArrayList<String>();
        AESUtils aes = new AESUtils();
        //加解密 密钥
        String key = aes.getRandomString(16);

        resultList.add(key);
        //如果报异常，则list最多最有一个
        try {
            byte[] keybytes = key.getBytes("utf-8");
            //开始加密
            byte[] enc;
            enc = aes.encrypt(content.getBytes(), keybytes);
            //加密后的内容
            resultList.add(new String(Base64.encodeBase64(enc)));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultList;
    }
}
