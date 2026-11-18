# Benne Badge Assets Kit — Android Implementation

## Contents
- `png/`: 15 individually cropped badges from the latest Benne mascot artwork.
- `svg/`: 15 SVG wrappers **embedding the same PNG artwork**. These are **not true vector paths** and do not scale without raster limitations.
- `source/`: Original 1536×1024 badge sheet.
- `manifest.json`: Asset IDs, categories, and paths.

## Important production limitations
The original image is an AI-generated **composite sheet**, not layered source artwork. Individual crops retain the original opaque beige/grey sheet background, may include neighbouring details at the edges, and are not transparent. Do not treat these as final production-ready transparent icons. The SVGs embed raster images; they are provided for compatibility only. For production, redraw each badge as editable SVG paths, verify typography, and export transparent WebP/PNG from the vector masters. Avoid automatic background removal that damages the white sticker outlines.

## Brand style
- Butter Yellow `#FFCC29`
- Pop Red `#FF3655`
- Cream `#FFF7E2`
- Teal `#00B8B0`
- Ink `#17172D`
- Bold dark outlines, white sticker keyline, flat pop colours, rounded elegant silhouettes, no grain/noise/texture.
- Maintain the cheerful butter-cube mascot and its consistent face/limbs.

## Badge IDs and unlock examples
| Streak | Points | Speed |
|---|---|---|
| `streak_3_days` | `points_100` | `speed_steady` |
| `streak_7_days` | `points_500` | `speed_quick` |
| `streak_25_days` | `points_1000` | `speed_fast` |
| `streak_50_days` | `points_5000` | `speed_super` |
| `streak_100_days` | `points_10000` | `speed_master` |

**Note:** Speed unlock thresholds are not specified by the artwork. Define measurable criteria (e.g., response-time percentile on sufficiently many exercises) in app logic, and do not reward guessing at the expense of accuracy.

## Jetpack Compose usage
Copy production-approved PNGs into `app/src/main/res/drawable-nodpi/` (lowercase file names already meet Android naming rules). Do not put opaque sheet crops in user-facing UI unless accepted as a temporary prototype.

```kotlin
@Composable
fun AchievementBadge(@DrawableRes badgeRes: Int, label: String, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(badgeRes),
        contentDescription = label,
        modifier = modifier.size(96.dp),
        contentScale = ContentScale.Fit
    )
}

AchievementBadge(R.drawable.streak_7_days, "7-day learning streak")
```

Import `androidx.compose.foundation.Image`, `androidx.compose.foundation.layout.size`, `androidx.compose.ui.Modifier`, `androidx.compose.ui.layout.ContentScale`, `androidx.compose.ui.res.painterResource`, `androidx.compose.ui.unit.dp`, and `androidx.annotation.DrawableRes`.

## UX guidance
- Use 64–96 dp badges in achievement grids and 112–160 dp on unlock celebrations.
- Add 8–12 dp breathing room; never stretch artwork.
- Show locked badges in a clearly labeled locked state; do not rely only on grayscale.
- Announce unlocked achievement names to accessibility services.
- Keep thresholds configurable and persisted locally, with offline progress and no backend requirement.
- For sharing images, render a separate celebration card and include clear text; don't rely on badge art alone.

## Quality checklist
1. Redraw to true vector masters, separate from the composite sheet.
2. Remove background manually without losing white sticker borders.
3. Confirm all numerals, labels, and mascot anatomy at small sizes.
4. Test at 48, 64, 96, and 128 dp in light/dark themes.
5. Export transparent PNG/WebP and retain true vector SVG originals.
6. Verify reward logic, screen-reader labels, and correct Kannada where applicable.
