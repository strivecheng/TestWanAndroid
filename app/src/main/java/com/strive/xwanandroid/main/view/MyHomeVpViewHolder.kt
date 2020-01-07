package com.strive.xwanandroid.main.view

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.bean.BannerInfo
import com.zhpan.bannerview.holder.ViewHolder

/**
 *
 * @description 功能描述
 * @date 2020-01-07
 * @author xingcc
 *
 */
class MyHomeVpViewHolder : ViewHolder<BannerInfo> {

    override fun getLayoutId(): Int = R.layout.home_banner_item_view

    override fun onBind(itemView: View?, data: BannerInfo?, position: Int, size: Int) {
        val imageView = itemView?.findViewById<ImageView>(R.id.banner_item_iv)

        imageView?.let {
            Glide.with(it).load(data?.imagePath).into(it)
        }

    }



}