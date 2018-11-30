package com.anew.update.dagger.othernews.detail;

import com.anew.update.dagger.main.MainComponent;
import com.anew.update.mvp.othernew.detail.OtherNewsDetailActivity;
import com.example.commonlibrary.dagger.scope.PerActivity;

import dagger.Component;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/26      16:07
 * QQ:             1981367757
 */
@PerActivity
@Component(dependencies = MainComponent.class,modules = OtherNewsDetailModule.class)
public interface OtherNewsDetailComponent {
    public void inject(OtherNewsDetailActivity otherNewsDetailActivity);
}
