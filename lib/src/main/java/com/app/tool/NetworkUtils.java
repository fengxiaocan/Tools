package com.app.tool;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.annotation.RequiresPermission;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * <pre>
 *
 *
 *     time  : 2016/08/02
 *     desc  : 网络相关工具类
 * </pre>
 */
class NetworkUtils extends Util {
    private static final int NETWORK_TYPE_GSM = 16;
    private static final int NETWORK_TYPE_TD_SCDMA = 17;
    private static final int NETWORK_TYPE_IWLAN = 18;

    /**
     * 打开网络设置界面
     * <p>3.0以下打开设置界面</p>
     */
    public static void openWirelessSettings() {
        Intent intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(intent);
    }

    /**
     * 获取活动网络信息 <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return NetworkInfo
     */
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    public static NetworkInfo getActiveNetworkInfo() {
        return ((ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    }

    /**
     * 判断网络是否连接 <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isConnected() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isConnected();
    }

    /**
     * 判断网络是否可用
     *
     * @return
     */
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isAvailable() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    /**
     * 判断网络是否可用 <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.INTERNET"/>}</p>
     *
     * @return {@code true}: 可用<br>{@code false}: 不可用
     */
    public static boolean isAvailableByPing() {
        int result = ShellUtils.execCmd("ping -c 1 -w 1 223.5.5.5", false);
        return result == 0;
    }

    /**
     * 判断移动数据是否打开
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean isOpenMobileWeb() {
        try {
            TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
            Method getMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("getDataEnabled");
            if (null != getMobileDataEnabledMethod) {
                return (boolean) getMobileDataEnabledMethod.invoke(tm);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 打开或关闭移动数据 <p>需系统应用 需添加权限{@code <uses-permission android:name="android
     * .permission.MODIFY_PHONE_STATE"/>}</p>
     *
     * @param enabled {@code true}: 打开<br>{@code false}: 关闭
     */
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    public static void setMobileWebEnabled(boolean enabled) {
        try {
            TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
            Method setMobileDataEnabledMethod = tm.getClass().getDeclaredMethod("setDataEnabled", boolean.class);
            if (null != setMobileDataEnabledMethod) {
                setMobileDataEnabledMethod.invoke(tm, enabled);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断网络是否是4G <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.ACCESS_NETWORK_STATE"/>}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    public static boolean is4G() {
        NetworkInfo info = getActiveNetworkInfo();
        return info != null && info.isAvailable() && info.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE;
    }

    /**
     * 判断wifi是否打开 <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isWifiEnabled() {
        return WifiConnect.isWifiEnabled();
    }


    /**
     * 判断wifi数据是否可用 <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.ACCESS_WIFI_STATE"/>}</p>
     * <p>需添加权限 {@code <uses-permission android:name="android.permission
     * .INTERNET"/>}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isWifiAvailable() {
        return isWifiEnabled() && isAvailableByPing();
    }

    /**
     * 获取网络运营商名称
     * <p>中国移动、如中国联通、中国电信</p>
     *
     * @return 运营商名称
     */
    public static String getNetworkOperatorName() {
        TelephonyManager tm = (TelephonyManager) getContext().getSystemService(Context.TELEPHONY_SERVICE);
        return tm != null ? tm.getNetworkOperatorName() : null;
    }

    /**
     * 获取当前网络类型 <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.ACCESS_NETWORK_STATE"/>}</p>
     */
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    public static Type getNetworkType() {
        NetworkInfo info = getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return Type.WIFI;
            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case NETWORK_TYPE_GSM:
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return Type.MOBILE_2G;

                    case NETWORK_TYPE_TD_SCDMA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return Type.MOBILE_3G;

                    case NETWORK_TYPE_IWLAN:
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return Type.MOBILE_4G;
                    default:
                        String subtypeName = info.getSubtypeName();
                        if (subtypeName.equalsIgnoreCase("TD-SCDMA")
                                || subtypeName.equalsIgnoreCase("WCDMA") ||
                                subtypeName.equalsIgnoreCase("CDMA2000")) {
                            return Type.MOBILE_3G;
                        } else {
                            return Type.UNKNOWN;
                        }
                }
            } else {
                return Type.UNKNOWN;
            }
        }
        return Type.UNKNOWN;
    }

    /**
     * 获取IP地址 <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.INTERNET"/>}</p>
     *
     * @param useIPv4 是否用IPv4
     * @return IP地址
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回10.0.2.15
                if (!ni.isUp()) {
                    continue;
                }
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) {
                                return hostAddress;
                            }
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index)
                                        .toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取域名ip地址 <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.INTERNET"/>}</p>
     *
     * @param domain 域名
     * @return ip地址
     */
    public static String getDomainAddress(final String domain) {
        try {
            ExecutorService exec = Executors.newCachedThreadPool();
            Future<String> fs = exec.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    InetAddress inetAddress;
                    try {
                        inetAddress = InetAddress.getByName(domain);
                        return inetAddress.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            });
            return fs.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取网络状态
     *
     * @return
     */
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    public static State getNetWorkState() {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return State.WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return State.MOBILE;
            }
        }
        return State.UNKNOWN;
    }


    /**
     * 获取网络信息
     *
     * @return
     */
    @RequiresPermission(value = Manifest.permission.ACCESS_NETWORK_STATE)
    public static String getNetworkInfo() {
        ConnectivityManager systemService = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = systemService.getActiveNetworkInfo();
        if (info != null && info.isConnected() && info.isAvailable()) {
            switch (info.getType()) {
                default:
                    return "UNKNOW";
                case ConnectivityManager.TYPE_MOBILE:
                    int nSubType = info.getSubtype();
                    TelephonyManager telephonyManager = (TelephonyManager) getContext().getSystemService(
                            Context.TELEPHONY_SERVICE);
                    //3G   联通的3G为UMTS或HSDPA 电信的3G为EVDO
                    String type;
                    switch (nSubType) {
                        case TelephonyManager.NETWORK_TYPE_GSM:
                            type = "GSM";
                            break;
                        case TelephonyManager.NETWORK_TYPE_GPRS:
                            type = "GPRS";
                            break;
                        case TelephonyManager.NETWORK_TYPE_CDMA:
                            type = "CDMA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EDGE:
                            type = "EDGE";
                            break;
                        case TelephonyManager.NETWORK_TYPE_1xRTT:
                            type = "1xRTT";
                            break;
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            type = "IDEN";
                            break;
                        case TelephonyManager.NETWORK_TYPE_TD_SCDMA:
                            type = "TD_SCDMA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                            type = "EVDO_A";
                            break;
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                            type = "UMTS";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                            type = "EVDO_0";
                            break;
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                            type = "HSDPA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                            type = "HSUPA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                            type = "HSPA";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                            type = "EVDO_B";
                            break;
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                            type = "EHRPD";
                            break;
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            type = "HSPAP";
                            break;
                        case TelephonyManager.NETWORK_TYPE_IWLAN:
                            type = "IWLAN";
                            break;
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            type = "LTE";
                            break;
                        default:
                            type = info.getSubtypeName();
                            break;
                    }

                    if (telephonyManager.isNetworkRoaming()) {
                        type += " 漫游";
                    }
                    return type;
                case ConnectivityManager.TYPE_WIFI:
                    return "WIFI";
                case ConnectivityManager.TYPE_MOBILE_MMS:
                    return "MOBILE_MMS";
                case ConnectivityManager.TYPE_MOBILE_SUPL:
                    return "MOBILE_SUPL";
                case ConnectivityManager.TYPE_MOBILE_DUN:
                    return "MOBILE_DUN";
                case ConnectivityManager.TYPE_MOBILE_HIPRI:
                    return "MOBILE_HIPRI";
                case ConnectivityManager.TYPE_WIMAX:
                    return "WIMAX";
                case ConnectivityManager.TYPE_BLUETOOTH:
                    return "BLUETOOTH";
                case ConnectivityManager.TYPE_DUMMY:
                    return "DUMMY";
                case ConnectivityManager.TYPE_ETHERNET:
                    return "ETHERNET";
                case ConnectivityManager.TYPE_VPN:
                    return "VPN";
                case 10:
                    return "MOBILE_FOTA";
                case 11:
                    return "MOBILE_IMS";
                case 12:
                    return "MOBILE_CBS";
                case 13:
                    return "WIFI_P2P";
                case 14:
                    return "MOBILE_IA";
                case 15:
                    return "MOBILE_EMERGENCY";
                case 16:
                    return "PROXY";
            }
        }
        return "UNKNOW";
    }

    public enum Type {
        MOBILE_2G,
        MOBILE_3G,
        MOBILE_4G,
        MOBILE_5G,
        WIFI,
        UNKNOWN
    }

    public enum State {
        MOBILE,
        WIFI,
        UNKNOWN
    }
}