package com.example.myapplicationtest

import android.os.Bundle
import android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplicationtest.base.BaseActivity
import com.example.myapplicationtest.base.QuickAdapter
import com.example.myapplicationtest.databinding.ActivityRvhorizontalBinding
import com.example.myapplicationtest.ktx.binding
import com.example.myapplicationtest.view.SimpleItemDecoration
import kotlin.math.abs
import kotlin.math.max

class RvhorizontalActivity : BaseActivity() {
    private val maxFactor = .45F
    private val mBinding by binding(ActivityRvhorizontalBinding::inflate)
    private lateinit var mManager: LinearLayoutManager
    private val mSnapHelper = PagerSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initRv()
    }

    private fun initRv() {
        mManager = LinearLayoutManager(
            this@RvhorizontalActivity,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        mBinding.rvAnim.apply {
            layoutManager = mManager
//            mSnapHelper.attachToRecyclerView(this)
            addItemDecoration(SimpleItemDecoration(this@RvhorizontalActivity))
            adapter = QuickAdapter(
                this@RvhorizontalActivity,
                R.layout.item_rv_anim,
                mutableListOf(
                    "1",
                    "2",
                    "3",
                    "4",
                    "5",
                    "6",
                    "7",
                    "8",
                    "9",
                    "10",
                    "11",
                    "12",
                    "13",
                    "14",
                    "15",
                    "16"
                )
            ) { _, _ -> }


            addOnScrollListener(listener)
        }
    }


    private val listener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            /**
             * 这个方法在滚动状态发生改变时触发，参数 newState 表示新的滚动状态。滚动状态有以下三种：
             * RecyclerView.SCROLL_STATE_IDLE：表示RecyclerView当前没有在滚动（已静止或尚未滚动）。
             * RecyclerView.SCROLL_STATE_DRAGGING：表示RecyclerView正在被外部拖拽,一般为用户正在用手指滚动。
             * RecyclerView.SCROLL_STATE_SETTLING：RecyclerView在由于某种原因（如自动滚动、惯性滚动）正在滚动
             */
            if (newState == SCROLL_STATE_IDLE) {
//                changeSnapView()
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            // 这个方法在RecyclerView滚动时会被调用，其中 dx 和 dy 表示在x和y方向上滚动的像素距离。
            // 注意这个距离并不一定等于RecyclerView当前滚动的总距离，因为RecyclerView可能在滚动过程中被回收或重新布局
            val first = mManager.findFirstVisibleItemPosition()
            val last = mManager.findLastVisibleItemPosition()
            val parentCenter = recyclerView.width / 2F
            for (i in first..last) {
                setItemTransform(i, parentCenter)
            }
//            changeSnapView()

        }
    }

    /**
     * 对item进行各种变换
     * 目前是缩放与透明度变换
     */
    private fun setItemTransform(position: Int, parentCenter: Float) {
        mManager.findViewByPosition(position)?.run {
            val factor = calculationViewFactor(left.toFloat(), width.toFloat(), parentCenter)
            val scale = 1 + factor
            scaleX = scale
            scaleY = scale
            alpha = 1 - maxFactor + factor
        }
    }

    /**
     * 计算当前item的缩放与透明度系数
     * item的中心离recyclerView的中心越远，系数越小（负相关）
     */
    private fun calculationViewFactor(left: Float, width: Float, parentCenter: Float): Float {
        val viewCenter = left + width / 2
        val distance = abs(viewCenter - parentCenter) / width
        return max(0F, (1F - distance) * maxFactor)
    }

    /**
     * 修改当前居中的item，把当前等级回调给外界
     */
//    private fun changeSnapView() {
//        mSnapHelper.findSnapView(mManager)?.let {
//            mManager.getPosition(it).let { position ->
//                if (lastPosition != position) {
//                    lastPosition = position
//                    levelListener?.onLevelChange(position)
//                }
//            }
//        }
//    }


}