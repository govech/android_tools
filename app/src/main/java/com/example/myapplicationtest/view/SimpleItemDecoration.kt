package com.example.myapplicationtest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.ktx.dp2px

class SimpleItemDecoration(
    val context: Context,
    val dividerHeight: Int = context.dp2px(4),
    val dividerWidth: Int = context.dp2px(40),
    val color: Int = Color.BLACK
) :
    RecyclerView.ItemDecoration() {


    private val dividerPaint: Paint = Paint()

    init {
        dividerPaint.color = color
        dividerPaint.style = Paint.Style.FILL
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.apply {
            right = dividerWidth
        }
    }

    override fun onDrawOver(c: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, recyclerView, state)

        val childCount = recyclerView.childCount
        for (i in 0 until childCount) {
            val child = recyclerView.getChildAt(i)
            val dividerTop = child.top + child.height / 2 - dividerHeight / 2
            val dividerBottom = dividerTop + dividerHeight
            val dividerLeft = child.left - dividerWidth
            val dividerRight = child.left
            c.drawRect(
                dividerLeft.toFloat(),
                dividerTop.toFloat(), dividerRight.toFloat(), dividerBottom.toFloat(), dividerPaint
            )

        }
    }
}