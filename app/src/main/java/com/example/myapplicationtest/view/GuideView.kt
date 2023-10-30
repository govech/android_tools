package com.example.myapplicationtest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import dip2px

class GuideView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var rectf: RectF? = null
    private val mPaint = Paint().apply {
        color = Color.parseColor("#ffffff")
        isAntiAlias = true
        // 设置Mode为XOR
        xfermode = PorterDuffXfermode(PorterDuff.Mode.XOR)
    }

    init {
        // 关闭硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }


    fun setContentLocation(x: Int, y: Int, w: Int, h: Int) {
        rectf = RectF(
            x.toFloat() - dip2px(4f),
            y.toFloat() - dip2px(4f),
            (x + w).toFloat() + dip2px(4f),
            (y + h).toFloat() + dip2px(4f)
        )
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawColor(Color.parseColor("#80000000"))
        rectf?.let { canvas.drawRoundRect(it, dip2px(18).toFloat(), dip2px(18).toFloat(), mPaint) }
    }

}