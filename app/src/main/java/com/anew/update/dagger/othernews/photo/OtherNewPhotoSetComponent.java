package com.anew.update.dagger.othernews.photo;

import com.anew.update.dagger.main.MainComponent;
import com.anew.update.mvp.othernew.photo.OtherNewPhotoSetActivity;
import com.example.commonlibrary.dagger.scope.PerActivity;

import dagger.Component;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/26      17:04
 * QQ:             1981367757
 */
@PerActivity
@Component(dependencies = MainComponent.class, modules = OtherNewPhotoSetModule.class)
public interface OtherNewPhotoSetComponent {
    public void inject(OtherNewPhotoSetActivity otherNewPhotoSetActivity);
}
