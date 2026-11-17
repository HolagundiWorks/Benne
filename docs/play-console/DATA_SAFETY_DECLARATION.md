# Google Play Console — App Content & Data Safety Declaration

**App:** Sarala Kannada (`com.aistudio.saralakannada.kydwpx`)  
**Developer Contact:** `vishwabhiram@gmail.com`

Use the exact answers below when completing the **App Content** section in Google Play Console.

---

## 1. Privacy Policy
- **GitHub Pages Privacy Policy URL (Recommended):**
  1. Push this project to GitHub using AI Studio's **Push to GitHub** menu option.
  2. In your GitHub repository, open **Settings → Pages → Build and deployment**:
     - Source: **Deploy from a branch**
     - Branch: **`main`**, Folder: **`/docs`**
  3. Paste the resulting HTTPS URL into **Google Play Console → App content → Privacy policy**:
     - `https://<your-github-username>.github.io/<your-repo-name>/privacy-policy.html`
- **In-App Access:** Available offline inside **Settings (gear icon) → Legal & Compliance → Privacy Policy**.

---

## 2. Data Safety Form
- **Does your app collect or share any of the required user data types?**
  - Answer: **No**
- **Rationale:**
  - All user info (optional local display name in Settings, XP, streak, completed lessons, mastered flashcards, bookmarks, audio playback speed, reminder settings) is stored strictly on-device in private `SharedPreferences` (`sarala_kannada_prefs`).
  - Zero logins, zero accounts, zero analytics SDKs, zero ad SDKs, zero Firebase/cloud telemetry, and zero external network calls are included.
  - `network_security_config.xml` explicitly disables cleartext HTTP traffic (`cleartextTrafficPermitted="false"`).
  - **Local Data Reset Control:** Even though no cloud account exists, users can wipe 100% of local device state at any time via **Settings → Security & Local Privacy → Reset All Local Progress**.

---

## 3. Ads Declaration
- **Does your app contain ads?**
  - Answer: **No, my app does not contain ads.**

---

## 4. App Access
- **Are all or some parts of your app restricted based on login credentials, memberships, location, or other forms of authentication?**
  - Answer: **All functionality in my app is available without any access restrictions.** (No sign-in wall, no paywall, no region lock.)

---

## 5. Content Ratings (IARC)
- **Category:** Reference, News, or Educational
- **Violence / Sexuality / Language / Controlled Substances / Gambling:** **No** across all items.
- **Users Interact / Shares Location / Digital Purchases:** **No** across all items.
- **Expected Rating:** **Everyone / PEGI 3 / Rated for 3+**

---

## 6. Target Audience and Content
- **Target Age Group:** General Audience / Suitable for all ages (e.g., **13–15, 16–17, 18 and over** — or select **9–12+** if aiming for Families compliance; the app contains zero ads, zero tracking, and zero external links).
- **Appeal to Children:** Offline educational language tool; safe if reviewed under Families Policy if you choose to include under-13 ages.

---

## 7. Permissions & Sensitive APIs Justification
- `android.permission.POST_NOTIFICATIONS` (Android 13+ runtime opt-in):
  - Strictly optional. Only requested when the learner taps the **Daily Practice Reminder** switch or **Send Test Signal** inside System Console.
  - Uses `AlarmManager.setInexactRepeating(...)` — **does NOT request `SCHEDULE_EXACT_ALARM` or `USE_EXACT_ALARM`**.
- **No Sensitive/Restricted Permissions Declared:**
  - Zero `CAMERA`, `RECORD_AUDIO`, `ACCESS_FINE_LOCATION`, `READ_EXTERNAL_STORAGE`, `READ_MEDIA_IMAGES`, `READ_MEDIA_VIDEO`, `REQUEST_INSTALL_PACKAGES`, or `QUERY_ALL_PACKAGES`.
  - `<queries>` in `AndroidManifest.xml` declares only `android.intent.action.TTS_SERVICE` for local Android Text-to-Speech lookup.

---

## 8. Financial Features, Health, Government & News
- **Financial features:** None.
- **Health apps:** None.
- **Government apps:** Not a government app.
- **News app:** Not a news app.
- **Data safety / Security verification:**
  - `isMinifyEnabled = true` and `isShrinkResources = true` enabled for `release` builds with ProGuard rules stripping verbose/debug logs (`Log.v`, `Log.d`, `Log.i`).
  - `allowBackup="true"` scoped via `backup_rules.xml` and `data_extraction_rules.xml` strictly to `sarala_kannada_prefs.xml`.
