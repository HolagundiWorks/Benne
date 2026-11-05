package com.example.data

import android.content.Context
import android.content.SharedPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UserProgressManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("sarala_kannada_prefs", Context.MODE_PRIVATE)

    private val _xp = MutableStateFlow(prefs.getInt("user_xp", 120))
    val xp: StateFlow<Int> = _xp.asStateFlow()

    private val _streakDays = MutableStateFlow(calculateStreak())
    val streakDays: StateFlow<Int> = _streakDays.asStateFlow()

    private val _completedLessons = MutableStateFlow(
        prefs.getStringSet("completed_lessons", setOf("lesson_1"))?.toSet() ?: setOf("lesson_1")
    )
    val completedLessons: StateFlow<Set<String>> = _completedLessons.asStateFlow()

    private val _favoritePhraseIds = MutableStateFlow(
        prefs.getStringSet("favorite_phrases", setOf("d1", "d14", "d25"))?.toSet() ?: setOf("d1", "d14", "d25")
    )
    val favoritePhraseIds: StateFlow<Set<String>> = _favoritePhraseIds.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(prefs.getFloat("audio_playback_speed", 0.85f))
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private fun calculateStreak(): Int {
        val lastDateStr = prefs.getString("last_active_date", null)
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        var currentStreak = prefs.getInt("current_streak", 3)

        if (lastDateStr != null && lastDateStr != todayStr) {
            // Check if last day was yesterday or earlier
            prefs.edit().putString("last_active_date", todayStr).apply()
        } else if (lastDateStr == null) {
            prefs.edit().putString("last_active_date", todayStr).apply()
        }
        return currentStreak
    }

    fun addXp(points: Int) {
        val newXp = _xp.value + points
        _xp.value = newXp
        prefs.edit().putInt("user_xp", newXp).apply()
    }

    fun completeLesson(lessonId: String) {
        val updated = _completedLessons.value + lessonId
        _completedLessons.value = updated
        prefs.edit().putStringSet("completed_lessons", updated).apply()
        addXp(50)
    }

    fun toggleFavoritePhrase(phraseId: String) {
        val current = _favoritePhraseIds.value.toMutableSet()
        if (current.contains(phraseId)) {
            current.remove(phraseId)
        } else {
            current.add(phraseId)
        }
        _favoritePhraseIds.value = current
        prefs.edit().putStringSet("favorite_phrases", current).apply()
    }

    fun setPlaybackSpeed(speed: Float) {
        _playbackSpeed.value = speed
        prefs.edit().putFloat("audio_playback_speed", speed).apply()
    }
}
