package com.example.lowvisionaidsbachelorthesis.components

import com.example.lowvisionaidsbachelorthesis.tflite.Classification

object LastClassification {
    private var last = Classification("", 0.0f)

    fun setLast(classification: Classification){
        last = classification
    }

    fun getLast(): Classification{
        return last
    }
}
