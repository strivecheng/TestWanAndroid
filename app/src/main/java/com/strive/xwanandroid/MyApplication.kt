package com.strive.xwanandroid

import android.app.Application
import com.hjq.bar.TitleBar
import com.strive.xwanandroid.common.utils.TitleBarStyle

/**
 *
 * @description 全局的application
 * @date 2020-01-04
 * @author xingcc
 *
 */
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        TitleBar.initStyle(TitleBarStyle(this))
    }

    /*大佬你好*/
}