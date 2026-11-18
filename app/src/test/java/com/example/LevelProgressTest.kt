package com.example

import com.example.data.LevelProgress
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class LevelProgressTest {

    @Test
    fun levelStartsAtOneAndStepsEvery100Xp() {
        assertEquals(1, LevelProgress.levelFor(0))
        assertEquals(1, LevelProgress.levelFor(99))
        assertEquals(2, LevelProgress.levelFor(100))
        assertEquals(3, LevelProgress.levelFor(250))
        assertEquals(1, LevelProgress.levelFor(-5))
    }

    @Test
    fun xpIntoLevelAndFractionTrackTheCurrentLevel() {
        assertEquals(50, LevelProgress.xpIntoLevel(250))
        assertEquals(0.5f, LevelProgress.fractionIntoLevel(250), 0.0001f)
        assertEquals(0, LevelProgress.xpIntoLevel(200))
    }

    @Test
    fun cardsUnlockAtTheirRequiredLevel() {
        assertFalse(LevelProgress.isUnlocked(99, 2))
        assertTrue(LevelProgress.isUnlocked(100, 2))
        assertTrue(LevelProgress.isUnlocked(0, 1))
    }
}
