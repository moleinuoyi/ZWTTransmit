package com.zwt.zwttransmit.modle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zwt.zwttransmit.MyApplication;
import com.zwt.zwttransmit.utils.TimeUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class PhotoViewModel extends ViewModel {

    Map<String, ArrayList<Photo>> photoMap = new LinkedHashMap<>();
    private MutableLiveData<Map<String, ArrayList<Photo>>> photoLiveData;

    String[] STORE_IMAGES = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Thumbnails.DATA,
            MediaStore.Images.Media.DISPLAY_NAME
    };

    public MutableLiveData<Map<String, ArrayList<Photo>>> getPhotoLiveData() {
        if (photoLiveData == null){
            photoLiveData = new MutableLiveData<Map<String, ArrayList<Photo>>>();
            loadPhoto();
        }
        return photoLiveData;
    }

    private void loadPhoto(){
        new Thread(()->{
            Context context = MyApplication.getContext();

            @SuppressLint("Recycle")
            Cursor cursor = context.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    STORE_IMAGES,
                    null,
                    null,
                    MediaStore.Images.Media.DATE_MODIFIED+" desc");

            int idIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
            int thumbPathIndex = cursor.getColumnIndex(MediaStore.Images.Thumbnails.DATA);
            int timeIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED);
            int displyNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
            long lastDate = 0;//获得第一个时间

            ArrayList<Photo> photoList = new ArrayList<>();
            while (cursor.moveToNext()){
                if (lastDate == 0)
                    lastDate = cursor.getLong(timeIndex);

                long id = cursor.getLong(idIndex);
                String thumbPath = cursor.getString(thumbPathIndex);
                long date = cursor.getLong(timeIndex);
                String filepath = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + id).toString();
                if (!TimeUtils.getDate(lastDate).equals(TimeUtils.getDate(date))) { //如果日期不同
                    lastDate = date;
                    photoMap.put(TimeUtils.getDateAdvanced(photoList.get(0).getTime()), photoList);
                    photoList = new ArrayList<>();
                }
                String filename = cursor.getString(displyNameIndex);
                Photo photo = new Photo(date, thumbPath, filepath, filename);
                photoList.add(photo);
            }
            if (lastDate != 0)
                photoMap.put(TimeUtils.getDateAdvanced(photoList.get(0).getTime()), photoList);

            //setValue()只能在主线程中调用，postValue()可以在任何线程中调用
            photoLiveData.postValue(photoMap);
            cursor.close();
        }).start();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // 释放资源
    }

}
