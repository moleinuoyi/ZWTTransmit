package com.zwt.zwttransmit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;

import com.zwt.zwttransmit.databinding.ActivityMainBinding;


public class MainActivity extends BaseActivity {
    // ViewBinding 绑定
    ActivityMainBinding inflate;

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
        //TODO
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflate = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(inflate.getRoot());

        initAllViews();
        initAllDatum();
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

    // 跳转到本页面
    public static void actionStart(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }
}