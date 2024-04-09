package com.ozanyazici.meowpedia.data.remote.dto

import com.ozanyazici.meowpedia.domain.model.Breed

class BreedsDto : ArrayList<BreedsDtoItem>()

fun BreedsDto.toBreedList(): List<Breed> {
    return this.map { breedsDtoItem ->
        Breed(breedsDtoItem.id,breedsDtoItem.name,breedsDtoItem.temperament,breedsDtoItem.origin,breedsDtoItem.country_code,breedsDtoItem.description,breedsDtoItem.life_span,breedsDtoItem.reference_image_id)
    }
}