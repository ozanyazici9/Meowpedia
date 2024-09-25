package com.ozanyazici.meowpedia.presentation

sealed class Screen(val route: String) {
    data object BreedScreen: Screen("breed_screen")
    data object BreedDetailScreen: Screen("breed_detail_screen",)
}