package com.anew.update.dagger.othernews.photo;

import com.anew.update.adapter.OtherNewPhotoSetAdapter;
import com.anew.update.mvp.othernew.photo.OtherNewPhotoSetActivity;
import com.anew.update.mvp.othernew.photo.OtherNewPhotoSetPresenter;
import com.example.commonlibrary.mvp.model.DefaultModel;

import dagger.Module;
import dagger.Provides;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/26      17:04
 * QQ:             1981367757
 */
@Module
public class OtherNewPhotoSetModule {
    private OtherNewPhotoSetActivity otherNewPhotoSetActivity;

    public OtherNewPhotoSetModule(OtherNewPhotoSetActivity otherNewPhotoSetActivity) {
        this.otherNewPhotoSetActivity = otherNewPhotoSetActivity;
    }


    @Provides
    public OtherNewPhotoSetAdapter provideOtherNewPhotoSetAdater(){
        return new OtherNewPhotoSetAdapter();
    }


    @Provides
    public OtherNewPhotoSetPresenter providePhotoSetPresenter(DefaultModel otherNewPhotoSetModel){
        return new OtherNewPhotoSetPresenter(otherNewPhotoSetActivity,otherNewPhotoSetModel);
    }


}
