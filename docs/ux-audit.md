# Benne UX and app-structure audit

Checked on device (SM-M115F) and against the code. Scope: Home, Letters, Talk, Practice, Profile, Settings, Karnataka (Food, Heritage), lesson sheet. Not yet checked: quiz results, Poems, Heritage detail on device.

## 1. App structure (information architecture)

| Area | Current | Issue | Recommendation |
|---|---|---|---|
| Bottom bar | Home, Letters, Talk, Practice, Profile, Settings (6) | Settings is an action, not a section; it sits beside tabs | Keep five sections (Home, Letters, Talk, Practice, Profile). Move Settings into Profile, with a small gear in the header |
| Hidden sections | Food and Heritage open only from a Home card | Two full sections have no permanent place in the structure | Give them a home: under Talk ("Phrases, Food, Heritage") or a single "Explore" tab |
| Practice | Flashcards, Quiz, Poems in one segmented control | Three different activities under one label; Poems is a game, not practice | Keep Flashcards and Quiz under Practice. Move Poems to Explore or Play |
| Talk | Dialogues and Phrases under one segmented control | Sound structure; works | No change |
| Home | About 10 blocks before the chapter list | Too many competing entry points | See Hick's law below |
| Profile | Achievements, stats, stickers, favourites | Long single scroll | Group into sections with a clear order |

## 2. UX laws

| Law | Where it breaks | Evidence | Fix |
|---|---|---|---|
| **Hick's law** (more choices, slower decisions) | Home: hero quote, level card, badges, continue lesson, quick tiles, Explore Karnataka strip, Karnataka modules, flashcard spotlight, poetry spotlight, roadmap | About 10 blocks compete on the first screen | One primary action (Continue lesson) at the top. Everything else below, in order of use |
| **Miller's law** (about 7 items at once) | Explore Karnataka strip shows 14 stickers at once; Food list shows 62 items; Heritage 35 | Long lists with no grouping | Group by category with a collapsed view. Limit the first screen to a few items |
| **Jakob's law** (familiar patterns) | Settings is a sheet from the dock; Food and Heritage use custom cards | The dock does not match the tab pattern | Settings as a regular screen. Use the same card and list pattern across Food, Heritage and Letters |
| **Progressive disclosure** | Lesson sheet shows the meaning, phonetics and the Kannada all at once; flashcards show Kannada first and hide the meaning until flipped | Learners see everything at once or must flip to learn | Show meaning first, add phonetics and script on tap or as the learner advances |
| **Recognition over recall** | Locked achievements show a rule only in the screen-reader label, and the header hides locked ones; the speed badge can't unlock | Learners must remember what each badge means | Show the rule under every locked badge. Keep locked badges visible but clearly unlocked-or-not |
| **Feedback** | Speaking is silent when Kannada voice data is missing; no message says why | A learner tapping speak hears nothing and assumes the app is broken | Show a one-line message when Kannada speech is unavailable, with a link to the phone's voice setting |
| **Fitts's law** (targets) | Dock items are 54dp tall; header badges are not buttons; settings button 38dp | Small targets in the header and dock | Keep dock items at least 48dp; the speed badge (tappable) needs 48dp |
| **Aesthetic-usability / visual hierarchy** | Pop-art cards compete with each other; many decorations (curls, stickers, halftone) | Decoration competes with content on several screens | Keep decoration on hero and header cards only; content cards plain (rule already documented) |
| **Doherty threshold** (under 400 ms response) | Audio starts after a delay on first use (engine start) | Learners tap speak and wait | Warm the engine on launch, and show a loading state if it isn't ready |

## 3. Problems found in this audit

1. **Header badges** (fixed in `22ebb7f`): earned badges only, with a hint when none are earned.
2. **Kannada speech silently skipped** when the phone has no Kannada voice (see the Feedback row). Not yet fixed.
3. **Home is crowded** (Hick's law). Not yet fixed.
4. **Settings sits in the dock** as an action (structure). Not yet fixed.
5. **Food and Heritage have no permanent home** (structure). Not yet fixed.
6. **Locked speed badge can't unlock** until response times are recorded. Not yet fixed.
7. **Unit tests**: 10 of 11 fail because of the Robolectric offline path. This is a build issue, not a UX issue.

## 4. Proposed order of work

1. Feedback for missing Kannada voice (small, high impact on trust).
2. Home: one primary action, the rest grouped (largest UX gain).
3. Move Settings into Profile; give Food and Heritage a place under Talk or Explore.
4. Move Poems out of Practice.
5. Dock target sizes and lock-state labels on badges.
6. Fix the Robolectric setup so the tests run.
