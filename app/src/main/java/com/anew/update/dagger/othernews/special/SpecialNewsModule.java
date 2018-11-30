package com.anew.update.dagger.othernews.special;

import com.anew.update.adapter.SpecialNewsAdapter;
import com.anew.update.bean.SpecialNewsBean;
import com.anew.update.mvp.othernew.special.ISpecialNewsView;
import com.anew.update.mvp.othernew.special.SpecialNewsPresenter;
import com.example.commonlibrary.mvp.model.DefaultModel;

import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/25      20:22
 * QQ:             1981367757
 */
@Module
public class SpecialNewsModule {
    private ISpecialNewsView<List<SpecialNewsBean>> iSpecialNewsView;

    public SpecialNewsModule(ISpecialNewsView<List<SpecialNewsBean>> iSpecialNewsView) {
        this.iSpecialNewsView = iSpecialNewsView;
    }

    @Provides
    public SpecialNewsAdapter provideSpecialNewsAdapter(){
        return new SpecialNewsAdapter();
    }



    @Provides
    public SpecialNewsPresenter provideSpecialNewsPresenter(DefaultModel specialNewsModel){
        return new SpecialNewsPresenter(iSpecialNewsView,specialNewsModel);
    }



}
