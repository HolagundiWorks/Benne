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

## Second pass
- `ui/components/PopComponents.kt` (new): `PopActionButton`, the shared primary action (Pop Red fill, Ink text, 2dp Ink border, 52dp minimum height).
- Used for the lesson step "Next Step" button, the Practice quiz action ("Check Answer" / "Next Question" / "See Results") and "Practice Again".
- Home dashboard: "Continue lesson" card (`home_continue_card`) showing the first unfinished chapter, opening its study sheet via `home_continue_button`. Hidden when every chapter is complete.
- Navigation: left unchanged. It already uses the Butter selected indicator, Ink labels and the Ink top rule from the palette pass.

## Not done
- A separate "Your Path" summary: the existing "Guided roadmap" card with progress bar already covers it, and was not renamed.
- Per-screen layout redesign of Letters, Dialogues and Phrases. They use the new tokens and components only.
- On-device visual check (no AVD on this machine). Lint and instrumented tests not run.

## Third pass: floating nav, merged header (feature/floating-nav)
- The Material `TopAppBar` and `Scaffold` bottom bar are removed. The header is now part of the page content (`ui/components/SaralaHeader.kt`), using the status-bar inset and no container or divider. Same controls: wordmark, Kannada pill, streak, XP, speed, settings.
- On compact widths the bottom nav floats (`ui/FloatingBottomNav.kt`): pill, Ink border, hard offset shadow, selected tab in Butter. Same `nav_item_<screen>` test tags. It sits above the navigation-bar inset.
- Screens reserve 120dp at the bottom (`FloatingNavClearance` in `MainScreen.kt`) so content is not hidden. On the Practice flashcards, the Prev/Flip/Next row is reached by scrolling.
- Wide layouts (>= 600dp) keep the navigation rail.
- Removed: `SaralaTopAppBar` from `SaralaComponents.kt`.
- Verified on device (SM_M115F): Home, Practice (scrolled and unscrolled), nav selection. Build passes; unit tests are unchanged from baseline (1 pre-existing Robolectric failure).
- Not checked: Letters, Dialogues and Phrases on device; tablet/rail layout; lint.

## Fourth pass: brand kit assets (feature/brand-kit)
Source: `benne_brand_kit/` (kept untracked; not committed).
- Launcher: adaptive foreground is now the kit PNG (`drawable/ic_benne_launcher_fg.png`, 432 px, transparent), replacing the JPEG (which had no transparency). Adaptive background is the kit Butter `#FFCC29` (was `#FCD228`).
- Legacy and round launcher icons: kit `app_icon_primary` at 48/72/96/144/192 px for mdpi to xxxhdpi.
- Mascot: `ButterMascot` now renders the kit transparent PNG (`drawable-nodpi/benne_mascot_compact.png`, pose selectable). The hero card uses `benne_mascot_hello.png`. The Canvas-drawn mascot is removed.
- `benne_mascot_learning.png` is added but not used yet.
- Colours: kit values are identical to `ui/theme/Color.kt`; no change needed.

Not used, and why:
- `wordmark_latin` (PNG and SVG): "Benne" is clipped at the right edge in the kit assets. The SVG sets text at font-size 195 inside a 512 px viewBox, so the word overflows. The header keeps the text wordmark. The kit needs fixing before use.
- Fonts: the kit specifies Baloo 2, Nunito Sans and Noto Sans Kannada. The app uses Bricolage Grotesque, Space Grotesk and Anek Kannada. Not changed; font licences need checking first.

Verified on device: home hero shows the kit mascot; header shows text wordmark; launcher shows the new icon. Build passes. Unit tests unchanged (1 pre-existing Robolectric failure).
Open: the launcher mascot looks small inside the icon (the foreground is wrapped in a 66 dp layer). Not tuned.
