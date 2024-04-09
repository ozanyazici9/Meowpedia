package com.ozanyazici.meowpedia.domain.use_case.get_breeds

import com.ozanyazici.meowpedia.domain.model.Breed
import com.ozanyazici.meowpedia.domain.repository.CatRepository
import com.ozanyazici.meowpedia.util.Resource
import kotlinx.coroutines.flow.flow
import java.util.concurrent.Flow
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(private val repository: CatRepository) {


}