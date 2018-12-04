package com.example.commonlibrary.manager.music;

import android.media.MediaPlayer;

import com.example.commonlibrary.rxbus.RxBusManager;
import com.example.commonlibrary.rxbus.event.PlayStateEvent;

import java.io.IOException;
import java.util.List;


/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/12/4     11:24
 */
public class MusicPlayerManager implements IMusicPlayer, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnErrorListener {
    private MediaPlayer mMediaPlayer;
    private String url;
    private PlayData mPlayData;

    //    播放未开始
    public static final int PLAY_STATE_IDLE = 0;
    //  资源准备中
    public static final int PLAY_STATE_PREPARING = 1;
    //    资源准备就绪
    public static final int PLAY_STATE_PREPARED = 2;
    //    播放中
    public static final int PLAY_STATE_PLAYING = 3;
    //    暂停中
    public static final int PLAY_STATE_PAUSE = 4;
    //    播放完成
    public static final int PLAY_STATE_FINISH = 5;
    public static final int PLAY_STATE_ERROR = 6;
    private int mState;
    private int bufferedPercent;


    private static MusicPlayerManager instance;

    public static MusicPlayerManager getInstance() {
        if (instance == null) {
            instance = new MusicPlayerManager();
        }
        return instance;
    }


    private MusicPlayerManager() {
        initData();
    }

    private void initData() {
        mMediaPlayer = new MediaPlayer();
        mPlayData = new PlayData();
        mMediaPlayer.setOnPreparedListener(this);
        mMediaPlayer.setOnErrorListener(this);
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnBufferingUpdateListener(this);
    }

    @Override
    public void play(String url) {
        if (url != null) {
            this.url = url;
            play();
        }
    }

    @Override
    public void play(List<String> urlList, int position) {
        mPlayData.setData(urlList, position);
        play(mPlayData.getCurrentUrl());
    }

    @Override
    public void play() {
        if (mState == PLAY_STATE_PAUSE) {
            mMediaPlayer.start();
            return;
        }
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(url);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        mMediaPlayer.pause();
        mState = PLAY_STATE_PAUSE;
        RxBusManager.getInstance().post(new PlayStateEvent(mState));
    }

    @Override
    public void seekTo(int position) {
        mMediaPlayer.seekTo(position);
    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public int getPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public int getBufferedPercentage() {
        return bufferedPercent;
    }


    @Override
    public int getCurrentState() {
        return mState;
    }


    @Override
    public void reset() {
        mMediaPlayer.reset();
    }

    @Override
    public void release() {
        mMediaPlayer.release();
        mMediaPlayer = null;
    }


    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
        mState = PLAY_STATE_PLAYING;
        RxBusManager.getInstance().post(new PlayStateEvent(mState));
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        String nextPath = mPlayData.next();
        if (nextPath == null) {
            mMediaPlayer.reset();
        } else {
            play(nextPath);
        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        bufferedPercent = percent;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mState = PLAY_STATE_ERROR;
        RxBusManager.getInstance().post(new PlayStateEvent(mState));
        return false;
    }
}
