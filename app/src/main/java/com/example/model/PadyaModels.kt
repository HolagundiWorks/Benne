package com.example.model

import androidx.annotation.DrawableRes

enum class PadyaGameType(
    val displayName: String,
    val kannadaTitle: String,
    val learningGoal: String,
    val howItWorks: String,
    val tier: Int,
    val xpReward: Int
) {
    RHYME_MATCH(
        displayName = "Rhyme Match",
        kannadaTitle = "ಪ್ರಾಸ ಜೋಡಿ",
        learningGoal = "Pronunciation",
        howItWorks = "Match words with similar endings",
        tier = 1,
        xpReward = 15
    ),
    MISSING_WORD(
        displayName = "Missing Word",
        kannadaTitle = "ಖಾಲಿ ಪದ",
        learningGoal = "Vocabulary",
        howItWorks = "Complete a line of poetry",
        tier = 1,
        xpReward = 10
    ),
    WORD_HUNT(
        displayName = "Word Hunt",
        kannadaTitle = "ಪದ ಹುಡುಕಾಟ",
        learningGoal = "Word recognition",
        howItWorks = "Find familiar words in a poem",
        tier = 1,
        xpReward = 15
    ),
    RHYTHM_TAP(
        displayName = "Rhythm Tap",
        kannadaTitle = "ಲಯ ತಾಳ",
        learningGoal = "Listening",
        howItWorks = "Tap along to spoken syllables",
        tier = 2,
        xpReward = 15
    ),
    PICTURE_TO_POEM(
        displayName = "Picture to Poem",
        kannadaTitle = "ಚಿತ್ರದಿಂದ ಪದ್ಯ",
        learningGoal = "Comprehension",
        howItWorks = "Match an illustration to a verse",
        tier = 2,
        xpReward = 15
    ),
    POEM_PUZZLE(
        displayName = "Poem Puzzle",
        kannadaTitle = "ಪದ್ಯ ಜೋಡಣೆ",
        learningGoal = "Sentence structure",
        howItWorks = "Arrange shuffled lines",
        tier = 2,
        xpReward = 20
    ),
    ECHO_THE_POEM(
        displayName = "Echo the Poem",
        kannadaTitle = "ಪ್ರತಿಧ್ವನಿ ಓದು",
        learningGoal = "Speaking",
        howItWorks = "Listen and repeat each line",
        tier = 3,
        xpReward = 20
    ),
    POETRY_REMIX(
        displayName = "Poetry Remix",
        kannadaTitle = "ಪದ್ಯ ಮಿಶ್ರಣ",
        learningGoal = "Creative Kannada",
        howItWorks = "Build a funny verse using word tiles",
        tier = 3,
        xpReward = 20
    )
}

data class PoemBeat(
    val text: String,
    val transliteration: String
)

data class PoemLine(
    val lineNumber: Int,
    val kannada: String,
    val transliteration: String,
    val english: String,
    val beats: List<PoemBeat>
)

data class PoemWordCard(
    val kannada: String,
    val transliteration: String,
    val english: String,
    val soundTip: String
)

data class PoemOption(
    val kannada: String,
    val transliteration: String,
    val english: String
)

data class MissingWordChallenge(
    val promptLineKannada: String,
    val promptTranslitMasked: String,
    val promptEnglishMasked: String,
    val fullLineKannada: String,
    val options: List<PoemOption>,
    val correctIndex: Int,
    val explanation: String
)

data class RhymeMatchChallenge(
    val targetWord: PoemOption,
    val options: List<PoemOption>,
    val correctIndex: Int,
    val rhymeExplanation: String
)

data class WordHuntTarget(
    val targetWordKannada: String,
    val targetTranslit: String,
    val promptEnglish: String,
    val lineIndex: Int
)

data class PictureToPoemChallenge(
    val questionPrompt: String,
    val options: List<PoemLine>,
    val correctIndex: Int
)

data class PoetryRemixSlot(
    val label: String,
    val choices: List<PoemOption>
)

data class KarnatakaSticker(
    val id: String,
    val title: String,
    val kannadaTitle: String,
    val regionTag: String,
    @DrawableRes val drawableRes: Int
)

data class PadyaPoem(
    val id: String,
    val levelNumber: Int,
    val titleKannada: String,
    val titleTranslit: String,
    val titleEnglish: String,
    val themeTag: String,
    @DrawableRes val illustrationRes: Int,
    val illustrationCaption: String,
    val lines: List<PoemLine>,
    val keyWords: List<PoemWordCard>,
    val missingWord: MissingWordChallenge,
    val rhymeMatch: RhymeMatchChallenge,
    val wordHuntTargets: List<WordHuntTarget>,
    val pictureToPoem: PictureToPoemChallenge,
    val remixSlots: List<PoetryRemixSlot>,
    val sticker: KarnatakaSticker
)
