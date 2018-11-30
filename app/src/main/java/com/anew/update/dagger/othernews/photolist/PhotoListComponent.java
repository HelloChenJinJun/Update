package com.anew.update.dagger.othernews.photolist;

import com.anew.update.dagger.main.MainComponent;
import com.anew.update.mvp.othernew.photolist.PhotoListFragment;
import com.example.commonlibrary.dagger.scope.PerFragment;

import dagger.Component;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/29      17:48
 * QQ:             1981367757
 */
@PerFragment
@Component(dependencies = MainComponent.class,modules = PhotoListModule.class)
public interface PhotoListComponent {
    public void inject(PhotoListFragment photoListFragment);
}
