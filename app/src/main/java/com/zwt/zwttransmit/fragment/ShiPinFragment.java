package com.zwt.zwttransmit.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.View;

import com.zwt.zwttransmit.BaseFragment;
import com.zwt.zwttransmit.adapter.SectionNewDecoration;
import com.zwt.zwttransmit.adapter.VideoAdapter;
import com.zwt.zwttransmit.databinding.FragmentShipinBinding;
import com.zwt.zwttransmit.modle.PhotoViewModel;
import com.zwt.zwttransmit.modle.Video;
import com.zwt.zwttransmit.modle.VideoViewModel;

import java.util.ArrayList;
import java.util.Map;

public class ShiPinFragment extends BaseFragment<FragmentShipinBinding> {

    public ShiPinFragment() {
        // Required empty public constructor
    }

    @Override
    public void initAllViews() {
        final GridLayoutManager layoutManager = new GridLayoutManager(viewBinding.getRoot().getContext(), 1);
        viewBinding.shipinRecyclerView.setLayoutManager(layoutManager);
        VideoViewModel model = new ViewModelProvider(this).get(VideoViewModel.class);

        model.getMusicLiveData().observe(getViewLifecycleOwner(), (Map<String, ArrayList<Video>> stringArrayListMap) -> {
            final ArrayList<String> videoMapKey = new ArrayList<String>(stringArrayListMap.keySet());
            viewBinding.shipinRecyclerView.addItemDecoration(new SectionNewDecoration((int position) -> {
                if(videoMapKey.get(position).length()>0) {
                    return videoMapKey.get(position);
                }
                return "-1";
            }));

            VideoAdapter adapter = new VideoAdapter(stringArrayListMap);
            viewBinding.shipinRecyclerView.setAdapter(adapter);
        });

    }

    @Override
    public void initAllDatum() {

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
        Log.d("lyt", "?????????????????????,?????????????????????");
        Log.d("lyt", "??????????????????????,?????????????????????");
        Log.d("lyt", "?????????????????????,?????????????????????");
        Log.d("lyt", "?????????????????????,?????????????????????");
    }

    public static ShiPinFragment newInstance() {
        return new ShiPinFragment();
    }
}