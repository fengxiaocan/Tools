package com.app.tool;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

/**
 * Created by sam on 2017/4/17.
 * 有关UI的工具类，如获取资源(颜色，字符串，drawable等)，
 * 屏幕宽高，dp与px转换
 */

class ResourceUtils extends Util {

    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 得到dimen值
     * resId = R.dimen.px1500
     *
     * @param resId
     * @return
     */
    public static float getDimen(int resId) {
        return getResources().getDimension(resId);
    }

    public static DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    public static int getDimenInt(int resId) {
        return (int) getResources().getDimension(resId);
    }

    public static int getColor(int resId) {
        return getResources().getColor(resId);
    }

    public static ColorStateList getColorStateList(int resId) {
        try {
            return getResources().getColorStateList(resId);
        } catch (Exception e) {
            return ColorStateList.valueOf(getColor(resId));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getColor(int resId, Resources.Theme theme) {
        return getResources().getColor(resId, theme);
    }


    /**
     * 获取Drawable
     *
     * @param resTd Drawable资源id
     * @return Drawable
     */
    public static Drawable getDrawable(int resTd) {
        return getResources().getDrawable(resTd);
    }

    /**
     * 获取字符串
     *
     * @param resId 字符串资源id
     * @return 字符串
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取字符串数组
     *
     * @param resId 数组资源id
     * @return 字符串数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }


    /**
     * 得到dimen值
     * resId = R.dimen.px1500
     *
     * @param resId
     * @return
     */
    public static float getDimen(Context context, int resId) {
        return context.getResources().getDimension(resId);
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        return context.getResources().getDisplayMetrics();
    }

    public static int getDimenInt(Context context, int resId) {
        return (int) context.getResources().getDimension(resId);
    }

    public static int getColor(Context context, int resId) {
        return context.getResources().getColor(resId);
    }

    public static ColorStateList getColorStateList(Context context, int resId) {
        try {
            return context.getResources().getColorStateList(resId);
        } catch (Exception e) {
            return ColorStateList.valueOf(getColor(context, resId));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getColor(Context context, int resId, Resources.Theme theme) {
        return context.getResources().getColor(resId, theme);
    }


    /**
     * 获取Drawable
     *
     * @param resTd Drawable资源id
     * @return Drawable
     */
    public static Drawable getDrawable(Context context, int resTd) {
        return context.getResources().getDrawable(resTd);
    }

    /**
     * 获取字符串
     *
     * @param resId 字符串资源id
     * @return 字符串
     */
    public static String getString(Context context, int resId) {
        return context.getResources().getString(resId);
    }

    /**
     * 获取字符串数组
     *
     * @param resId 数组资源id
     * @return 字符串数组
     */
    public static String[] getStringArray(Context context, int resId) {
        return context.getResources().getStringArray(resId);
    }

    public static int getResourcesId(AttributeSet set, String attributeName, int defResId) {
        if (set != null) {
            int count = set.getAttributeCount();
            for (int i = 0; i < count; i++) {
                String name = set.getAttributeName(i);
                if (StringUtils.equals(name, attributeName)) {
                    String attributeValue = set.getAttributeValue(i);
                    if (attributeValue.startsWith("@")) {
                        int resId = Integer.parseInt(attributeValue.substring(1));
                        if (resId != 0) {
                            return resId;
                        }
                        return defResId;
                    } else {
                        return defResId;
                    }
                }
            }
        }
        return defResId;
    }

    public static int getResourcesId(AttributeSet set, String attributeName) {
        return getResourcesId(set, attributeName, 0);
    }

    public static int[] getResourcesId(AttributeSet set, String[] attributeNames) {
        int[] resIds = new int[attributeNames.length];
        if (set != null) {
            int count = set.getAttributeCount();
            for (int i = 0; i < count; i++) {
                String name = set.getAttributeName(i);
                for (int y = 0; y < attributeNames.length; y++) {
                    if (StringUtils.equals(name, attributeNames[y])) {
                        String attributeValue = set.getAttributeValue(i);
                        if (attributeValue.startsWith("@")) {
                            resIds[y] = Integer.parseInt(attributeValue.substring(1));
                        }
                    }
                }
            }
        }
        return resIds;
    }
}
