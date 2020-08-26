package com.app.encrypt;

import android.net.Uri;

import com.app.tool.Tools.Close;
import com.app.tool.Tools.Hex;
import com.app.tool.UriUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Set;

public enum SHACoder {
    MD5("MD5"),
    SHA1("SHA-1"),
    SHA224("SHA-224"),
    SHA256("SHA-256"),
    SHA384("SHA-384"),
    SHA512("SHA-512");

    String algorithm;

    SHACoder(java.lang.String algorithm) {
        this.algorithm = algorithm;
    }

    public static Set<String> getAlgorithms() {
        return Security.getAlgorithms("MessageDigest");
    }

    /**
     * hash加密模板
     *
     * @param data      数据
     * @param algorithm 加密算法
     * @return 密文字节数组
     */
    public static byte[] hashCode(byte[] data, String algorithm) {
        if (data == null || data.length <= 0)
            return null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(data);
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 加密文件
     *
     * @param inputStream 文件流
     * @return 文件的校验码
     */
    public static byte[] code(InputStream inputStream, String algorithm) {
        DigestInputStream digestInputStream = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            digestInputStream = new DigestInputStream(inputStream, md);
            byte[] buffer = new byte[10 * 1024];
            while (true) {
                if (!(digestInputStream.read(buffer) > 0))
                    break;
            }
            md = digestInputStream.getMessageDigest();
            return md.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Close.closeIO(inputStream, digestInputStream);
        }
    }

    /**
     * 加密算法
     *
     * @return 密文
     */
    public byte[] code(byte[] data) {
        return hashCode(data, algorithm);
    }

    /**
     * 加密算法
     *
     * @return 密文
     */
    public byte[] code(String data) {
        return hashCode(data.getBytes(), algorithm);
    }

    /**
     * 加密
     *
     * @return 16进制密文
     */
    public String codeToHex(byte[] data) {
        return Hex.bytes2Hex(code(data));
    }

    /**
     * 加密
     *
     * @return 16进制密文
     */
    public String codeToHex(String data) {
        return Hex.bytes2Hex(code(data));
    }

    /**
     * 加密文件
     *
     * @param file 文件
     * @return 文件的校验码
     */
    public byte[] code(File file) {
        if (file == null || !file.exists())
            return null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return code(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Close.closeIO(fis);
        }
    }

    /**
     * 加密文件
     *
     * @param file 文件
     * @return 文件的校验码
     */
    public String codeToHex(File file) {
        return Hex.bytes2Hex(code(file));
    }

    /**
     * 加密文件
     *
     * @param file 文件
     * @return 文件的校验码
     */
    public byte[] code(Uri file) {
        if (file == null)
            return null;
        InputStream fis = null;
        try {
            fis = UriUtils.openInputStream(file);
            return code(fis);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            Close.closeIO(fis);
        }
    }

    /**
     * 加密文件
     *
     * @param file 文件
     * @return 16进制校验码
     */
    public String codeToHex(Uri file) {
        return Hex.bytes2Hex(code(file));
    }

    /**
     * 加密文件
     *
     * @param inputStream 文件流
     * @return 16进制校验码
     */
    public byte[] code(InputStream inputStream) {
        return code(inputStream, algorithm);
    }

    /**
     * 加密文件
     *
     * @param inputStream 文件流
     * @return 16进制校验码
     */
    public String codeToHex(InputStream inputStream) {
        return Hex.bytes2Hex(code(inputStream, algorithm));
    }

    public java.lang.String getAlgorithm() {
        return algorithm;
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
