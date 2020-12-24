package com.app.encrypt;

import android.util.Base64;

import com.app.tool.Tools;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * PBEWITHSHAAND128BITRC2-CBC
 * RSA/ECB/NOPADDING
 * AES_128/ECB/PKCS5PADDING
 * AES/CBC/PKCS5PADDING
 * PBEWITHSHA256AND128BITAES-CBC-BC
 * AES_256/CBC/NOPADDING
 * AES/CTR/NOPADDING
 * DESEDE/CBC/NOPADDING
 * AES_128/CBC/NOPADDING
 * AESWRAP
 * PBEWITHSHAANDTWOFISH-CBC
 * PBEWITHMD5ANDDES
 * PBEWITHSHAAND40BITRC2-CBC
 * PBEWITHSHA1ANDDES
 * RSA/ECB/OAEPPADDING
 * DESEDEWRAP
 * DESEDE/ECB/PKCS7PADDING
 * RSA
 * CHACHA20/POLY1305/NOPADDING
 * AES_256/ECB/PKCS5PADDING
 * PBEWITHSHAAND128BITRC4
 * AES_256/CBC/PKCS5PADDING
 * PBEWITHSHAAND3-KEYTRIPLEDES-CBC
 * DESEDE
 * BLOWFISH
 * PBEWITHSHAAND256BITAES-CBC-BC
 * RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING
 * PBEWITHMD5AND256BITAES-CBC-OPENSSL
 * RSA/ECB/PKCS1PADDING
 * AES
 * AES_128/GCM/NOPADDING
 * AES/CBC/PKCS7PADDING
 * AES/ECB/PKCS5PADDING
 * AES/ECB/PKCS7PADDING
 * PBEWITHSHA256AND256BITAES-CBC-BC
 * RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING
 * PBEWITHSHAAND192BITAES-CBC-BC
 * AES_128/CBC/PKCS5PADDING
 * PBEWITHMD5AND128BITAES-CBC-OPENSSL
 * AES_256/GCM/NOPADDING
 * DESEDE/ECB/NOPADDING
 * PBEWITHSHA256AND192BITAES-CBC-BC
 * AES_256/ECB/NOPADDING
 * CHACHA20
 * RSA/ECB/OAEPWITHSHA-224ANDMGF1PADDING
 * DESEDE/CBC/PKCS7PADDING
 * PBEWITHSHA1ANDRC2
 * AES_128/ECB/NOPADDING
 * DESEDE/CBC/PKCS5PADDING
 * ARC4
 * RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING
 * PBEWITHMD5AND192BITAES-CBC-OPENSSL
 * AES/ECB/NOPADDING
 * PBEWITHSHAAND128BITAES-CBC-BC
 * AES/CBC/NOPADDING
 * AES/GCM/NOPADDING
 * RSA/ECB/OAEPWITHSHA-384ANDMGF1PADDING
 * PBEWITHSHAAND40BITRC4
 * PBEWITHSHAAND2-KEYTRIPLEDES-CBC
 * DES
 * PBEWITHMD5ANDRC2
 */
public class CipherCoder {

    /**
     * 加密或解密
     *
     * @param data 数据
     * @param key  秘钥
     * @return 密文或者明文，适用于DES，3DES，AES
     */
    public static byte[] keyCode(ICipher iCipher, byte[] data, Key key, AlgorithmParameterSpec vectorKey, int opmode) {
        if (data == null || data.length == 0 || key == null)
            return null;
        try {
            Cipher cipher = Cipher.getInstance(iCipher.getTransformation());
            if (iCipher.isParameterMode()) {
                cipher.init(opmode, key, vectorKey);
            } else {
                cipher.init(opmode, key);
            }
            byte[] copyData = data;
            if (iCipher.isNoPadding()) {
                copyData = handleNoPaddingEncryptFormat(cipher, data);
            }
            return cipher.doFinal(copyData);
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密或解密
     *
     * @param data   数据
     * @param key    秘钥
     * @param opmode {@code true}: 加密 {@code false}: 解密
     * @return 密文或者明文，适用于DES，3DES，AES
     */
    public static byte[] keyCode(ICipher iCipher, byte[] data, byte[] key, byte[] vectorKey, int opmode) {
        if (data == null || data.length == 0 || key == null || key.length == 0)
            return null;
        return keyCode(iCipher, data, iCipher.toKey(key), iCipher.toParameterKey(vectorKey), opmode);
    }


    /*************************  加密  *************************/
    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, byte[] data, byte[] key, byte[] vectorKey) {
        return keyCode(iCipher, data, key, vectorKey, Cipher.ENCRYPT_MODE);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return keyCode(iCipher, data, key, vectorKey, Cipher.ENCRYPT_MODE);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, byte[] data, Key key, byte[] vectorKey) {
        return encrypt(iCipher, data, key, iCipher.toParameterKey(vectorKey));
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, byte[] data, byte[] key) {
        return encrypt(iCipher, data, key, key);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, byte[] data, Key key) {
        return encrypt(iCipher, data, key, key.getEncoded());
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, String data, byte[] key, byte[] vectorKey) {
        return encrypt(iCipher, data.getBytes(), key, vectorKey);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, String data, Key key, AlgorithmParameterSpec vectorKey) {
        return encrypt(iCipher, data.getBytes(), key, vectorKey);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, String data, Key key, byte[] vectorKey) {
        return encrypt(iCipher, data.getBytes(), key, vectorKey);
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, String data, byte[] key) {
        return encrypt(iCipher, data.getBytes(), key, key);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] encrypt(ICipher iCipher, String data, Key key) {
        return encrypt(iCipher, data.getBytes(), key);
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToHex(ICipher iCipher, byte[] data, byte[] key, byte[] vectorKey) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key, vectorKey));
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String encryptToHex(ICipher iCipher, byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key, vectorKey));
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String encryptToHex(ICipher iCipher, byte[] data, Key key, byte[] vectorKey) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key, vectorKey));
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToHex(ICipher iCipher, byte[] data, byte[] key) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key));
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToHex(ICipher iCipher, byte[] data, Key key) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key));
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToHex(ICipher iCipher, String data, byte[] key, byte[] vectorKey) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key, vectorKey));
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String encryptToHex(ICipher iCipher, String data, Key key, AlgorithmParameterSpec vectorKey) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key, vectorKey));
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String encryptToHex(ICipher iCipher, String data, Key key, byte[] vectorKey) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key, vectorKey));
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToHex(ICipher iCipher, String data, byte[] key) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key));
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToHex(ICipher iCipher, String data, Key key) {
        return Tools.Hex.bytes2Hex(encrypt(iCipher, data, key));
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, byte[] data, byte[] key, byte[] vectorKey) {
        return Base64.encodeToString(encrypt(iCipher, data, key, vectorKey), Base64.DEFAULT);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return Base64.encodeToString(encrypt(iCipher, data, key, vectorKey), Base64.DEFAULT);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, byte[] data, Key key, byte[] vectorKey) {
        return Base64.encodeToString(encrypt(iCipher, data, key, vectorKey), Base64.DEFAULT);
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, byte[] data, byte[] key) {
        return Base64.encodeToString(encrypt(iCipher, data, key), Base64.DEFAULT);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, byte[] data, Key key) {
        return Base64.encodeToString(encrypt(iCipher, data, key), Base64.DEFAULT);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, String data, byte[] key, byte[] vectorKey) {
        return Base64.encodeToString(encrypt(iCipher, data, key, vectorKey), Base64.DEFAULT);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, String data, Key key, AlgorithmParameterSpec vectorKey) {
        return Base64.encodeToString(encrypt(iCipher, data, key, vectorKey), Base64.DEFAULT);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, String data, Key key, byte[] vectorKey) {
        return Base64.encodeToString(encrypt(iCipher, data, key, vectorKey), Base64.DEFAULT);
    }


    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, String data, byte[] key) {
        return Base64.encodeToString(encrypt(iCipher, data, key), Base64.DEFAULT);
    }

    /**
     * 加密
     *
     * @param data
     * @param key
     * @return
     */
    public static String encryptToBase64(ICipher iCipher, String data, Key key) {
        return Base64.encodeToString(encrypt(iCipher, data, key), Base64.DEFAULT);
    }


    /*************************  解密  *************************/

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(ICipher iCipher, byte[] data, byte[] key, byte[] vectorKey) {
        return keyCode(iCipher, data, key, vectorKey, Cipher.DECRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] decrypt(ICipher iCipher, byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return keyCode(iCipher, data, key, vectorKey, Cipher.DECRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] decrypt(ICipher iCipher, byte[] data, Key key, byte[] vectorKey) {
        return decrypt(iCipher, data, key, iCipher.toParameterKey(vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(ICipher iCipher, byte[] data, byte[] key) {
        return decrypt(iCipher, data, key, key);
    }


    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decrypt(ICipher iCipher, byte[] data, Key key) {
        return decrypt(iCipher, data, key, key.getEncoded());
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptHex(ICipher iCipher, String data, byte[] key, byte[] vectorKey) {
        return decrypt(iCipher, Tools.Hex.hex2Bytes(data), key, vectorKey);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] decryptHex(ICipher iCipher, String data, Key key, AlgorithmParameterSpec vectorKey) {
        return decrypt(iCipher, Tools.Hex.hex2Bytes(data), key, vectorKey);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] decryptHex(ICipher iCipher, String data, Key key, byte[] vectorKey) {
        return decrypt(iCipher, Tools.Hex.hex2Bytes(data), key, vectorKey);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptHex(ICipher iCipher, String data, byte[] key) {
        return decrypt(iCipher, Tools.Hex.hex2Bytes(data), key);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptHex(ICipher iCipher, String data, Key key) {
        return decrypt(iCipher, Tools.Hex.hex2Bytes(data), key);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptBase64(ICipher iCipher, String data, byte[] key, byte[] vectorKey) {
        return decrypt(iCipher, Base64.decode(data, Base64.DEFAULT), key, vectorKey);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] decryptBase64(ICipher iCipher, String data, Key key, AlgorithmParameterSpec vectorKey) {
        return decrypt(iCipher, Base64.decode(data, Base64.DEFAULT), key, vectorKey);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static byte[] decryptBase64(ICipher iCipher, String data, Key key, byte[] vectorKey) {
        return decrypt(iCipher, Base64.decode(data, Base64.DEFAULT), key, vectorKey);
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptBase64(ICipher iCipher, String data, byte[] key) {
        return decrypt(iCipher, Base64.decode(data, Base64.DEFAULT), key);
    }


    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static byte[] decryptBase64(ICipher iCipher, String data, Key key) {
        return decrypt(iCipher, Base64.decode(data, Base64.DEFAULT), key);
    }


    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptToString(ICipher iCipher, byte[] data, byte[] key, byte[] vectorKey) {
        return new String(decrypt(iCipher, data, key, vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String decryptToString(ICipher iCipher, byte[] data, Key key, AlgorithmParameterSpec vectorKey) {
        return new String(decrypt(iCipher, data, key, vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String decryptToString(ICipher iCipher, byte[] data, Key key, byte[] vectorKey) {
        return new String(decrypt(iCipher, data, key, vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptToString(ICipher iCipher, byte[] data, byte[] key) {
        return new String(decrypt(iCipher, data, key));
    }


    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptToString(ICipher iCipher, byte[] data, Key key) {
        return new String(decrypt(iCipher, data, key));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptHexToString(ICipher iCipher, String data, byte[] key, byte[] vectorKey) {
        return new String(decryptHex(iCipher, data, key, vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String decryptHexToString(ICipher iCipher, String data, Key key, AlgorithmParameterSpec vectorKey) {
        return new String(decryptHex(iCipher, data, key, vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String decryptHexToString(ICipher iCipher, String data, Key key, byte[] vectorKey) {
        return new String(decryptHex(iCipher, data, key, vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptHexToString(ICipher iCipher, String data, byte[] key) {
        return new String(decryptHex(iCipher, data, key));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptHexToString(ICipher iCipher, String data, Key key) {
        return new String(decryptHex(iCipher, data, key));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptBase64ToString(ICipher iCipher, String data, byte[] key, byte[] vectorKey) {
        return new String(decryptBase64(iCipher, data, key, vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String decryptBase64ToString(ICipher iCipher, String data, Key key, AlgorithmParameterSpec vectorKey) {
        return new String(decryptBase64(iCipher, data, key, vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @param vectorKey
     * @return
     */
    public static String decryptBase64ToString(ICipher iCipher, String data, Key key, byte[] vectorKey) {
        return new String(decryptBase64(iCipher, data, key, vectorKey));
    }

    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptBase64ToString(ICipher iCipher, String data, byte[] key) {
        return new String(decryptBase64(iCipher, data, key));
    }


    /**
     * 解密
     *
     * @param data
     * @param key
     * @return
     */
    public static String decryptBase64ToString(ICipher iCipher, String data, Key key) {
        return new String(decryptBase64(iCipher, data, key));
    }


    /**
     * 获取随机秘钥
     *
     * @param algorithm e.g:AES/DES/RSA
     * @return 秘钥 key
     */
    public static Key getKey(String algorithm) {
        //生成随机秘钥
        SecretKey secretKey = null;
        try {
            secretKey = KeyGenerator.getInstance(algorithm).generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return secretKey;
    }

    /**
     * 创建Key
     *
     * @param algorithm e.g.AES
     * @param keySize   设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。DES只支持64位长度密钥
     * @return secret key
     * @throws Exception the exception
     */
    public static SecretKey createKey(String algorithm, int keySize) {
        try {
            //对称key即SecretKey创建和导入，假设双方约定使用DES算法来生成对称密钥
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            //设置密钥长度。注意，每种算法所支持的密钥长度都是不一样的。DES只支持64位长度密钥
            keyGenerator.init(keySize);
            //生成SecretKey对象，即创建一个对称密钥，并获取二进制的书面表达
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey;
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String keyToHex(Key key) {
        return Tools.Hex.bytes2Hex(key.getEncoded());
    }

    public static String keyToBase64(Key key) {
        return Base64.encodeToString(key.getEncoded(), Base64.DEFAULT);
    }

    /**
     * <p>NoPadding加密模式, 加密内容必须是 8byte的倍数, 不足8位则末位补足0</p>
     * <p>加密算法不提供该补码方式, 需要代码完成该补码方式</p>
     *
     * @param cipher
     * @param content ：加密内容
     * @return 符合加密的内容(byte[])
     * @Param charset :指定的字符集
     */
    static byte[] handleNoPaddingEncryptFormat(Cipher cipher, byte[] content) throws Exception {
        int blockSize = cipher.getBlockSize();
        int plaintextLength = content.length;
        if (plaintextLength % blockSize != 0) {
            plaintextLength = plaintextLength + (blockSize - plaintextLength % blockSize);
        }
        byte[] plaintext = new byte[plaintextLength];
        System.arraycopy(content, 0, plaintext, 0, content.length);
        return plaintext;
    }

}
