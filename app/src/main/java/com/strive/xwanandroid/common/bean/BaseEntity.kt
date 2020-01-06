package com.strive.xwanandroid.common.bean

/**
 *
 * @description 最外层结构体
 * @date 2020-01-03
 * @author xingcc
 *
 */
data class BaseEntity<T>(
    val errorCode: Int = 0,
    val errorMsg: String?,
    val data: T?
)