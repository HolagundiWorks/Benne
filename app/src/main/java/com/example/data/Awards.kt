package com.example.data

import androidx.annotation.DrawableRes
import com.example.R

/**
 * Identity awards from the Benne identity kit (assets/awards).
 *
 * Benne Kannadiga unlocks when the learner registers a name. Benne Ratna unlocks when every chapter
 * is finished; the app has no final assessment yet, so chapters stand in for it.
 */
enum class Award(
    val id: String,
    val titleEnglish: String,
    val titleKannada: String,
    @DrawableRes val badgeRes: Int,
    val rule: String
) {
    KANNADIGA(
        id = "benne_kannadiga",
        titleEnglish = "Benne Kannadiga",
        titleKannada = "ಬೆಣ್ಣೆ ಕನ್ನಡಿಗ",
        badgeRes = R.drawable.award_benne_kannadiga,
        rule = "Enter your name"
    ),
    RATNA(
        id = "benne_ratna",
        titleEnglish = "Benne Ratna",
        titleKannada = "ಬೆಣ್ಣೆ ರತ್ನ",
        badgeRes = R.drawable.award_benne_ratna,
        rule = "Finish every chapter"
    );

    companion object {
        fun byId(id: String): Award? = entries.firstOrNull { it.id == id }
    }
}

/** Which awards are earned for a set of finished lessons. Pure, so it can be unit tested. */
object AwardRules {
    fun earned(completedLessons: Set<String>, milestoneIds: List<String>, nameRegistered: Boolean): Set<String> = buildSet {
        if (nameRegistered) add(Award.KANNADIGA.id)
        if (milestoneIds.isNotEmpty() && milestoneIds.all { it in completedLessons }) add(Award.RATNA.id)
    }
}
