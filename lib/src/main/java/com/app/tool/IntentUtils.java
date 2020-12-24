package com.app.tool;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import java.io.File;

/**
 * <pre>
 *
 *
 *     time  : 2016/09/23
 *     desc  : 意图相关工具类
 * </pre>
 */
class IntentUtils extends Util {

    /**
     * 获取安装App（支持7.0）的意图
     *
     * @param filePath  文件路径
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *                  <br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return intent
     */
    public static Intent getInstallAppIntent(String filePath, String authority) {
        return getInstallAppIntent(FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * 获取安装App(支持7.0)的意图
     *
     * @param file      文件
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *                  <br>参看https://developer.android.com/reference/android/support/v4/content/FileProvider.html
     * @return intent
     */
    public static Intent getInstallAppIntent(File file, String authority) {
        if (file == null)
            return null;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data;
        String type = "application/vnd.android.package-archive";
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            data = Uri.fromFile(file);
        } else {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            data = FileProvider.getUriForFile(getContext(), authority, file);
        }
        intent.setDataAndType(data, type);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取卸载App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getUninstallAppIntent(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取打开App的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getLaunchAppIntent(String packageName) {
        return getContext().getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * 获取App具体设置的意图
     *
     * @param packageName 包名
     * @return intent
     */
    public static Intent getAppDetailsSettingsIntent(String packageName) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享文本的意图
     *
     * @param content 分享文本
     * @return intent
     */
    public static Intent getShareTextIntent(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取分享图片的意图
     *
     * @param content   文本
     * @param imagePath 图片文件路径
     * @return intent
     */
    public static Intent getShareImageIntent(String content, String imagePath) {
        return getShareImageIntent(content, FileUtils.getFileByPath(imagePath));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 文本
     * @param image   图片文件
     * @return intent
     */
    public static Intent getShareImageIntent(String content, File image) {
        if (!FileUtils.isFileExists(image))
            return null;
        return getShareImageIntent(content, Uri.fromFile(image));
    }

    /**
     * 获取分享图片的意图
     *
     * @param content 分享文本
     * @param uri     图片uri
     * @return intent
     */
    public static Intent getShareImageIntent(String content, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @return intent
     */
    public static Intent getComponentIntent(String packageName, String className) {
        return getComponentIntent(packageName, className, null);
    }

    /**
     * 获取其他应用组件的意图
     *
     * @param packageName 包名
     * @param className   全类名
     * @param bundle      bundle
     * @return intent
     */
    public static Intent getComponentIntent(String packageName, String className, Bundle bundle) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (bundle != null)
            intent.putExtras(bundle);
        ComponentName cn = new ComponentName(packageName, className);
        intent.setComponent(cn);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取关机的意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.SHUTDOWN"/>}</p>
     *
     * @return intent
     */
    public static Intent getShutdownIntent() {
        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳至拨号界面意图
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getDialIntent(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取拨打电话意图
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param phoneNumber 电话号码
     */
    public static Intent getCallIntent(String phoneNumber) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取跳至发送短信界面的意图
     *
     * @param phoneNumber 接收号码
     * @param content     短信内容
     */
    public static Intent getSendSmsIntent(String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }


    /**
     * 获取拍照的意图
     *
     * @param outUri 输出的uri
     * @return 拍照的意图
     */
    public static Intent getCaptureIntent(Uri outUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        return intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * Android获取一个用于打开所有文件的intent
     *
     * @return
     */
    public static Intent getAllIntent(Uri uri) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * Android获取一个用于打开压缩文件的intent
     *
     * @param uri
     * @return
     */
    public static Intent getGzipIntent(Uri uri) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/x-gzip");
        return intent;
    }

    /**
     * Android获取一个用于打开APK文件的intent
     *
     * @param uri
     * @return
     */
    public static Intent getApkFileIntent(Uri uri) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * Android获取一个用于打开VIDEO文件的intent
     *
     * @param uri
     * @return
     */
    public static Intent getVideoFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    /**
     * Android获取一个用于打开AUDIO文件的intent
     *
     * @param uri
     * @return
     */
    public static Intent getAudioFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    /**
     * Android获取一个用于打开Html文件的intent
     *
     * @return
     */
    public static Intent getHtmlFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    /**
     * Android获取一个用于打开图片文件的intent
     *
     * @return
     */
    public static Intent getImageFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * Android获取一个用于打开PPT文件的intent
     *
     * @return
     */
    public static Intent getPptFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * Android获取一个用于打开Excel文件的intent
     *
     * @return
     */
    public static Intent getExcelFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * Android获取一个用于打开Word文件的intent
     *
     * @return
     */
    public static Intent getWordFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * Android获取一个用于打开CHM文件的intent
     *
     * @return
     */
    public static Intent getChmFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    /**
     * Android获取一个用于打开文本文件的intent
     *
     * @return
     */
    public static Intent getTextFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    /**
     * Android获取一个用于打开PDF文件的intent
     *
     * @return
     */
    public static Intent getPdfFileIntent(Uri uri) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }


    /**
     * Android获取一个用于打开所有文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getAllIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getAllIntent(Uri.parse(param));
        } else {
            return getAllIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开压缩文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getGzipIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getGzipIntent(Uri.parse(param));
        } else {
            return getGzipIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开APK文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getApkFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getApkFileIntent(Uri.parse(param));
        } else {
            return getApkFileIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开VIDEO文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getVideoFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getVideoFileIntent(Uri.parse(param));
        } else {
            return getVideoFileIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开AUDIO文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getAudioFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getAudioFileIntent(Uri.parse(param));
        } else {
            return getAudioFileIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开Html文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getHtmlFileIntent(String param) {
        Uri uri = Uri.parse(param)
                .buildUpon()
                .encodedAuthority("com.android.htmlfileprovider")
                .scheme("content")
                .encodedPath(param)
                .build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    /**
     * Android获取一个用于打开图片文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getImageFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getImageFileIntent(Uri.parse(param));
        } else {
            return getImageFileIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开PPT文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getPptFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getPptFileIntent(Uri.parse(param));
        } else {
            return getPptFileIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开Excel文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getExcelFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getExcelFileIntent(Uri.parse(param));
        } else {
            return getExcelFileIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开Word文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getWordFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getWordFileIntent(Uri.parse(param));
        } else {
            return getWordFileIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开CHM文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getChmFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getChmFileIntent(Uri.parse(param));
        } else {
            return getChmFileIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开文本文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getTextFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getTextFileIntent(Uri.parse(param));
        } else {
            return getTextFileIntent(Uri.fromFile(new File(param)));
        }
    }

    /**
     * Android获取一个用于打开PDF文件的intent
     *
     * @param param
     * @return
     */
    public static Intent getPdfFileIntent(String param) {
        if (UriUtils.isUriContent(param)) {
            return getPdfFileIntent(Uri.parse(param));
        } else {
            return getPdfFileIntent(Uri.fromFile(new File(param)));
        }
    }


}
