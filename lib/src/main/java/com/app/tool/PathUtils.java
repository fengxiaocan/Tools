package com.app.tool;

import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

class PathUtils extends Util {

    /**
     * 获取 Android 系统根目录
     * <pre>path: /system</pre>
     *
     * @return 系统根目录
     */
    public static String getRootPath() {
        return Environment.getRootDirectory().getAbsolutePath();
    }

    /**
     * 获取 data 目录
     * <pre>path: /data</pre>
     *
     * @return data 目录
     */
    public static String getDataPath() {
        return Environment.getDataDirectory().getAbsolutePath();
    }

    /**
     * 获取缓存目录
     * <pre>path: data/cache</pre>
     *
     * @return 缓存目录
     */
    public static String getIntDownloadCachePath() {
        return Environment.getDownloadCacheDirectory().getAbsolutePath();
    }

    /**
     * 获取此应用的缓存目录
     * <pre>path: /data/data/package/cache</pre>
     *
     * @return 此应用的缓存目录
     */
    public static String getAppIntCachePath() {
        return getContext().getCacheDir().getAbsolutePath();
    }

    /**
     * 获取此应用的文件目录
     * <pre>path: /data/data/package/files</pre>
     *
     * @return 此应用的文件目录
     */
    public static String getAppIntFilesPath() {
        return getContext().getFilesDir().getAbsolutePath();
    }

    /**
     * 获取此应用的数据库文件目录
     * <pre>path: /data/data/package/databases/name</pre>
     *
     * @param name 数据库文件名
     * @return 数据库文件目录
     */
    public static String getAppIntDbPath(String name) {
        return getContext().getDatabasePath(name).getAbsolutePath();
    }

    /**
     * 获取 Android 外置储存的根目录
     * <pre>path: /storage/emulated/0</pre>
     *
     * @return 外置储存根目录
     */
    public static String getExtStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取闹钟铃声目录
     * <pre>path: /storage/emulated/0/Alarms</pre>
     *
     * @return 闹钟铃声目录
     */
    public static String getExtAlarmsPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).getAbsolutePath();
    }

    /**
     * 获取相机拍摄的照片和视频的目录
     * <pre>path: /storage/emulated/0/DCIM</pre>
     *
     * @return 照片和视频目录
     */
    public static String getExtDCIMPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * 获取文档目录
     * <pre>path: /storage/emulated/0/Documents</pre>
     *
     * @return 文档目录
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getExtDocumentsPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    }

    /**
     * 获取下载目录
     * <pre>path: /storage/emulated/0/Download</pre>
     *
     * @return 下载目录
     */
    public static String getExtDownloadsPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * 获取视频目录
     * <pre>path: /storage/emulated/0/Movies</pre>
     *
     * @return 视频目录
     */
    public static String getExtMoviesPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

    /**
     * 获取音乐目录
     * <pre>path: /storage/emulated/0/Music</pre>
     *
     * @return 音乐目录
     */
    public static String getExtMusicPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * 获取提示音目录
     * <pre>path: /storage/emulated/0/Notifications</pre>
     *
     * @return 提示音目录
     */
    public static String getExtNotificationsPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
    }

    /**
     * 获取图片目录
     * <pre>path: /storage/emulated/0/Pictures</pre>
     *
     * @return 图片目录
     */
    public static String getExtPicturesPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    /**
     * 获取 Podcasts 目录
     * <pre>path: /storage/emulated/0/Podcasts</pre>
     *
     * @return Podcasts 目录
     */
    public static String getExtPodCastsPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).getAbsolutePath();
    }

    /**
     * 获取铃声目录
     * <pre>path: /storage/emulated/0/Ringtones</pre>
     *
     * @return 下载目录
     */
    public static String getExtRingTonesPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的缓存目录
     * <pre>path: /storage/emulated/0/Android/data/package/cache</pre>
     *
     * @return 此应用在外置储存中的缓存目录
     */
    public static String getAppExtCachePath() {
        return getContext().getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的文件目录
     * <pre>path: /storage/emulated/0/Android/data/package/files</pre>
     *
     * @return 此应用在外置储存中的文件目录
     */
    public static String getAppExtFilePath() {
        return getContext().getExternalFilesDir(null).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的闹钟铃声目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Alarms</pre>
     *
     * @return 此应用在外置储存中的闹钟铃声目录
     */
    public static String getAppExtAlarmsPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_ALARMS).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的相机目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/DCIM</pre>
     *
     * @return 此应用在外置储存中的相机目录
     */
    public static String getAppExtDCIMPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的文档目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Documents</pre>
     *
     * @return 此应用在外置储存中的文档目录
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getAppExtDocumentsPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的闹钟目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Download</pre>
     *
     * @return 此应用在外置储存中的闹钟目录
     */
    public static String getAppExtDownloadPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的视频目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Movies</pre>
     *
     * @return 此应用在外置储存中的视频目录
     */
    public static String getAppExtMoviesPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_MOVIES).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的音乐目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Music</pre>
     *
     * @return 此应用在外置储存中的音乐目录
     */
    public static String getAppExtMusicPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的提示音目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Notifications</pre>
     *
     * @return 此应用在外置储存中的提示音目录
     */
    public static String getAppExtNotificationsPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_NOTIFICATIONS).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的图片目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Pictures</pre>
     *
     * @return 此应用在外置储存中的图片目录
     */
    public static String getAppExtPicturesPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的 Podcasts 目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Podcasts</pre>
     *
     * @return 此应用在外置储存中的 Podcasts 目录
     */
    public static String getAppExtPodCastsPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_PODCASTS).getAbsolutePath();
    }

    /**
     * 获取此应用在外置储存中的铃声目录
     * <pre>path: /storage/emulated/0/Android/data/package/files/Ringtones</pre>
     *
     * @return 此应用在外置储存中的铃声目录
     */
    public static String getAppExtRingTonesPath() {
        return getContext().getExternalFilesDir(Environment.DIRECTORY_RINGTONES).getAbsolutePath();
    }

    /**
     * 获取此应用的 Obb 目录
     * <pre>path: /storage/emulated/0/Android/obb/package</pre>
     * <pre>一般用来存放游戏数据包</pre>
     *
     * @return 此应用的 Obb 目录
     */
    public static String getObbPath() {
        return getContext().getObbDir().getAbsolutePath();
    }

}
