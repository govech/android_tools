package com.example.myapplicationtest.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.ktx.dp2px
import kotlinx.coroutines.NonDisposableHandle.parent

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
        val parentWidth = parent.measuredWidth
        val itemWidth = view.layoutParams.width
        val lastPosition = parent.adapter?.itemCount?.minus(1) ?: 0
        when (parent.getChildAdapterPosition(view)) {
            0 -> {
                outRect.left = parentWidth / 2 - itemWidth / 2
                outRect.right = dividerWidth
            }

            lastPosition -> {
                outRect.right = parentWidth / 2 - itemWidth / 2
            }

            else -> {
                outRect.right = dividerWidth
            }
        }

    }

    override fun onDrawOver(c: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, recyclerView, state)
//        val parentWidth = recyclerView.measuredWidth
//        val childCount = recyclerView.childCount
//        for (i in 0 until childCount) {
//            val child = recyclerView.getChildAt(i)
//            val dividerTop = child.top + child.height / 2 - dividerHeight / 2
//            val dividerBottom = dividerTop + dividerHeight
//            var dividerLeft = child.left - dividerWidth
//            var dividerRight = child.left
//            if (0 == i) {
//                dividerLeft = 0
//                dividerRight = child.left
//            }
//            if (childCount - 1 == i) {
//                dividerRight = parentWidth
//            }
//            c.drawRect(
//                dividerLeft.toFloat(),
//                dividerTop.toFloat(), dividerRight.toFloat(), dividerBottom.toFloat(), dividerPaint
//            )
//
//        }
    }

    override fun onDraw(c: Canvas, recyclerView: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, recyclerView, state)
        val parentWidth = recyclerView.measuredWidth
        val childCount = recyclerView.childCount
        for (i in 0 until childCount) {
            val child = recyclerView.getChildAt(i)
            val dividerTop = child.top + child.height / 2 - dividerHeight / 2
            val dividerBottom = dividerTop + dividerHeight
            var dividerLeft = child.left - dividerWidth
            var dividerRight = child.left
            if (0 == i) {
                dividerLeft = 0
                dividerRight = child.left
            }
            if (childCount - 1 == i) {
                dividerRight = parentWidth
            }
            c.drawRect(
                dividerLeft.toFloat(),
                dividerTop.toFloat(), dividerRight.toFloat(), dividerBottom.toFloat(), dividerPaint
            )

        }
    }

}