package com.jessy.foodmap

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.annotation.SuppressLint;
@SuppressLint("AppCompatCustomView")

class CircularPictureView : ImageView {
    private var mRadius = 0 //图片的半径
    private lateinit var paint: Paint //画笔对象延迟加载   在使用的时候进行加载
    private var mScale = 0F //缩放比列

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //圆形的图片 我们应该让他的宽高是相等的
        val size = Math.min(measuredWidth, measuredHeight)
        mRadius = size.div(2)
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        paint = Paint()
        val bitmap = drawableToBitmap(drawable)
        val bitmapShader = bitmap?.let {
            BitmapShader(it,
                Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP)
        }
        mScale = bitmap?.height?.let { Math.min(it, bitmap?.width) }?.let { mRadius.times(2.0).div(it).toFloat() }!!
        val matrix = Matrix()
        matrix.setScale(mScale, mScale)
        bitmapShader!!.setLocalMatrix(matrix)

        paint.shader = bitmapShader
        //话圆形 质地昂中心点坐标 半径 画笔
        canvas?.drawCircle(mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(), paint)
        /*
        *
        *  if (canvas != null) {
            canvas.drawCircle(mRadius.toFloat(), mRadius.toFloat(), mRadius.toFloat(),paint)
        }
        * */

    }

    /**
     * drawable 转成Bitmap的方法
     */
    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val width = drawable?.intrinsicWidth
        val height = drawable?.intrinsicHeight
        val bitmap = width?.let { height?.let { it1 -> Bitmap.createBitmap(it, it1, Bitmap.Config.ARGB_8888) } }
        val canvas = bitmap?.let { Canvas(it) }
        width?.let { height?.let { it1 -> drawable.setBounds(0, 0, it, it1) } }
        if (canvas != null) {
            drawable?.draw(canvas)
        }
        return bitmap
    }
}