package com.app.tool;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * The type Image type.
 */
class ImageType extends Util {

    /**
     * 获取文件的mimeType
     *
     * @param fileOrUri the file or uri
     * @return mime type
     */
    public static String getMimeType(String fileOrUri) {
        String mimeType = readType(fileOrUri);
        if (mimeType != null) {
            return String.format("image/%s", mimeType);
        } else {
            return "file/*";
        }
    }

    /**
     * Gets image type.
     *
     * @param fileOrUri the file or uri
     * @return the image type
     */
    public static String getImageType(String fileOrUri) {
        String type = readType(fileOrUri);
        if (type == null) {
            return "file";
        } else {
            return type;
        }
    }

    /**
     * Rename image type file.
     * 更改图片的文件类型
     *
     * @param file the file
     * @return the file
     */
    public static File renameImageType(File file) {
        String imageType = getImageType(file.getAbsolutePath());
        if ("jpeg".equals(imageType)) {
            imageType = "jpg";
        }
        return FileUtils.renameFileType(file, imageType);
    }

    /**
     * Is image boolean.
     *
     * @param fileOrUri the file or uri
     * @return the boolean
     */
    public static boolean isImage(String fileOrUri) {
        if (TextUtils.isEmpty(fileOrUri)) {
            return false;
        } else {
            if (fileOrUri.startsWith("http")) {
                String aCase = fileOrUri.toUpperCase();
                return aCase.contains(".GIF") || aCase.contains(".PNG") || aCase.contains(".JPEG") || aCase.contains(
                        ".JPG") || aCase.contains(".BMP") || aCase.contains(".WEBP") || aCase.contains(".ICO");
            } else {
                String mimeType = readType(fileOrUri);
                return mimeType != null;
            }
        }
    }


    /**
     * Is gif boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean isGif(File file) {
        return "gif".equals(readType(file));
    }

    /**
     * Is gif boolean.
     *
     * @param filename the filename
     * @return the boolean
     */
    public static boolean isGif(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        } else {
            if (filename.startsWith("http")) {
                return filename.toUpperCase().contains(".GIF");
            } else {
                return isGif(new File(filename));
            }
        }
    }

    /**
     * Is png boolean.
     *
     * @param filename the filename
     * @return the boolean
     */
    public static boolean isPng(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        } else {
            if (filename.startsWith("http")) {
                return filename.toUpperCase().contains(".PNG");
            } else {
                return isPng(new File(filename));
            }
        }
    }

    /**
     * Is png boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean isPng(File file) {
        return "png".equals(readType(file));
    }

    /**
     * Is webp boolean.
     *
     * @param filename the filename
     * @return the boolean
     */
    public static boolean isWebp(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        } else {
            if (filename.startsWith("http")) {
                return filename.toUpperCase().contains(".WEBP");
            } else {
                return isWebp(new File(filename));
            }
        }
    }

    /**
     * Is webp boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean isWebp(File file) {
        return "webp".equals(readType(file));
    }

    /**
     * Is bmp boolean.
     *
     * @param filename the filename
     * @return the boolean
     */
    public static boolean isBmp(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        } else {
            if (filename.startsWith("http")) {
                return filename.toUpperCase().contains(".BMP");
            } else {
                return isBmp(new File(filename));
            }
        }
    }

    /**
     * Is bmp boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean isBmp(File file) {
        return "bmp".equals(readType(file));
    }

    /**
     * Is ico boolean.
     *
     * @param filename the filename
     * @return the boolean
     */
    public static boolean isIco(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        } else {
            if (filename.startsWith("http")) {
                return filename.toUpperCase().contains(".ICO");
            } else {
                return isIco(new File(filename));
            }
        }
    }

    /**
     * Is ico boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean isIco(File file) {
        return "ico".equals(readType(file));
    }

    /**
     * Is jpeg boolean.
     *
     * @param filename the filename
     * @return the boolean
     */
    public static boolean isJpeg(String filename) {
        if (TextUtils.isEmpty(filename)) {
            return false;
        } else {
            if (filename.startsWith("http")) {
                String aCase = filename.toUpperCase();
                return aCase.contains(".JPEG") || aCase.contains(".JPG");
            } else {
                return isJpeg(new File(filename));
            }
        }
    }

    /**
     * Is jpeg boolean.
     *
     * @param file the file
     * @return the boolean
     */
    public static boolean isJpeg(File file) {
        return "jpeg".equals(readType(file));
    }


    /**
     * Read type string.
     *
     * @param fileOrUri the file or uri
     * @return string
     * @throws IOException
     */
    public static String readType(String fileOrUri) {
        InputStream fis = null;
        try {
            if (TextUtils.isEmpty(fileOrUri)) {
                return null;
            }
            long lenght;
            if (UriUtils.isUriContent(fileOrUri)) {
                //转化成Uri类型
                Uri uri = Uri.parse(fileOrUri);
                ContentResolver contentResolver = getContext().getContentResolver();
                ParcelFileDescriptor descriptor = contentResolver.openFileDescriptor(uri, "r");
                //获取Uri文件的长度
                lenght = descriptor.getStatSize();
                if (lenght <= 0) {
                    //获取不到的时候查询数据库
                    UriUtils.queryUriColumnV1(uri, MediaStore.Images.ImageColumns.SIZE);
                }
                fis = new FileInputStream(descriptor.getFileDescriptor());
            } else {
                File file = new File(fileOrUri);
                fis = new FileInputStream(file);
                lenght = file.length();
            }
            return getImageType(fis, lenght);
        } catch (Exception e) {
            return null;
        } finally {
            closeIo(fis);
        }
    }

    /**
     * 读取文件类型
     *
     * @param uri the uri
     * @return string
     */
    public static String readType(Uri uri) {
        InputStream fis = null;
        try {
            if (uri == null) {
                return null;
            }
            long lenght;
            ContentResolver contentResolver = getContext().getContentResolver();
            ParcelFileDescriptor descriptor = contentResolver.openFileDescriptor(uri, "r");
            //获取Uri文件的长度
            lenght = descriptor.getStatSize();
            if (lenght <= 0) {
                //获取不到的时候查询数据库
                UriUtils.queryUriColumnV1(uri, MediaStore.Images.ImageColumns.SIZE);
            }
            fis = new FileInputStream(descriptor.getFileDescriptor());
            return getImageType(fis, lenght);
        } catch (Exception e) {
            return null;
        } finally {
            closeIo(fis);
        }
    }

    /**
     * Read type string.
     *
     * @param file the file
     * @return string
     */
    public static String readType(File file) {
        InputStream fis = null;
        try {
            if (file == null) {
                return null;
            }
            long lenght = file.length();
            fis = new FileInputStream(file);
            return getImageType(fis, lenght);
        } catch (Exception e) {
            return null;
        } finally {
            closeIo(fis);
        }
    }

    /**
     * 获取文件类型
     *
     * @param fis
     * @param fileLenght
     * @return
     * @throws IOException
     */
    private static String getImageType(InputStream fis, long fileLenght) throws IOException {
        byte[] bufHeaders = readInputStreamAt(fis, 0, 8);
        if (isJPEGHeader(bufHeaders)) {
            long skiplength = fileLenght - 2 - 8; //第一次读取时已经读了8个byte,因此需要减掉
            byte[] bufFooters = readInputStreamAt(fis, skiplength, 2);
            if (isJPEGFooter(bufFooters)) {
                return "jpeg";
            }
        }
        if (isPNG(bufHeaders)) {
            return "png";
        }
        if (isGIF(bufHeaders)) {
            return "gif";
        }
        if (isWEBP(bufHeaders)) {
            return "webp";
        }
        if (isBMP(bufHeaders)) {
            return "bmp";
        }
        if (isICON(bufHeaders)) {
            return "ico";
        }
        return null;
    }

    private static void closeIo(Closeable c) {
        if (c != null) {
            try {
                c.close();
            } catch (IOException e) {
            }
        }
    }

    private static boolean isBMP(byte[] buf) {
        byte[] markBuf = "BM".getBytes();  //BMP图片文件的前两个字节
        return compare(buf, markBuf);
    }

    private static boolean isICON(byte[] buf) {
        byte[] markBuf = {0, 0, 1, 0, 1, 0, 32, 32};
        return compare(buf, markBuf);
    }

    private static boolean isWEBP(byte[] buf) {
        byte[] markBuf = "RIFF".getBytes(); //WebP图片识别符
        return compare(buf, markBuf);
    }

    private static boolean isGIF(byte[] buf) {
        byte[] markBuf = "GIF89a".getBytes(); //GIF识别符
        if (compare(buf, markBuf)) {
            return true;
        }
        markBuf = "GIF87a".getBytes(); //GIF识别符
        return compare(buf, markBuf);
    }

    private static boolean isPNG(byte[] buf) {
        byte[] markBuf = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A}; //PNG识别符
        // new String(buf).indexOf("PNG")>0 //也可以使用这种方式
        return compare(buf, markBuf);
    }

    private static boolean isJPEGHeader(byte[] buf) {
        byte[] markBuf = {(byte) 0xff, (byte) 0xd8}; //JPEG开始符
        return compare(buf, markBuf);
    }

    private static boolean isJPEGFooter(byte[] buf)//JPEG结束符
    {
        byte[] markBuf = {(byte) 0xff, (byte) 0xd9};
        return compare(buf, markBuf);
    }


    /**
     * 标示一致性比较
     *
     * @param buf     待检测标示
     * @param markBuf 标识符字节数组
     * @return 返回false标示标示不匹配
     */
    private static boolean compare(byte[] buf, byte[] markBuf) {
        for (int i = 0; i < markBuf.length; i++) {
            byte b = markBuf[i];
            byte a = buf[i];

            if (a != b) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param fis        输入流对象
     * @param skiplength 跳过位置长度
     * @param length     要读取的长度
     * @return 字节数组
     * @throws IOException
     */
    private static byte[] readInputStreamAt(InputStream fis, long skiplength, int length) throws IOException {
        byte[] buf = new byte[length];
        fis.skip(skiplength);
        fis.read(buf, 0, length);
        return buf;
    }
}
