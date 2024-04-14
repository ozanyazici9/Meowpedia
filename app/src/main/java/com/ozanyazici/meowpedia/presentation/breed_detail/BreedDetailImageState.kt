package com.ozanyazici.meowpedia.presentation.breed_detail

import com.ozanyazici.meowpedia.domain.model.CatBreedImage

data class BreedDetailImageState(
    val isLoading: Boolean = false,
    val error: String = "",
    val breedImage: List<CatBreedImage> = emptyList()
)