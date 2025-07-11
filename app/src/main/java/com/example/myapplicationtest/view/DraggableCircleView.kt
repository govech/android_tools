package com.example.myapplicationtest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import com.example.myapplicationtest.R
import kotlin.math.hypot
import kotlin.math.max
import kotlin.math.min

class DraggableCircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_RADIUS_DP = 50f
        private const val DEFAULT_COLOR = Color.RED
    }

    private val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = DEFAULT_COLOR
    }

    private var circleX: Float = 0f
    private var circleY: Float = 0f
    private var radiusPx: Float = 0f
    private var isDragging: Boolean = false
    private var touchSlop: Float = 0f
    private var minSizePx: Float = 0f

    // 自定义属性
    private var circleColor: Int = DEFAULT_COLOR
    private var circleRadiusDp: Float = DEFAULT_RADIUS_DP


    init {
        setupAttributes(attrs)
        setupView()
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        context.obtainStyledAttributes(attrs, R.styleable.DraggableCircleView).apply {
            try {
                circleColor = getColor(R.styleable.DraggableCircleView_circleColor, DEFAULT_COLOR)
                circleRadiusDp = getDimension(
                    R.styleable.DraggableCircleView_circleRadius,
                    DEFAULT_RADIUS_DP
                )
            } finally {
                recycle()
            }
        }
    }

    private fun setupView() {
        // 转换半径为像素
        radiusPx = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            circleRadiusDp,
            resources.displayMetrics
        )

        paint.color = circleColor
        paint.setShadowLayer(15f, 0f, 0f, Color.parseColor("#55000000"))
        setLayerType(LAYER_TYPE_SOFTWARE, null)

        // 计算最小尺寸
        minSizePx = radiusPx * 2

        // 获取触摸阈值
        touchSlop = ViewConfiguration.get(context).scaledTouchSlop.toFloat()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val defaultWidth = (2 * radiusPx + paddingLeft + paddingRight).toInt()
        val defaultHeight = (2 * radiusPx + paddingTop + paddingBottom).toInt()

        // 当 wrap_content 且没有明确设置大小时，使用默认大小
        val measuredWidth = when {
            widthMode == MeasureSpec.EXACTLY -> {
                if (widthSize < minSizePx) {
                    minSizePx.toInt()
                } else {
                    widthSize
                }
            }

            widthMode == MeasureSpec.AT_MOST -> min(
                defaultWidth,
                widthSize
            ).coerceAtLeast(minSizePx.toInt())

            else -> max(defaultWidth, minSizePx.toInt()) // UNSPECIFIED
        }

        val measuredHeight = when {
            heightMode == MeasureSpec.EXACTLY -> {
                if (heightSize < minSizePx) {
                    minSizePx.toInt()
                } else {
                    heightSize
                }
            }

            heightMode == MeasureSpec.AT_MOST -> min(defaultHeight, heightSize).coerceAtLeast(
                minSizePx.toInt()
            )

            else -> max(defaultHeight, minSizePx.toInt()) // UNSPECIFIED
        }

        setMeasuredDimension(measuredWidth, measuredHeight)

    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)


        // 更新圆心位置为视图中心
        circleX = (w / 2f).coerceIn(radiusPx, w - radiusPx)
        circleY = (h / 2f).coerceIn(radiusPx, h - radiusPx)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 绘制圆球
        canvas.drawCircle(circleX, circleY, radiusPx, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> handleDownEvent(event)
            MotionEvent.ACTION_MOVE -> handleMoveEvent(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> handleUpEvent()
        }
        return true
    }

    private fun handleDownEvent(event: MotionEvent) {
        if (isTouchInCircle(event.x, event.y)) {
            isDragging = true
            paint.alpha = 200 // 半透明效果表示按下状态
            invalidate()
        }
    }

    private fun handleMoveEvent(event: MotionEvent) {
        if (isDragging) {
            // 确保圆在边界内
            circleX = event.x.coerceIn(radiusPx, width - radiusPx)
            circleY = event.y.coerceIn(radiusPx, height - radiusPx)
            invalidate()
        }
    }

    private fun handleUpEvent() {
        if (isDragging) {
            isDragging = false
            paint.alpha = 255 // 恢复不透明度
            invalidate()
        }
    }

    /** 检查触摸点是否在圆内（考虑触摸阈值） */
    private fun isTouchInCircle(x: Float, y: Float): Boolean {
        val distance = hypot(x - circleX, y - circleY)
        return distance <= (radiusPx + touchSlop)
    }

    // 设置圆球位置的方法（可选）
    fun setCirclePosition(x: Float, y: Float) {
        circleX = x.coerceIn(radiusPx, width - radiusPx)
        circleY = y.coerceIn(radiusPx, height - radiusPx)
        invalidate()
    }
}