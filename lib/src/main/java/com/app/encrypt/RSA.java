package com.app.encrypt;

import android.util.Base64;

import androidx.annotation.IntRange;

import com.app.tool.Tools;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA
 * RSA/ECB/NOPADDING
 * RSA/ECB/OAEPPADDING
 * RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING
 * RSA/ECB/PKCS1PADDING
 * RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING
 * RSA/ECB/OAEPWITHSHA-224ANDMGF1PADDING
 * RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING
 * RSA/ECB/OAEPWITHSHA-384ANDMGF1PADDING
 */
public enum RSA {
    RSA("RSA"),
    NOPADDING("RSA/ECB/NOPADDING"),
    OAEPPADDING("RSA/ECB/OAEPPADDING"),
    OAEPWITHSHA256ANDMGF1PADDING("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING"),
    PKCS1PADDING("RSA/ECB/PKCS1PADDING"),
    OAEPWITHSHA1ANDMGF1PADDING("RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING"),
    OAEPWITHSHA224ANDMGF1PADDING("RSA/ECB/OAEPWITHSHA-224ANDMGF1PADDING"),
    OAEPWITHSHA512ANDMGF1PADDING("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING"),
    OAEPWITHSHA384ANDMGF1PADDING("RSA/ECB/OAEPWITHSHA-384ANDMGF1PADDING");

    private String transformation;

    RSA(String transformation) {
        this.transformation = transformation;
    }

    public static final String algorithm = "RSA";
    public static final int DEFAULT_KEYSIZE = 2048;
    public static final String SECURE_RANDOM_ALGORITHM = "SHA1PRNG";

    /**
     * 获取公钥跟私钥
     *
     * @return
     */
    public static KeyPair initKey(@IntRange(from = 1024) int keySize, SecureRandom random) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(algorithm);
            //初始化密钥对生成器，密钥大小为96-1024位
            keyPairGen.initialize(keySize, random);
            //生成一个密钥对，保存在keyPair中
            return keyPairGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取公钥跟私钥
     *
     * @return
     */
    public static KeyPair initKey() {
        return initKey(DEFAULT_KEYSIZE, new SecureRandom());
    }
    /**
     * 获取公钥跟私钥
     *
     * @return
     */
    public static KeyPair initKey(@IntRange(from = 1024) int keySize) {
        return initKey(keySize, new SecureRandom());
    }

    /**
     * 获取公钥跟私钥
     *
     * @return
     */
    public static KeyPair initKey(@IntRange(from = 1024) int keySize, byte[] slatKey) {
        try {
            SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
            random.setSeed(slatKey);
            return initKey(keySize, random);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取公钥跟私钥
     *
     * @return
     */
    public static KeyPair initKey(@IntRange(from = 1024) int keySize, String slatKey) {
        return initKey(keySize, slatKey.getBytes());
    }


    /**
     * 获取公钥跟私钥
     *
     * @return
     */
    public static KeyPair initKey(String slatKey) {
        return initKey(DEFAULT_KEYSIZE, slatKey.getBytes());
    }


    /**
     * 获取公钥跟私钥
     *
     * @return
     */
    public static KeyPair initKey(@IntRange(from = 1024) int keySize, long seed) {
        try {
            //初始化密钥对生成器，密钥大小为96-1024位
            SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
            random.setSeed(seed);
            return initKey(keySize, random);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据密码获取公钥私钥
     *
     * @return
     */
    public static KeyPair initKey(long seed) {
        return initKey(DEFAULT_KEYSIZE, seed);
    }

    /**
     * 获取公钥
     *
     * @param pair
     * @return
     */
    public static PublicKey getPublicKey(KeyPair pair) {
        return pair.getPublic();
    }

    /**
     * 获取私钥
     *
     * @param pair
     * @return
     */
    public static PrivateKey getPrivateKey(KeyPair pair) {
        return pair.getPrivate();
    }

    /**
     * 获取公钥
     *
     * @param pair
     * @return
     */
    public static byte[] getPublicKeyByte(KeyPair pair) {
        return getPublicKey(pair.getPublic());
    }

    /**
     * 获取私钥
     *
     * @param pair
     * @return
     */
    public static byte[] getPrivateKeyByte(KeyPair pair) {
        return getPrivateKey(pair.getPrivate());
    }

    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     */
    public static byte[] getPublicKey(PublicKey publicKey) {
        return publicKey.getEncoded();
    }

    /**
     * 获取私钥
     *
     * @param privateKey
     * @return
     */
    public static byte[] getPrivateKey(PrivateKey privateKey) {
        return privateKey.getEncoded();
    }

    /**
     * 获取公钥
     *
     * @param pair
     * @return
     */
    public static String getPublicHex(KeyPair pair) {
        return Tools.Hex.bytes2Hex(getPublicKeyByte(pair));
    }

    /**
     * 获取私钥
     *
     * @param pair
     * @return
     */
    public static String getPrivateHex(KeyPair pair) {
        return Tools.Hex.bytes2Hex(getPrivateKeyByte(pair));
    }

    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     */
    public static String getPublicHex(PublicKey publicKey) {
        return Tools.Hex.bytes2Hex(publicKey.getEncoded());
    }

    /**
     * 获取私钥
     *
     * @param privateKey
     * @return
     */
    public static String getPrivateHex(PrivateKey privateKey) {
        return Tools.Hex.bytes2Hex(privateKey.getEncoded());
    }

    /**
     * 获取公钥
     *
     * @param pair
     * @return
     */
    public static String getPublicBase64(KeyPair pair) {
        return Base64.encodeToString(getPublicKeyByte(pair), Base64.DEFAULT);
    }

    /**
     * 获取私钥
     *
     * @param pair
     * @return
     */
    public static String getPrivateBase64(KeyPair pair) {
        return Base64.encodeToString(getPrivateKeyByte(pair), Base64.DEFAULT);
    }

    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     */
    public static String getPublicBase64(PublicKey publicKey) {
        return Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT);
    }

    /**
     * 获取私钥
     *
     * @param privateKey
     * @return
     */
    public static String getPrivateBase64(PrivateKey privateKey) {
        return Base64.encodeToString(privateKey.getEncoded(), Base64.DEFAULT);
    }

    /**
     * byte[]转换成公钥
     *
     * @param encoded
     * @return
     */
    public static PublicKey getPublicKey(byte[] encoded) {
        try {
            return KeyFactory.getInstance(algorithm).generatePublic(new X509EncodedKeySpec(encoded));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * byte[]转换成私钥
     *
     * @param encoded
     * @return
     */
    public static PrivateKey getPrivateKey(byte[] encoded) {
        try {
            return KeyFactory.getInstance(algorithm).generatePrivate(new X509EncodedKeySpec(encoded));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 16进制密文转换成公钥
     *
     * @param encoded
     * @return
     */
    public static PublicKey hexToPublicKey(String encoded) {
        return getPublicKey(Tools.Hex.hex2Bytes(encoded));
    }

    /**
     * 16进制密文转换成私钥
     *
     * @param encoded
     * @return
     */
    public static PrivateKey hexToPrivateKey(String encoded) {
        return getPrivateKey(Tools.Hex.hex2Bytes(encoded));
    }

    /**
     * 16进制密文转换成公钥
     *
     * @param encoded
     * @return
     */
    public static PublicKey base64ToPublicKey(String encoded) {
        return getPublicKey(Base64.decode(encoded, Base64.DEFAULT));
    }

    /**
     * 16进制密文转换成私钥
     *
     * @param encoded
     * @return
     */
    public static PrivateKey base64ToPrivateKey(String encoded) {
        return getPrivateKey(Base64.decode(encoded, Base64.DEFAULT));
    }


    /**
     * 加密或解密逻辑
     *
     * @param opmode
     * @param key
     * @param data
     * @return
     */
    private byte[] cipher(int opmode, Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(opmode, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加密
     *
     * @param key
     * @param data
     * @return
     */
    private byte[] encryptByte(Key key, byte[] data) {
        return cipher(Cipher.ENCRYPT_MODE, key, data);
    }

    /**
     * 解密
     *
     * @param key
     * @param data
     * @return
     */
    private byte[] decryptByte(Key key, byte[] data) {
        return cipher(Cipher.DECRYPT_MODE, key, data);
    }


    /************************** 加密 **************************/


    /**
     * 通过公钥加密数据
     *
     * @param publicKey 公钥
     * @param data
     * @return
     */
    public byte[] encrypt(PublicKey publicKey, byte[] data) {
        return encryptByte(publicKey, data);
    }

    /**
     * 通过私钥加密数据
     *
     * @param privateKey
     * @param data
     * @return
     */
    public byte[] encrypt(PrivateKey privateKey, byte[] data) {
        return encryptByte(privateKey, data);
    }


    /**
     * 通过公钥加密数据
     *
     * @param publicKey 公钥
     * @param data
     * @return
     */
    public byte[] encrypt(PublicKey publicKey, String data) {
        return encryptByte(publicKey, data.getBytes());
    }

    /**
     * 通过私钥加密数据
     *
     * @param privateKey
     * @param data
     * @return
     */
    public byte[] encrypt(PrivateKey privateKey, String data) {
        return encryptByte(privateKey, data.getBytes());
    }

    /**
     * 通过公钥加密数据
     *
     * @param publicKey 公钥
     * @param data
     * @return 16进制密文
     */
    public String encryptToHex(PublicKey publicKey, byte[] data) {
        return Tools.Hex.bytes2Hex(encrypt(publicKey, data));
    }

    /**
     * 通过私钥加密数据
     *
     * @param privateKey
     * @param data
     * @return 16进制密文
     */
    public String encryptToHex(PrivateKey privateKey, byte[] data) {
        return Tools.Hex.bytes2Hex(encrypt(privateKey, data));
    }


    /**
     * 通过公钥加密数据
     *
     * @param publicKey 公钥
     * @param data
     * @return 16进制密文
     */
    public String encryptToHex(PublicKey publicKey, String data) {
        return Tools.Hex.bytes2Hex(encrypt(publicKey, data));
    }

    /**
     * 通过私钥加密数据
     *
     * @param privateKey
     * @param data
     * @return 16进制密文
     */
    public String encryptToHex(PrivateKey privateKey, String data) {
        return Tools.Hex.bytes2Hex(encrypt(privateKey, data));
    }


    /**
     * 通过公钥加密数据
     *
     * @param publicKey 公钥
     * @param data
     * @return base64密文
     */
    public String encryptToBase64(PublicKey publicKey, byte[] data) {
        return Base64.encodeToString(encrypt(publicKey, data), Base64.DEFAULT);
    }

    /**
     * 通过私钥加密数据
     *
     * @param privateKey
     * @param data
     * @return base64密文
     */
    public String encryptToBase64(PrivateKey privateKey, byte[] data) {
        return Base64.encodeToString(encrypt(privateKey, data), Base64.DEFAULT);
    }


    /**
     * 通过公钥加密数据
     *
     * @param publicKey 公钥
     * @param data
     * @return base64密文
     */
    public String encryptToBase64(PublicKey publicKey, String data) {
        return Base64.encodeToString(encrypt(publicKey, data), Base64.DEFAULT);
    }

    /**
     * 通过私钥加密数据
     *
     * @param privateKey
     * @param data
     * @return base64密文
     */
    public String encryptToBase64(PrivateKey privateKey, String data) {
        return Base64.encodeToString(encrypt(privateKey, data), Base64.DEFAULT);
    }

    /**
     * 通过公钥加密数据
     *
     * @param data
     * @return
     */
    public byte[] encryptByPublicKey(KeyPair key, byte[] data) {
        return encryptByte(key.getPublic(), data);
    }

    /**
     * 通过私钥加密数据
     *
     * @param data
     * @return
     */
    public byte[] encryptByPrivateKey(KeyPair key, byte[] data) {
        return encryptByte(key.getPrivate(), data);
    }


    /**
     * 通过公钥加密数据
     *
     * @param data
     * @return
     */
    public byte[] encryptByPublicKey(KeyPair key, String data) {
        return encryptByte(key.getPublic(), data.getBytes());
    }

    /**
     * 通过私钥加密数据
     *
     * @param data
     * @return
     */
    public byte[] encryptByPrivateKey(KeyPair key, String data) {
        return encryptByte(key.getPrivate(), data.getBytes());
    }

    /**
     * 通过公钥加密数据
     *
     * @param data
     * @return 16进制密文
     */
    public String encryptToHexByPublicKey(KeyPair key, byte[] data) {
        return Tools.Hex.bytes2Hex(encrypt(key.getPublic(), data));
    }

    /**
     * 通过私钥加密数据
     *
     * @param data
     * @return 16进制密文
     */
    public String encryptToHexByPrivateKey(KeyPair key, byte[] data) {
        return Tools.Hex.bytes2Hex(encrypt(key.getPrivate(), data));
    }


    /**
     * 通过公钥加密数据
     *
     * @param data
     * @return 16进制密文
     */
    public String encryptToHexByPublicKey(KeyPair key, String data) {
        return Tools.Hex.bytes2Hex(encrypt(key.getPublic(), data));
    }

    /**
     * 通过私钥加密数据
     *
     * @param data
     * @return 16进制密文
     */
    public String encryptToHexByPrivateKey(KeyPair key, String data) {
        return Tools.Hex.bytes2Hex(encrypt(key.getPrivate(), data));
    }


    /**
     * 通过公钥加密数据
     * @param data
     * @return base64密文
     */
    public String encryptToBase64ByPublicKey(KeyPair key, byte[] data) {
        return Base64.encodeToString(encrypt(key.getPublic(), data), Base64.DEFAULT);
    }

    /**
     * 通过私钥加密数据
     *
     * @param data
     * @return base64密文
     */
    public String encryptToBase64ByPrivateKey(KeyPair key, byte[] data) {
        return Base64.encodeToString(encrypt(key.getPrivate(), data), Base64.DEFAULT);
    }


    /**
     * 通过公钥加密数据
     *
     * @param data
     * @return base64密文
     */
    public String encryptToBase64ByPublicKey(KeyPair key, String data) {
        return Base64.encodeToString(encrypt(key.getPublic(), data), Base64.DEFAULT);
    }

    /**
     * 通过私钥加密数据
     *
     * @param data
     * @return base64密文
     */
    public String encryptToBase64ByPrivateKey(KeyPair key, String data) {
        return Base64.encodeToString(encrypt(key.getPrivate(), data), Base64.DEFAULT);
    }

    /********************** 解密 **********************/


    /**
     * 通过公钥解密
     *
     * @param publicKey
     * @param data
     * @return
     */
    public byte[] decrypt(PublicKey publicKey, byte[] data) {
        return decryptByte(publicKey, data);
    }

    /**
     * 通过私钥解密
     *
     * @param privateKey
     * @param data
     * @return
     */
    public byte[] decrypt(PrivateKey privateKey, byte[] data) {
        return decryptByte(privateKey, data);
    }

    /**
     * 通过公钥解密
     *
     * @param publicKey
     * @param data
     * @return
     */
    public byte[] decryptHex(PublicKey publicKey, String data) {
        return decryptByte(publicKey, Tools.Hex.hex2Bytes(data));
    }

    /**
     * 通过私钥解密
     *
     * @param privateKey
     * @param data
     * @return
     */
    public byte[] decryptHex(PrivateKey privateKey, String data) {
        return decryptByte(privateKey, Tools.Hex.hex2Bytes(data));
    }

    /**
     * 通过公钥解密
     *
     * @param publicKey
     * @param data
     * @return
     */
    public byte[] decryptBase64(PublicKey publicKey, String data) {
        return decryptByte(publicKey, Base64.decode(data, Base64.DEFAULT));
    }

    /**
     * 通过私钥解密
     *
     * @param privateKey
     * @param data
     * @return
     */
    public byte[] decryptBase64(PrivateKey privateKey, String data) {
        return decryptByte(privateKey, Base64.decode(data, Base64.DEFAULT));
    }

    /**
     * 通过公钥解密
     *
     * @param publicKey
     * @param data
     * @return
     */
    public String decryptToString(PublicKey publicKey, byte[] data) {
        return new String(decrypt(publicKey, data));
    }

    /**
     * 通过私钥解密
     *
     * @param privateKey
     * @param data
     * @return
     */
    public String decryptToString(PrivateKey privateKey, byte[] data) {
        return new String(decrypt(privateKey, data));
    }

    /**
     * 通过公钥解密
     *
     * @param publicKey
     * @param data
     * @return
     */
    public String decryptHexToString(PublicKey publicKey, String data) {
        return new String(decryptHex(publicKey, data));
    }

    /**
     * 通过私钥解密
     *
     * @param privateKey
     * @param data
     * @return
     */
    public String decryptHexToString(PrivateKey privateKey, String data) {
        return new String(decryptHex(privateKey, data));
    }

    /**
     * 通过公钥解密
     *
     * @param publicKey
     * @param data
     * @return
     */
    public String decryptBase64ToString(PublicKey publicKey, String data) {
        return new String(decryptBase64(publicKey, data));
    }

    /**
     * 通过私钥解密
     *
     * @param privateKey
     * @param data
     * @return
     */
    public String decryptBase64ToString(PrivateKey privateKey, String data) {
        return new String(decryptBase64(privateKey, data));
    }


    /**
     * 通过公钥解密
     *
     * @param data
     * @return
     */
    public byte[] decryptByPublicKey(KeyPair key, byte[] data) {
        return decryptByte(key.getPublic(), data);
    }

    /**
     * 通过私钥解密
     *
     *
     * @param data
     * @return
     */
    public byte[] decryptByPrivateKey(KeyPair key, byte[] data) {
        return decryptByte(key.getPrivate(), data);
    }

    /**
     * 通过公钥解密
     *
     *
     * @param data
     * @return
     */
    public byte[] decryptHexByPublicKey(KeyPair key, String data) {
        return decryptByte(key.getPublic(), Tools.Hex.hex2Bytes(data));
    }

    /**
     * 通过私钥解密
     *
     *
     * @param data
     * @return
     */
    public byte[] decryptHexByPrivateKey(KeyPair key, String data) {
        return decryptByte(key.getPrivate(), Tools.Hex.hex2Bytes(data));
    }

    /**
     * 通过公钥解密
     *
     *
     * @param data
     * @return
     */
    public byte[] decryptBase64ByPublicKey(KeyPair key, String data) {
        return decryptByte(key.getPublic(), Base64.decode(data, Base64.DEFAULT));
    }

    /**
     * 通过私钥解密
     *
     *
     * @param data
     * @return
     */
    public byte[] decryptBase64ByPrivateKey(KeyPair key, String data) {
        return decryptByte(key.getPrivate(), Base64.decode(data, Base64.DEFAULT));
    }

    /**
     * 通过公钥解密
     *
     *
     * @param data
     * @return
     */
    public String decryptToStringByPublicKey(KeyPair key, byte[] data) {
        return new String(decrypt(key.getPublic(), data));
    }

    /**
     * 通过私钥解密
     *
     *
     * @param data
     * @return
     */
    public String decryptToStringByPrivateKey(KeyPair key, byte[] data) {
        return new String(decrypt(key.getPrivate(), data));
    }

    /**
     * 通过公钥解密
     *
     *
     * @param data
     * @return
     */
    public String decryptHexToStringByPublicKey(KeyPair key, String data) {
        return new String(decryptHex(key.getPublic(), data));
    }

    /**
     * 通过私钥解密
     *
     *
     * @param data
     * @return
     */
    public String decryptHexToStringByPrivateKey(KeyPair key, String data) {
        return new String(decryptHex(key.getPrivate(), data));
    }

    /**
     * 通过公钥解密
     *
     *
     * @param data
     * @return
     */
    public String decryptBase64ToStringByPublicKey(KeyPair key, String data) {
        return new String(decryptBase64(key.getPublic(), data));
    }

    /**
     * 通过私钥解密
     *
     *
     * @param data
     * @return
     */
    public String decryptBase64ToStringByPrivateKey(KeyPair key, String data) {
        return new String(decryptBase64(key.getPrivate(), data));
    }


}
