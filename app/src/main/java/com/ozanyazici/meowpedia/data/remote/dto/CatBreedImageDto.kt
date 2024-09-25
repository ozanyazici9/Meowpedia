package com.ozanyazici.meowpedia.data.remote.dto

import com.ozanyazici.meowpedia.domain.model.CatBreedImage

class CatBreedImageDto : ArrayList<CatBreedImageDtoItem>()

fun CatBreedImageDto.toCatBreedImageList(): List<CatBreedImage> {
    return this.map { catBreedImageDtoItem -> CatBreedImage(catBreedImageDtoItem.url, catBreedImageDtoItem.width, catBreedImageDtoItem.height) }
}