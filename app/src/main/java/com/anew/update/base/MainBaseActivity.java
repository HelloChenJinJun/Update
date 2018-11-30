package com.anew.update.base;

import com.anew.update.dagger.main.MainComponent;
import com.example.commonlibrary.BaseFragment;
import com.example.commonlibrary.SlideBaseActivity;
import com.example.commonlibrary.mvp.presenter.BasePresenter;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/30     15:36
 */
public abstract class MainBaseActivity<T, P extends BasePresenter> extends SlideBaseActivity<T, P> {
    public MainComponent getComponent() {
        return App.getMainComponent();
    }


}
