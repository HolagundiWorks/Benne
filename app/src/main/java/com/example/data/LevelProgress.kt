package com.example.data

/**
 * Level rules for the header meter and the Home dashboard.
 * Level 1 starts at 0 XP and every level takes [XP_PER_LEVEL] XP. All content is unlocked.
 */
object LevelProgress {
    const val XP_PER_LEVEL = 100

    /** Current level for a total XP value (never below level 1). */
    fun levelFor(xp: Int): Int = xp.coerceAtLeast(0) / XP_PER_LEVEL + 1

    /** XP earned inside the current level, 0 until [XP_PER_LEVEL]. */
    fun xpIntoLevel(xp: Int): Int = xp.coerceAtLeast(0) % XP_PER_LEVEL

    /** Progress through the current level, 0f..1f. */
    fun fractionIntoLevel(xp: Int): Float = xpIntoLevel(xp) / XP_PER_LEVEL.toFloat()

    private val learnerTypes = listOf("Tender Learner", "Filter Coffee Fan", "Auto Negotiator", "Darshini Regular", "Bengaluru Local")

    /** Learner type shown for a level; the last type repeats for higher levels. */
    fun learnerTypeFor(level: Int): String = learnerTypes[(level - 1).coerceIn(0, learnerTypes.lastIndex)]
}
