package com.ozanyazici.meowpedia.presentation.breed_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanyazici.meowpedia.domain.model.CatBreedImage
import com.ozanyazici.meowpedia.domain.use_case.get_cat_breed_image.GetCatBreedImageUseCase
import com.ozanyazici.meowpedia.presentation.breeds.BreedsViewModel
import com.ozanyazici.meowpedia.util.Constants.BREED_ID
import com.ozanyazici.meowpedia.util.Constants.BREED_NAME
import com.ozanyazici.meowpedia.util.Constants.COUNTRY_CODE
import com.ozanyazici.meowpedia.util.Constants.DESCRIPTION
import com.ozanyazici.meowpedia.util.Constants.LIFE_SPAN
import com.ozanyazici.meowpedia.util.Constants.ORIGIN
import com.ozanyazici.meowpedia.util.Constants.REFERENCE_IMAGE_ID
import com.ozanyazici.meowpedia.util.Constants.TEMPERAMENT
import com.ozanyazici.meowpedia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BreedDetailViewModel @Inject constructor(
    private val getCatBreedImageUseCase: GetCatBreedImageUseCase,
    private val savedStateHandle: SavedStateHandle
    ) : ViewModel()  {

    private val _state = mutableStateOf<BreedDetailImageState>(BreedDetailImageState())
    val state: State<BreedDetailImageState> = _state

    private val _stateInfo = mutableStateOf<BreedDetailInfoState>(BreedDetailInfoState())
    val stateInfo: State<BreedDetailInfoState> = _stateInfo

    //State oluşturup argümanlarla gönderdiğim değerleri alıp listeye atıcam listeyide state e

    init {
        val breedName: String? = savedStateHandle.get<String>(BREED_NAME)
        val origin: String? = savedStateHandle.get<String>(ORIGIN)
        val countryCode: String? = savedStateHandle.get<String>(COUNTRY_CODE)
        val description: String? = savedStateHandle.get<String>(DESCRIPTION)
        val lifeSpan: String? = savedStateHandle.get<String>(LIFE_SPAN)
        val temperament: String? = savedStateHandle.get<String>(TEMPERAMENT)



        val referenceImageId: String? = savedStateHandle.get<String>(REFERENCE_IMAGE_ID)
        savedStateHandle.get<String>(BREED_ID)?.let {
            getCatBreedImage(it)
        }

        _stateInfo.value = _stateInfo.value.copy(breedName = breedName!!)
        _stateInfo.value = _stateInfo.value.copy(breedOrigin = origin!!)
        _stateInfo.value = _stateInfo.value.copy(breedCountryCode = countryCode!!)
        _stateInfo.value = _stateInfo.value.copy(breedDescription = description!!)
        _stateInfo.value = _stateInfo.value.copy(breedLifeSpan = lifeSpan!!)
        _stateInfo.value = _stateInfo.value.copy(breedReferenceImageId = referenceImageId!!)
        _stateInfo.value = _stateInfo.value.copy(breedTemperament = temperament!!)
    }

    private fun getCatBreedImage(breedId: String) {
        getCatBreedImageUseCase.executeGetCatBreedImage(breedId = breedId).onEach {
            _state.value = _state.value.copy(isLoading = true)
            when(it) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(breedImage = it.data ?: emptyList())
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "Error!")
                }
            }
        }.launchIn(viewModelScope)
    }
}