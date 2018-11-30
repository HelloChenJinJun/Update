package com.anew.update.dagger.othernews;

import com.anew.update.dagger.main.MainComponent;
import com.anew.update.mvp.othernew.OtherNewsListFragment;
import com.example.commonlibrary.dagger.scope.PerFragment;

import dagger.Component;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/24      18:33
 * QQ:             1981367757
 */
@PerFragment
@Component(dependencies = MainComponent.class,modules = OtherNewsModule.class)
public interface OtherNewsComponent {
    public void inject(OtherNewsListFragment otherNewsListFragment);
}
