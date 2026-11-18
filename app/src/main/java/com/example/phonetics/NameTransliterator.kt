package com.example.phonetics

import java.util.Locale

/**
 * Phonetically transliterates English-script names and words into Kannada script.
 * Designed for live user-profile preview and pronunciation practice.
 */
object NameTransliterator {

    private val exactWordOverrides = mapOf(
        "kannada" to "ಕನ್ನಡ",
        "kannadaa" to "ಕನ್ನಡಾ",
        "benne" to "ಬೆಣ್ಣೆ",
        "bengaluru" to "ಬೆಂಗಳೂರು",
        "bangalore" to "ಬೆಂಗಳೂರು",
        "karnataka" to "ಕರ್ನಾಟಕ",
        "mysuru" to "ಮೈಸೂರು",
        "mysore" to "ಮೈಸೂರು",
        "namaskara" to "ನಮಸ್ಕಾರ",
        "namaskaara" to "ನಮಸ್ಕಾರ",
        "dosa" to "ದೋಸೆ",
        "masala" to "ಮಸಾಲೆ",
        "coffee" to "ಕಾಫಿ",
        "idli" to "ಇಡ್ಲಿ",
        "vada" to "ವಡೆ",
        "siddharth" to "ಸಿದ್ಧಾರ್ಥ್",
        "karthik" to "ಕಾರ್ತಿಕ್",
        "vishwa" to "ವಿಶ್ವ",
        "abhiram" to "ಅಭಿರಾಮ್"
    )

    private val initialVowels = listOf(
        "aa" to "ಆ",
        "ee" to "ಈ",
        "ii" to "ಈ",
        "oo" to "ಊ",
        "uu" to "ಊ",
        "ai" to "ಐ",
        "au" to "ಔ",
        "ou" to "ಔ",
        "ae" to "ಏ",
        "ei" to "ಏ",
        "a" to "ಅ",
        "i" to "ಇ",
        "u" to "ಉ",
        "e" to "ಎ",
        "o" to "ಒ"
    )

    private val vowelMatras = listOf(
        "aa" to "ಾ",
        "ee" to "ೀ",
        "ii" to "ೀ",
        "oo" to "ೂ",
        "uu" to "ೂ",
        "ai" to "ೈ",
        "au" to "ೌ",
        "ou" to "ೌ",
        "ae" to "ೇ",
        "ei" to "ೇ",
        "a" to "",
        "i" to "ಿ",
        "u" to "ು",
        "e" to "ೇ",
        "o" to "ೋ"
    )

    private val consonants = listOf(
        "ksh" to "ಕ್ಷ",
        "gny" to "ಜ್ಞ",
        "dny" to "ಜ್ಞ",
        "shh" to "ಷ",
        "chh" to "ಛ",
        "khh" to "ಖ",
        "thh" to "ಥ",
        "dhh" to "ಧ",
        "sh" to "ಶ",
        "ch" to "ಚ",
        "kh" to "ಖ",
        "gh" to "ಘ",
        "jh" to "ಝ",
        "th" to "ತ",
        "dh" to "ಧ",
        "bh" to "ಭ",
        "ph" to "ಫ",
        "ng" to "ಂಗ",
        "nj" to "ಞ",
        "ny" to "ನ್ಯ",
        "zh" to "ಳ",
        "k" to "ಕ",
        "c" to "ಕ",
        "q" to "ಕ",
        "g" to "ಗ",
        "j" to "ಜ",
        "t" to "ಟ",
        "d" to "ದ",
        "n" to "ನ",
        "p" to "ಪ",
        "f" to "ಫ",
        "b" to "ಬ",
        "m" to "ಮ",
        "y" to "ಯ",
        "r" to "ರ",
        "l" to "ಲ",
        "v" to "ವ",
        "w" to "ವ",
        "s" to "ಸ",
        "h" to "ಹ",
        "z" to "ಜ",
        "x" to "ಕ್ಸ"
    )

    fun toKannada(input: String): String {
        val trimmed = input.trim()
        if (trimmed.isEmpty()) return ""

        val result = StringBuilder()
        var idx = 0
        while (idx < trimmed.length) {
            val ch = trimmed[idx]
            if (ch.isLetter()) {
                val end = findWordEnd(trimmed, idx)
                val word = trimmed.substring(idx, end)
                result.append(transliterateWord(word))
                idx = end
            } else {
                result.append(ch)
                idx++
            }
        }
        return result.toString()
    }

    private fun findWordEnd(text: String, start: Int): Int {
        var i = start
        while (i < text.length && text[i].isLetter()) {
            i++
        }
        return i
    }

    private fun transliterateWord(rawWord: String): String {
        val word = rawWord.lowercase(Locale.US)
        exactWordOverrides[word]?.let { return it }

        val out = StringBuilder()
        var i = 0
        var atStart = true

        while (i < word.length) {
            if (atStart) {
                val vowelMatch = matchPrefix(word, i, initialVowels)
                if (vowelMatch != null) {
                    out.append(vowelMatch.second)
                    i += vowelMatch.first.length
                    atStart = false
                    continue
                }
            }

            // Contextual soft 'c' before e/i/y
            if (word[i] == 'c' && i + 1 < word.length && word[i + 1] in listOf('e', 'i', 'y') &&
                !word.startsWith("ch", i)
            ) {
                out.append("ಸ")
                i++
                val matra = matchPrefix(word, i, vowelMatras)
                if (matra != null) {
                    out.append(matra.second)
                    i += matra.first.length
                } else if (i < word.length) {
                    out.append("್")
                } else {
                    out.append("್")
                }
                atStart = false
                continue
            }

            val consMatch = matchPrefix(word, i, consonants)
            if (consMatch != null) {
                // Handle "ng" at end or before consonant smoothly as anusvara + g or simple cluster
                val token = consMatch.first
                val baseKannada = consMatch.second
                i += token.length
                atStart = false

                // If 'ng' followed by vowel, keep as ಂಗ + matra; at word end keep ಂಗ್
                if (token == "ng") {
                    val matra = matchPrefix(word, i, vowelMatras)
                    if (matra != null) {
                        out.append(baseKannada).append(matra.second)
                        i += matra.first.length
                    } else {
                        out.append("ಂಗ್")
                    }
                    continue
                }

                // Special handling for 'x' at word start -> ಎಕ್ಸ್, mid-word -> ಕ್ಸ್
                if (token == "x") {
                    val matra = matchPrefix(word, i, vowelMatras)
                    if (matra != null) {
                        out.append(baseKannada).append(matra.second)
                        i += matra.first.length
                    } else {
                        out.append("ಕ್ಸ್")
                    }
                    continue
                }

                // Check for a vowel following the consonant
                val matra = matchPrefix(word, i, vowelMatras)
                if (matra != null) {
                    out.append(baseKannada).append(matra.second)
                    i += matra.first.length
                } else {
                    // Consonant followed by another consonant or end of word -> virama (halant)
                    out.append(baseKannada).append("್")
                }
                continue
            }

            // Fallback vowel in middle of word (e.g. hiatal vowel like "Ria", "Diya")
            val midVowel = matchPrefix(word, i, initialVowels)
            if (midVowel != null) {
                out.append(midVowel.second)
                i += midVowel.first.length
                atStart = false
                continue
            }

            out.append(word[i])
            i++
            atStart = false
        }

        return out.toString()
    }

    private fun matchPrefix(
        word: String,
        start: Int,
        table: List<Pair<String, String>>
    ): Pair<String, String>? {
        for (entry in table) {
            if (word.startsWith(entry.first, start)) {
                return entry
            }
        }
        return null
    }
}
