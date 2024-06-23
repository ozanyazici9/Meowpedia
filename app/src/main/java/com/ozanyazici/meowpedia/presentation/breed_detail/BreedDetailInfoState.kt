package com.ozanyazici.meowpedia.presentation.breed_detail

data class BreedDetailInfoState(
    val breedId: String = "",
    val breedName: String = "",
    val breedTemperament: String = "",
    val breedOrigin: String = "",
    val breedCountryCode: String = "",
    val breedDescription: String = "",
    val breedLifeSpan: String = "",
    val breedReferenceImageId: String? = ""
)
