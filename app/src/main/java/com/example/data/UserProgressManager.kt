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

    private val _masteredFlashcardIds = MutableStateFlow(
        prefs.getStringSet("mastered_flashcards", setOf("fc1", "fc2"))?.toSet() ?: setOf("fc1", "fc2")
    )
    val masteredFlashcardIds: StateFlow<Set<String>> = _masteredFlashcardIds.asStateFlow()

    private val _playbackSpeed = MutableStateFlow(prefs.getFloat("audio_playback_speed", 0.85f))
    val playbackSpeed: StateFlow<Float> = _playbackSpeed.asStateFlow()

    private val _notificationsEnabled = MutableStateFlow(prefs.getBoolean("notifications_enabled", false))
    val notificationsEnabled: StateFlow<Boolean> = _notificationsEnabled.asStateFlow()

    private val _reminderHour = MutableStateFlow(prefs.getInt("reminder_hour", 19))
    val reminderHour: StateFlow<Int> = _reminderHour.asStateFlow()

    private val _reminderMinute = MutableStateFlow(prefs.getInt("reminder_minute", 30))
    val reminderMinute: StateFlow<Int> = _reminderMinute.asStateFlow()

    private val _learnerName = MutableStateFlow(prefs.getString("learner_name", "Kannada Learner") ?: "Kannada Learner")
    val learnerName: StateFlow<String> = _learnerName.asStateFlow()

    private fun calculateStreak(): Int {
        val lastDateStr = prefs.getString("last_active_date", null)
        val todayStr = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val currentStreak = prefs.getInt("current_streak", 3)

        if (lastDateStr != null && lastDateStr != todayStr) {
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

    fun toggleMasterFlashcard(cardId: String): Boolean {
        val current = _masteredFlashcardIds.value.toMutableSet()
        val isNowMastered = if (current.contains(cardId)) {
            current.remove(cardId)
            false
        } else {
            current.add(cardId)
            addXp(15)
            true
        }
        _masteredFlashcardIds.value = current
        prefs.edit().putStringSet("mastered_flashcards", current).apply()
        return isNowMastered
    }

    fun setPlaybackSpeed(speed: Float) {
        _playbackSpeed.value = speed
        prefs.edit().putFloat("audio_playback_speed", speed).apply()
    }

    fun setNotificationsEnabled(enabled: Boolean) {
        _notificationsEnabled.value = enabled
        prefs.edit().putBoolean("notifications_enabled", enabled).apply()
    }

    fun setReminderTime(hour: Int, minute: Int) {
        _reminderHour.value = hour
        _reminderMinute.value = minute
        prefs.edit()
            .putInt("reminder_hour", hour)
            .putInt("reminder_minute", minute)
            .apply()
    }

    fun setLearnerName(name: String) {
        val cleaned = name.trim().ifEmpty { "Kannada Learner" }.take(32)
        _learnerName.value = cleaned
        prefs.edit().putString("learner_name", cleaned).apply()
    }

    fun resetAllUserData() {
        prefs.edit().clear().apply()
        _xp.value = 0
        _streakDays.value = 1
        _completedLessons.value = emptySet()
        _favoritePhraseIds.value = emptySet()
        _masteredFlashcardIds.value = emptySet()
        _playbackSpeed.value = 0.85f
        _notificationsEnabled.value = false
        _reminderHour.value = 19
        _reminderMinute.value = 30
        _learnerName.value = "Kannada Learner"
    }
}
