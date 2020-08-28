package com.app.tool;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;

import com.app.encrypt.SHACoder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *
 *     time  : 2016/08/02
 *     desc  : App相关工具类
 * </pre>
 */
class AppUtils extends Util {

    /**
     * 判断App是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(String packageName) {
        return !StringUtils.isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    /**
     * 安装App(支持7.0)
     *
     * @param filePath  文件路径
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *                  <br>参看https://developer.android
     *                  .com/reference/android/support/v4/content
     *                  /FileProvider.html
     */
    public static void installApp(String filePath, String authority) {
        installApp(FileUtils.getFileByPath(filePath), authority);
    }

    /**
     * 安装App（支持7.0）
     *
     * @param file      文件
     * @param authority 7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *                  <br>参看https://developer.android
     *                  .com/reference/android/support/v4/content
     *                  /FileProvider.html
     */
    public static void installApp(File file, String authority) {
        if (!FileUtils.isFileExists(file)) {
            return;
        }
        getContext().startActivity(IntentUtils.getInstallAppIntent(file, authority));
    }

    /**
     * 安装App（支持6.0）
     *
     * @param activity    activity
     * @param filePath    文件路径
     * @param authority   7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *                    <br>参看https://developer.android
     *                    .com/reference/android/support/v4/content
     *                    /FileProvider.html
     * @param requestCode 请求值
     */
    public static void installApp(Activity activity, String filePath, String authority, int requestCode) {
        installApp(activity, FileUtils.getFileByPath(filePath), authority, requestCode);
    }

    /**
     * 安装App(支持6.0)
     *
     * @param activity    activity
     * @param file        文件
     * @param authority   7.0及以上安装需要传入清单文件中的{@code <provider>}的authorities属性
     *                    <br>参看https://developer.android
     *                    .com/reference/android/support/v4/content
     *                    /FileProvider.html
     * @param requestCode 请求值
     */
    public static void installApp(Activity activity, File file, String authority, int requestCode) {
        if (!FileUtils.isFileExists(file)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getInstallAppIntent(file, authority), requestCode);
    }


    /**
     * 卸载App
     *
     * @param packageName 包名
     */
    public static void uninstallApp(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        getContext().startActivity(IntentUtils.getUninstallAppIntent(packageName));
    }

    /**
     * 卸载App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void uninstallApp(Activity activity, String packageName, int requestCode) {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getUninstallAppIntent(packageName), requestCode);
    }



    /**
     * 判断App是否有root权限
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppRoot() {
        return ShellUtils.execCmd("echo root", true) == 0;
    }

    /**
     * 打开App
     *
     * @param packageName 包名
     */
    public static void launchApp(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        getContext().startActivity(IntentUtils.getLaunchAppIntent(packageName));
    }

    /**
     * 打开App
     *
     * @param activity    activity
     * @param packageName 包名
     * @param requestCode 请求值
     */
    public static void launchApp(Activity activity, String packageName, int requestCode) {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        activity.startActivityForResult(IntentUtils.getLaunchAppIntent(packageName), requestCode);
    }

    /**
     * 获取App包名
     *
     * @return App包名
     */
    public static String getAppPackageName() {
        return getContext().getPackageName();
    }

    /**
     * 获取App具体设置
     */
    public static void getAppDetailsSettings() {
        getAppDetailsSettings(getAppPackageName());
    }

    /**
     * 获取App具体设置
     *
     * @param packageName 包名
     */
    public static void getAppDetailsSettings(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return;
        }
        getContext().startActivity(IntentUtils.getAppDetailsSettingsIntent(packageName));
    }

    /**
     * 获取包的信息
     *
     * @return
     */
    public static PackageInfo getPackageInfo() {
        return getPackageInfo(getAppPackageName());
    }

    /**
     * 获取包的信息
     *
     * @param packageName
     * @return
     */
    public static PackageInfo getPackageInfo(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = getPackageManager();
            return pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取App名称
     *
     * @return App名称
     */
    public static String getAppName() {
        return getAppName(getAppPackageName());
    }

    /**
     * 获取App名称
     *
     * @param packageName 包名
     * @return App名称
     */
    public static String getAppName(String packageName) {
        PackageManager pm = getPackageManager();
        PackageInfo pi = getPackageInfo(packageName);
        return pi == null ? null : pi.applicationInfo.loadLabel(pm).toString();
    }

    /**
     * 获取App图标
     *
     * @return App图标
     */
    public static Drawable getAppIcon() {
        return getAppIcon(getAppPackageName());
    }

    /**
     * 获取App图标
     *
     * @param packageName 包名
     * @return App图标
     */
    public static Drawable getAppIcon(String packageName) {
        PackageManager pm = getPackageManager();
        PackageInfo pi = getPackageInfo(packageName);
        return pi == null ? null : pi.applicationInfo.loadIcon(pm);
    }

    /**
     * 获取App路径
     *
     * @return App路径
     */
    public static String getAppPath() {
        return getAppPath(getAppPackageName());
    }


    /**
     * [获取应用程序版本名称信息]
     *
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        PackageInfo packageInfo = getPackageInfo(getAppPackageName());
        return packageInfo.versionName;
    }

    /**
     * 获取应用程序版本号
     *
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        PackageInfo packageInfo = getPackageInfo(getAppPackageName());
        return packageInfo.versionCode;
    }


    /**
     * 获取App路径
     *
     * @param packageName 包名
     * @return App路径
     */
    public static String getAppPath(String packageName) {
        PackageInfo pi = getPackageInfo(packageName);
        return pi == null ? null : pi.applicationInfo.sourceDir;
    }

    /**
     * 获取App版本号
     *
     * @return App版本号
     */
    public static String getAppVersionName() {
        return getAppVersionName(getAppPackageName());
    }

    /**
     * 获取App版本号
     *
     * @param packageName 包名
     * @return App版本号
     */
    public static String getAppVersionName(String packageName) {
        PackageInfo pi = getPackageInfo(packageName);
        return pi == null ? null : pi.versionName;
    }

    /**
     * 获取App版本码
     *
     * @return App版本码
     */
    public static int getAppVersionCode() {
        return getAppVersionCode(getAppPackageName());
    }

    /**
     * 获取App版本码
     *
     * @param packageName 包名
     * @return App版本码
     */
    public static int getAppVersionCode(String packageName) {
        PackageInfo pi = getPackageInfo(packageName);
        return pi == null ? -1 : pi.versionCode;
    }

    /**
     * 判断App是否是系统应用
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp() {
        return isSystemApp(getAppPackageName());
    }

    /**
     * 判断App是否是系统应用
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSystemApp(String packageName) {
        ApplicationInfo ai = getApplicationInfo(packageName, 0);
        return ai != null && (ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    /**
     * 获取Application信息
     *
     * @return ApplicationInfo
     */
    public static ApplicationInfo getApplicationInfo(String packageName, int packageManagerType) {
        PackageManager pm = getPackageManager();
        ApplicationInfo ai = null;
        try {
            ai = pm.getApplicationInfo(packageName, packageManagerType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ai;
    }

    /**
     * 获取Application信息
     *
     * @param packageManagerType
     * @return ApplicationInfo
     */
    public static ApplicationInfo getApplicationInfo(int packageManagerType) {
        return getApplicationInfo(getAppPackageName(), packageManagerType);
    }

    /**
     * 获取Application信息
     *
     * @return ApplicationInfo
     */
    public static PackageManager getPackageManager() {
        return getContext().getPackageManager();
    }


    /**
     * 获取清单文件信息
     *
     * @param key
     * @param defultVel
     * @return
     */
    public static String getManifestMetaData(String key, String defultVel) {
        String keyString = defultVel;
        try {
            ApplicationInfo appInfo = getApplicationInfo(PackageManager.GET_META_DATA);
            keyString = appInfo.metaData.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyString;
    }

    /**
     * Return the value of meta-data in activity.
     *
     * @param activity The activity.
     * @param key      The key of meta-data.
     * @return the value of meta-data in activity
     */
    public static String getActivityMetaData(@NonNull final Activity activity, @NonNull final String key) {
        return getActivityMetaData(activity.getClass(), key);
    }

    /**
     * Return the value of meta-data in activity.
     *
     * @param clz The activity class.
     * @param key The key of meta-data.
     * @return the value of meta-data in activity
     */
    public static String getActivityMetaData(@NonNull final Class<? extends Activity> clz, @NonNull final String key) {
        String value = "";
        PackageManager pm = getContext().getPackageManager();
        ComponentName componentName = new ComponentName(getContext(), clz);
        try {
            ActivityInfo ai = pm.getActivityInfo(componentName, PackageManager.GET_META_DATA);
            value = String.valueOf(ai.metaData.get(key));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Return the value of meta-data in service.
     *
     * @param service The service.
     * @param key     The key of meta-data.
     * @return the value of meta-data in service
     */
    public static String getServiceMetaData(@NonNull final Service service, @NonNull final String key) {
        return getServiceMetaData(service.getClass(), key);
    }

    /**
     * Return the value of meta-data in service.
     *
     * @param clz The service class.
     * @param key The key of meta-data.
     * @return the value of meta-data in service
     */
    public static String getServiceMetaData(@NonNull final Class<? extends Service> clz, @NonNull final String key) {
        String value = "";
        PackageManager pm = getContext().getPackageManager();
        ComponentName componentName = new ComponentName(getContext(), clz);
        try {
            ServiceInfo info = pm.getServiceInfo(componentName, PackageManager.GET_META_DATA);
            value = String.valueOf(info.metaData.get(key));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * Return the value of meta-data in receiver.
     *
     * @param receiver The receiver.
     * @param key      The key of meta-data.
     * @return the value of meta-data in receiver
     */
    public static String getReceiverMetaData(@NonNull final BroadcastReceiver receiver, @NonNull final String key) {
        return getReceiverMetaData(receiver, key);
    }

    /**
     * Return the value of meta-data in receiver.
     *
     * @param clz The receiver class.
     * @param key The key of meta-data.
     * @return the value of meta-data in receiver
     */
    public static String getReceiverMetaData(@NonNull final Class<? extends BroadcastReceiver> clz,
                                               @NonNull final String key) {
        String value = "";
        PackageManager pm = getContext().getPackageManager();
        ComponentName componentName = new ComponentName(getContext(), clz);
        try {
            ActivityInfo info = pm.getReceiverInfo(componentName, PackageManager.GET_META_DATA);
            value = String.valueOf(info.metaData.get(key));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return value;
    }


    /**
     * 判断App是否是Debug版本
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug() {
        return (getContext().getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * 判断App是否是Debug版本
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppDebug(String packageName) {
        ApplicationInfo ai = getApplicationInfo(packageName, 0);
        return ai != null && (ai.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
    }

    /**
     * 获取App签名
     *
     * @return App签名
     */
    public static Signature[] getAppSignature() {
        return getAppSignature(getAppPackageName());
    }

    /**
     * 获取App签名
     *
     * @param packageName 包名
     * @return App签名
     */
    public static Signature[] getAppSignature(String packageName) {
        if (StringUtils.isSpace(packageName)) {
            return null;
        }
        try {
            PackageManager pm = getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            return pi == null ? null : pi.signatures;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    /**
     * 获取应用签名的的SHA1值
     * <p>可据此判断高德，百度地图key是否正确</p>
     *
     * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88
     * :1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1() {
        return getAppSignatureSHA1(getAppPackageName());
    }

    /**
     * 获取应用签名的的SHA1值
     * <p>可据此判断高德，百度地图key是否正确</p>
     *
     * @param packageName 包名
     * @return 应用签名的SHA1字符串, 比如：53:FD:54:DC:19:0F:11:AC:B5:22:9E:F1:1A:68:88
     * :1B:8B:E8:54:42
     */
    public static String getAppSignatureSHA1(String packageName) {
        Signature[] signature = getAppSignature(packageName);
        if (signature == null) {
            return null;
        }
        return SHACoder.SHA1.codeToHex(signature[0].toByteArray()).
                replaceAll("(?<=[0-9A-F]{2})[0-9A-F]{2}", ":$0");
    }

    /**
     * 判断App是否处于前台
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground() {
        return isAppForeground(getAppPackageName());
    }

    /**
     * 判断App是否处于前台
     * <p>当不是查看当前App，且SDK大于21时，
     * 需添加权限 {@code <uses-permission android:name="android.permission
     * .PACKAGE_USAGE_STATS"/>}</p>
     *
     * @param packageName 包名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isAppForeground(String packageName) {
        List<ActivityManager.RunningAppProcessInfo> info = getRunningAppProcessInfo();
        if (info == null || info.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo aInfo : info) {
            if (aInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                return aInfo.processName.equals(packageName);
            }
        }
        return false;
    }

    /**
     * 获取App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
     *
     * @return 当前应用的AppInfo
     */
    public static AppInfo getAppInfo() {
        return getAppInfo(getAppPackageName());
    }

    /**
     * 获取App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
     *
     * @param packageName 包名
     * @return 当前应用的AppInfo
     */
    public static AppInfo getAppInfo(String packageName) {
        PackageManager pm = getPackageManager();
        PackageInfo pi = getPackageInfo(packageName);
        return getBean(pm, pi);
    }

    /**
     * 得到AppInfo的Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo类
     */
    private static AppInfo getBean(PackageManager pm, PackageInfo pi) {
        if (pm == null || pi == null) {
            return null;
        }
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(packageName, name, icon, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * 获取所有已安装App信息
     * <p>{@link #getBean(PackageManager, PackageInfo)}（名称，图标，包名，包路径，版本号，版本Code，是否系统应用）</p>
     * <p>依赖上面的getBean方法</p>
     *
     * @return 所有已安装的AppInfo列表
     */
    public static List<AppInfo> getAppsInfo() {
        List<AppInfo> list = new ArrayList<>();
        PackageManager pm = getPackageManager();
        // 获取系统中安装的所有软件信息
        List<PackageInfo> installedPackages = pm.getInstalledPackages(0);
        for (PackageInfo pi : installedPackages) {
            AppInfo ai = getBean(pm, pi);
            if (ai == null) {
                continue;
            }
            list.add(ai);
        }
        return list;
    }

    /**
     * 清除App所有数据
     *
     * @param dirPaths 目录路径
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(String... dirPaths) {
        File[] dirs = new File[dirPaths.length];
        int i = 0;
        for (String dirPath : dirPaths) {
            dirs[i++] = new File(dirPath);
        }
        return cleanAppData(dirs);
    }

    /**
     * 清除App所有数据
     *
     * @param dirs 目录
     * @return {@code true}: 成功<br>{@code false}: 失败
     */
    public static boolean cleanAppData(File... dirs) {
        boolean isSuccess = CleanUtils.cleanInternalCache();
        isSuccess &= CleanUtils.cleanInternalDatabases();
        isSuccess &= CleanUtils.cleanPreferences();
        isSuccess &= CleanUtils.cleanInternalFiles();
        isSuccess &= CleanUtils.cleanExternalCache();
        isSuccess &= CleanUtils.cleanExternalFiles();
        for (File dir : dirs) {
            isSuccess &= FileUtils.deleteDir(dir);
        }
        return isSuccess;
    }



    /**
     * 关闭自身app
     */
    public static void exitApp() {
        //退出程序
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    /**
     * 获取当前正在运行的进程信息
     *
     * @return
     */
    public static List<ActivityManager.RunningAppProcessInfo> getRunningAppProcessInfo() {
        ActivityManager am = (ActivityManager) getContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        return runningApps;
    }

    /**
     * 获取进程名
     *
     * @param pid
     * @return
     */
    public static String getProcessName(int pid) {
        List<ActivityManager.RunningAppProcessInfo> runningApps = getRunningAppProcessInfo();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }

    /**
     * 获取当前进程名
     *
     * @return
     */
    public static String getProcessName() {
        return getProcessName(android.os.Process.myPid());
    }

    /**
     * 获取运行时最大内存
     *
     * @return
     */
    public static long getRuntimeMaxMemory() {
        return Runtime.getRuntime().maxMemory();
    }

    /**
     * 获取运行时空闲内存
     *
     * @return
     */
    public static long getRuntimeFreeMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    /**
     * 获取运行时最大内存
     *
     * @return
     */
    public static long getRuntimeTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    /**
     * 是否忽略电池优化
     *
     * @return
     */
    public static boolean isIgnoreBatteryOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
            return powerManager.isIgnoringBatteryOptimizations(getAppPackageName());
        }
        return true;
    }

    /**
     * 打开电池优化
     */
    @RequiresPermission(value = Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
    public static void openIgnoreBatteryOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
            intent.setData(Uri.parse("package:" + getAppPackageName()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        }
    }

    /**
     * 打开HuaWei电池优化
     */
    @RequiresPermission(value = Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
    public static void openHuaWeiIgnoreBatteryOptimization() {
            Intent intent = new Intent(getAppPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity");
            intent.setComponent(comp);
            //检测是否有能接受该Intent的Activity存在
            List<ResolveInfo> resolveInfos = getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfos.size() > 0) {
                getContext().startActivity(intent);
            }
    }
    /**
     * 打开XiaoMi电池优化
     */
    @RequiresPermission(value = Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
    public static void openXiaoMiIgnoreBatteryOptimization() {
            Intent intent = new Intent(getAppPackageName());
            ComponentName componentName = new ComponentName("com.miui.powerkeeper", "com.miui.powerkeeper.ui.HiddenAppsConfigActivity");
            intent.setComponent(componentName);
            intent.putExtra("package_name", getAppPackageName());
            intent.putExtra("package_label", getAppName());
            //检测是否有能接受该Intent的Activity存在
            List<ResolveInfo> resolveInfos = getContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            if (resolveInfos.size() > 0) {
                getContext().startActivity(intent);
            }
    }

    /**
     * /data/user/0/{包名}/app_{name}
     *
     * @param name
     * @return
     */
    public static File getInternalAppDir(String name) {
        return getContext().getDir(name, Context.MODE_PRIVATE);
    }

    /**
     * /data/user/0/{包名}/app_{name}
     *
     * @param dirName
     * @param fileName
     * @return
     */
    public static File getInternalAppFile(String dirName, String fileName) {
        File file = new File(getInternalAppDir(dirName), fileName);
        file.getParentFile().mkdirs();
        return file;
    }

    /**
     * 获取 Preferences的文件夹
     *
     * @return
     */
    public static File getPreferencesDir() {
        return getInternalDataDir("shared_prefs");
    }

    /**
     * 获取 /data/user/0/{包名}
     *
     * @return
     */
    public static File getInternalDataDir() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return getContext().getDataDir();
        } else {
            return getContext().getFilesDir().getParentFile();
        }
    }

    /**
     * 获取data目录下的文件夹
     *
     * @param name
     * @return
     */
    public static File getInternalDataDir(String name) {
        File dir = new File(getInternalDataDir(), name);
        dir.mkdirs();
        return dir;
    }

    /**
     * 获取data目录下的文件
     *
     * @param name
     * @return
     */
    public static File getInternalDataFile(String name) {
        return new File(getInternalDataDir(), name);
    }

    /**
     * 获取data目录下的文件夹
     *
     * @param dirName  文件夹的名字
     * @param fileName 文件的名字
     * @return
     */
    public static File getInternalDataFile(String dirName, String fileName) {
        return new File(getInternalDataDir(dirName), fileName);
    }

    /**
     * 获取App内部code_cache文件夹
     *
     * @return
     */
    public static File getCodeCacheDir() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getContext().getCodeCacheDir();
        } else {
            File code_cache = new File(getContext().getFilesDir().getParentFile(), "code_cache");
            code_cache.mkdirs();
            return code_cache;
        }
    }

    public static File getCodeCacheDir(String name) {
        File dir = new File(getCodeCacheDir(), name);
        dir.mkdirs();
        return dir;
    }

    /**
     * 获取App内部code_cache文件
     *
     * @return
     */
    public static File getCodeCacheFile(String fileName) {
        return new File(getCodeCacheDir(), fileName);
    }

    /**
     * 获取App内部code_cache文件
     *
     * @return
     */
    public static File getCodeCacheFile(String dirName, String fileName) {
        return new File(getCodeCacheDir(dirName), fileName);
    }

    /**
     * 获取App内部cache文件夹
     *
     * @return
     */
    public static File getInternalCacheDir() {
        return getContext().getCacheDir();
    }

    /**
     * 获取App内部cache文件夹
     *
     * @return
     */
    public static File getInternalCacheDir(String name) {
        File dir = new File(getInternalCacheDir(), name);
        dir.mkdirs();
        return dir;
    }

    /**
     * 获取cache里面的文件
     *
     * @param name
     * @return
     */
    public static File getInternalCacheFile(String name) {
        return new File(getInternalCacheDir(), name);
    }


    /**
     * 获取cache里面的文件
     *
     * @param dirName
     * @param fileName
     * @return
     */
    public static File getInternalCacheFile(String dirName, String fileName) {
        return new File(getInternalCacheDir(dirName), fileName);
    }


    /**
     * 获取App内部file文件夹
     *
     * @return
     */
    public static File getInternalFileDir() {
        return getContext().getFilesDir();
    }

    /**
     * 获取App内部file文件夹
     *
     * @return
     */
    public static File getInternalFileDir(String name) {
        File dir = getContext().getFileStreamPath(name);
        dir.mkdirs();
        return dir;
    }

    /**
     * 获取App内部file文件
     *
     * @return
     */
    public static File getInternalFile(String name) {
        return getContext().getFileStreamPath(name);
    }

    /**
     * 获取App内部file文件
     *
     * @return
     */
    public static File getInternalFile(String dirName, String fileName) {
        return new File(getInternalFileDir(dirName), fileName);
    }

    /**
     * 获取App内部database文件夹
     *
     * @return
     */
    public static File getInternalDatabaseDir() {
        return getContext().getDatabasePath("a").getParentFile();
    }

    /**
     * 获取App内部database文件夹
     *
     * @return
     */
    public static File getInternalDatabaseDir(String name) {
        File dir = getContext().getDatabasePath(name);
        dir.mkdirs();
        return dir;
    }

    /**
     * 获取App内部database文件
     *
     * @param name
     * @return
     */
    public static File getInternalDatabaseFile(String name) {
        return getContext().getDatabasePath(name);
    }

    /**
     * 获取App内部database文件
     *
     * @param dirName
     * @param fileName
     * @return
     */
    public static File getInternalDatabaseFile(String dirName, String fileName) {
        return new File(getInternalDatabaseDir(dirName), fileName);
    }

    /**
     * storage/emulated/0/Android/obb/{包名}
     *
     * @return
     */
    public static File getObbDir() {
        return getContext().getObbDir();
    }

    /**
     * storage/emulated/0/Android/obb/{包名}/{name}
     *
     * @return
     */
    public static File getObbDir(String name) {
        File dir = new File(getObbDir(), name);
        dir.mkdirs();
        return dir;
    }

    /**
     * storage/emulated/0/Android/obb/{包名}/{name}
     *
     * @return
     */
    public static File getObbFile(String name) {
        return new File(getObbDir(), name);
    }

    /**
     * storage/emulated/0/Android/obb/{包名}/{dir}/{name}
     *
     * @return
     */
    public static File getObbFile(String dirName, String fileName) {
        return new File(getObbDir(dirName), fileName);
    }


    /**
     * /storage/emulated/0/Android/data/{包名}/cache
     *
     * @return
     */
    public static File getExternalCacheDir() {
        return getContext().getExternalCacheDir();
    }

    /**
     * /storage/emulated/0/Android/data/{包名}/cache/{dir}
     *
     * @return
     */
    public static File getExternalCacheDir(String name) {
        File dir = new File(getExternalCacheDir(), name);
        dir.mkdirs();
        return dir;
    }

    /**
     * /storage/emulated/0/Android/data/{包名}/cache/{name}
     *
     * @return
     */
    public static File getExternalCacheFile(String name) {
        return new File(getExternalCacheDir(), name);
    }

    /**
     * /storage/emulated/0/Android/data/{包名}/cache/{dir}/{name}
     *
     * @return
     */
    public static File getExternalCacheFile(String dirName, String fileName) {
        return new File(getExternalCacheDir(dirName), fileName);
    }

    /**
     * /storage/emulated/0/Android/data/{包名}/files
     *
     * @return
     */
    public static File getExternalFilesDir() {
        return getContext().getExternalFilesDir("");
    }

    /**
     * /storage/emulated/0/Android/data/{包名}/files/{dir}
     *
     * @return
     */
    public static File getExternalFilesDir(String name) {
        File dir = getContext().getExternalFilesDir(name);
        dir.mkdirs();
        return dir;
    }

    /**
     * /storage/emulated/0/Android/data/{包名}/files/{name}
     *
     * @return
     */
    public static File getExternalFile(String name) {
        return getContext().getExternalFilesDir(name);
    }

    /**
     * /storage/emulated/0/Android/data/{包名}/files/{dir}/{name}
     *
     * @return
     */
    public static File getExternalFile(String dirName, String fileName) {
        return new File(getExternalFilesDir(dirName), fileName);
    }

    /**
     * 封装App信息的Bean类
     */
    public static class AppInfo {

        private String name;
        private Drawable icon;
        private String packageName;
        private String packagePath;
        private String versionName;
        private int versionCode;
        private boolean isSystem;

        /**
         * @param name        名称
         * @param icon        图标
         * @param packageName 包名
         * @param packagePath 包路径
         * @param versionName 版本号
         * @param versionCode 版本码
         * @param isSystem    是否系统应用
         */
        public AppInfo(String packageName, String name, Drawable icon, String packagePath, String versionName,
                       int versionCode, boolean isSystem) {
            this.setName(name);
            this.setIcon(icon);
            this.setPackageName(packageName);
            this.setPackagePath(packagePath);
            this.setVersionName(versionName);
            this.setVersionCode(versionCode);
            this.setSystem(isSystem);
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(boolean isSystem) {
            this.isSystem = isSystem;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(String packagePath) {
            this.packagePath = packagePath;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        @Override
        public String toString() {
            return "pkg name: " + getPackageName() + "\napp name: " + getName() + "\napp path: " + getPackagePath() + "\napp v name: " + getVersionName() + "\napp v code: " + getVersionCode() + "\nis system: " + isSystem();
        }
    }
}