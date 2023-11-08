package com.tvink28.rainbowdrum

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View


class ArrowView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    companion object {
        private const val TRIANGLE_SIZE = 0.05f
    }

    private val arrowPath = Path()
    private val arrowPaint = Paint()
    private var centerX = 0f
    private var centerY = 0f
    private var lineLeft = 0f
    private var lineRight = 0f
    private var triangleSize = 0f

    init {
        arrowPaint.color = Color.BLACK
        arrowPaint.style = Paint.Style.FILL
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        triangleSize = width * TRIANGLE_SIZE
        centerX = width / 2f
        centerY = triangleSize * 2
        lineLeft = centerX - triangleSize
        lineRight = centerX + triangleSize
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        arrowPath.moveTo(centerX, 0f)
        arrowPath.lineTo(lineLeft, centerY)
        arrowPath.lineTo(lineRight, centerY)
        arrowPath.close()
        canvas.drawPath(arrowPath, arrowPaint)
    }
}