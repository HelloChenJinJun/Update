package com.example.commonlibrary.manager.video;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.commonlibrary.BaseApplication;
import com.example.commonlibrary.manager.IVideoPlayer;
import com.example.commonlibrary.utils.AppUtil;
import com.example.commonlibrary.utils.CommonLogger;
import com.example.commonlibrary.utils.DensityUtil;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/22     11:04
 */
public class DefaultVideoPlayer extends FrameLayout implements IVideoPlayer, TextureView.SurfaceTextureListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnInfoListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnVideoSizeChangedListener {

    MediaPlayer mMediaPlayer;

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
    //    缓存播放中
    public static final int PLAY_STATE_BUFFERING_PLAYING = 5;
    //    缓存暂停中
    public static final int PLAY_STATE_BUFFERING_PAUSE = 6;
    //    播放完成
    public static final int PLAY_STATE_FINISH = 7;

    public static final int PLAY_STATE_ERROR = 8;


    //全屏模式
    public static final int WINDOW_STATE_FULL = 0;
    //    列表模式
    public static final int WINDOW_STATE_LIST = 1;

    //    小窗口tiny模式
    public static final int WINDOW_STATE_TINY = 2;
    private FrameLayout container;


    private VideoController mVideoController;
    private AudioManager audioManager;
    private DefaultTextureView defaultTextureView;
    private SurfaceTexture mSurfaceTexture;
    private int mState = PLAY_STATE_IDLE;
    private boolean needContinueLastPlay = true;
    private String url;
    private Map<String, String> headers;
    private int mBufferedPercent;
    private int mWindowState = -1;


    public DefaultVideoPlayer(@NonNull Context context) {
        this(context, null);
    }

    public DefaultVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DefaultVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        container = new FrameLayout(getContext());
        container.setBackgroundColor(Color.BLACK);
        setVideoController(new DefaultVideoController(getContext()));
        setWindowState(WINDOW_STATE_LIST);
    }


    public void setVideoController(VideoController videoController) {
        if (videoController != null && !videoController.equals(mVideoController)) {
            container.removeView(mVideoController);
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(videoController, layoutParams);
            videoController.setIVideoPlayer(this);
            videoController.reset();
            mVideoController = videoController;
        }
    }

    @Override
    public IVideoPlayer setUp(String url, Map<String, String> headers) {
        this.url = url;
        this.headers = headers;
        return this;
    }

    @Override
    public void start() {
        ListVideoManager.getInstance().setCurrentPlayer(this);
        if (((DefaultVideoController) mVideoController).getOnItemClickListener() != null
                && !((DefaultVideoController) mVideoController).getOnItemClickListener().onStartClick(null, url)) {
            return;
        }
        if (getCurrentState() == PLAY_STATE_IDLE) {
            initMediaPlayer();
            initAudioManager();
            initTextureView();
        } else if (getCurrentState() == PLAY_STATE_PAUSE) {
            mState = PLAY_STATE_PLAYING;
            innerStart(mMediaPlayer.getCurrentPosition());
        } else if (getCurrentState() == PLAY_STATE_BUFFERING_PAUSE) {
            mState = PLAY_STATE_BUFFERING_PLAYING;
            innerStart(mMediaPlayer.getCurrentPosition());
        } else if (getCurrentState() == PLAY_STATE_ERROR || getCurrentState() == PLAY_STATE_FINISH) {
            reset();
            prepareAsync();
        } else if (getCurrentState() == PLAY_STATE_PLAYING || getCurrentState() == PLAY_STATE_BUFFERING_PLAYING) {

        }
    }


    private void initTextureView() {
        if (defaultTextureView != null) {
            container.removeView(defaultTextureView);
        } else {
            defaultTextureView = new DefaultTextureView(getContext());
        }
        defaultTextureView.setSurfaceTextureListener(this);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        container.addView(defaultTextureView, 0, layoutParams);
    }

    private void initAudioManager() {
        if (audioManager == null) {
            audioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                audioManager.requestAudioFocus(null, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
            }
        }
    }

    private void initMediaPlayer() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.setOnInfoListener(this);
            mMediaPlayer.setOnCompletionListener(this);
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setOnBufferingUpdateListener(this);
            mMediaPlayer.setOnVideoSizeChangedListener(this);
        }
    }


    @Override
    public void pause() {
        if (mState == PLAY_STATE_PLAYING) {
            mState = PLAY_STATE_PAUSE;
            innerPause();
        } else if (mState == PLAY_STATE_BUFFERING_PLAYING) {
            mState = PLAY_STATE_BUFFERING_PAUSE;
            innerPause();
        }
    }

    private void innerPause() {
        mMediaPlayer.pause();
        mVideoController.onPlayStateChanged(mState);
    }

    @Override
    public void seekTo(int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

    @Override
    public IVideoPlayer setVolume(int volume) {
        if (audioManager != null) {
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        }
        return this;
    }

    @Override
    public int getMaxVolume() {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public int getVolume() {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public int getDuration() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    @Override
    public int getPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public int getBufferedPercentage() {
        return mBufferedPercent;
    }

    @Override
    public IVideoPlayer setTitle(String title) {
        mVideoController.setTitle(title);
        return this;
    }

    @Override
    public IVideoPlayer setTotalLength(long length) {
        mVideoController.setTotalLength(length);
        return this;
    }

    @Override
    public int getCurrentState() {
        return mState;
    }

    @Override
    public int getWindowState() {
        return mWindowState;
    }

    @Override
    public IVideoPlayer setWindowState(int state) {
        if (state != mWindowState) {
            FrameLayout contentView = ((AppCompatActivity) getContext()).findViewById(android.R.id.content);
            if (contentView.indexOfChild(container) >= 0) {
                contentView.removeView(container);
            }
            if (indexOfChild(container) >= 0) {
                removeView(container);
            }
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mWindowState = state;
            if (mWindowState == WINDOW_STATE_FULL) {
                AppCompatActivity appCompatActivity = ((AppCompatActivity) getContext());
                ActionBar actionBar = appCompatActivity.getSupportActionBar();
                if (actionBar != null) {
                    actionBar.hide();
                }
                appCompatActivity.getWindow()
                        .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                WindowManager.LayoutParams.FLAG_FULLSCREEN);
                appCompatActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                contentView.addView(container, layoutParams);
            } else if (mWindowState == WINDOW_STATE_LIST) {
                AppCompatActivity appCompatActivity = ((AppCompatActivity) getContext());
                appCompatActivity.getWindow()
                        .clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                ActionBar ab = appCompatActivity.getSupportActionBar();
                if (ab != null) {
                    ab.show();
                }
                appCompatActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                addView(container, layoutParams);
            } else if (mWindowState == WINDOW_STATE_TINY) {
                layoutParams.width = (int) (DensityUtil.getScreenWidth(getContext()) * 0.6f);
                layoutParams.height = (int) (DensityUtil.getScreenWidth(getContext()) * 0.6f * 9 / 16);
                layoutParams.bottomMargin = DensityUtil.toDp(7);
                layoutParams.rightMargin = DensityUtil.toDp(7);
                layoutParams.gravity = Gravity.BOTTOM | Gravity.END;
                contentView.addView(container, layoutParams);
            }
            mVideoController.onWindowStateChanged(mWindowState);
        }
        return this;
    }

    @Override
    public void reset() {
        if (mMediaPlayer != null) {
            BaseApplication.getAppComponent()
                    .getSharedPreferences().edit().putLong(url, (mState == PLAY_STATE_FINISH || mState == PLAY_STATE_ERROR) ? 0 : mMediaPlayer.getCurrentPosition()).apply();
            mMediaPlayer.reset();
        }
        mState = PLAY_STATE_IDLE;
        mVideoController.onPlayStateChanged(mState);
    }

    @Override
    public void release() {
        reset();
        if (mSurfaceTexture != null) {
            mSurfaceTexture.release();
            mSurfaceTexture = null;
        }
        container.removeView(defaultTextureView);
    }

    @Override
    public IVideoPlayer setClarity(List<VideoController.Clarity> urlList) {
        mVideoController.setClarity(urlList);
        return this;
    }

    @Override
    public IVideoPlayer setImageCover(String imageUrl) {
        mVideoController.setImageCover(imageUrl);
        return this;
    }

    @Override
    public VideoController getController() {
        return mVideoController;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int width, int height) {
        //        防止屏幕切换时，重新创建textureView
        if (mSurfaceTexture == null) {
            mSurfaceTexture = surfaceTexture;
            mMediaPlayer.setSurface(new Surface(mSurfaceTexture));
            prepareAsync();
        } else {
            defaultTextureView.setSurfaceTexture(mSurfaceTexture);

        }
    }

    private void prepareAsync() {
        container.setKeepScreenOn(true);
        try {
            mState = PLAY_STATE_PREPARING;
            mVideoController.onPlayStateChanged(mState);
            if (AppUtil.isNetworkAvailable()) {
                mMediaPlayer.setDataSource(getContext(), Uri.parse(url), headers);
                mMediaPlayer.prepareAsync();
            } else {
                mState = PLAY_STATE_ERROR;
                mVideoController.onPlayStateChanged(mState);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return mSurfaceTexture == null;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mState = PLAY_STATE_PREPARED;
        mVideoController.onPlayStateChanged(mState);
        long position = 0;
        if (needContinueLastPlay) {
            position = BaseApplication
                    .getAppComponent().getSharedPreferences()
                    .getLong(url, 0L);
        }
        innerStart((int) position);
    }

    private void innerStart(int position) {
        mMediaPlayer.start();
        mMediaPlayer.seekTo(position);
        mVideoController.onPlayStateChanged(mState);
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
            //            渲染开始
            mState = PLAY_STATE_PLAYING;
            mVideoController.onPlayStateChanged(mState);
        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
            //            缓存开始
            if (mState == PLAY_STATE_PLAYING) {
                mState = PLAY_STATE_BUFFERING_PLAYING;
            } else if (mState == PLAY_STATE_PAUSE) {
                mState = PLAY_STATE_BUFFERING_PAUSE;
            }
            mVideoController.onPlayStateChanged(mState);
        } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
            //            缓存结束
            if (mState == PLAY_STATE_BUFFERING_PAUSE) {
                mState = PLAY_STATE_PAUSE;
            } else if (mState == PLAY_STATE_BUFFERING_PLAYING) {
                mState = PLAY_STATE_PLAYING;
            }
            mVideoController.onPlayStateChanged(mState);
        } else {
            CommonLogger.e("播放视频出错" + what + ":::" + extra);
        }
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        container.setKeepScreenOn(false);
        mState = PLAY_STATE_FINISH;
        //        BaseApplication.getAppComponent().getSharedPreferences().edit().putBoolean(url, true).apply();
        mVideoController.onPlayStateChanged(mState);
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // 直播流播放时去调用mediaPlayer.getDuration会导致-38和-2147483648错误，忽略该错误
        mState = PLAY_STATE_ERROR;
        mVideoController.onPlayStateChanged(mState);
        CommonLogger.e("视频播放出错 ———— what：" + what + ", extra: " + extra);
        return true;
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        mBufferedPercent = percent;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        //        切换window状态时候回调
        defaultTextureView.adaptVideoSize(width, height);
    }


}
