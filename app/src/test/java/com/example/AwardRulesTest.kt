package com.example

import com.example.data.AwardRules
import com.example.data.Award
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

class AwardRulesTest {

    private val chapters = listOf("c1", "c2", "c3")

    @Test
    fun noNameAndNoLessonsEarnsNothing() {
        assertEquals(emptySet<String>(), AwardRules.earned(emptySet(), chapters, nameRegistered = false))
    }

    @Test
    fun registeringANameEarnsKannadigaWithoutLessons() {
        val earned = AwardRules.earned(emptySet(), chapters, nameRegistered = true)
        assertEquals(setOf(Award.KANNADIGA.id), earned)
    }

    @Test
    fun lessonsAloneDoNotEarnKannadiga() {
        assertFalse(Award.KANNADIGA.id in AwardRules.earned(setOf("c1"), chapters, nameRegistered = false))
    }

    @Test
    fun everyChapterEarnsRatnaAndKeepsKannadiga() {
        val earned = AwardRules.earned(chapters.toSet(), chapters, nameRegistered = true)
        assertEquals(setOf(Award.KANNADIGA.id, Award.RATNA.id), earned)
    }

    @Test
    fun partialChaptersDoNotEarnRatna() {
        assertFalse(Award.RATNA.id in AwardRules.earned(setOf("c1", "c2"), chapters, nameRegistered = true))
    }

    @Test
    fun noChaptersMeansNoRatna() {
        assertFalse(Award.RATNA.id in AwardRules.earned(setOf("c1"), emptyList(), nameRegistered = true))
    }

    @Test
    fun byIdFindsAwards() {
        assertEquals(Award.RATNA, Award.byId("benne_ratna"))
        assertEquals(null, Award.byId("unknown"))
    }
}
