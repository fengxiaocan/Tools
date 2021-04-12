package com.app.tool;

import android.Manifest;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.SystemClock;
import android.text.TextUtils;

import androidx.annotation.RequiresPermission;

import java.util.List;

class WifiConnect {

    public static WifiManager getWifiManager() {
        return (WifiManager) Tools.getContext().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 判断wifi是否打开 <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.ACCESS_WIFI_STATE"/>}</p>
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isWifiEnabled() {
        return getWifiManager().isWifiEnabled();
    }

    /**
     * 打开或关闭wifi <p>需添加权限 {@code <uses-permission android:name="android
     * .permission.CHANGE_WIFI_STATE"/>}</p>
     *
     * @param enabled {@code true}: 打开<br>{@code false}: 关闭
     */
    public static void setWifiEnabled(boolean enabled) {
        WifiManager wifiManager = getWifiManager();
        if (enabled) {
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
        } else {
            if (wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(false);
            }
        }
    }

    /**
     * 打开wifi功能
     *
     * @return
     */
    public static boolean openWifi() {
        boolean bRet = true;
        WifiManager wifiManager = getWifiManager();
        if (!wifiManager.isWifiEnabled()) {
            bRet = wifiManager.setWifiEnabled(true);
        }
        return bRet;
    }

    /**
     * 打开wifi功能
     *
     * @return
     */
    public static boolean openWifi(WifiManager wifiManager) {
        boolean bRet = true;
        if (!wifiManager.isWifiEnabled()) {
            bRet = wifiManager.setWifiEnabled(true);
        }
        return bRet;
    }

    /**
     * 获取Wifi扫描结果
     *
     * @return
     */
    public static List<ScanResult> getWifiScanResults() {
        WifiManager wifiManager = getWifiManager();
        return wifiManager.getScanResults();//扫描到的周围热点信息
    }

    /**
     * 获取已连接WiFi的信息
     *
     * @return
     */
    public static WifiInfo getWifiConnectInfo() {
        WifiManager wifiManager = getWifiManager();
        return wifiManager.getConnectionInfo();//已连接wifi信息
    }

    /**
     * 获取已连接WiFi的信息
     *
     * @return
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE})
    public static List<WifiConfiguration> getWifiExistsInfo() {
        WifiManager wifiManager = getWifiManager();
        return wifiManager.getConfiguredNetworks();
    }

    /**
     * 连接WiFi
     *
     * @param SSID
     * @param Password
     * @return
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE})
    public static boolean connect(String SSID, String Password) {
        WifiManager wifiManager = getWifiManager();
        if (!openWifi(wifiManager)) {
            return false;
        }
        //开启wifi功能需要一段时间(我在手机上测试一般需要1-3秒左右)，所以要等到wifi
        //状态变成WIFI_STATE_ENABLED 的时候才能执行下面的语句
        //WifiManager.WIFI_STATE_ENABLING 2
        //WifiManager.WIFI_STATE_ENABLED 3
        while (wifiManager.getWifiState() == WifiManager.WIFI_STATE_ENABLING) {
            SystemClock.sleep(10);
        }

        WifiConfiguration wifiConfig;
        if (TextUtils.isEmpty(Password)) {
            wifiConfig = connectWifi(SSID);
        } else {
            wifiConfig = connectWifi(SSID, Password);
        }

        WifiConfiguration tempConfig = isExists(wifiManager, SSID);

        if (tempConfig != null) {
            wifiManager.removeNetwork(tempConfig.networkId);
        }

        int netID = wifiManager.addNetwork(wifiConfig);
        return wifiManager.enableNetwork(netID, false);
    }

    /**
     * 查看以前是否也配置过这个网络
     *
     * @param wifiManager
     * @param SSID
     * @return
     */
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE})
    private static WifiConfiguration isExists(WifiManager wifiManager, String SSID) {
        List<WifiConfiguration> existingConfigs = wifiManager.getConfiguredNetworks();
        String anObject = "\"" + SSID + "\"";
        for (WifiConfiguration existingConfig : existingConfigs) {
            if (existingConfig.SSID.equals(anObject)) {
                return existingConfig;
            }
        }
        return null;
    }


    /**
     * 连接有密码的wifi.
     *
     * @param SSID     ssid
     * @param Password Password
     * @return apConfig
     */
    protected static WifiConfiguration connectWifi(String SSID, String Password) {
        WifiConfiguration apConfig = new WifiConfiguration();
        apConfig.SSID = "\"" + SSID + "\"";
        apConfig.preSharedKey = "\"" + Password + "\"";
        //不广播其SSID的网络
        apConfig.hiddenSSID = true;
        apConfig.status = WifiConfiguration.Status.ENABLED;
        //公认的IEEE 802.11验证算法。
        apConfig.allowedAuthAlgorithms.clear();
        apConfig.allowedAuthAlgorithms.set(WifiConfiguration.AuthAlgorithm.OPEN);
        //公认的的公共组密码
        apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        apConfig.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        //公认的密钥管理方案
        apConfig.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        //密码为WPA。
        apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        apConfig.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        //公认的安全协议。
        apConfig.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return apConfig;
    }

    /**
     * 连接没有密码wifi.
     *
     * @param SSID ssid
     * @return configuration
     */
    protected static WifiConfiguration connectWifi(String SSID) {
        WifiConfiguration configuration = new WifiConfiguration();
        configuration.SSID = "\"" + SSID + "\"";
        configuration.status = WifiConfiguration.Status.ENABLED;
        configuration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
        configuration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        configuration.allowedPairwiseCiphers
                .set(WifiConfiguration.PairwiseCipher.TKIP);
        configuration.allowedPairwiseCiphers
                .set(WifiConfiguration.PairwiseCipher.CCMP);
        configuration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        return configuration;
    }

    /**
     * 判断是否有密码.
     *
     * @param result ScanResult
     * @return 0
     */
    public static Security getSecurity(ScanResult result) {
        if (null != result && null != result.capabilities) {
            if (result.capabilities.contains("WEP")) {
                return Security.WEP;
            } else if (result.capabilities.contains("PSK")) {
                return Security.PSK;
            } else if (result.capabilities.contains("EAP")) {
                return Security.EAP;
            }
        }
        return Security.NO_PASS;
    }

    //定义几种加密方式，一种是WEP，一种是WPA，还有没有密码的情况
    public enum Security {
        WEP,
        PSK,
        EAP,
        NO_PASS
    }
}
