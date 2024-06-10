package com.example.lowvisionaidsbachelorthesis

import android.content.Context
import android.speech.tts.TextToSpeech
import android.widget.Toast
import kotlinx.coroutines.*
import java.util.*


class TTS(context: Context) : TextToSpeech.OnInitListener {
    private var textToSpeech: TextToSpeech = TextToSpeech(context, this)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        textToSpeech = TextToSpeech(context, this)
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val defaultLocale = Locale.getDefault()
            val result = textToSpeech.setLanguage(defaultLocale)
        }
    }

    fun speak(text: String, delayMillis: Long = 5000) {
        coroutineScope.launch {
            delay(delayMillis) // Delay for the specified time (default is 5000 milliseconds)
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    fun shutdown() {
        if (textToSpeech.isSpeaking) {
            textToSpeech.stop()
        }
        textToSpeech.shutdown()
    }
}
