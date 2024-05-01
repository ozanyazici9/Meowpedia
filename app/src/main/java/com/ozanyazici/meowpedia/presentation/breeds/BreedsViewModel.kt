package com.ozanyazici.meowpedia.presentation.breeds

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
class BreedsViewModel @Inject constructor(private val getBreedsUseCase: GetBreedsUseCase): ViewModel() {

    private val _state = mutableStateOf(BreedsState())
    val state: State<BreedsState> = _state

    private var allList: List<Breed>? = emptyList()

    private var job: Job? = null

    init {
        getBreeds()
    }

    private fun getBreeds() {
        job?.cancel()

        job = getBreedsUseCase.executeGetBreeds().onEach {
            when(it) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(breeds = it.data ?: emptyList(), isLoading = false)
                    allList = it.data
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

    fun onEvent(event: BreedsEvent) {
        when(event) {
            is BreedsEvent.Search -> {
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
                _state.value = _state.value.copy(breeds = filteredList, isLoading = false)
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




















