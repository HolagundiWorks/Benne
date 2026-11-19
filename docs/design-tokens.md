# Benne design tokens

Tokens are the named values the UI uses. Screens and components use the token names, never raw numbers or colours. The values live in one place, so a change there changes the whole app.

Source: `app/src/main/java/com/example/ui/theme/`
- `Color.kt`: colours. Use the names: `PopPosterInk`, `PopPosterPaper`, `PopPosterButter`, `PopPosterMint`, `PopPosterCyan`, `PopPosterPeach`, `PopPosterPink`, `PopPosterLavender`, and the raw palette `BenneInk`, `BenneCream`, `BennePopRed`, `BenneButterYellow`, `BenneTeal`, `BenneDheerghaRed`.
- `Tokens.kt`: spacing, component sizes, radius, border, depth and type size. Each token is named by its value (`X16` = 16 dp).
- `Type.kt` and `BenneTypography`: fonts and the type roles.

## Token groups

| Group | Names | Values | Use |
|---|---|---|---|
| `Space` | `X0`, `X1`, `X2`, `X3`, `X4`, `X5`, `X6`, `X8`, `X9`, `X10`, `X12`, `X13`, `X14`, `X16`, `X17`, `X18`, `X20`, `X22`, `X24` | 0–24 dp | Gaps and padding. Also `ScreenGutter` (16) and `Section` (14) |
| `Dim` | `S…` (for example `S44`, `S96`, `S336`) | 1.2–760 dp | Fixed component sizes: icons, art, tiles, touch targets, card widths |
| `Radius` | `R6` … `R28`, `Pill` | 6–28 dp, 50 | Corners: chips, tiles, cards, posters, dock |
| `Border` | `W1`, `W1_2`, `W1_5`, `W1_6`, `W1_8`, `W2`, `W2_5`, `W3` | 1–3 dp | Outline widths |
| `Depth` | `S3`, `S4`, `S5`, `S6` | 3–6 dp | Hard offset shadow of pop cards |
| `TypeScale` | `T…` (for example `T12`, `T18`, `T40`) | sp | Raw sizes. Learning text uses H1, H2 and H3 roles |

## Rules

- Use `Space` for gaps and padding. Use `Dim` for fixed sizes. Never write `16.dp` in a screen.
- Use `Radius` for corners, `Border` for outlines, `Depth` for pop shadows.
- Use `TypeScale` only where a role has no component. Learning text uses `BenneH1`, `BenneH2Latin`, `BenneH2Devanagari` and `BenneH3`.
- Colours come from the named palette. A new colour is added to `Color.kt` with a name first.
- Change a value in `Tokens.kt` to change it everywhere. Do not override it in a screen.

## Audit status

- Outside `ui/theme/`: 0 raw `dp` values, 0 raw `sp` values, 0 raw `Color(0x…)` values, 0 `Color.White`.
- Every token declared in `Tokens.kt` is used by at least one screen; unused tokens are not declared.
- Fonts: the app uses the Baloo family only. Baloo 2 covers Latin and Devanagari and is used everywhere except Kannada script. Kannada script still uses Noto Sans Kannada until Baloo Tamma 2 (the Kannada member of the family) is added. Nunito Sans and Noto Sans Devanagari are removed.
