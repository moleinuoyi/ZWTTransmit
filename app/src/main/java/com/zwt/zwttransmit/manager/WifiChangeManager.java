package com.zwt.zwttransmit.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

public class WifiChangeManager {

    public static final int NET_NO_CONNECT = 0;
    public static final int NET_WIFI_MIN = 1;
    public static final int NET_WIFI_MIDDLE = 2;
    public static final int NET_WIFI_ABOVE = 3;
    public static final int NET_WIFI_MAX = 4;

    private static volatile WifiChangeManager wifiChangeManager;
    // 回调函数
    private WifiIconCallBack wifiIconCallBack;

    private WifiChangeManager(){}

    // 单例模式
    public static WifiChangeManager getInstance() {
        if(wifiChangeManager == null) {
            synchronized(WifiChangeManager.class) {
                if(wifiChangeManager == null) {
                    wifiChangeManager = new WifiChangeManager();
                }
            }
        }
        return wifiChangeManager;
    }

    // 注册回调函数
    public void registerWifiIconCallBack(WifiIconCallBack callBack){
        wifiIconCallBack = callBack;
    }
    // 解绑回调函数
    public void unregisterWifiIconCallBack(){
        wifiIconCallBack = null;
    }

    // 接收到广播时事件
    public void WifiIconChange(Context context){
        WifiIconChange(isNetworkAvailable(context));
    }
    public void WifiIconChange(int netType){
        if (wifiIconCallBack!=null){
            wifiIconCallBack.changeWifiIcon(netType);
        }
    }

    //判斷WiFi是否可用
    public Boolean isWifiAvailable(Context context){
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        return wifiManager != null && wifiManager.isWifiEnabled();
    }

    // 判断网路连接类型
    private int isNetworkAvailable(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT>=23){
            NetworkCapabilities networkCapabilities = connectMgr.getNetworkCapabilities(connectMgr.getActiveNetwork());
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)){
                return NetworkSignalStrength(context);
            }else {
                return WifiChangeManager.NET_NO_CONNECT;
            }
        }else {
            NetworkInfo wifiNetInfo = connectMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (wifiNetInfo != null && wifiNetInfo.isConnected()) {
                return NetworkSignalStrength(context);
            } else {
                return WifiChangeManager.NET_NO_CONNECT;
            }
        }
    }

    // 判断网络信号强度
    private static int NetworkSignalStrength(Context context){
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int level = wifiInfo.getRssi();
        //得到的值是一个0到-100的区间值，是一个int型数据，其中0到-50表示信号最好，-50到-70表示信号偏差，小于-70表示最差，有可能连接不上或者掉线。
        if (level <= 0 && level >= -45) {
            return WifiChangeManager.NET_WIFI_MAX;
        } else if(level < -45 && level >= -60){
            return WifiChangeManager.NET_WIFI_ABOVE;
        } else if (level < -60 && level >= -80) {
            return WifiChangeManager.NET_WIFI_MIDDLE;
        } else if (level < -80) {
            return WifiChangeManager.NET_WIFI_MIN;
        } else {
            return WifiChangeManager.NET_WIFI_MIN;
        }
    }

    public interface WifiIconCallBack{
        void changeWifiIcon(int wifiStatusCode);
    }
}
