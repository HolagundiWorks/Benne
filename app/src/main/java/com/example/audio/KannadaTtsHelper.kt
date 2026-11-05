package com.example.audio

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class KannadaTtsHelper(context: Context) {
    private var tts: TextToSpeech? = null
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _currentlySpeakingText = MutableStateFlow<String?>(null)
    val currentlySpeakingText: StateFlow<String?> = _currentlySpeakingText.asStateFlow()

    private var kannadaLocaleAvailable = false

    init {
        tts = TextToSpeech(context.applicationContext) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val knLocale = Locale("kn", "IN")
                val langResult = tts?.isLanguageAvailable(knLocale)
                if (langResult == TextToSpeech.LANG_AVAILABLE ||
                    langResult == TextToSpeech.LANG_COUNTRY_AVAILABLE ||
                    langResult == TextToSpeech.LANG_COUNTRY_VAR_AVAILABLE
                ) {
                    tts?.language = knLocale
                    kannadaLocaleAvailable = true
                } else {
                    // Fallback to default locale
                    tts?.language = Locale.getDefault()
                    kannadaLocaleAvailable = false
                    Log.d("KannadaTts", "Kannada TTS locale not fully installed on system, using fallback")
                }

                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _isSpeaking.value = true
                    }

                    override fun onDone(utteranceId: String?) {
                        _isSpeaking.value = false
                        _currentlySpeakingText.value = null
                    }

                    @Deprecated("Deprecated in Java")
                    override fun onError(utteranceId: String?) {
                        _isSpeaking.value = false
                        _currentlySpeakingText.value = null
                    }
                })

                _isReady.value = true
            } else {
                Log.e("KannadaTts", "TTS initialization failed with code: $status")
            }
        }
    }

    fun speak(text: String, speedRate: Float = 0.85f) {
        val engine = tts ?: return
        try {
            engine.stop()
            engine.setSpeechRate(speedRate)
            engine.setPitch(1.0f)

            _currentlySpeakingText.value = text
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "kannada_utterance_${System.currentTimeMillis()}")
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, params, "kannada_utterance")
        } catch (e: Exception) {
            Log.e("KannadaTts", "Error invoking speak: ${e.message}")
            _isSpeaking.value = false
            _currentlySpeakingText.value = null
        }
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
        _currentlySpeakingText.value = null
    }

    fun destroy() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: Exception) {
            // Ignored on teardown
        }
        tts = null
    }
}
