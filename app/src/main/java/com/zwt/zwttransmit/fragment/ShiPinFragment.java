package com.zwt.zwttransmit.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zwt.zwttransmit.R;
import com.zwt.zwttransmit.databinding.FragmentShipinBinding;

public class ShiPinFragment extends Fragment {

    FragmentShipinBinding inflate;

    public ShiPinFragment() {
        // Required empty public constructor
    }

    public static ShiPinFragment newInstance() {
        return new ShiPinFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipin, container, false);
    }
}