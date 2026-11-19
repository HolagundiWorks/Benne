# Benne design tokens

Tokens are the named values the UI uses. Screens and components use the token names, not raw numbers. The values live in one place, so a change there changes the whole app.

Source: `app/src/main/java/com/example/ui/theme/`
- `Color.kt`: colours (`PopPosterInk`, `PopPosterPaper`, `PopPosterButter`, `PopPosterMint`, `PopPosterCyan`, `PopPosterPeach`, and the raw `Benne*` palette)
- `Tokens.kt`: spacing, radius, border, depth and type size
- `Type.kt` and `BenneTypography` (`ui/components/BenneTypography.kt`): fonts and the H1, H2 and H3 hierarchy

## Tokens

| Group | Token | Value | Typical use |
|---|---|---|---|
| Spacing | `Space.X2` … `Space.X24` | 2 … 24 dp | Gaps and padding on the 2 dp rhythm |
| Spacing | `Space.ScreenGutter` | 16 dp | Left and right gutter on every screen |
| Spacing | `Space.Section` | 14 dp | Gap between sections in a list |
| Radius | `Radius.R10` … `Radius.R28` | 10 … 28 dp | Chips (10–12), tiles (14–16), cards (18–20), posters (22–24), dock (28) |
| Radius | `Radius.Pill` | 50 | Fully rounded ends |
| Border | `Border.W1_2`, `W1_5`, `W1_8`, `W2`, `W2_5`, `W3` | 1.2 … 3 dp | Ink outline width (1.5 dp standard for chips, 2 dp for cards) |
| Depth | `Depth.S3` … `Depth.S6` | 3 … 6 dp | Hard offset shadow under pop cards |
| Type | `TypeScale.T12` … `TypeScale.T40` | 12 … 40 sp | Raw sizes. Use the H1/H2/H3 components for learning text |

## Rules

- Use `Space` for every gap and padding. Do not write `16.dp` in a screen.
- Use `Radius` for every rounded corner, `Border` for every outline, `Depth` for every pop shadow.
- Use `TypeScale` only where a heading or label has no component yet. Learning text uses `BenneH1`, `BenneH2Latin`, `BenneH2Devanagari` and `BenneH3`.
- Colours come from the named palette only. No new `Color(0x…)` in a screen; add a named colour in `Color.kt` first.
- Sizes that are not spacing (icons, tiles, the header logo) stay as named constants in their component.
- Change a value in `Tokens.kt` to change it everywhere. Do not override it in a screen.

## Status

- Replaced across `ui/`: 637 spacing values, 98 radii, 82 borders, 45 depths, 99 type sizes. Each replacement keeps the same value, so the screens look the same.
- Still raw: sizes that are not spacing (icons, art, fixed tiles), which stay in their components.
- Verified: debug build passes; 27 unit tests pass. On SM-M115F the Home screen is unchanged.
