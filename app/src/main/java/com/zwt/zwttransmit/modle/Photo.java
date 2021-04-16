package com.zwt.zwttransmit.modle;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo {
    private long time;
    private String thumbPath;

    private String filePath;
    private String fileName;


    public String getDate() {
        return new SimpleDateFormat("yyyy年MM月dd日")
                .format(new Date(time*1000L));
    }


    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
