package com.strive.xwanandroid.common.bean


/**
 *
 * @description 功能描述
 * @date 2020-01-17
 * @author xingcc
 *
 */

data class UserInfo(
    val publicName: String,
    val nickname: String,
    val username: String,
    val email: String,
    val collectIds: MutableList<Int>
)