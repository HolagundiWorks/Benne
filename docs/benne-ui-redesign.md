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
