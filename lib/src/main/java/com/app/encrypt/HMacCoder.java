package com.app.encrypt;

import android.net.Uri;

import com.app.tool.Tools.Close;
import com.app.tool.Tools.Hex;
import com.app.tool.UriUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Set;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * keyed-Hash Message Authentication Code
 */
public enum HMacCoder {
    PBEWithHMacSHA1("PBEWITHHMACSHA1"),
    HMacSHA512("HMACSHA512"),
    PBEWithHMacSHA512("PBEWITHHMACSHA512"),
    PBEWithHMacSHA224("PBEWITHHMACSHA224"),
    HMacSHA384("HMACSHA384"),
    PBEWithHMacSHA("PBEWITHHMACSHA"),
    PBEWithHMacSHA256("PBEWITHHMACSHA256"),
    HMacSHA1("HMACSHA1"),
    HMacSHA256("HMACSHA256"),
    PBEWithHMacSHA384("PBEWITHHMACSHA384"),
    HMacSHA224("HMACSHA224"),
    HMacMD5("HMACMD5");

    private final String algorithm;

    HMacCoder(String algorithm) {
        this.algorithm = algorithm;
    }

    public static Set<String> getAlgorithms() {
        return Security.getAlgorithms("Mac");
    }

    /**
     * HashMac加密模板
     *
     * @param data      数据
     * @param key       秘钥
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    public static byte[] macEncrypt(byte[] data, byte[] key, String algorithm) {
        if (data == null || data.length == 0 || key == null || key.length == 0)
            return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密输入流
     *
     * @param inputStream
     * @param key
     * @param algorithm
     * @return
     */
    public static byte[] encrypt(InputStream inputStream, byte[] key, String algorithm) {
        if (inputStream == null || key == null || key.length == 0)
            return null;
        try {
            SecretKeySpec secretKey = new SecretKeySpec(key, algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKey);
            int length;
            byte[] bytes = new byte[8 * 1024];
            while ((length = inputStream.read(bytes)) > 0) {
                mac.update(bytes, 0, length);
            }
            return mac.doFinal();
        } catch (InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            Close.closeIO(inputStream);
        }
    }

    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 16进制密文
     */
    public String encryptToHex(byte[] data, byte[] key) {
        return Hex.bytes2Hex(encrypt(data, key));
    }

    /**
     * 加密
     *
     * @param data 明文字符串
     * @param key  秘钥
     * @return 16进制密文
     */
    public String encryptToHex(String data, String key) {
        return encryptToHex(data.getBytes(), key.getBytes());
    }

    /**
     * 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public byte[] encrypt(byte[] data, byte[] key) {
        return macEncrypt(data, key, algorithm);
    }

    /**
     * 加密
     *
     * @param data 明文字节数组
     * @param key  秘钥
     * @return 密文字节数组
     */
    public byte[] encrypt(String data, String key) {
        return encrypt(data.getBytes(), key.getBytes());
    }

    /**
     * 加密输入流
     *
     * @param inputStream
     * @param key
     * @return 密文字节数组
     */
    public byte[] encrypt(InputStream inputStream, byte[] key) {
        return encrypt(inputStream, key, algorithm);
    }

    /**
     * 加密输入流
     *
     * @param inputStream
     * @param key
     * @return 16进制密文
     */
    public String encryptToHex(InputStream inputStream, byte[] key) {
        return Hex.bytes2Hex(encrypt(inputStream, key, algorithm));
    }

    /**
     * 加密输入流
     *
     * @param inputStream
     * @param key
     * @return 密文字节数组
     */
    public byte[] encrypt(InputStream inputStream, String key) {
        return encrypt(inputStream, key.getBytes(), algorithm);
    }

    /**
     * 加密输入流
     *
     * @param inputStream
     * @param key
     * @return 16进制密文
     */
    public String encryptToHex(InputStream inputStream, String key) {
        return encryptToHex(inputStream, key.getBytes());
    }

    /**
     * 加密文件
     *
     * @param file
     * @param key
     * @return 密文字节数组
     */
    public byte[] encrypt(File file, byte[] key) {
        if (file == null || !file.exists()) {
            return null;
        }
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            return encrypt(inputStream, key, algorithm);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } finally {
            Close.closeIO(inputStream);
        }
    }

    /**
     * 加密文件
     *
     * @param file
     * @param key
     * @return 密文字节数组
     */
    public byte[] encrypt(File file, String key) {
        return encrypt(file, key.getBytes());
    }

    /**
     * 加密文件
     *
     * @param file
     * @param key
     * @return 16进制密文
     */
    public String encryptToHex(File file, byte[] key) {
        return Hex.bytes2Hex(encrypt(file, key));
    }

    /**
     * 加密文件
     *
     * @param file
     * @param key
     * @return 16进制密文
     */
    public String encryptToHex(File file, String key) {
        return encryptToHex(file, key.getBytes());
    }

    /**
     * 加密文件
     *
     * @param file
     * @param key
     * @return 密文字节数组
     */
    public byte[] encrypt(Uri file, byte[] key) {
        InputStream inputStream = null;
        try {
            inputStream = UriUtils.openInputStream(file);
            return encrypt(inputStream, key);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            Close.closeIO(inputStream);
        }
    }

    /**
     * 加密文件
     *
     * @param file
     * @param key
     * @return 密文字节数组
     */
    public byte[] encrypt(Uri file, String key) {
        return encrypt(file, key.getBytes());
    }

    /**
     * 加密文件
     *
     * @param file
     * @param key
     * @return 16进制密文
     */
    public String encryptToHex(Uri file, byte[] key) {
        return Hex.bytes2Hex(encrypt(file, key));
    }

    /**
     * 加密文件
     *
     * @param file
     * @param key
     * @return 16进制密文
     */
    public String encryptToHex(Uri file, String key) {
        return encryptToHex(file, key.getBytes());
    }


    /**
     * 产生摘要算法的密钥
     */
    public byte[] initMacKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(algorithm);
            // 产生密钥
            SecretKey secretKey = generator.generateKey();
            // 获得密钥
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * 是否能够使用该方式加密
     *
     * @return
     */
    public boolean hasAlgorithm() {
        return getAlgorithms().contains(algorithm);
    }
}
