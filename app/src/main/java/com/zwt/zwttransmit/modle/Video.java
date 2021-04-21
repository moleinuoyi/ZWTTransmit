package com.zwt.zwttransmit.modle;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Video {
    // 视屏名称
    private String name;
    // 路径
    private String path;
    // 大小
    private String size;
    // 时长
    private int duration;
    //日期
    private long time;
    // 视屏文件类型
    private String mimeType;
    // 视屏缩略图
    private String thumbnailData;

    public String getDate() {
        return new SimpleDateFormat("yyyy年MM月dd日", Locale.getDefault())
                .format(new Date(time*1000L));
    }
    public String getDateMinute() {
        return new SimpleDateFormat("yyyy/MM/dd a hh:mm:ss", Locale.getDefault())
                .format(new Date(time*1000L));
    }

    public Video(){}

    public Video(String name, String path, String size, int duration, long time, String mimeType, String thumbnailData) {
        this.name = name;
        this.path = path;
        this.size = size;
        this.duration = duration;
        this.time = time;
        this.mimeType = mimeType;
        this.thumbnailData = thumbnailData;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getThumbnailData() {
        return thumbnailData;
    }

    public void setThumbnailData(String thumbnailData) {
        this.thumbnailData = thumbnailData;
    }
}
