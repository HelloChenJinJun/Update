package com.anew.update.dagger.othernews.special;

import com.anew.update.dagger.main.MainComponent;
import com.anew.update.mvp.othernew.special.SpecialNewsActivity;
import com.example.commonlibrary.dagger.scope.PerActivity;

import dagger.Component;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/25      20:21
 * QQ:             1981367757
 */
@PerActivity
@Component(dependencies = MainComponent.class, modules = SpecialNewsModule.class)
public interface SpecialNewsComponent {
    public void inject(SpecialNewsActivity specialNewsActivity);
}
