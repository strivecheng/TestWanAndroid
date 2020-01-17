package com.strive.xwanandroid.common.utils

import com.blankj.utilcode.util.SPUtils
import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 * @description 保存cookies的拦截器
 * @date 2020-01-17
 * @author xingcc
 *
 */
class SaveCookiesInterceptor :Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val headers = response.headers("Set-Cookie")
        if (request.url.toString().contains("login")&&headers.isNotEmpty()){
            var stringBuffer = StringBuffer()
            for (header in headers){
                stringBuffer.append(header).append(";")
            }
            if (stringBuffer.isNotEmpty()){
                SPUtils.getInstance().put("cookies",stringBuffer.toString().substring(0,stringBuffer.toString().length-1))
            }
        }
        return response
    }


}