package com.tvink28.rainbowdrum

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import kotlin.math.min

class RainbowDrumView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    companion object {
        private const val MIN_DURATION = 600
        private const val MAX_DURATION = 4000
        private const val FULL_CIRCLE = 360f
        private const val CORRECTED_ANGLE = 90f
        private const val ROTATION_DURATION: Long = 2000
        private const val ROTATION = "rotation"
    }

    private val colors = arrayOf(
        Color.RED,
        ContextCompat.getColor(context, R.color.orange),
        Color.YELLOW,
        Color.GREEN,
        Color.CYAN,
        Color.BLUE,
        Color.MAGENTA
    )

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var rotateAnimator: ObjectAnimator
    private val drumPaint = Paint()
    private var currentAngle = 0f
    private var centerX = 0f
    private var centerY = 0f
    private val sweepAngle = FULL_CIRCLE / colors.size
    private var startAngle = 0f
    private var sizeDrum = 0f
    private var halfSize = 0f
    private var radius = 0f

    init {
        drumPaint.isAntiAlias = true
        drumPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = width / 2f
        centerY = height / 2f
        halfSize = min(width, height) / 2f
        sizeDrum = 0.5f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        radius = halfSize * sizeDrum
        for (i in colors.indices) {
            startAngle = CORRECTED_ANGLE + i * sweepAngle
            drumPaint.color = colors[i]
            canvas.drawArc(
                centerX - radius, centerY - radius,
                centerX + radius, centerY + radius,
                startAngle, sweepAngle, true, drumPaint
            )
        }
    }

    fun startSpinning(onAnimationComplete: (Int) -> Unit) {
        val randomDuration = (MIN_DURATION..MAX_DURATION).random()
        rotateAnimator =
            ObjectAnimator.ofFloat(
                this,
                ROTATION,
                currentAngle,
                currentAngle + FULL_CIRCLE
            )
        rotateAnimator.duration = ROTATION_DURATION
        rotateAnimator.repeatCount = ObjectAnimator.INFINITE
        rotateAnimator.interpolator = LinearInterpolator()
        rotateAnimator.start()

        handler.postDelayed({
            rotateAnimator.cancel()
            currentAngle = rotation
            invalidate()
            onAnimationComplete(getNameColor(currentAngle))
        }, randomDuration.toLong())
    }

    fun updateDrumSize(progress: Int, max: Int) {
        sizeDrum = progress.toFloat() / max
        invalidate()
    }

    private fun getNameColor(currentAngle: Float): Int {
        val angleOnColors = FULL_CIRCLE / colors.size
        val normalizedAngle = currentAngle % FULL_CIRCLE
        return (normalizedAngle / angleOnColors).toInt()
    }
}