package com.strive.xwanandroid.main.ui

import android.Manifest
import androidx.navigation.Navigation
import com.blankj.utilcode.constant.PermissionConstants
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.base.BaseActivity
import com.strive.xwanandroid.common.base.BaseFragment
import com.strive.xwanandroid.common.utils.PermissionUtils
import com.strive.xwanandroid.main.ui.fragment.HomeFragment
import com.strive.xwanandroid.main.ui.fragment.MyFragment
import com.strive.xwanandroid.main.ui.fragment.SquareFragment
import com.strive.xwanandroid.main.ui.fragment.SystemFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {
    private var homeFragment: HomeFragment? = null
    private var squareFragment: SquareFragment? = null
    private var systemFragment: SystemFragment? = null
    private var myFragment: MyFragment? = null
    private var currentFragment: BaseFragment? = null
    private val fragmentManager by lazy {
        supportFragmentManager
    }

    override fun bindLayout(): Int = R.layout.activity_main

    override fun initView() {
        homeFragment = HomeFragment()
        homeFragment?.let {
            fragmentManager.beginTransaction().add(R.id.fl, it, HomeFragment.TAG).commit()
            currentFragment = it
        }

//        Navigation.findNavController(this,)
    }

    override fun initListener() {
        bottom_navigation_view.run {
            setOnNavigationItemSelectedListener {
                it.isChecked = true
                val beginTransaction = fragmentManager.beginTransaction()
                currentFragment?.let { fragment ->
                    beginTransaction.hide(fragment)
                }
                when (it.itemId) {
                    R.id.home -> {

                        if (homeFragment == null) {
                            HomeFragment()
                                .let { fragment ->
                                homeFragment = fragment
                                beginTransaction.add(R.id.fl, fragment, HomeFragment.TAG).commit()
                            }
                        } else {
                            homeFragment?.let { fragment ->
                                if (fragment.isAdded) {
                                    beginTransaction.show(fragment).commit()
                                } else {
                                    beginTransaction.add(R.id.fl, fragment, HomeFragment.TAG)
                                        .commit()
                                }
                            }
                        }
                        currentFragment = homeFragment
                        true
                    }
                    R.id.square -> {
                        if (squareFragment == null) {
                            SquareFragment()
                                .let { fragment ->
                                squareFragment = fragment
                                beginTransaction.add(R.id.fl, fragment, SquareFragment.TAG).commit()
                            }
                        } else {
                            squareFragment?.let { fragment ->
                                if (fragment.isAdded) {
                                    beginTransaction.show(fragment).commit()
                                } else {
                                    beginTransaction.add(R.id.fl, fragment, SquareFragment.TAG)
                                        .commit()
                                }
                            }
                        }
                        currentFragment = squareFragment
                        true
                    }
                    R.id.system -> {
                        if (systemFragment == null) {
                            SystemFragment()
                                .let { fragment ->
                                systemFragment = fragment
                                beginTransaction.add(R.id.fl, fragment, SystemFragment.TAG).commit()
                            }
                        } else {
                            systemFragment?.let { fragment ->
                                if (fragment.isAdded) {
                                    beginTransaction.show(fragment).commit()
                                } else {
                                    beginTransaction.add(R.id.fl, fragment, SystemFragment.TAG)
                                        .commit()
                                }
                            }

                        }
                        currentFragment = systemFragment
                        true
                    }
                    R.id.my -> {
                        if (myFragment == null) {
                            MyFragment()
                                .let { fragment ->
                                myFragment = fragment
                                beginTransaction.add(R.id.fl, fragment, MyFragment.TAG).commit()
                            }
                        } else {
                            myFragment?.let { fragment ->
                                if (fragment.isAdded) {
                                    beginTransaction.show(fragment).commit()
                                } else {
                                    beginTransaction.add(R.id.fl, fragment, MyFragment.TAG).commit()
                                }
                            }
                        }
                        currentFragment = myFragment
                        true

                    }
                    else -> {
                        false
                    }
                }
            }
        }

    }

    override fun initData() {

    }

}
