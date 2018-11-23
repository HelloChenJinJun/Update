package com.anew.update;

import com.example.commonlibrary.baseadapter.adapter.BaseRecyclerAdapter;
import com.example.commonlibrary.baseadapter.viewholder.BaseWrappedViewHolder;
import com.example.commonlibrary.manager.DefaultVideoController;
import com.example.commonlibrary.manager.DefaultVideoPlayer;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 项目名称:    Update
 * 创建人:      陈锦军
 * 创建时间:    2018/11/23     11:06
 */
public class VideoAdapter extends BaseRecyclerAdapter<VideoBean, BaseWrappedViewHolder> {
    @Override
    protected int getLayoutId() {
        return R.layout.item_activity_video_list;
    }

    @Override
    protected void convert(BaseWrappedViewHolder holder, VideoBean data) {
        DefaultVideoPlayer defaultVideoPlayer = (DefaultVideoPlayer) holder.getView(R.id.dvp_item_activity_video_list_display);
        defaultVideoPlayer.setTitle(data.getTitle()).setClarity(data.getClarities())
                .setTotalLength(data.getTotalLength())
                .setImageCover(data.getImageCover())
                .setUp(data.getUrl(), data.getHeaders());
    }


    @Override
    public void onViewDetachedFromWindow(@NonNull BaseWrappedViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        ((DefaultVideoPlayer) holder.getView(R.id.dvp_item_activity_video_list_display)).release();
    }


}