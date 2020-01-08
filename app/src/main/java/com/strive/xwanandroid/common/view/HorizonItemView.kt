package com.strive.xwanandroid.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import com.blankj.utilcode.util.SizeUtils
import com.strive.xwanandroid.R
import kotlinx.android.synthetic.main.horizon_item_view.view.*

/**
 *
 * @description 通用水平的item
 * 包含左边图标 文本 右边箭头
 * @date 2020-01-08
 * @author xingcc
 *
 */
class HorizonItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr, 0) {

    private var leftTitleText: String?
    private var leftTextSize: Float?
    private var rightTitleText: String?
    private var rightHintText: String?
    private var rightTextSize: Float?
    private var rightTextColor: Int?
    private var leftIconImg: Int
    private var canLeftImg: Boolean
    private var canRightImg: Boolean

    init {
        LayoutInflater.from(context).inflate(R.layout.horizon_item_view, this)
        val ta =
            context.obtainStyledAttributes(attrs, R.styleable.HorizonItemView)
        try {
            leftTitleText = ta.getString(R.styleable.HorizonItemView_leftTitleText)
            leftTextSize = ta.getDimensionPixelSize(
                R.styleable.HorizonItemView_leftTextSize,
                SizeUtils.dp2px(14f)
            ).toFloat()
            leftIconImg = ta.getResourceId(R.styleable.HorizonItemView_leftIconImg, 0)
            canLeftImg = ta.getBoolean(R.styleable.HorizonItemView_canLeftImg, false)
            rightTitleText = ta.getString(R.styleable.HorizonItemView_rightTitleText)
            rightHintText = ta.getString(R.styleable.HorizonItemView_rightHintText)
            rightTextSize = ta.getDimensionPixelSize(
                R.styleable.HorizonItemView_rightTextSize,
                SizeUtils.dp2px(14f)
            ).toFloat()
            rightTextColor = ta.getColor(
                R.styleable.HorizonItemView_rightTextColor,
                ActivityCompat.getColor(context, R.color.fontBlack)
            )
            canRightImg = ta.getBoolean(R.styleable.HorizonItemView_canRightImg, true)

            setupView()
        } finally {
            ta.recycle()
        }
    }

    private fun setupView() {
        left_title_tv.text = leftTitleText
    }
}