package com.app.tool;

import android.content.res.AssetManager;
import android.util.Xml;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

class AssetsUtils extends Util {

    /**
     * 解析xml
     *
     * @param xmlName
     * @param handler
     */
    public static void parseXml(String xmlName, DefaultHandler handler) throws IOException, SAXException {
        Xml.parse(getFileFromAssets(xmlName), Xml.Encoding.UTF_8, handler);
    }

    /**
     * 异步解析xml
     *
     * @param xmlName
     * @param handler
     */
    public static void parseAsyXml(final String xmlName, final DefaultHandler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Xml.parse(getFileFromAssets(xmlName), Xml.Encoding.UTF_8, handler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 从assets获取文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static InputStream getFileFromAssets(String fileName) throws IOException {
        AssetManager am = getContext().getAssets();
        return am.open(fileName);
    }

    /**
     * 从assets获取文本文件
     *
     * @param fileName
     * @return
     */
    public static String getTextFromAssets(String fileName, Charset charset) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getFileFromAssets(fileName), charset);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从assets获取文本文件
     *
     * @param fileName
     * @return
     */
    public static String getTextFromAssets(String fileName) {
        return getTextFromAssets(fileName, Charsets.UTF_8);
    }
}
