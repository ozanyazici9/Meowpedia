package com.ozanyazici.meowpedia.domain.repository

import com.ozanyazici.meowpedia.data.remote.dto.BreedsDto
import com.ozanyazici.meowpedia.data.remote.dto.CatBreedImageDto
import com.ozanyazici.meowpedia.domain.model.CatBreedImage

interface CatRepository {

    suspend fun getBreeds(): BreedsDto

    suspend fun getCatBreedImage(breedId: String): CatBreedImageDto
}