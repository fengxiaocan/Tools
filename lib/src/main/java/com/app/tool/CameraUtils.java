package com.app.tool;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by sam on 2017/6/1.
 * 调用相机工具类
 */

class CameraUtils {

    public static final int CODE_TAKE_PHOTO = 1;
    public static final int CODE_TAKE_PHOTO_ZOOM = 2;
    public static final int CODE_ALBUM_CHOOSE = 3;

    /**
     * android Q 以上 调用相机拍照
     */
    public static Uri takePhotoAndroidQ(Activity activity, String photoName, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) != null) {
            ContentValues contentValues = new ContentValues(2);
            contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, photoName);
            contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            Uri insert = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, insert);
            activity.startActivityForResult(intent, requestCode);
            return insert;
        }
        return null;
    }

    /**
     * android Q 以上 调用相机拍照
     */
    public static Uri takePhotoAndroidQ(Activity activity, String photoName) {
        return takePhotoAndroidQ(activity, photoName, CODE_TAKE_PHOTO);
    }

    /**
     * android Q 以上 调用相机拍照
     */
    public static Uri takePhotoAndroidQ(Activity activity) {
        String photoName = System.currentTimeMillis() + ".jpg";
        return takePhotoAndroidQ(activity, photoName, CODE_TAKE_PHOTO);
    }

    /**
     * android Q 以上 调用相机拍照
     */
    public static Uri takePhotoAndroidQ(Activity activity, int requestCode) {
        String photoName = System.currentTimeMillis() + ".jpg";
        return takePhotoAndroidQ(activity, photoName, requestCode);
    }

    /**
     * android Q 以下 调用相机拍照
     */
    public static Uri takePhotoPreAndroidQ(Activity activity, Uri uri, int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        activity.startActivityForResult(intent, requestCode);
        return uri;
    }

    /**
     * android Q 以下 调用相机拍照
     */
    public static Uri takePhotoPreAndroidQ(Activity activity, File file, int requestCode) {
        Uri uri = Uri.fromFile(file);
        return takePhotoPreAndroidQ(activity, uri, requestCode);
    }

    /**
     * android Q 以下 调用相机拍照
     */
    public static Uri takePhotoPreAndroidQ(Activity activity, String file, int requestCode) {
        return takePhotoPreAndroidQ(activity, new File(file), requestCode);
    }

    /**
     * android Q 以下 调用相机拍照
     */
    public static Uri takePhotoPreAndroidQ(Activity activity, int requestCode) {
        String name = System.currentTimeMillis() + ".jpg";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), name);
        return takePhotoPreAndroidQ(activity, file, requestCode);
    }

    /**
     * android Q 以下 调用相机拍照
     */
    public static Uri takePhotoPreAndroidQ(Activity activity, String file) {
        return takePhotoPreAndroidQ(activity, new File(file), CODE_TAKE_PHOTO);
    }

    /**
     * android Q 以下 调用相机拍照
     */
    public static Uri takePhotoPreAndroidQ(Activity activity, File file) {
        return takePhotoPreAndroidQ(activity, file, CODE_TAKE_PHOTO);
    }

    /**
     * android Q 以下 调用相机拍照
     */
    public static Uri takePhotoPreAndroidQ(Activity activity) {
        String name = System.currentTimeMillis() + ".jpg";
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), name);
        return takePhotoPreAndroidQ(activity, file, CODE_TAKE_PHOTO);
    }

    /**
     * 相机拍照裁剪
     *
     * @param activity
     */
    public static void takePhotoZoom(Activity activity, Uri uri, int outputX, int outputY, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", outputX);
        intent.putExtra("outputY", outputY);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", false);
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 相机拍照裁剪
     *
     * @param activity
     */
    public static void takePhotoZoom(Activity activity, Uri uri, int outputX, int outputY) {
        takePhotoZoom(activity, uri, outputX, outputY, CODE_TAKE_PHOTO_ZOOM);
    }

    /**
     * 相册选择图片
     *
     * @param activity
     */
    public static void choosePhoto(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 相册选择图片
     *
     * @param activity
     */
    public static void choosePhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, CODE_ALBUM_CHOOSE);
    }

    /**
     * 处理图片
     *
     * @param activity
     * @param data
     * @return
     */
    public static Uri handlePhotoResult(Activity activity, Intent data) {
        Uri uri = data.getData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(activity, uri)) {
                // 如果是document类型的Uri，则通过document id进行处理
                String docId = DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                    //String id = docId.split(":")[1]; // 解析出数字格式的id
                    //String selection = MediaStore.Images.Media._ID + "=" + id;
                    return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("com.android.provides.downloads.documents".equals(uri.getAuthority())) {
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(docId));
                    return contentUri;
                }
            } else if ("content".equalsIgnoreCase(uri.getScheme())) {
                // 如果不是document类型的uri，则使用普通方式处理
                return uri;
            }
        }
        return uri;
    }
}
