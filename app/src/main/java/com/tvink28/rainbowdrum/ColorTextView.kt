package com.tvink28.rainbowdrum

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class ColorTextView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint = Paint()
    private var text: String = ""
    private var centerX = 0f
    private var centerY = 0f

    init {
        paint.textSize = resources.getDimension(R.dimen.text_size)
        paint.textAlign = Paint.Align.CENTER
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        centerX = width / 2f
        centerY = height / 2f
    }

    fun setColor(color: Int) {
        this.text = resources.getString(R.string.painted_img)
        paint.color = color
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(text, centerX, centerY, paint)
    }

    fun clear() {
        text = ""
        paint.color = 0
        invalidate()
    }
}