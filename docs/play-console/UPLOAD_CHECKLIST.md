# Benne — Google Play upload checklist

**App:** Benne (ಬೆಣ್ಣೆ) — Learn Kannada
**Application ID:** `com.benne.kannada` (permanent once the first release is uploaded)
**Version:** 1.0 (versionCode 1)
**Status date:** see git history of this file

Status key: **Done** = verified here · **Ready** = prepared, needs your action in Play Console · **Blocked** = needs input from you

## 1. Build and signing

| Requirement | Status | Notes |
|---|---|---|
| Android App Bundle (AAB) | Done (debug) | `:app:bundleDebug` builds (`app-debug.aab`, about 17.7 MB unminified). |
| Release AAB | **Blocked** | `signingConfigs.release` reads `KEYSTORE_PATH`, `STORE_PASSWORD`, `KEY_PASSWORD` and `my-upload-key.jks`. None are in the repo (correctly, the keystore is git-ignored). Needed: an upload keystore and its passwords. |
| Play App Signing | Ready | Enrol in Play App Signing on first upload. Google holds the app signing key; the upload key is yours. |
| Target API level | Done | `targetSdk 36`, `minSdk 24`. Play requires a recent target API for new apps (currently API 35+). |
| Minified release build | Ready | `isMinifyEnabled = true` with `proguard-android-optimize.txt`. Verify once the release signing config exists. |
| Version code | Ready | `versionCode = 1`, `versionName = "1.0"`. Increase `versionCode` for every upload. |
| Permissions | Done | Only `POST_NOTIFICATIONS` (daily study reminders). Declare its purpose in the Play Console. No exact-alarm, location, camera or storage permissions. |
| Application ID | Done | Changed from the AI Studio ID to `com.benne.kannada` before any upload. |
| 64-bit libraries | Ready | Checked by Play on upload. |

## 2. Developer account and console

| Requirement | Status | Notes |
|---|---|---|
| Google Play developer account | **Blocked** | One-time registration fee and identity verification. Done by you. |
| Create app in Play Console | Ready | Name: **Benne**. Default language: English (India) or English (United States). |
| Countries / regions | Ready | Choose release countries. |

## 3. Policy declarations

| Requirement | Status | Notes |
|---|---|---|
| Privacy policy URL | **Blocked (hosting)** | Source: `docs/privacy-policy.html` (also `docs/play-console/PRIVACY_POLICY.md`). Must be on a public HTTPS URL before submission. Suggested: GitHub Pages from the Benne repo. |
| Data safety form | Ready | Answers are in `docs/play-console/DATA_SAFETY_DECLARATION.md`. Data stays on device; no account, no analytics, no ads. Confirm each answer in the console. |
| Content rating (IARC) | Ready | Questionnaire: educational app, no violence, no user-generated content, no purchases. Expected rating: Everyone. Complete in the console. |
| Target audience and content | Ready | Choose the age group. The app is general educational content; it is not designed for children specifically. If you select children (under 13), the Families policy applies and extra rules apply. |
| Ads declaration | Ready | Answer **No, the app does not contain ads**. |
| App access | Ready | No login or restricted features. Declare that all features are available without sign-in. |
| Government / news / health / finance declarations | Not applicable | None apply. |
| Terms of service | Ready | `docs/play-console/TERMS_OF_SERVICE.md`. Link it in the listing or the app's legal screen. |

## 4. Store listing

| Asset | Requirement | Status | File |
|---|---|---|---|
| App name | 30 characters max | Done | "Benne" (5 characters), as in `PLAY_STORE_LISTING.md`. |
| Short description | 80 characters max | Ready | See `PLAY_STORE_LISTING.md`. |
| Full description | 4000 characters max | Ready | See `PLAY_STORE_LISTING.md`. |
| App icon | 512 × 512 PNG, 32-bit | Done | `docs/play-console/assets/play_store_icon_512.png` (from the corrected app icon). |
| Feature graphic | 1024 × 500 PNG or JPG | Done | `docs/play-console/assets/feature_graphic_1024x500.png` (replaced; the old graphic had no Benne branding). |
| Phone screenshots | 2–8 images; 320–3840 px per side; aspect ratio at most 2:1 | Done | `docs/play-console/screenshots/` (720 × 1440). Four screens: Home, Practice, Dialogues, Letters. Add a results screen and a lesson sheet if you have them. |
| Tablet screenshots | Optional | Not prepared | Only needed for tablet listings. |
| Promo video | Optional | Not prepared | — |

## 5. Quality checks before submission

| Check | Status | Notes |
|---|---|---|
| Unit tests | Done | `ExampleUnitTest` and `ExampleRobolectricTest` pass via `gradle :app:testDebugUnitTest`. |
| Crash-free run on device | Done (debug) | Launched and navigated on a Samsung SM-M115F. |
| Accessibility | Partly checked | Icons and key images have content descriptions. Check with TalkBack before release. |
| Kannada text rendering | Done | Noto Sans Kannada is bundled. |
| Permission prompt | Ready | The notification permission is requested at runtime on Android 13+. |

## 6. Generate upload key & deploy Beta (Open Testing)

1. **Generate your upload keystore locally** (back up the `.jks` file and passwords safely; `*.jks` is git-ignored):
   ```bash
   keytool -genkeypair -v \
     -keystore my-upload-key.jks \
     -alias upload \
     -keyalg RSA -keysize 2048 -validity 10000
   ```
   *(The alias **must** be `upload` to match `signingConfigs.release` in `app/build.gradle.kts`.)*
2. **Build the signed release Android App Bundle (`.aab`)**:
   ```bash
   export KEYSTORE_PATH="$(pwd)/my-upload-key.jks"
   export STORE_PASSWORD="<your-store-password>"
   export KEY_PASSWORD="<your-key-password>"
   gradle :app:bundleRelease
   ```
   Output: `app/build/outputs/bundle/release/app-release.aab` *(or export directly via AI Studio's **Generate APK/AAB** menu).*
3. **Publish your Privacy Policy over HTTPS**:
   Push to GitHub and enable **Settings → Pages → Deploy from a branch (`main` / `/docs`)** to serve `docs/privacy-policy.html`.
4. **Deploy to Beta (Open Testing) in Google Play Console**:
   - Create the app **Benne** (`com.benne.kannada`) and enroll in **Play App Signing** when prompted.
   - Complete **App content** (Privacy Policy URL, Data Safety from `DATA_SAFETY_DECLARATION.md`, Content Rating, Ads = No, App Access = No restrictions, Target Audience) and **Store listing** (`PLAY_STORE_LISTING.md`, `assets/`, `screenshots/`).
   - Open **Testing → Open testing** (or **Closed testing** first if required for a new personal developer account), upload `app-release.aab`, select countries/regions, and submit for review. Once approved, share the Play Console **opt-in URL** for public beta testers.

## 7. Known gaps (environment / account actions required)

- **Upload keystore & passwords:** Private signing keys cannot be generated or stored on your behalf in the shared cloud workspace; generate `my-upload-key.jks` locally (or export via AI Studio's APK/AAB generator).
- **Privacy policy hosting & Play Console upload:** Requires your GitHub Pages toggle and Google Play Developer Console account to upload `app-release.aab` and publish the Beta track.
