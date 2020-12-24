package com.app.encrypt;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES_128/ECB/PKCS5PADDING
 * AES/CBC/PKCS5PADDING
 * AES_256/CBC/NOPADDING
 * AES/CTR/NOPADDING
 * AES_128/CBC/NOPADDING
 * AESWRAP
 * AES_256/ECB/PKCS5PADDING
 * AES_256/CBC/PKCS5PADDING
 * AES_128/GCM/NOPADDING
 * AES/CBC/PKCS7PADDING
 * AES/ECB/PKCS5PADDING
 * AES/ECB/PKCS7PADDING
 * AES_128/CBC/PKCS5PADDING
 * AES_256/GCM/NOPADDING
 * AES_256/ECB/NOPADDING
 * AES_128/ECB/NOPADDING
 * AES/ECB/NOPADDING
 * AES/CBC/NOPADDING
 * AES/GCM/NOPADDING
 */
public enum AES implements ICipher {
    AES("AES", 128),
    ECB_NOPADDING("AES/ECB/NOPADDING", false, true),
    ECB_PKCS5PADDING("AES/ECB/PKCS5PADDING", 128),
    ECB_PKCS7PADDING("AES/ECB/PKCS7PADDING", 128),

    CBC_NOPADDING("AES/CBC/NOPADDING", true, true),
    CBC_PKCS5PADDING("AES/CBC/PKCS5PADDING", true, false),
    CBC_PKCS7PADDING("AES/CBC/PKCS7PADDING", true, false),

    _128_GCM_NOPADDING("AES_128/GCM/NOPADDING", true, true, 12, 128),
    _128_CBC_NOPADDING("AES_128/CBC/NOPADDING", true, true),
    _128_CBC_PKCS5PADDING("AES_128/CBC/PKCS5PADDING", true, false),
    _128_ECB_NOPADDING("AES_128/ECB/NOPADDING", false, true),
    _128_ECB_PKCS5PADDING("AES_128/ECB/PKCS5PADDING", 128),

    _256_GCM_NOPADDING("AES_256/GCM/NOPADDING", true, true, 12, 256),
    _256_ECB_NOPADDING("AES_256/ECB/NOPADDING", false, true, 16, 256),
    _256_ECB_PKCS5PADDING("AES_256/ECB/PKCS5PADDING", 128),
    _256_CBC_NOPADDING("AES_256/CBC/NOPADDING", true, true, 16, 256),
    _256_CBC_PKCS5PADDING("AES_256/CBC/PKCS5PADDING", true, false, 16, 256),

    WRAP("AESWRAP", 128),
    CTR_NOPADDING("AES/CTR/NOPADDING", true, true),
    GCM_NOPADDING("AES/GCM/NOPADDING", true, true);

    public static final String algorithm = "AES";

    private final String transformation;
    private boolean isParameterMode = false;
    private boolean isNoPadding = false;
    private int vectorSize = 16;//12/16
    private int keySize = 128;//128,192,256


    AES(String transformation, int keySize) {
        this.transformation = transformation;
        this.keySize = keySize;
    }

    AES(String transformation, boolean isParameterMode, boolean isNoPadding) {
        this.transformation = transformation;
        this.isParameterMode = isParameterMode;
        this.isNoPadding = isNoPadding;
    }

    AES(String transformation, boolean isParameterMode, boolean isNoPadding, int vectorSize, int keySize) {
        this.transformation = transformation;
        this.isParameterMode = isParameterMode;
        this.isNoPadding = isNoPadding;
        this.vectorSize = vectorSize;
        this.keySize = keySize;
    }

    public static Key getKey(int keySize) {
        return CipherCoder.createKey(algorithm, keySize);
    }

    public Key getKey() {
        return getKey(keySize);
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

    @Override
    public Key toKey(byte[] key) {
        return new SecretKeySpec(key, algorithm);
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
