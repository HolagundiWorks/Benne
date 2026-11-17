# Benne — ಬೆಣ್ಣೆ | Brand kit v1.0

## Concept
Benne means butter in Kannada. The mascot is a friendly butter cube that guides newcomers through Kannada learning. Brand personality: cheerful, bold, encouraging, never childish in instructional content.

## Colour tokens
| Token | Hex | Use |
|---|---|---|
| Butter | #FFCC29 | Brand, hero surfaces, badges |
| Pop Red | #FF3655 | Highlights and prominent actions with dark text |
| Cream | #FFF7E2 | Main background |
| Teal | #00B8B0 | Listening and practice accents with dark text |
| Ink | #17172D | Text, outlines, icon strokes |

## Usage
- `logo_primary.svg`: hero/splash and marketing; 1000x420 viewbox.
- `wordmark_latin.svg`: compact horizontal brand treatment.
- `app_icon_primary.svg`: square logo for store listing and app icon; export 512px PNG provided.
- `mascot_compact.svg`: simple transparent character for UI.
- `mascot_*.svg`: six expression illustrations; use as teaching/reward states.
- Android assets: place raster launcher icons in appropriate mipmap density directories; adaptive foreground is supplied as a starting asset, and should be verified in Android Studio Image Asset Studio.
- SVG mascot is true vector. Primary logo contains live font text and is **not outlined**: for cross-platform fidelity, use PNG or convert text to paths in your vector editor. Kannada font requires Noto Sans Kannada to render as intended.
- Maintain at least 48dp touch targets; keep decorative halftone patterns away from learning text.
- Do not stretch or recolour the mascot, change proportions, or add gradients to learning content.

## Typography
- English display: bold rounded sans (suggested Baloo 2, license verification before bundling).
- English body: Nunito Sans or accessible system sans.
- Kannada: Noto Sans Kannada with proper line height and glyph shaping.

## Accessibility
Use Ink on Yellow, Cream and Teal. Do not use white text on Yellow or Pop Red for small labels. Verify contrast for each component.

## Licensing
These newly drawn geometric mascot assets are supplied for this project. The logo uses system font references rather than bundled font files. Review any chosen font's license before shipping.
