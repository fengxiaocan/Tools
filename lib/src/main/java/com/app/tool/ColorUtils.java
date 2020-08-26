package com.app.tool;


import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;

class ColorUtils {
    /**
     * 给颜色设置Alpha
     *
     * @param color
     * @param alpha
     * @return
     */
    public static int setColorAlpha(int color, @IntRange(from = 0,
            to = 255) int alpha) {
        return color & 0xFFFFFF | (alpha << 24);
    }

    /**
     * 给颜色设置red 色值
     *
     * @param color
     * @param red
     * @return
     */
    public static int setColorRed(int color, @IntRange(from = 0,
            to = 255) int red) {
        return color & 0xFF00FFFF | (red << 16);
    }

    /**
     * 给颜色设置green 色值
     *
     * @param color
     * @param green
     * @return
     */
    public static int setColorGreen(int color, @IntRange(from = 0,
            to = 255) int green) {
        return color & 0xFFFF00FF | (green << 8);
    }

    /**
     * 给颜色设置blue 色值
     *
     * @param color
     * @param blue
     * @return
     */
    public static int setColorBlue(int color, @IntRange(from = 0,
            to = 255) int blue) {
        return color & 0xFFFFFF00 | blue;
    }

    /**
     * 给颜色设置Alpha
     *
     * @param color
     * @param alpha
     * @return
     */
    public static int setColorAlpha(int color, @FloatRange(from = 0.0f,
            to = 1.0f) float alpha) {
        return color & 0xFFFFFF | ((int) (alpha * 255.0f + 0.5f) << 24);
    }

    /**
     * 给颜色设置red 色值
     *
     * @param color
     * @param red
     * @return
     */
    public static int setColorRed(int color, @FloatRange(from = 0.0f,
            to = 1.0f) float red) {
        return color & 0xFF00FFFF | ((int) (red * 255.0f + 0.5f) << 16);
    }

    /**
     * 给颜色设置green 色值
     *
     * @param color
     * @param green
     * @return
     */
    public static int setColorGreen(int color, @FloatRange(from = 0.0f,
            to = 1.0f) float green) {
        return color & 0xFFFF00FF | ((int) (green * 255.0f + 0.5f) << 8);
    }

    /**
     * 给颜色设置blue 色值
     *
     * @param color
     * @param blue
     * @return
     */
    public static int setColorBlue(int color, @FloatRange(from = 0.0f,
            to = 1.0f) float blue) {
        return color & 0xFFFFFF00 | (int) (blue * 255.0f + 0.5f);
    }
}
