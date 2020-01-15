package com.strive.xwanandroid.main.ui

import com.blankj.utilcode.util.ToastUtils
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.HttpClient
import com.strive.xwanandroid.common.base.BaseActivity
import com.strive.xwanandroid.common.bean.BaseEntity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*

class LoginActivity : BaseActivity() {


    override fun bindLayout(): Int = R.layout.activity_login

    override fun initView() {
        account_et.setText("strive")
        password_et.setText("19930410")
    }

    override fun initListener() {
        login_btn.setOnClickListener {

            val coroutineScope = CoroutineScope(Dispatchers.Main)
            coroutineScope.launch {
                var loginResult: Deferred<BaseEntity<String>>? = null
                loginResult = login()
                ToastUtils.showShort("登录成功")
                finish()
            }
        }
    }

    override fun initData() {
    }

    suspend fun login() =
        withContext(Dispatchers.IO) {
            val string = HttpClient.retrofitService.login(
                account_et.text.toString(),
                password_et.text.toString()
            )
            string
        }

}
