package com.example

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.example.data.PadyaAataData
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.legal.LegalDocuments
import com.example.model.PadyaGameType
import com.example.notifications.StudyReminderManager
import com.example.phonetics.KannadaToHindiPhonetic
import com.example.phonetics.NameTransliterator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string and compliance resources from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Benne", appName)
        assertTrue(appName.length <= 30)
        assertTrue(context.getString(R.string.notification_channel_name).isNotBlank())
        assertTrue(context.getString(R.string.notification_channel_desc).isNotBlank())
        assertEquals("vishwabhiram@gmail.com", context.getString(R.string.developer_email))
    }

    @Test
    fun `validate kannada learning curriculum content`() {
        assertTrue(SaralaKannadaData.vowels.isNotEmpty())
        assertTrue(SaralaKannadaData.consonants.isNotEmpty())
        assertTrue(SaralaKannadaData.milestones.isNotEmpty())
        assertTrue(SaralaKannadaData.dialogues.isNotEmpty())
        assertTrue(SaralaKannadaData.quizzes.isNotEmpty())
        assertTrue(SaralaKannadaData.dictionary.isNotEmpty())
        assertTrue(SaralaKannadaData.flashcards.isNotEmpty())

        assertTrue(SaralaKannadaData.milestones.all {
            it.titleTransliteration.isNotBlank() && it.steps.all { step -> step.transliteration.isNotBlank() }
        })
        assertTrue(SaralaKannadaData.dialogues.all {
            it.titleTransliteration.isNotBlank() && it.lines.all { line -> line.transliteration.isNotBlank() }
        })
        assertTrue((SaralaKannadaData.vowels + SaralaKannadaData.consonants + SaralaKannadaData.kagunitaSamples).all {
            it.englishTransliteration.isNotBlank() && it.sampleWordTransliteration.isNotBlank()
        })
        assertTrue(SaralaKannadaData.dictionary.all { it.transliteration.isNotBlank() })
        assertTrue(SaralaKannadaData.flashcards.all {
            it.transliteration.isNotBlank() && it.exampleSentenceTranslit.isNotBlank()
        })
    }

    @Test
    fun `validate legal documents are complete and include contact info`() {
        assertEquals(3, LegalDocuments.all.size)
        assertTrue(LegalDocuments.privacyPolicy.content.contains("ZERO PERSONAL DATA COLLECTION & NO LOGINS"))
        assertTrue(LegalDocuments.privacyPolicy.content.contains("vishwabhiram@gmail.com"))
        assertTrue(LegalDocuments.termsOfService.content.contains("ACCEPTANCE OF TERMS"))
        assertTrue(LegalDocuments.openSourceNotices.content.contains("SIL OFL 1.1"))
        assertTrue(LegalDocuments.openSourceNotices.content.contains("Baloo 2"))
        assertTrue(LegalDocuments.openSourceNotices.content.contains("Nunito Sans"))
        assertTrue(LegalDocuments.openSourceNotices.content.contains("Noto Sans Kannada"))
        assertTrue(LegalDocuments.openSourceNotices.content.contains("Noto Sans Devanagari"))
    }

    @Test
    fun `validate notification channel creation`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        StudyReminderManager.ensureNotificationChannel(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = nm.getNotificationChannel(StudyReminderManager.CHANNEL_ID)
            assertNotNull(channel)
            assertEquals("Daily Kannada Practice", channel.name.toString())
        }
    }

    @Test
    fun `validate progress manager notification prefs and data reset`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val manager = UserProgressManager(context)

        manager.setLearnerName("Abhi")
        manager.setNotificationsEnabled(true)
        manager.setReminderTime(8, 30)
        manager.addXp(45)
        assertEquals("Abhi", manager.learnerName.value)
        assertTrue(manager.notificationsEnabled.value)
        assertEquals(8, manager.reminderHour.value)
        assertEquals(30, manager.reminderMinute.value)

        manager.resetAllUserData()
        assertEquals("Kannada Learner", manager.learnerName.value)
        assertFalse(manager.notificationsEnabled.value)
        assertEquals(0, manager.xp.value)
        assertTrue(manager.completedLessons.value.isEmpty())
        assertTrue(manager.masteredFlashcardIds.value.isEmpty())
        assertTrue(manager.favoritePhraseIds.value.isEmpty())
        assertEquals(0, manager.streakDays.value)
    }

    @Test
    fun `fresh install starts at zero and xp updates streak in real time`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.getSharedPreferences("sarala_kannada_prefs", Context.MODE_PRIVATE)
            .edit().clear().commit()

        val manager = UserProgressManager(context)
        assertEquals(0, manager.xp.value)
        assertEquals(0, manager.streakDays.value)
        assertTrue(manager.completedLessons.value.isEmpty())
        assertTrue(manager.masteredFlashcardIds.value.isEmpty())
        assertTrue(manager.favoritePhraseIds.value.isEmpty())
        assertTrue(manager.showHindiPhonetics.value)

        manager.addXp(20)
        assertEquals(20, manager.xp.value)
        assertEquals(1, manager.streakDays.value)

        manager.completeLesson("lesson_1")
        assertEquals(70, manager.xp.value)
        assertTrue(manager.completedLessons.value.contains("lesson_1"))

        manager.setShowHindiPhonetics(false)
        assertFalse(manager.showHindiPhonetics.value)
    }

    @Test
    fun `transliterate english names into kannada script`() {
        assertEquals("ಕನ್ನಡ", NameTransliterator.toKannada("Kannada"))
        assertEquals("ವಿಶ್ವ", NameTransliterator.toKannada("Vishwa"))
        assertEquals("ಅಭಿರಾಮ್", NameTransliterator.toKannada("Abhiram"))
        assertEquals("ಪ್ರಿಯ", NameTransliterator.toKannada("Priya"))
        assertEquals("ನೇಹ", NameTransliterator.toKannada("Neha"))
        assertEquals("ರೋಹನ್", NameTransliterator.toKannada("Rohan"))
    }

    @Test
    fun `convert kannada to hindi phonetic segments with dheergha flags`() {
        val kannadaSegs = KannadaToHindiPhonetic.convertToSegments("ಕನ್ನಡ")
        assertEquals("कन्नड", kannadaSegs.joinToString("") { it.text })
        assertTrue(kannadaSegs.none { it.isDheergha })

        val kannadaLongSegs = KannadaToHindiPhonetic.convertToSegments("ಕನ್ನಡಾ")
        assertEquals("कन्नडा", kannadaLongSegs.joinToString("") { it.text })
        assertTrue(kannadaLongSegs.last().isDheergha)
        assertEquals("डा", kannadaLongSegs.last().text)

        val namaskaraSegs = KannadaToHindiPhonetic.convertToSegments("ನಮಸ್ಕಾರ")
        assertEquals("नमस्कार", namaskaraSegs.joinToString("") { it.text })
        assertTrue(namaskaraSegs.any { it.text == "स्का" && it.isDheergha })

        val houdaSegs = KannadaToHindiPhonetic.convertToSegments("ಹೌದಾ")
        assertEquals("हौदा", houdaSegs.joinToString("") { it.text })
        assertTrue(houdaSegs.all { it.isDheergha })

        val doseSegs = KannadaToHindiPhonetic.convertToSegments("ದೋಸೆ")
        assertEquals("दोसॆ", doseSegs.joinToString("") { it.text })
        assertTrue(doseSegs[0].isDheergha)
        assertFalse(doseSegs[1].isDheergha)
    }

    @Test
    fun `validate padya aata 10 poems and all 8 game datasets`() {
        val poems = PadyaAataData.poems
        assertEquals(10, poems.size)
        assertEquals(8, PadyaGameType.entries.size)

        poems.forEachIndexed { index, poem ->
            assertEquals(index + 1, poem.levelNumber)
            assertEquals(4, poem.lines.size)
            assertTrue(poem.keyWords.size in 4..6)

            poem.lines.forEach { line ->
                assertTrue(line.kannada.isNotBlank())
                assertTrue(line.transliteration.isNotBlank())
                assertTrue(line.english.isNotBlank())
                assertTrue(line.beats.isNotEmpty())
            }

            assertTrue(poem.missingWord.correctIndex in poem.missingWord.options.indices)
            assertTrue(poem.rhymeMatch.correctIndex in poem.rhymeMatch.options.indices)
            assertTrue(poem.pictureToPoem.correctIndex in poem.pictureToPoem.options.indices)
            assertEquals(3, poem.remixSlots.size)

            poem.wordHuntTargets.forEach { target ->
                val lineTokens = poem.lines[target.lineIndex].kannada
                    .split(" ")
                    .map { it.trim(',', '.', '!', '?') }
                assertTrue(
                    "Word Hunt target '${target.targetWordKannada}' missing in ${poem.id} line ${target.lineIndex}",
                    lineTokens.contains(target.targetWordKannada)
                )
            }
        }
    }

    @Test
    fun `validate padya aata progressive unlock, xp rewards, and sticker collection`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        context.getSharedPreferences("sarala_kannada_prefs", Context.MODE_PRIVATE)
            .edit().clear().commit()

        val manager = UserProgressManager(context)
        val poem1 = PadyaAataData.poems.first()

        // Poems 1-3 unlocked initially, Poem 4 locked until 2 games solved in Poem 3
        assertTrue(PadyaAataData.isPoemUnlocked(0, manager.completedPoemChallenges.value))
        assertTrue(PadyaAataData.isPoemUnlocked(2, manager.completedPoemChallenges.value))
        assertFalse(PadyaAataData.isPoemUnlocked(3, manager.completedPoemChallenges.value))

        // Tier 1 unlocked, Tier 2/3 locked before solving any game
        assertTrue(PadyaAataData.isGameUnlocked(poem1.id, PadyaGameType.MISSING_WORD, manager.completedPoemChallenges.value))
        assertFalse(PadyaAataData.isGameUnlocked(poem1.id, PadyaGameType.RHYTHM_TAP, manager.completedPoemChallenges.value))
        assertFalse(PadyaAataData.isGameUnlocked(poem1.id, PadyaGameType.ECHO_THE_POEM, manager.completedPoemChallenges.value))

        // 1st game solved unlocks Tier 2
        val (xp1, sticker1) = manager.recordPoemChallengeCompleted(
            poem1.id, PadyaAataData.challengeKey(poem1.id, PadyaGameType.MISSING_WORD), 10, poem1.sticker.id
        )
        assertEquals(10, xp1)
        assertEquals(null, sticker1)
        assertTrue(PadyaAataData.isGameUnlocked(poem1.id, PadyaGameType.RHYTHM_TAP, manager.completedPoemChallenges.value))

        // Repeat practice awards +5 XP
        val (xpRepeat, _) = manager.recordPoemChallengeCompleted(
            poem1.id, PadyaAataData.challengeKey(poem1.id, PadyaGameType.MISSING_WORD), 10, poem1.sticker.id
        )
        assertEquals(5, xpRepeat)

        // 2nd and 3rd unique games unlock Tier 3 + collectible Karnataka sticker (+25 bonus XP)
        manager.recordPoemChallengeCompleted(
            poem1.id, PadyaAataData.challengeKey(poem1.id, PadyaGameType.RHYME_MATCH), 15, poem1.sticker.id
        )
        val (xp3, sticker3) = manager.recordPoemChallengeCompleted(
            poem1.id, PadyaAataData.challengeKey(poem1.id, PadyaGameType.RHYTHM_TAP), 15, poem1.sticker.id
        )
        assertEquals(15, xp3)
        assertEquals(poem1.sticker.id, sticker3)
        assertTrue(manager.unlockedStickers.value.contains(poem1.sticker.id))
        assertTrue(PadyaAataData.isGameUnlocked(poem1.id, PadyaGameType.POETRY_REMIX, manager.completedPoemChallenges.value))
        assertEquals(70, manager.xp.value)

        manager.resetAllUserData()
        assertTrue(manager.completedPoemChallenges.value.isEmpty())
        assertTrue(manager.unlockedStickers.value.isEmpty())
        assertEquals(0, manager.xp.value)
    }
}
