package com.example.mysignature

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var icon: Bitmap? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        icon = BitmapFactory.decodeResource(resources, R.drawable.a_image)

        image_sign.setImageBitmap(icon)

        saveButton.setOnClickListener{
            Log.e("Points: ", signaturePad.points.toString())

            findDifference(BitmapFactory.decodeResource(resources, R.drawable.a_image),signaturePad.signatureBitmap)

            signaturePad.points.forEach {
                Log.e("Points: ", it.x.toString()+" "+it.y.toString())
            }
        }

        clearButton.setOnClickListener{
                signaturePad.clear()
        }
    }

    private fun findDifference(firstImage: Bitmap, secondImage: Bitmap) {
        var diff = 0f
        for (i in 0 until firstImage.width) {
            for (j in 0 until firstImage.height) {
                val pixel = firstImage.getPixel(i, j)

                val pixel2 = secondImage.getPixel(i, j)

                    diff += pixelDiff(pixel, pixel2);

            }
        }
        val maxDiff = 3 * 255 * firstImage.width * firstImage.height
        val maxDiffInPercent:Float =  100 * diff/(maxDiff)
        Log.e("Difference: ", diff.toString())
        Log.e("Value: ", maxDiffInPercent.toString())
        tv_percentage.text = "Difference: "+maxDiffInPercent.toString()
    }

    private fun pixelDiff(rgb1: Int, rgb2: Int): Int {
        val r1 = rgb1 shr 16 and 0xff
        val g1 = rgb1 shr 8 and 0xff
        val b1 = rgb1 and 0xff
        val r2 = rgb2 shr 16 and 0xff
        val g2 = rgb2 shr 8 and 0xff
        val b2 = rgb2 and 0xff
        return Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2)
    }

}