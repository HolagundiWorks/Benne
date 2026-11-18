# Benne (ಬೆಣ್ಣೆ) — Learn Kannada

An offline Android app that teaches Kannada to Hindi-speaking learners. Built with Kotlin, Jetpack Compose and Material 3, in a Benne pop-art style with a butter-cube mascot.

**Package:** `com.benne.kannada` · **Min SDK** 24 · **Target SDK** 36

## Learning sections

| Section | What it covers |
|---|---|
| **Home** | Continue lesson, level and badges, daily quote, Karnataka flag and map cards, Food and Heritage, Poetry Play, the chapter list |
| **Letters** | Kannada vowels and consonants, with pronunciation and stroke tips |
| **Talk** | Dialogues (street scenarios) and the Phrasebook |
| **Practice** | Flashcards, a multiple-choice quiz, and Poetry Play (poem games) |
| **Profile** | Achievements, stickers, favourites, Settings |

Karnataka explorers: **Karnataka Flag** (quiz and audio), **Karnataka Map** (30 districts, tap for heritage and foods), **Food** (62 dishes, pending review), **Heritage** (35 places, pending review).

## Text hierarchy

Learning content uses three levels, in this order:

- **H1**: English meaning
- **H2**: Kannada pronunciation in Latin letters and in Devanagari (long vowels in Pop Red)
- **H3**: Kannada script, bold

Components: `ui/components/BenneTypography.kt`. Devanagari comes from reviewed text where it exists, otherwise from `phonetics/KannadaToHindiPhonetic`.

## Audio

Kannada speech uses the phone's built-in `TextToSpeech` engine (`audio/KannadaTtsHelper.kt`). The app picks the best Kannada voice the phone offers. If none is installed, speech is skipped and Home shows a card that opens the phone's text-to-speech settings. No audio files or network calls are used.

## Build and test

```bash
./gradlew :app:assembleDebug
./gradlew :app:bundleRelease   # needs the upload keystore, see docs/play-console/UPLOAD_CHECKLIST.md
./gradlew :app:testDebugUnitTest
```

The debug build needs a local `debug.keystore` in the repo root (git-ignored). The unit tests currently fail for an environment reason: `app/build.gradle.kts` points Robolectric at a Linux-only offline path. This needs fixing before release.

## Documentation

- `docs/benne-ui-redesign.md`: design decisions and the record of each pass
- `docs/ux-audit.md`: UX laws and app-structure audit
- `docs/play-console/`: Play Store listing, data safety, privacy policy, upload checklist and store assets
- Privacy policy (published): https://holagundiworks.github.io/Benne/privacy-policy.html

## Brand assets

- `benne_brand_kit/`: palette, logos, mascot, app icon sources
- `Benne_Pop_Elements_Karnataka_Kit/`: cards, bursts, ribbons, landmarks
- `Benne_Karnataka_Stickers_PNG/`: Karnataka sticker pack
- `Benne_Badges_Production_Kit/`: achievement badges (SVG and transparent PNG)

## Palette

Butter Yellow `#FFCC29` · Pop Red `#FF3655` · Cream `#FFF7E2` · Teal `#00B8B0` · Ink `#17172D`
