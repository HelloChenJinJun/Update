package com.anew.update.dagger;

import com.anew.update.dagger.main.MainComponent;
import com.anew.update.mvp.video.VideoListFragment;
import com.example.commonlibrary.dagger.scope.PerFragment;

import dagger.Component;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/27     15:56
 */
@Component(dependencies = MainComponent.class, modules = VideoFragmentModule.class)
@PerFragment
public interface VideoFragmentComponent {
    public void inject(VideoListFragment videoFragment);
}
