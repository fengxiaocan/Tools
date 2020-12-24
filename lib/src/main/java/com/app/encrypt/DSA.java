package com.app.encrypt;

import android.util.Base64;

import androidx.annotation.IntRange;

import com.app.tool.Tools;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;


import static com.app.encrypt.RSA.SECURE_RANDOM_ALGORITHM;

public final class DSA {
    public static final String algorithm = "DSA";
    public static final String DSA_SIGNATURE = "SHA1withDSA";


    public static KeyPair getKey() {
        return getKey(1024);
    }

    /**
     * 获取秘钥
     *
     * @return
     */
    public static KeyPair getKey(@IntRange(from = 1024) int keySize) {
        try {
            //创建秘钥生成器
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
            kpg.initialize(keySize);
            return kpg.generateKeyPair();//生成秘钥对
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取秘钥
     *
     * @return
     */
    public static KeyPair getKey(@IntRange(from = 1024) int keySize, SecureRandom random) {
        try {
            //创建秘钥生成器
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(algorithm);
            kpg.initialize(keySize, random);
            return kpg.generateKeyPair();//生成秘钥对
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取秘钥
     *
     * @return
     */
    public static KeyPair getKey(@IntRange(from = 1024) int keySize, byte[] salt) {
        try {
            SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
            random.setSeed(salt);
            return getKey(keySize, random);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取秘钥
     *
     * @return
     */
    public static KeyPair getKey(byte[] salt) {
        return getKey(1024, salt);
    }

    /**
     * 获取秘钥
     *
     * @return
     */
    public static KeyPair getKey(@IntRange(from = 1024) int keySize, String salt) {
        return getKey(keySize, salt.getBytes());
    }

    /**
     * 获取秘钥
     *
     * @return
     */
    public static KeyPair getKey(String salt) {
        return getKey(1024, salt.getBytes());
    }

    /**
     * 获取秘钥
     *
     * @return
     */
    public static KeyPair getKey(long see) {
        return getKey(1024, see);
    }

    /**
     * 获取秘钥
     *
     * @return
     */
    public static KeyPair getKey(@IntRange(from = 1024) int keySize, long seed) {
        try {
            SecureRandom random = SecureRandom.getInstance(SECURE_RANDOM_ALGORITHM);
            random.setSeed(seed);
            return getKey(keySize, random);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
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
    /**************** 签名 *******************/

    /**
     * 给数据签名
     *
     * @param privateKey 私钥,只能私钥签名,公钥验证
     * @param data       数据
     * @return 签名后的数据
     */
    public static byte[] signature(PrivateKey privateKey, byte[] data) {
        try {
            //签名和验证
            Signature sign = Signature.getInstance(DSA_SIGNATURE);
            sign.initSign(privateKey);//初始化私钥，签名只能是私钥
            sign.update(data);//更新签名数据
            return sign.sign();//签名，返回签名后的字节数组
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 给数据签名
     *
     * @param keyPair 私钥,只能私钥签名,公钥验证
     * @param data    数据
     * @return 签名后的数据
     */
    public static byte[] signature(KeyPair keyPair, byte[] data) {
        return signature(keyPair.getPrivate(), data);
    }

    /**
     * 给数据签名
     *
     * @param privateKey 私钥,只能私钥签名,公钥验证
     * @param data       数据
     * @return 签名后的数据
     */
    public static byte[] signature(PrivateKey privateKey, String data) {
        return signature(privateKey, data.getBytes());
    }

    /**
     * 给数据签名
     *
     * @param keyPair 私钥,只能私钥签名,公钥验证
     * @param data    数据
     * @return 签名后的数据
     */
    public static byte[] signature(KeyPair keyPair, String data) {
        return signature(keyPair.getPrivate(), data.getBytes());
    }

    /**
     * 给数据签名
     *
     * @param privateKey 私钥,只能私钥签名,公钥验证
     * @param data       数据
     * @return 签名后的数据
     */
    public static String signatureToHex(PrivateKey privateKey, byte[] data) {
        return Tools.Hex.bytes2Hex(signature(privateKey, data));
    }

    /**
     * 给数据签名
     *
     * @param keyPair 私钥,只能私钥签名,公钥验证
     * @param data    数据
     * @return 签名后的数据
     */
    public static String signatureToHex(KeyPair keyPair, byte[] data) {
        return Tools.Hex.bytes2Hex(signature(keyPair, data));
    }

    /**
     * 给数据签名
     *
     * @param privateKey 私钥,只能私钥签名,公钥验证
     * @param data       数据
     * @return 签名后的数据
     */
    public static String signatureToHex(PrivateKey privateKey, String data) {
        return Tools.Hex.bytes2Hex(signature(privateKey, data));
    }

    /**
     * 给数据签名
     *
     * @param keyPair 私钥,只能私钥签名,公钥验证
     * @param data    数据
     * @return 签名后的数据
     */
    public static String signatureToHex(KeyPair keyPair, String data) {
        return Tools.Hex.bytes2Hex(signature(keyPair, data));
    }


    /**
     * 给数据签名
     *
     * @param privateKey 私钥,只能私钥签名,公钥验证
     * @param data       数据
     * @return 签名后的数据
     */
    public static String signatureToBase64(PrivateKey privateKey, byte[] data) {
        return Base64.encodeToString(signature(privateKey, data), Base64.DEFAULT);
    }

    /**
     * 给数据签名
     *
     * @param keyPair 私钥,只能私钥签名,公钥验证
     * @param data    数据
     * @return 签名后的数据
     */
    public static String signatureToBase64(KeyPair keyPair, byte[] data) {
        return Base64.encodeToString(signature(keyPair, data), Base64.DEFAULT);
    }

    /**
     * 给数据签名
     *
     * @param privateKey 私钥,只能私钥签名,公钥验证
     * @param data       数据
     * @return 签名后的数据
     */
    public static String signatureToBase64(PrivateKey privateKey, String data) {
        return Base64.encodeToString(signature(privateKey, data), Base64.DEFAULT);
    }

    /**
     * 给数据签名
     *
     * @param keyPair 私钥,只能私钥签名,公钥验证
     * @param data    数据
     * @return 签名后的数据
     */
    public static String signatureToBase64(KeyPair keyPair, String data) {
        return Base64.encodeToString(signature(keyPair, data), Base64.DEFAULT);
    }


    /******************** 数据校验 *******************/

    /**
     * 验证签名数据
     *
     * @param publicKey 公钥
     * @param data      校验的元数据
     * @param signature 需要校验的签名数据
     * @return
     */
    public static boolean verify(PublicKey publicKey, byte[] data, byte[] signature) {
        try {
            //签名和验证
            Signature sign = Signature.getInstance(DSA_SIGNATURE);
            sign.initVerify(publicKey);//初始化公钥，验证只能是公钥
            sign.update(data);//更新验证的数据
            return sign.verify(signature);//签名和验证一致返回true  不一致返回false
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 验证签名数据
     *
     * @param keyPair   公钥
     * @param data      校验的元数据
     * @param signature 需要校验的签名数据
     * @return
     */
    public static boolean verify(KeyPair keyPair, byte[] data, byte[] signature) {
        return verify(keyPair.getPublic(), data, signature);
    }

    /**
     * 验证签名数据
     *
     * @param publicKey 公钥
     * @param data      校验的元数据
     * @param signature 需要校验的签名数据
     * @return
     */
    public static boolean verify(PublicKey publicKey, String data, byte[] signature) {
        return verify(publicKey, data.getBytes(), signature);
    }

    /**
     * 验证签名数据
     *
     * @param keyPair   公钥
     * @param data      校验的元数据
     * @param signature 需要校验的签名数据
     * @return
     */
    public static boolean verify(KeyPair keyPair, String data, byte[] signature) {
        return verify(keyPair, data.getBytes(), signature);
    }


    /**
     * 验证签名数据
     *
     * @param publicKey 公钥
     * @param data      校验的元数据
     * @param signature 需要校验的签名数据
     * @return
     */
    public static boolean verifyHex(PublicKey publicKey, String data, String signature) {
        return verify(publicKey, data.getBytes(), Tools.Hex.hex2Bytes(signature));
    }

    /**
     * 验证签名数据
     *
     * @param keyPair   公钥
     * @param data      校验的元数据
     * @param signature 需要校验的签名数据
     * @return
     */
    public static boolean verifyBase64(KeyPair keyPair, String data, String signature) {
        return verify(keyPair, data.getBytes(), Base64.decode(signature, Base64.DEFAULT));
    }
}
