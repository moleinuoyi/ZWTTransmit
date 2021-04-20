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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zwt.zwttransmit.BaseFragment;
import com.zwt.zwttransmit.R;
import com.zwt.zwttransmit.adapter.PhotoAdapter;
import com.zwt.zwttransmit.adapter.SectionNewDecoration;
import com.zwt.zwttransmit.databinding.FragmentTupianBinding;
import com.zwt.zwttransmit.modle.Photo;
import com.zwt.zwttransmit.modle.PhotoViewModel;

import java.util.ArrayList;
import java.util.Map;

public class TuPianFragment extends BaseFragment<FragmentTupianBinding> {

    public TuPianFragment() {
        // Required empty public constructor
    }

    @Override
    public void initAllViews() {

        GridLayoutManager layoutManager = new GridLayoutManager(viewBinding.getRoot().getContext(), 1);
        viewBinding.tupianRecyclerView.setLayoutManager(layoutManager);
        //获取图片
        final PhotoViewModel model = new ViewModelProvider(this).get(PhotoViewModel.class);

        //监听 LiveData 中 photoLiveData 属性变化，只要触发了setValue/postValue方法就会被调用
        model.getPhotoLiveData().observe(getViewLifecycleOwner(), new Observer<Map<String, ArrayList<Photo>>>() {
            @Override
            public void onChanged(final Map<String, ArrayList<Photo>> stringArrayListMap) {

                final ArrayList<String> photoMapKey = new ArrayList<String>(stringArrayListMap.keySet());
                viewBinding.tupianRecyclerView.addItemDecoration(new SectionNewDecoration((position)->{
                    if(photoMapKey.get(position).length()>0) {
                        return photoMapKey.get(position);
                    }
                    return "-1";
                }));
                PhotoAdapter adapter = new PhotoAdapter(stringArrayListMap);
                viewBinding.tupianRecyclerView.setAdapter(adapter);
            }
        });

    }

    @Override
    public void initAllDatum() {

    }

    public static TuPianFragment newInstance() {
        return new TuPianFragment();
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

}