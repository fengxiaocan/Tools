package com.app.tool;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.graphics.Paint;
import android.location.LocationListener;

import androidx.annotation.RequiresPermission;

import com.app.encrypt.EncodeUtils;

import java.lang.reflect.Method;

public class Tools {

    private static Application application;

    /**
     * 初始化application
     */
    public static void initApplication(Application baseApp) {
        application = baseApp;
    }

    /**
     * 获取ApplicationContext
     */
    public static Context getContext() {
        return getApplication();
    }

    /**
     * Get application application.
     */
    public static synchronized Application getApplication() {
        if (application != null)
            return application;
        return application = loaderApplication();
    }

    private static Application loaderApplication() {
        Method method;
        try {
            method = java.lang.Class.forName("android.app.AppGlobals").getDeclaredMethod("getInitialApplication");
            method.setAccessible(true);
            return (Application) method.invoke(null);
        } catch (Exception e) {
            try {
                method = java.lang.Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication");
                method.setAccessible(true);
                return (Application) method.invoke(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static class Activitys extends com.app.tool.ActivityUtils {
    }

    public static class System extends com.app.tool.SystemUtils {
    }

    public static class Animations extends com.app.tool.AnimationUtils {
    }

    public static class Apk extends com.app.tool.ApkUtils {
    }

    public static class App extends com.app.tool.AppUtils {
    }

    public static class Assets extends com.app.tool.AssetsUtils {
    }

    public static class Bar extends com.app.tool.BarUtils {
    }

    public static class Bitmap extends com.app.tool.BitmapUtils {
    }

    public static class Bit extends com.app.tool.BitUtils {
    }

    public static class Brightness extends com.app.tool.BrightnessUtils {
    }

    public static class Camera extends com.app.tool.CameraUtils {
    }

    public static class CharSequence extends com.app.tool.CharSequenceUtils {
    }

    public static class Charsets extends com.app.tool.Charsets {
    }

    public static class Clazz extends com.app.tool.ClassUtils {
    }

    public static class Clean extends com.app.tool.CleanUtils {
    }

    public static class Clipboard extends com.app.tool.ClipboardUtils {
    }

    public static class Clone extends com.app.tool.CloneUtils {
    }

    public static class Close extends com.app.tool.CloseUtils {
    }

    public static class Color extends com.app.tool.ColorUtils {
    }

    public static class Compare extends com.app.tool.CompareUtils {
    }

    public static class Hex extends HexUtils {
    }

    public static class Coordinate extends com.app.tool.CoordinateUtils {
    }

    public static class Data extends com.app.tool.DataUtils {
    }

    public static class Device extends com.app.tool.DeviceUtils {
    }

    public static class Empty extends com.app.tool.EmptyUtils {
    }

    public static class Encode extends EncodeUtils {
    }

    public static class FileType extends com.app.tool.FileType {
    }

    public static class Files extends com.app.tool.FileUtils {
    }

    public static class Flashlight extends com.app.tool.FlashlightUtils {
    }

    public static class Fragments extends com.app.tool.FragmentUtils {
    }

    public static class Handlers extends com.app.tool.HandlerUtils {
    }

    public static class Https extends com.app.tool.HttpsUtils {
    }

    public static class Html extends com.app.tool.HtmlUtils {
    }

    public static class IDCard extends com.app.tool.IDCardUtil {
    }

    public static class ImageType extends com.app.tool.ImageType {
    }

    public static class Image extends com.app.tool.ImageUtils {
    }

    public static class Intents extends com.app.tool.IntentUtils {
    }

    public static class Json extends com.app.tool.JsonUtils {
    }

    public static class Keyboard extends com.app.tool.KeyboardUtils {
    }

    public static class Lists extends com.app.tool.ListUtils {
    }

    public static class Location extends com.app.tool.LocationUtils {
        @RequiresPermission(allOf = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
        public boolean register(String tag, long minTime, long minDistance, LocationListener listener) {
            LifeHelper helper = LifeHelper.with(tag);
            Builder builder = helper.get(Builder.class);
            if (builder == null) {
                builder = new Builder();
                helper.set(Builder.class, builder);
            }
            return builder.register(minTime, minDistance, listener);
        }

        @RequiresPermission(allOf = {Manifest.permission.INTERNET, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION})
        public void unregister(String tag) {
            LifeHelper helper = LifeHelper.with(tag);
            Builder builder = helper.get(Builder.class);
            if (builder != null) {
                builder.unregister();
            }
        }
    }

    public static class Lunar extends com.app.tool.LunarUtils {
    }

    public static class Maps extends com.app.tool.MapUtils {
    }

    public static class Math extends com.app.tool.MathUtil {
    }

    public static class Net extends com.app.tool.NetworkUtils {
    }

    public static class Objects extends com.app.tool.ObjectUtils {
    }

    public static class Open extends com.app.tool.OpenUtils {
    }

    public static class Paints extends com.app.tool.PaintUtils {
        public Paints() {
        }

        public Paints(android.graphics.Paint paint) {
            super(paint);
        }

        public static Paint create() {
            return new Paint();
        }

        public static Paint create(android.graphics.Paint paint) {
            return new Paint(paint);
        }
    }

    public static class Path extends PathUtils {
    }

    public static class Phone extends PhoneUtils {
    }

    public static class Pinyin extends PinyinUtils {
    }

    public static class Regex extends RegexUtils {
    }

    public static class Resource extends ResourceUtils {
    }

    public static class Shot extends ShotUtil {
    }

    public static class Screen extends ScreenUtils {
    }

    public static class SDCard extends SDCardUtils {
    }

    public static class Services extends ServiceUtils {
    }

    public static class Shell extends ShellUtils {
    }

    public static class Strings extends com.app.tool.StringUtils {
    }

    public static class Size extends com.app.tool.SizeUtils {
    }

    public static class Sp extends com.app.tool.SpUtils {
    }

    public static class StackTrace extends com.app.tool.StackTraceUtils {
    }

    public static class Toasts extends com.app.tool.ToastUtils {
    }

    public static class Time extends com.app.tool.TimeUtils {
    }

    public static class Tint extends com.app.tool.TintUtils {
    }

    public static class Vibration extends com.app.tool.VibrationUtils {
    }

    public static class Video extends com.app.tool.VideoUtils {
    }

    public static class Views extends com.app.tool.ViewUtils {
    }

    public static class Wifi extends com.app.tool.WifiConnect {
    }

    public static class Zip extends com.app.tool.ZipUtils {
    }

    public static class Encrypt {
        public static class Caesar extends com.app.tool.CaesarUtils {
        }

        public static class CRC16 extends com.app.tool.CRC16Check {
        }
    }
}
