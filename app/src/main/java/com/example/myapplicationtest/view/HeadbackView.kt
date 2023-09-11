package com.example.myapplicationtest.view

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.myapplicationtest.R


class HeadbackView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {


    private var backgroundColor = Color.BLACK

    private val paintRect = Paint().apply {
        this.color = backgroundColor
        this.isAntiAlias = true
    }


    private var roundedCorner = 2
    private var cornerRadius = 0

    lateinit var rect: RectF
    private val path = Path()

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeadbackView)
            roundedCorner = typedArray.getInt(R.styleable.HeadbackView_roundedCorner, 0)
            cornerRadius =
                typedArray.getDimensionPixelSize(R.styleable.HeadbackView_cornerRadius, 0)
            backgroundColor =
                typedArray.getColor(R.styleable.HeadbackView_backgroundColor, Color.BLACK)
            paintRect.color = backgroundColor
            typedArray.recycle()
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onDraw(canvas: android.graphics.Canvas) {
        super.onDraw(canvas)
        val allwidth = width.toFloat()
        val allheight = height.toFloat()
        rect = RectF(0f, 0f, allwidth, allheight)
        val radii = when (roundedCorner) {
            0 -> {
                floatArrayOf(
                    cornerRadius.toFloat(),
                    cornerRadius.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat()
                )
            }

            1 -> {
                floatArrayOf(
                    0.toFloat(),
                    0.toFloat(),
                    cornerRadius.toFloat(),
                    cornerRadius.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat()
                )
            }

            2 -> {
                floatArrayOf(
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    cornerRadius.toFloat(),
                    cornerRadius.toFloat(),
                    0.toFloat(),
                    0.toFloat()
                )

            }

            3 -> {
                floatArrayOf(
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    cornerRadius.toFloat(),
                    cornerRadius.toFloat()
                )
            }

            else -> {
                floatArrayOf(
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat(),
                    0.toFloat()
                )
            }
        }



        path.addRoundRect(rect, radii, Path.Direction.CW)
        canvas.drawPath(path, paintRect)

    }


    private fun px2dp(px: Float): Float {
        val scale = resources.displayMetrics.density
        return px / scale + 0.5f
    }

}