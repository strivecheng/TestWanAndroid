package com.strive.xwanandroid.common.utils

import android.content.SharedPreferences
import com.blankj.utilcode.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Response
import java.util.prefs.Preferences

/**
 *
 * @description 添加cookies的拦截器
 * @date 2020-01-17
 * @author xingcc
 *
 */
class AddCookiesInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val cookies = SPUtils.getInstance().getString("cookies")
        val split = cookies.split(";")
        val newBuilder = request.newBuilder()
        for (string in split){
            newBuilder.addHeader("Cookie",string)
        }
        return chain.proceed(newBuilder.build())
    }


}