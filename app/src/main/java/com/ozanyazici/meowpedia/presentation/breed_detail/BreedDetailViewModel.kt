package com.ozanyazici.meowpedia.presentation.breed_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanyazici.meowpedia.domain.use_case.get_cat_breed_image.GetCatBreedImageUseCase
import com.ozanyazici.meowpedia.util.Constants.BREED_ID
import com.ozanyazici.meowpedia.util.Constants.BREED_NAME
import com.ozanyazici.meowpedia.util.Constants.COUNTRY_CODE
import com.ozanyazici.meowpedia.util.Constants.DESCRIPTION
import com.ozanyazici.meowpedia.util.Constants.LIFE_SPAN
import com.ozanyazici.meowpedia.util.Constants.ORIGIN
import com.ozanyazici.meowpedia.util.Constants.REFERENCE_IMAGE_ID
import com.ozanyazici.meowpedia.util.Constants.TEMPERAMENT
import com.ozanyazici.meowpedia.util.Constants.WIKIPEDIA_URL
import com.ozanyazici.meowpedia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val getCatBreedImageUseCase: GetCatBreedImageUseCase,
    savedStateHandle: SavedStateHandle
    ) : ViewModel()  {

    private val _breedImageState = mutableStateOf(BreedDetailImageState())
    val breedImageState: State<BreedDetailImageState> = _breedImageState

    private val _breedArgumentState = mutableStateOf(BreedDetailArgumentState())
    val breedArgumentState: State<BreedDetailArgumentState> = _breedArgumentState

    init {
        savedStateHandle.get<String>(BREED_ID)?.let {
            getCatBreedImage(it)
        }

        val breedName: String? = savedStateHandle.get<String>(BREED_NAME)
        val description: String? = savedStateHandle.get<String>(DESCRIPTION)
        val temperament: String? = savedStateHandle.get<String>(TEMPERAMENT)
        val origin: String? = savedStateHandle.get<String>(ORIGIN)
        val countryCode: String? = savedStateHandle.get<String>(COUNTRY_CODE)
        val lifeSpan: String? = savedStateHandle.get<String>(LIFE_SPAN)
        val referenceImageId: String? = savedStateHandle.get<String>(REFERENCE_IMAGE_ID)
        val encodedWikipediaUrl: String? = savedStateHandle.get<String>(WIKIPEDIA_URL)
        val decodedWikipediaUrl = URLDecoder.decode(encodedWikipediaUrl, "UTF-8")

        _breedArgumentState.value = _breedArgumentState.value.copy(
            breedName = breedName!!,
            breedDescription = description!!,
            breedTemperament = temperament!!,
            breedOrigin = origin!!,
            breedCountryCode = countryCode!!,
            breedLifeSpan = lifeSpan!!,
            breedReferenceImageId = referenceImageId,
            breedWikipediaUrl = decodedWikipediaUrl
        )
    }

    private fun getCatBreedImage(breedId: String) {
        getCatBreedImageUseCase.executeGetCatBreedImage(breedId = breedId).onEach {
            _breedImageState.value = _breedImageState.value.copy(isLoading = true)
            when(it) {
                is Resource.Success -> {
                    _breedImageState.value = _breedImageState.value.copy(breedImage = it.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _breedImageState.value = _breedImageState.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _breedImageState.value = _breedImageState.value.copy(error = it.message ?: "Error!")
                }
            }
        }.launchIn(viewModelScope)
    }
}