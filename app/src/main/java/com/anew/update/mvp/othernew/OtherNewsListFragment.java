package com.anew.update.mvp.othernew;

import android.os.Bundle;
import android.view.View;

import com.anew.update.R;
import com.anew.update.base.MainBaseFragment;
import com.anew.update.bean.NewInfoBean;
import com.anew.update.dagger.othernews.DaggerOtherNewsComponent;
import com.anew.update.dagger.othernews.OtherNewsModule;
import com.anew.update.mvp.othernew.detail.OtherNewsDetailActivity;
import com.anew.update.mvp.othernew.photo.OtherNewPhotoSetActivity;
import com.anew.update.mvp.othernew.special.SpecialNewsActivity;
import com.anew.update.util.NewsUtil;
import com.example.commonlibrary.BaseFragment;
import com.example.commonlibrary.baseadapter.SuperRecyclerView;
import com.example.commonlibrary.baseadapter.empty.EmptyLayout;
import com.example.commonlibrary.baseadapter.foot.LoadMoreFooterView;
import com.example.commonlibrary.baseadapter.foot.OnLoadMoreListener;
import com.example.commonlibrary.baseadapter.listener.OnSimpleItemClickListener;
import com.example.commonlibrary.baseadapter.manager.WrappedLinearLayoutManager;
import com.example.commonlibrary.bean.news.OtherNewsTypeBean;
import com.example.commonlibrary.cusotomview.ListViewDecoration;

import java.util.List;

import javax.inject.Inject;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/24      17:02
 * QQ:             1981367757
 */

public class OtherNewsListFragment extends MainBaseFragment<List<NewInfoBean>,OtherNewsListPresenter> implements OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    OtherNewsListAdapter otherNewsListAdapter;
    private SwipeRefreshLayout refresh;
    private SuperRecyclerView display;
    private OtherNewsTypeBean otherNewsTypeBean;



    @Override
    public void updateData(List<NewInfoBean> newInfoBeen) {
        if (refresh.isRefreshing()) {
            otherNewsListAdapter.refreshData(newInfoBeen);
        }else {
            otherNewsListAdapter.addData(newInfoBeen);
        }
    }

    @Override
    protected boolean isNeedHeadLayout() {
        return false;
    }

    @Override
    protected boolean isNeedEmptyLayout() {
        return true;
    }

    @Override
    protected boolean needStatusPadding() {
        return false;
    }

    @Override
    protected int getContentLayout() {
        return R.layout.fragment_other_news_list;
    }

    @Override
    protected void initView() {
        refresh= (SwipeRefreshLayout) findViewById(R.id.refresh_fragment_other_news_list_refresh);
        display= (SuperRecyclerView) findViewById(R.id.srcv_fragment_other_news_list_display);
    }

    @Override
    protected void initData() {
        otherNewsTypeBean=getArguments().getParcelable("item");
        DaggerOtherNewsComponent.builder()
                .otherNewsModule(new OtherNewsModule(this))
                .mainComponent(getComponent())
                .build().inject(this);
        refresh.setOnRefreshListener(this);
        display.setLayoutManager(new WrappedLinearLayoutManager(getContext()));
        display.addItemDecoration(new ListViewDecoration());
        display.setLoadMoreFooterView(new LoadMoreFooterView(getContext()));
        display.setOnLoadMoreListener(this);
        display.setAdapter(otherNewsListAdapter);
        otherNewsListAdapter.setOnItemClickListener(new OnSimpleItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                NewInfoBean newInfoBean=otherNewsListAdapter.getData(position);
                if (NewsUtil.SPECIAL_TITLE.equals(newInfoBean.getSkipType())) {
                    SpecialNewsActivity.start(getContext(),newInfoBean.getSpecialID(),newInfoBean.getTitle());
                }else if (NewsUtil.PHOTO_SET.equals(newInfoBean.getSkipType())){
                    OtherNewPhotoSetActivity.start(getContext(),newInfoBean.getPhotosetID());
                }else {
                    OtherNewsDetailActivity.start(getContext(),newInfoBean.getPostid());
                }
            }
        });
    }

    @Override
    protected void updateView() {
        refresh.setRefreshing(true);
        presenter.getOtherNewsListData(true,true,otherNewsTypeBean.getTypeId());
    }



    public static OtherNewsListFragment newInstance(OtherNewsTypeBean otherNewsTypeBean){
        Bundle bundle=new Bundle();
        bundle.putParcelable("item",otherNewsTypeBean);
        OtherNewsListFragment otherNewsListFragment=new OtherNewsListFragment();
        otherNewsListFragment.setArguments(bundle);
        return otherNewsListFragment;
    }

    @Override
    public void loadMore() {
        presenter.getOtherNewsListData(false,false,otherNewsTypeBean.getTypeId());
    }

    @Override
    public void onRefresh() {
        presenter.getOtherNewsListData(false,true,otherNewsTypeBean.getTypeId());
    }


    @Override
    public void showLoading(String loadingMsg) {
        refresh.setRefreshing(true);
    }


    @Override
    public void hideLoading() {
        super.hideLoading();
        refresh.setRefreshing(false);
    }


    @Override
    public void showError(String errorMsg, EmptyLayout.OnRetryListener listener) {
        if (refresh.isRefreshing()) {
            super.showError(errorMsg, listener);
            refresh.setRefreshing(false);
        }else {
            ((LoadMoreFooterView) display.getLoadMoreFooterView()).setStatus(LoadMoreFooterView.Status.ERROR);
        }
    }
}
