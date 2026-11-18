package com.example.data

/**
 * Karnataka food and heritage catalogue, taken from the Benne implementation specifications.
 *
 * Nothing here is verified yet. `reviewed = false` on every item: no photo is bundled, and no
 * historical text, Kannada spelling beyond the given names, or Devanagari pronunciation is shown
 * as checked. Fields left blank mean "pending review", never a guess.
 */
data class FoodItem(
    val id: String,
    val englishName: String,
    val kannadaName: String = "",
    val category: FoodCategory,
    val region: String = "",
    val reviewed: Boolean = false
)

enum class FoodCategory(val label: String) {
    BREAKFAST("Breakfast"),
    MEALS("Meals"),
    SNACKS("Snacks"),
    COASTAL("Coastal"),
    NORTH_KARNATAKA("North Karnataka"),
    MALNAD_KODAGU("Malnad & Kodagu"),
    SWEETS("Sweets")
}

data class HeritagePlace(
    val id: String,
    val englishName: String,
    val kannadaName: String = "",
    val district: String,
    val focus: String,
    val priority: String,
    val reviewed: Boolean = false
)

object KarnatakaCatalog {

    val foods: List<FoodItem> = listOf(
        // Breakfast and tiffin
        FoodItem("bisi_bele_bath", "Bisi Bele Bath", "ಬಿಸಿ ಬೇಳೆ ಬಾತ್", FoodCategory.BREAKFAST),
        FoodItem("mysore_masala_dosa", "Mysore Masala Dosa", category = FoodCategory.BREAKFAST, region = "Mysuru"),
        FoodItem("thatte_idli", "Thatte Idli", "ತಟ್ಟೆ ಇಡ್ಲಿ", FoodCategory.BREAKFAST),
        FoodItem("rava_idli", "Rava Idli", category = FoodCategory.BREAKFAST),
        FoodItem("khali_dosa", "Khali Dosa", category = FoodCategory.BREAKFAST),
        FoodItem("davangere_benne_dose", "Davangere Benne Dose", "ಬೆಣ್ಣೆ ದೋಸೆ", FoodCategory.BREAKFAST, region = "Davangere"),
        FoodItem("neer_dosa", "Neer Dosa", "ನೀರು ದೋಸೆ", FoodCategory.BREAKFAST),
        FoodItem("akki_rotti", "Akki Rotti", "ಅಕ್ಕಿ ರೊಟ್ಟಿ", FoodCategory.BREAKFAST),
        FoodItem("ragi_rotti", "Ragi Rotti", category = FoodCategory.BREAKFAST),
        FoodItem("shavige_bath", "Shavige Bath", category = FoodCategory.BREAKFAST),

        // Rice, meals and accompaniments
        FoodItem("chitranna", "Chitranna", "ಚಿತ್ರಾನ್ನ", FoodCategory.MEALS),
        FoodItem("puliyogare", "Puliyogare", category = FoodCategory.MEALS),
        FoodItem("vangi_bath", "Vangi Bath", "ವಾಂಗಿ ಬಾತ್", FoodCategory.MEALS),
        FoodItem("tomato_bath", "Tomato Bath", category = FoodCategory.MEALS),
        FoodItem("curd_rice", "Curd Rice", category = FoodCategory.MEALS),
        FoodItem("jolada_rotti_oota", "Jolada Rotti Oota", category = FoodCategory.MEALS),
        FoodItem("ragi_mudde", "Ragi Mudde", category = FoodCategory.MEALS),
        FoodItem("bassaru", "Bassaru", category = FoodCategory.MEALS),
        FoodItem("saaru", "Saaru", category = FoodCategory.MEALS),
        FoodItem("majjige_huli", "Majjige Huli", "ಮಜ್ಜಿಗೆ ಹುಳಿ", FoodCategory.MEALS),

        // North Karnataka
        FoodItem("jolada_rotti", "Jolada Rotti", category = FoodCategory.NORTH_KARNATAKA),
        FoodItem("ennegayi", "Ennegayi", category = FoodCategory.NORTH_KARNATAKA),
        FoodItem("shenga_chutney", "Shenga Chutney", category = FoodCategory.NORTH_KARNATAKA),
        FoodItem("girmit", "Girmit", category = FoodCategory.NORTH_KARNATAKA),
        FoodItem("mirchi_bajji", "Mirchi Bajji", category = FoodCategory.NORTH_KARNATAKA),
        FoodItem("mandakki_oggarane", "Mandakki Oggarane", category = FoodCategory.NORTH_KARNATAKA),

        // Coastal Karnataka
        FoodItem("mangalore_buns", "Mangalore Buns", category = FoodCategory.COASTAL),
        FoodItem("goli_baje", "Goli Baje", category = FoodCategory.COASTAL),
        FoodItem("kori_rotti", "Kori Rotti", category = FoodCategory.COASTAL),
        FoodItem("chicken_sukka", "Chicken Sukka", category = FoodCategory.COASTAL),
        FoodItem("kotte_kadubu", "Kotte Kadubu", category = FoodCategory.COASTAL),
        FoodItem("patrode", "Patrode", "ಪತ್ರೊಡೆ", FoodCategory.COASTAL),
        FoodItem("pundi", "Pundi", category = FoodCategory.COASTAL),
        FoodItem("fish_curry", "Fish Curry", category = FoodCategory.COASTAL),

        // Malnad and Kodagu
        FoodItem("akki_kadubu", "Akki Kadubu", category = FoodCategory.MALNAD_KODAGU),
        FoodItem("kadambuttu", "Kadambuttu", category = FoodCategory.MALNAD_KODAGU),
        FoodItem("noolputtu", "Noolputtu", category = FoodCategory.MALNAD_KODAGU),
        FoodItem("pandi_curry", "Pandi Curry", category = FoodCategory.MALNAD_KODAGU),
        FoodItem("bamboo_shoot_curry", "Bamboo Shoot Curry", category = FoodCategory.MALNAD_KODAGU),
        FoodItem("tambuli", "Tambuli", category = FoodCategory.MALNAD_KODAGU),

        // Snacks and street food
        FoodItem("maddur_vade", "Maddur Vade", "ಮದ್ದೂರು ವಡೆ", FoodCategory.SNACKS, region = "Maddur"),
        FoodItem("nippattu", "Nippattu", category = FoodCategory.SNACKS),
        FoodItem("kodubale", "Kodubale", category = FoodCategory.SNACKS),
        FoodItem("congress_kadlekai", "Congress Kadlekai", category = FoodCategory.SNACKS),
        FoodItem("churumuri", "Churumuri", category = FoodCategory.SNACKS),
        FoodItem("masala_puri", "Masala Puri", "ಮಸಾಲೆ ಪುರಿ", FoodCategory.SNACKS),
        FoodItem("dharwad_mirchi", "Dharwad-style Mirchi", category = FoodCategory.SNACKS, region = "Dharwad"),

        // Sweets
        FoodItem("mysore_pak", "Mysore Pak", "ಮೈಸೂರು ಪಾಕ್", FoodCategory.SWEETS, region = "Mysuru"),
        FoodItem("dharwad_peda", "Dharwad Peda", "ಧಾರವಾಡ ಪೇಡಾ", FoodCategory.SWEETS, region = "Dharwad"),
        FoodItem("belagavi_kunda", "Belagavi Kunda", category = FoodCategory.SWEETS, region = "Belagavi"),
        FoodItem("karadantu", "Karadantu", category = FoodCategory.SWEETS),
        FoodItem("chiroti", "Chiroti", "ಚಿರೋಟಿ", FoodCategory.SWEETS),
        FoodItem("obbattu_holige", "Obbattu / Holige", "ಒಬ್ಬಟ್ಟು / ಹೋಳಿಗೆ", FoodCategory.SWEETS),
        FoodItem("hayagreeva", "Hayagreeva", category = FoodCategory.SWEETS),
        FoodItem("kesari_bath", "Kesari Bath", "ಕೇಸರಿ ಬಾತ್", FoodCategory.SWEETS),
        FoodItem("rava_unde", "Rava Unde", category = FoodCategory.SWEETS),
        FoodItem("shavige_payasa", "Shavige Payasa", category = FoodCategory.SWEETS),
        FoodItem("gasagase_payasa", "Gasagase Payasa", category = FoodCategory.SWEETS),
        FoodItem("tambittu", "Tambittu", category = FoodCategory.SWEETS),
        FoodItem("kajjaya", "Kajjaya", category = FoodCategory.SWEETS),
        FoodItem("ellu_bella", "Ellu Bella", category = FoodCategory.SWEETS),
        FoodItem("ginnu", "Ginnu", "ಗಿಣ್ಣು", FoodCategory.SWEETS)
    )

    /** Heritage destinations from the editorial queue. Facts and photos are pending review. */
    val heritage: List<HeritagePlace> = listOf(
        HeritagePlace("hampi", "Hampi Group of Monuments", district = "Vijayanagara", focus = "Vijayanagara Empire; UNESCO World Heritage", priority = "P0"),
        HeritagePlace("virupaksha", "Virupaksha Temple, Hampi", district = "Vijayanagara", focus = "Temple heritage; part of the Hampi complex", priority = "P0"),
        HeritagePlace("vittala", "Vittala Temple and Stone Chariot", district = "Vijayanagara", focus = "Vijayanagara architecture; Hampi complex", priority = "P0"),
        HeritagePlace("lotus_mahal", "Lotus Mahal", district = "Vijayanagara", focus = "Hampi court architecture", priority = "P1"),
        HeritagePlace("elephant_stables", "Elephant Stables, Hampi", district = "Vijayanagara", focus = "Royal enclosure architecture", priority = "P1"),
        HeritagePlace("mysore_palace", "Mysore Palace", district = "Mysuru", focus = "Wadiyar dynasty and palace architecture", priority = "P0"),
        HeritagePlace("somanathapura", "Keshava Temple, Somanathapura", district = "Mysuru", focus = "Hoysala temple; UNESCO serial property", priority = "P0"),
        HeritagePlace("srirangapatna", "Srirangapatna historic town", district = "Mandya", focus = "Mysore Kingdom; historic fortifications", priority = "P0"),
        HeritagePlace("daria_daulat", "Daria Daulat Bagh", district = "Mandya", focus = "Tipu Sultan's summer palace", priority = "P1"),
        HeritagePlace("belur", "Chennakeshava Temple, Belur", district = "Hassan", focus = "Hoysala architecture; UNESCO serial property", priority = "P0"),
        HeritagePlace("halebidu", "Hoysaleswara Temple, Halebidu", district = "Hassan", focus = "Hoysala architecture; UNESCO serial property", priority = "P0"),
        HeritagePlace("shravanabelagola", "Shravanabelagola", district = "Hassan", focus = "Jain heritage and Gomateshwara", priority = "P0"),
        HeritagePlace("badami", "Badami Cave Temples", district = "Bagalkot", focus = "Early Chalukya rock-cut architecture", priority = "P0"),
        HeritagePlace("aihole", "Aihole temple complex", district = "Bagalkot", focus = "Early Chalukya temple experimentation", priority = "P0"),
        HeritagePlace("pattadakal", "Group of Monuments at Pattadakal", district = "Bagalkot", focus = "Chalukya temples; UNESCO World Heritage", priority = "P0"),
        HeritagePlace("gol_gumbaz", "Gol Gumbaz", district = "Vijayapura", focus = "Adil Shahi funerary architecture", priority = "P0"),
        HeritagePlace("ibrahim_rauza", "Ibrahim Rauza", district = "Vijayapura", focus = "Adil Shahi architecture", priority = "P1"),
        HeritagePlace("bara_kaman", "Bara Kaman", district = "Vijayapura", focus = "Unfinished Adil Shahi mausoleum", priority = "P2"),
        HeritagePlace("chitradurga", "Chitradurga Fort", district = "Chitradurga", focus = "Fortification and regional history", priority = "P0"),
        HeritagePlace("bidar_fort", "Bidar Fort", district = "Bidar", focus = "Bahmani / Barid Shahi history", priority = "P0"),
        HeritagePlace("mahmud_gawan", "Mahmud Gawan Madrasa", district = "Bidar", focus = "Bahmani-era education and architecture", priority = "P1"),
        HeritagePlace("bengaluru_palace", "Bengaluru Palace", district = "Bengaluru Urban", focus = "Modern-era royal residence", priority = "P1"),
        HeritagePlace("bengaluru_fort", "Bengaluru Fort", district = "Bengaluru Urban", focus = "Fort history and urban development", priority = "P1"),
        HeritagePlace("tipu_bengaluru", "Tipu Sultan's Summer Palace, Bengaluru", district = "Bengaluru Urban", focus = "Mysore-era palace architecture", priority = "P1"),
        HeritagePlace("keladi", "Rameshwara Temple, Keladi", district = "Shivamogga", focus = "Keladi Nayaka history", priority = "P1"),
        HeritagePlace("ikkeri", "Aghoreshwara Temple, Ikkeri", district = "Shivamogga", focus = "Keladi Nayaka architecture", priority = "P1"),
        HeritagePlace("mirjan_fort", "Mirjan Fort", district = "Uttara Kannada", focus = "Coastal fortification", priority = "P1"),
        HeritagePlace("st_aloysius", "St. Aloysius Chapel", district = "Dakshina Kannada", focus = "Historic chapel and paintings", priority = "P2"),
        HeritagePlace("moodabidri", "Thousand Pillar Jain Temple, Moodabidri", district = "Dakshina Kannada", focus = "Jain architecture", priority = "P1"),
        HeritagePlace("banavasi", "Madhukeshwara Temple, Banavasi", district = "Uttara Kannada", focus = "Early Karnataka history", priority = "P1"),
        HeritagePlace("lakshmeshwar", "Someshwara Temple, Lakshmeshwar", district = "Gadag", focus = "Historic temple architecture", priority = "P2"),
        HeritagePlace("lakkundi", "Lakkundi heritage temples and stepwells", district = "Gadag", focus = "Western Chalukya architecture", priority = "P1"),
        HeritagePlace("gadag", "Trikuteshwara Temple, Gadag", district = "Gadag", focus = "Temple architecture", priority = "P2"),
        HeritagePlace("melukote", "Melukote heritage town", district = "Mandya", focus = "Vaishnava heritage and temple traditions", priority = "P1"),
        HeritagePlace("kittur", "Kittur Fort and heritage sites", district = "Belagavi", focus = "Kittur Rani Chennamma and regional history", priority = "P1")
    )
}
