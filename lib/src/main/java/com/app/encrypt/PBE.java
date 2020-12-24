package com.app.encrypt;

import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * PBEWITHSHAAND128BITRC2-CBC
 * PBEWITHSHA256AND128BITAES-CBC-BC
 * PBEWITHSHAANDTWOFISH-CBC
 * PBEWITHMD5ANDDES
 * PBEWITHSHAAND40BITRC2-CBC
 * PBEWITHSHA1ANDDES
 * PBEWITHSHAAND128BITRC4
 * PBEWITHSHAAND3-KEYTRIPLEDES-CBC
 * PBEWITHSHAAND256BITAES-CBC-BC
 * PBEWITHMD5AND256BITAES-CBC-OPENSSL
 * PBEWITHSHA256AND256BITAES-CBC-BC
 * PBEWITHSHAAND192BITAES-CBC-BC
 * PBEWITHMD5AND128BITAES-CBC-OPENSSL
 * PBEWITHSHA256AND192BITAES-CBC-BC
 * PBEWITHSHA1ANDRC2
 * PBEWITHMD5AND192BITAES-CBC-OPENSSL
 * PBEWITHSHAAND128BITAES-CBC-BC
 * PBEWITHSHAAND40BITRC4
 * PBEWITHSHAAND2-KEYTRIPLEDES-CBC
 * PBEWITHMD5ANDRC2
 */
public enum PBE implements ICipher {
    SHAAND128BITRC2_CBC("PBEWITHSHAAND128BITRC2-CBC"),
    SHAAND40BITRC2_CBC("PBEWITHSHAAND40BITRC2-CBC"),
    SHAAND3_KEYTRIPLEDES_CBC("PBEWITHSHAAND3-KEYTRIPLEDES-CBC"),
    SHAAND2_KEYTRIPLEDES_CBC("PBEWITHSHAAND2-KEYTRIPLEDES-CBC"),

    SHA256AND128BITAES_CBC_BC("PBEWITHSHA256AND128BITAES-CBC-BC"),
    SHAANDTWOFISH_CBC("PBEWITHSHAANDTWOFISH-CBC"),
    MD5ANDDES("PBEWITHMD5ANDDES"),
    SHA1ANDDES("PBEWITHSHA1ANDDES"),
    SHAAND128BITRC4("PBEWITHSHAAND128BITRC4"),

    SHAAND256BITAES_CBC_BC("PBEWITHSHAAND256BITAES-CBC-BC"),
    MD5AND256BITAES_CBC_OPENSSL("PBEWITHMD5AND256BITAES-CBC-OPENSSL"),
    SHA256AND256BITAES_CBC_BC("PBEWITHSHA256AND256BITAES-CBC-BC"),
    SHAAND192BITAES_CBC_BC("PBEWITHSHAAND192BITAES-CBC-BC"),
    MD5AND128BITAES_CBC_OPENSSL("PBEWITHMD5AND128BITAES-CBC-OPENSSL"),
    SHA256AND192BITAES_CBC_BC("PBEWITHSHA256AND192BITAES-CBC-BC"),
    SHA1ANDRC2("PBEWITHSHA1ANDRC2"),
    MD5AND192BITAES_CBC_OPENSSL("PBEWITHMD5AND192BITAES-CBC-OPENSSL"),
    SHAAND128BITAES_CBC_BC("PBEWITHSHAAND128BITAES-CBC-BC"),
    SHAAND40BITRC4("PBEWITHSHAAND40BITRC4"),
    MD5ANDRC2("PBEWITHMD5ANDRC2");

    public static final String algorithm = "PBE";
    private final String transformation;
    private final boolean isParameterMode = true;
    private final boolean isNoPadding = false;
    private final int vectorSize = 8;
    private int iterationCount = 100;//迭代次数默认100

    PBE(String transformation) {
        this.transformation = transformation;
    }

    /**
     * 随机获取加密时使用的向量
     *
     * @return
     */
    public static byte[] randomSalt() {
        return new SecureRandom().generateSeed(8);
    }

    /**
     * 设置默认的迭代次数
     *
     * @param iterationCount
     * @return
     */
    public PBE setIterationCount(int iterationCount) {
        this.iterationCount = iterationCount;
        return this;
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
        return null;
    }

    /**
     * 把密码转换成秘钥
     *
     * @param password 密码
     * @return
     */
    public Key toKey(String password) {
        return toKey(password, password.getBytes(), iterationCount);
    }

    /**
     * 把密码转换成秘钥
     *
     * @param password       密码
     * @param salt           加密时使用的向量
     * @param iterationCount 迭代次数
     * @return
     */
    public Key toKey(String password, byte[] salt, int iterationCount) {
        try {
            //密钥材料转换
            PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount);
            //实例化
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(transformation);
            //生成密钥
            return keyFactory.generateSecret(keySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把加密时使用的向量转换为系统的秘钥
     *
     * @param vectorKey      加密时使用的向量
     * @param iterationCount 迭代次数
     * @return
     */
    public AlgorithmParameterSpec toParameterKey(byte[] vectorKey, int iterationCount) {
        byte[] bytes = new byte[vectorSize];
        System.arraycopy(vectorKey, 0, bytes, 0, Math.min(vectorSize, vectorKey.length));
        return new PBEParameterSpec(bytes, iterationCount);
    }

    /**
     * 把加密时使用的向量转换为系统的秘钥
     *
     * @param vectorKey
     * @return
     */
    public AlgorithmParameterSpec toParameterKey(byte[] vectorKey) {
        return toParameterKey(vectorKey, 100);
    }

    /*************************  加密  *************************/
    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(byte[] data, String password) {
        return CipherCoder.encrypt(this, data, toKey(password));
    }

    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(byte[] data, Key key) {
        return CipherCoder.encrypt(this, data, key);
    }

    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encrypt(this, data, key, vectorKey);
    }

    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(byte[] data, String key, byte[] vector) {
        return CipherCoder.encrypt(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(byte[] data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.encrypt(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(String data, String key) {
        return CipherCoder.encrypt(this, data, toKey(key));
    }

    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(String data, Key key) {
        return CipherCoder.encrypt(this, data, key);
    }

    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encrypt(this, data, key, vectorKey);
    }

    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(String data, String key, byte[] vector) {
        return CipherCoder.encrypt(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 加密
     *
     * @return
     */
    public byte[] encrypt(String data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.encrypt(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(byte[] data, String key) {
        return CipherCoder.encryptToHex(this, data, toKey(key));
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(byte[] data, Key key) {
        return CipherCoder.encryptToHex(this, data, key);
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encryptToHex(this, data, key, vectorKey);
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(byte[] data, String key, byte[] vector) {
        return CipherCoder.encryptToHex(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(byte[] data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.encryptToHex(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(String data, String key) {
        return CipherCoder.encryptToHex(this, data, toKey(key));
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(String data, Key key) {
        return CipherCoder.encryptToHex(this, data, key);
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encryptToHex(this, data, key, vectorKey);
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(String data, String key, byte[] vector) {
        return CipherCoder.encryptToHex(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 加密成16进制密文
     *
     * @return
     */
    public String encryptToHex(String data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.encryptToHex(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(byte[] data, String key) {
        return CipherCoder.encryptToBase64(this, data, toKey(key));
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(byte[] data, Key key) {
        return CipherCoder.encryptToBase64(this, data, key);
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encryptToBase64(this, data, key, vectorKey);
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(byte[] data, String key, byte[] vector) {
        return CipherCoder.encryptToBase64(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(byte[] data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.encryptToBase64(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(String data, String key) {
        return CipherCoder.encryptToBase64(this, data, toKey(key));
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(String data, Key key) {
        return CipherCoder.encryptToBase64(this, data, key);
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.encryptToBase64(this, data, key, vectorKey);
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(String data, String key, byte[] vector) {
        return CipherCoder.encryptToBase64(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 加密成base64密文
     *
     * @return
     */
    public String encryptToBase64(String data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.encryptToBase64(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /*************************  解密  *************************/
    /**
     * 解密
     *
     * @return
     */
    public byte[] decrypt(byte[] data, String key) {
        return CipherCoder.decrypt(this, data, toKey(key));
    }

    /**
     * 解密
     *
     * @return
     */
    public byte[] decrypt(byte[] data, Key key) {
        return CipherCoder.decrypt(this, data, key);
    }

    /**
     * 解密
     *
     * @return
     */
    public byte[] decrypt(byte[] data, String key, byte[] vector) {
        return CipherCoder.decrypt(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 解密
     *
     * @return
     */
    public byte[] decrypt(byte[] data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.decrypt(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 解密
     *
     * @return
     */
    public byte[] decrypt(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decrypt(this, data, key, vectorKey);
    }

    /**
     * 解密16进制的密文
     *
     * @return
     */
    public byte[] decryptHex(String data, String key) {
        return CipherCoder.decryptHex(this, data, toKey(key));
    }

    /**
     * 解密16进制的密文
     *
     * @return
     */
    public byte[] decryptHex(String data, Key key) {
        return CipherCoder.decryptHex(this, data, key);
    }

    /**
     * 解密16进制的密文
     *
     * @return
     */
    public byte[] decryptHex(String data, String key, byte[] vector) {
        return CipherCoder.decryptHex(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 解密16进制的密文
     *
     * @return
     */
    public byte[] decryptHex(String data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.decryptHex(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 解密16进制的密文
     *
     * @return
     */
    public byte[] decryptHex(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptHex(this, data, key, vectorKey);
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public byte[] decryptBase64(String data, String key) {
        return CipherCoder.decryptBase64(this, data, toKey(key));
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public byte[] decryptBase64(String data, Key key) {
        return CipherCoder.decryptBase64(this, data, key);
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public byte[] decryptBase64(String data, String key, byte[] vector) {
        return CipherCoder.decryptBase64(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public byte[] decryptBase64(String data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.decryptBase64(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public byte[] decryptBase64(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptBase64(this, data, key, vectorKey);
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public String decryptToString(byte[] data, String key) {
        return CipherCoder.decryptToString(this, data, toKey(key));
    }

    /**
     * 解密
     *
     * @return
     */
    public String decryptToString(byte[] data, Key key) {
        return CipherCoder.decryptToString(this, data, key);
    }

    /**
     * 解密
     *
     * @return
     */
    public String decryptToString(byte[] data, String key, byte[] vector) {
        return CipherCoder.decryptToString(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 解密
     *
     * @return
     */
    public String decryptToString(byte[] data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.decryptToString(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 解密
     *
     * @return
     */
    public String decryptToString(byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptToString(this, data, key, vectorKey);
    }

    /**
     * 解密16进制密文
     *
     * @return
     */
    public String decryptHexToString(String data, String key) {
        return CipherCoder.decryptHexToString(this, data, toKey(key));
    }

    /**
     * 解密16进制密文
     *
     * @return
     */
    public String decryptHexToString(String data, Key key) {
        return CipherCoder.decryptHexToString(this, data, key);
    }

    /**
     * 解密16进制密文
     *
     * @return
     */
    public String decryptHexToString(String data, String key, byte[] vector) {
        return CipherCoder.decryptHexToString(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 解密16进制密文
     *
     * @return
     */
    public String decryptHexToString(String data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.decryptHexToString(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 解密16进制密文
     *
     * @return
     */
    public String decryptHexToString(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptHexToString(this, data, key, vectorKey);
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public String decryptBase64ToString(String data, String key) {
        return CipherCoder.decryptBase64ToString(this, data, toKey(key));
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public String decryptBase64ToString(String data, Key key) {
        return CipherCoder.decryptBase64ToString(this, data, key);
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public String decryptBase64ToString(String data, String key, byte[] vector) {
        return CipherCoder.decryptBase64ToString(this, data, toKey(key), toParameterKey(vector));
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public String decryptBase64ToString(String data, String key, byte[] vector, int iterationCount) {
        return CipherCoder.decryptBase64ToString(this, data, toKey(key), toParameterKey(vector, iterationCount));
    }

    /**
     * 解密Base64密文
     *
     * @return
     */
    public String decryptBase64ToString(String data, Key key, AlgorithmParameterSpec vectorKey) {
        return CipherCoder.decryptBase64ToString(this, data, key, vectorKey);
    }


}
