package com.strive.xwanandroid.common.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.strive.xwanandroid.R

/**
 *
 * @description ac基类
 * @date 2020-01-03
 * @author xingcc
 *
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(bindLayout())
        initView()
        initListener()
        initData()
    }

    abstract fun bindLayout(): Int

    abstract fun initView()

    abstract fun initListener()

    abstract fun initData()
}