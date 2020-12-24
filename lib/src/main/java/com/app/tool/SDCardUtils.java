package com.app.tool;

import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <pre>
 *     time  : 2016/08/11
 *     desc  : SD卡相关工具类
 * </pre>
 */
class SDCardUtils extends PathUtils {

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean sdCardExist() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     * <p>先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径 string
     */
    public static String getSDCardPath() {
        if (!sdCardExist()) {
            return null;
        }
        return getExtStoragePath();
    }


    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间 string
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        if (!sdCardExist()) {
            return null;
        }
        StatFs stat = getSDCardInfo();
        long availableBlocks = stat.getAvailableBlocksLong();
        long blockSize = stat.getBlockSizeLong();
        return Formatter.formatFileSize(getContext(), availableBlocks * blockSize);
    }

    /**
     * 获取SD卡信息
     */
    public static StatFs getSDCardInfo() {
        if (!sdCardExist()) {
            return null;
        }
        return new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
    }

    /**
     * 刷新系统的图库
     */
    public static void refreshSystemImage(String image) {
        try {
            MediaScannerConnection.scanFile(getContext(), new String[]{image},
                    new String[]{"image/jpeg", "image/jpg", "image/png"},
                    new MediaScanner());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(UriUtils.getUriForFile(image));
        getContext().sendBroadcast(intent);
    }


    /**
     * 复制沙盒私有文件到Download公共目录下
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void androidQCopyFileToPublic(String filePath, String displayName, String title, String mimeType, PublicDirectory type, String dir) {
        androidQCopyFileToPublic(new File(filePath), displayName, title, mimeType, type, dir);
    }

    /**
     * 复制沙盒私有文件到Download公共目录下
     *
     * @param filePath    需要复制的文件
     * @param displayName 展示的名字
     * @param mimeType    类型
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void androidQCopyFileToPublic(File filePath, String displayName, String mimeType, PublicDirectory type, String dir) {
        androidQCopyFileToPublic(filePath, displayName, displayName, mimeType, type, dir);
    }

    /**
     * 复制沙盒私有文件到Download公共目录下
     *
     * @param filePath    需要复制的文件
     * @param displayName 展示的名字
     * @param mimeType    类型
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void androidQCopyFileToPublic(String filePath, String displayName, String mimeType, PublicDirectory type, String dir) {
        androidQCopyFileToPublic(filePath, displayName, displayName, mimeType, type, dir);
    }

    /**
     * 复制沙盒私有文件到Download公共目录下
     *
     * @param filePath    需要复制的文件
     * @param displayName 展示的名字
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void androidQCopyFileToPublic(File filePath, String displayName, PublicDirectory type, String dir) {
        androidQCopyFileToPublic(filePath, displayName, displayName, FileType.getMimeType(filePath), type, dir);
    }

    /**
     * 复制沙盒私有文件到Download公共目录下
     *
     * @param filePath    需要复制的文件
     * @param displayName 展示的名字
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void androidQCopyFileToPublic(String filePath, String displayName, PublicDirectory type, String dir) {
        androidQCopyFileToPublic(filePath, displayName, displayName, FileType.getMimeType(filePath), type, dir);
    }

    /**
     * 复制沙盒私有文件到Download公共目录下
     *
     * @param filePath
     * @param displayName
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void androidQCopyFileToPublicDownload(String filePath, String displayName) {
        androidQCopyFileToPublic(filePath, displayName, displayName, FileType.getMimeType(filePath), PublicDirectory.Download, AppUtils.getAppPackageName());
    }

    /**
     * 复制沙盒私有文件到Download公共目录下
     *
     * @param filePath    需要复制的文件
     * @param displayName 展示的名字
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void androidQCopyFileToPublicDownload(File filePath, String displayName) {
        androidQCopyFileToPublic(filePath, displayName, displayName, FileType.getMimeType(filePath), PublicDirectory.Download, AppUtils.getAppPackageName());
    }


    /**
     * 复制沙盒私有文件到Download公共目录下
     *
     * @param filePath    需要复制的文件
     * @param displayName 展示的名字
     * @param title       标题
     * @param mimeType    类型
     * @param type        Environment type {@link Environment.DIRECTORY_DOWNLOADS}+ DirName
     *                    ,{@link Environment.DIRECTORY_DOCUMENTS}+ DirName
     *                    ,{@link Environment.DIRECTORY_MOVIES}+ DirName
     *                    ,{@link Environment.DIRECTORY_DCIM}+ DirName
     *                    ,{@link Environment.DIRECTORY_MUSIC}+ DirName
     *                    ,{@link Environment.DIRECTORY_PICTURES}+ DirName
     *                    ,{@link Environment.DIRECTORY_SCREENSHOTS}+ DirName
     * @param dir         需要新建的文件夹名字
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static void androidQCopyFileToPublic(File filePath, String displayName, String title, String mimeType, PublicDirectory type, String dir) {
        if (filePath == null || !filePath.exists()) {
            return;
        }
        ContentValues values = new ContentValues();
        values.put(MediaStore.Files.FileColumns.DISPLAY_NAME, displayName);
        values.put(MediaStore.Files.FileColumns.MIME_TYPE, mimeType);
        values.put(MediaStore.Files.FileColumns.TITLE, title);
        values.put(MediaStore.Images.Media.RELATIVE_PATH, type.getDirectory() + "/" + dir);

        Uri external = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
        ContentResolver resolver = getContext().getContentResolver();

        Uri insertUri = resolver.insert(external, values);
        InputStream ist = null;
        OutputStream ost = null;
        try {
            ist = new FileInputStream(filePath);
            if (insertUri != null) {
                ost = resolver.openOutputStream(insertUri);
            }
            if (ost != null) {
                byte[] buffer = new byte[1024 * 8];
                int byteCount = 0;
                while ((byteCount = ist.read(buffer)) != -1) {  // 循环从输入流读取 buffer字节
                    ost.write(buffer, 0, byteCount);        // 将读取的输入流写入到输出流
                    ost.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            CloseUtils.closeIO(ist, ost);
        }
    }

    /**
     * 复制沙盒私有文件到Download公共目录下
     *
     * @param filePath
     * @return
     */
    public static boolean copyFileToPublic(File filePath, PublicDirectory type, String destName) {
        File directory = Environment.getExternalStoragePublicDirectory(type.getDirectory());
        File file = new File(directory, destName);
        return FileUtils.copyFile(filePath, file);
    }


    static class MediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

        @Override
        public void onMediaScannerConnected() {

        }

        @Override
        public void onScanCompleted(String s, Uri uri) {

        }
    }
}