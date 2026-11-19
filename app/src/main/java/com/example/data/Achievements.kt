package com.example.data

import com.example.R

/**
 * Achievement rules for the Benne badge set (see assets/badges/IMPLEMENTATION.md).
 *
 * Streak and points badges are derived from progress the app already stores (streak days, XP),
 * so nothing new is persisted and progress works offline.
 *
 * Speed badges need measured response times. The app does not record response times yet, so the
 * speed badges stay locked with an explicit note rather than guessing at accuracy or speed.
 */
enum class AchievementCategory(val label: String) {
    STREAK("Streak"),
    POINTS("Points"),
    SPEED("Speed")
}

data class Achievement(
    val id: String,
    val category: AchievementCategory,
    val title: String,
    /** Human-readable unlock rule, shown on locked badges. */
    val rule: String,
    /** Returns true when unlocked. Null means "not measurable yet" (speed). */
    val check: ((AchievementInputs) -> Boolean)?
)

data class AchievementInputs(val streakDays: Int, val xp: Int)

object Achievements {
    val all: List<Achievement> = listOf(
        Achievement("streak_3", AchievementCategory.STREAK, "3-day streak", "Study 3 days in a row") { it.streakDays >= 3 },
        Achievement("streak_7", AchievementCategory.STREAK, "7-day streak", "Study 7 days in a row") { it.streakDays >= 7 },
        Achievement("streak_25", AchievementCategory.STREAK, "25-day streak", "Study 25 days in a row") { it.streakDays >= 25 },
        Achievement("streak_50", AchievementCategory.STREAK, "50-day streak", "Study 50 days in a row") { it.streakDays >= 50 },
        Achievement("streak_100", AchievementCategory.STREAK, "100-day streak", "Study 100 days in a row") { it.streakDays >= 100 },
        Achievement("points_100", AchievementCategory.POINTS, "100 points", "Earn 100 XP") { it.xp >= 100 },
        Achievement("points_500", AchievementCategory.POINTS, "500 points", "Earn 500 XP") { it.xp >= 500 },
        Achievement("points_1000", AchievementCategory.POINTS, "1,000 points", "Earn 1,000 XP") { it.xp >= 1_000 },
        Achievement("points_5000", AchievementCategory.POINTS, "5,000 points", "Earn 5,000 XP") { it.xp >= 5_000 },
        Achievement("points_10000", AchievementCategory.POINTS, "10,000 points", "Earn 10,000 XP") { it.xp >= 10_000 },
        Achievement("speed_steady", AchievementCategory.SPEED, "Steady", "Needs response-time tracking", null),
        Achievement("speed_quick", AchievementCategory.SPEED, "Quick", "Needs response-time tracking", null),
        Achievement("speed_fast", AchievementCategory.SPEED, "Fast", "Needs response-time tracking", null),
        Achievement("speed_super", AchievementCategory.SPEED, "Super", "Needs response-time tracking", null),
        Achievement("speed_master", AchievementCategory.SPEED, "Master", "Needs response-time tracking", null)
    )

    /** Kit artwork for each achievement id (explicit mapping, no reflection). */
    fun artRes(id: String): Int? = when (id) {
        "streak_3" -> R.drawable.streak_3
        "streak_7" -> R.drawable.streak_7
        "streak_25" -> R.drawable.streak_25
        "streak_50" -> R.drawable.streak_50
        "streak_100" -> R.drawable.streak_100
        "points_100" -> R.drawable.points_100
        "points_500" -> R.drawable.points_500
        "points_1000" -> R.drawable.points_1000
        "points_5000" -> R.drawable.points_5000
        "points_10000" -> R.drawable.points_10000
        "speed_steady" -> R.drawable.speed_steady
        "speed_quick" -> R.drawable.speed_quick
        "speed_fast" -> R.drawable.speed_fast
        "speed_super" -> R.drawable.speed_super
        "speed_master" -> R.drawable.speed_master
        else -> null
    }

    /** True when an achievement can be evaluated and is unlocked. */
    fun isUnlocked(a: Achievement, inputs: AchievementInputs): Boolean = a.check?.invoke(inputs) ?: false
}
