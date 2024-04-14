package com.ozanyazici.meowpedia.presentation.breeds

import com.ozanyazici.meowpedia.domain.model.Breed
import com.ozanyazici.meowpedia.util.Resource

data class BreedsState (
    val isLoading: Boolean = false,
    val breeds: List<Breed> = emptyList(),
    val error: String = "",
)
