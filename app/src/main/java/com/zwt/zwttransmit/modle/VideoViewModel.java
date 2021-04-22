package com.zwt.zwttransmit.modle;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zwt.zwttransmit.MyApplication;
import com.zwt.zwttransmit.utils.TimeUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class VideoViewModel extends ViewModel {
    Map<String, ArrayList<Video>> videoMap = new HashMap<>();

    private MutableLiveData<Map<String, ArrayList<Video>>> videoLiveData;

    String[] STORE_IMAGES = {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DURATION,//时长
            MediaStore.Video.Media.DATE_ADDED,//放入数据库日期
            MediaStore.Video.Media.MIME_TYPE //视屏格式类型
    };

    public MutableLiveData<Map<String, ArrayList<Video>>> getMusicLiveData(){
        if (videoLiveData == null){
            videoLiveData = new MutableLiveData<Map<String, ArrayList<Video>>>();
            loadVideo();
        }
        return videoLiveData;
    }

    private void loadVideo(){
        new Thread(() -> {
            @SuppressLint("Recycle")
            Cursor cursor = MyApplication.getContext().getContentResolver().query(
                    MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                    STORE_IMAGES,
                    null,
                    null,
                    MediaStore.Video.Media.DATE_ADDED+" desc"
            );
            int idIndex = cursor.getColumnIndex(MediaStore.Video.Media._ID);
            int nameIndex = cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME);
            int pathIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATA);
            int sizeIndex = cursor.getColumnIndex(MediaStore.Video.Media.SIZE);
            int durationIndex = cursor.getColumnIndex(MediaStore.Video.Media.DURATION);
            int timeIndex = cursor.getColumnIndex(MediaStore.Video.Media.DATE_ADDED);
            int mimeTypeIndex = cursor.getColumnIndex(MediaStore.Video.Media.MIME_TYPE);

            long lastDate = 0;
            ArrayList<Video> videoList = new ArrayList<>();
            while (cursor.moveToNext()){
                if (lastDate == 0)
                    lastDate = cursor.getLong(timeIndex);
                int id = cursor.getInt(idIndex);
                String name = cursor.getString(nameIndex);
                String path = cursor.getString(pathIndex);
                long size = cursor.getLong(sizeIndex);
                int duration = cursor.getInt(durationIndex);
                long time = cursor.getLong(timeIndex);
                String mimeType = cursor.getString(mimeTypeIndex);
                // 计算大小
                String sizeStr = String.valueOf(size / 1024f / 1024f).substring(0, 4) + "MB";

                String thumbnailData = getThumbnailData(id);
                if (!TimeUtils.getDate(lastDate).equals(TimeUtils.getDate(time))){
                    lastDate = time;
                    videoMap.put(TimeUtils.getDateAdvanced(videoList.get(0).getTime()), videoList);
                    videoList = new ArrayList<>();
                }
                Video video = new Video(name, path, sizeStr, duration, time, mimeType, thumbnailData);
                videoList.add(video);

            }
            if (lastDate != 0)
                videoMap.put(TimeUtils.getDateAdvanced(videoList.get(0).getTime()), videoList);

            videoLiveData.postValue(videoMap);
            cursor.close();
        }).start();
    }

    //获得视频第一帧图片
    private String getThumbnailData(int id){
        String[] projection = new String[]{
                MediaStore.Video.Thumbnails.DATA,
                MediaStore.Video.Thumbnails.VIDEO_ID
        };
        Cursor cur = MyApplication.getContext().getContentResolver().query(
                MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Video.Thumbnails.VIDEO_ID +"=?",
                new String[]{ String.valueOf(id) },
                null
        );

        String thumbnailData = "";
        if (cur != null && cur.moveToFirst()) {
            thumbnailData = cur.getString(cur.getColumnIndex(MediaStore.Video.Thumbnails.DATA));
        }
        cur.close();
        return thumbnailData;
    }
}
