package com.app.tool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static android.content.Intent.CATEGORY_DEFAULT;

/**
 * @项目名： MyComUtils
 * @包名： com.dgtle.baselib.lib.utils
 * @创建者: Noah.冯
 * @时间: 18:56
 * @描述： 实现静默安装与卸载的工具 需要权限<uses-permission android:name="android.permission.INSTALL_PACKAGES" />
 */
class ApkUtils{

    public static boolean install(String apkPath,Context context){
        File file=new File(apkPath);
        if(!file.exists()){
            return false;
        }
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file),"application/vnd.android.package-archive");
        context.startActivity(intent);
        return true;
    }

    /**
     * 描述: 卸载
     */
    public static boolean uninstall(String packageName,Context context){
        Uri packageURI=Uri.parse("package:"+packageName);
        Intent uninstallIntent=new Intent(Intent.ACTION_DELETE,packageURI);
        uninstallIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(uninstallIntent);
        return true;
    }

    /**
     * 启动app
     */
    public static boolean startApp(String packageName,String activityName){
        boolean isSuccess=false;
        String cmd="am start -n "+packageName+"/"+activityName+" \n";
        Process process=null;
        try{
            process=Runtime.getRuntime().exec(cmd);
            int value=process.waitFor();
            return returnResult(value);
        } catch(Exception e){
            e.printStackTrace();
        } finally{
            if(process != null){
                process.destroy();
            }
        }
        return isSuccess;
    }


    private static boolean returnResult(int value){
        // 代表成功
        if(value == 0){
            return true;
        }
        else if(value == 1){ // 失败
            return false;
        }
        else{ // 未知情况
            return false;
        }
    }

    /**
     * 获取已安装Apk文件的源Apk文件
     *
     * @param context
     * @param packageName
     * @return
     */
    public static String getSourceApkPath(Context context,String packageName){
        if(StringUtils.isEmpty(packageName)){
            return null;
        }
        try{
            ApplicationInfo appInfo=context.getPackageManager().getApplicationInfo(packageName,0);
            return appInfo.sourceDir;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    /**
     * APK版本比较
     *
     * @param info 下载的apk信息
     * @return 下载的apk版本号大于当前版本号时返回true，反之返回false
     */
    public static boolean compareApkInfo(PackageInfo info){
        if(info == null){
            return false;
        }

        if(info.packageName.equals(AppUtils.getAppPackageName())){
            return info.versionCode>AppUtils.getVersionCode();
        }
        return false;
    }
}
