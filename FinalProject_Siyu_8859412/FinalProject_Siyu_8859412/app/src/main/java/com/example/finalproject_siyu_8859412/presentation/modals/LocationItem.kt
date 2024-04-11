package com.example.finalproject_siyu_8859412.presentation.modals

import kotlinx.serialization.Serializable

@Serializable
data class LocationItem(
    var latitude: Double,
    var longitude: Double,
    var address: String
)