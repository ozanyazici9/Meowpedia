package com.ozanyazici.meowpedia.presentation.breeds

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozanyazici.meowpedia.domain.model.Breed
import com.ozanyazici.meowpedia.domain.use_case.get_breeds.GetBreedsUseCase
import com.ozanyazici.meowpedia.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BreedViewModel @Inject constructor(
    private val getBreedsUseCase: GetBreedsUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _breedState = mutableStateOf(BreedState())
    val breedState: State<BreedState> = _breedState

    private var allList: List<Breed>? = emptyList()
    private var job: Job? = null

    var searchText: String
        get() = savedStateHandle.get<String>("searchText") ?: ""
        set(value) {
            savedStateHandle.set("searchText", value)
        }

    init {
        getBreeds()
    }

    private fun getBreeds() {
        job?.cancel()
        job = getBreedsUseCase.executeGetBreeds().onEach {
            when(it) {
                is Resource.Success -> {
                    _breedState.value = _breedState.value.copy(breeds = it.data ?: emptyList(), isLoading = false)
                    allList = it.data
                }
                is Resource.Loading -> {
                    _breedState.value = _breedState.value.copy(isLoading = true)
                }
                is Resource.Error -> {
                    _breedState.value = _breedState.value.copy(isLoading = false, error = "Error")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: BreedsEvent) {
        when(event) {
            is BreedsEvent.Search -> {
                searchText = event.searchString
                val searchString = event.searchString.lowercase(Locale.getDefault())
                val filteredList = if (searchString.isEmpty()) {
                    allList ?: emptyList()
                } else {
                    allList?.filter { breed ->
                        searchString.split("\\s".toRegex()).all { word ->
                            breed.name.lowercase(Locale.getDefault()).contains(word)
                        }
                    } ?: emptyList()
                }
                _breedState.value = _breedState.value.copy(breeds = filteredList, isLoading = false)
            }
        }
    }
}
/* Küçük büyük harf duyarsızlığı contains metodunun ignoreCase metodu ile de sağlanabilir.
   Bu durmda split, toRegex, all gibi metodların kullanımına gerek kalmaz.

is BreedsEvent.Search -> {
    val searchString = event.searchString
    val filteredList = if (searchString.isEmpty()) {
        // Eğer arama metni boşsa, tüm listeyi göster
        allList ?: emptyList()
    } else {
        // Değilse, arama metni breed.name içinde geçiyorsa filtrele
        allList?.filter { breed ->
            breed.name.contains(searchString, ignoreCase = true)
        } ?: emptyList()
    }
    // Filtrelenmiş listeyi güncelle
    _state.value = _state.value.copy(breeds = filteredList, isLoading = false)
}
 */