package com.ozanyazici.meowpedia.presentation.breed_detail

data class BreedDetailArgumentState(
    val breedId: String = "",
    val breedName: String = "",
    val breedDescription: String = "",
    val breedTemperament: String = "",
    val breedOrigin: String = "",
    val breedCountryCode: String = "",
    val breedLifeSpan: String = "",
    val breedReferenceImageId: String? = "",
    val breedWikipediaUrl: String = ""
)
