package com.ozanyazici.meowpedia.data.repository

import com.ozanyazici.meowpedia.data.remote.CatAPI
import com.ozanyazici.meowpedia.data.remote.dto.BreedsDto
import com.ozanyazici.meowpedia.data.remote.dto.CatBreedImageDto
import com.ozanyazici.meowpedia.domain.repository.CatRepository
import javax.inject.Inject

class CatRepositoryImpl @Inject constructor(private val api: CatAPI) : CatRepository {

    override suspend fun getBreeds(): BreedsDto {
        return api.getBreeds()
    }

    override suspend fun getCatBreedImage(breedId: String): CatBreedImageDto {
        return api.getCatBreedImage(breedId = breedId)
    }
}