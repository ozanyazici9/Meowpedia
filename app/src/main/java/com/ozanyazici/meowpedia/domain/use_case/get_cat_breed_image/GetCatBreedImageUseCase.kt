package com.ozanyazici.meowpedia.domain.use_case.get_cat_breed_image

import com.ozanyazici.meowpedia.data.remote.dto.toCatBreedImageList
import com.ozanyazici.meowpedia.domain.model.CatBreedImage
import com.ozanyazici.meowpedia.domain.repository.CatRepository
import com.ozanyazici.meowpedia.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject
class GetCatBreedImageUseCase @Inject constructor(private val repository: CatRepository) {

    fun executeGetCatBreedImage(breedId: String): Flow<Resource<List<CatBreedImage>>> = flow {
        try {
            emit(Resource.Loading())
            val catBreedImageList =  repository.getCatBreedImage(breedId)
            if (catBreedImageList.isNotEmpty()) {
                emit(Resource.Success(catBreedImageList.toCatBreedImageList()))
            } else if (catBreedImageList.isEmpty()) {
                emit(Resource.Error(message = "No image found!"))
            } else {
                emit(Resource.Loading())
            }
        } catch (e: IOError) {
            emit(Resource.Error(message = "Network error"))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.localizedMessage ?: "Http Error"))
        }
    }
}