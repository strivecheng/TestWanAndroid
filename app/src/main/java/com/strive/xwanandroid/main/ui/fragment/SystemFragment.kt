package com.strive.xwanandroid.main.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.http.HttpClient
import com.strive.xwanandroid.common.base.BaseFragment
import com.strive.xwanandroid.common.bean.SystemInfo
import com.strive.xwanandroid.main.ui.adapter.SystemFirstMenuAdapter
import kotlinx.android.synthetic.main.fragment_system.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * @description 广场
 * @date 2020-01-03
 * @author xingcc
 *
 */
class SystemFragment : BaseFragment() {
    companion object {
        val TAG = SystemFragment::class.java.simpleName
    }

    private val firstSystemList = mutableListOf<SystemInfo>()
    private val systemList = mutableListOf<SystemInfo>()

    private val systemFirstMenuAdapter: SystemFirstMenuAdapter by lazy {
        SystemFirstMenuAdapter(firstSystemList)
    }

    override fun bindLayout(): Int = R.layout.fragment_system

    override fun initView() {
        system_rv.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = systemFirstMenuAdapter
        }
    }

    override fun initListener() {

    }

    override fun initData() {
        var coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            val systemData = HttpClient.retrofitService.getSystemData()
            val data = systemData.await()
            withContext(Dispatchers.Main) {
                systemFirstMenuAdapter.setNewData(data.data)

                data.data?.let {
                    for (system in data.data) {

                    }
                }
            }
        }
    }
}