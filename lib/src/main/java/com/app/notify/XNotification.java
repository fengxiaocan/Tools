package com.app.notify;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import androidx.core.content.LocusIdCompat;
import androidx.core.content.pm.ShortcutInfoCompat;
import androidx.core.graphics.drawable.IconCompat;

public class XNotification extends NotificationCompat.Builder {
    public static final String CHANNEL_ID = "DEFAULT";

    private Context context;

    public XNotification(@NonNull Context context) {
        super(context, CHANNEL_ID);
        this.context = context;
    }

    public XNotification(@NonNull Context context, @NonNull String channelId) {
        super(context, channelId);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public XNotification(@NonNull Context context, @NonNull Notification notification) {
        super(context, notification);
        this.context = context;
    }

    public static Channel from(String channelId) {
        return new Channel(channelId);
    }

    public static XNotification from(Context context) {
        return new XNotification(context);
    }

    public static long[] vibrationPattern() {
        return new long[]{300, 300, 300, 300};
    }

    /**
     * 获取创建一个NotificationManager的对象
     * <p>
     * NotificationManager对象
     */
    public NotificationManager getManager() {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    /**
     * 清空所有的通知
     */
    public XNotification clearNotification() {
        getManager().cancelAll();
        return this;
    }

    /**
     * 取消该标志的通知
     *
     * @param tag
     * @param id
     */
    public XNotification cancelNotification(String tag, int id) {
        getManager().cancel(tag, id);
        return this;
    }

    /**
     * 取消该标志的通知
     *
     * @param id
     */
    public XNotification cancelNotification(int id) {
        getManager().cancel(id);
        return this;
    }

    public void sendNotification(int id) {
        getManager().notify(id, build());
    }

    public void sendNotification(String tag, int id) {
        getManager().notify(tag, id, build());
    }

    @NonNull
    @Override
    public Notification build() {
        return super.build();
    }

    public XNotification reset() {
        return setUsesChronometer(false)//设置是否显示时间计时，电话通知就会使用到
                .setWhen(0)//通知产生的时间，会在通知栏信息里显示，一般是系统获取到的时间
                .setSilent(false)//如果为true ，则不管通知或通知通道上设置的声音或振动如何，都将使该通知实例静音
                .setShowWhen(true)//设置是否显示当前时间
                .setContentTitle(null)//设置通知标题
                .setContentText(null)//设置通知内容
                .setSubText(null)//在通知上新增一行文本
                .setSettingsText(null)//提供文本，这些文本将显示为您的应用程序设置的链接。
                .setRemoteInputHistory(null)//设置远程输入历史。
                .setNumber(0)//显示在右边的数字
                .setContentInfo(null)
                .setProgress(0, 0, false)//设置进度
                .setContent(null)
                .setContentIntent(null)// 设置pendingIntent,点击通知时就会用到
                .setDeleteIntent(null)//设置pendingIntent,左滑右滑通知时就会用到
                .setFullScreenIntent(null, false)
                .setTicker(null, null)//状态栏的通知提示预览文本
                .setSound(null)//设置铃声
                .setVibrate(null)//设置震动
                .setOngoing(false)//设置是否是正在进行中的通知，默认是false
                .setColorized(false)//设置此通知是否应为彩色。
                .setOnlyAlertOnce(false)//设置是否只通知一次
                .setAutoCancel(false)//是否点击后自动取消
                .setLocalOnly(false)//设置此通知是否仅与当前设备相关
                .setDefaults(0)//向通知添加默认的声音、闪灯和震动效果
                .setCategory(null)//设置通知类别
                .setPriority(0)//设置此通知的相对优先级
                .setGroup(null)//设置该通知组的密钥
                .setGroupSummary(false)//设置是否为一组通知的第一个显示
                .setSortKey(null)//设置针对一个包内的通知进行排序的键值
                .clearActions()
                .clearInvisibleActions()
                .setStyle(null)//设置样式
                .setPublicVersion(null)
                .setCustomContentView(null)
                .setCustomBigContentView(null)
                .setCustomHeadsUpContentView(null)
                .setTimeoutAfter(0)//设置超时时间，超时之后自动取消。
                .setBubbleMetadata(null)
                .clearPeople()
                .setBadgeIconType(NotificationCompat.BADGE_ICON_NONE)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
                .setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL);
    }

    public PendingIntent getServicePending(Class<? extends android.app.Service> clazz, int pendingIntentFlags) {
        Intent intent = new Intent(context, clazz);
        return PendingIntent.getService(context, 0, intent, pendingIntentFlags);
    }

    public PendingIntent getBroadcastPending(Class<? extends BroadcastReceiver> clazz, String action,
                                             int pendingIntentFlags) {
        Intent intent = new Intent(context, clazz);
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, pendingIntentFlags);
    }

    public PendingIntent getActivityPending(Class<? extends Activity> clazz, int pendingIntentFlags) {
        Intent intent = new Intent(context, clazz);
        return PendingIntent.getActivity(context, 0, intent, pendingIntentFlags);
    }

    /**
     * 通知产生的时间，会在通知栏信息里显示，一般是系统获取到的时间
     *
     * @param when
     */
    @NonNull
    @Override
    public XNotification setWhen(long when) {
        return (XNotification) super.setWhen(when);
    }

    /**
     * 设置是否显示当前时间
     *
     * @param show
     */
    @NonNull
    @Override
    public XNotification setShowWhen(boolean show) {
        return (XNotification) super.setShowWhen(show);
    }

    /**
     * 设置小图标
     *
     * @param icon
     */
    @NonNull
    @Override
    public XNotification setSmallIcon(@NonNull IconCompat icon) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            super.setSmallIcon(icon);
        }
        return this;
    }

    /**
     * 设置是否显示时间计时，电话通知就会使用到。
     *
     * @param b
     */
    @NonNull
    @Override
    public XNotification setUsesChronometer(boolean b) {
        return (XNotification) super.setUsesChronometer(b);
    }

    /**
     * 将计时器设定为倒数而不是递增。 仅当setUsesChronometer（boolean）设置为true时才有意义。 如果未设置，则计时器将计数。
     *
     * @param countsDown
     */
    @NonNull
    @Override
    public XNotification setChronometerCountDown(boolean countsDown) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.setChronometerCountDown(countsDown);
        }
        return this;
    }

    @NonNull
    @Override
    public XNotification setSmallIcon(int icon) {
        return (XNotification) super.setSmallIcon(icon);
    }

    @NonNull
    @Override
    public XNotification setSmallIcon(int icon, int level) {
        return (XNotification) super.setSmallIcon(icon, level);
    }

    @NonNull
    @Override
    public XNotification setNotificationSilent() {
        return (XNotification) super.setNotificationSilent();
    }

    /**
     * 如果为true ，则不管通知或通知通道上设置的声音或振动如何，都将使该通知实例静音。 如果为false ，则应用正常的声音和振动逻辑。 默认为false 。
     *
     * @param silent
     */
    @NonNull
    @Override
    public XNotification setSilent(boolean silent) {
        return (XNotification) super.setSilent(silent);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    @NonNull
    @Override
    public XNotification setContentTitle(@Nullable CharSequence title) {
        return (XNotification) super.setContentTitle(title);
    }

    /**
     * 设置内容
     *
     * @param text
     */
    @NonNull
    @Override
    public XNotification setContentText(@Nullable CharSequence text) {
        return (XNotification) super.setContentText(text);
    }

    /**
     * 在通知上新增一行文本，使原本两行文本变成三行文本,显示在内容的文本之下
     *
     * @param text
     */
    @NonNull
    @Override
    public XNotification setSubText(@Nullable CharSequence text) {
        return (XNotification) super.setSubText(text);
    }

    /**
     * 提供文本，这些文本将显示为您的应用程序设置的链接。
     *
     * @param text
     */
    @NonNull
    @Override
    public XNotification setSettingsText(@Nullable CharSequence text) {
        return (XNotification) super.setSettingsText(text);
    }

    @NonNull
    @Override
    public XNotification setRemoteInputHistory(@Nullable CharSequence[] text) {
        return (XNotification) super.setRemoteInputHistory(text);
    }

    /**
     * 显示在通知栏右边的数字
     *
     * @param number
     */
    @NonNull
    @Override
    public XNotification setNumber(int number) {
        return (XNotification) super.setNumber(number);
    }

    @NonNull
    @Override
    public XNotification setContentInfo(@Nullable CharSequence info) {
        return (XNotification) super.setContentInfo(info);
    }

    /**
     * 为通知设置设置一个进度条
     *
     * @param max：最大值
     * @param progress：当前进度
     * @param indeterminate:进度是否确定;当进度不确定的情况下,进度条会一直显示循环进度
     */
    @NonNull
    @Override
    public XNotification setProgress(int max, int progress, boolean indeterminate) {
        return (XNotification) super.setProgress(max, progress, indeterminate);
    }

    /**
     * 重置进度
     */
    public XNotification resetProgress() {
        return setProgress(0, 0, false);
    }

    @NonNull
    @Override
    public XNotification setContent(@Nullable RemoteViews views) {
        return (XNotification) super.setContent(views);
    }

    /**
     * 点击通知事件
     *
     * @param intent
     */
    @NonNull
    @Override
    public XNotification setContentIntent(@Nullable PendingIntent intent) {
        return (XNotification) super.setContentIntent(intent);
    }

    /**
     * 设置内容点击事件,发送广播
     */
    public XNotification setContentBroadcast(Class<? extends BroadcastReceiver> clazz, String action, int flags) {
        PendingIntent intent = getBroadcastPending(clazz, action, flags);
        setContentIntent(intent);
        return this;
    }

    /**
     * 设置内容点击事件,发送广播
     */
    public XNotification setContentBroadcast(Class<? extends BroadcastReceiver> clazz, String action) {
        return setContentBroadcast(clazz, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 设置内容点击事件,跳转到Activity
     */
    public XNotification setContentActivity(Class<? extends Activity> clazz, int flags) {
        PendingIntent intent = getActivityPending(clazz, flags);
        setContentIntent(intent);
        return this;
    }

    /**
     * 设置内容点击事件,跳转到Activity
     */
    public XNotification setContentActivity(Class<? extends Activity> clazz) {
        return setContentActivity(clazz, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    /**
     * 设置滑动消失行为的监听行为。
     * Intent click = new Intent(this, NotificationBroadcastReceiver.class);
     * click.setAction("com.xxx.xxx.click");
     * PendingIntent clickPI = PendingIntent.getBroadcast(this, 1, click, PendingIntent.FLAG_CANCEL_CURRENT);
     * <p>
     * Intent cancel = new Intent(this, NotificationBroadcastReceiver.class);
     * cancel.setAction("com.xxx.xxx.cancel");
     * PendingIntent cancelPI = PendingIntent.getBroadcast(this, 2, cancel, PendingIntent.FLAG_CANCEL_CURRENT );
     *
     * @param intent
     */
    @NonNull
    @Override
    public XNotification setDeleteIntent(@Nullable PendingIntent intent) {
        return (XNotification) super.setDeleteIntent(intent);
    }

    /**
     * 设置删除通知事件,发送广播
     */
    public XNotification setDeleteBroadcast(Class<? extends BroadcastReceiver> clazz, String action, int flags) {
        PendingIntent intent = getBroadcastPending(clazz, action,  flags);
        setDeleteIntent(intent);
        return this;
    }

    /**
     * 设置删除通知事件,发送广播
     */
    public XNotification setDeleteBroadcast(Class<? extends BroadcastReceiver> clazz, String action) {
        return setDeleteBroadcast(clazz, action,  PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * 设置删除通知事件,跳转到Activity
     */
    public XNotification setDeleteActivity(Class<? extends Activity> clazz, int flags) {
        PendingIntent intent = getActivityPending(clazz, flags);
        setDeleteIntent(intent);
        return this;
    }

    /**
     * 设置删除通知事件,跳转到Activity
     */
    public XNotification setDeleteActivity(Class<? extends Activity> clazz) {
        return setDeleteActivity(clazz, PendingIntent.FLAG_CANCEL_CURRENT);
    }


    /**
     * 响应紧急状态的全屏事件（例如来电事件），也就是说通知来的时候，
     * 跳过在通知区域点击通知这一步，直接执行fullScreenIntent代表的事件
     *
     * @param intent
     * @param highPriority
     */
    @NonNull
    @Override
    public XNotification setFullScreenIntent(@Nullable PendingIntent intent, boolean highPriority) {
        return (XNotification) super.setFullScreenIntent(intent, highPriority);
    }

    /**
     * 设置通知在第一次到达时在状态栏中显示的文本,状态栏上显示出来的简短文本消息
     *
     * @param tickerText
     */
    @NonNull
    @Override
    public XNotification setTicker(@Nullable CharSequence tickerText) {
        return (XNotification) super.setTicker(tickerText);
    }

    /**
     * 设置通知在第一次到达时在状态栏中显示的文本,状态栏上显示出来的简短文本消息
     *
     * @param tickerText
     */
    @NonNull
    @Override
    public XNotification setTicker(@Nullable CharSequence tickerText, @Nullable RemoteViews views) {
        return (XNotification) super.setTicker(tickerText, views);
    }

    /**
     * 设置大图标，如果没有设置大图标，大图标的位置的图片将会是小图标的图片。
     *
     * @param icon 该图不需要做成透明背景图
     */
    @NonNull
    @Override
    public XNotification setLargeIcon(@Nullable Bitmap icon) {
        return (XNotification) super.setLargeIcon(icon);
    }

    /**
     * 设置铃声。
     *
     * @param sound
     */
    @NonNull
    @Override
    public XNotification setSound(@Nullable Uri sound) {
        return (XNotification) super.setSound(sound);
    }

    /**
     * 设置铃声。
     *
     * @param sound
     * @param streamType 铃声类型
     */
    @NonNull
    @Override
    public XNotification setSound(@Nullable Uri sound, int streamType) {
        return (XNotification) super.setSound(sound, streamType);
    }

    /**
     * 设置使用震动模式
     * 如果setDefaults的属性设置的是Notification.DEFAULT_ALL或者Notification.DEFAULT_VISIBLE，
     * setVibrate将无效，因为Notification.DEFAULT_ALL或者Notification.DEFAULT_VISIBLE会替代setVibrate。
     *
     * @param pattern 第一位为延迟时间,第二位为震动时间,依次变换
     */
    @NonNull
    @Override
    public XNotification setVibrate(@Nullable long[] pattern) {
        return (XNotification) super.setVibrate(pattern);
    }

    /**
     * 设置呼吸灯。
     *
     * @param argb  灯光颜色
     * @param onMs  亮持续时间
     * @param offMs 暗的时间
     */
    @NonNull
    @Override
    public XNotification setLights(int argb, int onMs, int offMs) {
        return (XNotification) super.setLights(argb, onMs, offMs);
    }

    /**
     * 设置是否是正在进行中的通知，默认是false。如果设置成true，左右滑动的时候就不会被删除了。
     *
     * @param ongoing
     */
    @NonNull
    @Override
    public XNotification setOngoing(boolean ongoing) {
        return (XNotification) super.setOngoing(ongoing);
    }

    /**
     * 设置此通知是否应为彩色。 设置后，使用setColor(int)设置的颜色将用作此通知的背景色。
     *
     * @param colorize
     */
    @NonNull
    @Override
    public XNotification setColorized(boolean colorize) {
        return (XNotification) super.setColorized(colorize);
    }

    /**
     * 设置是否只通知一次，这个效果主要体现在震动、提示音、悬挂通知上。
     * 默认相同ID的Notification可以无限通知，如果有震动、闪灯、提示音和悬挂通知的话可能会不断的打扰用户。
     * setOnlyAlertOnce的默认值是false，如果设置成true，那么一旦状态栏有ID为n的通知，
     * 再次调用notificationManager.notify(n, notification)时，将不会有震动、闪灯、提示音以及悬挂通知的提醒。
     *
     * @param onlyAlertOnce
     */
    @NonNull
    @Override
    public XNotification setOnlyAlertOnce(boolean onlyAlertOnce) {
        return (XNotification) super.setOnlyAlertOnce(onlyAlertOnce);
    }

    /**
     * 点击通知自动消失,setAutoCancel需要和setContentIntent一起使用
     *
     * @param autoCancel
     */
    @NonNull
    @Override
    public XNotification setAutoCancel(boolean autoCancel) {
        return (XNotification) super.setAutoCancel(autoCancel);
    }

    /**
     * 设置此通知是否仅与当前设备相关。如果设置为true，通知就不能桥接到其他设备上进行远程显示。
     *
     * @param b
     */
    @NonNull
    @Override
    public XNotification setLocalOnly(boolean b) {
        return (XNotification) super.setLocalOnly(b);
    }

    /**
     * 设置通知类别:CATEGORY_CALL、CATEGORY_MESSAGE等
     *
     * @param category
     */
    @NonNull
    @Override
    public XNotification setCategory(@Nullable String category) {
        return (XNotification) super.setCategory(category);
    }

    /**
     * 向通知添加声音、闪灯和震动效果，最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，
     * 可以组合Notification.DEFAULT_ALL、Notification.DEFAULT_SOUND添加声音。
     *
     * @param defaults
     */
    @NonNull
    @Override
    public XNotification setDefaults(int defaults) {
        return (XNotification) super.setDefaults(defaults);
    }

    /**
     * 向通知添加默认声音、闪灯和震动效果
     */
    @NonNull
    public XNotification setDefaultAll() {
        return (XNotification) super.setDefaults(Notification.DEFAULT_ALL);
    }

    /**
     * 向通知添加默认声音效果
     */
    @NonNull
    public XNotification setDefaultSound() {
        return (XNotification) super.setDefaults(Notification.DEFAULT_SOUND);
    }

    /**
     * 添加默认震动提醒 需要VIBRATE permission
     */
    @NonNull
    public XNotification setDefaultVibrate() {
        return (XNotification) super.setDefaults(Notification.DEFAULT_VIBRATE);
    }

    /**
     * 向通知添加默认闪灯效果
     */
    @NonNull
    public XNotification setDefaultLights() {
        return (XNotification) super.setDefaults(Notification.DEFAULT_LIGHTS);
    }

    /**
     * 设置此通知的相对优先级。
     *
     * @param pri
     */
    @NonNull
    @Override
    public XNotification setPriority(int pri) {
        return (XNotification) super.setPriority(pri);
    }

    /**
     * 设置此通知的最大的优先级。
     */
    @NonNull
    public XNotification setMaxPriority() {
        return setPriority(NotificationCompat.PRIORITY_MAX);
    }

    /**
     * 设置该通知组的密钥，即确认为哪一组。主要体现在Android7.0以上的手机上的捆绑通知或者折叠通知.
     *
     * @param groupKey
     */
    @NonNull
    @Override
    public XNotification setGroup(@Nullable String groupKey) {
        return (XNotification) super.setGroup(groupKey);
    }

    /**
     * 设置是否为一组通知的第一个显示，默认是false。
     *
     * @param isGroupSummary
     */
    @NonNull
    @Override
    public XNotification setGroupSummary(boolean isGroupSummary) {
        return (XNotification) super.setGroupSummary(isGroupSummary);
    }

    /**
     * 设置针对一个包内的通知进行排序的键值，键值是一个字符串，通知会按照键值的顺序排列。
     *
     * @param sortKey
     */
    @NonNull
    @Override
    public XNotification setSortKey(@Nullable String sortKey) {
        return (XNotification) super.setSortKey(sortKey);
    }

    @NonNull
    @Override
    public XNotification addExtras(@Nullable android.os.Bundle extras) {
        return (XNotification) super.addExtras(extras);
    }

    /**
     * 为通知设置数据，在Android4.4新增了设置数据的入口。
     *
     * @param extras
     */
    @NonNull
    @Override
    public XNotification setExtras(@Nullable android.os.Bundle extras) {
        return (XNotification) super.setExtras(extras);
    }

    /**
     * 添加内联回复的通知。
     *
     * @param icon
     * @param title
     * @param intent
     */
    @NonNull
    @Override
    public XNotification addAction(int icon, @Nullable CharSequence title, @Nullable PendingIntent intent) {
        return (XNotification) super.addAction(icon, title, intent);
    }

    @NonNull
    @Override
    public XNotification addAction(@Nullable NotificationCompat.Action action) {
        return (XNotification) super.addAction(action);
    }

    @NonNull
    @Override
    public XNotification clearActions() {
        return (XNotification) super.clearActions();
    }

    @NonNull
    @Override
    public XNotification addInvisibleAction(int icon, @Nullable CharSequence title, @Nullable PendingIntent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.addInvisibleAction(icon, title, intent);
        }
        return this;
    }

    @NonNull
    @Override
    public XNotification addInvisibleAction(@Nullable NotificationCompat.Action action) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.addInvisibleAction(action);
        }
        return this;
    }

    @NonNull
    @Override
    public XNotification clearInvisibleActions() {
        return (XNotification) super.clearInvisibleActions();
    }

    @NonNull
    @Override
    public XNotification setStyle(@Nullable NotificationCompat.Style style) {
        return (XNotification) super.setStyle(style);
    }

    /**
     * 设置通知栏颜色（Android5.0新增）
     *
     * @param argb
     */
    @NonNull
    @Override
    public XNotification setColor(int argb) {
        return (XNotification) super.setColor(argb);
    }

    /**
     * 设置通知的显示等级
     * VISIBILITY_PUBLIC 任何情况都会显示通知
     * VISIBILITY_PRIVATE 只有在没有锁屏时会显示通知
     * VISIBILITY_SECRET 在安全锁和没有锁屏的情况下显示通知
     *
     * @param visibility
     */
    @NonNull
    @Override
    public XNotification setVisibility(int visibility) {
        return (XNotification) super.setVisibility(visibility);
    }

    /**
     * 任何情况都会显示通知
     */
    public XNotification setPublicVisibility() {
        return setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
    }

    /**
     * 只有在没有锁屏时会显示通知
     */
    public XNotification setPrivateVisibility() {
        return setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
    }

    /**
     * 在安全锁和没有锁屏的情况下显示通知
     */
    public XNotification setSecretVisibility() {
        return setVisibility(NotificationCompat.VISIBILITY_SECRET);
    }

    /**
     * 设置安全锁屏下的通知
     *
     * @param n
     */
    @NonNull
    @Override
    public XNotification setPublicVersion(@Nullable Notification n) {
        setPublicVisibility();
        return (XNotification) super.setPublicVersion(n);
    }

    public XNotification setPublicVersion() {
        setPublicVersion(build());
        return this;
    }

    /**
     * 设置小通知的布局
     *
     * @param contentView
     */
    @NonNull
    @Override
    public XNotification setCustomContentView(@Nullable RemoteViews contentView) {
        return (XNotification) super.setCustomContentView(contentView);
    }

    /**
     * 设置通知的布局
     *
     * @param contentView
     */
    @NonNull
    @Override
    public XNotification setCustomBigContentView(@Nullable RemoteViews contentView) {
        return (XNotification) super.setCustomBigContentView(contentView);
    }

    /**
     * 设置悬挂通知的布局
     *
     * @param contentView
     */
    @NonNull
    @Override
    public XNotification setCustomHeadsUpContentView(@Nullable RemoteViews contentView) {
        return (XNotification) super.setCustomHeadsUpContentView(contentView);
    }

    /**
     * 创建通知时指定channelID
     *
     * @param channelId
     */
    @NonNull
    @Override
    public XNotification setChannelId(@NonNull String channelId) {
        return (XNotification) super.setChannelId(channelId);
    }

    /**
     * 设置超时时间，超时之后自动取消。
     *
     * @param durationMs
     */
    @NonNull
    @Override
    public XNotification setTimeoutAfter(long durationMs) {
        return (XNotification) super.setTimeoutAfter(durationMs);
    }

    @NonNull
    @Override
    public XNotification setShortcutId(@Nullable String shortcutId) {
        return (XNotification) super.setShortcutId(shortcutId);
    }

    @NonNull
    @Override
    public XNotification setShortcutInfo(@Nullable ShortcutInfoCompat shortcutInfo) {
        return (XNotification) super.setShortcutInfo(shortcutInfo);
    }

    @NonNull
    @Override
    public XNotification setLocusId(@Nullable LocusIdCompat locusId) {
        return (XNotification) super.setLocusId(locusId);
    }

    @NonNull
    @Override
    public XNotification setBadgeIconType(int icon) {
        return (XNotification) super.setBadgeIconType(icon);
    }

    /**
     * 设置群组提醒的行为；
     * <p>
     * NotificationCompat.GROUP_ALERT_ALL：这意味着在有声音或振动的组中，所有通知都应该发出声音或振动，因此当该通知在组中时，不会将其静音
     * NotificationCompat.GROUP_ALERT_SUMMARY：即使将一个组中的摘要通知发布到具有声音和/或振动的通知通道中，也应使其静音（无声音或振动）
     * NotificationCompat.GROUP_ALERT_CHILDREN：一个组中的所有子通知都应该被静音（没有声音或振动），即使他们被发布到有声音和/或振动的通知频道。如果此通知是子组级，则使用此常量将此通知静音。这必须应用于所有要静音的子通知。
     *
     * @param groupAlertBehavior
     */
    @NonNull
    @Override
    public XNotification setGroupAlertBehavior(int groupAlertBehavior) {
        return (XNotification) super.setGroupAlertBehavior(groupAlertBehavior);
    }

    /**
     * 在有声音或振动的组中，所有通知都应该发出声音或振动，因此当该通知在组中时，不会将其静音
     */
    @NonNull
    public XNotification setGroupAlertAll() {
        return setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_ALL);
    }

    /**
     * 即使将一个组中的摘要通知发布到具有声音和/或振动的通知通道中，也应使其静音（无声音或振动）
     */
    @NonNull
    public XNotification setGroupAlertSummary() {
        return setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_SUMMARY);
    }

    /**
     * 一个组中的所有子通知都应该被静音（没有声音或振动），即使他们被发布到有声音和/或振动的通知频道。
     * 如果此通知是子组级，则使用此常量将此通知静音。这必须应用于所有要静音的子通知。
     */
    @NonNull
    public XNotification setGroupAlertChildren() {
        return setGroupAlertBehavior(NotificationCompat.GROUP_ALERT_CHILDREN);
    }

    @NonNull
    @Override
    public XNotification setBubbleMetadata(@Nullable NotificationCompat.BubbleMetadata data) {
        return (XNotification) super.setBubbleMetadata(data);
    }

    @NonNull
    @Override
    public XNotification extend(@NonNull NotificationCompat.Extender extender) {
        return (XNotification) super.extend(extender);
    }

    @NonNull
    @Override
    public XNotification setAllowSystemGeneratedContextualActions(boolean allowed) {
        return (XNotification) super.setAllowSystemGeneratedContextualActions(allowed);
    }

    @NonNull
    @Override
    public XNotification addPerson(@Nullable String uri) {
        return (XNotification) super.addPerson(uri);
    }

    @NonNull
    @Override
    public XNotification addPerson(@Nullable Person person) {
        return (XNotification) super.addPerson(person);
    }

    @NonNull
    @Override
    public XNotification clearPeople() {
        return (XNotification) super.clearPeople();
    }


    public static class Channel {
        @NonNull
        private final String mId;
        private CharSequence mName = "Default-Channel";
        private int mImportance = NotificationManager.IMPORTANCE_HIGH;
        private String mDescription = "Default channel";
        private String mGroupId;
        private boolean mShowBadge = true;
        private Uri mSound = Settings.System.DEFAULT_NOTIFICATION_URI;
        private AudioAttributes mAudioAttributes;
        private boolean mLights;
        private int mLightColor = 0;
        private boolean mVibrationEnabled;
        private long[] mVibrationPattern;

        private boolean mBypassDnd;
        private boolean mCanBubble;

        public Channel(@NonNull String id) {
            this.mId = id;
            if (Build.VERSION.SDK_INT >= 21) {
                this.mAudioAttributes = Notification.AUDIO_ATTRIBUTES_DEFAULT;
            }
        }

        /**
         * 设置渠道名称
         *
         * @param name
         */
        @NonNull
        public Channel setName(@Nullable CharSequence name) {
            this.mName = name;
            return this;
        }

        /**
         * 设置重要性
         * IMPORTANCE_NONE 关闭通知
         * IMPORTANCE_MIN 开启通知，不会弹出，但没有提示音，状态栏中无显示
         * IMPORTANCE_LOW 开启通知，不会弹出，不发出提示音，状态栏中显示
         * IMPORTANCE_DEFAULT 开启通知，不会弹出，发出提示音，状态栏中显示
         * IMPORTANCE_HIGH 开启通知，会弹出，发出提示音，状态栏中显示
         *
         * @param importance
         */
        @NonNull
        public Channel setImportance(int importance) {
            this.mImportance = importance;
            return this;
        }

        /**
         * 设置渠道的描述信息。
         *
         * @param description
         */
        @NonNull
        public Channel setDescription(@Nullable String description) {
            this.mDescription = description;
            return this;
        }

        /**
         * 设置渠道分组
         *
         * @param groupId
         */
        @NonNull
        public Channel setGroup(@Nullable String groupId) {
            this.mGroupId = groupId;
            return this;
        }

        /**
         * 显示通知角标。
         *
         * @param showBadge
         */
        @NonNull
        public Channel setShowBadge(boolean showBadge) {
            this.mShowBadge = showBadge;
            return this;
        }

        /**
         * 设置铃声
         *
         * @param sound
         */
        @NonNull
        public Channel setSound(@Nullable Uri sound) {
            this.mSound = sound;
            return this;
        }

        /**
         * 设置铃声
         *
         * @param audioAttributes
         */
        @NonNull
        public Channel setAudioAttributes(@Nullable AudioAttributes audioAttributes) {
            this.mAudioAttributes = audioAttributes;
            return this;
        }

        /**
         * 设置通知出现时的闪灯（如果 android 设备支持的话）
         *
         * @param lights
         */
        @NonNull
        public Channel setLightsEnabled(boolean lights) {
            this.mLights = lights;
            return this;
        }

        /**
         * 设置闪灯的颜色
         *
         * @param argb
         */
        @NonNull
        public Channel setLightColor(int argb) {
            this.mLightColor = argb;
            return this;
        }

        /**
         * 设置通知出现时的震动（如果 android 设备支持的话）
         *
         * @param vibration
         */
        @NonNull
        public Channel setVibrationEnabled(boolean vibration) {
            this.mVibrationEnabled = vibration;
            return this;
        }

        /**
         * 设置震动模式
         *
         * @param vibrationPattern
         */
        @NonNull
        public Channel setVibrationPattern(@Nullable long[] vibrationPattern) {
            this.mVibrationPattern = vibrationPattern;
            this.mVibrationEnabled = vibrationPattern != null && vibrationPattern.length > 0;
            return this;
        }

        /**
         * 设置可以绕过请勿打扰模式。
         *
         * @param bypassDnd
         */
        @NonNull
        public Channel setBypassDnd(boolean bypassDnd) {
            this.mBypassDnd = bypassDnd;
            return this;
        }

        /**
         * 设置发布到此频道的通知是否可以显示在通知栏的外面，以气泡的形式浮动在其他应用程序的内容上。
         *
         * @param canBubble
         */
        @NonNull
        public Channel setCanBubble(boolean canBubble) {
            this.mCanBubble = canBubble;
            return this;
        }

        public XNotification create(Context context) {
            initChannel(context);
            return new XNotification(context, mId);
        }

        public void initChannel(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = notificationChannel();
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                manager.createNotificationChannel(channel);
            }
        }

        public NotificationChannel notificationChannel() {
            if (Build.VERSION.SDK_INT < 26) {
                return null;
            }
            NotificationChannel channel = new NotificationChannel(mId, mName, mImportance);
            channel.setDescription(mDescription);
            if (!TextUtils.isEmpty(mGroupId)) {
                channel.setGroup(mGroupId);
            }
            channel.setShowBadge(mShowBadge);
            channel.setSound(mSound, mAudioAttributes);
            channel.enableLights(mLights);
            channel.setLightColor(mLightColor);
            if (mVibrationPattern != null) {
                channel.setVibrationPattern(mVibrationPattern);
            }
            channel.enableVibration(mVibrationEnabled);
            channel.setBypassDnd(mBypassDnd);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                channel.setAllowBubbles(mCanBubble);
            }
            return channel;
        }
    }
}

