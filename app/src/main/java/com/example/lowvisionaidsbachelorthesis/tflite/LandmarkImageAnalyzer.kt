package com.example.lowvisionaidsbachelorthesis.tflite

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class LandmarkImageAnalyzer(
    private val classifier: LandmarkClassifier,
    private val onResults: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {
    private var frameSkipCounter = 0

    private fun resizeImage(image: Bitmap, targetWidth: Int = 224, targetHeight: Int = 224): Bitmap {
        val width = image.width
        val height = image.height
        val aspectRatio = width.toFloat() / height.toFloat()

        val newWidth: Int
        val newHeight: Int

        if (width > height) {
            newWidth = targetWidth
            newHeight = (targetWidth / aspectRatio).toInt()
        } else {
            newHeight = targetHeight
            newWidth = (targetHeight * aspectRatio).toInt()
        }

        val scaledBitmap = Bitmap.createScaledBitmap(image, newWidth, newHeight, true)
        val bitmapWithPadding = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(bitmapWithPadding)
        val left = (targetWidth - newWidth) / 2
        val top = (targetHeight - newHeight) / 2

        canvas.drawBitmap(scaledBitmap, left.toFloat(), top.toFloat(), null)
        return bitmapWithPadding
    }

    override fun analyze(image: ImageProxy) {
        if(frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image.toBitmap()
            val resizedImage = resizeImage(bitmap, 224, 224)

            val results = classifier.classify(resizedImage, rotationDegrees)

            println("RESULTS $results")
            onResults(results)
        } else onResults(emptyList())
        frameSkipCounter++

        image.close()
    }
}