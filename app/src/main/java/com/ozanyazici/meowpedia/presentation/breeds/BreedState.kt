package com.ozanyazici.meowpedia.presentation.breeds

import com.ozanyazici.meowpedia.domain.model.Breed

data class BreedState(
    val isLoading: Boolean = false,
    val breeds: List<Breed> = emptyList(),
    val error: String = "",
)
