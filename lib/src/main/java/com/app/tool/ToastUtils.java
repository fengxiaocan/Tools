package com.app.tool;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.StringRes;

import java.lang.ref.SoftReference;


import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

/**
 * <pre>
 *
 *
 *     time  : 2016/09/29
 *     desc  : 吐司相关工具类
 * </pre>
 */
class ToastUtils extends Util {
    private static SoftReference<Toast> sToast;
    private static boolean canShow = true;
    private static boolean autoCancel = false;
    private static boolean autoCreateNew = false;
    private static int gravity = 0;
    private static int xOffset = 0;
    private static int yOffset = SizeUtils.dp2px(60);


    /**
     * 设置吐司位置
     *
     * @param gravity 位置
     * @param xOffset x偏移
     * @param yOffset y偏移
     */
    public static void setGravity(int gravity, int xOffset, int yOffset) {
        ToastUtils.gravity = gravity;
        ToastUtils.xOffset = xOffset;
        ToastUtils.yOffset = yOffset;
    }

    public static void setCanShow(boolean canShow) {
        ToastUtils.canShow = canShow;
    }

    public static void setAutoCancel(boolean autoCancel) {
        ToastUtils.autoCancel = autoCancel;
    }

    public static void setAutoCreateNew(boolean autoCreateNew) {
        ToastUtils.autoCreateNew = autoCreateNew;
    }

    public static boolean isRunOnMain() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private static void show(final Context context, final CharSequence sequence, final int duration) {
        if (isRunOnMain()) {
            Toast.makeText(context, sequence, duration).show();
        } else {
            HandlerUtils.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(context, sequence, duration).show();
                }
            });
        }
    }

    public static void show(final Context context, final CharSequence sequence) {
        show(context, sequence, LENGTH_SHORT);
    }

    public static void show(final Context context, final String format, final Object... args) {
        show(context, String.format(format, args), LENGTH_SHORT);
    }

    public static void showLong(final Context context, final CharSequence sequence) {
        show(context, sequence, LENGTH_LONG);
    }

    public static void showLong(final Context context, final String format, final Object... args) {
        show(context, String.format(format, args), LENGTH_LONG);
    }

    public static void toast(final CharSequence sequence) {
        show(getContext(), sequence, LENGTH_SHORT);
    }

    public static void toast(final String format, final Object... args) {
        show(getContext(), String.format(format, args), LENGTH_SHORT);
    }

    public static void toastLong(final CharSequence sequence) {
        show(getContext(), sequence, LENGTH_LONG);
    }

    public static void toastLong(final String format, final Object... args) {
        show(getContext(), String.format(format, args), LENGTH_LONG);
    }

    /**
     * 显示短时吐司
     *
     * @param text 文本
     */
    public static void show(CharSequence text) {
        show(text, LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     */
    public static void show(@StringRes int resId) {
        show(resId, LENGTH_SHORT);
    }

    /**
     * 显示短时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void show(@StringRes int resId, Object... args) {
        show(resId, LENGTH_SHORT, args);
    }

    /**
     * 显示短时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void show(String format, Object... args) {
        show(format, LENGTH_SHORT, args);
    }

    /**
     * 显示长时吐司
     *
     * @param text 文本
     */
    public static void showLong(CharSequence text) {
        show(text, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     */
    public static void showLong(@StringRes int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时吐司
     *
     * @param resId 资源Id
     * @param args  参数
     */
    public static void showLong(@StringRes int resId, Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示长时吐司
     *
     * @param format 格式
     * @param args   参数
     */
    public static void showLong(String format, Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     */
    private static void show(@StringRes int resId, int duration) {
        show(ResourceUtils.getResources().getText(resId).toString(), duration);
    }

    /**
     * 显示吐司
     *
     * @param resId    资源Id
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(@StringRes int resId, int duration, Object... args) {
        show(String.format(ResourceUtils.getResources().getString(resId), args), duration);
    }

    /**
     * 显示吐司
     *
     * @param format   格式
     * @param duration 显示时长
     * @param args     参数
     */
    private static void show(String format, int duration, Object... args) {
        if (TextUtils.isEmpty(format)) {
            return;
        }
        show(String.format(format, args), duration);
    }

    /**
     * 显示吐司
     *
     * @param text     文本
     * @param duration 显示时长
     */
    private static void show(final CharSequence text, final int duration) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        if (!canShow) {
            return;
        }

        if (isRunOnMain()) {
            postToast(text, duration);
        } else {
            HandlerUtils.post(new Runnable() {
                @Override
                public void run() {
                    postToast(text, duration);
                }
            });
        }
    }

    private static void postToast(CharSequence text, int duration) {
        cancel();
        if (autoCreateNew || sToast == null || sToast.get() == null) {
            Toast toast = Toast.makeText(getContext(), text, duration);
            sToast = new SoftReference<>(toast);
        }
        Toast toast = sToast.get();
        if (gravity > 0) {
            toast.setGravity(gravity, xOffset, yOffset);
        }
        toast.setText(text);
        toast.show();
    }


    /**
     * 取消吐司显示
     */
    public static void cancel() {
        if (autoCancel && sToast != null && sToast.get() != null) {
            sToast.get().cancel();
        }
    }
}