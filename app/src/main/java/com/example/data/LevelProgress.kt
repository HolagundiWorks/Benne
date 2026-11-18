package com.example.data

/**
 * Level rules shared by the header meter and the locked Home cards.
 * Level 1 starts at 0 XP and every level takes [XP_PER_LEVEL] XP.
 */
object LevelProgress {
    const val XP_PER_LEVEL = 100

    /** Current level for a total XP value (never below level 1). */
    fun levelFor(xp: Int): Int = xp.coerceAtLeast(0) / XP_PER_LEVEL + 1

    /** XP earned inside the current level, 0 until [XP_PER_LEVEL]. */
    fun xpIntoLevel(xp: Int): Int = xp.coerceAtLeast(0) % XP_PER_LEVEL

    /** Progress through the current level, 0f..1f. */
    fun fractionIntoLevel(xp: Int): Float = xpIntoLevel(xp) / XP_PER_LEVEL.toFloat()

    /** True once the learner has reached [requiredLevel]. */
    fun isUnlocked(xp: Int, requiredLevel: Int): Boolean = levelFor(xp) >= requiredLevel
}
