# Benne Achievement Badges — Production Asset Kit

## Contents
- 15 original **editable vector SVG** badges (not embedded raster images).
- 15 transparent PNG exports at **512 × 512** and **256 × 256**.
- `manifest.json` with IDs, paths and achievement thresholds.
- `contact_sheet.png` for review.

## Design
- Benne butter-cube mascot on each badge.
- Flat, solid pop colours, no photographic texture or noisy effects.
- Ink `#17172D`, Butter `#FFCC29`, Red `#FF3655`, Cream `#FFF7E2`, Teal `#00B8B0`; purple/blue reserved for high tiers.
- All badges have a 512×512 transparent artboard and visible safe margin.
- SVG uses native shapes and live text; the text remains editable and may depend on font availability. For exact portability, convert text to paths in a vector editor after review.

## Android Jetpack Compose implementation

Copy the chosen PNGs to `app/src/main/res/drawable-nodpi/` (lowercase filenames already compliant). Avoid drawable density scaling for these source-resolution images. Alternatively convert SVG to Android VectorDrawable after checking supported features.

```kotlin
@Composable
fun BenneBadge(@DrawableRes badgeRes: Int, label: String, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(badgeRes),
        contentDescription = label,
        modifier = modifier.size(96.dp),
        contentScale = ContentScale.Fit
    )
}
```

Imports: `androidx.compose.foundation.Image`, `androidx.compose.foundation.layout.size`, `androidx.compose.runtime.Composable`, `androidx.compose.ui.Modifier`, `androidx.compose.ui.layout.ContentScale`, `androidx.compose.ui.res.painterResource`, `androidx.compose.ui.unit.dp`, `androidx.annotation.DrawableRes`.

Use 64–96dp in collection grids, 128–160dp for achievement celebrations. Do not rely on colour alone: show the badge title and unlock requirement outside the graphic, including English H1, romanized/Devanagari Kannada H2 and bold Kannada H3 where learning copy is shown.

## Award logic
- Streak: 3, 7, 25, 50, 100 **consecutive qualifying learning days**; define one qualifying completion event per local calendar day and handle timezone changes.
- Points: 100, 500, 1000, 5000, 10000 **lifetime earned XP**, not spendable balance.
- Speed: Steady, Quick, Fast, Super, Master. **Do not hard-code thresholds from artwork**. Calibrate from real lessons and require a minimum accuracy so rushing does not win.
- Persist awarded badge IDs, award timestamp and progress; award once per threshold. Keep offline and restore state after restart.

## QA and usage
1. Review each PNG at 64dp and 96dp in light and dark themes.
2. Ensure text and mascot remain legible; prefer separate accessible text labels at small sizes.
3. Verify transparent alpha and no clipped edges.
4. Check `manifest.json` references and unlock thresholds.
5. Confirm consistency with the app's actual butter-cube mascot before release.

## Rights
These are newly authored geometric SVG illustrations prepared for the Benne app. They do not incorporate third-party photo assets.
