package com.example

import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.test.core.app.ApplicationProvider
import com.example.data.SaralaKannadaData
import com.example.data.UserProgressManager
import com.example.legal.LegalDocuments
import com.example.notifications.StudyReminderManager
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
        assertEquals("Sarala Kannada", appName)
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
    }

    @Test
    fun `validate legal documents are complete and include contact info`() {
        assertEquals(3, LegalDocuments.all.size)
        assertTrue(LegalDocuments.privacyPolicy.content.contains("ZERO PERSONAL DATA COLLECTION & NO LOGINS"))
        assertTrue(LegalDocuments.privacyPolicy.content.contains("vishwabhiram@gmail.com"))
        assertTrue(LegalDocuments.termsOfService.content.contains("ACCEPTANCE OF TERMS"))
        assertTrue(LegalDocuments.openSourceNotices.content.contains("SIL OFL 1.1"))
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
    }
}
