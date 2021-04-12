package com.app.tool;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

class ActivityUtils {

    /**
     * 创建快捷方式
     * 首先生成一个Intent实例（隐式Intent），然后向intent中放入一些值来配置
     * 1.是否允许重复创建
     * 2.确定创建的图标的名称
     * 3.确定创建的图标的图片
     * 4.设置点击这个快捷图标就运行该APP
     * 你可以在你的APP第一次运行时在第一个Activity中调用这个类的静态方法，就实现了快捷图标的创建
     *
     * @param activity
     * @param iconResId
     */
    public static void createActivityLauncher(Activity activity, int iconResId, String shortcutName) {
        //<uses-permission android:name="com.android.launcher.permission
        // .INSTALL_SHORTCUT"/>
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity.checkSelfPermission(Manifest.permission.INSTALL_SHORTCUT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 不允许重复创建
        intent.putExtra(activity.getLocalClassName(), false);
        // 需要现实的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
        // 快捷图片
        Context appContext = activity.getApplicationContext();
        Parcelable icon = Intent.ShortcutIconResource.fromContext(appContext, iconResId);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        // 点击快捷图片，运行程序
        Intent value = new Intent(appContext, activity.getClass());
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, value);
        // 发送广播
        activity.sendBroadcast(intent);
    }

    /**
     * 判断是否存在Activity
     *
     * @param packageName 包名
     * @param className   activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(Context context, String packageName, String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName, className);
        return !(context.getPackageManager().resolveActivity(intent, 0) == null
                || intent.resolveActivity(context.getPackageManager()) == null
                || context.getPackageManager().queryIntentActivities(intent, 0).size() == 0);
    }


    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     */
    public static void startActivity(Context context, Class<? extends Activity> clazz) {
        startActivity(context, clazz, null);
    }

    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     */
    public static void startActivity(Context context, Class<? extends Activity> clazz, Bundle extras) {
        Intent intent = new Intent(context, clazz);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    /**
     * 打开本应用的设置界面
     */
    public static void openSettingActivity(Context context) {
        openSettingActivity(context, context.getPackageName());
    }

    /**
     * 根据包名打开应用的设置界面
     */
    public static void openSettingActivity(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", packageName, null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 获取Activity根节点View
     *
     * @return
     */
    public static View getRootView(Activity activity) {
        return ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 获取Activity根节点Group
     */
    public static ViewGroup getRootViewGroup(Activity activity) {
        return activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * 设置Activity全屏
     */
    public static void fullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 屏幕常亮
     *
     * @param activity
     * @param tag
     * @return
     */
    public static PowerManager.WakeLock keepScreen(Activity activity, String tag) {
        activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        PowerManager powerManager = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            return powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, tag);
        }
        return null;
    }
}
