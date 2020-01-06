package com.strive.xwanandroid.main.ui.fragment

import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.HttpClient
import com.strive.xwanandroid.common.base.BaseFragment
import com.strive.xwanandroid.common.bean.ArticleInfo
import com.strive.xwanandroid.common.bean.BaseEntity
import com.strive.xwanandroid.common.bean.ListDataInfo
import com.strive.xwanandroid.main.ui.WebActivity
import com.strive.xwanandroid.main.ui.adapter.HomeAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import java.lang.Runnable

/**
 *
 * @description 首页
 * @date 2020-01-03
 * @author xingcc
 *
 *
 *
 *
 */
class HomeFragment : BaseFragment() {
    companion object {
        val TAG = HomeFragment::class.java.simpleName
    }

    private val articleInfos = mutableListOf<ArticleInfo>()


    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(articleInfos)
    }

    /**
     * Home list async
     */
    private var homeListAsync: Deferred<BaseEntity<ListDataInfo>>? = null

    override fun bindLayout(): Int = R.layout.fragment_home

    override fun initView() {
        for (index in 1..100) {
            articleInfos.add(ArticleInfo("标题", "周杰伦", "http://www.baidu.com"))
        }
        home_rv.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }
    }

    override fun initListener() {
        homeAdapter.setOnItemClickListener { adapter, view, position ->
            run {
                val item = homeAdapter.getItem(position)
                Intent(activity, WebActivity::class.java).run {
                    putExtra("url", item?.link)
                    startActivity(this)
                }
            }
        }
    }

    override fun initData() {
//        async(UI) {
//            tryCatch({
//                it.printStackTrace()
//                onHomeListListener.getHomeListFailed(it.toString())
//            }) {
//                homeListAsync?.cancelByActive()
//                homeListAsync = RetrofitHelper.retrofitService.getHomeList(page)
//                // Get async result
//                val result = homeListAsync?.await()
//                result ?: let {
//                    onHomeListListener.getHomeListFailed(Constant.RESULT_NULL)
//                    return@async
//                }
//                onHomeListListener.getHomeListSuccess(result)
//            }
//        }

        val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
            Log.i(TAG, "当前线程：${Thread.currentThread().name}")
            homeListAsync = getData()
            val result = homeListAsync?.await()
            val datas = result?.data?.datas
            homeAdapter.setNewData(datas)
        }

        coroutineScope.launch {
            withContext(Dispatchers.IO){
                var homeBanner = HttpClient.retrofitService.getHomeBanner()
                homeBanner
            }
        }
    }

    /**
     * 创建协程的方式
     * ① runBlocking {getImage()}
     * 方法一通常适用于单元测试的场景，而业务开发中不会用到这种方法，因为它是线程阻塞的。
     * ② GlobalScope.launch {getImage()}
     * 方法二和使用 runBlocking 的区别在于不会阻塞线程。但在 Android 开发中同样不推荐这种用法
     * ，因为它的生命周期会和 app 一致，且不能取消（什么是协程的取消后面的文章会讲）
     * ③
     * val coroutineScope = CoroutineScope(context)  类型为 CoroutineContext 的参数
     * coroutineScope.launch {getImage(imageId)}
     *
     * async也可以创建协程
     *
     * withContext 切换线程
     * suspend 挂起，就是一个稍后会被自动切回来的线程调度操作。
     */
    private suspend fun getData() =
        withContext(Dispatchers.IO) {
            Log.i(TAG, "当前线程：${Thread.currentThread().name}")
            val  homeList = HttpClient.retrofitService.getHomeData(0)
            homeList
        }

}