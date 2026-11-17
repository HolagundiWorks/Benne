package com.example.legal

data class LegalDocument(
    val id: String,
    val code: String,
    val title: String,
    val effectiveDate: String,
    val content: String
)

object LegalDocuments {
    val privacyPolicy = LegalDocument(
        id = "privacy",
        code = "DOC 01 // PRIVACY POLICY",
        title = "Privacy Policy",
        effectiveDate = "Effective Date: November 16, 2026",
        content = """
1. OVERVIEW
Sarala Kannada ("the App", package com.aistudio.saralakannada.kydwpx) is an offline-first educational application designed to teach the Kannada script, vocabulary, and everyday conversational phrases. We respect your privacy and adhere strictly to data minimization principles.

2. ZERO PERSONAL DATA COLLECTION & NO LOGINS
Sarala Kannada requires NO account creation, NO sign-in, and NO login of any kind. The App does NOT collect, sell, rent, or transmit any personally identifiable information (PII), location data, contacts, photos, or behavioral analytics to external servers.

3. LOCAL ON-DEVICE STORAGE (SETTINGS ONLY)
Your optional local display name and learning progress—including XP score, daily streak count, completed curriculum units, mastered flashcards, bookmarked phrases, audio playback speed, and reminder schedule—are stored exclusively on your device inside Settings using Android private application storage (MODE_PRIVATE SharedPreferences). You may permanently erase all local data at any time via Settings > Security & Local Privacy > Reset All Local Progress.

4. DEVICE PERMISSIONS
• Notifications (POST_NOTIFICATIONS — Optional, Android 13+): Requested only if you explicitly enable Daily Practice Reminders. Used strictly to schedule local inexact study reminders on your device. You may revoke this permission anytime in Android System Settings.
• No Location, Camera, Microphone, Contacts, or Storage permissions are requested.

5. TEXT-TO-SPEECH (TTS)
Spoken Kannada audio playback uses the standard Android Text-to-Speech service configured on your device. Audio synthesis is processed by your device's system TTS engine.

6. THIRD-PARTY SERVICES & ADS
Sarala Kannada contains zero advertisements, zero ad-tracking SDKs, and zero third-party analytics trackers.

7. CHILDREN'S PRIVACY
Because Sarala Kannada collects zero personal information and includes no external social features or ads, it is safe for learners of all ages in compliance with COPPA and Google Play Families policies.

8. CONTACT
For privacy questions or support, contact the developer at:
vishwabhiram@gmail.com
        """.trimIndent()
    )

    val termsOfService = LegalDocument(
        id = "terms",
        code = "DOC 02 // TERMS OF SERVICE",
        title = "Terms of Service",
        effectiveDate = "Effective Date: November 16, 2026",
        content = """
1. ACCEPTANCE OF TERMS
By installing or using Sarala Kannada, you agree to be bound by these Terms of Service. If you do not agree, please discontinue use and uninstall the application.

2. EDUCATIONAL LICENSE
We grant you a personal, non-exclusive, non-transferable, revocable license to use Sarala Kannada for personal, non-commercial Kannada language learning and practice.

3. INTELLECTUAL PROPERTY
All original application design, Material Design 3 UI architecture, curated dialogue scenarios, flashcard decks, and source materials are the intellectual property of the Sarala Kannada developer unless otherwise noted in Open Source Attributions.

4. EDUCATIONAL CONTENT DISCLAIMER
Kannada spellings, Romanized transliterations, phonetic guides, and conversational examples reflect common spoken and formal usage across Karnataka (including Bengaluru). Regional dialects vary; content is provided "AS IS" for educational purposes without warranty of uninterrupted availability or linguistic completeness.

5. PROHIBITED USE
You may not reverse-engineer, redistribute, repackage, or commercially exploit the application binary or curated educational datasets outside rights expressly granted by applicable open-source licenses.

6. LIMITATION OF LIABILITY
To the maximum extent permitted by law, the developer shall not be liable for any indirect, incidental, or consequential damages arising from the use or inability to use the application.

7. UPDATES & CONTACT
These terms may be updated in future application releases. Contact: vishwabhiram@gmail.com
        """.trimIndent()
    )

    val openSourceNotices = LegalDocument(
        id = "oss",
        code = "DOC 03 // ATTRIBUTIONS & LICENSES",
        title = "Open Source & Font Licenses",
        effectiveDate = "Updated: November 16, 2026",
        content = """
TYPOGRAPHY ASSETS (SIL Open Font License 1.1)
• Anek Kannada — EkType / Google Fonts (SIL OFL 1.1)
• Plus Jakarta Sans — Tokotype / Google Fonts (SIL OFL 1.1)

ANDROID & JETPACK LIBRARIES (Apache License 2.0)
• AndroidX Core KTX, Activity Compose, Lifecycle Runtime & ViewModel
• Jetpack Compose UI, Foundation, Material 3 & Material Icons
• Kotlin Standard Library & Kotlinx Coroutines

TESTING FRAMEWORKS
• JUnit 4 (Eclipse Public License 1.0)
• Robolectric (MIT License)

All bundled fonts and libraries are used in compliance with their respective open-source licenses.
        """.trimIndent()
    )

    val all: List<LegalDocument> = listOf(privacyPolicy, termsOfService, openSourceNotices)
}
