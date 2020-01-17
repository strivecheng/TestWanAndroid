package com.strive.xwanandroid.main.ui.adapter

import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.bean.ArticleInfo

/**
 *
 * @description 功能描述
 * @date 2020-01-15
 * @author xingcc
 *
 */
class SquareAdapter(data: MutableList<ArticleInfo>?) :
    BaseQuickAdapter<ArticleInfo, BaseViewHolder>(R.layout.square_item_view,data) {
    override fun convert(helper: BaseViewHolder, item: ArticleInfo?) {
        item?.let {
            var author = item.author
            if (TextUtils.isEmpty(item.author)){
                author = item.shareUser
            }
            helper.setText(R.id.title_tv, item.title)
                .setText(R.id.author_tv,author)
                .setText(R.id.date_str_tv,item.niceShareDate)
        }
    }
}