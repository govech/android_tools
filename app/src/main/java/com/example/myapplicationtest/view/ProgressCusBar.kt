package com.example.myapplicationtest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class ProgressCusBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val defaultSize = 500//默认大小

    private val defaultPaintSize = 20f//画笔默认宽度

    private var progress = 0f//当前进度 0～1

    private lateinit var rectF: RectF

    private var mSize = defaultSize

    private val defaultPaint = Paint().apply {
        color = Color.parseColor("#FFFFD200")
        isAntiAlias = true //设置抗锯齿
        strokeWidth = defaultPaintSize // 设置画笔的宽度为 20 像素
        style = Paint.Style.STROKE // 设置画笔的样式为描边
        strokeCap = Paint.Cap.ROUND // 设置画笔的端点
    }
    private val arcPaint = Paint().apply {
        color = Color.parseColor("#FF336E65")
        isAntiAlias = true //设置抗锯齿
        strokeWidth = defaultPaintSize // 设置画笔的宽度为 20 像素
        style = Paint.Style.STROKE // 设置画笔的样式为描边
        strokeCap = Paint.Cap.ROUND // 设置画笔的端点
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = defaultSize
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = defaultSize
        }
        val size = min(widthSize, heightSize)
        setMeasuredDimension(size, size)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mSize = w
        rectF  = RectF(
            0f + defaultPaintSize,
            0f + defaultPaintSize,
            mSize.toFloat() - defaultPaintSize,
            mSize.toFloat() - defaultPaintSize
        )
    }

    override fun onDraw(canvas: Canvas) {
        //画默认圆环
        val radius = (mSize - defaultPaintSize * 2) / 2
        val cx: Float = (mSize / 2).toFloat()
        val cy: Float = (mSize / 2).toFloat()
        canvas.drawCircle(cx, cy, radius, defaultPaint)

        //画进度圆弧
        canvas.drawArc(rectF, 270f, 360 * progress, false, arcPaint)

    }


    fun setProgress(pro: Float) {
        progress = pro
        invalidate()
    }

}