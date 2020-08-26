package com.app.tool;

import android.media.ExifInterface;

import java.io.IOException;

/**
 * <pre>
 *
 *
 *     time  : 2016/08/12
 *     desc  : 图片相关工具类
 * </pre>
 */
 class ImageUtils{

    /**
     * 获取图片旋转角度
     *
     * @param filePath 文件路径
     * @return 旋转角度
     */
    public static int getRotateDegree(String filePath){
        int degree = 0;
        try{
            ExifInterface exifInterface = new ExifInterface(filePath);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch(orientation){
                default:
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        return degree;
    }

}