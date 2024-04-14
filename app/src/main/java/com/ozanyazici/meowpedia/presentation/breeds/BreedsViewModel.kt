package com.ozanyazici.meowpedia.presentation.breeds

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanyazici.meowpedia.domain.use_case.get_breeds.GetBreedsUseCase
import com.ozanyazici.meowpedia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class BreedsViewModel @Inject constructor(private val getBreedsUseCase: GetBreedsUseCase): ViewModel() {

    private val _state = mutableStateOf<BreedsState>(BreedsState())
    val state: State<BreedsState> = _state

    private var job: Job? = null

    init {
        getBreeds()
    }

    private fun getBreeds() {
        job?.cancel()

        job = getBreedsUseCase.executeGetBreeds().onEach {
            when(it) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(breeds = it.data ?: emptyList())
                }
                is Resource.Error -> {
                    _state.value = _state.value.copy(error = it.message ?: "Error!")
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}