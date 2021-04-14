package com.zwt.zwttransmit.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zwt.zwttransmit.R;

public class YinYueFragment extends Fragment {

    public YinYueFragment() {
        // Required empty public constructor
    }

    public static YinYueFragment newInstance() {
        return new YinYueFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_yinyue, container, false);
    }
}