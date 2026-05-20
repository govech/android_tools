package com.sword.tools.view.circle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap


/**
 * 缓存 circleBitmap：
 *
 * circleBitmap 会在第一次创建时缓存，并且只有在 ImageView 的尺寸（宽高）变化时才重新创建，这避免了每次 onDraw() 都重复创建 Bitmap。
 *
 *
 *
 *
 * 优化 paint 对象：
 *
 * paint 被缓存，只需在视图初始化时创建一次，并且标记为抗锯齿模式。
 *
 *
 *
 *
 * 图片大小适配：
 *
 * 当图片的宽度和高度与视图不匹配时，通过计算最小尺寸来确保裁剪成圆形。还避免了无效的 drawable（例如为空或尺寸为零的图片）参与绘制。
 *
 *
 *
 *
 * 内存管理：
 *
 * 在 onDetachedFromWindow() 中释放 circleBitmap，避免内存泄漏。
 * 不在 onDraw() 中调用 Bitmap.recycle()，防止回收发生在绘制过程中。
 *
 *
 *
 *
 * 总结：
 * 性能优化：通过缓存 circleBitmap 和 Paint，减少了不必要的对象创建。
 * 内存管理：合理释放资源，避免内存泄漏和频繁创建大内存对象。
 * 代码简化：避免在每次绘制时都创建新的 Bitmap 和 Paint 对象，减少了代码复杂度。
 *
 */

class CircleImageView(context: Context, attrs: AttributeSet) : AppCompatImageView(context, attrs) {

    private var circleBitmap: Bitmap? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        // 初始化 paint 设置
        paint.isAntiAlias = true
    }

    override fun onDraw(canvas: Canvas) {
        val drawable = drawable
        if (drawable == null || drawable.intrinsicWidth == 0 || drawable.intrinsicHeight == 0) {
            // 如果 drawable 无效或图片为空，则直接返回
            return
        }

        // 如果图片尺寸变化，需要重新创建圆形 Bitmap
        if (circleBitmap == null || circleBitmap?.width != width || circleBitmap?.height != height) {
            circleBitmap = createCircleBitmap(drawable)
        }

        // 绘制圆形图片
        canvas.drawBitmap(circleBitmap!!, 0f, 0f, null)
    }

    // 创建圆形 Bitmap
    private fun createCircleBitmap(drawable: Drawable): Bitmap {
        // 获取 drawable 的宽高
        val bitmap = drawable.toBitmap()

        // 计算裁剪的圆形区域
        val size = Math.min(bitmap.width, bitmap.height)
        val x = (bitmap.width - size) / 2
        val y = (bitmap.height - size) / 2

        // 创建圆形 Bitmap
        val squaredBitmap = Bitmap.createBitmap(bitmap, x, y, size, size)

        // 创建圆形 Bitmap
        val circleBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(circleBitmap)
        val shader = BitmapShader(squaredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        // 设置 paint 使用圆形 Shader
        paint.shader = shader
        val radius = size / 2f

        // 绘制圆形
        canvas.drawCircle(radius, radius, radius, paint)

        // 回收原始的 squaredBitmap
        squaredBitmap.recycle()

        return circleBitmap
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        // 在视图被销毁时回收圆形 Bitmap
        circleBitmap?.recycle()
        circleBitmap = null
    }
}
