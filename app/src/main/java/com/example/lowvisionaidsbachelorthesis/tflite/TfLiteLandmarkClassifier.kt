package com.example.lowvisionaidsbachelorthesis.tflite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.Size
import android.view.Surface
import kotlinx.coroutines.awaitAll
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.task.core.BaseOptions
import org.tensorflow.lite.task.core.vision.ImageProcessingOptions
import org.tensorflow.lite.task.vision.classifier.ImageClassifier

class TfLiteLandmarkClassifier(
    private val context: Context,
    private val threshold: Float = 0.95f,
    private val maxResults: Int = 1
): LandmarkClassifier {

    private var classifier: ImageClassifier? = null

    private fun setupClassifier() {
        val baseOptions = BaseOptions.builder()
            .setNumThreads(2)
            .build()
        val options = ImageClassifier.ImageClassifierOptions.builder()
            .setBaseOptions(baseOptions)
            .setMaxResults(maxResults)
            .setScoreThreshold(threshold)
            .build()

        try {
            classifier = ImageClassifier.createFromFileAndOptions(
                context,
                "model_with_metadata (1).tflite",
                options
            )
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

     override fun classify(bitmap: Bitmap, rotation: Int): List<Classification> {
        if(classifier == null) {
            setupClassifier()
        }

         val resizedBitmap = Bitmap.createScaledBitmap(bitmap, 224, 224, true)
         val tensorImage = TensorImage.fromBitmap(resizedBitmap)

         val imageProcessingOptions = ImageProcessingOptions.builder()
             .setOrientation(getOrientationFromRotation(rotation))
             .build()

         val results = classifier?.classify(tensorImage, imageProcessingOptions)

        return results?.flatMap { classifications ->
            classifications.categories.map { category ->
                Classification(
                    name = category.label,
                    score = category.score
                )
            }
        }?.distinctBy { it.name }?.sortedBy { it.score } ?: emptyList()
    }

    private fun getOrientationFromRotation(rotation: Int): ImageProcessingOptions.Orientation {
        return when(rotation) {
            Surface.ROTATION_270 -> ImageProcessingOptions.Orientation.BOTTOM_RIGHT
            Surface.ROTATION_90 -> ImageProcessingOptions.Orientation.TOP_LEFT
            Surface.ROTATION_180 -> ImageProcessingOptions.Orientation.RIGHT_BOTTOM
            else -> ImageProcessingOptions.Orientation.RIGHT_TOP
        }
    }

    // Function to normalize the bitmap according to model's metadata
    private fun normalizeBitmap(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val normalizedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(normalizedBitmap)
        val paint = Paint()

        val colorMatrix = ColorMatrix()
        colorMatrix.setScale(1f / 127.5f, 1f / 127.5f, 1f / 127.5f, 1f) // Normalization
        paint.colorFilter = ColorMatrixColorFilter(colorMatrix)

        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        return normalizedBitmap
    }
}