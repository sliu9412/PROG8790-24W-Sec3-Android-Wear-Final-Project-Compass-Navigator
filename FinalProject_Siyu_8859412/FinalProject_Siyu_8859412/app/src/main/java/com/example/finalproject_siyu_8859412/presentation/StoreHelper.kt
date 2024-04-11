package com.example.finalproject_siyu_8859412.presentation

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.compose.runtime.mutableStateListOf
import com.example.finalproject_siyu_8859412.presentation.modals.LocationItem
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StoreHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences(storeName, MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    var locationList = mutableStateListOf<LocationItem>()

    fun storeList() {
        editor.putString(key, Json.encodeToString(locationList.toList()))
        editor.apply()
    }

    fun loadList() {
        val jsonString = sharedPreferences.getString(key, null)
        locationList = mutableStateListOf<LocationItem>()
        if (!jsonString.isNullOrBlank()) {
            val jsonList = Json.decodeFromString<List<LocationItem>>(jsonString)
            jsonList.forEach { locationItem -> locationList.add(locationItem) }
        }
    }

    companion object {
        const val storeName = "Store"
        const val key = "Collection"
    }
}