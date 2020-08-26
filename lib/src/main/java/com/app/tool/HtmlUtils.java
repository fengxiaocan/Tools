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
//        final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
//        final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        final String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

//        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
//        Matcher m_script = p_script.matcher(htmlStr);
//        htmlStr = m_script.replaceAll(""); //过滤script标签
//
//        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
//        Matcher m_style = p_style.matcher(htmlStr);
//        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlReplace(htmlStr).trim(); //返回文本字符串
    }
//
//    private static class HtmlParser{
//        private final char[] chars;
//        private final StringBuilder builder;
//
//        public HtmlParser(String html){
//            this.builder = new StringBuilder(html);
//            this.chars = html.toCharArray();
//        }
//
//        public String parser(){
//            int startIndex = - 1;
//            int endIndex = - 1;
//            int offset = 0;
//            final String str = "\r\n";
//            final int line = str.length();
//            for(int i = 0;i < chars.length;i++){
//                char c = chars[i];
//                switch(c){
//                    case '<':
//                        startIndex = i;
//                        break;
//                    case '>':
//                        endIndex = i;
//                        break;
//                }
//                if(startIndex >= 0 && endIndex > 0 && endIndex > startIndex){
//                    if(chars[startIndex + 1] == 'b' && chars[startIndex + 2] == 'r'){
//                        builder.replace(startIndex - offset,endIndex - offset + 1,str);
//                        offset += (endIndex - startIndex + 1);
//                        offset -= line;
//                    } else{
//                        builder.delete(startIndex - offset,endIndex - offset + 1);
//                        offset += (endIndex - startIndex + 1);
//                    }
//                    startIndex = - 1;
//                    endIndex = - 1;
//                }
//            }
//            return builder.toString();
//        }
//    }

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
