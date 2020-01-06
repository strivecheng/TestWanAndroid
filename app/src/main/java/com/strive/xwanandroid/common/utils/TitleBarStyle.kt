package com.strive.xwanandroid.common.utils

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.hjq.bar.SelectorDrawable
import com.hjq.bar.style.BaseTitleBarStyle
import com.strive.xwanandroid.R

/**
 *
 * @description titleBar的Style
 * @date 2020-01-04
 * @author xingcc
 *
 */
class TitleBarStyle(context: Context) : BaseTitleBarStyle(context) {
    override fun getRightColor(): Int = 0xFFFFFFFF.toInt()

    override fun getBackIcon(): Drawable {
        return getDrawable(R.drawable.ic_arrow_back_black_24dp)
    }

    override fun getRightBackground(): Drawable = leftBackground

    override fun getBackground(): Drawable = ColorDrawable(0xFFF5F5F5.toInt())

    override fun getLeftBackground(): Drawable {
        return SelectorDrawable.Builder()
            .setDefault(ColorDrawable(0x00000000))
            .setFocused(ColorDrawable(0x0C000000))
            .setPressed(ColorDrawable(0x0C000000))
            .builder()
    }

    override fun isLineVisible(): Boolean = true

    override fun getLineSize(): Int = 1

    override fun getLineDrawable(): Drawable = ColorDrawable(0xFFECECEC.toInt())

    override fun getTitleColor(): Int = 0xFF000000.toInt()

    override fun getLeftColor(): Int = 0xFF000000.toInt()
}