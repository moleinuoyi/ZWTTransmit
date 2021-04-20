package com.zwt.zwttransmit;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import org.xmlpull.v1.XmlPullParser;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

public abstract class BaseFragment<T extends ViewBinding> extends Fragment {
    private static final String TAG = "BaseActivity";
    protected T viewBinding;

    public abstract void initAllViews();
    public abstract void initAllDatum();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, getClass().getSimpleName());
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        assert type != null;
        Class cls = (Class) type.getActualTypeArguments()[0];
        try {
            Method inflate = cls.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            viewBinding = (T) inflate.invoke(null, inflater, container, false);
        }  catch (NoSuchMethodException | IllegalAccessException| InvocationTargetException e) {
            e.printStackTrace();
        }
        assert viewBinding != null;
        return viewBinding.getRoot();
    }

}
