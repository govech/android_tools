package com.example.myapplicationtest.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt


class CornerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private val paintRect = Paint().apply {
        this.color = Color.parseColor("#FFFFD200")
        //设置抗锯齿
        this.isAntiAlias = true

    }
    private val paintCircle = Paint().apply {
        this.color = Color.WHITE
    }

//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
//        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
//
//        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
//        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
//
//        setMeasuredDimension(500, 500)
//    }

    override fun onDraw(canvas: android.graphics.Canvas) {

        val allwidth = width.toFloat()
        val allheight = height.toFloat()

        canvas.drawRect(0f, 0f, allwidth / 2, allheight / 2, paintRect)
        val rectf = RectF(0f, 0f, allwidth, allheight)
        canvas.drawArc(rectf, 179f, 92f, true, paintCircle)
//        canvas.drawArc(rectf, 0f, 90f, true, paintCircle)
    }


}