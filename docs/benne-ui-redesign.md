# Benne UI redesign (feature/benne-ui)

## What
Apply the Benne Classic palette and pop-art direction across the existing Compose UI, add a butter-cube mascot,
and bring the home dashboard in line with the Benne design sheet.

## Why
The app was rebranded to Benne but still used an older orange/blue/mint palette. The brief asks for the
Benne Classic palette, a consistent pop-art look, legible Kannada text and accessible contrast.

## Palette (Benne Classic)
| Token | Hex | Use |
|---|---|---|
| Butter Yellow | `#FFCC29` | primary containers, selected nav indicator, hero card |
| Pop Red | `#FF3655` | primary actions (text on it is Ink, ~5.0:1) |
| Cream | `#FFF7E2` | canvas / background |
| Teal | `#00B8B0` | secondary accents (Ink text on it ~7.2:1) |
| Ink | `#17172D` | text, borders, hard shadows (~11.9:1 on Butter) |

Light tints (`PopPoster*` Cyan/Mint/Peach/Pink/Lavender) are derived from the five brand colours so that
existing card colour rotations keep working. No dark theme (unchanged).

## Areas touched
- `ui/theme/Color.kt`: palette values only; token names kept so all screens re-skin.
- `ui/components/BenneMascot.kt` (new): Canvas-drawn butter-cube mascot (no image assets, no network).
- `ui/screens/HomeScreen.kt`: mascot added to the hero card.

## Out of scope
- No change to learning logic, ViewModels, persistence, TTS, content data or navigation structure.
- No new dependencies, no cloud services.
- Full per-screen layout rewrites are not included; learning screens inherit the new tokens and shapes.

## Acceptance
- `:app:assembleDebug` builds.
- `:app:testDebugUnitTest` results compared with baseline (see below).
- Visual check of home and a learning screen on an emulator or device.

## Baseline (before changes, this machine)
- `assembleDebug`: fails with a missing `debug.keystore` (gitignored). Fixed locally by copying the standard
  Android debug keystore to the repo root; the file is not committed.
- `testDebugUnitTest`: 2 tests, 1 failed. `ExampleRobolectricTest` fails with `UnsupportedOperationException`
  at `DefaultSdkProvider`, because `build.gradle.kts` sets `robolectric.dependency.dir` to a Linux path
  (`/home/appuser/.m2/...`) with offline mode on. Pre-existing; not changed here.
- Gradle wrapper jar was missing from the repo; regenerated with Gradle 9.3.1 (matches `gradle-wrapper.properties`).

## Built in this change
- Palette tokens switched to Benne Classic (`Color.kt`). Ink on Pop Red and on Butter/Teal is used for text; white is no longer used on red.
- Butter-cube mascot (`ui/components/BenneMascot.kt`), Canvas-drawn, winking, decorative by default.
- Mascot shown in the home hero card (`HomeScreen.kt`), above the existing audio button.

## Results after change (this machine)
- `:app:assembleDebug`: BUILD SUCCESSFUL (with local debug keystore, see baseline).
- `:app:testDebugUnitTest`: same as baseline, 2 tests, 1 failed (`ExampleRobolectricTest`, pre-existing Robolectric path issue).
- Not run: on-device/emulator visual check (no AVD configured on this machine); lint; instrumented tests.

## Not yet done (from the brief)
- Reusable pop components beyond the mascot (button, card, chip) as a shared set.
- Navigation restyle beyond the new colours.
- Dashboard "Continue lesson" card and Your Path summary.
- Per-screen layout redesign of the learning screens (Letters, Dialogues, Practice, Phrases); they inherit the new tokens only.
