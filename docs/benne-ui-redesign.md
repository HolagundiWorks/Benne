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

## Eleventh pass: official logo on the celebration screen (feature/pop-cards)
- Logo: `logo/beene logo.png` had garbled Kannada. The corrected SVG (`benne_kannada_corrected.svg`, Noto Sans Kannada text) was rendered with the bundled font and composited over the lettering band of the long-shadow logo. The result is `logo/beene logo corrected.png` (not committed: the `logo/` folder is untracked).
- App asset: `res/drawable-nodpi/benne_logo_longshadow.png` (560 px wide, transparent).
- Placement: bottom-right on the quiz results screen only, 16dp margin plus the floating-dock inset, 15% of width. The "Practice Again" button is 60% wide to keep clear space.
- App icon: `logo/app icon corrected.png` (not committed; `logo/` is untracked). Garbled Kannada replaced with the corrected text, and the white area outside the rounded corners made transparent. Not yet wired into the launcher.
- Verified on device: quiz results with the logo placed bottom-right.
- Open: the "Practice Again" and "Continue" labels look lighter than expected with Nunito Sans; the cause is not yet isolated.

## Twelfth pass: launcher icon (feature/pop-cards)
- Launcher now uses `logo/app icon corrected.png`: adaptive foreground (`drawable/ic_benne_launcher_fg.png`, 432 px, sized 84 dp in the adaptive layer), Butter background (`#FFCC29`), and legacy and round icons at 48/72/96/144/192 px.
- The kit mascot foreground is replaced. The kit's app icon sources are unchanged.
- Verified on device: launcher shows the new icon.

## Thirteenth pass: floating top bar (feature/pop-cards)
- The header is a floating pill at the top (`SaralaHeader`), drawn above the content, with the status-bar inset. Content runs up under it and scrolls beneath, like the bottom dock.
- `LocalFloatingHeaderInset` (status-bar inset + 76 dp) gives each screen the space its first item needs. Screens add it to their top padding; the quiz screen adds it once on its outer column.
- Verified on device: Home and Practice.
- Logo: `logo/beene logo.png` replaced with the corrected version (backup of the original kept outside the repo). The header still shows the kit wordmark; the long-shadow logo is used on the quiz results screen only.

## Fourteenth pass: logo in the header (feature/pop-cards)
- Header shows the official long-shadow logo (`drawable-nodpi/benne_logo_longshadow.png`) at 88 dp high (2x the earlier size), on the right edge. The kit wordmark resource is removed (unused).
- The separate Kannada pill is removed, since the logo already includes ಬೆಣ್ಣೆ.
- Status pills and settings sit to the left of the logo. Screen top inset grows to 124 dp (status bar + header height).
- Verified on device: Home.

## Fifteenth pass: header height and logo crop (feature/pop-cards)
- Logo stays at 88 dp. The header pill is back to its earlier height; the logo extends past it and is cropped by the header's rounded border (`clip` on the header surface, `requiredHeight` on the logo).
- Screen top inset back to 84 dp (status bar + header height).
- Verified on device: Home.

## Sixteenth pass: even card borders (feature/pop-cards)
- Card slices no longer overlap in destination. The overlap doubled the semi-transparent outline pixels, which made some border lines look heavier than others.
- Each slice now samples one source pixel past its edge and is clipped to its own rectangle, so filtering does not fade at slice boundaries and seams do not return.
- Verified on device: Home and Practice (flashcards); borders even, no seams.

## Seventeenth pass: header position and logo overhang (feature/pop-cards)
- Header pill moved down (top margin 22 dp).
- The logo (88 dp) is a separate layer centred on the pill's right edge, so it extends above and below the border. The pill keeps its height; the pills and settings are padded so they stay clear of the logo.
- Screen top inset set to 100 dp (status bar + header).
- Verified on device: Home.

## Eighteenth pass: logo anchored to the header's bottom border (feature/pop-cards)
- The logo's bottom edge sits on the pill's bottom border, and its right edge stays inside the border. It extends only upward above the pill.
- Header top margin 34 dp; screen top inset 112 dp.
- Verified on device: Home.

## Nineteenth pass: logo stays inside the header (feature/pop-cards)
- The logo layer is clipped to the header's rounded shape, so nothing extends past the border. Its bottom sits just above the border line.
- At 88 dp in a ~52 dp pill, the top of the wordmark is cropped. A taller pill would show the full wordmark.
- Verified on device: Home.

## Twentieth pass: logo top not cropped (feature/pop-cards)
- The logo's top is no longer cropped; it overhangs the header upward. Its bottom and right edges stay inside the border.
- Verified on device: Home.

## Twenty-first pass: playful, gamified touches (feature/pop-cards)
- Home: level card (`home_level_card`). Level = XP / 100 + 1, with a playful title per level (Tender Learner, Filter Coffee Fan, Auto Negotiator, Darshini Regular, Bengaluru Local) and progress to the next level. Uses the existing XP stream; no new storage.
- Quiz results: Benne mascot pose and title by score: 80%+ success pose "Kannada superstar!", 50%+ reward pose "Nicely done!", below 50% thinking pose "Keep going!", each with a Kannada line.
- Verified on device: Home level card. Results screen builds; not yet checked on the device.

## Twenty-second pass: badges (feature/pop-cards)
- Home badge row (`home_badges`): First Steps (1 lesson), On Fire (3-day streak), XP Hunter (300 XP), Chapter Champ (all lessons). Unlocked badges are yellow; locked ones show what unlocks them. Scrolls sideways.
- Derived from existing progress (lessons, streak, XP); nothing new is stored.
- Verified on device: Home.

## Twenty-third pass: kit stickers on cards (feature/pop-cards)
- `PosterCard` takes an optional `sticker` (kit PNG), `stickerSize` and `stickerAlignment`. It is drawn above the card surface and does not take taps.
- Dialogues header: Vidhana Soudha landmark (`pop_landmark_vidhana`) at bottom-right.
- Continue lesson card: kit star reward (`pop_star_reward`) at bottom-right.
- Verified on device: Home and Dialogues.

## Twenty-fourth pass: Karnataka sticker pack (feature/pop-cards)
- Source: `Benne_Karnataka_Stickers_PNG/` (15 transparent 512 px stickers). Only the auto rickshaw is placed so far (`pop_sticker_auto_rickshaw`, level card, bottom-right, 56 dp).
- The other 14 are not in the app yet; most cards have no corner free of text or buttons. They need a placement decision.
- Verified on device: Home.
- Explore Karnataka strip (`home_karnataka_strip`) on Home: the other 14 stickers in a sideways row, 72 dp each, with accessible names. Placed after the module tiles.
- Adds 14 sticker PNGs (`res/drawable-nodpi/pop_sticker_*`). These add to APK size; compress if needed.

## Design audit and rules (feature/ui-cleanup)
Rules applied across the app:
- **Hero and header cards** use the kit artwork (`PosterCard(kit = true)`): daily quote, Continue lesson, flashcard spotlight, Dialogues, Letters and Phrasebook headers, quiz results.
- **Content cards** use a plain fill with a 2 dp ink outline and a hard shadow (default). This covers the level card, roadmap, flashcards, quiz questions and options, phrase rows and dialogue lines. Decorative curls no longer sit under text or buttons.
- **Colour** is for meaning, not decoration: tags, the speaking state and the active tab. Phrase rows are white; they turn butter while their audio plays.
- Letter tiles keep their colour coding (vowels, consonants, combinations).
- Sticker placement: bottom-right on the Continue card, level card and Dialogues header only; never over text or buttons.
Verified on device: Home, Letters, Dialogues, Phrasebook, Practice flashcards.
Not yet re-checked: quiz question and results screens, lesson sheet, settings sheet.
Remaining-screen audit (feature/ui-cleanup), on device:
- Lesson sheet: consistent. Step card, audio button and Next button follow the rules above.
- Quiz (evaluation): question card is a plain teal fill (content card), options are plain outlined. Consistent.
- Settings sheet: consistent. Title uses the app heading font; section headers come from one shared component.
No changes needed on these screens.
