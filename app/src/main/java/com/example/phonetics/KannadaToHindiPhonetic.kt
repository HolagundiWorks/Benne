package com.example.phonetics

/**
 * A single Devanagari syllable (akshara) or punctuation token produced from Kannada text.
 * [isDheergha] is true when the syllable carries a long vowel (दीर्घ: आ, ई, ऊ, ॠ, ए, ऐ, ओ, औ
 * or matras ा, ी, ू, ॄ, े, ै, ो, ौ) so UI can apply typographic emphasis and a red stroke.
 */
data class HindiPhoneticSegment(
    val text: String,
    val isDheergha: Boolean
)

/**
 * Converts Kannada script into Devanagari phonetic segments grouped by atomic syllable,
 * never splitting a consonant from its vowel matra or virama so Devanagari shaping stays intact.
 */
object KannadaToHindiPhonetic {

    private val independentVowels = mapOf(
        'ಅ' to Pair("अ", false),
        'ಆ' to Pair("आ", true),
        'ಇ' to Pair("इ", false),
        'ಈ' to Pair("ई", true),
        'ಉ' to Pair("उ", false),
        'ಊ' to Pair("ऊ", true),
        'ಋ' to Pair("ऋ", false),
        'ೠ' to Pair("ॠ", true),
        'ಌ' to Pair("ऌ", false),
        'ೡ' to Pair("ॡ", true),
        'ಎ' to Pair("ऎ", false),
        'ಏ' to Pair("ए", true),
        'ಐ' to Pair("ऐ", true),
        'ಒ' to Pair("ऒ", false),
        'ಓ' to Pair("ओ", true),
        'ಔ' to Pair("औ", true)
    )

    private val consonants = mapOf(
        'ಕ' to "क", 'ಖ' to "ख", 'ಗ' to "ग", 'ಘ' to "घ", 'ಙ' to "ङ",
        'ಚ' to "च", 'ಛ' to "छ", 'ಜ' to "ज", 'ಝ' to "झ", 'ಞ' to "ञ",
        'ಟ' to "ट", 'ಠ' to "ठ", 'ಡ' to "ड", 'ಢ' to "ढ", 'ಣ' to "ण",
        'ತ' to "त", 'ಥ' to "थ", 'ದ' to "द", 'ಧ' to "ध", 'ನ' to "न",
        'ಪ' to "प", 'ಫ' to "फ", 'ಬ' to "ब", 'ಭ' to "भ", 'ಮ' to "म",
        'ಯ' to "य", 'ರ' to "र", 'ಱ' to "ऱ", 'ಲ' to "ल", 'ವ' to "व",
        'ಶ' to "श", 'ಷ' to "ष", 'ಸ' to "स", 'ಹ' to "ह", 'ಳ' to "ळ",
        'ೞ' to "ऴ"
    )

    private val matras = mapOf(
        'ಾ' to Pair("ा", true),
        'ಿ' to Pair("ि", false),
        'ೀ' to Pair("ी", true),
        'ು' to Pair("ु", false),
        'ೂ' to Pair("ू", true),
        'ೃ' to Pair("ृ", false),
        'ೄ' to Pair("ॄ", true),
        'ೆ' to Pair("ॆ", false),
        'ೇ' to Pair("े", true),
        'ೈ' to Pair("ै", true),
        'ೊ' to Pair("ॊ", false),
        'ೋ' to Pair("ो", true),
        'ೌ' to Pair("ौ", true)
    )

    private val modifiers = mapOf(
        'ಂ' to "ं",
        'ಃ' to "ः",
        'ಁ' to "ँ"
    )

    private val kannadaDigits = mapOf(
        '೦' to '0', '೧' to '1', '೨' to '2', '೩' to '3', '೪' to '4',
        '೫' to '5', '೬' to '6', '೭' to '7', '೮' to '8', '೯' to '9'
    )

    private const val KANNADA_VIRAMA = '್'
    private const val KANNADA_NUKTA = '಼'

    fun convertPlain(kannadaText: String): String =
        convertToSegments(kannadaText).joinToString(separator = "") { it.text }

    fun convertToSegments(kannadaText: String): List<HindiPhoneticSegment> {
        if (kannadaText.isEmpty()) return emptyList()

        val segments = mutableListOf<HindiPhoneticSegment>()
        val nonKannadaBuffer = StringBuilder()

        fun flushNonKannada() {
            if (nonKannadaBuffer.isNotEmpty()) {
                segments.add(HindiPhoneticSegment(nonKannadaBuffer.toString(), isDheergha = false))
                nonKannadaBuffer.clear()
            }
        }

        var i = 0
        val n = kannadaText.length
        while (i < n) {
            val ch = kannadaText[i]

            // 1. Consonant cluster: (C + virama)* + C + optional nukta + optional matra + optional modifier
            if (consonants.containsKey(ch)) {
                flushNonKannada()
                val cluster = StringBuilder()
                var isLong = false

                while (i < n && consonants.containsKey(kannadaText[i])) {
                    cluster.append(consonants[kannadaText[i]])
                    i++
                    if (i < n && kannadaText[i] == KANNADA_NUKTA) {
                        cluster.append("़")
                        i++
                    }
                    if (i < n && kannadaText[i] == KANNADA_VIRAMA) {
                        cluster.append("्")
                        i++
                        // If followed by another consonant, continue conjunct cluster
                        if (i < n && consonants.containsKey(kannadaText[i])) {
                            continue
                        } else {
                            // Trailing virama (halant) ends this syllable
                            break
                        }
                    } else {
                        break
                    }
                }

                // Optional vowel matra
                if (i < n && matras.containsKey(kannadaText[i])) {
                    val matra = matras[kannadaText[i]]!!
                    cluster.append(matra.first)
                    isLong = matra.second
                    i++
                }

                // Optional modifier (anusvara / visarga)
                while (i < n && modifiers.containsKey(kannadaText[i])) {
                    cluster.append(modifiers[kannadaText[i]])
                    i++
                }

                segments.add(HindiPhoneticSegment(cluster.toString(), isDheergha = isLong))
                continue
            }

            // 2. Independent vowel + optional modifier
            val vowel = independentVowels[ch]
            if (vowel != null) {
                flushNonKannada()
                val sb = StringBuilder(vowel.first)
                i++
                while (i < n && modifiers.containsKey(kannadaText[i])) {
                    sb.append(modifiers[kannadaText[i]])
                    i++
                }
                segments.add(HindiPhoneticSegment(sb.toString(), isDheergha = vowel.second))
                continue
            }

            // 3. Standalone modifier
            val mod = modifiers[ch]
            if (mod != null) {
                flushNonKannada()
                segments.add(HindiPhoneticSegment(mod, isDheergha = false))
                i++
                continue
            }

            // 4. Kannada digits or passthrough character
            val digit = kannadaDigits[ch]
            nonKannadaBuffer.append(digit ?: ch)
            i++
        }

        flushNonKannada()
        return segments
    }
}
