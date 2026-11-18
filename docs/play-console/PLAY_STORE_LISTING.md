# Google Play Store Listing & Release Checklist — Benne

**Application ID:** `com.benne.kannada`  
**Version:** `1.0` (`versionCode = 1`)  
**Min SDK / Target SDK:** `24` / `36`  
**Support Email:** `vishwabhiram@gmail.com`

---

## 1. Store Listing Copy (Play Policy Compliant)

### App Name (Max 30 characters — 5 chars)
```
Benne
```
*(No emojis, no ALL CAPS, no promotional buzzwords like "Free" or "Best". Matches launcher `app_name`.)*

### Short Description (Max 80 characters — 76 chars)
```
Learn Kannada script, flashcards, and everyday Bengaluru spoken dialogues.
```

### Full Description (Max 4,000 characters)
```
Learn spoken and written Kannada effortlessly with Benne (ಬೆಣ್ಣೆ) — an offline-first, pop-styled Kannada language learning companion.

Designed with expressive display typography, high-contrast poster cards, and a clean light-only layout, Benne helps beginners, travelers, and Bengaluru residents master real everyday Kannada at their own pace.

KEY FEATURES

1. Interactive 3D Vocabulary Flashcards
• Flip-to-reveal translations, phonetic pronunciations, and real usage examples
• Native Kannada Text-to-Speech pronunciation with adjustable playback speed (0.75x, 0.85x, 1.0x)
• Track mastered vocabulary cards and filter across Essentials, Food & Dining, Travel & Auto, Commerce, and People & Life

2. Complete Varnamale Script Matrix
• Master 14 Swaragalu (vowels) and 34 Vyanjanagalu (consonants)
• Interactive inspection sheets with acoustic audio, stroke cues, and sample words

3. Everyday Bengaluru Spoken Dialogues
• Real-world scenarios: Auto Rickshaw Ride, Bengaluru Darshini, KR Market Mandi, and City Metro Directions
• Line-by-line Kannada script, Romanized transliteration, English translation, and toggleable subtitles

4. Evaluation Quiz & Lexicon Archive
• Test your letter recognition and conversational comprehension with instant feedback
• Searchable offline Kannada–English lexicon with instant bookmarks

5. Daily Study Reminders & Total Privacy
• Optional daily practice reminders (strictly opt-in notifications)
• 100% offline-first with zero ads, zero trackers, and zero account sign-in required
• Reset all locally stored progress anytime from the in-app System Console
```

### Store Categorization & Tags
- **App Type:** App
- **Category:** Education
- **Tags:** Language learning, Kannada, Indian languages, Flashcards, Offline learning

---

## 2. Graphic Assets Ready for Upload

| Asset | Requirements | Local Path | Status |
| :--- | :--- | :--- | :--- |
| **App Icon** | 512 x 512 px, PNG, <= 1 MB | `docs/play-console/assets/play_store_icon_512.png` | Ready |
| **Feature Graphic** | 1024 x 500 px, PNG/JPG, <= 15 MB | `docs/play-console/assets/feature_graphic_1024x500.png` | Ready |
| **Adaptive Launcher Icon** | Foreground + background in APK | `app/src/main/res/mipmap-*` & `res/drawable/ic_launcher_*` | Ready |
| **Phone Screenshots** | 2–8 screenshots (16:9 or 9:16, min 320px, max 3840px) | `docs/play-console/screenshots/01_home.png` through `04_letters.png` | Ready |

---

## 3. Pre-Submission Technical Audit Checklist

- [x] **Unique Application ID:** `com.benne.kannada`
- [x] **Target SDK:** `36` (exceeds Google Play Android 15/API 35 requirement)
- [x] **R8 Code Shrinking & Resource Shrinking:** `isMinifyEnabled = true`, `isShrinkResources = true` in `release`
- [x] **Unused SDKs Removed:** Firebase, Room, Retrofit, Moshi, OkHttp, Coil commented out to minimize AAB footprint and avoid unused transitive permissions
- [x] **Network Security:** `network_security_config.xml` sets `cleartextTrafficPermitted="false"`
- [x] **Backup Rules:** Scoped explicitly to `sarala_kannada_prefs.xml`
- [x] **Notifications (Android 13+):** `POST_NOTIFICATIONS` requested at runtime only when user enables Daily Practice Reminders; uses `AlarmManager.setInexactRepeating` (no restricted exact-alarm permission) and `PendingIntent.FLAG_IMMUTABLE`
- [x] **Edge-to-Edge & Accessibility:** `enableEdgeToEdge()`, `statusBarsPadding()` on top bar, 48dp minimum touch targets on primary controls, `contentDescription` on icons
- [x] **In-App Legal Access & Data Reset:** Privacy Policy, Terms of Service, Open Source Notices, and local progress reset available inside System Console
- [ ] **Host Privacy Policy URL:** Enable GitHub Pages (`main` branch, `/docs` folder) to serve `docs/privacy-policy.html` at HTTPS and enter the URL in Play Console
- [ ] **Build Signed Android App Bundle (`.aab`):** Export or build with your Play upload keystore (`gradle :app:bundleRelease` with `KEYSTORE_PATH`, `STORE_PASSWORD`, `KEY_PASSWORD` environment variables)
- [ ] **Upload Store Listing & Complete Testing:** Upload `play_store_icon_512.png`, `feature_graphic_1024x500.png`, and `docs/play-console/screenshots/*.png`, then complete Internal/Closed Testing before Production rollout
