package com.strive.xwanandroid.common.bean

/**
 *
 * @description 功能描述
 * @date 2020-01-03
 * @author xingcc
 *
 */
data class ListDataInfo(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    val datas:MutableList<ArticleInfo>

    )