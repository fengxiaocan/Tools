package com.app.tool;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


class HtmlUtils {

    /**
     * 去除html标签
     *
     * @param htmlStr
     * @return
     */
    public static String removeHtmlTag(String htmlStr) {
        if (TextUtils.isEmpty(htmlStr)) {
            return "";
        }
        final String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式
        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlReplace(htmlStr).trim(); //返回文本字符串
    }

    public static String htmlReplace(String str) {
        str = str.replace("&ldquo;", "“");
        str = str.replace("&rdquo;", "”");
        str = str.replace("&nbsp;", " ");
        str = str.replace("&amp;", "&");
        str = str.replace("&#39;", "'");
        str = str.replace("&rsquo;", "’");
        str = str.replace("&mdash;", "—");
        str = str.replace("&ndash;", "–");
        return str;
    }
}
