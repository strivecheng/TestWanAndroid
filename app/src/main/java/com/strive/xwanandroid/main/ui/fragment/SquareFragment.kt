package com.strive.xwanandroid.main.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.http.HttpClient
import com.strive.xwanandroid.common.base.BaseFragment
import com.strive.xwanandroid.common.bean.ArticleInfo
import com.strive.xwanandroid.main.ui.adapter.SquareAdapter
import kotlinx.android.synthetic.main.fragment_square.*
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
class SquareFragment : BaseFragment() {
    companion object {
        val TAG = SquareFragment::class.java.simpleName
    }
    private var pageNum = 1

    private var squareList = mutableListOf<ArticleInfo>()

    private val squareAdapter: SquareAdapter by lazy {
        SquareAdapter(squareList)
    }

    override fun bindLayout(): Int = R.layout.fragment_square

    override fun initView() {
        square_rv.run {
            layoutManager = LinearLayoutManager(activity)
            adapter = squareAdapter
        }
    }

    override fun initListener() {

    }

    override fun initData() {

        getSquareData()
    }

    private fun getSquareData() {
       val coroutineScope = CoroutineScope(Dispatchers.Main)
        coroutineScope.launch {
           val baseEntity =withContext(Dispatchers.IO){
                val squareList1 = HttpClient.retrofitService.getSquareList(pageNum)
                val baseEntity = squareList1.await()
                baseEntity
            }
            squareAdapter.setNewData(baseEntity.data?.datas)
        }
    }
}