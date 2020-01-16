package com.strive.xwanandroid.common.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @description recycleView的分组头
 * @date 2020-01-16
 * @author xingcc
 *
 */
class StickItemDecoration : RecyclerView.ItemDecoration() {
    private var itemSectionHeight = 5
    private var mPaint = Paint()

    init {
        mPaint.color = Color.parseColor("#00ff00")
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val childAdapterPosition = parent.getChildAdapterPosition(view)
        if (childAdapterPosition != 0) {
            outRect.top = itemSectionHeight
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val left = parent.left
        val right = parent.right
        val childCount = parent.childCount
        for (i in 0..childCount ) {
            val childAt = parent.getChildAt(i)
            //itemView的顶部，section的底部
            val bottom = childAt.top
            val top = bottom - itemSectionHeight
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(),mPaint)

        }
    }

}