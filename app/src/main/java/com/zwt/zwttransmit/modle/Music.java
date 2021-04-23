package com.zwt.zwttransmit.modle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Music {
    // 歌曲名
    private String name;
    // 路径
    private String path;
    // 专辑
    private String album;
    //专辑图片地址
    private String albumArt;
    // 作者
    private String artist;
    // 大小
    private String size;
    // 时长
    private int duration;
    //日期
    private long time;
    //首字母
    private String pinyin;
    // 音乐文件类型
    private String contentType;

    public Music(){}

    public Music(String name, String path, String album, String albumArt, String artist, String size, int duration, long time, String pinyin, String contentType) {
        this.name = name;
        this.path = path;
        this.album = album;
        this.albumArt = albumArt;
        this.artist = artist;
        this.size = size;
        this.duration = duration;
        this.time = time;
        this.pinyin = pinyin;
        this.contentType = contentType;
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
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

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
