package com.strive.xwanandroid.main.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.bean.ArticleInfo

/**
 *
 * @description 首页文章适配器
 * @date 2020-01-04
 * @author xingcc
 *
 */
class HomeAdapter(data: MutableList<ArticleInfo>?) :
    BaseQuickAdapter<ArticleInfo, BaseViewHolder>(R.layout.home_item_view, data) {
    override fun convert(helper: BaseViewHolder, item: ArticleInfo?) {
        helper.setText(R.id.title_tv, item?.title)
            .setText(R.id.author_tv,item?.author)
    }
}