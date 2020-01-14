package com.strive.xwanandroid.common.http

import com.strive.xwanandroid.common.bean.BannerInfo
import com.strive.xwanandroid.common.bean.BaseEntity
import com.strive.xwanandroid.common.bean.ListDataInfo
import kotlinx.coroutines.Deferred
import retrofit2.http.*

/**
 *
 * @description 后台接口
 * @date 2020-01-03
 * @author xingcc
 *
 */
interface ApiService {

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field("username") username: String, @Field("password") password: String): Deferred<BaseEntity<String>>

    @GET("/article/list/{pageNum}/json")
    fun getHomeData(@Path("pageNum") pageNum: Int): Deferred<BaseEntity<ListDataInfo>>

    @GET("/banner/json")
    fun getHomeBanner(): Deferred<BaseEntity<MutableList<BannerInfo>>>
}