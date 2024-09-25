package com.ozanyazici.meowpedia.domain.model

data class Breed(
    val id: String,
    val name: String,
    val temperament: String,
    val origin: String,
    val country_code: String,
    val description: String,
    val life_span: String,
    val reference_image_id: String?,
    val wikipediaUrl: String?
)
