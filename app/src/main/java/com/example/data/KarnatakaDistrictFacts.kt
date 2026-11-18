package com.example.data

/**
 * District-level facts for the Karnataka map, built only from the project's own catalogues
 * (KarnatakaCatalog). Nothing is added from memory.
 *
 * - Heritage places are mapped to the geometry's district names. Vijayanagara district (created 2021)
 *   is not in the 2011 boundary file, so Hampi is shown under Ballari; this mapping is pending review.
 * - Foods are listed only where the catalogue gives an explicit region.
 * - Important cities and Kannada district names are NOT included: they need editorial and native review.
 */
object KarnatakaDistrictFacts {

    /** Heritage catalogue district name -> map (geometry) district name. */
    private val heritageDistrictToMap = mapOf(
        "Vijayanagara" to "Ballari",
        "Bagalkot" to "Bagalkote",
        "Bengaluru Urban" to "Bengaluru",
        "Mysuru" to "Mysuru",
        "Mandya" to "Mandya",
        "Hassan" to "Hassan",
        "Vijayapura" to "Vijayapura",
        "Chitradurga" to "Chitradurga",
        "Bidar" to "Bidar",
        "Shivamogga" to "Shivamogga",
        "Uttara Kannada" to "Uttara Kannada",
        "Dakshina Kannada" to "Dakshina Kannada",
        "Gadag" to "Gadag",
        "Belagavi" to "Belagavi"
    )

    /** Food region text from the catalogue -> map district. */
    private val foodRegionToMap = mapOf(
        "Mysuru" to "Mysuru",
        "Dharwad" to "Dharwad",
        "Maddur" to "Mandya",
        "Davangere" to "Davanagere",
        "Belagavi" to "Belagavi"
    )

    fun heritageFor(mapDistrict: String): List<HeritagePlace> =
        KarnatakaCatalog.heritage.filter { heritageDistrictToMap[it.district] == mapDistrict }

    fun foodsFor(mapDistrict: String): List<FoodItem> =
        KarnatakaCatalog.foods.filter { foodRegionToMap[it.region] == mapDistrict }
}
