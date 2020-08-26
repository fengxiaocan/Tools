package com.app.encrypt;

import android.net.Uri;

import java.io.File;
import java.io.InputStream;

public class MD5Coder {
    /**
     * 加密算法
     * @return 密文
     */
    public static byte[] code(byte[] data) {
        return SHACoder.MD5.code(data);
    }

    /**
     * 加密算法
     *
     * @return 密文
     */
    public static byte[] code(String data) {
        return SHACoder.MD5.code(data);
    }

    /**
     * 加密
     *
     * @return 16进制密文
     */
    public static String codeToHex(byte[] data) {
        return SHACoder.MD5.codeToHex(data);
    }

    /**
     * 加密
     *
     * @return 16进制密文
     */
    public static String codeToHex(String data) {
        return SHACoder.MD5.codeToHex(data);
    }

    /**
     * 加密文件
     *
     * @param file 文件
     * @return 文件的校验码
     */
    public static byte[] code(File file) {
        return SHACoder.MD5.code(file);
    }

    /**
     * 加密文件
     *
     * @param file 文件
     * @return 文件的校验码
     */
    public static String codeToHex(File file) {
        return SHACoder.MD5.codeToHex(file);
    }

    /**
     * 加密文件
     *
     * @param file 文件
     * @return 文件的校验码
     */
    public static byte[] code(Uri file) {
        return SHACoder.MD5.code(file);
    }

    /**
     * 加密文件
     *
     * @param file 文件
     * @return 16进制校验码
     */
    public static String codeToHex(Uri file) {
        return SHACoder.MD5.codeToHex(file);
    }

    /**
     * 加密文件
     *
     * @param inputStream 文件流
     * @return 16进制校验码
     */
    public static byte[] code(InputStream inputStream) {
        return SHACoder.MD5.code(inputStream);
    }

    /**
     * 加密文件
     *
     * @param inputStream 文件流
     * @return 16进制校验码
     */
    public static String codeToHex(InputStream inputStream) {
        return SHACoder.MD5.codeToHex(inputStream);
    }
}
