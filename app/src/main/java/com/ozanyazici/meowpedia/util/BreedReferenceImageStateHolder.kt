package com.ozanyazici.meowpedia.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import coil.compose.rememberAsyncImagePainter
import com.ozanyazici.meowpedia.R
import com.ozanyazici.meowpedia.util.Constants.BASE_URL_REFERENCE_IMAGE

class BreedReferenceImageStateHolder {
    var referenceImage by mutableStateOf<Painter?>(null)
        private set

    @Composable
    fun UpdateReferenceImage(breedName: String, breedReferenceImageId: String?) {
        referenceImage = when (breedName) {
            "European Burmese" -> {
                painterResource(R.drawable.european_burmese_cat)
            }
            "Malayan" -> {
                painterResource(R.drawable.malayan_cat)
            }
            in listOf("Bengal", "Devon Rex", "Korat") -> {
                rememberAsyncImagePainter(model = "$BASE_URL_REFERENCE_IMAGE$breedReferenceImageId.png")
            }
            else -> {
                rememberAsyncImagePainter(model = "$BASE_URL_REFERENCE_IMAGE$breedReferenceImageId.jpg")
            }
        }
    }
}

