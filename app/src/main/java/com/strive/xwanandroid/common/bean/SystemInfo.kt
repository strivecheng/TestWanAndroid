package com.strive.xwanandroid.common.bean

/**
 *
 * @description 功能描述
 * @date 2020-01-15
 * @author xingcc
 *
 */

data class SystemInfo(
    var courseId: String,
    var name: String,
    var id: String,
    var parentChapterId: Int,
    var userControlSetTop:Boolean,
    var visible:Int,
    var order:Int,
    var children:MutableList<SystemInfo>
)