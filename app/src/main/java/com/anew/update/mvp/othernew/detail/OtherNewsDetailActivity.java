package com.anew.update.mvp.othernew.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import com.anew.update.R;
import com.anew.update.base.MainBaseActivity;
import com.anew.update.bean.OtherNewsDetailBean;
import com.anew.update.dagger.othernews.detail.DaggerOtherNewsDetailComponent;
import com.anew.update.dagger.othernews.detail.OtherNewsDetailModule;
import com.anew.update.util.DefaultRichTextLoader;
import com.anew.update.util.NewsUtil;
import com.bumptech.glide.Glide;
import com.example.commonlibrary.cusotomview.ToolBarOption;
import com.zzhoujay.richtext.ImageHolder;
import com.zzhoujay.richtext.RichText;
import com.zzhoujay.richtext.RichTextConfig;
import com.zzhoujay.richtext.callback.BitmapStream;
import com.zzhoujay.richtext.callback.ImageGetter;
import com.zzhoujay.richtext.callback.ImageLoadNotify;
import com.zzhoujay.richtext.ig.ImageDownloader;

import java.io.IOException;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/26      15:27
 * QQ:             1981367757
 */

public class OtherNewsDetailActivity extends MainBaseActivity<OtherNewsDetailBean, OtherNewsDetailPresenter> {
    private TextView content;


    @Override
    public void updateData(OtherNewsDetailBean otherNewsDetailBean) {
        if (otherNewsDetailBean != null) {
            RichText.from(otherNewsDetailBean.getBody())
                    .into(content);
        }
    }

    @Override
    protected boolean isNeedHeadLayout() {
        return true;
    }

    @Override
    protected boolean isNeedEmptyLayout() {
        return true;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.activity_other_news_detail;
    }

    @Override
    protected void initView() {
        content = findViewById(R.id.tv_activity_other_news_detail_content);
    }

    @Override
    protected void initData() {
        DaggerOtherNewsDetailComponent.builder()
                .otherNewsDetailModule(new OtherNewsDetailModule(this))
                .mainComponent(getComponent())
                .build().inject(this);
        runOnUiThread(() -> presenter.getOtherNewsDetailData(getIntent().getStringExtra(NewsUtil.POST_ID)));
        ToolBarOption toolBarOption = new ToolBarOption();
        toolBarOption.setTitle("详情");
        toolBarOption.setNeedNavigation(true);
        setToolBar(toolBarOption);
    }

    public static void start(Context context, String postid) {
        Intent intent = new Intent(context, OtherNewsDetailActivity.class);
        intent.putExtra(NewsUtil.POST_ID, postid);
        context.startActivity(intent);
    }
}
