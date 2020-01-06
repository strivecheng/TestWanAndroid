package com.strive.xwanandroid.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_web.*

class WebActivity : BaseActivity() {



    override fun bindLayout(): Int = R.layout.activity_web

    override fun initView() {
        val url = intent.getStringExtra("url")
        web_view.loadUrl(url)
    }

    override fun initListener() {
    }

    override fun initData() {
    }
}
