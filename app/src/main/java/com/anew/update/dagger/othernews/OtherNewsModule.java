package com.anew.update.dagger.othernews;

import com.anew.update.mvp.othernew.OtherNewsListAdapter;
import com.anew.update.mvp.othernew.OtherNewsListFragment;
import com.anew.update.mvp.othernew.OtherNewsListPresenter;
import com.example.commonlibrary.mvp.model.DefaultModel;

import dagger.Module;
import dagger.Provides;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/24      18:33
 * QQ:             1981367757
 */
@Module
public class OtherNewsModule {
    private OtherNewsListFragment otherNewsListFragment;


    public OtherNewsModule(OtherNewsListFragment otherNewsListFragment) {
        this.otherNewsListFragment = otherNewsListFragment;
    }


    @Provides
    public OtherNewsListAdapter provideOtherNewsListAdapter() {
        return new OtherNewsListAdapter();
    }


    @Provides
    public OtherNewsListPresenter provideOtherNewsListPresenter(DefaultModel otherNewsListModel) {
        return new OtherNewsListPresenter(otherNewsListFragment, otherNewsListModel);
    }


}
