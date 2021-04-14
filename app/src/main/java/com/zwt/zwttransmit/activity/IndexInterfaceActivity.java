package com.zwt.zwttransmit.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.zwt.zwttransmit.BaseActivity;
import com.zwt.zwttransmit.R;
import com.zwt.zwttransmit.databinding.ActivityIndexInterfaceBinding;
import com.zwt.zwttransmit.fragment.ShiPinFragment;
import com.zwt.zwttransmit.fragment.TuPianFragment;
import com.zwt.zwttransmit.fragment.YinYueFragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class IndexInterfaceActivity extends BaseActivity<ActivityIndexInterfaceBinding> {

    public Map<String, Fragment> tabMap = new LinkedHashMap<String, Fragment>(){
        {
            put("图片", TuPianFragment.newInstance());
            put("音乐", YinYueFragment.newInstance());
            put("视频", ShiPinFragment.newInstance());
        }
    };


    @Override
    public void initAllViews() {
        // 设置自定义工具栏
        setSupportActionBar(viewBinding.indexToolbar);
        // 添加左边菜单按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            //设置Toolbar home键可点击
            actionBar.setDisplayHomeAsUpEnabled(true);
            //设置Toolbar home键图标 如果不设置，默认是一个箭头
            actionBar.setHomeAsUpIndicator(R.drawable.ic_liebiao);
        }

        // 再actionBar中添加导航栏
        final List<String> keys = new ArrayList<String>(tabMap.keySet());

        for (String key : keys) {
            viewBinding.indexTablayout.addTab(viewBinding.indexTablayout.newTab().setText(key));
        }
        viewBinding.indexViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return Objects.requireNonNull(tabMap.get(keys.get(position)));
            }

            @Override
            public int getCount() {
                return tabMap.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return keys.get(position);
            }
        });
        //设置缓存页面数
        // viewPager.setOffscreenPageLimit(2);
        // 关联 TabLayout 和 ViewPager 控件
        viewBinding.indexTablayout.setupWithViewPager(viewBinding.indexViewPager,false);
    }

    @Override
    public void initAllDatum() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initAllDatum();
        initAllViews();

    }

    // 添加菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.indexinterface_toolbar_menu, menu);
        return true;
    }

    // 点击菜单选项后回掉
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                viewBinding.indexDrawerLayout.openDrawer(GravityCompat.START);
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
        Intent intent = new Intent(context, IndexInterfaceActivity.class);
        context.startActivity(intent);
    }
}