package com.strive.xwanandroid.common.http

import com.strive.xwanandroid.common.bean.BaseEntity
import com.strive.xwanandroid.common.bean.ListDataInfo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

/**
 *
 * @description 后台接口
 * @date 2020-01-03
 * @author xingcc
 *
 */
interface ApiService {
    @GET("/article/list/{pageNum}/json")
    fun getHomeData(@Path("pageNum") pageNum: Int): Deferred<BaseEntity<ListDataInfo>>
}