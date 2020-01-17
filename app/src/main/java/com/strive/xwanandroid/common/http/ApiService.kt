package com.strive.xwanandroid.common.http

import com.strive.xwanandroid.common.bean.*
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
    fun login(@Field("username") username: String, @Field("password") password: String): Deferred<BaseEntity<UserInfo>>

    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field("username") username: String, @Field("password") password: String, @Field("password") repassword: String): Deferred<BaseEntity<String>>

    @GET("/user/login")
    fun logout(): Deferred<BaseEntity<String>>

    @GET("/article/list/{pageNum}/json")
    fun getHomeData(@Path("pageNum") pageNum: Int): Deferred<BaseEntity<ListDataInfo>>

    @GET("/banner/json")
    fun getHomeBanner(): Deferred<BaseEntity<MutableList<BannerInfo>>>

    @GET("/user_article/list/{pageNum}/json")
    fun getSquareList(@Path("pageNum") pageNum: Int): Deferred<BaseEntity<ListDataInfo>>

    @GET("/tree/json")
    fun getSystemData(): Deferred<BaseEntity<MutableList<SystemInfo>>>

    @GET("/article/list/{pageNum}/json")
    fun getSystemChildArtical(@Path("pageNum") pageNum: Int, @Query("cid") cid: String): Deferred<BaseEntity<ListDataInfo>>

    @POST("lg/collect/{id}/json")
    fun favoriteInsideArticle(@Path("id") id: String): Deferred<BaseEntity<String>>

    @POST("lg/uncollect_originId/{id}/json")
    fun cancelfavoriteArticle(@Path("id") id: String): Deferred<BaseEntity<String>>

    @FormUrlEncoded
    @POST("lg/collect/add/json")
    fun favoriteOuterArticle(
        @Field("title") title: String, @Field("author") author: String, @Field(
            "link"
        ) link: String
    ): Deferred<BaseEntity<String>>

}