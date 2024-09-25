package com.ozanyazici.meowpedia.data.remote

import com.ozanyazici.meowpedia.data.remote.dto.BreedsDto
import com.ozanyazici.meowpedia.data.remote.dto.CatBreedImageDto
import com.ozanyazici.meowpedia.util.Constants.IMAGE_LIMIT
import retrofit2.http.GET
import retrofit2.http.Query

interface CatAPI {

    //https://api.thecatapi.com/v1/breeds
    //https://api.thecatapi.com/v1/images/search?limit=10&breed_ids=beng&api_key=REPLACE_ME
    //https://api.thecatapi.com/v1/images/search?limit=10&breed_ids=beng
    //https://api.thecatapi.com/v1/breeds/beng*ids

    @GET("v1/breeds")
    suspend fun getBreeds(): BreedsDto

    @GET("v1/images/search")
    suspend fun getCatBreedImage(
        @Query("limit") limit: String = IMAGE_LIMIT,
        @Query("breed_ids") breedId: String,
    ): CatBreedImageDto
}