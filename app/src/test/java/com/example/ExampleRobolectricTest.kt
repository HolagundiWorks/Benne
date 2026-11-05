package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.SaralaKannadaData
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Sarala Kannada", appName)
    }

    @Test
    fun `validate kannada learning curriculum content`() {
        assertTrue(SaralaKannadaData.vowels.isNotEmpty())
        assertTrue(SaralaKannadaData.consonants.isNotEmpty())
        assertTrue(SaralaKannadaData.milestones.isNotEmpty())
        assertTrue(SaralaKannadaData.dialogues.isNotEmpty())
        assertTrue(SaralaKannadaData.quizzes.isNotEmpty())
        assertTrue(SaralaKannadaData.dictionary.isNotEmpty())
    }
}
