package com.example.commonlibrary.manager.music;

import java.security.PrivilegedAction;
import java.util.List;
import java.util.Random;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/12/4     11:42
 */
public class PlayData {

    public static final int PLAY_MODE_RANDOM = 1;
    public static final int PLAY_MODE_ORDER = 2;
    public static final int PLAY_MODE_RECYCLER = 3;
    private List<String> urlList;
    private int position;
    private int playMode;
    private boolean needRecycler = false;

    public void setData(List<String> urlList, int position) {
        this.urlList = urlList;
        this.position = position;
    }

    public String getCurrentUrl() {
        if (urlList != null && position > 0 && position < urlList.size()) {
            return urlList.get(position);
        }
        return null;
    }

    public String next() {
        if (urlList == null || urlList.size() == 0) {
            return null;
        }
        switch (playMode) {
            case PLAY_MODE_RANDOM:
                Random random = new Random();
                position = random.nextInt(urlList.size());
                return urlList.get(position);
            case PLAY_MODE_ORDER:
                int newPosition = position + 1;
                if (newPosition == urlList.size()) {
                    if (needRecycler) {
                        position = (position + 1) % urlList.size();
                        return urlList.get(position);
                    } else {
                        return null;
                    }
                }
                return urlList.get(position);
            case PLAY_MODE_RECYCLER:
                return urlList.get(position);
            default:
                return null;
        }
    }
}
