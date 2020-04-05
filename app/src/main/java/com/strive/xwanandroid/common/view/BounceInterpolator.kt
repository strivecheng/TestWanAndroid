package com.strive.xwanandroid.common.view

import android.view.animation.Interpolator
import kotlin.math.cos
import kotlin.math.pow

/**
 *
 * @description 功能描述
 * @date 2020/4/3
 * @author xingcc
 *
 */
class BounceInterpolator(private val amplitude: Double, private val frequency: Double) :
    Interpolator {
    override fun getInterpolation(input: Float): Float =
        (-1 * Math.E.pow(-input / amplitude) * cos(frequency * input) + 1).toFloat()

}