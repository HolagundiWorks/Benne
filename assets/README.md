# Benne assets catalogue

All brand, art and store assets are in this folder. Files the app uses are copied into `app/src/main/res/`; this folder keeps the source files.

| Folder | What it holds | Used by the app | Files |
|---|---|---|---|
| `brand/` | Palette, logos, mascot poses, app icon, wordmark (PNG and SVG), brand guidelines, Android colour file | Source. App copies live in `app/src/main/res/` (mascot poses, logo, launcher) | 44 |
| `badges/` | Achievement badges (streak, points, speed): production set, 512 and 256 px transparent PNG, SVG, manifest, notes | The 512 px PNGs are copied to `drawable-nodpi/` (streak_*, points_*, speed_*) | 47 |
| `awards/source/` | Original award badge artwork (Image 2 bundle), for reference | Not used. The app uses regenerated badges in `drawable-nodpi/award_*` | 2 |
| `pop-elements/` | Pop-art elements: cards, bursts, ribbons, halftone, arrows, landmarks | Source. Some art is copied to `drawable-nodpi/` as pop_* | 108 |
| `stickers/` | Karnataka sticker pack, 15 transparent 512 px PNGs, fragments from the source sheet removed | Not used in the app yet | 16 |
| `logo/` | Corrected Benne logo and app icon (Kannada text fixed) | Source for the launcher icon and the header logo | 2 |
| `share/` | Progress update template (reference image) | Not copied; the app draws this layout in `ProgressShareCard.kt` | 1 |
| `store/` | Play Store icon (512), feature graphic (1024x500), phone screenshots | Not in the app. Play Console listing | 9 |

## Rules

- Add new source art here, not in the repo root.
- Copy only the sizes the app needs into `app/src/main/res/`; keep the source file here.
- Record the licence and source of third-party art in the folder (see `brand/docs/BRAND_GUIDELINES.md`).
- Do not bake dynamic text (names, numbers, Kannada copy) into artwork. Use live text in the app.
- Check each new asset for stray fragments from its source sheet, cut-off edges and sheet backgrounds before it is committed.

## Audit (this pass)

- Removed: defective crops from the Image 2 bundle (icons with stray text, cut-off mascots, banner with baked text), the bundle's manifest and notes, two contact sheets, a kit preview, and the 15 unclean sticker copies.
- Removed duplicate app resources: two mascot poses identical to `benne_mascot_hello` and `benne_mascot_learning`.
- Regenerated: `award_benne_kannadiga.png` and `award_benne_ratna.png` (512 px, Benne palette, English name live in the art).
- Open: the river sticker's right edge is cut in the source sheet; the app icon source is 1.8 MB.
- Kannada lettering is not in the art, because the image tooling here cannot shape Kannada text. The app renders it as live text.
