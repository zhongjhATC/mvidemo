package com.zhongjh.mvidemo.data.http.service;


import com.zhongjh.mvidemo.entity.Banner;
import com.zhongjh.mvidemo.entity.WanEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * @author zhongjh
 * @date 2022/3/30
 * 面板api
 */
public interface BannerApi {

    @GET("banner/json")
    Observable<WanEntity<List<Banner>>> json();



}
