package com.app.encrypt;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DESEDE/CBC/NOPADDING
 * DESEDEWRAP
 * DESEDE/ECB/PKCS7PADDING
 * DESEDE
 * DESEDE/ECB/NOPADDING
 * DESEDE/CBC/PKCS7PADDING
 * DESEDE/CBC/PKCS5PADDING
 * DES
 */
public enum DES3 implements ICipher {
    ECB_NOPADDING("DESEDE/ECB/NOPADDING"),
    ECB_PKCS7PADDING("DESEDE/ECB/PKCS7PADDING"),

    CBC_NOPADDING("DESEDE/CBC/NOPADDING", true, true),
    CBC_PKCS5PADDING("DESEDE/CBC/PKCS5PADDING", true, false),
    CBC_PKCS7PADDING("DESEDE/CBC/PKCS7PADDING", true, false),

    DESEDE("DESEDE"),
    WRAP("DESEDEWRAP");


    public static final String algorithm = "DESEDE";

    private String transformation;
    private boolean isParameterMode = false;
    private boolean isNoPadding = false;
    private int vectorSize = 8;//8
    private int keySize = 168;//112或168

    DES3(String transformation) {
        this.transformation = transformation;
    }

    DES3(String transformation, boolean isParameterMode, boolean isNoPadding) {
        this.transformation = transformation;
        this.isParameterMode = isParameterMode;
        this.isNoPadding = isNoPadding;
    }

    /**
     * 获取秘钥
     * @return
     */
    public Key getKey() {
        return getKey(keySize);
    }

    /**
     * 获取秘钥
     * @param keySize
     * @return
     */
    public static Key getKey(int keySize) {
        return CipherCoder.createKey(algorithm, keySize);
    }


    @Override
    public String getTransformation() {
        return transformation;
    }

    @Override
    public boolean isParameterMode() {
        return isParameterMode;
    }

    @Override
    public boolean isNoPadding() {
        return isNoPadding;
    }

    @Override
    public Key toKey(byte[] key) {
        try {
            DESKeySpec dks = new DESKeySpec(key);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
            return keyFactory.generateSecret(dks);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public AlgorithmParameterSpec toParameterKey(byte[] vectorKey) {
        if (vectorKey == null) {
            return null;
        }
        if (vectorKey.length != vectorSize) {
            byte[] bytes = new byte[vectorSize];
            System.arraycopy(vectorKey, 0, bytes, 0, Math.min(vectorSize, vectorKey.length));
            return new IvParameterSpec(bytes);
        } else {
            return new IvParameterSpec(vectorKey);
        }
    }

    /*************************  加密  *************************/
    public byte[] encrypt(byte[] data, byte[] key) {
        return CipherCoder.encrypt(this, data, key);
    }

    public byte[] encrypt(byte[] data, Key key) {
        return CipherCoder.encrypt(this, data, key);
    }

    public byte[] encrypt(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encrypt(this, data, key, vectorKey);
    }

    public byte[] encrypt(byte[] data, byte[] key, byte[] vector) {
        return CipherCoder.encrypt(this, data, key, vector);
    }

    public byte[] encrypt(String data, byte[] key) {
        return CipherCoder.encrypt(this, data, key);
    }

    public byte[] encrypt(String data, Key key) {
        return CipherCoder.encrypt(this, data, key);
    }

    public byte[] encrypt(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encrypt(this, data, key, vectorKey);
    }

    public byte[] encrypt(String data, byte[] key, byte[] vector) {
        return CipherCoder.encrypt(this, data, key, vector);
    }

    public String encryptToHex(byte[] data, byte[] key) {
        return CipherCoder.encryptToHex(this, data, key);
    }

    public String encryptToHex(byte[] data, Key key) {
        return CipherCoder.encryptToHex(this, data, key);
    }

    public String encryptToHex(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encryptToHex(this, data, key, vectorKey);
    }

    public String encryptToHex(byte[] data, byte[] key, byte[] vector) {
        return CipherCoder.encryptToHex(this, data, key, vector);
    }

    public String encryptToHex(String data, byte[] key) {
        return CipherCoder.encryptToHex(this, data, key);
    }

    public String encryptToHex(String data, Key key) {
        return CipherCoder.encryptToHex(this, data, key);
    }

    public String encryptToHex(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encryptToHex(this, data, key, vectorKey);
    }

    public String encryptToHex(String data, byte[] key, byte[] vector) {
        return CipherCoder.encryptToHex(this, data, key, vector);
    }

    public String encryptToBase64(byte[] data, byte[] key) {
        return CipherCoder.encryptToBase64(this, data, key);
    }

    public String encryptToBase64(byte[] data, Key key) {
        return CipherCoder.encryptToBase64(this, data, key);
    }

    public String encryptToBase64(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encryptToBase64(this, data, key, vectorKey);
    }

    public String encryptToBase64(byte[] data, byte[] key, byte[] vector) {
        return CipherCoder.encryptToBase64(this, data, key, vector);
    }

    public String encryptToBase64(String data, byte[] key) {
        return CipherCoder.encryptToBase64(this, data, key);
    }

    public String encryptToBase64(String data, Key key) {
        return CipherCoder.encryptToBase64(this, data, key);
    }

    public String encryptToBase64(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encryptToBase64(this, data, key, vectorKey);
    }

    public String encryptToBase64(String data, byte[] key, byte[] vector) {
        return CipherCoder.encryptToBase64(this, data, key, vector);
    }

    /*************************  解密  *************************/

    public byte[] decrypt(byte[] data, byte[] key) {
        return CipherCoder.decrypt(this, data, key);
    }

    public byte[] decrypt(byte[] data, Key key) {
        return CipherCoder.decrypt(this, data, key);
    }

    public byte[] decrypt(byte[] data, byte[] key, byte[] vector) {
        return CipherCoder.decrypt(this, data, key, vector);
    }

    public byte[] decrypt(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decrypt(this, data, key, vectorKey);
    }

    public byte[] decryptHex(String data, byte[] key) {
        return CipherCoder.decryptHex(this, data, key);
    }

    public byte[] decryptHex(String data, Key key) {
        return CipherCoder.decryptHex(this, data, key);
    }

    public byte[] decryptHex(String data, byte[] key, byte[] vector) {
        return CipherCoder.decryptHex(this, data, key, vector);
    }

    public byte[] decryptHex(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptHex(this, data, key, vectorKey);
    }

    public byte[] decryptBase64(String data, byte[] key) {
        return CipherCoder.decryptBase64(this, data, key);
    }

    public byte[] decryptBase64(String data, Key key) {
        return CipherCoder.decryptBase64(this, data, key);
    }

    public byte[] decryptBase64(String data, byte[] key, byte[] vector) {
        return CipherCoder.decryptBase64(this, data, key, vector);
    }

    public byte[] decryptBase64(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptBase64(this, data, key, vectorKey);
    }

    public String decryptToString(byte[] data, byte[] key) {
        return CipherCoder.decryptToString(this, data, key);
    }

    public String decryptToString(byte[] data, Key key) {
        return CipherCoder.decryptToString(this, data, key);
    }

    public String decryptToString(byte[] data, byte[] key, byte[] vector) {
        return CipherCoder.decryptToString(this, data, key, vector);
    }

    public String decryptToString(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptToString(this, data, key, vectorKey);
    }

    public String decryptHexToString(String data, byte[] key) {
        return CipherCoder.decryptHexToString(this, data, key);
    }

    public String decryptHexToString(String data, Key key) {
        return CipherCoder.decryptHexToString(this, data, key);
    }

    public String decryptHexToString(String data, byte[] key, byte[] vector) {
        return CipherCoder.decryptHexToString(this, data, key, vector);
    }

    public String decryptHexToString(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptHexToString(this, data, key, vectorKey);
    }

    public String decryptBase64ToString(String data, byte[] key) {
        return CipherCoder.decryptBase64ToString(this, data, key);
    }

    public String decryptBase64ToString(String data, Key key) {
        return CipherCoder.decryptBase64ToString(this, data, key);
    }

    public String decryptBase64ToString(String data, byte[] key, byte[] vector) {
        return CipherCoder.decryptBase64ToString(this, data, key, vector);
    }

    public String decryptBase64ToString(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptBase64ToString(this, data, key, vectorKey);
    }


}
