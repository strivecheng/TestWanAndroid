package com.strive.xwanandroid.common.bean

/**
 *
 * @description 功能描述
 * @date 2020-01-04
 * @author xingcc
 *
 */
data class ArticleInfo(
    val id:Int,
    var title:String,
    var author: String,
    var link: String,
    val shareUser:String,
    val chapterName:String,
    val chapterId:Int,
    val niceShareDate:String,
    var isFavorite:Boolean
) : BaseInfo()