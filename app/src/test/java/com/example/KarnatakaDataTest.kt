package com.example

import com.example.data.AchievementInputs
import com.example.data.Achievements
import com.example.data.KarnatakaCatalog
import com.example.data.KarnatakaDistrictFacts
import com.example.data.KarnatakaGeometry
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class KarnatakaDataTest {

    @Test
    fun streakAndPointsUnlockAtThresholds() {
        val a = Achievements.all.first { it.id == "streak_3" }
        assertFalse(Achievements.isUnlocked(a, AchievementInputs(streakDays = 2, xp = 0)))
        assertTrue(Achievements.isUnlocked(a, AchievementInputs(streakDays = 3, xp = 0)))
        val p = Achievements.all.first { it.id == "points_100" }
        assertFalse(Achievements.isUnlocked(p, AchievementInputs(streakDays = 0, xp = 99)))
        assertTrue(Achievements.isUnlocked(p, AchievementInputs(streakDays = 0, xp = 100)))
    }

    @Test
    fun speedBadgesStayLockedUntilMeasured() {
        val speed = Achievements.all.filter { it.id.startsWith("speed_") }
        assertEquals(5, speed.size)
        speed.forEach { assertFalse(Achievements.isUnlocked(it, AchievementInputs(999, 999_999))) }
    }

    @Test
    fun everyAchievementHasArt() {
        Achievements.all.forEach { assertTrue("missing art for ${it.id}", Achievements.artRes(it.id) != null) }
    }

    @Test
    fun geometryHasThirtyDistrictsWithRings() {
        assertEquals(30, KarnatakaGeometry.districts.size)
        KarnatakaGeometry.districts.forEach { d ->
            val rings = d.encodedRings.split("|").filter { it.isNotBlank() }
            assertTrue("no rings for ${d.name}", rings.isNotEmpty())
        }
    }

    @Test
    fun mysuruDistrictShowsMysorePalace() {
        val names = KarnatakaDistrictFacts.heritageFor("Mysuru").map { it.englishName }
        assertTrue(names.any { it.contains("Mysore Palace") })
    }

    @Test
    fun foodsAppearOnlyWhereRegionIsGiven() {
        val mysuru = KarnatakaDistrictFacts.foodsFor("Mysuru").map { it.englishName }
        assertTrue(mysuru.contains("Mysore Pak"))
        assertTrue(KarnatakaDistrictFacts.foodsFor("Kalaburagi").isEmpty())
    }

    @Test
    fun catalogueSizesMatchSpecifications() {
        assertEquals(62, KarnatakaCatalog.foods.size)
        assertEquals(35, KarnatakaCatalog.heritage.size)
        assertTrue(KarnatakaCatalog.foods.all { !it.reviewed })
        assertTrue(KarnatakaCatalog.heritage.all { !it.reviewed })
    }
}
