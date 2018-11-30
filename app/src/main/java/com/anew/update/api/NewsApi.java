package com.anew.update.api;

import com.anew.update.bean.NewInfoBean;
import com.anew.update.bean.OtherNewsDetailBean;
import com.anew.update.bean.PhotoSetBean;
import com.anew.update.bean.PictureBean;
import com.anew.update.bean.RawSpecialNewsBean;
import com.anew.update.util.NewsUtil;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Url;

/**
 * 项目名称:    NewFastFrame
 * 创建人:        陈锦军
 * 创建时间:    2017/9/24      16:56
 * QQ:             1981367757
 */

public interface NewsApi {


    @Headers({NewsUtil.HEADER_AGENT
            , NewsUtil.CACHE_CONTROL})
    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewInfoBean>>> getNewsList(@Path("type") String type, @Path("id") String id,
                                                           @Path("startPage") int startPage);

    @Headers({NewsUtil.HEADER_AGENT
            , NewsUtil.CACHE_CONTROL})
    @GET("nc/special/{specialId}.html")
    Observable<Map<String, RawSpecialNewsBean>> getSpecialNewsData(@Path("specialId") String specialIde);

    @Headers({NewsUtil.HEADER_AGENT
            , NewsUtil.CACHE_CONTROL})
    @GET("nc/article/{newsId}/full.html")
    Observable<Map<String, OtherNewsDetailBean>> getNewsDetail(@Path("newsId") String newsId);

    @Headers({NewsUtil.HEADER_AGENT
            , NewsUtil.CACHE_CONTROL})
    @GET("photo/api/set/{photoId}.json")
    Observable<PhotoSetBean> getPhotoSetData(@Path("photoId") String photoId);

    @GET
    Observable<PictureBean> getPhotoListData(@Url String url);

}


