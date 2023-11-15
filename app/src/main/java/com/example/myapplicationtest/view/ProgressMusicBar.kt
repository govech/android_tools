package com.example.myapplicationtest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.myapplicationtest.ktx.px

class ProgressMusicBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var progress = 30f
    private var maxSize = 100
    private var firstColor = Color.parseColor("#45FFFFFF")
    private var secondColor = Color.parseColor("#33FFFFFF")
    private var circleColor = Color.WHITE
    private var strokeWidth = 2.px.toFloat()
    private var thumbRadius = 4.px.toFloat()

    /**
     * 由于一般需要进度条两边为圆头所以需要先圈定画笔位置
     */
    private var startX = 0f
    private var endX = 0f
    private val firstPaint = Paint().apply {
        color = firstColor
        isAntiAlias = true
        strokeWidth = strokeWidth
        strokeCap = Paint.Cap.ROUND
    }
    private val secondPaint = Paint().apply {
        color = secondColor
        isAntiAlias = true
        strokeWidth = strokeWidth
        strokeCap = Paint.Cap.ROUND
    }
    private val circlePaint = Paint().apply {
        color = circleColor
        isAntiAlias = true
        strokeWidth = thumbRadius * 2
        strokeCap = Paint.Cap.ROUND
    }

    init {
        firstPaint.strokeWidth = strokeWidth
        secondPaint.strokeWidth = strokeWidth
        circlePaint.strokeWidth = thumbRadius * 2

    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = MeasureSpec.getSize(heightMeasureSpec)
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = (thumbRadius * 2).toInt()
        }
        setMeasuredDimension(widthSize, heightSize)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        startX = strokeWidth / 2
        endX = width - strokeWidth / 2
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerY = (height / 2).toFloat()
        canvas.drawLine(
            startX,
            centerY,
            endX,
            centerY,
            secondPaint
        )

        val progressWidth = ((endX - startX) * progress / maxSize) + startX

        canvas.drawLine(
            startX,
            centerY,
            progressWidth,
            centerY,
            firstPaint
        )
        canvas.drawPoint(progressWidth, centerY, circlePaint)
    }


    fun setProgress(currentProgress:Int){
        progress = currentProgress.toFloat()
        invalidate()
    }
}