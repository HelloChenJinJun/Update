package com.example.chat.mvp.account.password;


import com.example.commonlibrary.mvp.model.DefaultModel;
import com.example.commonlibrary.mvp.presenter.RxBasePresenter;
import com.example.commonlibrary.mvp.view.IView;


/**
 * 项目名称:    NewFastFrame
 * 创建人:      陈锦军
 * 创建时间:    2018/1/13     23:46
 * QQ:         1981367757
 */

public class PasswordChangePresenter extends RxBasePresenter<IView<Object>, DefaultModel> {
    public PasswordChangePresenter(final IView<Object> iView, DefaultModel baseModel) {
        super(iView, baseModel);

    }


    public void resetPassword(String old, String news) {

    }
}
