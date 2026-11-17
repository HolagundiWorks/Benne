package com.example.data

import com.example.model.AlphabetCategory
import com.example.model.AlphabetItem
import com.example.model.ConversationDialogue
import com.example.model.DialogueLine
import com.example.model.DictionaryPhrase
import com.example.model.LessonMilestone
import com.example.model.LessonStep
import com.example.model.QuizQuestion
import com.example.model.QuizType

object SaralaKannadaData {

    val vowels: List<AlphabetItem> = listOf(
        AlphabetItem("ಅ", "A", AlphabetCategory.VOWELS, "ಅರಸ", "King", "Arasa", "Short 'u' sound as in 'cup' or 'run'", "Start with a small round loop, curve up and loop back downwards with an open bottom arc."),
        AlphabetItem("ಆ", "Aa", AlphabetCategory.VOWELS, "ಆನೆ", "Elephant", "Aane", "Long 'aa' sound as in 'father' or 'car'", "Same as 'ಅ' but with an extended curved flourish rightwards at the tail."),
        AlphabetItem("ಇ", "I", AlphabetCategory.VOWELS, "ಇಲಿ", "Mouse / Rat", "Ili", "Short 'i' sound as in 'pin' or 'sit'", "Curved rounded figure with a gentle wave at the top."),
        AlphabetItem("ಈ", "Ee", AlphabetCategory.VOWELS, "ಈಜು", "Swimming", "Eeju", "Long 'ee' sound as in 'feel' or 'see'", "Round base with an open loop and a top curved crest."),
        AlphabetItem("ಉ", "U", AlphabetCategory.VOWELS, "ಉಡುಗೊರೆ", "Gift", "Udugore", "Short 'oo' sound as in 'put' or 'book'", "Resembles an open curve with a top hook and rightward base tail."),
        AlphabetItem("ಊ", "Oo", AlphabetCategory.VOWELS, "ಊಟ", "Meal / Food", "Oota", "Long 'oo' sound as in 'moon' or 'food'", "Like 'ಉ' with a looping tail extension curled at the back."),
        AlphabetItem("ಋ", "Ru", AlphabetCategory.VOWELS, "ಋಷಿ", "Sage", "Rushi", "Vocalic 'ri' sound as in 'rhythm'", "Two rounded waves side by side with a downward stroke."),
        AlphabetItem("ಎ", "E", AlphabetCategory.VOWELS, "ಎಲೆ", "Leaf", "Ele", "Short 'e' sound as in 'pet' or 'bed'", "Rounded shape with an upper open mouth."),
        AlphabetItem("ಏ", "Ae", AlphabetCategory.VOWELS, "ಏಣಿ", "Ladder", "Eani", "Long 'ae' sound as in 'play' or 'gate'", "Like 'ಎ' with an elongated upward flourish top accent."),
        AlphabetItem("ಐ", "Ai", AlphabetCategory.VOWELS, "ಐದು", "Five (5)", "Aidu", "Diphthong 'ai' sound as in 'sky' or 'eye'", "A combination curve forming the twin sound."),
        AlphabetItem("ಒ", "O", AlphabetCategory.VOWELS, "ಒಂಟೆ", "Camel", "Onte", "Short 'o' sound as in 'go' or 'orange'", "Smooth round looping letter with center arch."),
        AlphabetItem("ಓ", "Oo", AlphabetCategory.VOWELS, "ಓದು", "Study / Read", "Oodu", "Long 'o' sound as in 'boat' or 'home'", "Like 'ಒ' with a pronounced top accent hook."),
        AlphabetItem("ಔ", "Au", AlphabetCategory.VOWELS, "ಔಷಧ", "Medicine", "Aushadha", "Diphthong 'ow' sound as in 'cow' or 'loud'", "Like 'ಒ' with an accompanying vowel ligature."),
        AlphabetItem("ಅಂ", "Am", AlphabetCategory.YOGAVAHA, "ಅಂಕಿ", "Number", "Anki", "Anusvara: nasal ending 'am/an'", "Letter 'ಅ' followed by a simple small circle (0)."),
        AlphabetItem("ಅಃ", "Aha", AlphabetCategory.YOGAVAHA, "ಅಂತಃಪುರ", "Palace quarters", "Antahpura", "Visarga: soft breathy 'h' echo", "Letter 'ಅ' followed by two vertical dots (:).")
    )

    val consonants: List<AlphabetItem> = listOf(
        AlphabetItem("ಕ", "Ka", AlphabetCategory.CONSONANTS, "ಕಮಲ", "Lotus", "Kamala", "Crisp 'k' as in 'kite'", "Form an open circle with a middle bar crossing through."),
        AlphabetItem("ಖ", "Kha", AlphabetCategory.CONSONANTS, "ಖಡ್ಗ", "Sword", "Khadga", "Aspirated 'kh' with a puff of air", "Curved bottom with a hollow loop on the left."),
        AlphabetItem("ಗ", "Ga", AlphabetCategory.CONSONANTS, "ಗಂಟೆ", "Bell", "Gante", "Soft 'g' as in 'garden'", "Smooth arch open at the bottom with top tala-kattu."),
        AlphabetItem("ಘ", "Gha", AlphabetCategory.CONSONANTS, "ಘಂಟೆ", "Hour / Gong", "Ghante", "Aspirated 'gh' sound", "Like 'ಗ' with a center stroke downwards."),
        AlphabetItem("ಙ", "Nga", AlphabetCategory.CONSONANTS, "ವಾಙ್ಮಯ", "Literature", "Vangmaya", "Nasal 'ng' sound as in 'sing'", "Rare nasal letter."),
        AlphabetItem("ಚ", "Cha", AlphabetCategory.CONSONANTS, "ಚಮಚ", "Spoon", "Chamacha", "Clear 'ch' as in 'chair'", "Curled head with a sweeping right loop."),
        AlphabetItem("ಛ", "Chha", AlphabetCategory.CONSONANTS, "ಛತ್ರಿ", "Umbrella", "Chhatri", "Aspirated 'chh' as in 'match-house'", "Like 'ಚ' with an emphatic underline accent."),
        AlphabetItem("ಜ", "Ja", AlphabetCategory.CONSONANTS, "ಜಲ", "Water", "Jala", "Clear 'j' as in 'joy'", "Two rounded lobes meeting in the middle."),
        AlphabetItem("ಝ", "Jha", AlphabetCategory.CONSONANTS, "ಝರಿ", "Stream / Waterfall", "Jhari", "Aspirated 'jh' as in 'hedge-hog'", "Elaborate looped glyph."),
        AlphabetItem("ಞ", "Nya", AlphabetCategory.CONSONANTS, "ಜ್ಞಾನ", "Knowledge", "Jnaana", "Palatal nasal sound", "Classic conjunct nasal letter."),
        AlphabetItem("ಟ", "Ta", AlphabetCategory.CONSONANTS, "ಟೊಮೇಟೊ", "Tomato", "Tomato", "Retroflex hard 'T' tongue curled back", "Open rightward arc with a top hook."),
        AlphabetItem("ಠ", "Tha", AlphabetCategory.CONSONANTS, "ಠಸ್ಸೆ", "Stamp / Seal", "Thasse", "Aspirated retroflex 'Th'", "Full circle with a small center dot."),
        AlphabetItem("ಡ", "Da", AlphabetCategory.CONSONANTS, "ಡಮರು", "Small Drum", "Damaru", "Retroflex 'D' sound", "Curved wave with retroflex mouth feel."),
        AlphabetItem("ಢ", "Dha", AlphabetCategory.CONSONANTS, "ಢಕ್ಕೆ", "Big Drum", "Dhakke", "Aspirated retroflex 'Dh'", "Like 'ಡ' with bottom accent."),
        AlphabetItem("ಣ", "Na", AlphabetCategory.CONSONANTS, "ಹಣ", "Money", "Hana", "Retroflex 'N' tongue curved to roof of mouth", "Triple looped curvy glyph."),
        AlphabetItem("ತ", "Tha", AlphabetCategory.CONSONANTS, "ತಾಯಿ", "Mother", "Thaayi", "Dental soft 'th' as in 'think' or 'tabla'", "Open rounded crest with inward hook."),
        AlphabetItem("ಥ", "Thha", AlphabetCategory.CONSONANTS, "ರಥ", "Chariot", "Ratha", "Aspirated dental 'thh'", "Round body with an internal dot and bottom stroke."),
        AlphabetItem("ದ", "Da", AlphabetCategory.CONSONANTS, "ದೀಪ", "Lamp", "Deepa", "Dental soft 'd' as in 'the'", "Smooth downward scoop."),
        AlphabetItem("ಧ", "Dha", AlphabetCategory.CONSONANTS, "ಧನ", "Wealth", "Dhana", "Aspirated dental 'dh'", "Scoop with mid-level strike."),
        AlphabetItem("ನ", "Na", AlphabetCategory.CONSONANTS, "ನಮಸ್ಕಾರ", "Greeting / Hello", "Namaskaara", "Standard dental 'n' as in 'nest'", "Curving arch with lower hook."),
        AlphabetItem("ಪ", "Pa", AlphabetCategory.CONSONANTS, "ಪುಸ್ತಕ", "Book", "Pustaka", "Soft 'p' as in 'pen'", "Cup shaped glyph with top tick."),
        AlphabetItem("ಫ", "Pha", AlphabetCategory.CONSONANTS, "ಫಲ", "Fruit / Result", "Phala", "Aspirated 'ph' as in 'phone'", "Like 'ಪ' with middle drop line."),
        AlphabetItem("ಬ", "Ba", AlphabetCategory.CONSONANTS, "ಬೆಕ್ಕು", "Cat", "Bekku", "Soft 'b' as in 'ball'", "Round loop similar to '3' shape."),
        AlphabetItem("ಭ", "Bha", AlphabetCategory.CONSONANTS, "ಭಾರತ", "India", "Bhaarata", "Aspirated 'bh'", "Like 'ಬ' with bottom downward stroke."),
        AlphabetItem("ಮ", "Ma", AlphabetCategory.CONSONANTS, "ಮನೆ", "Home / House", "Mane", "Soft 'm' as in 'mango'", "Round loop with a top right circle."),
        AlphabetItem("ಯ", "Ya", AlphabetCategory.CONSONANTS, "ಯಾನ", "Journey", "Yaana", "Soft 'y' as in 'yard'", "Wide bowl with large left loop."),
        AlphabetItem("ರ", "Ra", AlphabetCategory.CONSONANTS, "ರೈಲು", "Train", "Railu", "Rolling 'r' tap with tongue", "Simple neat circle with top accent."),
        AlphabetItem("ಲ", "La", AlphabetCategory.CONSONANTS, "ಲತೆ", "Creeper / Vine", "Lathe", "Smooth 'l' as in 'love'", "Two upward curving lobes."),
        AlphabetItem("ವ", "Va", AlphabetCategory.CONSONANTS, "ವನ", "Forest", "Vana", "Gentle 'v/w' sound", "Single rounded circle with right tick."),
        AlphabetItem("ಶ", "Sha", AlphabetCategory.CONSONANTS, "ಶಂಖ", "Conch", "Shankha", "Palatal 'sh' as in 'sugar'", "Elegant arched form."),
        AlphabetItem("ಷ", "Sha", AlphabetCategory.CONSONANTS, "ಷಣ್ಮುಖ", "Six-faced Deity", "Shanmukha", "Retroflex hard 'sh'", "Resembles 'ಪ' with cross diagonal stroke."),
        AlphabetItem("ಸ", "Sa", AlphabetCategory.CONSONANTS, "ಸೂರ್ಯ", "Sun", "Soorya", "Crisp 's' as in 'sun'", "Arched letter with upper crest."),
        AlphabetItem("ಹ", "Ha", AlphabetCategory.CONSONANTS, "ಹೂವು", "Flower", "Hoovu", "Warm 'h' as in 'hope'", "Double curving letterform."),
        AlphabetItem("ಳ", "La", AlphabetCategory.CONSONANTS, "ಕನ್ನಡದ ಕಹಳೆ", "Trumpet", "Kahale", "Retroflex hard 'L' uniquely prominent in Kannada!", "Wide double round retroflex curl.")
    )

    val kagunitaSamples: List<AlphabetItem> = listOf(
        AlphabetItem("ಕ", "Ka", AlphabetCategory.KAGUNITA, "ಕಣ್ಣು", "Eye", "Kannu", "Base consonant sound", "Root consonant with talakattu"),
        AlphabetItem("ಕಾ", "Kaa", AlphabetCategory.KAGUNITA, "ಕಾಲು", "Leg / Foot", "Kaalu", "Ka + Aa vowel sign", "Add Dirgha accent rightwards"),
        AlphabetItem("ಕಿ", "Ki", AlphabetCategory.KAGUNITA, "ಕಿವಿ", "Ear", "Kivi", "Ka + I vowel sign", "Top curved loop (Gudisu)"),
        AlphabetItem("ಕೀ", "Kee", AlphabetCategory.KAGUNITA, "ಕೀಲಿ", "Key", "Keeli", "Ka + Ee vowel sign", "Top curved loop with extended hook (Gudisu Dirgha)"),
        AlphabetItem("ಕು", "Ku", AlphabetCategory.KAGUNITA, "ಕುದುರೆ", "Horse", "Kudure", "Ka + U vowel sign", "Bottom hook tail (Kombu)"),
        AlphabetItem("ಕೂ", "Koo", AlphabetCategory.KAGUNITA, "ಕೂದಲು", "Hair", "Koodalu", "Ka + Oo vowel sign", "Bottom hook with loop (Kombu Dirgha)"),
        AlphabetItem("ಕೆ", "Ke", AlphabetCategory.KAGUNITA, "ಕೆಂಪು", "Red color", "Kempu", "Ka + E vowel sign", "Ettva top marker"),
        AlphabetItem("ಕೇ", "Kae", AlphabetCategory.KAGUNITA, "ಕೇಕ್", "Cake", "Cake", "Ka + Ae vowel sign", "Ettva with extended crest"),
        AlphabetItem("ಕೈ", "Kai", AlphabetCategory.KAGUNITA, "ಕೈ", "Hand", "Kai", "Ka + Ai vowel sign", "Aittva bottom flourishing swirl"),
        AlphabetItem("ಕೊ", "Ko", AlphabetCategory.KAGUNITA, "ಕೊಡೆ", "Umbrella", "Kode", "Ka + O vowel sign", "Ottva compound vowel sign"),
        AlphabetItem("ಕೋ", "Koo", AlphabetCategory.KAGUNITA, "ಕೋತಿ", "Monkey", "Kooti", "Ka + Oo vowel sign", "Ottva with Dirgha"),
        AlphabetItem("ಕೌ", "Kau", AlphabetCategory.KAGUNITA, "ಕೌತುಕ", "Wonder / Curiosity", "Kautuka", "Ka + Au vowel sign", "Auttva attached accent"),
        AlphabetItem("ಕಂ", "Kam", AlphabetCategory.KAGUNITA, "ಕಂಪನ", "Vibration", "Kampana", "Ka + Anusvara (circle)", "Consonant followed by 0"),
        AlphabetItem("ಕಃ", "Kaha", AlphabetCategory.KAGUNITA, "ದುಃಖ", "Sorrow", "Duhkha", "Ka + Visarga (two dots)", "Consonant followed by :")
    )

    val milestones: List<LessonMilestone> = listOf(
        LessonMilestone(
            id = "lesson_1",
            levelNumber = 1,
            titleKannada = "ವರ್ಣಮಾಲೆ - ಸ್ವರಗಳು",
            titleEnglish = "Kannada Vowels (Swaragalu)",
            description = "Master the 14 essential vowel sounds from ಅ to ಔ that form the soul of the language.",
            iconEmoji = "🔤",
            badgeTag = "Foundation",
            steps = listOf(
                LessonStep("s1", "ಅ & ಆ (A & Aa)", "ಅ - ಅರಸ | ಆ - ಆನೆ", "A - Arasa | Aa - Aane", "A (King) and Aa (Elephant). Notice how 'ಆ' elongates the breath.", "Short 'a' vs elongated 'aa'"),
                LessonStep("s2", "ಇ & ಈ (I & Ee)", "ಇ - ಇಲಿ | ಈ - ಈಜು", "I - Ili | Ee - Eeju", "I (Mouse) and Ee (Swimming).", "Top curl marks the 'ee' sound"),
                LessonStep("s3", "ಉ & ಊ (U & Oo)", "ಉ - ಉಡುಗೊರೆ | ಊ - ಊಟ", "U - Udugore | Oo - Oota", "U (Gift) and Oo (Food/Meal). Essential for daily food conversations!", "Kombu accent indicates 'u'"),
                LessonStep("s4", "ಎ, ಏ & ಐ (E, Ae, Ai)", "ಎ - ಎಲೆ | ಏ - ಏಣಿ | ಐ - ಐದು", "E - Ele | Ae - Eani | Ai - Aidu", "Ele (Leaf), Eani (Ladder), Aidu (Five).", "Ascending pitch in pronunciation"),
                LessonStep("s5", "ಒ, ಓ & ಔ (O, Oo, Au)", "ಒ - ಒಂಟೆ | ಓ - ಓದು | ಔ - ಔಷಧ", "O - Onte | Oo - Oodu | Au - Aushadha", "Onte (Camel), Oodu (Study), Aushadha (Medicine).", "Round lips for warm deep sounds")
            )
        ),
        LessonMilestone(
            id = "lesson_2",
            levelNumber = 2,
            titleKannada = "ದಿನನಿತ್ಯದ ಶುಭಾಶಯಗಳು",
            titleEnglish = "Daily Greetings & Courtesies",
            description = "Everyday respectful greetings to connect with locals, neighbors, and colleagues.",
            iconEmoji = "🙏",
            badgeTag = "Everyday Spoken",
            steps = listOf(
                LessonStep("s2_1", "ನಮಸ್ಕಾರ (Hello)", "ನಮಸ್ಕಾರ!", "Namaskaara!", "Hello / Greetings. Universal polite greeting in Karnataka.", "Use with hands joined or warm smile"),
                LessonStep("s2_2", "ಹೇಗಿದ್ದೀರಾ? (How are you?)", "ನೀವು ಹೇಗಿದ್ದೀರಾ?", "Neevu hegiddeera?", "How are you? (Respectful / Formal).", "For friends say 'Hegidya?'"),
                LessonStep("s2_3", "ನಾನು ಚೆನ್ನಾಗಿದ್ದೇನೆ (I am fine)", "ನಾನು ಚೆನ್ನಾಗಿದ್ದೇನೆ, ಧನ್ಯವಾದ!", "Naanu chennaagiddene, dhanyavaada!", "I am fine, thank you!", "Chennaagi means good/nice"),
                LessonStep("s2_4", "ನಿಮ್ಮ ಹೆಸರು ಏನು? (What is your name?)", "ನಿಮ್ಮ ಹೆಸರು ಏನು?", "Nimma hesaru enu?", "What is your name?", "Answer with 'Nanna hesaru...'"),
                LessonStep("s2_5", "ಮತ್ತೆ ಸಿಗೋಣ (See you again)", "ಆಯ್ತು, ಮತ್ತೆ ಸಿಗೋಣ!", "Aaythu, matte sigona!", "Alright, see you again / Goodbye!", "Aaythu means ok/done")
            )
        ),
        LessonMilestone(
            id = "lesson_3",
            levelNumber = 3,
            titleKannada = "ಸಂಖ್ಯೆಗಳು & ಲೆಕ್ಕಾಚಾರ",
            titleEnglish = "Numbers (1 to 20 & Tens)",
            description = "Crucial for prices, bus routes, bargaining, and telling time.",
            iconEmoji = "🔢",
            badgeTag = "Essential Utility",
            steps = listOf(
                LessonStep("s3_1", "೧ ರಿಂದ ೫ (1 to 5)", "ಒಂದು, ಎರಡು, ಮೂರು, ನಾಲ್ಕು, ಐದು", "Ondu, Eradu, Mooru, Naalku, Aidu", "1 (Ondu), 2 (Eradu), 3 (Mooru), 4 (Naalku), 5 (Aidu).", "Counting on fingers"),
                LessonStep("s3_2", "೬ ರಿಂದ ೧೦ (6 to 10)", "ಆರು, ಏಳು, ಎಂಟು, ಒಂಬತ್ತು, ಹತ್ತು", "Aaru, Ealu, Entu, Ombattu, Hattu", "6 (Aaru), 7 (Ealu), 8 (Entu), 9 (Ombattu), 10 (Hattu).", "Ombattu = 9, Hattu = 10"),
                LessonStep("s3_3", "ಹತ್ತರ ಗುಣಕಗಳು (Tens)", "ಇಪ್ಪತ್ತು, ಮೂವತ್ತು, ನಲವತ್ತು, ಐವತ್ತು, ನೂರು", "Ippattu, Moovattu, Nalavattu, Aivattu, Nooru", "20 (Ippattu), 30 (Moovattu), 40 (Nalavattu), 50 (Aivattu), 100 (Nooru).", "Used everywhere in shops"),
                LessonStep("s3_4", "ಎಷ್ಟು? (How much?)", "ಇದು ಎಷ್ಟು ರೂಪಾಯಿ?", "Idu eshtu roopaayi?", "How many rupees is this?", "Eshtu = How much / How many")
            )
        ),
        LessonMilestone(
            id = "lesson_4",
            levelNumber = 4,
            titleKannada = "ನಮ್ಮ ಆಟೋ & ಪ್ರಯಾಣ",
            titleEnglish = "Auto Rickshaw & City Travel",
            description = "Navigate Bengaluru roads like a local! Talk to auto drivers with confidence.",
            iconEmoji = "🛺",
            badgeTag = "Bengaluru Survival",
            steps = listOf(
                LessonStep("s4_1", "ಆಟೋ ಕರೆಯುವುದು", "ಅಣ್ಣಾ, ಮೆಜೆಸ್ಟಿಕ್‌ಗೆ ಬರ್ತೀರಾ?", "Anna, Majestic-ge bartheera?", "Brother, will you come to Majestic?", "Addressing as 'Anna' (brother) shows respect"),
                LessonStep("s4_2", "ಮೀಟರ್ ಹಾಕಿ", "ದಯವಿಟ್ಟು ಮೀಟರ್ ಹಾಕಿ ಅಣ್ಣಾ.", "Dayavittu meter haaki anna.", "Please turn on the meter, brother.", "Meter haaki is universally understood"),
                LessonStep("s4_3", "ದಾರಿ ಹೇಳುವುದು", "ಇಲ್ಲಿಂದ ನೇರವಾಗಿ ಹೋಗಿ, ಸಿಗ್ನಲ್‌ನಲ್ಲಿ ಬಲಕ್ಕೆ.", "Illinda neravaagi hogi, signal-nalli balakke.", "Go straight from here, turn right at the traffic signal.", "Balakke = Right, Edakke = Left"),
                LessonStep("s4_4", "ಇಲ್ಲಿ ನಿಲ್ಲಿಸಿ", "ಇಲ್ಲಿ ಮುಂದೆ ನಿಲ್ಲಿಸಿ, ಎಷ್ಟು ಆಯ್ತು?", "Illi munde nillisi, eshtu aaythu?", "Stop here ahead, how much did it cost?", "Illi nillisi = Stop here")
            )
        ),
        LessonMilestone(
            id = "lesson_5",
            levelNumber = 5,
            titleKannada = "ಕಾಫಿ, ತಿಂಡಿ & ಊಟ",
            titleEnglish = "Darshini Cafe & Food Ordering",
            description = "Order crisp masala dosa, filter coffee, and meals in Karnataka Darshinis.",
            iconEmoji = "☕",
            badgeTag = "Food Culture",
            steps = listOf(
                LessonStep("s5_1", "ತಿಂಡಿ ಕೇಳುವುದು", "ತಿಂಡಿಗೆ ಏನಿದೆ?", "Thindige enide?", "What breakfast items are available?", "Thindi = breakfast/snacks"),
                LessonStep("s5_2", "ದೋಸೆ ಆರ್ಡರ್", "ಒಂದು ಮಸಾಲೆ ದೋಸೆ ಕೊಡಿ.", "Ondu masaale dose kodi.", "Give one masala dosa, please.", "Kodi = Please give"),
                LessonStep("s5_3", "ಫಿಲ್ಟರ್ ಕಾಫಿ", "ಒಂದು ಸ್ಟ್ರಾಂಗ್ ಫಿಲ್ಟರ್ ಕಾಫಿ, ಸಕ್ಕರೆ ಕಡಿಮೆ.", "Ondu strong filter coffee, sakkare kadime.", "One strong filter coffee, less sugar.", "Sakkare = Sugar, Kadime = Less"),
                LessonStep("s5_4", "ನೀರು ಮತ್ತು ಬಿಲ್", "ಸ್ವಲ್ಪ ಕುಡಿಯುವ ನೀರು ಕೊಡಿ, ಬಿಲ್ ಎಷ್ಟು?", "Swalpa kudiyuva neeru kodi, bill eshtu?", "Please give some drinking water, how much is the bill?", "Neeru = Water, Swalpa = A little")
            )
        ),
        LessonMilestone(
            id = "lesson_6",
            levelNumber = 6,
            titleKannada = "ಶಾಪಿಂಗ್ & ಮಾರುಕಟ್ಟೆ",
            titleEnglish = "Market Bargaining & Shopping",
            description = "Buy fresh vegetables, fruits, and groceries at local mandis.",
            iconEmoji = "🛍️",
            badgeTag = "Shopping",
            steps = listOf(
                LessonStep("s6_1", "ಬೆಲೆ ವಿಚಾರಣೆ", "ಈ ಟೊಮೇಟೊ ಕೆಜಿಗೆ ಎಷ್ಟು?", "Ee tomato kg-ge eshtu?", "How much per kg for these tomatoes?", "Kg-ge eshtu = How much per kilo"),
                LessonStep("s6_2", "ಕಡಿಮೆ ಮಾಡಿ", "ಸ್ವಲ್ಪ ಜಾಸ್ತಿ ಆಯ್ತು, ಕಡಿಮೆ ಮಾಡಿ ಅಣ್ಣಾ.", "Swalpa jaasti aaythu, kadime maadi anna.", "It's a bit high, please reduce the price, brother.", "Jaasti = More/High, Kadime = Less"),
                LessonStep("s6_3", "ತಾಜಾ ಇದೆಯಾ?", "ಇದು ತಾಜಾ ತರಕಾರಿನಾ?", "Idu taaja tharakaarina?", "Are these vegetables fresh?", "Tharakaari = Vegetables"),
                LessonStep("s6_4", "ಚಿಲ್ಲರೆ ಕೊಡಿ", "ಚಿಲ್ಲರೆ ಕೊಡಿ, ಗೂಗಲ್ ಪೇ ಮಾಡ್ತೀನಿ.", "Chillare kodi, Google Pay maadtini.", "Give loose change, or I will pay via Google Pay.", "Chillare = Coins / Loose change")
            )
        )
    )

    val dialogues: List<ConversationDialogue> = listOf(
        ConversationDialogue(
            id = "dial_auto",
            titleKannada = "ಆಟೋ ಪ್ರಯಾಣ",
            titleEnglish = "Booking an Auto Rickshaw",
            locationTag = "Indiranagar 100ft Road",
            iconEmoji = "🛺",
            contextDescription = "Negotiating a ride to MG Road and giving turn-by-turn directions.",
            lines = listOf(
                DialogueLine("Learner", true, "ನಮಸ್ಕಾರ ಅಣ್ಣಾ, ಎಂ.ಜಿ. ರೋಡ್‌ಗೆ ಬರ್ತೀರಾ?", "Namaskaara anna, MG Road-ge bartheera?", "Hello brother, will you come to MG Road?"),
                DialogueLine("Auto Anna", false, "ಬನ್ನಿ ಸರ್, ಹತ್ತಿರಿ. ಮೀಟರ್ ಮೇಲೆ ಇಪ್ಪತ್ತು ರೂಪಾಯಿ ಕೊಡಿ.", "Banni sir, hattiri. Meter mele ippattu roopaayi kodi.", "Come sir, get in. Give twenty rupees above the meter."),
                DialogueLine("Learner", true, "ಇಲ್ಲ ಅಣ್ಣಾ, ಮೀಟರ್ ಹಾಕಿ. ಸರಿಯಾಗಿ ಕೊಡ್ತೀನಿ.", "Illa anna, meter haaki. Sariyaagi kodthini.", "No brother, please run by meter. I will pay properly."),
                DialogueLine("Auto Anna", false, "ಸರಿ ಬನ್ನಿ, ಕೂತ್ಕೊಳ್ಳಿ. ಟ್ರಾಫಿಕ್ ಜಾಸ್ತಿ ಇದೆ.", "Sari banni, kootkolli. Traffic jaasti ide.", "Alright come, take a seat. Traffic is heavy."),
                DialogueLine("Learner", true, "ಇಲ್ಲಿ ಮುಂದಿನ ಸಿಗ್ನಲ್‌ನಲ್ಲಿ ಬಲಕ್ಕೆ ತಿರುಗಿ.", "Illi mundina signal-nalli balakke thirugi.", "Turn right at the upcoming traffic signal here."),
                DialogueLine("Auto Anna", false, "ಆಯ್ತು ಸರ್, ಮೆಟ್ರೋ ಸ್ಟೇಷನ್ ಹತ್ತಿರನಾ?", "Aaythu sir, Metro station hathirana?", "Done sir, is it near the Metro station?"),
                DialogueLine("Learner", true, "ಹೌದು, ಮೆಟ್ರೋ ಗೇಟ್ ೨ ಹತ್ತಿರ ನಿಲ್ಲಿಸಿ. ಎಷ್ಟು ಆಯ್ತು?", "Haudu, Metro gate 2 hathira nillisi. Eshtu aaythu?", "Yes, please stop near Metro Gate 2. How much is the fare?"),
                DialogueLine("Auto Anna", false, "ಐವತ್ತೈದು ರೂಪಾಯಿ ಸರ್.", "Aivattaidu roopaayi sir.", "Fifty-five rupees sir."),
                DialogueLine("Learner", true, "ತಗೊಳ್ಳಿ ಅರವತ್ತು ರೂಪಾಯಿ, ಧನ್ಯವಾದಗಳು!", "Tagolli aravattu roopaayi, dhanyavaadagalu!", "Here take sixty rupees, thank you very much!")
            )
        ),
        ConversationDialogue(
            id = "dial_darshini",
            titleKannada = "ಬೆಂಗಳೂರು ದರ್ಶಿನಿ ತಿಂಡಿ",
            titleEnglish = "Breakfast at a Bengaluru Darshini",
            locationTag = "CTR Malleshwaram / Taaza Thindi",
            iconEmoji = "☕",
            contextDescription = "Ordering hot crispy benne dosa and authentic South Indian filter coffee.",
            lines = listOf(
                DialogueLine("Learner", true, "ನಮಸ್ಕಾರ, ತಿಂಡಿಗೆ ಬಿಸಿ ಬಿಸಿ ಏನಿದೆ?", "Namaskaara, thindige bisi bisi enide?", "Hello, what hot items are available for breakfast?"),
                DialogueLine("Server", false, "ಮಸಾಲೆ ದೋಸೆ, ಇಡ್ಲಿ ವಡೆ, ಕೇಸರಿ ಬಾತ್ ಮತ್ತು ಖಾರಾ ಬಾತ್ ಇದೆ.", "Masaale dose, idli vade, kesari baath mattu khaara baath ide.", "We have masala dosa, idli vada, kesari bath and khara bath."),
                DialogueLine("Learner", true, "ಒಂದು ಮಸಾಲೆ ದೋಸೆ ಮತ್ತು ಒಂದು ಪ್ಲೇಟ್ ಇಡ್ಲಿ ವಡೆ ಕೊಡಿ.", "Ondu masaale dose mattu ondu plate idli vade kodi.", "Please give one masala dosa and one plate of idli vada."),
                DialogueLine("Server", false, "ದೋಸೆಗೆ ಸ್ವಲ್ಪ ಬೆಣ್ಣೆ ಜಾಸ್ತಿ ಬೇಕಾ?", "Dosege swalpa benne jaasti beka?", "Do you want extra butter on the dosa?"),
                DialogueLine("Learner", true, "ಹೌದು, ಬೆಣ್ಣೆ ಹಾಕಿ! ಜೊತೆಗೆ ಸ್ವಲ್ಪ ಗಟ್ಟಿ ಚಟ್ನಿ ಕೊಡಿ.", "Haudu, benne haaki! Jothege swalpa gatti chutney kodi.", "Yes, add butter! Also give thick coconut chutney with it."),
                DialogueLine("Server", false, "ಖಂಡಿತ ಸರ್. ಕುಡಿಯಲು ಕಾಫಿ ತರಲಾ?", "Khandita sir. Kudiyalu coffee tharala?", "Certainly sir. Shall I bring coffee to drink?"),
                DialogueLine("Learner", true, "ಖಂಡಿತ, ಒಂದು ಸ್ಟ್ರಾಂಗ್ ಫಿಲ್ಟರ್ ಕಾಫಿ ಕೊಡಿ.", "Khandita, ondu strong filter coffee kodi.", "Certainly, give one strong filter coffee."),
                DialogueLine("Server", false, "ಇಲ್ಲಿ ನಿಮ್ಮ ಬಿಸಿ ಬಿಸಿ ದೋಸೆ ಮತ್ತು ಕಾಫಿ ರೆಡಿ!", "Illi nimma bisi bisi dose mattu coffee ready!", "Here is your piping hot dosa and coffee ready!"),
                DialogueLine("Learner", true, "ತುಂಬಾ ರುಚಿಯಾಗಿದೆ, ಧನ್ಯವಾದ!", "Tumba ruchiyaagide, dhanyavaada!", "It is extremely delicious, thank you!")
            )
        ),
        ConversationDialogue(
            id = "dial_market",
            titleKannada = "ತರಕಾರಿ ಮಾರುಕಟ್ಟೆ",
            titleEnglish = "Buying Fresh Veggies at KR Market",
            locationTag = "KR Market / Gandhi Bazaar",
            iconEmoji = "🥦",
            contextDescription = "Asking rates, negotiating prices, and picking fresh vegetables.",
            lines = listOf(
                DialogueLine("Learner", true, "ಅಣ್ಣಾ, ಈ ಆಲೂಗಡ್ಡೆ ಮತ್ತು ಈರುಳ್ಳಿ ಬೆಲೆ ಎಷ್ಟು?", "Anna, ee aaloogadde mattu eerulli bele eshtu?", "Brother, how much are these potatoes and onions?"),
                DialogueLine("Vendor", false, "ಆಲೂಗಡ್ಡೆ ಮೂವತ್ತು ರೂಪಾಯಿ, ಈರುಳ್ಳಿ ನಲವತ್ತು ರೂಪಾಯಿ ಕೆಜಿಗೆ.", "Aaloogadde moovattu roopaayi, eerulli nalavattu roopaayi kg-ge.", "Potatoes thirty rupees, onions forty rupees per kilo."),
                DialogueLine("Learner", true, "ಸ್ವಲ್ಪ ಕಡಿಮೆ ಮಾಡಿ ಅಣ್ಣಾ, ಎರಡು ಕೆಜಿ ತಗೊಳ್ತೀನಿ.", "Swalpa kadime maadi anna, eradu kg tagolthini.", "Please reduce a little brother, I will take two kilos."),
                DialogueLine("Vendor", false, "ಸರಿ ಸರ್, ಎರಡು ಕೆಜಿಗೆ ಎಪ್ಪತ್ತು ರೂಪಾಯಿ ಕೊಡಿ.", "Sari sir, eradu kg-ge eppattu roopaayi kodi.", "Okay sir, give seventy rupees for two kilos."),
                DialogueLine("Learner", true, "ಕೊತ್ತಂಬರಿ ಸೊಪ್ಪು ಮತ್ತು ಹಸಿಮೆಣಸಿನಕಾಯಿ ಸ್ವಲ್ಪ ಹಾಕಿ.", "Kotthambari soppu mattu hasimensinakaayi swalpa haaki.", "Add some fresh coriander leaves and green chilies."),
                DialogueLine("Vendor", false, "ಖಂಡಿತ, ಬ್ಯಾಗ್‌ನಲ್ಲಿ ಹಾಕಿದ್ದೀನಿ ಸರ್.", "Khandita, bag-nalli haakiddini sir.", "Sure, I have placed it in your carry bag sir.")
            )
        ),
        ConversationDialogue(
            id = "dial_direction",
            titleKannada = "ದಾರಿ ವಿಚಾರಣೆ",
            titleEnglish = "Asking for Directions",
            locationTag = "Majestic Bus Station",
            iconEmoji = "🧭",
            contextDescription = "Finding the nearest Metro train entrance and bus stop.",
            lines = listOf(
                DialogueLine("Learner", true, "ಕ್ಷಮಿಸಿ, ನಮ್ಮ ಮೆಟ್ರೋ ನಿಲ್ದಾಣಕ್ಕೆ ಹೇಗೆ ಹೋಗಬೇಕು?", "Kshamisi, Namma Metro nildaana-kke hege hogabeku?", "Excuse me, how should I go to Namma Metro station?"),
                DialogueLine("Localite", false, "ಇಲ್ಲಿಂದ ನೇರವಾಗಿ ಮುಂದೆ ಹೋಗಿ, ಆ ಮೇಲ್ಸೇತುವೆ ಕೆಳಗೆ ಎಡಕ್ಕೆ ತಿರುಗಿ.", "Illinda neravaagi munde hogi, aa melseetuve kelage edakke thirugi.", "Go straight ahead from here, turn left under that flyover."),
                DialogueLine("Learner", true, "ಅಲ್ಲಿಂದ ನಡೆದುಕೊಂಡು ಹೋಗಬಹುದಾ?", "Allinda nadedukondu hogabahuda?", "Can I walk from there?"),
                DialogueLine("Localite", false, "ಹೌದು, ಕೇವಲ ಮೂರು ನಿಮಿಷ ನಡಿಗೆ ಅಷ್ಟೇ.", "Haudu, kevala mooru nimisha nadige ashte.", "Yes, just a three-minute walk."),
                DialogueLine("Learner", true, "ತುಂಬಾ ಉಪಕಾರ ಅಣ್ಣಾ!", "Tumba upakaara anna!", "Thank you very much, brother!")
            )
        )
    )

    val quizzes: List<QuizQuestion> = listOf(
        QuizQuestion(
            id = "q1",
            type = QuizType.IDENTIFY_LETTER,
            prompt = "What is the sound of the Kannada letter shown below?",
            kannadaText = "ಅ",
            transliteration = "A",
            options = listOf("Short 'a' (like in 'cup')", "Long 'aa' (like in 'father')", "Short 'i' (like in 'pin')", "Deep 'u' (like in 'put')"),
            correctIndex = 0,
            explanation = "'ಅ' is the first letter of Kannada Varnamale, pronounced as short 'a' (as in 'Arasa' / King)."
        ),
        QuizQuestion(
            id = "q2",
            type = QuizType.TRANSLATE_PHRASE,
            prompt = "How do you say 'Hello / Greetings' politely in Kannada?",
            kannadaText = "ನಮಸ್ಕಾರ",
            transliteration = "Namaskaara",
            options = listOf("ಬನ್ನಿ (Banni)", "ನಮಸ್ಕಾರ (Namaskaara)", "ಧನ್ಯವಾದ (Dhanyavaada)", "ಹೋಗಿ (Hogi)"),
            correctIndex = 1,
            explanation = "'ನಮಸ್ಕಾರ' (Namaskaara) is the classic respectful greeting across Karnataka."
        ),
        QuizQuestion(
            id = "q3",
            type = QuizType.SITUATIONAL_CHOICE,
            prompt = "What should you tell the auto driver to turn on the fare meter?",
            kannadaText = "ಮೀಟರ್ ಹಾಕಿ",
            transliteration = "Meter haaki",
            options = listOf("ಬೇಗ ಹೋಗಿ (Bega hogi)", "ಇಲ್ಲಿ ನಿಲ್ಲಿಸಿ (Illi nillisi)", "ಮೀಟರ್ ಹಾಕಿ (Meter haaki)", "ಹಣ ಕೊಡಿ (Hana kodi)"),
            correctIndex = 2,
            explanation = "'ಮೀಟರ್ ಹಾಕಿ' (Meter haaki) directly translates to 'Put the meter on'."
        ),
        QuizQuestion(
            id = "q4",
            type = QuizType.IDENTIFY_LETTER,
            prompt = "Which letter represents the elongated sound 'Ee' as in 'Eeju' (Swimming)?",
            kannadaText = "ಈ",
            transliteration = "Ee",
            options = listOf("ಉ", "ಇ", "ಈ", "ಎ"),
            correctIndex = 2,
            explanation = "'ಈ' has the top curved crest denoting the long 'ee' vowel sound."
        ),
        QuizQuestion(
            id = "q5",
            type = QuizType.TRANSLATE_PHRASE,
            prompt = "What is the Kannada word for number 5 (Five)?",
            kannadaText = "ಐದು",
            transliteration = "Aidu",
            options = listOf("ಒಂದು (Ondu)", "ಮೂರು (Mooru)", "ಐದು (Aidu)", "ಹತ್ತು (Hattu)"),
            correctIndex = 2,
            explanation = "1 is Ondu, 2 is Eradu, 3 is Mooru, 4 is Naalku, and 5 is 'ಐದು' (Aidu)."
        ),
        QuizQuestion(
            id = "q6",
            type = QuizType.SITUATIONAL_CHOICE,
            prompt = "In a Darshini cafe, how do you ask 'What is there for breakfast / snacks?'",
            kannadaText = "ತಿಂಡಿಗೆ ಏನಿದೆ?",
            transliteration = "Thindige enide?",
            options = listOf("ತಿಂಡಿಗೆ ಏನಿದೆ? (Thindige enide?)", "ನೀರು ಕೊಡಿ (Neeru kodi)", "ಬಿಲ್ ಎಷ್ಟು? (Bill eshtu?)", "ಊಟ ಆಯ್ತಾ? (Oota aaytha?)"),
            correctIndex = 0,
            explanation = "'Thindige enide?' literally means 'For snacks/breakfast what is there?'"
        ),
        QuizQuestion(
            id = "q7",
            type = QuizType.AUDIO_LISTEN,
            prompt = "Listen to the word and select what it means in English:",
            kannadaText = "ಧನ್ಯವಾದಗಳು",
            transliteration = "Dhanyavaadagalu",
            options = listOf("Please", "Thank you very much", "Good morning", "Excuse me"),
            correctIndex = 1,
            explanation = "'ಧನ್ಯವಾದಗಳು' (Dhanyavaadagalu) means 'Thank you very much'."
        ),
        QuizQuestion(
            id = "q8",
            type = QuizType.TRANSLATE_PHRASE,
            prompt = "How do you say 'How much is it?' to a shopkeeper?",
            kannadaText = "ಎಷ್ಟು ಆಯ್ತು?",
            transliteration = "Eshtu aaythu?",
            options = listOf("ಎಲ್ಲಿ ಇದೆ? (Elli ide?)", "ಎಷ್ಟು ಆಯ್ತು? (Eshtu aaythu?)", "ಯಾರು ನೀವು? (Yaaru neevu?)", "ಯಾವಾಗ ಬರ್ತೀರಾ? (Yaavaaga bartheera?)"),
            correctIndex = 1,
            explanation = "'Eshtu' means How much / How many. 'Eshtu aaythu?' means 'How much did it come to?'"
        )
    )

    val dictionary: List<DictionaryPhrase> = listOf(
        // Greetings
        DictionaryPhrase("d1", "Greetings", "ನಮಸ್ಕಾರ", "Namaskaara", "Hello / Greetings", true),
        DictionaryPhrase("d2", "Greetings", "ಶುಭೋದಯ", "Shubhdaya", "Good morning", false),
        DictionaryPhrase("d3", "Greetings", "ಶುಭ ಸಂಜೆ", "Shubha sanje", "Good evening", false),
        DictionaryPhrase("d4", "Greetings", "ಶುಭ ರಾತ್ರಿ", "Shubha raathri", "Good night", false),
        DictionaryPhrase("d5", "Greetings", "ಹೇಗಿದ್ದೀರಾ?", "Hegiddeera?", "How are you? (Respectful)", true),
        DictionaryPhrase("d6", "Greetings", "ನಾನು ಚೆನ್ನಾಗಿದ್ದೇನೆ", "Naanu chennaagiddene", "I am fine / doing well", true),
        DictionaryPhrase("d7", "Greetings", "ಧನ್ಯವಾದ", "Dhanyavaada", "Thank you", true),
        DictionaryPhrase("d8", "Greetings", "ದಯವಿಟ್ಟು", "Dayavittu", "Please", true),
        DictionaryPhrase("d9", "Greetings", "ಕ್ಷಮಿಸಿ", "Kshamisi", "Excuse me / Sorry", true),
        DictionaryPhrase("d10", "Greetings", "ಮತ್ತೆ ಸಿಗೋಣ", "Matte sigona", "See you again", false),
        DictionaryPhrase("d11", "Greetings", "ನಿಮ್ಮ ಹೆಸರು ಏನು?", "Nimma hesaru enu?", "What is your name?", true),
        DictionaryPhrase("d12", "Greetings", "ನನ್ನ ಹೆಸರು...", "Nanna hesaru...", "My name is...", true),

        // Travel & Auto
        DictionaryPhrase("d13", "Travel & Auto", "ಆಟೋ ಬರ್ತೀರಾ?", "Auto bartheera?", "Will you come, auto?", true),
        DictionaryPhrase("d14", "Travel & Auto", "ಮೀಟರ್ ಹಾಕಿ", "Meter haaki", "Turn on the meter", true),
        DictionaryPhrase("d15", "Travel & Auto", "ಇಲ್ಲಿ ನಿಲ್ಲಿಸಿ", "Illi nillisi", "Stop here", true),
        DictionaryPhrase("d16", "Travel & Auto", "ಸ್ವಲ್ಪ ಮುಂದೆ ಹೋಗಿ", "Swalpa munde hogi", "Go a little further ahead", true),
        DictionaryPhrase("d17", "Travel & Auto", "ಬಲಕ್ಕೆ ತಿರುಗಿ", "Balakke thirugi", "Turn right", true),
        DictionaryPhrase("d18", "Travel & Auto", "ಎಡಕ್ಕೆ ತಿರುಗಿ", "Edakke thirugi", "Turn left", true),
        DictionaryPhrase("d19", "Travel & Auto", "ನೇರವಾಗಿ ಹೋಗಿ", "Neravaagi hogi", "Go straight", true),
        DictionaryPhrase("d20", "Travel & Auto", "ಎಷ್ಟು ಆಯ್ತು?", "Eshtu aaythu?", "How much is the fare?", true),
        DictionaryPhrase("d21", "Travel & Auto", "ಮೆಟ್ರೋ ನಿಲ್ದಾಣ ಎಲ್ಲಿದೆ?", "Metro nildaana ellide?", "Where is the metro station?", true),
        DictionaryPhrase("d22", "Travel & Auto", "ಬಸ್ ಸ್ಟಾಪ್ ಎಲ್ಲಿದೆ?", "Bus stop ellide?", "Where is the bus stop?", false),
        DictionaryPhrase("d23", "Travel & Auto", "ಬೇಗ ಹೋಗಿ", "Bega hogi", "Go fast", false),
        DictionaryPhrase("d24", "Travel & Auto", "ನಿಧಾನವಾಗಿ ಹೋಗಿ", "Nidhaanavaagi hogi", "Go slowly / carefully", true),

        // Food & Cafe
        DictionaryPhrase("d25", "Food & Cafe", "ತಿಂಡಿಗೆ ಏನಿದೆ?", "Thindige enide?", "What is for breakfast/snacks?", true),
        DictionaryPhrase("d26", "Food & Cafe", "ಒಂದು ಕಾಫಿ ಕೊಡಿ", "Ondu coffee kodi", "Give one coffee", true),
        DictionaryPhrase("d27", "Food & Cafe", "ಕುಡಿಯುವ ನೀರು ಕೊಡಿ", "Kudiyuva neeru kodi", "Give drinking water", true),
        DictionaryPhrase("d28", "Food & Cafe", "ಸಕ್ಕರೆ ಕಡಿಮೆ", "Sakkare kadime", "Less sugar", true),
        DictionaryPhrase("d29", "Food & Cafe", "ಖಾರ ಕಡಿಮೆ ಮಾಡಿ", "Khaara kadime maadi", "Make it less spicy", true),
        DictionaryPhrase("d30", "Food & Cafe", "ಊಟ ಆಯ್ತಾ?", "Oota aaytha?", "Did you have food?", true),
        DictionaryPhrase("d31", "Food & Cafe", "ಬಿಸಿ ಬಿಸಿ ದೋಸೆ", "Bisi bisi dose", "Hot dosa", false),
        DictionaryPhrase("d32", "Food & Cafe", "ತುಂಬಾ ರುಚಿಯಾಗಿದೆ", "Tumba ruchiyaagide", "It is very delicious", true),
        DictionaryPhrase("d33", "Food & Cafe", "ಬಿಲ್ ಕೊಡಿ", "Bill kodi", "Please bring the bill", true),
        DictionaryPhrase("d34", "Food & Cafe", "ಚಟ್ನಿ ಸ್ವಲ್ಪ ಹಾಕಿ", "Chutney swalpa haaki", "Put a little more chutney", false),

        // Shopping & Numbers
        DictionaryPhrase("d35", "Shopping & Numbers", "ಬೆಲೆ ಎಷ್ಟು?", "Bele eshtu?", "How much is the price?", true),
        DictionaryPhrase("d36", "Shopping & Numbers", "ಸ್ವಲ್ಪ ಕಡಿಮೆ ಮಾಡಿ", "Swalpa kadime maadi", "Reduce the price a bit", true),
        DictionaryPhrase("d37", "Shopping & Numbers", "ಚಿಲ್ಲರೆ ಇದೆಯಾ?", "Chillare ideya?", "Do you have change?", true),
        DictionaryPhrase("d38", "Shopping & Numbers", "ಗೂಗಲ್ ಪೇ ಮಾಡ್ತೀನಿ", "Google Pay maadtini", "I will Google Pay", true),
        DictionaryPhrase("d39", "Shopping & Numbers", "ಒಂದು ಕೆಜಿ ಕೊಡಿ", "Ondu kg kodi", "Give one kilogram", true),
        DictionaryPhrase("d40", "Shopping & Numbers", "ಇದು ಬೇಡ", "Idu beda", "I don't want this", false),
        DictionaryPhrase("d41", "Shopping & Numbers", "ಇದು ಬೇಕು", "Idu beku", "I want this", true),
        DictionaryPhrase("d42", "Shopping & Numbers", "ಹತ್ತು ರೂಪಾಯಿ", "Hattu roopaayi", "Ten rupees", false),
        DictionaryPhrase("d43", "Shopping & Numbers", "ನೂರು ರೂಪಾಯಿ", "Nooru roopaayi", "One hundred rupees", false),

        // Emergency & Common
        DictionaryPhrase("d44", "Common & Help", "ಸಹಾಯ ಮಾಡಿ", "Sahaaya maadi", "Please help", true),
        DictionaryPhrase("d45", "Common & Help", "ನನಗೆ ಕನ್ನಡ ಸ್ವಲ್ಪ ಗೊತ್ತು", "Nanage Kannada swalpa gottu", "I know a little Kannada", true),
        DictionaryPhrase("d46", "Common & Help", "ಇನ್ನೊಮ್ಮೆ ಹೇಳಿ", "Innomme heli", "Please say it once again", true),
        DictionaryPhrase("d47", "Common & Help", "ನನಗೆ ಅರ್ಥವಾಗಲಿಲ್ಲ", "Nanage arthavaagalilla", "I did not understand", true),
        DictionaryPhrase("d48", "Common & Help", "ಡಾಕ್ಟರ್ ಎಲ್ಲಿದ್ದಾರೆ?", "Doctor elliddaare?", "Where is the doctor?", true),
        DictionaryPhrase("d49", "Common & Help", "ಆಸ್ಪತ್ರೆ ಎಲ್ಲಿದೆ?", "Aaspatre ellide?", "Where is the hospital?", true),
        DictionaryPhrase("d50", "Common & Help", "ಆಯ್ತು", "Aaythu", "Okay / Done / Understood", true)
    )

    val flashcards: List<com.example.model.VocabularyFlashcard> = listOf(
        com.example.model.VocabularyFlashcard(
            id = "fc1",
            kannadaWord = "ಊಟ",
            transliteration = "Oota",
            englishMeaning = "Meal / Food",
            category = "Food & Dining",
            phoneticBreakdown = "Oo-ta (elongated 'oo' as in 'moon')",
            exampleSentenceKn = "ನಿಮ್ಮ ಊಟ ಆಯ್ತಾ?",
            exampleSentenceTranslit = "Nimma oota aaytha?",
            exampleSentenceEn = "Did you have your meal / food?",
            culturalNote = "In Karnataka, asking 'ಊಟ ಆಯ್ತಾ?' (Did you eat?) is the warmest everyday greeting, expressing genuine care."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc2",
            kannadaWord = "ನಮಸ್ಕಾರ",
            transliteration = "Namaskaara",
            englishMeaning = "Hello / Greetings",
            category = "Essentials",
            phoneticBreakdown = "Na-mas-kaa-ra",
            exampleSentenceKn = "ಎಲ್ಲರಿಗೂ ನಮಸ್ಕಾರ, ಹೇಗಿದ್ದೀರಾ?",
            exampleSentenceTranslit = "Ellarigoo namaskaara, hegiddeera?",
            exampleSentenceEn = "Hello everyone, how are you all?",
            culturalNote = "Universal polite greeting used with joined hands or a warm smile throughout Karnataka."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc3",
            kannadaWord = "ಧನ್ಯವಾದ",
            transliteration = "Dhanyavaada",
            englishMeaning = "Thank you",
            category = "Essentials",
            phoneticBreakdown = "Dhan-ya-vaa-da",
            exampleSentenceKn = "ನಿಮ್ಮ ಸಹಾಯಕ್ಕೆ ತುಂಬಾ ಧನ್ಯವಾದಗಳು.",
            exampleSentenceTranslit = "Nimma sahaayakke tumba dhanyavaadagalu.",
            exampleSentenceEn = "Thank you very much for your kind help.",
            culturalNote = "'ಧನ್ಯವಾದಗಳು' adds the plural suffix 'ಗಳು', making it even more courteous and heartfelt."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc4",
            kannadaWord = "ತಿಂಡಿ",
            transliteration = "Thindi",
            englishMeaning = "Breakfast / Snacks",
            category = "Food & Dining",
            phoneticBreakdown = "Thin-di (soft dental 'th')",
            exampleSentenceKn = "ಬೆಳಗಿನ ತಿಂಡಿಗೆ ಬಿಸಿ ಮಸಾಲೆ ದೋಸೆ ರೆಡಿ.",
            exampleSentenceTranslit = "Belagina thindige bisi masaale dose ready.",
            exampleSentenceEn = "Hot masala dosa is ready for morning breakfast.",
            culturalNote = "Bengaluru Darshinis are world-famous for rapid self-service hot morning 'Thindi'."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc5",
            kannadaWord = "ಮೀಟರ್ ಹಾಕಿ",
            transliteration = "Meter Haaki",
            englishMeaning = "Turn on the meter",
            category = "Travel & Auto",
            phoneticBreakdown = "Mee-ter Haa-ki",
            exampleSentenceKn = "ದಯವಿಟ್ಟು ಮೀಟರ್ ಹಾಕಿ ಅಣ್ಣಾ.",
            exampleSentenceTranslit = "Dayavittu meter haaki anna.",
            exampleSentenceEn = "Please turn on the fare meter, brother.",
            culturalNote = "Essential phrase when hiring an auto rickshaw in Bengaluru and Mysuru."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc6",
            kannadaWord = "ನೀರು",
            transliteration = "Neeru",
            englishMeaning = "Water",
            category = "Food & Dining",
            phoneticBreakdown = "Nee-ru",
            exampleSentenceKn = "ದಯವಿಟ್ಟು ಸ್ವಲ್ಪ ಕುಡಿಯುವ ನೀರು ಕೊಡಿ.",
            exampleSentenceTranslit = "Dayavittu swalpa kudiyuva neeru kodi.",
            exampleSentenceEn = "Please give a little drinking water.",
            culturalNote = "'ಕುಡಿಯುವ ನೀರು' means potable drinking water. 'ಬಿಸಿ ನೀರು' is warm/hot water."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc7",
            kannadaWord = "ಕಾಫಿ",
            transliteration = "Coffee",
            englishMeaning = "Filter Coffee",
            category = "Food & Dining",
            phoneticBreakdown = "Kaa-fi",
            exampleSentenceKn = "ಒಂದು ಸ್ಟ್ರಾಂಗ್ ಫಿಲ್ಟರ್ ಕಾಫಿ ಕೊಡಿ, ಸಕ್ಕರೆ ಕಡಿಮೆ.",
            exampleSentenceTranslit = "Ondu strong filter coffee kodi, sakkare kadime.",
            exampleSentenceEn = "Give one strong filter coffee with less sugar.",
            culturalNote = "South Indian filter coffee served in stainless steel dabara-tumbler sets is a cultural staple."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc8",
            kannadaWord = "ಬೆಲೆ ಎಷ್ಟು?",
            transliteration = "Bele Eshtu?",
            englishMeaning = "How much is the price?",
            category = "Commerce & Market",
            phoneticBreakdown = "Be-le Esh-tu?",
            exampleSentenceKn = "ಈ ಪುಸ್ತಕದ ಬೆಲೆ ಎಷ್ಟು ರೂಪಾಯಿ?",
            exampleSentenceTranslit = "Ee pustakada bele eshtu roopaayi?",
            exampleSentenceEn = "How many rupees is the price of this book?",
            culturalNote = "'Bele' means price, 'Eshtu' means how much. You can also simply ask 'Eshtu aaythu?'"
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc9",
            kannadaWord = "ಕಡಿಮೆ ಮಾಡಿ",
            transliteration = "Kadime Maadi",
            englishMeaning = "Reduce the price",
            category = "Commerce & Market",
            phoneticBreakdown = "Ka-di-me Maa-di",
            exampleSentenceKn = "ಸ್ವಲ್ಪ ಜಾಸ್ತಿ ಆಯ್ತು, ಸ್ವಲ್ಪ ಕಡಿಮೆ ಮಾಡಿ ಅಣ್ಣಾ.",
            exampleSentenceTranslit = "Swalpa jaasti aaythu, swalpa kadime maadi anna.",
            exampleSentenceEn = "It's a bit high, please reduce it a little brother.",
            culturalNote = "'Kadime' means less, 'Jaasti' means more. 'Maadi' is the respectful imperative for 'do / make'."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc10",
            kannadaWord = "ಇಲ್ಲಿ ನಿಲ್ಲಿಸಿ",
            transliteration = "Illi Nillisi",
            englishMeaning = "Stop here",
            category = "Travel & Auto",
            phoneticBreakdown = "Il-li Nil-li-si",
            exampleSentenceKn = "ಆ ಮೆಟ್ರೋ ಗೇಟ್ ಹತ್ತಿರ ಇಲ್ಲಿ ನಿಲ್ಲಿಸಿ.",
            exampleSentenceTranslit = "Aa Metro gate hathira illi nillisi.",
            exampleSentenceEn = "Stop here near that Metro gate.",
            culturalNote = "Use 'Illi nillisi' (Stop here) or 'Munde nillisi' (Stop a bit ahead)."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc11",
            kannadaWord = "ಸ್ನೇಹಿತ",
            transliteration = "Snehitha",
            englishMeaning = "Friend",
            category = "People & Life",
            phoneticBreakdown = "Sne-hi-tha",
            exampleSentenceKn = "ಅವರು ನನ್ನ ಕಾಲೇಜು ಸ್ನೇಹಿತ.",
            exampleSentenceTranslit = "Avaru nanna college snehitha.",
            exampleSentenceEn = "He is my college friend.",
            culturalNote = "'ಸ್ನೇಹಿತ' (male friend) and 'ಸ್ನೇಹಿತೆ' (female friend) or colloquial 'ಗೆಳೆಯ / ಗೆಳತಿ'."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc12",
            kannadaWord = "ಮನೆ",
            transliteration = "Mane",
            englishMeaning = "Home / House",
            category = "People & Life",
            phoneticBreakdown = "Ma-ne",
            exampleSentenceKn = "ನನ್ನ ಮನೆ ಜಯನಗರದಲ್ಲಿದೆ.",
            exampleSentenceTranslit = "Nanna mane Jayanagar-dallide.",
            exampleSentenceEn = "My home is in Jayanagar.",
            culturalNote = "'Mane' also carries the warm connotation of household and family."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc13",
            kannadaWord = "ಆಯ್ತು",
            transliteration = "Aaythu",
            englishMeaning = "Okay / Done / Understood",
            category = "Essentials",
            phoneticBreakdown = "Aay-thu",
            exampleSentenceKn = "ಸರಿ ಅಣ್ಣಾ, ನಿಮ್ಮ ಕೆಲಸ ಆಯ್ತು.",
            exampleSentenceTranslit = "Sari anna, nimma kelasa aaythu.",
            exampleSentenceEn = "Alright brother, your work is done.",
            culturalNote = "The most versatile word in Kannada! Means 'OK', 'It happened', 'Agreed', and 'Completed'."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc14",
            kannadaWord = "ದಯವಿಟ್ಟು",
            transliteration = "Dayavittu",
            englishMeaning = "Please",
            category = "Essentials",
            phoneticBreakdown = "Da-ya-vit-tu",
            exampleSentenceKn = "ದಯವಿಟ್ಟು ಒಳಗೆ ಬನ್ನಿ.",
            exampleSentenceTranslit = "Dayavittu olage banni.",
            exampleSentenceEn = "Please come inside.",
            culturalNote = "Derived from 'Daya' (kindness/grace) + 'Ittu' (having placed) = 'Please with kindness'."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc15",
            kannadaWord = "ಚಿಲ್ಲರೆ",
            transliteration = "Chillare",
            englishMeaning = "Coins / Loose Change",
            category = "Commerce & Market",
            phoneticBreakdown = "Chil-la-re",
            exampleSentenceKn = "ಐನೂರು ರೂಪಾಯಿಗೆ ಚಿಲ್ಲರೆ ಇದೆಯಾ?",
            exampleSentenceTranslit = "Ainooru roopaayige chillare ideya?",
            exampleSentenceEn = "Do you have loose change for five hundred rupees?",
            culturalNote = "Frequently asked in BMTC buses, auto rickshaws, and corner tea stalls."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc16",
            kannadaWord = "ನೇರವಾಗಿ",
            transliteration = "Neravaagi",
            englishMeaning = "Straight ahead",
            category = "Travel & Auto",
            phoneticBreakdown = "Ne-ra-vaa-gi",
            exampleSentenceKn = "ಇಲ್ಲಿಂದ ನೇರವಾಗಿ ಮುಂದೆ ಹೋಗಿ.",
            exampleSentenceTranslit = "Illinda neravaagi munde hogi.",
            exampleSentenceEn = "Go straight ahead from here.",
            culturalNote = "Direction triad: 'Neravaagi' (straight), 'Balakke' (right), 'Edakke' (left)."
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc17",
            kannadaWord = "ಸಮಯ",
            transliteration = "Samaya",
            englishMeaning = "Time",
            category = "People & Life",
            phoneticBreakdown = "Sa-ma-ya",
            exampleSentenceKn = "ಈಗ ಸಮಯ ಎಷ್ಟು ಗಂಟೆ?",
            exampleSentenceTranslit = "Eega samaya eshtu gante?",
            exampleSentenceEn = "What time is it right now?",
            culturalNote = "'Gante' means hour/bell/clock. 'Eshtu gante?' literally means 'How many chimes?'"
        ),
        com.example.model.VocabularyFlashcard(
            id = "fc18",
            kannadaWord = "ರುಚಿ",
            transliteration = "Ruchi",
            englishMeaning = "Taste / Delicious",
            category = "Food & Dining",
            phoneticBreakdown = "Ru-chi",
            exampleSentenceKn = "ಈ ಸಾಂಬಾರ್ ತುಂಬಾ ರುಚಿಯಾಗಿದೆ!",
            exampleSentenceTranslit = "Ee saambaar tumba ruchiyaagide!",
            exampleSentenceEn = "This sambar is very delicious!",
            culturalNote = "'Tumba ruchi' (Very tasty) is the highest compliment you can give to a South Indian cook."
        )
    )
}

