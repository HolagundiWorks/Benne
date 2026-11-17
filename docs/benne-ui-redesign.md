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
Source: `benne_brand_kit/` (now tracked in git).
- Launcher: adaptive foreground is now the kit PNG (`drawable/ic_benne_launcher_fg.png`, 432 px, transparent), replacing the JPEG (which had no transparency). Adaptive background is the kit Butter `#FFCC29` (was `#FCD228`).
- Legacy and round launcher icons: kit `app_icon_primary` at 48/72/96/144/192 px for mdpi to xxxhdpi.
- Mascot: `ButterMascot` now renders the kit transparent PNG (`drawable-nodpi/benne_mascot_compact.png`, pose selectable). The hero card uses `benne_mascot_hello.png`. The Canvas-drawn mascot is removed.
- `benne_mascot_learning.png` is added but not used yet.
- Colours: kit values are identical to `ui/theme/Color.kt`; no change needed.

Not used, and why:
- `wordmark_latin` (PNG and SVG): the kit wordmark was clipped ("Benn") because the SVG text overflowed its 512 px viewBox. The kit SVG now uses `font-size=180` with `textLength=452`, and the 512/1024 PNGs were re-rendered with a transparent background. The app header now uses this fixed wordmark (`drawable-nodpi/benne_wordmark.png`, from `wordmark_latin_1024.png`, 40 dp high).
- Fonts: the kit specifies Baloo 2, Nunito Sans and Noto Sans Kannada. The app uses Bricolage Grotesque, Space Grotesk and Anek Kannada. Not changed; font licences need checking first.

Verified on device: home hero shows the kit mascot; header shows text wordmark; launcher shows the new icon. Build passes. Unit tests unchanged (1 pre-existing Robolectric failure).
Open: the launcher mascot looks small inside the icon (the foreground is wrapped in a 66 dp layer). Not tuned.

## Fifth pass: pop card assets (feature/pop-cards)
Source: `Benne_Pop_Elements_Karnataka_Kit/` (now tracked in git).
- `PosterCard` (`ui/components/PopCard.kt`) now draws the kit card PNGs (`pop_card_yellow`, `pop_card_red`, `pop_card_cream`) with nine-slice scaling. Corners and outline keep their size, and the middle stretches. The kit PNG has about 48 px of transparent margin, so the slice works on the card's bounding box (x 48-464, y 65-437 of 512). The hard ink offset shadow is kept.
- Colour to skin: Butter -> yellow, Pop Red -> red, anything else -> cream. Existing call sites keep working; chapter cards rotate cream, yellow and red.
- Practice flashcard (`InteractiveFlashcard.kt`): front uses yellow when unmastered, cream otherwise; the skin is drawn by the card's `drawBehind`.
- Home module tiles use kit icons (`pop_icon_book`, `pop_icon_speech`, `pop_icon_trophy`) on yellow, cream and red cards.
- Header: the wordmark is the kit's fixed `wordmark_latin` image (verified on device).
- Not used: landmark illustrations, bursts, ribbons, speech bubbles, halftone. Available in the kit for later screens.
- Verified on device: home (hero, continue card, module tiles), Practice flashcard (mastered and unmastered content visible). Build passes. Unit tests unchanged from baseline (1 pre-existing Robolectric failure).
- Not checked on device: Letters, Dialogues, Phrases, lesson sheet, quiz cards.
- The yellow card's red dots sit in the top band and stretch with card width.

## Sixth pass: on-device verification and card fixes (feature/pop-cards)
Verified on device (SM_M115F): Home, Letters, Dialogues, Phrases, Practice (flashcards and quiz), lesson sheet (opened and Next Step).
Fixes:
- Yellow card red dots: the top-left corner is a fixed block (200 px band), so the dots keep their shape and do not stretch.
- Cream card corner curl: a fixed top-right block (160 x 150 px). Previously the curl stretched across the top edge of short cards.
- Corner band now 16 dp (was 24 dp) so the fixed decorations fit small cards.
- Quiz option buttons use `PosterCard(plain = true)`: exact fill colour and ink outline, no kit artwork, so answer colours stay exact.
Decisions:
- The cream curl stays on letter tiles and list cards; the info icon remains readable on it.
- Landmarks, bursts, ribbons and halftone remain unused.

## Seventh pass: tighter header and dock spacing (feature/pop-cards)
- Header top padding reduced (10 dp to 4 dp top, 6 dp bottom); the status-bar inset is kept.
- Reserved space for the floating dock reduced from 120 dp to 112 dp (`FloatingNavClearance`), and the dock's bottom margin from 12 dp to 8 dp. Content now sits closer to the dock. Scrollable screens such as Practice still show all their controls by scrolling; 100 dp left the Prev/Next row touching the dock.
- Verified on device: Home and Practice.

## Eighth pass: content runs under the floating dock (feature/pop-cards)
- The content container no longer reserves bottom space. Content runs to the bottom of the screen, under the floating dock.
- `LocalFloatingNavInset` (`ui/FloatingNavInset.kt`) gives the dock height (112 dp) on compact widths and 0 with the navigation rail. Scrollable screens add it to their bottom padding (`LazyColumn`/`LazyVerticalGrid` `contentPadding`), so the last item scrolls clear of the dock.
- Quiz columns (flashcards and evaluation) end with a spacer of the dock height inside the scroll.
- Nine-slice pieces overlap by 1 px to remove hairline seams between slices.
- Verified on device: Home, Practice (flashcards), Letters.

## Ninth pass: dashboard hierarchy (UX review, feature/pop-cards)
Applied from the UX review:
- Continue lesson is first on the home screen, above the daily quote.
- Continue card shows "Lesson N of M" and a course progress bar; the Kannada title is 18 sp with a 28 sp line height.
- Daily quote padding reduced (20 to 16 dp). Transliteration is now quieter (ink at 75%, label style), so Kannada and the English meaning lead.
- Verified on device: Home.
Not changed (larger decisions):
- One Kannada family and one Latin family: the app uses Anek Kannada, Bricolage Grotesque and Space Grotesk. The brand kit specifies Baloo 2, Nunito Sans and Noto Sans Kannada, so this needs a font decision first.
- Navigation duplication (module cards and tabs): kept for now.
- Decorations: the teal curl is tied to cream cards and the dots to yellow cards, following the kit skins; no separate decoration rules were added.
- Bottom nav overlap: content now runs under the dock and scrolls clear of it (eighth pass).

## Tenth pass: brand kit fonts and comic shadow modifier (feature/pop-cards)
Fonts (brand kit, Google Fonts, SIL Open Font License; variable TTFs from google/fonts main):
- Latin display and headings: Baloo 2 (`BalooFamily`, `res/font/baloo2_variable.ttf`).
- Latin body and labels: Nunito Sans (`NunitoSansFamily`, `res/font/nunito_sans_variable.ttf`).
- Kannada: Noto Sans Kannada (`NotoSansKannadaFamily`, `res/font/noto_sans_kannada_variable.ttf`).
- Weights use `FontVariation` (400-800). On Android 7 (minSdk 24) the variable weights are not applied and the default weight shows.
- Removed: Anek Kannada, Bricolage Grotesque, Space Grotesk, Plus Jakarta Sans (unused).
- Verified on device: Home renders with the new fonts. The "Continue" button label looks lighter than expected; not yet investigated.

Shadow:
- `Modifier.BennePopShadow(offsetDp, borderDp, shadowColor, backgroundColor, cornerRadius)` in `ui/components/PopShadow.kt`. Draws a hard, unblurred ink offset down and right, a background fill and an ink border inside the edge. Reserves `offsetDp` on the right and bottom.
- Not applied to any screen yet.
