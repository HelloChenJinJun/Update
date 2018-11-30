package com.anew.update.dagger;

import com.anew.update.adapter.VideoAdapter;
import com.anew.update.mvp.video.VideoListFragment;
import com.anew.update.mvp.video.VideoPresenter;
import com.example.commonlibrary.mvp.model.DefaultModel;

import dagger.Module;
import dagger.Provides;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/27     15:56
 */
@Module
public class VideoFragmentModule {
    private VideoListFragment mVideoFragment;

    public VideoFragmentModule(VideoListFragment videoFragment) {
        mVideoFragment = videoFragment;
    }

    @Provides
    public VideoAdapter provideAdapter() {
        return new VideoAdapter();
    }

    @Provides
    public VideoPresenter providePresenter(DefaultModel defaultModel) {
        return new VideoPresenter(mVideoFragment, defaultModel);
    }
}
