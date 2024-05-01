package com.ozanyazici.meowpedia.presentation.breeds

sealed class BreedsEvent {
    data class Search(val searchString: String): BreedsEvent()
}