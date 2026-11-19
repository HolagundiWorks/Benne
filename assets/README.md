# Benne assets catalogue

All brand, art and store assets are in this folder. Files used by the app are copied into `app/src/main/res/`; this folder keeps the source files.

| Folder | What it holds | Used by the app | Files |
|---|---|---|---|
| `brand/` | Palette, logos, mascot poses, app icon, wordmark (PNG and SVG), brand guidelines, Android colour file | Used as source. App copies live in `app/src/main/res/drawable-nodpi/` (mascot poses, logo) and `mipmap-*` | 44 |
| `badges/` | Achievement badges: production set, 512 and 256 px transparent PNG, editable SVG, manifest, implementation notes | The 512 px PNGs are copied into `drawable-nodpi/` (streak_*, points_*, speed_*) | 48 |
| `pop-elements/` | Pop-art elements: cards, bursts, ribbons, halftone, arrows, landmarks (256, 512, 1024 px) | Used as source. The landmark and card art used in the app is copied into `drawable-nodpi/` (pop_*) | 109 |
| `stickers/` | Karnataka sticker pack, 15 transparent PNGs (512 px and full size) | Copied into `drawable-nodpi/` as pop_sticker_* (one placed so far) | 31 |
| `logo/` | Corrected Benne logo and app icon (Kannada text fixed) | Source for the launcher icon (`mipmap-*`, `ic_benne_launcher_fg`) and the header logo (`benne_logo_longshadow`) | 2 |
| `awards/bundle-v2/` | Image 2 deployment bundle: new award badges (472 and 493 px, transparent), four mascot crops, icons, welcome banner, manifest | The two award badges are copied into `drawable-nodpi/` (`award_benne_kannadiga`, `award_benne_ratna`). Mascot crops are not used: they are cut off, and the bundle notes call them not production quality. The banner is not used yet | 26 |
| `store/` | Play Store icon (512), feature graphic (1024x500), phone screenshots | Not in the app. Used for the Play Console listing | 9 |

## Rules

- Add new source art here, not in the repo root.
- Copy only the sizes the app needs into `app/src/main/res/`; keep the source file here.
- Note the licence and source for any third-party art in the folder README (see `brand/docs/BRAND_GUIDELINES.md`).
- The Kannada speech and flag artwork rights need checking before release (see `docs/benne-ui-redesign.md`).

## Removed

- `awards/` first kit (badges, share banner, source art): superseded by `awards/bundle-v2/` (Image 2 bundle), which the app uses.
- `Benne_Badges_Asset_Kit/`: superseded by `badges/` (the production set the app uses). Its opaque-background PNGs and the original badge sheet were removed.
- `logo/beene logo.png` and `logo/app icon.png`: garbled Kannada originals, replaced by the corrected files in `logo/`.
- Root `build/` (generated reports, git-ignored).
