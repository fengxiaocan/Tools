package com.app.tool;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

class OpenUtils extends Util {

    private static final String taobaoName = "com.taobao.taobao";
    private static final String tmallName = "com.tmall.wireless";
    private static final String jingdongName = "com.jingdong.app.mall";
    private static final String pinduoduoName = "com.xunmeng.pinduoduo";
    private static final String suningName = "com.suning.mobile.ebuy";

    /**
     * 打开文件
     *
     * @param filePath
     * @return
     */
    public static Intent openFile(String filePath) {
        if (StringUtils.isEmpty(filePath)) {
            return null;
        }
        /* 取得扩展名 */
        String end = filePath.substring(filePath.lastIndexOf(".") + 1).toLowerCase();
        /* 依扩展名的类型决定MimeType */
        if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") || end.equals("xmf") || end.equals("ogg") ||
                end.equals("wav")) {
            return IntentUtils.getAudioFileIntent(filePath);
        } else if (end.equals("3gp") || end.equals("mp4")) {
            return IntentUtils.getVideoFileIntent(filePath);
        } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") || end.equals("jpeg") || end.equals(
                "bmp")) {
            return IntentUtils.getImageFileIntent(filePath);
        } else if (end.equals("apk")) {
            return IntentUtils.getApkFileIntent(filePath);
        } else if (end.equals("ppt")) {
            return IntentUtils.getPptFileIntent(filePath);
        } else if (end.equals("xls")) {
            return IntentUtils.getExcelFileIntent(filePath);
        } else if (end.equals("doc") || end.equals("docx")) {
            return IntentUtils.getWordFileIntent(filePath);
        } else if (end.equals("pdf")) {
            return IntentUtils.getPdfFileIntent(filePath);
        } else if (end.equals("chm")) {
            return IntentUtils.getChmFileIntent(filePath);
        } else if (end.equals("html")) {
            return IntentUtils.getHtmlFileIntent(filePath);
        } else if (end.equals("txt")) {
            return IntentUtils.getTextFileIntent(filePath);
        } else if (end.equals("zip") || end.equals("rar")) {
            return IntentUtils.getGzipIntent(filePath);
        } else {
            return IntentUtils.getAllIntent(filePath);
        }
    }

    /**
     * 检测该包名所对应的应用是否存在 ** @param packageName * @return
     */
    public static boolean checkPackage(String packageName) {
        if (packageName == null || "".equals(packageName)) {
            return false;
        }
        //手机已安装，返回true
        if (AppUtils.isInstallApp(packageName)) {
            return true;
        } else {
            //手机未安装，跳转到应用商店下载，并返回false
            Uri uri = Uri.parse("market://details?id=" + packageName);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            getContext().startActivity(it);
            return false;
        }
    }

    /**
     * 打开微信
     */
    public static void openWeiChat() {
        try {
            //打开微信
            Intent intent = new Intent();
            ComponentName cmp = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            getContext().startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 是否有淘宝客户端
     *
     * @return
     */
    public static boolean checkTaobaoClient() {
        return AppUtils.isInstallApp(taobaoName);
    }

    /**
     * 是否有天猫客户端
     *
     * @return
     */
    public static boolean checkTmallClient() {
        return AppUtils.isInstallApp(tmallName);
    }

    /**
     * 是否有京东客户端
     *
     * @return
     */
    public static boolean checkJDClient() {
        return AppUtils.isInstallApp(jingdongName);
    }

    /**
     * 是否有拼多多客户端
     *
     * @return
     */
    public static boolean checkPinDuoDuoClient() {
        return AppUtils.isInstallApp(pinduoduoName);
    }

    /**
     * 打开淘宝详情页
     *
     * @param url
     * @return
     */
    public static boolean openTaoBaoApp(String url) {
        if (url != null && AppUtils.isInstallApp(taobaoName)) {
            if (url.startsWith("https://")) {
                url = url.replaceFirst("https://", "taobao://");
            }
            if (url.startsWith("http://")) {
                url = url.replaceFirst("http://", "taobao://");
            }
            if (url.startsWith("tbopen://")) {
                url = url.replaceFirst("tbopen://", "taobao://");
            }
            openIntent(url);
            return true;
        }
        return false;
    }

    /**
     * 打开天猫详情页
     *
     * @param url
     * @return
     */
    public static boolean openTmallApp(String url) {
        if (url != null && AppUtils.isInstallApp(tmallName)) {
            if (url.startsWith("https://")) {
                url = url.replaceFirst("https://", "tmall://");
            }
            openIntent(url);
            return true;
        }
        return false;
    }

    /**
     * 打开京东详情页
     *
     * @param url
     * @return
     */
    public static boolean openJDApp(String url) {
        if (url != null && AppUtils.isInstallApp(jingdongName)) {
            if (url.startsWith("https://")) {
                url = url.replaceFirst("https://", "jingdong://");
            }
            openIntent(url);
            return true;
        }
        return false;
    }

    /**
     * 打开苏宁
     *
     * @param url
     * @return
     */
    public static boolean openSuningApp(String url) {
        if (url != null && AppUtils.isInstallApp(suningName)) {
            if (url.startsWith("http")) {
                url = "suning://m.suning.com/index?adTypeCode=1002" +
                        "&utm_source=direct&utm_midium=direct" +
                        "&utm_content=&utm_campaign=&adId=" +
                        url;
            }
            openIntent(url);
            return true;
        }
        return false;
    }

    /**
     * 打开亚马逊的app
     *
     * @param url
     * @return
     */
    public static boolean openAmazonApp(String url) {
        if (url != null && url.contains("www.amazon")) {
            if (url.startsWith("https:")) {
                url = url.replaceFirst("https:", "com.amazon.mobile.shopping.web:");
            } else if (url.startsWith("http:")) {
                url = url.replaceFirst("http:", "com.amazon.mobile.shopping.web:");
            }
            openIntent(url);
            return true;
        }
        return false;
    }

    /**
     * 打开拼多多
     *
     * @param url
     */
    public static void openPinDuoDuoApp(String url) {
        if (url != null && AppUtils.isInstallApp(pinduoduoName)) {
            if (url.startsWith("https://mobile.yangkeduo.com/app.html?launch_url=")) {
                url = url.replaceFirst("https://mobile.yangkeduo.com/app.html?launch_url=",
                        "pinduoduo://com.xunmeng.pinduoduo/");
            }
            openIntent("pinduoduo://com.xunmeng.pinduoduo/" + url);
        } else {
            openIntent(url);
        }
    }

    public static boolean openIntent(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}