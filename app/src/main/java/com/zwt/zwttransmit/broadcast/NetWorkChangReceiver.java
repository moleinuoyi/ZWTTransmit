package com.zwt.zwttransmit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.zwt.zwttransmit.manager.WifiChangeManager;

public class NetWorkChangReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        Log.d("zwt", "收到广播");
        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    WifiChangeManager.getInstance().WifiIconChange(WifiChangeManager.NET_NO_CONNECT);
                    Log.d("zwt", "wifi断开连接");
                    break;
                case WifiManager.WIFI_STATE_ENABLING:
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    WifiChangeManager.getInstance().WifiIconChange(context);
                    Log.d("zwt", "wifi连接");
                    break;
                default:
                    break;
            }
        }
    }
}
