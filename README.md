# Benne (ಬೆಣ್ಣೆ) — Learn Kannada

An offline Android app that teaches Kannada to Hindi-speaking learners. Built with Kotlin, Jetpack Compose and Material 3, in a Benne pop-art style with a butter-cube mascot.

**Package:** `com.benne.kannada` · **Min SDK** 24 · **Target SDK** 36

## Menu

| Item | What it covers |
|---|---|
| **Home** | Dashboard: word of the day, progress, scores, Continue lesson, voice notice |
| **Alphabets** | Kannada letters (vowels, consonants, combinations), with pronunciation and stroke tips |
| **Dialogues** | Street dialogues and the Phrasebook |
| **Flashcards** | Flashcards and the multiple-choice quiz |
| **Curriculum** | Practice tiles (flashcards, quiz, poetry), the guided roadmap and its chapters |
| **Geography** | Karnataka map (30 districts; tap for heritage and foods), with Heritage and Food screens |
| **Awards** | Benne identity awards, achievements, share cards and the learner's name |
| **Settings** | Reminders, speed and legal (opens as a sheet) |

The top bar shows the greeting, the learner's level and XP. The Karnataka flag sticker in the top bar opens the flag lesson. Profile (XP, stats, phonetic guide, reset) opens from the learner row under the top bar.

## Learning text

Learning content uses three levels, in this order:

- **H1**: English meaning
- **H2**: Kannada pronunciation in Latin letters and in Devanagari (long vowels in Pop Red)
- **H3**: Kannada script, bold

Components: `ui/components/BenneTypography.kt`. Devanagari comes from reviewed text where it exists, otherwise from `phonetics/KannadaToHindiPhonetic`.

## Audio

Kannada speech uses the phone's built-in `TextToSpeech` engine (`audio/KannadaTtsHelper.kt`). The app picks the best Kannada voice the phone offers. If none is installed, speech is skipped and Home shows a card that opens the phone's text-to-speech settings. No audio files or network calls are used.

## Design

- **Tokens:** colours, spacing, radius, border, depth and type are named in `app/src/main/java/com/example/ui/theme/` (`Color.kt`, `Tokens.kt`). Use the names, not raw numbers. See [`docs/design-tokens.md`](docs/design-tokens.md).
- **Cards** are for things the learner can tap or control. Information sits directly on the screen.
- **Screen rules and history:** [`docs/benne-ui-redesign.md`](docs/benne-ui-redesign.md).

## Assets

All brand, art and store assets are in `assets/`, with a catalogue at [`assets/README.md`](assets/README.md). Files the app uses are copied into `app/src/main/res/`.

## Build and test

```bash
./gradlew :app:assembleDebug
./gradlew :app:bundleRelease   # needs the upload keystore, see docs/play-console/UPLOAD_CHECKLIST.md
./gradlew :app:testDebugUnitTest
```

The debug build needs a local `debug.keystore` in the repo root (git-ignored). The first unit-test run downloads Robolectric's Android runtime, which is then cached. All 27 unit tests pass (`ExampleRobolectricTest`, `ExampleUnitTest`, `KarnatakaDataTest`, `LevelProgressTest`, `AwardRulesTest`).

## Documentation

- [`docs/benne-ui-redesign.md`](docs/benne-ui-redesign.md): current design and screen rules
- [`docs/design-tokens.md`](docs/design-tokens.md): token names and where to use them
- [`docs/security-performance-audit.md`](docs/security-performance-audit.md): security and performance status
- [`docs/play-console/`](docs/play-console/): Play Store listing, data safety, privacy policy, terms, upload checklist
- Privacy policy (published): https://holagundiworks.github.io/Benne/privacy-policy.html

## Palette

Butter Yellow `#FFCC29` · Pop Red `#FF3655` · Cream `#FFF7E2` · Teal `#00B8B0` · Ink `#17172D`
