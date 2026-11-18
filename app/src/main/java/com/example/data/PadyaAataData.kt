package com.example.data

import com.example.R
import com.example.model.KarnatakaSticker
import com.example.model.MissingWordChallenge
import com.example.model.PadyaGameType
import com.example.model.PadyaPoem
import com.example.model.PictureToPoemChallenge
import com.example.model.PoemBeat
import com.example.model.PoemLine
import com.example.model.PoemOption
import com.example.model.PoemWordCard
import com.example.model.PoetryRemixSlot
import com.example.model.RhymeMatchChallenge
import com.example.model.WordHuntTarget

object PadyaAataData {

    val poems: List<PadyaPoem> = listOf(
        // 1. Male Bantu (Rain Came)
        PadyaPoem(
            id = "poem_male_bantu",
            levelNumber = 1,
            titleKannada = "ಮಳೆ ಬಂತು",
            titleTranslit = "Male Bantu",
            titleEnglish = "Rain Came",
            themeTag = "RAIN & NATURE",
            illustrationRes = R.drawable.img_poem_rain,
            illustrationCaption = "Monsoon drops, green leaves, singing bird & dancing child",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ಮಳೆ ಬಂತು, ಮಳೆ ಬಂತು",
                    transliteration = "Male bantu, male bantu",
                    english = "Rain came, rain came",
                    beats = listOf(
                        PoemBeat("ಮಳೆ", "ma-le"),
                        PoemBeat("ಬಂತು", "ban-tu"),
                        PoemBeat("ಮಳೆ", "ma-le"),
                        PoemBeat("ಬಂತು", "ban-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ಹಸಿರು ಎಲೆ ನಗುತಿತ್ತು",
                    transliteration = "Hasiru ele nagutittu",
                    english = "The green leaf was smiling",
                    beats = listOf(
                        PoemBeat("ಹಸಿರು", "ha-si-ru"),
                        PoemBeat("ಎಲೆ", "e-le"),
                        PoemBeat("ನಗುತಿತ್ತು", "na-gu-tit-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ಹಕ್ಕಿ ಹಾಡು ಹಾಡಿತು",
                    transliteration = "Hakki haadu haaditu",
                    english = "The bird sang a song",
                    beats = listOf(
                        PoemBeat("ಹಕ್ಕಿ", "hak-ki"),
                        PoemBeat("ಹಾಡು", "haa-du"),
                        PoemBeat("ಹಾಡಿತು", "haa-di-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ಪುಟ್ಟ ಮಗು ಕುಣಿಯಿತು",
                    transliteration = "Putta magu kuniyitu",
                    english = "The little child danced",
                    beats = listOf(
                        PoemBeat("ಪುಟ್ಟ", "put-ta"),
                        PoemBeat("ಮಗು", "ma-gu"),
                        PoemBeat("ಕುಣಿಯಿತು", "ku-ni-yi-tu")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ಮಳೆ", "male", "Rain", "Retroflex 'le' — curl tongue slightly back"),
                PoemWordCard("ಹಸಿರು", "hasiru", "Green", "Three crisp short vowels: ha-si-ru"),
                PoemWordCard("ಎಲೆ", "ele", "Leaf", "Starts with short 'e', rhymes with male"),
                PoemWordCard("ಹಕ್ಕಿ", "hakki", "Bird", "Double 'kk' — pause lightly before 'ki'"),
                PoemWordCard("ಮಗು", "magu", "Child", "Warm everyday word for little child")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "ಮಳೆ ಬಂತು, ____ ಬಂತು",
                promptTranslitMasked = "Male bantu, ____ bantu",
                promptEnglishMasked = "Rain came, ____ came",
                fullLineKannada = "ಮಳೆ ಬಂತು, ಮಳೆ ಬಂತು",
                options = listOf(
                    PoemOption("ಮಳೆ", "male", "Rain"),
                    PoemOption("ಮನೆ", "mane", "House"),
                    PoemOption("ಹಕ್ಕಿ", "hakki", "Bird"),
                    PoemOption("ಹೂವು", "hoovu", "Flower")
                ),
                correctIndex = 0,
                explanation = "Repetition creates the rainbeat! 'ಮಳೆ ಬಂತು, ಮಳೆ ಬಂತು' means 'Rain came, rain came'."
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ಹಾಡಿತು", "haaditu", "sang"),
                options = listOf(
                    PoemOption("ಮಳೆ", "male", "rain"),
                    PoemOption("ಕುಣಿಯಿತು", "kuniyitu", "danced"),
                    PoemOption("ಹಸಿರು", "hasiru", "green")
                ),
                correctIndex = 1,
                rhymeExplanation = "ಹಾಡಿತು (haaditu) and ಕುಣಿಯಿತು (kuniyitu) share the musical past-tense ending '-itu' (-ಇತು)."
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ಹಕ್ಕಿ", "hakki", "Bird", lineIndex = 2),
                WordHuntTarget("ಎಲೆ", "ele", "Leaf", lineIndex = 1)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Which Kannada line describes what is shown in the rain illustration?",
                options = listOf(
                    PoemLine(3, "ಹಕ್ಕಿ ಹಾಡು ಹಾಡಿತು", "Hakki haadu haaditu", "The bird sang a song", emptyList()),
                    PoemLine(1, "ಬಿಸಿ ಬಿಸಿ ದೋಸೆ ಬಂತು", "Bisi bisi dose bantu", "Hot hot dosa arrived", emptyList()),
                    PoemLine(3, "ಪುಟ್ಟ ತಾರೆ ಮಿನುಗಿತು", "Putta taare minugitu", "The little star twinkled", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Who / What",
                    choices = listOf(
                        PoemOption("ಪುಟ್ಟ ಮಗು", "putta magu", "Little child"),
                        PoemOption("ಹಸಿರು ಹಕ್ಕಿ", "hasiru hakki", "Green bird"),
                        PoemOption("ತಂಪು ಮಳೆ", "tampu male", "Cool rain")
                    )
                ),
                PoetryRemixSlot(
                    label = "Where",
                    choices = listOf(
                        PoemOption("ಮಳೆಯಲ್ಲಿ", "maleyalli", "in the rain"),
                        PoemOption("ತೋಟದಲ್ಲಿ", "totadalli", "in the garden"),
                        PoemOption("ಮರದ ಕೆಳಗೆ", "marada kelage", "under the tree")
                    )
                ),
                PoetryRemixSlot(
                    label = "Action",
                    choices = listOf(
                        PoemOption("ಕುಣಿಯಿತು!", "kuniyitu!", "danced!"),
                        PoemOption("ನಕ್ಕಿತು!", "nakkitu!", "smiled!"),
                        PoemOption("ಹಾಡಿತು!", "haaditu!", "sang!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_jog_falls",
                title = "Jog Falls Raindrop",
                kannadaTitle = "ಜೋಗ ಜಲಪಾತ",
                regionTag = "Shivamogga",
                drawableRes = R.drawable.pop_landmark_jog_falls
            )
        ),

        // 2. Bisi Bisi Dose (Hot Hot Dosa)
        PadyaPoem(
            id = "poem_bisi_dose",
            levelNumber = 2,
            titleKannada = "ಬಿಸಿ ಬಿಸಿ ದೋಸೆ",
            titleTranslit = "Bisi Bisi Dose",
            titleEnglish = "Hot Hot Dosa",
            themeTag = "BENGALURU BREAKFAST",
            illustrationRes = R.drawable.img_poem_dosa,
            illustrationCaption = "Crispy golden butter dosa on a banana leaf with filter coffee",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ಬಿಸಿ ಬಿಸಿ ದೋಸೆ ಬಂತು",
                    transliteration = "Bisi bisi dose bantu",
                    english = "Hot hot dosa arrived",
                    beats = listOf(
                        PoemBeat("ಬಿಸಿ", "bi-si"),
                        PoemBeat("ಬಿಸಿ", "bi-si"),
                        PoemBeat("ದೋಸೆ", "do-se"),
                        PoemBeat("ಬಂತು", "ban-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ಬೆಣ್ಣೆ ಸವರಿ ರುಚಿ ತಂತು",
                    transliteration = "Benne savari ruchi tantu",
                    english = "Spreading butter brought flavor",
                    beats = listOf(
                        PoemBeat("ಬೆಣ್ಣೆ", "ben-ne"),
                        PoemBeat("ಸವರಿ", "sa-va-ri"),
                        PoemBeat("ರುಚಿ", "ru-chi"),
                        PoemBeat("ತಂತು", "tan-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ಪುಟ್ಟ ಕಪ್ಪು ಕಾಫಿ ಇತ್ತು",
                    transliteration = "Putta kappu kaafi ittu",
                    english = "There was a little black coffee",
                    beats = listOf(
                        PoemBeat("ಪುಟ್ಟ", "put-ta"),
                        PoemBeat("ಕಪ್ಪು", "kap-pu"),
                        PoemBeat("ಕಾಫಿ", "kaa-fi"),
                        PoemBeat("ಇತ್ತು", "it-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ತಿಂಡಿ ತಿಂದು ಮನಸು ನಕ್ಕಿತು",
                    transliteration = "Tindi tindu manasu nakkitu",
                    english = "Eating tiffin, the heart smiled",
                    beats = listOf(
                        PoemBeat("ತಿಂಡಿ", "tin-di"),
                        PoemBeat("ತಿಂದು", "tin-du"),
                        PoemBeat("ಮನಸು", "ma-na-su"),
                        PoemBeat("ನಕ್ಕಿತು", "nak-ki-tu")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ಬಿಸಿ", "bisi", "Hot", "Repeated 'bisi bisi' means piping hot!"),
                PoemWordCard("ದೋಸೆ", "dose", "Dosa", "Stretch the long 'o' in do-se"),
                PoemWordCard("ಬೆಣ್ಣೆ", "benne", "Butter", "Double 'nn' — crisp and creamy"),
                PoemWordCard("ರುಚಿ", "ruchi", "Flavor / Tasty", "Use 'ruchi ide' when food is delicious"),
                PoemWordCard("ಕಾಫಿ", "kaafi", "Coffee", "Long 'aa' vowel at the start")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "ಬಿಸಿ ಬಿಸಿ ____ ಬಂತು",
                promptTranslitMasked = "Bisi bisi ____ bantu",
                promptEnglishMasked = "Hot hot ____ arrived",
                fullLineKannada = "ಬಿಸಿ ಬಿಸಿ ದೋಸೆ ಬಂತು",
                options = listOf(
                    PoemOption("ಮರ", "mara", "Tree"),
                    PoemOption("ದೋಸೆ", "dose", "Dosa"),
                    PoemOption("ಚಂದಿರ", "chandira", "Moon"),
                    PoemOption("ಕಡಲು", "kadalu", "Sea")
                ),
                correctIndex = 1,
                explanation = "'ಬಿಸಿ ಬಿಸಿ ದೋಸೆ ಬಂತು' celebrates piping hot Bengaluru benne dosa!"
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ಬಂತು", "bantu", "arrived"),
                options = listOf(
                    PoemOption("ತಂತು", "tantu", "brought"),
                    PoemOption("ದೋಸೆ", "dose", "dosa"),
                    PoemOption("ಬೆಣ್ಣೆ", "benne", "butter")
                ),
                correctIndex = 0,
                rhymeExplanation = "ಬಂತು (bantu) and ತಂತು (tantu) end with the identical '-antu' (-ಅಂತು) chime!"
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ಬೆಣ್ಣೆ", "benne", "Butter", lineIndex = 1),
                WordHuntTarget("ಕಾಫಿ", "kaafi", "Coffee", lineIndex = 2)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Pick the Kannada line that matches this breakfast plate:",
                options = listOf(
                    PoemLine(2, "ಬೆಣ್ಣೆ ಸವರಿ ರುಚಿ ತಂತು", "Benne savari ruchi tantu", "Spreading butter brought flavor", emptyList()),
                    PoemLine(1, "ಹಳದಿ ಆಟೋ ದಾರಿಯಲ್ಲಿ", "Haladi auto daariyalli", "A yellow auto is on the road", emptyList()),
                    PoemLine(4, "ಪುಟ್ಟ ಮೀನು ಹಾರಿತು", "Putta meenu haaritu", "The little fish leaped", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Flavor",
                    choices = listOf(
                        PoemOption("ಬಿಸಿ ಬಿಸಿ", "bisi bisi", "Piping hot"),
                        PoemOption("ಸಿಹಿ ಸಿಹಿ", "sihi sihi", "Extra sweet"),
                        PoemOption("ತಂಪು ತಂಪು", "tampu tampu", "Ice cool")
                    )
                ),
                PoetryRemixSlot(
                    label = "Treat",
                    choices = listOf(
                        PoemOption("ಬೆಣ್ಣೆ ದೋಸೆ", "benne dose", "butter dosa"),
                        PoemOption("ಫಿಲ್ಟರ್ ಕಾಫಿ", "filter kaafi", "filter coffee"),
                        PoemOption("ಮಾವಿನ ಹಣ್ಣು", "maavina hannu", "mango fruit")
                    )
                ),
                PoetryRemixSlot(
                    label = "Result",
                    choices = listOf(
                        PoemOption("ರುಚಿ ತಂತು!", "ruchi tantu!", "brought flavor!"),
                        PoemOption("ನಗು ತಂತು!", "nagu tantu!", "brought smiles!"),
                        PoemOption("ಕುಣಿಯಿತು!", "kuniyitu!", "danced!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_benne_chef",
                title = "Benne Butter Emblem",
                kannadaTitle = "ಬೆಣ್ಣೆ ದೋಸೆ",
                regionTag = "Davanagere",
                drawableRes = R.drawable.benne_pose_reward
            )
        ),

        // 3. Putta Bekku (Little Cat)
        PadyaPoem(
            id = "poem_putta_bekku",
            levelNumber = 3,
            titleKannada = "ಪುಟ್ಟ ಬೆಕ್ಕು",
            titleTranslit = "Putta Bekku",
            titleEnglish = "Little Cat",
            themeTag = "HOME & PETS",
            illustrationRes = R.drawable.img_poem_moon_cat,
            illustrationCaption = "Cozy little cat beside a bowl of milk under a moonlit window",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ಪುಟ್ಟ ಬಿಳಿ ಬೆಕ್ಕು ಬಂತು",
                    transliteration = "Putta bili bekku bantu",
                    english = "The little white cat came",
                    beats = listOf(
                        PoemBeat("ಪುಟ್ಟ", "put-ta"),
                        PoemBeat("ಬಿಳಿ", "bi-li"),
                        PoemBeat("ಬೆಕ್ಕು", "bek-ku"),
                        PoemBeat("ಬಂತು", "ban-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ಬಟ್ಟಲ ಹಾಲು ಕುಡಿಯಿತು",
                    transliteration = "Battala haalu kudiyitu",
                    english = "It drank a bowl of milk",
                    beats = listOf(
                        PoemBeat("ಬಟ್ಟಲ", "bat-ta-la"),
                        PoemBeat("ಹಾಲು", "haa-lu"),
                        PoemBeat("ಕುಡಿಯಿತು", "ku-di-yi-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ಮನೆಯ ಒಳಗೆ ನಡೆಯಿತು",
                    transliteration = "Maneya olage nadeyitu",
                    english = "It walked inside the house",
                    beats = listOf(
                        PoemBeat("ಮನೆಯ", "ma-ne-ya"),
                        PoemBeat("ಒಳಗೆ", "o-la-ge"),
                        PoemBeat("ನಡೆಯಿತು", "na-de-yi-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ರಾತ್ರಿ ತಂಪು ನಿದ್ದೆ ಮಾಡಿತು",
                    transliteration = "Raatri tampu nidde maaditu",
                    english = "In the cool night, it went to sleep",
                    beats = listOf(
                        PoemBeat("ರಾತ್ರಿ", "raa-tri"),
                        PoemBeat("ತಂಪು", "tam-pu"),
                        PoemBeat("ನಿದ್ದೆ", "nid-de"),
                        PoemBeat("ಮಾಡಿತು", "maa-di-tu")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ಬೆಕ್ಕು", "bekku", "Cat", "Double 'kk' sound: bek-ku"),
                PoemWordCard("ಬಿಳಿ", "bili", "White", "Soft retroflex 'li' at the end"),
                PoemWordCard("ಹಾಲು", "haalu", "Milk", "Stretch the 'aa' — haa-lu"),
                PoemWordCard("ಮನೆ", "mane", "House / Home", "Quick two-syllable everyday word"),
                PoemWordCard("ರಾತ್ರಿ", "raatri", "Night", "Long 'aa' followed by crisp 'tri'")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "ಬಟ್ಟಲ ____ ಕುಡಿಯಿತು",
                promptTranslitMasked = "Battala ____ kudiyitu",
                promptEnglishMasked = "It drank a bowl of ____",
                fullLineKannada = "ಬಟ್ಟಲ ಹಾಲು ಕುಡಿಯಿತು",
                options = listOf(
                    PoemOption("ಹಾಲು", "haalu", "Milk"),
                    PoemOption("ಮರ", "mara", "Tree"),
                    PoemOption("ಹೂವು", "hoovu", "Flower"),
                    PoemOption("ಚೀಲ", "cheela", "Bag")
                ),
                correctIndex = 0,
                explanation = "'ಹಾಲು' (haalu) means milk — a favorite treat for the little cat!"
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ಕುಡಿಯಿತು", "kudiyitu", "drank"),
                options = listOf(
                    PoemOption("ಹಾಲು", "haalu", "milk"),
                    PoemOption("ಮನೆ", "mane", "house"),
                    PoemOption("ನಡೆಯಿತು", "nadeyitu", "walked")
                ),
                correctIndex = 2,
                rhymeExplanation = "ಕುಡಿಯಿತು (kudiyitu) and ನಡೆಯಿತು (nadeyitu) rhyme cleanly on '-yitu' (-ಯಿತು)."
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ಬೆಕ್ಕು", "bekku", "Cat", lineIndex = 0),
                WordHuntTarget("ರಾತ್ರಿ", "raatri", "Night", lineIndex = 3)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Which verse matches the cozy night kitten picture?",
                options = listOf(
                    PoemLine(2, "ಬಟ್ಟಲ ಹಾಲು ಕುಡಿಯಿತು", "Battala haalu kudiyitu", "It drank a bowl of milk", emptyList()),
                    PoemLine(2, "ಏಳು ಬಣ್ಣ ಕಂಡಿತು", "Elu banna kanditu", "Seven colors appeared", emptyList()),
                    PoemLine(3, "ಚಿಕ್ಕ ದೋಣಿ ತೇಲಿತು", "Chikka doni telitu", "The small boat floated", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Animal",
                    choices = listOf(
                        PoemOption("ಪುಟ್ಟ ಬೆಕ್ಕು", "putta bekku", "Little cat"),
                        PoemOption("ಬಿಳಿ ಹಕ್ಕಿ", "bili hakki", "White bird"),
                        PoemOption("ಚಿಕ್ಕ ಮೀನು", "chikka meenu", "Small fish")
                    )
                ),
                PoetryRemixSlot(
                    label = "Place",
                    choices = listOf(
                        PoemOption("ಮನೆಯಲ್ಲಿ", "maneyalli", "in the house"),
                        PoemOption("ಹಾಲಿನಲ್ಲಿ", "haalinalli", "in the milk"),
                        PoemOption("ಬಟ್ಟಲಲ್ಲಿ", "battalalli", "in the bowl")
                    )
                ),
                PoetryRemixSlot(
                    label = "Action",
                    choices = listOf(
                        PoemOption("ನಿದ್ದೆ ಮಾಡಿತು!", "nidde maaditu!", "went to sleep!"),
                        PoemOption("ಹಾಡು ಹಾಡಿತು!", "haadu haaditu!", "sang a song!"),
                        PoemOption("ಓಡಿತು!", "oditu!", "ran around!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_hampi_chariot",
                title = "Hampi Stone Chariot",
                kannadaTitle = "ಹಂಪಿ ರಥ",
                regionTag = "Vijayanagara",
                drawableRes = R.drawable.pop_landmark_hampi_chariot
            )
        ),

        // 4. Haladi Auto (Yellow Auto)
        PadyaPoem(
            id = "poem_haladi_auto",
            levelNumber = 4,
            titleKannada = "ಹಳದಿ ಆಟೋ",
            titleTranslit = "Haladi Auto",
            titleEnglish = "Yellow Auto",
            themeTag = "BENGALURU STREETS",
            illustrationRes = R.drawable.img_poem_auto_city,
            illustrationCaption = "Yellow auto rickshaw rolling past blooming trees in Bengaluru",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ಹಳದಿ ಆಟೋ ದಾರಿಯಲ್ಲಿ",
                    transliteration = "Haladi auto daariyalli",
                    english = "A yellow auto is on the road",
                    beats = listOf(
                        PoemBeat("ಹಳದಿ", "ha-la-di"),
                        PoemBeat("ಆಟೋ", "aa-to"),
                        PoemBeat("ದಾರಿಯಲ್ಲಿ", "daa-ri-yal-li")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ಹೂವಿನ ಮರವು ಪಕ್ಕದಲ್ಲಿ",
                    transliteration = "Hoovina maravu pakkadalli",
                    english = "A flowering tree is beside it",
                    beats = listOf(
                        PoemBeat("ಹೂವಿನ", "hoo-vi-na"),
                        PoemBeat("ಮರವು", "ma-ra-vu"),
                        PoemBeat("ಪಕ್ಕದಲ್ಲಿ", "pak-ka-dal-li")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ಬೇಗ ಬೇಗ ಸಾಗುತಿದೆ",
                    transliteration = "Bega bega saagutide",
                    english = "Fast, fast it moves along",
                    beats = listOf(
                        PoemBeat("ಬೇಗ", "be-ga"),
                        PoemBeat("ಬೇಗ", "be-ga"),
                        PoemBeat("ಸಾಗುತಿದೆ", "saa-gu-ti-de")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ನಮ್ಮ ಊರು ನಗುತಿದೆ",
                    transliteration = "Namma ooru nagutide",
                    english = "Our city is smiling",
                    beats = listOf(
                        PoemBeat("ನಮ್ಮ", "nam-ma"),
                        PoemBeat("ಊರು", "oo-ru"),
                        PoemBeat("ನಗುತಿದೆ", "na-gu-ti-de")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ಹಳದಿ", "haladi", "Yellow", "Color of Bengaluru autos and turmeric!"),
                PoemWordCard("ದಾರಿ", "daari", "Road / Path", "Stretch the first 'aa': daa-ri"),
                PoemWordCard("ಮರ", "mara", "Tree", "Short crisp syllables: ma-ra"),
                PoemWordCard("ಬೇಗ", "bega", "Fast / Quick", "Long 'ee' vowel: bee-ga"),
                PoemWordCard("ಊರು", "ooru", "Town / City", "Warm word for hometown — namma ooru")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "____ ಆಟೋ ದಾರಿಯಲ್ಲಿ",
                promptTranslitMasked = "____ auto daariyalli",
                promptEnglishMasked = "A ____ auto is on the road",
                fullLineKannada = "ಹಳದಿ ಆಟೋ ದಾರಿಯಲ್ಲಿ",
                options = listOf(
                    PoemOption("ನೀರು", "neeru", "Water"),
                    PoemOption("ಹಳದಿ", "haladi", "Yellow"),
                    PoemOption("ಹಾಲು", "haalu", "Milk"),
                    PoemOption("ಹಣ್ಣು", "hannu", "Fruit")
                ),
                correctIndex = 1,
                explanation = "'ಹಳದಿ' (haladi) means yellow — matching the iconic yellow-and-green auto!"
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ದಾರಿಯಲ್ಲಿ", "daariyalli", "on the road"),
                options = listOf(
                    PoemOption("ಪಕ್ಕದಲ್ಲಿ", "pakkadalli", "beside it"),
                    PoemOption("ಬೇಗ", "bega", "fast"),
                    PoemOption("ಊರು", "ooru", "city")
                ),
                correctIndex = 0,
                rhymeExplanation = "ದಾರಿಯಲ್ಲಿ (daariyalli) and ಪಕ್ಕದಲ್ಲಿ (pakkadalli) share the locative ending '-alli' (-ಅಲ್ಲಿ)."
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ಮರವು", "maravu", "Tree", lineIndex = 1),
                WordHuntTarget("ಊರು", "ooru", "City / Town", lineIndex = 3)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Which Kannada line describes this street scene?",
                options = listOf(
                    PoemLine(1, "ಹಳದಿ ಆಟೋ ದಾರಿಯಲ್ಲಿ", "Haladi auto daariyalli", "A yellow auto is on the road", emptyList()),
                    PoemLine(4, "ರಾತ್ರಿ ತಂಪು ನಿದ್ದೆ ಮಾಡಿತು", "Raatri tampu nidde maaditu", "In the cool night, it went to sleep", emptyList()),
                    PoemLine(1, "ನೀಲಿ ಕಡಲು ಹಾಡಿತು", "Neeli kadalu haaditu", "The blue sea sang", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Color / Speed",
                    choices = listOf(
                        PoemOption("ಹಳದಿ ಆಟೋ", "haladi auto", "Yellow auto"),
                        PoemOption("ಪುಟ್ಟ ದೋಣಿ", "putta doni", "Little boat"),
                        PoemOption("ಹಸಿರು ಬಸ್ಸು", "hasiru bassu", "Green bus")
                    )
                ),
                PoetryRemixSlot(
                    label = "Pace",
                    choices = listOf(
                        PoemOption("ಬೇಗ ಬೇಗ", "bega bega", "fast fast"),
                        PoemOption("ಮೆಲ್ಲ ಮೆಲ್ಲ", "mella mella", "slowly slowly"),
                        PoemOption("ನಗು ನಗುತಾ", "nagu nagutaa", "smilingly")
                    )
                ),
                PoetryRemixSlot(
                    label = "Verb",
                    choices = listOf(
                        PoemOption("ಸಾಗುತಿದೆ!", "saagutide!", "moves along!"),
                        PoemOption("ಹಾರಿತು!", "haaritu!", "leaped!"),
                        PoemOption("ನಗುತಿದೆ!", "nagutide!", "is smiling!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_vidhana",
                title = "Vidhana Soudha Badge",
                kannadaTitle = "ವಿಧಾನ ಸೌಧ",
                regionTag = "Bengaluru",
                drawableRes = R.drawable.pop_landmark_vidhana
            )
        ),

        // 5. Kaamanabillu (Rainbow)
        PadyaPoem(
            id = "poem_kaamanabillu",
            levelNumber = 5,
            titleKannada = "ಕಾಮನಬಿಲ್ಲು",
            titleTranslit = "Kaamanabillu",
            titleEnglish = "Rainbow",
            themeTag = "COLORS & SKY",
            illustrationRes = R.drawable.img_poem_rainbow_garden,
            illustrationCaption = "Bright rainbow arching over flowers, mango tree, and warm sun",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ನೀಲಿ ಆಕಾಶ ತೆರೆಯಿತು",
                    transliteration = "Neeli aakaasha tereyitu",
                    english = "The blue sky opened up",
                    beats = listOf(
                        PoemBeat("ನೀಲಿ", "nee-li"),
                        PoemBeat("ಆಕಾಶ", "aa-kaa-sha"),
                        PoemBeat("ತೆರೆಯಿತು", "te-re-yi-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ಏಳು ಬಣ್ಣ ಕಂಡಿತು",
                    transliteration = "Elu banna kanditu",
                    english = "Seven colors appeared",
                    beats = listOf(
                        PoemBeat("ಏಳು", "e-lu"),
                        PoemBeat("ಬಣ್ಣ", "ban-na"),
                        PoemBeat("ಕಂಡಿತು", "kan-di-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ಕೆಂಪು ಹೂವು ಅರಳಿತು",
                    transliteration = "Kempu hoovu aralitu",
                    english = "The red flower bloomed",
                    beats = listOf(
                        PoemBeat("ಕೆಂಪು", "kem-pu"),
                        PoemBeat("ಹೂವು", "hoo-vu"),
                        PoemBeat("ಅರಳಿತು", "a-ra-li-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ತಂಪು ಗಾಳಿ ಬೀಸಿತು",
                    transliteration = "Tampu gaali beesitu",
                    english = "A cool breeze blew",
                    beats = listOf(
                        PoemBeat("ತಂಪು", "tam-pu"),
                        PoemBeat("ಗಾಳಿ", "gaa-li"),
                        PoemBeat("ಬೀಸಿತು", "bee-si-tu")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ನೀಲಿ", "neeli", "Blue", "Long 'ee' sound: nee-li"),
                PoemWordCard("ಆಕಾಶ", "aakaasha", "Sky", "Two long 'aa' vowels!"),
                PoemWordCard("ಬಣ್ಣ", "banna", "Color", "Double retroflex 'nn'"),
                PoemWordCard("ಕೆಂಪು", "kempu", "Red", "Vibrant color of hibiscus flowers"),
                PoemWordCard("ಹೂವು", "hoovu", "Flower", "Long 'oo': hoo-vu")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "ಏಳು ____ ಕಂಡಿತು",
                promptTranslitMasked = "Elu ____ kanditu",
                promptEnglishMasked = "Seven ____ appeared",
                fullLineKannada = "ಏಳು ಬಣ್ಣ ಕಂಡಿತು",
                options = listOf(
                    PoemOption("ಬಣ್ಣ", "banna", "Colors"),
                    PoemOption("ದೋಸೆ", "dose", "Dosa"),
                    PoemOption("ಬೆಕ್ಕು", "bekku", "Cat"),
                    PoemOption("ಮನೆ", "mane", "House")
                ),
                correctIndex = 0,
                explanation = "'ಏಳು ಬಣ್ಣ' (elu banna) means 'seven colors' — just like a rainbow!"
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ಅರಳಿತು", "aralitu", "bloomed"),
                options = listOf(
                    PoemOption("ಗಾಳಿ", "gaali", "wind"),
                    PoemOption("ತೆರೆಯಿತು", "tereyitu", "opened"),
                    PoemOption("ಕೆಂಪು", "kempu", "red")
                ),
                correctIndex = 1,
                rhymeExplanation = "ಅರಳಿತು (aralitu) and ತೆರೆಯಿತು (tereyitu) both echo the '-itu' ending."
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ಆಕಾಶ", "aakaasha", "Sky", lineIndex = 0),
                WordHuntTarget("ಹೂವು", "hoovu", "Flower", lineIndex = 2)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Choose the verse that matches the rainbow & blooming flower scene:",
                options = listOf(
                    PoemLine(2, "ಏಳು ಬಣ್ಣ ಕಂಡಿತು", "Elu banna kanditu", "Seven colors appeared", emptyList()),
                    PoemLine(2, "ಬಟ್ಟಲ ಹಾಲು ಕುಡಿಯಿತು", "Battala haalu kudiyitu", "It drank a bowl of milk", emptyList()),
                    PoemLine(3, "ಪುಟ್ಟ ಕಪ್ಪು ಕಾಫಿ ಇತ್ತು", "Putta kappu kaafi ittu", "There was a little black coffee", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Color",
                    choices = listOf(
                        PoemOption("ನೀಲಿ", "neeli", "Blue"),
                        PoemOption("ಕೆಂಪು", "kempu", "Red"),
                        PoemOption("ಹಳದಿ", "haladi", "Yellow")
                    )
                ),
                PoetryRemixSlot(
                    label = "Nature",
                    choices = listOf(
                        PoemOption("ಹೂವು", "hoovu", "flower"),
                        PoemOption("ಆಕಾಶ", "aakaasha", "sky"),
                        PoemOption("ಗಾಳಿ", "gaali", "breeze")
                    )
                ),
                PoetryRemixSlot(
                    label = "Verb",
                    choices = listOf(
                        PoemOption("ಅರಳಿತು!", "aralitu!", "bloomed!"),
                        PoemOption("ಬೀಸಿತು!", "beesitu!", "blew!"),
                        PoemOption("ನಕ್ಕಿತು!", "nakkitu!", "smiled!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_mysore_palace",
                title = "Mysore Palace Crest",
                kannadaTitle = "ಮೈಸೂರು ಅರಮನೆ",
                regionTag = "Mysuru",
                drawableRes = R.drawable.pop_landmark_mysore_palace
            )
        ),

        // 6. Namma Tota (Our Garden)
        PadyaPoem(
            id = "poem_namma_tota",
            levelNumber = 6,
            titleKannada = "ನಮ್ಮ ತೋಟ",
            titleTranslit = "Namma Tota",
            titleEnglish = "Our Garden",
            themeTag = "GARDEN & FRUIT",
            illustrationRes = R.drawable.img_poem_rainbow_garden,
            illustrationCaption = "Mango tree shade, sweet fruit, and cool garden water",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ದೊಡ್ಡ ಮರದ ನೆರಳಲ್ಲಿ",
                    transliteration = "Dodda marada neralalli",
                    english = "In the shade of the big tree",
                    beats = listOf(
                        PoemBeat("ದೊಡ್ಡ", "dod-da"),
                        PoemBeat("ಮರದ", "ma-ra-da"),
                        PoemBeat("ನೆರಳಲ್ಲಿ", "ne-ra-lal-li")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ಸಿಹಿ ಮಾವಿನ ಹಣ್ಣಲ್ಲಿ",
                    transliteration = "Sihi maavina hannalli",
                    english = "Inside the sweet mango fruit",
                    beats = listOf(
                        PoemBeat("ಸಿಹಿ", "si-hi"),
                        PoemBeat("ಮಾವಿನ", "maa-vi-na"),
                        PoemBeat("ಹಣ್ಣಲ್ಲಿ", "han-nal-li")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ತಂಪು ನೀರು ಹರಿಯಿತು",
                    transliteration = "Tampu neeru hariyitu",
                    english = "Cool water flowed",
                    beats = listOf(
                        PoemBeat("ತಂಪು", "tam-pu"),
                        PoemBeat("ನೀರು", "nee-ru"),
                        PoemBeat("ಹರಿಯಿತು", "ha-ri-yi-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ನಮ್ಮ ತೋಟ ಬೆಳೆಯಿತು",
                    transliteration = "Namma tota beleyitu",
                    english = "Our garden grew",
                    beats = listOf(
                        PoemBeat("ನಮ್ಮ", "nam-ma"),
                        PoemBeat("ತೋಟ", "to-ta"),
                        PoemBeat("ಬೆಳೆಯಿತು", "be-le-yi-tu")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ದೊಡ್ಡ", "dodda", "Big", "Opposite of 'chikka' / 'putta'"),
                PoemWordCard("ಸಿಹಿ", "sihi", "Sweet", "Great for describing fruits and desserts"),
                PoemWordCard("ಹಣ್ಣು", "hannu", "Fruit", "Soft 'nn' before 'u'"),
                PoemWordCard("ನೀರು", "neeru", "Water", "Essential word everywhere in Karnataka"),
                PoemWordCard("ತೋಟ", "tota", "Garden / Orchard", "Long 'oo': too-ta")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "ತಂಪು ____ ಹರಿಯಿತು",
                promptTranslitMasked = "Tampu ____ hariyitu",
                promptEnglishMasked = "Cool ____ flowed",
                fullLineKannada = "ತಂಪು ನೀರು ಹರಿಯಿತು",
                options = listOf(
                    PoemOption("ಹಕ್ಕಿ", "hakki", "Bird"),
                    PoemOption("ಬಣ್ಣ", "banna", "Color"),
                    PoemOption("ನೀರು", "neeru", "Water"),
                    PoemOption("ಚಂದಿರ", "chandira", "Moon")
                ),
                correctIndex = 2,
                explanation = "'ತಂಪು ನೀರು' (tampu neeru) means cool, refreshing water!"
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ನೆರಳಲ್ಲಿ", "neralalli", "in the shade"),
                options = listOf(
                    PoemOption("ಹಣ್ಣಲ್ಲಿ", "hannalli", "in the fruit"),
                    PoemOption("ನೀರು", "neeru", "water"),
                    PoemOption("ತೋಟ", "tota", "garden")
                ),
                correctIndex = 0,
                rhymeExplanation = "ನೆರಳಲ್ಲಿ (neralalli) and ಹಣ್ಣಲ್ಲಿ (hannalli) rhyme smoothly on '-alli'."
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ನೀರು", "neeru", "Water", lineIndex = 2),
                WordHuntTarget("ತೋಟ", "tota", "Garden", lineIndex = 3)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Which Kannada verse describes the mango tree garden?",
                options = listOf(
                    PoemLine(2, "ಸಿಹಿ ಮಾವಿನ ಹಣ್ಣಲ್ಲಿ", "Sihi maavina hannalli", "Inside the sweet mango fruit", emptyList()),
                    PoemLine(1, "ಹಳದಿ ಆಟೋ ದಾರಿಯಲ್ಲಿ", "Haladi auto daariyalli", "A yellow auto is on the road", emptyList()),
                    PoemLine(3, "ಪುಟ್ಟ ತಾರೆ ಮಿನುಗಿತು", "Putta taare minugitu", "The little star twinkled", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Taste",
                    choices = listOf(
                        PoemOption("ಸಿಹಿ", "sihi", "Sweet"),
                        PoemOption("ತಂಪು", "tampu", "Cool"),
                        PoemOption("ದೊಡ್ಡ", "dodda", "Big")
                    )
                ),
                PoetryRemixSlot(
                    label = "Item",
                    choices = listOf(
                        PoemOption("ಮಾವಿನ ಹಣ್ಣು", "maavina hannu", "mango fruit"),
                        PoemOption("ಕಾವೇರಿ ನೀರು", "kaaveri neeru", "Kaveri water"),
                        PoemOption("ಹಸಿರು ತೋಟ", "hasiru tota", "green garden")
                    )
                ),
                PoetryRemixSlot(
                    label = "Verb",
                    choices = listOf(
                        PoemOption("ಬೆಳೆಯಿತು!", "beleyitu!", "grew!"),
                        PoemOption("ಹರಿಯಿತು!", "hariyitu!", "flowed!"),
                        PoemOption("ರುಚಿ ಇತ್ತು!", "ruchi ittu!", "was tasty!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_belur_temple",
                title = "Belur Hoysala Star",
                kannadaTitle = "ಬೇಲೂರು ದೇಗುಲ",
                regionTag = "Hassan",
                drawableRes = R.drawable.pop_landmark_belur_temple
            )
        ),

        // 7. Chandira Baa (Come, Moon)
        PadyaPoem(
            id = "poem_chandira_baa",
            levelNumber = 7,
            titleKannada = "ಚಂದಿರ ಬಾ",
            titleTranslit = "Chandira Baa",
            titleEnglish = "Come, Moon",
            themeTag = "NIGHT & STARS",
            illustrationRes = R.drawable.img_poem_moon_cat,
            illustrationCaption = "Glowing crescent moon and twinkling stars through the evening window",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ಚಂದಿರ ಬಾ, ಚಂದಿರ ಬಾ",
                    transliteration = "Chandira baa, chandira baa",
                    english = "Come moon, come moon",
                    beats = listOf(
                        PoemBeat("ಚಂದಿರ", "chan-di-ra"),
                        PoemBeat("ಬಾ", "baa"),
                        PoemBeat("ಚಂದಿರ", "chan-di-ra"),
                        PoemBeat("ಬಾ", "baa")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ಬೆಳ್ಳಿ ಬೆಳಕು ತಂದು ಬಾ",
                    transliteration = "Belli belaku tandu baa",
                    english = "Bring silver light and come",
                    beats = listOf(
                        PoemBeat("ಬೆಳ್ಳಿ", "bel-li"),
                        PoemBeat("ಬೆಳಕು", "be-la-ku"),
                        PoemBeat("ತಂದು", "tan-du"),
                        PoemBeat("ಬಾ", "baa")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ಪುಟ್ಟ ತಾರೆ ಮಿನುಗಿತು",
                    transliteration = "Putta taare minugitu",
                    english = "The little star twinkled",
                    beats = listOf(
                        PoemBeat("ಪುಟ್ಟ", "put-ta"),
                        PoemBeat("ತಾರೆ", "taa-re"),
                        PoemBeat("ಮಿನುಗಿತು", "mi-nu-gi-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ಸಿಹಿ ಕನಸು ಮೂಡಿತು",
                    transliteration = "Sihi kanasu mooditu",
                    english = "A sweet dream appeared",
                    beats = listOf(
                        PoemBeat("ಸಿಹಿ", "si-hi"),
                        PoemBeat("ಕನಸು", "ka-na-su"),
                        PoemBeat("ಮೂಡಿತು", "moo-di-tu")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ಚಂದಿರ", "chandira", "Moon", "Classic poetic Kannada word for moon"),
                PoemWordCard("ಬೆಳಕು", "belaku", "Light", "Starts with short 'be' — be-la-ku"),
                PoemWordCard("ತಾರೆ", "taare", "Star", "Long 'aa': taa-re"),
                PoemWordCard("ಕನಸು", "kanasu", "Dream", "Three gentle short syllables"),
                PoemWordCard("ಬಾ", "baa", "Come (friendly)", "Long 'aa' vowel — friendly invitation")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "ಪುಟ್ಟ ____ ಮಿನುಗಿತು",
                promptTranslitMasked = "Putta ____ minugitu",
                promptEnglishMasked = "The little ____ twinkled",
                fullLineKannada = "ಪುಟ್ಟ ತಾರೆ ಮಿನುಗಿತು",
                options = listOf(
                    PoemOption("ದೋಸೆ", "dose", "Dosa"),
                    PoemOption("ತಾರೆ", "taare", "Star"),
                    PoemOption("ಚೀಲ", "cheela", "Bag"),
                    PoemOption("ಆಟೋ", "auto", "Auto")
                ),
                correctIndex = 1,
                explanation = "'ತಾರೆ' (taare) means star — twinkling alongside the silver moon!"
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ಮಿನುಗಿತು", "minugitu", "twinkled"),
                options = listOf(
                    PoemOption("ಬೆಳಕು", "belaku", "light"),
                    PoemOption("ಕನಸು", "kanasu", "dream"),
                    PoemOption("ಮೂಡಿತು", "mooditu", "appeared")
                ),
                correctIndex = 2,
                rhymeExplanation = "ಮಿನುಗಿತು (minugitu) and ಮೂಡಿತು (mooditu) mirror each other on '-itu'."
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ಬೆಳಕು", "belaku", "Light", lineIndex = 1),
                WordHuntTarget("ಕನಸು", "kanasu", "Dream", lineIndex = 3)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Select the Kannada line that matches the moonlit stars scene:",
                options = listOf(
                    PoemLine(3, "ಪುಟ್ಟ ತಾರೆ ಮಿನುಗಿತು", "Putta taare minugitu", "The little star twinkled", emptyList()),
                    PoemLine(1, "ಬಿಸಿ ಬಿಸಿ ದೋಸೆ ಬಂತು", "Bisi bisi dose bantu", "Hot hot dosa arrived", emptyList()),
                    PoemLine(3, "ಹಸಿರು ಸೊಪ್ಪು ಚೀಲದಲ್ಲಿದೆ", "Hasiru soppu cheeladallide", "Green leafy greens are in the bag", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Night Friend",
                    choices = listOf(
                        PoemOption("ಚಂದಿರ", "chandira", "Moon"),
                        PoemOption("ಪುಟ್ಟ ತಾರೆ", "putta taare", "Little star"),
                        PoemOption("ಬೆಳ್ಳಿ ಬೆಳಕು", "belli belaku", "Silver light")
                    )
                ),
                PoetryRemixSlot(
                    label = "Gift",
                    choices = listOf(
                        PoemOption("ಸಿಹಿ ಕನಸು", "sihi kanasu", "sweet dream"),
                        PoemOption("ತಂಪು ಗಾಳಿ", "tampu gaali", "cool breeze"),
                        PoemOption("ನಗು ನಗು", "nagu nagu", "happy smile")
                    )
                ),
                PoetryRemixSlot(
                    label = "Ending",
                    choices = listOf(
                        PoemOption("ತಂದು ಬಾ!", "tandu baa!", "bring and come!"),
                        PoemOption("ಮಿನುಗಿತು!", "minugitu!", "twinkled!"),
                        PoemOption("ಮೂಡಿತು!", "mooditu!", "appeared!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_gol_gumbaz",
                title = "Gol Gumbaz Dome",
                kannadaTitle = "ಗೋಲ್ ಗುಂಬಜ್",
                regionTag = "Vijayapura",
                drawableRes = R.drawable.pop_landmark_gol_gumbaz
            )
        ),

        // 8. Santeya Dina (Market Day)
        PadyaPoem(
            id = "poem_santeya_dina",
            levelNumber = 8,
            titleKannada = "ಸಂತೆಯ ದಿನ",
            titleTranslit = "Santeya Dina",
            titleEnglish = "Market Day",
            themeTag = "MARKET & FAMILY",
            illustrationRes = R.drawable.img_poem_auto_city,
            illustrationCaption = "Morning auto ride to the vibrant street market for fresh fruit & greens",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ಬೆಳಗ್ಗೆ ಬೇಗ ಸಂತೆಗೆ ಹೋದೆ",
                    transliteration = "Belagge bega santege hode",
                    english = "Early morning I went to the market",
                    beats = listOf(
                        PoemBeat("ಬೆಳಗ್ಗೆ", "be-lag-ge"),
                        PoemBeat("ಬೇಗ", "be-ga"),
                        PoemBeat("ಸಂತೆಗೆ", "san-te-ge"),
                        PoemBeat("ಹೋದೆ", "ho-de")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ತಾಜಾ ಹಣ್ಣು ಕೈಯಲ್ಲಿ ತಂದೆ",
                    transliteration = "Taajaa hannu kaiyalli tande",
                    english = "I brought fresh fruit in my hand",
                    beats = listOf(
                        PoemBeat("ತಾಜಾ", "taa-jaa"),
                        PoemBeat("ಹಣ್ಣು", "han-nu"),
                        PoemBeat("ಕೈಯಲ್ಲಿ", "kai-yal-li"),
                        PoemBeat("ತಂದೆ", "tan-de")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ಹಸಿರು ಸೊಪ್ಪು ಚೀಲದಲ್ಲಿದೆ",
                    transliteration = "Hasiru soppu cheeladallide",
                    english = "Green leafy greens are in the bag",
                    beats = listOf(
                        PoemBeat("ಹಸಿರು", "ha-si-ru"),
                        PoemBeat("ಸೊಪ್ಪು", "sop-pu"),
                        PoemBeat("ಚೀಲದಲ್ಲಿದೆ", "chee-la-dal-li-de")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ಅಮ್ಮನ ನಗು ಮನೆಯಲ್ಲಿ ಇದೆ",
                    transliteration = "Ammana nagu maneyalli ide",
                    english = "Mother's smile is at home",
                    beats = listOf(
                        PoemBeat("ಅಮ್ಮನ", "am-ma-na"),
                        PoemBeat("ನಗು", "na-gu"),
                        PoemBeat("ಮನೆಯಲ್ಲಿ", "ma-ne-yal-li"),
                        PoemBeat("ಇದೆ", "i-de")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ಬೆಳಗ್ಗೆ", "belagge", "Morning", "Double 'gg': be-lag-ge"),
                PoemWordCard("ಸಂತೆ", "sante", "Market", "Traditional weekly bazaar in Karnataka"),
                PoemWordCard("ತಾಜಾ", "taajaa", "Fresh", "Both 'aa' vowels are long!"),
                PoemWordCard("ಚೀಲ", "cheela", "Bag", "Stretch long 'ee': chee-la"),
                PoemWordCard("ಅಮ್ಮ", "amma", "Mother", "Universal affectionate word across South India")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "____ ಹಣ್ಣು ಕೈಯಲ್ಲಿ ತಂದೆ",
                promptTranslitMasked = "____ hannu kaiyalli tande",
                promptEnglishMasked = "I brought ____ fruit in my hand",
                fullLineKannada = "ತಾಜಾ ಹಣ್ಣು ಕೈಯಲ್ಲಿ ತಂದೆ",
                options = listOf(
                    PoemOption("ರಾತ್ರಿ", "raatri", "Night"),
                    PoemOption("ತಾಜಾ", "taajaa", "Fresh"),
                    PoemOption("ಕಡಲು", "kadalu", "Sea"),
                    PoemOption("ಬೆಕ್ಕು", "bekku", "Cat")
                ),
                correctIndex = 1,
                explanation = "'ತಾಜಾ ಹಣ್ಣು' (taajaa hannu) means fresh fruit straight from the market!"
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ಹೋದೆ", "hode", "I went"),
                options = listOf(
                    PoemOption("ತಂದೆ", "tande", "I brought"),
                    PoemOption("ಸೊಪ್ಪು", "soppu", "greens"),
                    PoemOption("ನಗು", "nagu", "smile")
                ),
                correctIndex = 0,
                rhymeExplanation = "ಹೋದೆ (hode) and ತಂದೆ (tande) share the first-person past '-de' (-ದೆ) rhythm."
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ಸಂತೆಗೆ", "santege", "To the market", lineIndex = 0),
                WordHuntTarget("ನಗು", "nagu", "Smile", lineIndex = 3)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Which morning street market line matches the journey scene?",
                options = listOf(
                    PoemLine(1, "ಬೆಳಗ್ಗೆ ಬೇಗ ಸಂತೆಗೆ ಹೋದೆ", "Belagge bega santege hode", "Early morning I went to the market", emptyList()),
                    PoemLine(1, "ಮಳೆ ಬಂತು, ಮಳೆ ಬಂತು", "Male bantu, male bantu", "Rain came, rain came", emptyList()),
                    PoemLine(3, "ಪುಟ್ಟ ತಾರೆ ಮಿನುಗಿತು", "Putta taare minugitu", "The little star twinkled", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Time",
                    choices = listOf(
                        PoemOption("ಬೆಳಗ್ಗೆ ಬೇಗ", "belagge bega", "Early morning"),
                        PoemOption("ಮಧ್ಯಾಹ್ನ", "madhyaahna", "Afternoon"),
                        PoemOption("ಸಂಜೆ ಹೊತ್ತು", "sanje hottu", "Evening time")
                    )
                ),
                PoetryRemixSlot(
                    label = "Item",
                    choices = listOf(
                        PoemOption("ತಾಜಾ ಹಣ್ಣು", "taajaa hannu", "fresh fruit"),
                        PoemOption("ಹಸಿರು ಸೊಪ್ಪು", "hasiru soppu", "green leafy greens"),
                        PoemOption("ಬಿಸಿ ದೋಸೆ", "bisi dose", "hot dosa")
                    )
                ),
                PoetryRemixSlot(
                    label = "Action",
                    choices = listOf(
                        PoemOption("ಮನೆಗೆ ತಂದೆ!", "manege tande!", "I brought home!"),
                        PoemOption("ಚೀಲದಲ್ಲಿ ಇದೆ!", "cheeladalli ide!", "is in the bag!"),
                        PoemOption("ಅಮ್ಮನಿಗೆ ಕೊಟ್ಟೆ!", "ammanige kotte!", "I gave to Mom!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_badami_caves",
                title = "Badami Sandstone Seal",
                kannadaTitle = "ಬಾದಾಮಿ ಗುಹೆಗಳು",
                regionTag = "Bagalkot",
                drawableRes = R.drawable.pop_landmark_badami_caves
            )
        ),

        // 9. Neeli Kadalu (Blue Sea)
        PadyaPoem(
            id = "poem_neeli_kadalu",
            levelNumber = 9,
            titleKannada = "ನೀಲಿ ಕಡಲು",
            titleTranslit = "Neeli Kadalu",
            titleEnglish = "Blue Sea",
            themeTag = "COASTAL KARNATAKA",
            illustrationRes = R.drawable.pop_landmark_murudeshwara,
            illustrationCaption = "Coastal Karnataka temple tower overlooking blue waves and boats",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ನೀಲಿ ಕಡಲು ಹಾಡಿತು",
                    transliteration = "Neeli kadalu haaditu",
                    english = "The blue sea sang",
                    beats = listOf(
                        PoemBeat("ನೀಲಿ", "nee-li"),
                        PoemBeat("ಕಡಲು", "ka-da-lu"),
                        PoemBeat("ಹಾಡಿತು", "haa-di-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ಬಿಳಿ ಅಲೆ ಬಂದು ಸೇರಿತು",
                    transliteration = "Bili ale bandu seritu",
                    english = "The white wave came and joined",
                    beats = listOf(
                        PoemBeat("ಬಿಳಿ", "bi-li"),
                        PoemBeat("ಅಲೆ", "a-le"),
                        PoemBeat("ಬಂದು", "ban-du"),
                        PoemBeat("ಸೇರಿತು", "se-ri-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ಚಿಕ್ಕ ದೋಣಿ ತೇಲಿತು",
                    transliteration = "Chikka doni telitu",
                    english = "The small boat floated",
                    beats = listOf(
                        PoemBeat("ಚಿಕ್ಕ", "chik-ka"),
                        PoemBeat("ದೋಣಿ", "do-ni"),
                        PoemBeat("ತೇಲಿತು", "te-li-tu")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ಪುಟ್ಟ ಮೀನು ಹಾರಿತು",
                    transliteration = "Putta meenu haaritu",
                    english = "The little fish leaped",
                    beats = listOf(
                        PoemBeat("ಪುಟ್ಟ", "put-ta"),
                        PoemBeat("ಮೀನು", "mee-nu"),
                        PoemBeat("ಹಾರಿತು", "haa-ri-tu")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ಕಡಲು", "kadalu", "Sea / Ocean", "Poetic Kannada word for ocean"),
                PoemWordCard("ಅಲೆ", "ale", "Wave", "Crisp two syllables: a-le"),
                PoemWordCard("ದೋಣಿ", "doni", "Boat", "Long 'oo' and retroflex 'ni'"),
                PoemWordCard("ಮೀನು", "meenu", "Fish", "Long 'ee': mee-nu"),
                PoemWordCard("ಚಿಕ್ಕ", "chikka", "Small", "Very useful pair with 'dodda' (big)")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "ಚಿಕ್ಕ ____ ತೇಲಿತು",
                promptTranslitMasked = "Chikka ____ telitu",
                promptEnglishMasked = "The small ____ floated",
                fullLineKannada = "ಚಿಕ್ಕ ದೋಣಿ ತೇಲಿತು",
                options = listOf(
                    PoemOption("ಮನೆ", "mane", "House"),
                    PoemOption("ಕಾಫಿ", "kaafi", "Coffee"),
                    PoemOption("ದೋಣಿ", "doni", "Boat"),
                    PoemOption("ಹೂವು", "hoovu", "Flower")
                ),
                correctIndex = 2,
                explanation = "'ಚಿಕ್ಕ ದೋಣಿ' (chikka doni) means small boat gliding over the waves!"
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ತೇಲಿತು", "telitu", "floated"),
                options = listOf(
                    PoemOption("ಹಾರಿತು", "haaritu", "leaped"),
                    PoemOption("ಮೀನು", "meenu", "fish"),
                    PoemOption("ಕಡಲು", "kadalu", "sea")
                ),
                correctIndex = 0,
                rhymeExplanation = "ತೇಲಿತು (telitu) and ಹಾರಿತು (haaritu) rhyme neatly with '-itu' (-ಇತು)."
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ಅಲೆ", "ale", "Wave", lineIndex = 1),
                WordHuntTarget("ಮೀನು", "meenu", "Fish", lineIndex = 3)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Which verse fits Karnataka's coastal Murudeshwara shoreline?",
                options = listOf(
                    PoemLine(2, "ಬಿಳಿ ಅಲೆ ಬಂದು ಸೇರಿತು", "Bili ale bandu seritu", "The white wave came and joined", emptyList()),
                    PoemLine(1, "ದೊಡ್ಡ ಮರದ ನೆರಳಲ್ಲಿ", "Dodda marada neralalli", "In the shade of the big tree", emptyList()),
                    PoemLine(3, "ಪುಟ್ಟ ಕಪ್ಪು ಕಾಫಿ ಇತ್ತು", "Putta kappu kaafi ittu", "There was a little black coffee", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Ocean Pal",
                    choices = listOf(
                        PoemOption("ಪುಟ್ಟ ಮೀನು", "putta meenu", "Little fish"),
                        PoemOption("ಚಿಕ್ಕ ದೋಣಿ", "chikka doni", "Small boat"),
                        PoemOption("ಬಿಳಿ ಅಲೆ", "bili ale", "White wave")
                    )
                ),
                PoetryRemixSlot(
                    label = "Where",
                    choices = listOf(
                        PoemOption("ನೀಲಿ ಕಡಲಲ್ಲಿ", "neeli kadalalli", "in the blue sea"),
                        PoemOption("ತಂಪು ನೀರಲ್ಲಿ", "tampu neeralli", "in cool water"),
                        PoemOption("ಗಾಳಿಯಲ್ಲಿ", "gaaliyalli", "in the breeze")
                    )
                ),
                PoetryRemixSlot(
                    label = "Action",
                    choices = listOf(
                        PoemOption("ಹಾರಿತು!", "haaritu!", "leaped!"),
                        PoemOption("ತೇಲಿತು!", "telitu!", "floated!"),
                        PoemOption("ಹಾಡಿತು!", "haaditu!", "sang!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_murudeshwara",
                title = "Murudeshwara Coast",
                kannadaTitle = "ಮುರುಡೇಶ್ವರ",
                regionTag = "Uttara Kannada",
                drawableRes = R.drawable.pop_landmark_murudeshwara
            )
        ),

        // 10. Kannada Naadu (Land of Kannada)
        PadyaPoem(
            id = "poem_kannada_naadu",
            levelNumber = 10,
            titleKannada = "ಕನ್ನಡ ನಾಡು",
            titleTranslit = "Kannada Naadu",
            titleEnglish = "Land of Kannada",
            themeTag = "GREETINGS & FRIENDSHIP",
            illustrationRes = R.drawable.pop_landmark_mysore_palace,
            illustrationCaption = "Illuminated Mysore Palace celebrating Karnataka culture & friendship",
            lines = listOf(
                PoemLine(
                    lineNumber = 1,
                    kannada = "ನಮಸ್ಕಾರ ಗೆಳೆಯ ನಮಸ್ಕಾರ",
                    transliteration = "Namaskaara geleya namaskaara",
                    english = "Hello friend, hello",
                    beats = listOf(
                        PoemBeat("ನಮಸ್ಕಾರ", "na-mas-kaa-ra"),
                        PoemBeat("ಗೆಳೆಯ", "ge-le-ya"),
                        PoemBeat("ನಮಸ್ಕಾರ", "na-mas-kaa-ra")
                    )
                ),
                PoemLine(
                    lineNumber = 2,
                    kannada = "ಕನ್ನಡ ಮಾತು ಮಧುರ ಸಾರ",
                    transliteration = "Kannada maatu madhura saara",
                    english = "Kannada words have sweet essence",
                    beats = listOf(
                        PoemBeat("ಕನ್ನಡ", "kan-na-da"),
                        PoemBeat("ಮಾತು", "maa-tu"),
                        PoemBeat("ಮಧುರ", "ma-dhu-ra"),
                        PoemBeat("ಸಾರ", "saa-ra")
                    )
                ),
                PoemLine(
                    lineNumber = 3,
                    kannada = "ಸ್ನೇಹ ಬೆಳಕು ನಮ್ಮ ದಾರಿ",
                    transliteration = "Sneha belaku namma daari",
                    english = "Light of friendship is our path",
                    beats = listOf(
                        PoemBeat("ಸ್ನೇಹ", "sne-ha"),
                        PoemBeat("ಬೆಳಕು", "be-la-ku"),
                        PoemBeat("ನಮ್ಮ", "nam-ma"),
                        PoemBeat("ದಾರಿ", "daa-ri")
                    )
                ),
                PoemLine(
                    lineNumber = 4,
                    kannada = "ಸಂತೋಷ ಹಾಡು ಹಾಡೋಣ ಸೇರಿ",
                    transliteration = "Santosha haadu haadona seri",
                    english = "Let us sing a happy song together",
                    beats = listOf(
                        PoemBeat("ಸಂತೋಷ", "san-to-sha"),
                        PoemBeat("ಹಾಡು", "haa-du"),
                        PoemBeat("ಹಾಡೋಣ", "haa-do-na"),
                        PoemBeat("ಸೇರಿ", "se-ri")
                    )
                )
            ),
            keyWords = listOf(
                PoemWordCard("ನಮಸ್ಕಾರ", "namaskaara", "Hello", "Long 'kaa' in the third syllable"),
                PoemWordCard("ಗೆಳೆಯ", "geleya", "Friend", "Warm everyday word for friend"),
                PoemWordCard("ಮಾತು", "maatu", "Word / Speech", "Long 'aa': maa-tu"),
                PoemWordCard("ಸ್ನೇಹ", "sneha", "Friendship", "Begins with 'sn' blend + long 'e'"),
                PoemWordCard("ಸಂತೋಷ", "santosha", "Happiness / Joy", "Say 'thumba santosha' = very happy!")
            ),
            missingWord = MissingWordChallenge(
                promptLineKannada = "____ ಗೆಳೆಯ ನಮಸ್ಕಾರ",
                promptTranslitMasked = "____ geleya namaskaara",
                promptEnglishMasked = "____ friend, hello",
                fullLineKannada = "ನಮಸ್ಕಾರ ಗೆಳೆಯ ನಮಸ್ಕಾರ",
                options = listOf(
                    PoemOption("ನಮಸ್ಕಾರ", "namaskaara", "Hello"),
                    PoemOption("ಅಲೆ", "ale", "Wave"),
                    PoemOption("ಮಳೆ", "male", "Rain"),
                    PoemOption("ಬೆಕ್ಕು", "bekku", "Cat")
                ),
                correctIndex = 0,
                explanation = "Framing the line with 'ನಮಸ್ಕಾರ' creates an unforgettable greeting chant!"
            ),
            rhymeMatch = RhymeMatchChallenge(
                targetWord = PoemOption("ದಾರಿ", "daari", "path"),
                options = listOf(
                    PoemOption("ಮಾತು", "maatu", "word"),
                    PoemOption("ಸೇರಿ", "seri", "together"),
                    PoemOption("ಸ್ನೇಹ", "sneha", "friendship")
                ),
                correctIndex = 1,
                rhymeExplanation = "ದಾರಿ (daari) and ಸೇರಿ (seri) finish with the crisp '-ri' (-ರಿ) rhyme."
            ),
            wordHuntTargets = listOf(
                WordHuntTarget("ಗೆಳೆಯ", "geleya", "Friend", lineIndex = 0),
                WordHuntTarget("ಸಂತೋಷ", "santosha", "Happiness", lineIndex = 3)
            ),
            pictureToPoem = PictureToPoemChallenge(
                questionPrompt = "Which celebratory verse belongs with Karnataka's grand Mysore Palace?",
                options = listOf(
                    PoemLine(4, "ಸಂತೋಷ ಹಾಡು ಹಾಡೋಣ ಸೇರಿ", "Santosha haadu haadona seri", "Let us sing a happy song together", emptyList()),
                    PoemLine(2, "ಬಟ್ಟಲ ಹಾಲು ಕುಡಿಯಿತು", "Battala haalu kudiyitu", "It drank a bowl of milk", emptyList()),
                    PoemLine(1, "ಮಳೆ ಬಂತು, ಮಳೆ ಬಂತು", "Male bantu, male bantu", "Rain came, rain came", emptyList())
                ),
                correctIndex = 0
            ),
            remixSlots = listOf(
                PoetryRemixSlot(
                    label = "Greeting",
                    choices = listOf(
                        PoemOption("ನಮಸ್ಕಾರ", "namaskaara", "Hello"),
                        PoemOption("ಸಂತೋಷ", "santosha", "Joyful"),
                        PoemOption("ಮಧುರ", "madhura", "Sweet")
                    )
                ),
                PoetryRemixSlot(
                    label = "Companion",
                    choices = listOf(
                        PoemOption("ಗೆಳೆಯ", "geleya", "friend"),
                        PoemOption("ಕನ್ನಡ ಹಾಡು", "kannada haadu", "Kannada song"),
                        PoemOption("ನಮ್ಮ ಊರು", "namma ooru", "our city")
                    )
                ),
                PoetryRemixSlot(
                    label = "Chorus",
                    choices = listOf(
                        PoemOption("ಹಾಡೋಣ ಸೇರಿ!", "haadona seri!", "let's sing together!"),
                        PoemOption("ನಗೋಣ ಬಾ!", "nagona baa!", "come let's smile!"),
                        PoemOption("ಸೂಪರ್ ಗುರು!", "super guru!", "super pal!")
                    )
                )
            ),
            sticker = KarnatakaSticker(
                id = "sticker_karnataka_star",
                title = "Kannada Poet Trophy",
                kannadaTitle = "ಕನ್ನಡ ಕವಿ ನಕ್ಷತ್ರ",
                regionTag = "Karnataka",
                drawableRes = R.drawable.pop_star_reward
            )
        )
    )

    /**
     * Progressive unlocking rules for Padya Aata:
     * - Poems 1–3 are unlocked right away so learners have variety from the start.
     * - Poems 4–10 unlock once at least 2 challenges are completed in the previous poem.
     */
    fun isPoemUnlocked(poemIndex: Int, completedChallenges: Set<String>): Boolean {
        if (poemIndex <= 2) return true
        val prevPoem = poems.getOrNull(poemIndex - 1) ?: return true
        return completedCountForPoem(prevPoem.id, completedChallenges) >= 2
    }

    /**
     * Progressive game-tier rules within an unlocked poem:
     * - Tier 1 games (Missing Word, Rhyme Match, Word Hunt): unlocked immediately.
     * - Tier 2 games (Rhythm Tap, Picture to Poem, Poem Puzzle): unlocked after 1 challenge in this poem.
     * - Tier 3 games (Echo the Poem, Poetry Remix): unlocked after 3 challenges in this poem.
     */
    fun isGameUnlocked(
        poemId: String,
        gameType: PadyaGameType,
        completedChallenges: Set<String>
    ): Boolean {
        val count = completedCountForPoem(poemId, completedChallenges)
        return when (gameType.tier) {
            1 -> true
            2 -> count >= 1
            else -> count >= 3
        }
    }

    fun completedCountForPoem(poemId: String, completedChallenges: Set<String>): Int {
        val prefix = "${poemId}_"
        return completedChallenges.count { it.startsWith(prefix) }
    }

    fun challengeKey(poemId: String, gameType: PadyaGameType): String =
        "${poemId}_${gameType.name}"
}
