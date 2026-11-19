# Benne Pop Elements & Karnataka Landmarks

Transparent-background PNG and editable SVG assets for Benne, the Kannada learning app.

## Contents
- UI: bursts, speech bubbles, card shapes, ribbons, halftone, accents, badges, reward icons and learning icons.
- Karnataka landmarks: Vidhana Soudha, Mysore Palace, Hampi Stone Chariot, Belur Hoysala Temple, Gol Gumbaz, Badami Caves, Murudeshwara and Jog Falls.

## Brand palette
- Butter Yellow `#FFCC29`
- Pop Red `#FF3655`
- Cream `#FFF7E2`
- Teal `#00B8B0`
- Ink `#17172D`

## Implementation
All PNGs use alpha transparency. Use `png/*_512.png` for standard UI cards and `png/landmark_*_1024.png` for hero illustrations. SVGs are editable source artwork; import through Android Studio Vector Asset when supported or rasterize to density-appropriate WebP/PNG. For large backgrounds, use repeating dot patterns rather than oversized bitmap decorations. Keep landmark names as accessible Compose text, not embedded into illustrations.

Landmarks are original simplified stylized illustrations, not architecturally exact representations.
