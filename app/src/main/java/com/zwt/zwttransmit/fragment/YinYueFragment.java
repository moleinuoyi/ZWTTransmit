package com.zwt.zwttransmit.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zwt.zwttransmit.BaseFragment;
import com.zwt.zwttransmit.R;
import com.zwt.zwttransmit.adapter.MusicAdapter;
import com.zwt.zwttransmit.adapter.SectionNewDecoration;
import com.zwt.zwttransmit.databinding.FragmentYinyueBinding;
import com.zwt.zwttransmit.modle.Music;
import com.zwt.zwttransmit.modle.MusicViewModel;
import com.zwt.zwttransmit.modle.PhotoViewModel;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class YinYueFragment extends BaseFragment<FragmentYinyueBinding> {

    //目标项是否在最后一个可见项之后
    private boolean mShouldScroll;
    // 记录目标项位置
    private int mToPosition;

    public YinYueFragment() {
        // Required empty public constructor
    }

    @Override
    public void initAllViews() {
        // 设置Sidebar 回调事件
        viewBinding.yinyueSidebar.setOnStrSelectCallBack((int index, String selectStr)->{
            smoothMoveToPosition(index);
        });
        final GridLayoutManager layoutManager = new GridLayoutManager(viewBinding.getRoot().getContext(), 1);
        viewBinding.yinyueRecyclerView.setLayoutManager(layoutManager);

        // 添加滚动事件监听器
        viewBinding.yinyueRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mShouldScroll) {
                    mShouldScroll = false;
                    smoothMoveToPosition(mToPosition);
                }
            }
        });

        /**********获得数据**************/
        final MusicViewModel model = new ViewModelProvider(this).get(MusicViewModel.class);
        model.getMusicLiveData().observe(getViewLifecycleOwner(), (Map<String,ArrayList<Music>> musicMap) -> {

            // 添加分隔符
            viewBinding.yinyueRecyclerView.addItemDecoration(new SectionNewDecoration((int position) ->{
                if(Objects.requireNonNull(musicMap.get(SideBar.letters[position])).size()>0) {
                    return SideBar.letters[position];
                }
                return "-1";
            }));

            MusicAdapter adapter = new MusicAdapter(musicMap);
            viewBinding.yinyueRecyclerView.setAdapter(adapter);

        });

    }

    @Override
    public void initAllDatum() {

    }

    public static YinYueFragment newInstance() {
        return new YinYueFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initAllViews();
        initAllDatum();
    }

    private void smoothMoveToPosition(final int position) {
        RecyclerView recyclerView = viewBinding.yinyueRecyclerView;
        // 第一个可见位置
        int firstItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(0));
        // 最后一个可见位置
        int lastItem = recyclerView.getChildLayoutPosition(recyclerView.getChildAt(recyclerView.getChildCount() - 1));

        if (position < firstItem) {
            // 如果跳转位置在第一个可见位置之前，就smoothScrollToPosition可以直接跳转
            recyclerView.smoothScrollToPosition(position);
        } else if (position <= lastItem) {
            // 跳转位置在第一个可见项之后，最后一个可见项之前
            // smoothScrollToPosition根本不会动，此时调用smoothScrollBy来滑动到指定位置
            int movePosition = position - firstItem;
            if (movePosition >= 0 && movePosition < recyclerView.getChildCount()) {
                int top = recyclerView.getChildAt(movePosition).getTop();
                recyclerView.smoothScrollBy(0, top);
            }
        } else {
            // 如果要跳转的位置在最后可见项之后，则先调用smoothScrollToPosition将要跳转的位置滚动到可见位置
            // 再通过onScrollStateChanged控制再次调用smoothMoveToPosition，执行上一个判断中的方法
            recyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mShouldScroll = true;
        }
    }
}