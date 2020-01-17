package com.strive.xwanandroid.main.ui.fragment

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.http.HttpClient
import com.strive.xwanandroid.common.base.BaseFragment
import com.strive.xwanandroid.common.bean.ArticleInfo
import com.strive.xwanandroid.common.bean.BannerInfo
import com.strive.xwanandroid.common.bean.BaseEntity
import com.strive.xwanandroid.common.bean.ListDataInfo
import com.strive.xwanandroid.common.global.GlobalData
import com.strive.xwanandroid.main.ui.WebActivity
import com.strive.xwanandroid.main.ui.adapter.HomeAdapter
import com.strive.xwanandroid.main.view.MyHomeVpViewHolder
import com.zhpan.bannerview.BannerViewPager
import com.zhpan.bannerview.constants.PageStyle
import com.zhpan.bannerview.constants.TransformerStyle
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow

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

    private lateinit var bannerViewPager: BannerViewPager<BannerInfo, MyHomeVpViewHolder>


    /**
     * Home list async
     */
    private var homeListAsync: Deferred<BaseEntity<ListDataInfo>>? = null

    override fun bindLayout(): Int = R.layout.fragment_home

    override fun initView() {

        home_rv.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = homeAdapter
        }

        val headerView: View = layoutInflater.inflate(R.layout.home_header_view, null)
        homeAdapter.addHeaderView(headerView)
        homeAdapter.addChildClickViewIds(R.id.favorite_iv)
        bannerViewPager =
            headerView.findViewById(R.id.banner_view)

        bannerViewPager.setIndicatorVisibility(View.VISIBLE)
            .setInterval(2000)
            .setAutoPlay(true)
            .setHolderCreator { MyHomeVpViewHolder() }
            .setPageTransformerStyle(TransformerStyle.SCALE_IN)
            .setPageStyle(PageStyle.NORMAL)
    }

    override fun initListener() {
        bannerViewPager.setOnPageClickListener {
            val mutableList = bannerViewPager.list
            val bannerInfo = mutableList[it]
            Intent(activity, WebActivity::class.java).run {
                putExtra("url", bannerInfo?.url)
                startActivity(this)
            }
        }
        homeAdapter.setOnItemClickListener { adapter, view, position ->
            run {
                val item = homeAdapter.getItem(position)
                Intent(activity, WebActivity::class.java).run {
                    putExtra("url", item?.link)
                    startActivity(this)
                }
            }
        }

        homeAdapter.setOnItemChildClickListener { adapter, view, position ->
            run {
                when (view.id) {
                    R.id.favorite_iv -> {
                        val articleInfo = homeAdapter.getItem(position)

                        val coroutineScope = CoroutineScope(Dispatchers.IO)
                        coroutineScope.launch {
                            val string = articleInfo?.let {
                                val deferred =
                                    HttpClient.retrofitService.favoriteInsideArticle(articleInfo.id.toString())

                                val await = deferred.await()
                                await.data
                            }
                            withContext(Dispatchers.Main) {
                                ToastUtils.showShort("收藏成功")
                            }
                        }
                    }
                    else -> {

                    }
                }
            }
        }

        home_srlt.setOnRefreshListener{
            initData()
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
            datas?.let {
                for (article in datas) {
                    val collectIds = GlobalData.userInfo?.collectIds
                    collectIds?.let {
                        for (id in collectIds) {
                            if (article.id == id) {
                                article.isFavorite = true
                            }
                        }
                    }
                }
            }
            homeAdapter.setNewData(datas)

            val bannerData = getBannerData()
            val bannerDatas = bannerData.await()
            bannerViewPager.create(bannerDatas.data)
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
            val homeList = HttpClient.retrofitService.getHomeData(0)
            homeList
        }

    private suspend fun getBannerData() =
        withContext(Dispatchers.IO) {
            val homeBanner = HttpClient.retrofitService.getHomeBanner()
            homeBanner
        }
}