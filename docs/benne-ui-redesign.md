# Benne UI: current design

This describes the app as it is now. The earlier change log was removed; `git log` has the history.

## Palette

| Token | Hex | Use |
|---|---|---|
| Butter Yellow | `#FFCC29` | primary containers, selected menu item, hero cards |
| Pop Red | `#FF3655` | primary actions (Ink text on it, about 5:1) |
| Cream | `#FFF7E2` | canvas and background |
| Teal | `#00B8B0` | secondary accents |
| Ink | `#17172D` | text, outlines, hard shadows |

Light tints (`PopPosterCyan`, `PopPosterMint`, `PopPosterPeach`, `PopPosterPink`, `PopPosterLavender`) are in `ui/theme/Color.kt`. There is no dark theme.

Spacing, radius, border, depth and type sizes are tokens in `ui/theme/Tokens.kt`. See [design-tokens.md](design-tokens.md).

## Shell

- **Top bar:** a pill with the greeting ("ನಮಸ್ಕಾರ") and the learner's name, the streak and points badges, and the Benne logo. The Karnataka flag sticker sits beside the logo and opens the flag lesson. Below the pill, a row shows the learner type, XP and level badge. It hides while the content scrolls down.
- **Menu:** a floating dock with five items visible at a time. Arrow buttons in the side gutters scroll to the rest. Items: Home, Alphabets, Dialogues, Flashcards, Curriculum, Geography, Awards, Settings.
- **Wide screens:** a navigation rail with the same items.
- **Tabs** cross-fade when switched.

## Hierarchy

- **Learning text:** H1 English meaning, H2 Kannada pronunciation (Latin and Devanagari), H3 Kannada script. Components in `ui/components/BenneTypography.kt`.
- **Screen headings:** small caps labels (`HomeSectionHeader`), for example "YOUR PROGRESS", "SCORES", "PRACTICE".
- **Cards** are for things the learner can tap or control: lessons, practice tiles, the Continue lesson card, the word of the day (has a speak button), buttons and forms.
- **Information sits directly on the screen**, with the same spacing as a card would have: progress bars, scores, the Curriculum hero, the Alphabets header, the Profile stats.

## Screens

- **Home:** word of the day, your progress (chapters and flashcards), scores, Continue lesson, voice notice (only when Kannada voice is missing), badges shown in progress.
- **Alphabets:** letters grid with pronunciation and stroke tips. The letter sheet shows an example word and the Kannada letter.
- **Dialogues:** street dialogues with an English toggle, and the Phrasebook.
- **Flashcards:** flashcards and the multiple-choice quiz.
- **Curriculum:** practice tiles (Flashcards, Quiz, Poetry), the guided roadmap and the chapter lesson sheet.
- **Geography:** the Karnataka map fills the screen between the top bar and the menu; Heritage and Food buttons below it. Tapping a district opens its detail sheet.
- **Share cards:** the progress card follows the "My Kannada Journey · Progress Update" template (`ui/components/ProgressShareCard.kt`, reference image in `assets/share/`). It shows the learner's milestone badge, the Kannadiga line, lessons completed, streak and overall progress, all as live text.
- **Awards:** Benne Kannadiga (unlocks when a name is entered) and Benne Ratna (every chapter finished), the achievements grid, "Share my progress", and the learner name card.
- **Profile:** level and stats, the Hindi phonetic guide, and the reset section. Settings opens from the menu.

## Rules

- Cards only for interactive items. Information is shown directly on the screen.
- Use tokens for spacing, radius, borders, shadows and type sizes. Use named colours.
- Every learning word carries its English meaning and pronunciation.
- Locked content shows what unlocks it, not an empty card.
- Mascot poses are used for the learner card, the word of the day, headers, and the Geography caption.

## Known gaps

- Kannada lettering inside the award artwork is not proofread by a native speaker.
- Kannada speech depends on the phone's Kannada voice data.
- Dialogues has no mascot yet.
- The top-bar level badge shows the next level; the share card shows the current level.
- Joining a WhatsApp group: no group link is in the project.

- **Progress card revision:** stats numbers enlarged (24 sp), stats row padded on the card grid, and a 9:16 story option in the share sheet (640 dp tall card). Verified: debug build passes, 27 unit tests pass; on SM-M115F the square and story previews render.
- **Progress card, logo and watermark:** logo box doubled (72 dp); the Karnataka flag sits beside it, sized to the logo's visible area (40 dp tall, because the logo image has transparent padding); the Karnataka map is a 12% watermark behind the content; the Kannada tagline moved to the footer. Verified: debug build passes, 27 unit tests pass; on SM-M115F the top half renders as intended.

- **Token audit and fixes:** raw dp, sp and colour values outside the theme replaced with tokens (`Dim` added for fixed sizes); palette definitions corrected. Home dashboard has a "Share my progress" button under the progress block. The share template has the Karnataka flag top left and the Benne logo top right. Verified: debug build and 27 unit tests pass; the Home share button renders on SM-M115F.
- **Pending:** two-font decision (see the report that accompanies this log entry).

- **Asset audit and badges:** all raster assets audited (edges, crops, sheet remnants, duplicates). Defective bundle icons and cut-off mascots removed; sticker fragments removed; duplicate pose resources removed. Award badges regenerated in the Benne palette (English name in the art, Kannada as live text). Progress card: placeholder circle removed, motto at the top centre. Verified: debug build passes and 27 unit tests pass; on SM-M115F the new badges and the share card render.
- **Fonts:** Baloo family only. Baloo 2 is used for Latin, Devanagari and body text (Nunito Sans and Noto Sans Devanagari removed). Kannada script is pending Baloo Tamma 2, which needs to be added to the project; until then it uses Noto Sans Kannada. Verified: debug build and 27 unit tests pass; Devanagari and Latin render in Baloo 2 on SM-M115F.

- **Progress share card, restructure:** the square was overflowing, so the card was rebuilt in reading order: motto (top centre), flag left and logo right (unchanged), title ribbon, the hero award line, the stats (numbers first, one-line labels), and one footer line. The second Kannada tagline and the two-line footer were removed; the square now fits with no cropping. Logo, flag and map watermark are unchanged. Verified: debug build and 27 unit tests pass; on SM-M115F the full square renders.
- **Progress card, new learner:** with no lessons and no streak, the stats row is replaced by "I've begun my Kannada journey with Benne!". Verified: debug build and 27 unit tests pass; on SM-M115F the card renders for a new learner.
