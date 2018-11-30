package com.anew.update.dagger.othernews.photolist;

import com.anew.update.adapter.PhotoListAdapter;
import com.anew.update.mvp.othernew.photolist.PhotoListFragment;
import com.anew.update.mvp.othernew.photolist.PhotoListPresenter;
import com.example.commonlibrary.mvp.model.DefaultModel;

import dagger.Module;
import dagger.Provides;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/29      17:49
 * QQ:             1981367757
 */
@Module
public class PhotoListModule {

    private PhotoListFragment photoListFragment;

    public PhotoListModule(PhotoListFragment photoListFragment) {
        this.photoListFragment = photoListFragment;
    }


    @Provides
    public PhotoListAdapter providePhotoListAdapter() {
        return new PhotoListAdapter();
    }


    @Provides
    public PhotoListPresenter providePhotoListPresenter(DefaultModel photoListModel) {
        return new PhotoListPresenter(photoListFragment, photoListModel);
    }

}
