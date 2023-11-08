package com.tvink28.rainbowdrum

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat

class VerticalSeekBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val MAX = 100
    }

    private val scalePaint = Paint()
    private val scalePath = Path()
    private val bigThumbPaint = Paint()
    private val smallThumbPaint = Paint()
    private var rainbowDrumView: RainbowDrumView? = null
    private var centerX = 0f
    private var progress = 50
    private var rectF = RectF()
    private var bigThumbRadius = 0f
    private var smallThumbRadius = 0f
    private var thumbPos = 0f
    private var padding = 0f

    init {
        bigThumbRadius = resources.getDimension(R.dimen.big_thumb_radius)
        smallThumbRadius = resources.getDimension(R.dimen.small_thumb_radius)
        padding = resources.getDimension(R.dimen.padding_scale)
        bigThumbPaint.color = Color.RED
        smallThumbPaint.color = ContextCompat.getColor(context, R.color.bg)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        centerX = width / 2f

        rectF.left = (width - padding) / 2f
        rectF.top = padding
        rectF.right = (width + padding) / 2f
        rectF.bottom = height.toFloat() - padding

        scalePaint.shader = LinearGradient(
            0f, padding, 0f, height.toFloat() - padding,
            intArrayOf(Color.RED, Color.TRANSPARENT), null, Shader.TileMode.CLAMP
        )

        thumbPos = (height - 2 * padding) * (1 - progress.toFloat() / MAX) + padding
        scalePath.addRoundRect(
            rectF,
            smallThumbRadius,
            smallThumbRadius,
            Path.Direction.CW
        )
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPath(scalePath, scalePaint)
        canvas.drawCircle(centerX, thumbPos, bigThumbRadius, bigThumbPaint)
        canvas.drawCircle(centerX, thumbPos, smallThumbRadius, smallThumbPaint)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                thumbPos = event.y.coerceIn(padding, height.toFloat() - padding)
                progress = ((1 - thumbPos / height) * MAX).toInt()
                rainbowDrumView?.updateDrumSize(progress, MAX)
                invalidate()
            }
        }
        return true
    }

    fun setRainbowDrumView(rainbowDrumView: RainbowDrumView) {
        this.rainbowDrumView = rainbowDrumView
    }
}