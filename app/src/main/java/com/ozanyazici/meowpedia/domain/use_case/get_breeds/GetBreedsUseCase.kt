package com.ozanyazici.meowpedia.domain.use_case.get_breeds

import com.ozanyazici.meowpedia.data.remote.dto.toBreedList
import com.ozanyazici.meowpedia.domain.model.Breed
import com.ozanyazici.meowpedia.domain.repository.CatRepository
import com.ozanyazici.meowpedia.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(private val repository: CatRepository) {

    fun executeGetBreeds(): Flow<Resource<List<Breed>>> = flow {
        try {
            emit(Resource.Loading())
            val breedList = repository.getBreeds()
            if (breedList.isNotEmpty()) {
                emit(Resource.Success(breedList.toBreedList()))
            } else if (breedList.isEmpty()) {
                emit(Resource.Error(message = "No breed found!"))
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
