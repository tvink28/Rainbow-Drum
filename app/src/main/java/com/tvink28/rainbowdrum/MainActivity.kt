package com.tvink28.rainbowdrum

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.transform.RoundedCornersTransformation


class MainActivity : AppCompatActivity() {

    companion object {
        private const val DOG = "dog"
        private const val PARIS = "paris,girl/all"
        private const val RIO = "brazil,rio"
        private const val URL = "https://loremflickr.com/320/240/"
    }

    private lateinit var rainbowDrumView: RainbowDrumView
    private lateinit var colorTextView: ColorTextView
    private lateinit var arrowView: ArrowView
    private lateinit var startBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var loadImgView: ImageView
    private lateinit var seekBar: VerticalSeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rainbowDrumView = findViewById(R.id.rainbowDrum)
        colorTextView = findViewById(R.id.colorText)
        startBtn = findViewById(R.id.startBtn)
        resetBtn = findViewById(R.id.resetBtn)
        loadImgView = findViewById(R.id.loadImg)
        arrowView = findViewById(R.id.arrow)
        seekBar = findViewById(R.id.seekBar)

        seekBar.setRainbowDrumView(rainbowDrumView)

        startBtn.setOnClickListener { startButtonClick() }
        resetBtn.setOnClickListener { resetButtonClick() }
    }

    private fun loadImage(keyword: String) {
        loadImgView.load(URL + keyword) {
            crossfade(true)
            error(R.drawable.error_icon)
            transformations(RoundedCornersTransformation(10f))
        }
    }

    private fun startButtonClick() {
        rainbowDrumView.startSpinning { result ->
            when (result) {
                0 -> colorTextView.setColor(Color.MAGENTA)
                1 -> loadImage(DOG)
                2 -> colorTextView.setColor(Color.CYAN)
                3 -> loadImage(PARIS)
                4 -> colorTextView.setColor(Color.YELLOW)
                5 -> loadImage(RIO)
                6 -> colorTextView.setColor(Color.RED)
            }
        }
    }

    private fun resetButtonClick() {
        loadImgView.load(null)
        colorTextView.clear()
    }
}