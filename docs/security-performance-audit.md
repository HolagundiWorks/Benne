# Benne security and performance audit

Scope: manifest, network and backup configuration, logging, storage, threading, asset sizes, map rendering, and release build settings. Checked against the code and the debug APK (24.8 MB). No app code was changed.

## Security

| # | Area | Finding | Risk | Action |
|---|---|---|---|---|
| S1 | Components | Only `MainActivity` is exported (required for the launcher). `StudyReminderReceiver` is `exported="false"`. | OK | None |
| S2 | Network | Cleartext traffic disabled. No network calls in the app. No Firebase, analytics or ad SDKs. | OK | None |
| S3 | Secrets | No keys, passwords or keystores are tracked in git. Signing reads `STORE_PASSWORD` and `KEY_PASSWORD` from the environment. | OK | Keep the upload keystore outside the repo |
| S4 | Permissions | Only `POST_NOTIFICATIONS`, requested at runtime for study reminders. | OK | None |
| S5 | Backup | `allowBackup="false"`, yet `backup_rules.xml` and `data_extraction_rules.xml` still reference `sarala_kannada_prefs.xml`. The rules are dead and misleading. | Low | Remove the two XML rule files, or document why they remain |
| S6 | Local data | Learner name, XP, streak and progress are stored in plain SharedPreferences, on the device only. | Low | Acceptable under the stated data policy. Add encryption before storing anything sensitive |
| S7 | Intents | Maps and settings intents use fixed or encoded data. The Maps query contains only app-owned place names. | OK | None |
| S8 | Logging | Logs are limited to TTS status. R8 strips `Log.v/d/i` in release. | OK | None |
| S9 | Shrinking | R8 minify and resource shrinking are enabled for release. ProGuard keeps are minimal and match the code. | OK | Verify a release build once the upload keystore exists |

## Performance

| # | Area | Finding | Impact | Action |
|---|---|---|---|---|
| P1 | Poem images | Five JPEGs at 1200×896, 754 KB to 1.09 MB each (about 4.6 MB). About a fifth of the debug APK. | High (APK size, memory on load) | Re-encode to WebP at about 600 px wide. Target under 100 KB each |
| P2 | Map drawing | `KarnatakaMapArtwork` rebuilds every district `Path` (about 3,800 points) on each draw, so selection redraws all of it. | Medium (jank on selection) | Build the paths once in `remember` and reuse them. Hit-testing can stay as it is |
| P3 | Flag artwork | Canvas drawing of two rectangles. | OK | None |
| P4 | Threading | No `GlobalScope`, `runBlocking` or `Thread.sleep`. TTS callbacks update StateFlows. | OK | None |
| P5 | Lists | Home, Letters, Talk, Practice and Profile use lazy lists. Food and heritage filter on each recomposition over at most 62 items. | Low | Optional `remember` on filtered lists |
| P6 | Drawable decoding | `ImageBitmap.imageResource` is called in each `PosterCard`. Compose caches decoded resources, so this is acceptable. | Low | Optional: share one decoded bitmap per skin |
| P7 | Release size | Unknown until a release build runs (no upload keystore on this PC). The debug APK is 24.8 MB. | Unknown | Measure after the keystore is in place |
| P8 | Badge art | Production badge PNGs are 512 px and under 50 KB each. | OK | None |

## Recommended order

1. **P1**: re-encode the five poem images. Largest gain, lowest risk.
2. **P2**: cache the district paths in the map.
3. **S5**: remove the dead backup rules.
4. **P7**: measure the release bundle size once the upload keystore is set up.

## Status of fixes
- **P1 done:** five poem images re-encoded at 640 px, JPEG quality 80. Each is now about 46–102 KB (about 410 KB in total, down from about 4.6 MB). Three are just over the 100 KB target. WebP was not used because the Windows encoder here can't write it.
- **P2 done:** the district paths are built once per size with `drawWithCache`. Selection only changes the fill colour.
- **S5 done:** the unreferenced `backup_rules.xml` and `data_extraction_rules.xml` were removed. `allowBackup` stays `false`.
- **P7 open:** the release bundle size still needs measuring once the upload keystore is set up.
- Verified: build passes, 18 unit tests pass, the poem screen loads its artwork, and the map shows and selects districts on the phone. The debug APK went from 24.8 MB to 23.7 MB.
