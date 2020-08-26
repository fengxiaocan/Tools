package com.app.tool;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import java.io.File;

/**
 * 后台截屏工具类
 */
class ShotUtil {
    /**
     * 截屏
     *
     * @param activity
     * @param filePath
     * @return
     */
    public static void screenshot(Activity activity, File filePath) {
        if (FileUtils.isFileExists(filePath)) {
            Bitmap bitmap = shot(activity, true);
            BitmapUtils.saveBitmapToFile(filePath, bitmap, Bitmap.CompressFormat.JPEG, 100);
        }
    }

    public static void screenshot(Activity activity, String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return;
        }
        screenshot(activity, new File(filePath));
    }


    /**
     * 截取activity的界面
     *
     * @param activity  the activity
     * @param isWithBar the is with bar 是否包含状态栏
     * @return bitmap
     */
    public static Bitmap shot(Activity activity, boolean isWithBar) {
        //View是你需要截图的View
        View view = activity.getWindow().getDecorView();
        Bitmap b1 = BitmapUtils.getViewBitmap(view);
        Bitmap b;
        // 获取屏幕长和高
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();
        if (isWithBar) {
            b = Bitmap.createBitmap(b1, 0, 0, width, height);
        } else {
            // 获取状态栏高度 /
            Rect frame = new Rect();
            activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;
            // 去掉标题栏
            b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        }
        view.destroyDrawingCache();
        return b;
    }

}
