package com.strive.xwanandroid.main.ui.fragment

import android.content.Intent
import android.view.View
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.base.BaseFragment
import com.strive.xwanandroid.common.global.GlobalData
import com.strive.xwanandroid.main.ui.LoginActivity
import kotlinx.android.synthetic.main.fragment_my.*

/**
 *
 * @description 我
 * @date 2020-01-03
 * @author xingcc
 *
 */
class MyFragment : BaseFragment() {
    companion object {
        val TAG = MyFragment::class.java.simpleName
    }

    override fun bindLayout(): Int = R.layout.fragment_my

    override fun initView() {

    }

    override fun initListener() {
        favorite_hiv.setOnClickListener {
            if (!GlobalData.isLogin) {
                val intent = Intent(activity,LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }

    override fun initData() {
    }
}