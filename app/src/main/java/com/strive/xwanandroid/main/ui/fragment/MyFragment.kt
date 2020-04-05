package com.strive.xwanandroid.main.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.view.View
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.base.BaseFragment
import com.strive.xwanandroid.common.global.GlobalData
import com.strive.xwanandroid.common.utils.PermissionUtils
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
        if (!PermissionUtils.hasPermission(context!!, Manifest.permission.CAMERA)) {
            val hasAlwaysDeniedPermission =
                PermissionUtils.hasAlwaysDeniedPermission(this, Manifest.permission.CAMERA)
            if (hasAlwaysDeniedPermission){
                PermissionUtils.requestPermission(this, Manifest.permission.CAMERA,100)
            }else{
                PermissionUtils.requestPermission(this, Manifest.permission.CAMERA,100)
            }
        }
        user_name_tv.text = GlobalData.userInfo?.publicName
    }

    override fun initListener() {
        favorite_hiv.setOnClickListener {
            if (!GlobalData.isLogin) {
                val intent = Intent(activity,LoginActivity::class.java)
                startActivityForResult(intent,0)
            }
        }

    }

    override fun initData() {
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK){
            initView()
        }
    }
}