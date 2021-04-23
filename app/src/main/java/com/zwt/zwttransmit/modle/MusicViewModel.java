package com.zwt.zwttransmit.modle;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.zwt.zwttransmit.MyApplication;
import com.zwt.zwttransmit.fragment.SideBar;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class MusicViewModel extends ViewModel {
    Map<String, ArrayList<Music>> musicMap = new LinkedHashMap<>();

    private MutableLiveData<Map<String, ArrayList<Music>>> musicLiveData;

    String[] STORE_IMAGES = {
            MediaStore.Audio.Media.TITLE,//歌曲名
            MediaStore.Audio.Media.MIME_TYPE,//歌曲类型
            MediaStore.Audio.Media.ALBUM,//专辑
            MediaStore.Audio.Media.ALBUM_ID, //专辑图片地址
            MediaStore.Audio.Media.ARTIST,//作者
            MediaStore.Audio.Media.DATA,//路径
            MediaStore.Audio.Media.DURATION,//时长
            MediaStore.Audio.Media.SIZE, // 大小
            MediaStore.Audio.Media.DATE_ADDED //放入数据库日期

    };

    public MutableLiveData<Map<String, ArrayList<Music>>> getMusicLiveData() {
        if (musicLiveData == null) {
            musicLiveData = new MutableLiveData<Map<String, ArrayList<Music>>>();
            loadMusic();
        }
        return musicLiveData;
    }

    private void loadMusic(){
        new Thread(() -> {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            initMap();
            @SuppressLint("Recycle")
            Cursor cursor = MyApplication.getContext().getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    STORE_IMAGES,
                    null,
                    null,
                    null);
            int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int albumIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
            int artistIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int dataIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int durationIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            int sizeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.SIZE);
            int timeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATE_ADDED);
            int albumIdIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
//                    int albumArtIndex = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            int mimeTypeIndex = cursor.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE);

            while (cursor.moveToNext()){
                String title = cursor.getString(titleIndex);
                String album = cursor.getString(albumIndex);
                String artist = cursor.getString(artistIndex);
                String path = cursor.getString(dataIndex);
                int duration = cursor.getInt(durationIndex);
                long size = cursor.getLong(sizeIndex);
                long time = cursor.getLong(timeIndex);
                int albumId = cursor.getInt(albumIdIndex);
                String mimeType = cursor.getString(mimeTypeIndex);

                if ("audio/mpeg".equals(mimeType.trim())) {
                    mimeType = "mp3";
                } else if ("audio/x-ms-wma".equals(mimeType.trim())) {
                    mimeType = "wma";
                }
                String albumArt = getAlbumArt(albumId);
                String pinyin = getPinyin(title);

                String sizeStr;
                // 计算大小
                if (size < 1024f){
                    sizeStr = df.format(size) + "B";
                }else if ((size/1024f) < 1024f){
                    sizeStr = df.format(size / 1024f) + "kB";
                }else if ((size/1024f/1024f) < 1024f){
                    sizeStr = df.format(size / 1024f / 1024f) + "MB";
                }else {
                    sizeStr = df.format(size / 1024f / 1024f / 1024f) + "GB";
                }

                Music music = new Music(title, path, album, albumArt, artist, sizeStr, duration, time, pinyin, mimeType);

                Objects.requireNonNull(musicMap.get(pinyin)).add(music);
            }
            musicLiveData.postValue(musicMap);
            cursor.close();
        }).start();
    }

    private void initMap(){
        for(String s: SideBar.letters){
            musicMap.put(s,new ArrayList<Music>());
        }
    }

    // 获得专辑图片
    private String getAlbumArt(int album_id) {
        String mUriAlbums = "content://media/external/audio/albums";
        String[] projection = new String[]{"album_art"};
        Cursor cur = MyApplication.getContext().getContentResolver().query(Uri.parse(mUriAlbums + "/" + Integer.toString(album_id)), projection, null, null, null);
        String album_art = "";
        if (cur.getCount() > 0 && cur.getColumnCount() > 0) {
            cur.moveToNext();
            album_art = cur.getString(0);
        }
        cur.close();
        return album_art;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        // 释放资源
    }

    private String getPinyin(String str){
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//设置为大写形式
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不用加入声调

        if (str == null || str.equals(""))
            return "#";
        try {
            char input = str.trim().charAt(0);//拿到第一个字
            if (isHanZi(input)) {// 如果是汉字
                //返回一个字符串数组是因为该汉字可能是多音字，此处只取第一个结果
                return PinyinHelper.toHanyuPinyinStringArray(input, format)[0].substring(0, 1);
            }else {
                if (isEnglish(input))
                    return String.valueOf(input).toUpperCase(Locale.ENGLISH);
                else
                    return "#";
            }

        }catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return "#";
    }
    // 判断是否是汉字
    private boolean isHanZi(char c){
        return (c >= 0x4e00) && (c <= 0x9fbb);
    }
    // 判断是否是英文
    private boolean isEnglish(char c){
        return Character.isLowerCase(c) || Character.isUpperCase(c);
    }
}
