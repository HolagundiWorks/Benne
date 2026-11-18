package com.example.audio

import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.speech.tts.Voice
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

/**
 * Kannada text-to-speech.
 *
 * - Requests made before the engine is ready are queued and played once it is ready.
 * - Picks the highest-quality Kannada voice the device offers (more natural than the default).
 * - Does not read Kannada text with a non-Kannada voice. If no Kannada voice data is installed,
 *   speech is skipped and [kannadaVoiceAvailable] stays false.
 */
class KannadaTtsHelper(context: Context) {
    private var tts: TextToSpeech? = null
    private var engineReady = false
    private var pendingText: String? = null
    private var pendingRate: Float = DEFAULT_RATE

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady.asStateFlow()

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private val _currentlySpeakingText = MutableStateFlow<String?>(null)
    val currentlySpeakingText: StateFlow<String?> = _currentlySpeakingText.asStateFlow()

    private val _kannadaVoiceAvailable = MutableStateFlow(false)
    val kannadaVoiceAvailable: StateFlow<Boolean> = _kannadaVoiceAvailable.asStateFlow()

    private val listener = object : UtteranceProgressListener() {
        override fun onStart(utteranceId: String?) {
            _isSpeaking.value = true
        }

        override fun onDone(utteranceId: String?) {
            clearSpeaking()
        }

        @Deprecated("Deprecated in Java")
        override fun onError(utteranceId: String?) {
            clearSpeaking()
        }

        override fun onError(utteranceId: String?, errorCode: Int) {
            clearSpeaking()
        }
    }

    init {
        tts = TextToSpeech(context.applicationContext) { status -> onEngineInit(status) }
    }

    private fun onEngineInit(status: Int) {
        val engine = tts ?: return
        if (status != TextToSpeech.SUCCESS) {
            Log.e(TAG, "TTS initialization failed with code: $status")
            return
        }

        val kannada = Locale.Builder().setLanguage("kn").setRegion("IN").build()
        val support = engine.isLanguageAvailable(kannada)
        val available = support >= TextToSpeech.LANG_AVAILABLE
        _kannadaVoiceAvailable.value = available

        if (available) {
            engine.language = kannada
            pickBestKannadaVoice(engine)
        } else {
            Log.w(TAG, "Kannada voice data is not installed on this device; Kannada speech is skipped.")
        }

        engine.setOnUtteranceProgressListener(listener)
        engineReady = true
        _isReady.value = true

        // Play whatever was requested while the engine was starting.
        pendingText?.let { speak(it, pendingRate) }
        pendingText = null
    }

    /** Chooses the most natural Kannada voice: highest quality, offline preferred on ties. */
    private fun pickBestKannadaVoice(engine: TextToSpeech) {
        val voices: Set<Voice> = try {
            engine.voices.orEmpty()
        } catch (e: Exception) {
            emptySet()
        }
        val best = voices
            .filter { it.locale.language == "kn" }
            .sortedWith(compareByDescending<Voice> { it.quality }.thenBy { it.isNetworkConnectionRequired })
            .firstOrNull()
        if (best != null) {
            engine.voice = best
            Log.d(TAG, "Using Kannada voice ${best.name} (quality ${best.quality})")
        }
    }

    fun speak(text: String, speedRate: Float = DEFAULT_RATE) {
        val engine = tts
        if (engine == null || !engineReady) {
            pendingText = text
            pendingRate = speedRate
            return
        }
        if (!_kannadaVoiceAvailable.value) {
            Log.w(TAG, "Skipping speech: no Kannada voice installed.")
            return
        }
        try {
            engine.stop()
            engine.setSpeechRate(speedRate)
            engine.setPitch(1.0f)

            _currentlySpeakingText.value = text
            val params = Bundle()
            params.putString(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "kannada_utterance_${System.currentTimeMillis()}")
            engine.speak(text, TextToSpeech.QUEUE_FLUSH, params, "kannada_utterance")
        } catch (e: Exception) {
            Log.e(TAG, "Error invoking speak: ${e.message}")
            clearSpeaking()
        }
    }

    fun stop() {
        pendingText = null
        tts?.stop()
        clearSpeaking()
    }

    fun destroy() {
        try {
            tts?.stop()
            tts?.shutdown()
        } catch (e: Exception) {
            // Ignored on teardown
        }
        tts = null
        engineReady = false
    }

    private fun clearSpeaking() {
        _isSpeaking.value = false
        _currentlySpeakingText.value = null
    }

    private companion object {
        const val TAG = "KannadaTts"
        const val DEFAULT_RATE = 0.9f
    }
}
