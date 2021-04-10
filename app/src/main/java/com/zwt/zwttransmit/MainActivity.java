package com.zwt.zwttransmit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;

import com.zwt.zwttransmit.broadcast.NetWorkChangReceiver;
import com.zwt.zwttransmit.databinding.ActivityMainBinding;
import com.zwt.zwttransmit.manager.WifiChangeManager;


public class MainActivity extends BaseActivity {
    // ViewBinding 绑定
    ActivityMainBinding inflate;
    NetWorkChangReceiver networkChangeReceiver;
    WifiChangeManager.WifiIconCallBack wifiCallBack = this::changWifiPicture;

    @Override
    public void initAllViews() {
        // 设置自定义工具栏
        setSupportActionBar(inflate.mainToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            //设置Toolbar home键可点击
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置Toolbar home键图标 如果不设置，默认是一个箭头
            actionBar.setHomeAsUpIndicator(R.drawable.ic_liebiao);
        }

    }

    @Override
    public void initAllDatum() {
        initWifiStatusMonitor();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        initAllDatum();
        initAllViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (networkChangeReceiver!= null){
            unregisterReceiver(networkChangeReceiver);
        }
    }

    // 添加菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar_menu, menu);
        return true;
    }

    // 点击菜单选项后回掉
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                inflate.mainDrawerlayout.openDrawer(GravityCompat.START);
                break;
            case R.id.toolbar_saoyisao:

                break;
            default:
                break;
        }
        return true;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initWifiStatusMonitor(){

        // 注册广播7.0以上静态广播接收不到
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        networkChangeReceiver = new NetWorkChangReceiver();
        registerReceiver(networkChangeReceiver, intentFilter);

        // 注册回调函数
        WifiChangeManager.getInstance().registerWifiIconCallBack(wifiCallBack);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void changWifiPicture(int wifiCode){
        switch (wifiCode){
            case WifiChangeManager.NET_NO_CONNECT:
                inflate.ivWifi.setImageDrawable(getResources().getDrawable(R.drawable.ic_no_wifi));
                break;
            case WifiChangeManager.NET_WIFI_MAX:
                inflate.ivWifi.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_4));
                break;
            case WifiChangeManager.NET_WIFI_ABOVE:
                inflate.ivWifi.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_3));
                break;
            case WifiChangeManager.NET_WIFI_MIDDLE:
                inflate.ivWifi.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_2));
                break;
            case WifiChangeManager.NET_WIFI_MIN:
                inflate.ivWifi.setImageDrawable(getResources().getDrawable(R.drawable.ic_wifi_1));
                break;
            default:
                break;
        }

    }

    // 跳转到本页面
    public static void actionStart(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}