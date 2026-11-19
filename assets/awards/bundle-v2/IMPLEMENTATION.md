# Benne Image 2 — Android Deployment Assets

Source: the second illustrated Benne asset board from the previous generation.

## Contents
- PNG crops for two awards, four mascot poses, supporting icons, and a welcome share banner.
- SVG wrappers containing the same PNG pixels. **These are not true vector paths.**
- `manifest.json` maps asset names to files and dimensions.

## Android integration
1. Copy PNGs to `app/src/main/res/drawable-nodpi/` (or use optimized WebP exports).
2. In Compose use `Image(painterResource(R.drawable.benne_kannadiga_badge), contentDescription = ...)`.
3. For share images use `welcome_share_banner.png` as a background and overlay *native rendered* dynamic text, or share a rendered bitmap through FileProvider.
4. Keep badge unlock logic separate from graphics.

## IMPORTANT QUALITY LIMITATIONS
The assets were cropped from a single generated composite. They are not separately illustrated originals. Edges may have remnants of the sheet background, and some crops may include neighboring graphics. The Kannada lettering inside artwork is AI-generated and **must be checked/corrected** before production. For perfect transparency and editable vector geometry, redraw each asset as native SVG paths. The SVGs here merely embed PNG images.

Benne palette: Butter Yellow #FFCC29, Pop Red #FF3655, Cream #FFF7E2, Teal #00B8B0, Ink #17172D.
