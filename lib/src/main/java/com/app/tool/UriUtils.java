package com.app.tool;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

public final class UriUtils extends Util {
    public static final String ASSET_BASE = "file:///android_asset/";
    public static final String RESOURCE_BASE = "file:///android_res/";
    public static final String FILE_BASE = "file:";
    public static final String FILE_BASE_URL = "file://";
    public static final String CONTENT_BASE = "content:";

    private UriUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 从file转成Uri
     *
     * @param file
     * @return
     */
    public static Uri getUriForFile(final File file) {
        if (file == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = AppUtils.getAppPackageName() + ".provider";
            return FileProvider.getUriForFile(getContext(), authority, file);
        } else {
            return Uri.fromFile(file);
        }
    }

    /**
     * 从file转成Uri
     *
     * @param file
     * @return
     */
    public static Uri getUriForFile(String file) {
        return getUriForFile(new File(file));
    }

    /**
     * Uri转文件
     *
     * @param uri
     * @return
     */
    public static File uriToFile(Uri uri) {
        String data = queryUriColumn(uri, MediaStore.Images.Media.DATA);
        if (!TextUtils.isEmpty(data)) {
            return new File(data);
        }
        return null;
    }

    /**
     * 获取Uri的InputStream
     *
     * @param uri
     * @return
     * @throws FileNotFoundException
     */
    public static InputStream openInputStream(Uri uri) throws FileNotFoundException {
        ContentResolver contentResolver = getContext().getContentResolver();
        return contentResolver.openInputStream(uri);
    }

    public static OutputStream openOutputStream(Uri uri) throws FileNotFoundException {
        ContentResolver contentResolver = getContext().getContentResolver();
        return contentResolver.openOutputStream(uri);
    }

    /**
     * 获取Uri的文件类型
     *
     * @param uri
     * @return
     */
    public static String getUriMimeType(Uri uri) {
        //这里选择先使用读取文件流获取文件类型是因为系统Uri的gif默认类型都为PNG格式
        String type = ImageType.readType(uri);
        if (type != null) {
            return type;
        } else {
            ContentResolver contentResolver = getContext().getContentResolver();
            String resolverType = contentResolver.getType(uri);
            if (resolverType != null) {
                return queryUriColumn(uri, MediaStore.Images.ImageColumns.MIME_TYPE);
            } else {
                return resolverType;
            }
        }
    }

    /**
     * 获取Uri文件的长度
     *
     * @param uri
     * @return
     */
    public static long getUriLenght(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        long column = 0;
        ParcelFileDescriptor descriptor = null;
        try {
            descriptor = contentResolver.openFileDescriptor(uri, "r");
            column = descriptor.getStatSize();
        } catch (Exception e) {
        } finally {
            CloseUtils.closeIO(descriptor);
        }
        if (column <= 0) {
            return queryUriColumnV1(uri, MediaStore.Images.ImageColumns.SIZE);
        } else {
            return column;
        }
    }


    /**
     * 查询Uri的文件的列表类型值
     *
     * @param columns
     * @return
     */
    private static String queryUriColumn(Uri uri, String columns) {
        String column = null;
        // 4.2.2以后
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = null;
        if ("file".equals(uri.getScheme())) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                StringBuffer buff = new StringBuffer();
                buff.append("(")
                        .append(MediaStore.Images.ImageColumns.DATA)
                        .append("=")
                        .append("'" + path + "'")
                        .append(")");
                String[] projection = {MediaStore.Images.ImageColumns._ID, columns};
                cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        buff.toString(),
                        null,
                        null);

                if (cursor.moveToLast()) {
                    int dataIdx = cursor.getColumnIndex(columns);
                    column = cursor.getString(dataIdx);
                }
            }
        } else if ("content".equals(uri.getScheme())) {
            cursor = contentResolver.query(uri, new String[]{columns}, null, null, null);
            if (cursor.moveToLast()) {
                int columnIndex = cursor.getColumnIndex(columns);
                column = cursor.getString(columnIndex);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return column;
    }

    static long queryUriColumnV1(Uri uri, String columns) {

        long column = 0;
        // 4.2.2以后
        ContentResolver contentResolver = getContext().getContentResolver();
        Cursor cursor = null;
        if ("file".equals(uri.getScheme())) {
            String path = uri.getEncodedPath();
            if (path != null) {
                path = Uri.decode(path);
                StringBuffer buff = new StringBuffer();
                buff.append("(")
                        .append(MediaStore.Images.ImageColumns.DATA)
                        .append("=")
                        .append("'")
                        .append(path)
                        .append("'")
                        .append(")");
                String[] projection = {MediaStore.Images.ImageColumns._ID, columns};
                cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection,
                        buff.toString(),
                        null,
                        null);

                if (cursor.moveToLast()) {
                    int dataIdx = cursor.getColumnIndex(columns);
                    column = cursor.getLong(dataIdx);
                }
            }
        } else if ("content".equals(uri.getScheme())) {
            cursor = contentResolver.query(uri, new String[]{columns}, null, null, null);
            if (cursor.moveToLast()) {
                int columnIndex = cursor.getColumnIndex(columns);
                column = cursor.getLong(columnIndex);
            }
        }
        if (column == 0) {
            try {
                AssetFileDescriptor descriptor = getContext().getContentResolver().openAssetFileDescriptor(uri, "r");
                column = descriptor.getLength();
                CloseUtils.closeIO(descriptor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return column;
    }

    /**
     * 把Uri文件复制到指定文件夹下
     *
     * @param uri
     * @return
     */
    public static File copyUriToDir(Uri uri, File parentDir) {
        if (parentDir != null) {
            parentDir.mkdir();
            File uriToFile = uriToFile(uri);
            String name = uriToFile.getName();
            File file = new File(parentDir, name);
            if (file.exists() && file.length() == uriToFile.length()) {
                return file;
            } else {
                FileUtils.copyFile(uri, file);
                return file;
            }
        } else {
            return null;
        }
    }

    public static File moveUriToFile(Uri uri, File file) {
        if (file != null) {
            File uriToFile = uriToFile(uri);
            if (file.exists() && file.length() == uriToFile.length()) {
                return file;
            } else {
                FileUtils.copyFile(uri, file);
                return file;
            }
        } else {
            return null;
        }
    }

    public static File copyUriToCacheFile(Uri uri) {
        File uriToFile = uriToFile(uri);
        String name = uriToFile.getName();
        File file = new File(getContext().getCacheDir(), name);
        if (!file.exists() || file.length() != uriToFile.length()) {
            FileUtils.copyFile(uri, file);
        }
        return file;
    }

    /**
     * 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
     */
    @SuppressLint("NewApi")
    public static String getPath(Context context, Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } catch (Exception ignore) {
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isUriContent(String uri) {
        return uri != null && (uri.startsWith("/content:/") || uri.startsWith("content:/"));
    }
}
