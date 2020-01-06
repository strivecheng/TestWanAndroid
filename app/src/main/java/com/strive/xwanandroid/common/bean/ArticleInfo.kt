package com.strive.xwanandroid.common.bean

/**
 *
 * @description 功能描述
 * @date 2020-01-04
 * @author xingcc
 *
 */
data class ArticleInfo(
    var title:String,
    var author: String,
    var link: String
) : BaseInfo()