package com.anew.update;

import com.example.commonlibrary.manager.VideoController;

import java.util.List;
import java.util.Map;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/23     11:07
 */
public class VideoBean {
    private String title;
    private String imageCover;
    private String url;
    private long totalLength;
    private Map<String, String> headers;


    public VideoBean(String title, long totalLength, String imageCover, String url) {
        this.title = title;
        this.imageCover = imageCover;
        this.url = url;
        this.totalLength = totalLength;
    }

    public long getTotalLength() {
        return totalLength;
    }


    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }


    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    private List<VideoController.Clarity> mClarities;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<VideoController.Clarity> getClarities() {
        return mClarities;
    }

    public void setClarities(List<VideoController.Clarity> clarities) {
        mClarities = clarities;
    }
}
