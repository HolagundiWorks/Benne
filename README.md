# Sarala Kannada (ಸರಳ ಕನ್ನಡ) // System SK-26
> **"Weniger, aber besser" (Less, but better) — An authentic, functional Kannada language learning system.**

Sarala Kannada is a modern, high-fidelity Android application built with **Jetpack Compose** and inspired by **Dieter Rams' ten principles of good design** and the **Braun industrial design aesthetic**. It enables learners to easily master spoken and written Kannada through tactile, interactive components, real Bengaluru situational dialogues, 3D flip-to-reveal vocabulary flashcards, and native acoustic speech synthesis.

---

## 📻 Key Features

### 1. Interactive 3D Flip Vocabulary Flashcards
- **Flip-to-Reveal Mechanism**: Smooth 180-degree 3D rotational perspective card flip (`graphicsLayer`).
- **Front Face**: High-contrast Kannada glyph in authentic **Anek Kannada** typography (`44sp`), phonetic pronunciation breakdown, and a tactile push-key TTS audio button.
- **Back Face**: English translation, real-world example sentences with secondary native audio pronunciation, and cultural memory notes (e.g. why *"ಊಟ ಆಯ್ತಾ?"* is the quintessential greeting in Karnataka).
- **Mastery Engine**: Mark cards as *Mastered* to earn +15 XP and persistently track learning progress.
- **Category Filter & Shuffle**: Study cards across *Essentials*, *Food & Dining*, *Travel & Auto*, *Commerce & Market*, and *People & Life*.

### 2. Varnamale Script Matrix (`SYSTEM 02 // SCRIPT MATRIX`)
- **Complete Alphabet Exploration**:
  - **14 Swaragalu (Vowels)**: Foundational sounds from `ಅ` to `ಔ` + `ಅಂ`, `ಅಃ`.
  - **34 Vyanjanagalu (Consonants)**: Gutturals to retroflexes (`ಕ` through `ಳ`).
  - **Kagunita**: Consonant-vowel compound combinations.
- **Laboratory Inspection Console**: Detailed modal sheet for each glyph with acoustic audio, manual stroke guidance, and illustrated example words.

### 3. Real-World Bengaluru Spoken Dialogues (`SYSTEM 03 // DIALOGUES`)
- **Authentic Scenarios**:
  - `PRG.01`: **Auto Rickshaw Ride** (indiranagar, MG road meter negotiation, turn-by-turn directions).
  - `PRG.02`: **Bengaluru Darshini** (Ordering benne masala dosa, idli vada, and filter coffee with less sugar).
  - `PRG.03`: **KR Market Mandi** (Asking potato/onion rates, bargaining, getting loose change).
  - `PRG.04`: **City Directions** (Locating the nearest Namma Metro station).
- **Dual-Channel Audio**: Line-by-line native speech synthesis with transliteration and a toggleable subtitle switch.

### 4. Evaluation Quiz & Testing Console (`SYSTEM 04 // EVALUATION`)
- Multiple-choice questions assessing letter identification, spoken reply accuracy, and listening comprehension.
- Instant feedback with technical explanations and celebratory scoring reports (+50 XP per session).

### 5. Lexicon Archive (`SYSTEM 05 // LEXICON`)
- Instant real-time search across Kannada script, English definitions, and Roman transliterations.
- Quick bookmarking for rapid offline retrieval on the street.

---

## 📐 Dieter Rams Design System

The application strictly adheres to functional minimalism and precision industrial product design:

- **Palette**: Anodized matte off-white (`#F4F4F1`), stark chassis surfaces (`#FFFFFF`), precision border hairlines (`#D2D2CC`), and high-contrast charcoal typography (`#161616`).
- **Iconic Braun Accents**: Functional Braun Orange (`#FF5200`), Signal Green (`#1E824C`), and Instrument Amber (`#E69500`).
- **Acoustic Perforated Grille**: Custom canvas dot matrix inspired by the classic Braun T3 transistor radio and SK4 phonograph.
- **Tactile Keys**: Circular convex push buttons reminiscent of the Braun ET66 calculator keys.
- **Typography Pairing**:
  - **Anek Kannada** (`res/font/anek_kannada.ttf`) for authentic, bold regional glyphs.
  - **Plus Jakarta Sans** (`res/font/plus_jakarta_sans.ttf`) for tracked uppercase technical labeling.

---

## 🛠️ Architecture & Tech Stack

- **UI Framework**: Android Jetpack Compose (Material 3).
- **Speech Engine**: Built-in Android `TextToSpeech` with `Locale("kn", "IN")` support and phonetic acoustic fallback.
- **State Management**: Android `ViewModel`, Kotlin Coroutines `StateFlow`, and reactive Compose state.
- **Persistence**: `UserProgressManager` backed by Android `SharedPreferences` for streak tracking, XP points, completed units, and mastered flashcards.
- **Testing**: Robolectric local JVM testing (`RobolectricTestRunner`) with offline Android 16 SDK instrumentation.

---

## 🚀 Building & Testing

### Compile the APK
```bash
gradle assembleDebug
```

### Run Local Unit & Robolectric Tests
```bash
gradle :app:testDebugUnitTest
```

---

## 🛡️ Play Console Compliance, Security & Legal Docs

- **Notifications**: Optional daily practice reminders (`POST_NOTIFICATIONS` runtime opt-in on Android 13+) scheduled via `AlarmManager.setInexactRepeating` with `PendingIntent.FLAG_IMMUTABLE`.
- **Security & Privacy**: Zero ads, zero analytics SDKs, `network_security_config.xml` (`cleartextTrafficPermitted="false"`), scoped Auto Backup rules, R8 minification & resource shrinking, and in-app local progress reset.
- **Play Console Documentation & Assets**:
  - [Privacy Policy](docs/play-console/PRIVACY_POLICY.md)
  - [Terms of Service](docs/play-console/TERMS_OF_SERVICE.md)
  - [Data Safety & App Content Declaration](docs/play-console/DATA_SAFETY_DECLARATION.md)
  - [Play Store Listing & Release Checklist](docs/play-console/PLAY_STORE_LISTING.md)
  - Store Graphics: `docs/play-console/assets/play_store_icon_512.png` (512×512) & `docs/play-console/assets/feature_graphic_1024x500.png` (1024×500)

---

*Sarala Kannada // System SK-26 — Designed with precision for effortless Kannada learning.*
