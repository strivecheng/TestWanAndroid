package com.strive.xwanandroid.main.ui.adapter

import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.bean.SystemInfo

/**
 *
 * @description 二级菜单适配器
 * @date 2020-01-16
 * @author xingcc
 *
 */
class SystemSecondMenuAdapter(data: MutableList<SystemInfo>?) :
    BaseQuickAdapter<SystemInfo, BaseViewHolder>(R.layout.system_second_menu_item_view, data) {
    override fun convert(helper: BaseViewHolder, item: SystemInfo?) {
        helper.setText(R.id.first_menu_tv,item?.name)

    }
}