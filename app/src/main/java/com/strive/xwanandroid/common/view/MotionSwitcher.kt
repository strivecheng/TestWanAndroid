package com.strive.xwanandroid.common.view

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.annotation.ColorInt
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.graphics.withTranslation
import com.google.android.material.math.MathUtils.lerp
import com.strive.xwanandroid.R
import com.strive.xwanandroid.common.global.*

/**
 *
 * @description 功能描述
 * @date 2020/4/3
 * @author xingcc
 *
 */
class MotionSwitcher @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr) {

    /**
     * 默认高度
     */
    private var defHeight = 0
    /**
     * 默认宽带
     */
    private var defWidth = 0
    /**
     * 选中状态
     */
    var isChecked = true
        private set

    /**
     * switcher的矩形
     */
    private val switcherRect = RectF(0f, 0f, 0f, 0f)

    /**
     * 内部圆形的矩形
     */
    private val iconRect = RectF(0f, 0f, 0f, 0f)

    /**
     * 内部圆形的镂空的小圆形
     */
    private val iconClipRect = RectF(0f, 0f, 0f, 0f)

    /**
     * switcher的圆角
     */
    private var switcherCornerRadius = 0f

    /**
     * switcher的海拔
     */
    private var switchElevation = 0f

    /**
     * 阴影高度
     */
    private var shadowOffset = 0f
    private var iconHeight = 0f
    private var iconRadius = 0f
    private var iconClipRadius = 0f
    private var iconCollapsedWidth = 0f

    private val switcherPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val iconPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val iconClipPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val shadowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var shadow: Bitmap? = null

    @ColorInt
    private var onColor = 0

    @ColorInt
    private var offColor = 0

    @ColorInt
    private var iconColor = 0

    @ColorInt
    private var currentColor = 0
        set(value) {
            field = value
            switcherPaint.color = value
            iconClipPaint.color = value
        }

    private var animatorSet: AnimatorSet? = AnimatorSet()
    private var onClickOffset = 0f
        set(value) {
            field = value
            switcherRect.left = value + shadowOffset
            switcherRect.top = value + shadowOffset / 2
            switcherRect.right = width.toFloat() - value - shadowOffset
            switcherRect.bottom = height.toFloat() - value - shadowOffset - shadowOffset / 2
            if (!isLollipopAndAbove()) generateShadow()
            invalidate()
        }

    private var iconTranslateX = 0f

    // from rounded rect to circle and back
    private var iconProgress = 0f
        set(value) {
            if (field != value) {
                field = value
                //lerp
                val iconOffset = lerp(0f, iconRadius - iconCollapsedWidth / 2, value)
                iconRect.left = width - switcherCornerRadius - iconCollapsedWidth / 2 - iconOffset
                iconRect.right = width - switcherCornerRadius + iconCollapsedWidth / 2 + iconOffset

                val clipOffset = lerp(0f, iconClipRadius, value)
                iconClipRect.set(
                    iconRect.centerX() - clipOffset,
                    iconRect.centerY() - clipOffset,
                    iconRect.centerX() + clipOffset,
                    iconRect.centerY() + clipOffset
                )
                if (!isLollipopAndAbove()) generateShadow()
                postInvalidateOnAnimation()
            }
        }

    init {
        attrs?.let { retrieveAttributes(attrs = attrs, defStyleAttr = defStyleAttr) }
        setOnClickListener { setChecked(!isChecked) }
    }

    @SuppressLint("CustomViewStyleable")
    private fun retrieveAttributes(attrs: AttributeSet, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.MotionSwitcher,
            defStyleAttr,
            R.style.MotionSwitcher
        )
        switchElevation = typedArray.getDimension(R.styleable.MotionSwitcher_elevation, 0f)

        onColor = typedArray.getColor(R.styleable.MotionSwitcher_switcher_on_color, 0)
        offColor = typedArray.getColor(R.styleable.MotionSwitcher_switcher_off_color, 0)
        iconColor = typedArray.getColor(R.styleable.MotionSwitcher_switcher_icon_color, 0)

        isChecked = typedArray.getBoolean(R.styleable.MotionSwitcher_android_checked, true)

        if (!isChecked) iconProgress = 1f

        currentColor = if (isChecked) onColor else offColor

        iconPaint.color = iconColor

        defHeight =
            typedArray.getDimensionPixelOffset(R.styleable.MotionSwitcher_switcher_height, 0)
        defWidth = typedArray.getDimensionPixelOffset(R.styleable.MotionSwitcher_switcher_width, 0)
        typedArray.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
            width = defWidth
            height = defHeight
        }

        if (!isLollipopAndAbove()) {
            width += switchElevation.toInt() * 2
            height += switchElevation.toInt() * 2
        }

        setMeasuredDimension(width, height)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        if (isLollipopAndAbove()) {
            outlineProvider = SwitchOutline(w, h)
            elevation = switchElevation
        } else {
//            shadowOffset = switchElevation
//            iconTranslateX = -shadowOffset
        }

        switcherRect.left = shadowOffset
        switcherRect.top = shadowOffset / 2
        switcherRect.right = width.toFloat() - shadowOffset
        switcherRect.bottom = height.toFloat() - shadowOffset - shadowOffset / 2

        switcherCornerRadius = (height - shadowOffset * 2) / 2f

        iconRadius = switcherCornerRadius * 0.6f
        iconClipRadius = iconRadius / 2.25f
        iconCollapsedWidth = iconRadius - iconClipRadius

        iconHeight = iconRadius * 2f

        iconRect.set(
            width - switcherCornerRadius - iconCollapsedWidth / 2,
            ((height - iconHeight) / 2f) - shadowOffset / 2,
            width - switcherCornerRadius + iconCollapsedWidth / 2,
            (height - (height - iconHeight) / 2f) - shadowOffset / 2
        )

        if (!isChecked) {
            iconRect.left =
                width - switcherCornerRadius - iconCollapsedWidth / 2 - (iconRadius - iconCollapsedWidth / 2)
            iconRect.left =
                width - switcherCornerRadius + iconCollapsedWidth / 2 + (iconRadius - iconCollapsedWidth / 2)

            iconClipRect.set(
                iconRect.centerX() - iconClipRadius,
                iconRect.centerY() - iconClipRadius,
                iconRect.centerX() + iconClipRadius,
                iconRect.centerY() + iconClipRadius
            )

            iconTranslateX = -(width - shadowOffset - switcherCornerRadius * 2)
        }


//        if (!isLollipopAndAbove()) generateShadow()


    }

    private fun generateShadow() {

    }

    override fun onDraw(canvas: Canvas?) {
        // shadow
        if (!isLollipopAndAbove() && switchElevation > 0f && !isInEditMode) {
            canvas?.drawBitmap(shadow as Bitmap, 0f, shadowOffset, null)
        }

        //switcher
        canvas?.drawRoundRect(
            switcherRect,
            switcherCornerRadius,
            switcherCornerRadius,
            switcherPaint
        )

        //icon
        canvas?.withTranslation(x = iconTranslateX) {
            drawRoundRect(iconRect, switcherCornerRadius, switcherCornerRadius, iconPaint)
            if (iconClipRect.width() > iconCollapsedWidth)
                drawRoundRect(iconClipRect, iconRadius, iconRadius, iconClipPaint)
        }
    }

    private fun animateSwitch() {
        animatorSet?.cancel()
        animatorSet = AnimatorSet()

        onClickOffset = ON_CLICK_RADIUS_OFFSET
        var amplitude = BOUNCE_ANIM_AMPLITUDE_IN
        var frequency = BOUNCE_ANIM_FREQUENCY_IN
        var iconTranslateA = 0f
        var iconTranslateB = -(width - shadowOffset - switcherCornerRadius * 2)
        var newProgress = 1f

        if (isChecked) {
            amplitude = BOUNCE_ANIM_AMPLITUDE_OUT
            frequency = BOUNCE_ANIM_FREQUENCY_OUT
            iconTranslateA = iconTranslateB
            iconTranslateB = -shadowOffset
            newProgress = 0f
        }

        val switcherAnimator = ValueAnimator.ofFloat(iconProgress, newProgress).apply {
            addUpdateListener {
                iconProgress = it.animatedValue as Float
            }
            interpolator = BounceInterpolator(amplitude, frequency)
            duration = SWITCHER_ANIMATION_DURATION
        }

        val translateAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener {
                val value = it.animatedValue as Float
                iconTranslateX = lerp(iconTranslateA, iconTranslateB, value)
            }
            doOnEnd { onClickOffset = 0f }
            duration = TRANSLATE_ANIMATION_DURATION
        }
        val toColor = if (isChecked) onColor else offColor
        iconClipPaint.color = toColor

        val colorAnimator = ValueAnimator().apply {
            addUpdateListener {
                currentColor = it.animatedValue as Int
            }
            setIntValues(currentColor, toColor)
            setEvaluator(ArgbEvaluator())
            duration = COLOR_ANIMATION_DURATION
        }

        animatorSet?.apply {
            doOnStart {
                listener?.invoke(isChecked)
            }
            playTogether(switcherAnimator, translateAnimator, colorAnimator)
            start()
        }
    }

    private var listener: ((isChecked: Boolean) -> Unit)? = null

    fun setChecked(checked: Boolean, withAnimator: Boolean = true) {
        if (this.isChecked != checked) {
            this.isChecked = checked
            if (withAnimator && width != 0) {
                animateSwitch()
            } else {
                animatorSet?.cancel()
                if (!checked) {
                    currentColor = offColor
                    iconProgress = 1f
                    iconTranslateX = -(width - shadowOffset - switcherCornerRadius * 2)
                } else {
                    currentColor = onColor
                    iconProgress = 0f
                    iconTranslateX = -shadowOffset
                }
            }
        }
    }

    override fun onSaveInstanceState(): Parcelable? {
        super.onSaveInstanceState()
        return Bundle().apply {
            putBoolean(KEY_CHECKED, isChecked)
            putParcelable(STATE, super.onSaveInstanceState())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            super.onRestoreInstanceState(state.getParcelable(STATE))
            isChecked = state.getBoolean(KEY_CHECKED)
            if (!isChecked) {
                forceUncheck()
            }

        }
    }

    private fun forceUncheck() {
        currentColor = offColor
        iconProgress = 1f
        iconTranslateX = -(width - shadowOffset - switcherCornerRadius * 2)
    }

//    private fun setShadowBlurRadius(elevation: Float) {
//        val maxElevation = context.toPx(24f)
//        switchElevation = min(25f * (elevation / maxElevation), 25f)
//    }

    private inner class SwitchOutline internal constructor(
        val width: Int,
        val height: Int
    ) : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            outline?.setRoundRect(0, 0, width, height, switcherCornerRadius)
        }

    }

    fun isLollipopAndAbove(): Boolean =
        android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP

}