package com.anew.update;

import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.commonlibrary.SlideBaseActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/27     9:04
 */
public class WheelActivity extends SlideBaseActivity implements View.OnClickListener {
    private BitWheelView mBitWheelView;
    private ImageView play;

    @Override
    protected boolean isNeedHeadLayout() {
        return false;
    }

    @Override
    protected boolean isNeedEmptyLayout() {
        return false;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_wheel;
    }

    @Override
    protected void initView() {
        mBitWheelView = findViewById(R.id.bv_activity_wheel_display);
        play = findViewById(R.id.iv_activity_wheel_play);


    }


    @Override
    protected void initData() {
        play.setOnClickListener(this);
    }

    @Override
    public void updateData(Object o) {

    }

    @Override
    public void onClick(View v) {
        mBitWheelView.luckyStart(2);
        addDisposable(Observable.timer(3, TimeUnit.SECONDS).subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> mBitWheelView.luckyEnd()));
    }

    private class JsToJava {
        @JavascriptInterface //sdk17版本以上加上注解
        public void onDataGet(String r, String s) {

        }
    }
}
