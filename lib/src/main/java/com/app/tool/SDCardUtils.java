package com.app.tool;

import android.annotation.TargetApi;
import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.format.Formatter;

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
        return Formatter.formatFileSize(getContext(),availableBlocks * blockSize);
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


    static class MediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {

        @Override
        public void onMediaScannerConnected() {

        }

        @Override
        public void onScanCompleted(String s, Uri uri) {

        }
    }
}