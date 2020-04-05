package com.strive.xwanandroid.common.utils

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 *
 * @description 权限申请工具类
 * @date 2020/3/18
 * @author xingcc
 *
 */
object PermissionUtils {
    fun hasPermission(
        context: Context,
        permission: String
    ): Boolean {
        val result: Int = ContextCompat.checkSelfPermission(context, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(
        activity: Activity,
        permission: String,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            requestCode
        )
    }

    fun requestPermission(
        activity: Activity,
        requestCode: Int,
        vararg permission: String?
    ) {
        ActivityCompat.requestPermissions(activity, permission, requestCode)
    }

    fun requestPermission(
        activity: Activity,
        permissions: Array<String?>,
        requestCode: Int
    ) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode)
    }

    fun requestPermission(
        fragment: Fragment,
        permission: String,
        requestCode: Int
    ) {
        fragment.requestPermissions(arrayOf(permission), requestCode)
    }

    fun requestPermission(
        fragment: Fragment,
        requestCode: Int,
        vararg permission: String?
    ) {
        fragment.requestPermissions(permission, requestCode)
    }

    fun requestPermission(
        fragment: Fragment,
        permissions: Array<String?>,
        requestCode: Int
    ) {
        fragment.requestPermissions(permissions, requestCode)
    }

    fun hasAlwaysDeniedPermission(activity: Activity, permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }

    fun hasAlwaysDeniedPermission(fragment: Fragment, permission: String): Boolean {
        return fragment.shouldShowRequestPermissionRationale(permission)
    }
}