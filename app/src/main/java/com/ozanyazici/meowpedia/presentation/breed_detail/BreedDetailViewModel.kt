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

    //State oluşturup argümanlarla gönderdiğim değerleri alıp listeye atıcam listeyide state e

    init {
        savedStateHandle.get<String>(BREED_ID)?.let {
            getCatBreedImage(it)
        }
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