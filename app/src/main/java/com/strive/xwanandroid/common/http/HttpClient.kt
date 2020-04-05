package com.strive.xwanandroid.common.http

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.strive.xwanandroid.common.global.BASE_URL
import com.strive.xwanandroid.common.utils.AddCookiesInterceptor
import com.strive.xwanandroid.common.utils.HttpLoggingInterceptor
import com.strive.xwanandroid.common.utils.SaveCookiesInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 * @description 网络封装类
 * @date 2020-01-03
 * @author xingcc
 *
 */

object HttpClient {
    private const val TAG = "HttpClient"

    val retrofitService: ApiService =
        getService(ApiService::class.java)

    private fun createRetrofit(): Retrofit {
        return RetrofitBuild(
            initClient(),
            GsonConverterFactory.create(),
            CoroutineCallAdapterFactory()
        ).retrofit
    }

    private fun initClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(
                10L,
                TimeUnit.SECONDS
            )
            .readTimeout(
                10L,
                TimeUnit.SECONDS
            )
            .writeTimeout(
                10L,
                TimeUnit.SECONDS

            ).addInterceptor(SaveCookiesInterceptor())
            .addInterceptor(AddCookiesInterceptor())
            .addInterceptor(
                HttpLoggingInterceptor(
                    "wanAndroid",
                    true
                ).setLevel(HttpLoggingInterceptor.Level.BODY)

            )
            .build()

    }

    private fun <T> getService(service: Class<T>): T = createRetrofit().create(service)

}

/**
 * create retrofit build
 */
class RetrofitBuild(
    client: OkHttpClient,
    gsonFactory: GsonConverterFactory,
    coroutineCallAdapterFactory: CoroutineCallAdapterFactory
) {
    val retrofit: Retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        client(client)
        addConverterFactory(gsonFactory)
        addCallAdapterFactory(coroutineCallAdapterFactory)
    }.build()
}
