package com.strive.xwanandroid.main.ui.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.bean.SystemInfo

/**
 *
 * @description 功能描述
 * @date 2020-01-16
 * @author xingcc
 *
 */
class SystemFirstMenuAdapter(data: MutableList<SystemInfo>?) :
    BaseQuickAdapter<SystemInfo, BaseViewHolder>(R.layout.system_first_menu_item_view, data) {
    override fun convert(helper: BaseViewHolder, item: SystemInfo?) {
        item?.let {
            helper.setText(R.id.first_menu_tv, item.name)
            val secondMenuRv = helper.getView<RecyclerView>(R.id.second_menu_rv)
            secondMenuRv.adapter = SystemSecondMenuAdapter(item.children)
            secondMenuRv.layoutManager = GridLayoutManager(mContext,3)
        }

    }
}