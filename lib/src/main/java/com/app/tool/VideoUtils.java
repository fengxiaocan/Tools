package com.app.tool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Map;

import static android.media.ThumbnailUtils.OPTIONS_RECYCLE_INPUT;
import static android.media.ThumbnailUtils.extractThumbnail;

class VideoUtils {

    /**
     * 获取清晰的视频缩略图
     *
     * @param videoPath the video path
     * @return video thumbnail
     */
    public static Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }

    /**
     * 获取压缩的视频缩略图
     *
     * @param videoPath the video path
     * @param width     the width
     * @param height    the height
     * @param kind      the kind
     * @return video thumbnail
     */
    public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
        Bitmap bitmap;
        bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
        bitmap = extractThumbnail(bitmap, width, height, OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 创建视频缩略图
     *
     * @param filePath the file path
     * @return bitmap bitmap
     */
    public static Bitmap createVideoThumbnail(String filePath) {
        // MediaMetadataRetriever is available on API Level 8
        // but is hidden until API Level 10
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");

            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                return (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) {
                        return bitmap;
                    }
                }
                return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
            }
        } catch (Exception e) {
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {

            }
        }
        return null;
    }

    /**
     * Gets video thumb.
     *
     * @param path the path
     * @return the video thumb
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(path);
        Bitmap bitmap = mmr.getFrameAtTime();
        mmr.release();
        return bitmap;
    }

    /**
     * Gets video thumb.
     *
     * @param uri the uri
     *            the header
     * @return the video thumb
     */
    public static Bitmap getVideoThumb(String uri, Map<String, String> header) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(uri, header);
        Bitmap bitmap = mmr.getFrameAtTime();
        mmr.release();
        return bitmap;
    }

    /**
     * Gets video thumb.
     *
     * @param context the context
     * @param uri     the uri
     * @return the video thumb
     */
    public static Bitmap getVideoThumb(Context context, Uri uri) {
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(context, uri);
        Bitmap bitmap = mmr.getFrameAtTime();
        mmr.release();
        return bitmap;
    }

    /**
     * Gets video time length.
     * 获取视频长度
     * 不适用
     *
     * @param path the path
     * @return the video time length
     */
    @Deprecated
    public static int getVideoTimeLengthStandby(String path) {
        MediaPlayer meidaPlayer = new MediaPlayer();
        try {
            meidaPlayer.setDataSource(path);
            meidaPlayer.prepare();
        } catch (Exception e) {
            return 0;
        }
        int duration = meidaPlayer.getDuration();
        meidaPlayer.reset();
        return duration;//获得了视频的时长（以毫秒为单位）
    }

    /**
     * Gets video time length.
     *
     * @param file the file
     * @return the video time length
     */
    public static int getVideoTimeLength(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }
        return getVideoTimeLength(file.getAbsolutePath());
    }

    /**
     * Gets video time length.
     * 获取视频长度
     *
     * @param path the path
     * @return the video time length
     */
    public static int getVideoTimeLength(String path) {
        if (StringUtils.isEmpty(path)) {
            return 0;
        }
        int timeLength = 0;
        try {
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            String duration = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION); // 播放时长单位为毫秒
            timeLength = Integer.valueOf(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeLength;
    }

    public static boolean isVideo(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }
        String path = filePath.toUpperCase();
        return path.endsWith(".AVI") || path.endsWith(".RM") || path.endsWith(".MPEG") || path.endsWith(".MPG") ||
                path.endsWith(".MP4") || path.endsWith(".MEPG4") || path.endsWith(".MOV") || path.endsWith(".WMV");
    }

    public static boolean isVideoType(String isVideo) {
        if (TextUtils.isEmpty(isVideo)) {
            return false;
        }
        String path = isVideo.toUpperCase();
        return path.contains("AVI") || path.contains("RM") || path.contains("MPEG") || path.contains("MPG") ||
                path.contains("MP4") || path.contains("MEPG4") || path.contains("MOV") || path.contains("WMV");
    }
}
