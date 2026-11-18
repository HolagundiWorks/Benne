package com.example.model

enum class AlphabetCategory(val displayName: String, val kannadaName: String) {
    VOWELS("Vowels", "ಸ್ವರಗಳು"),
    YOGAVAHA("Modifiers", "ಯೋಗವಾಹಗಳು"),
    CONSONANTS("Consonants", "ವ್ಯಂಜನಗಳು"),
    KAGUNITA("Kagunita", "ಕಾಗುಣಿತ")
}

data class AlphabetItem(
    val char: String,
    val englishTransliteration: String,
    val category: AlphabetCategory,
    val sampleWordKn: String,
    val sampleWordEn: String,
    val sampleWordTransliteration: String,
    val phoneticTip: String,
    val strokeDescription: String
)

data class LessonStep(
    val id: String,
    val title: String,
    val kannadaText: String,
    val transliteration: String,
    val englishText: String,
    val tip: String = ""
)

data class LessonMilestone(
    val id: String,
    val levelNumber: Int,
    val titleKannada: String,
    val titleTransliteration: String,
    val titleEnglish: String,
    val description: String,
    val iconEmoji: String,
    val badgeTag: String,
    val steps: List<LessonStep>
)

data class DialogueLine(
    val speakerName: String,
    val isUser: Boolean,
    val kannadaText: String,
    val transliteration: String,
    val englishTranslation: String
)

data class ConversationDialogue(
    val id: String,
    val titleKannada: String,
    val titleTransliteration: String,
    val titleEnglish: String,
    val locationTag: String,
    val iconEmoji: String,
    val contextDescription: String,
    val lines: List<DialogueLine>
)

enum class QuizType {
    IDENTIFY_LETTER,
    TRANSLATE_PHRASE,
    SITUATIONAL_CHOICE,
    AUDIO_LISTEN
}

data class QuizQuestion(
    val id: String,
    val type: QuizType,
    val prompt: String,
    val kannadaText: String = "",
    val transliteration: String = "",
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class DictionaryPhrase(
    val id: String,
    val category: String,
    val kannada: String,
    val transliteration: String,
    val english: String,
    val isEssential: Boolean = false
)

data class VocabularyFlashcard(
    val id: String,
    val kannadaWord: String,
    val transliteration: String,
    val englishMeaning: String,
    val category: String,
    val phoneticBreakdown: String,
    val exampleSentenceKn: String,
    val exampleSentenceTranslit: String,
    val exampleSentenceEn: String,
    val culturalNote: String = "",
    // Hindi-script pronunciation (Devanagari). Empty = not yet reviewed, hidden in the app.
    val hindiPronunciation: String = "",
    // Exact substring of hindiPronunciation to show in Pop Red (verified by a reviewer only)
    val pronunciationHighlight: String = "",
    val pronunciationNote: String = ""
)
