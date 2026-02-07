package com.example.weatherapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.model.FavoriteCity
import com.example.weatherapp.data.repository.FirebaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FavoritesViewModel : ViewModel() {

    private val repository = FirebaseRepository()

    private val _favorites =
        MutableStateFlow<List<FavoriteCity>>(emptyList())
    val favorites: StateFlow<List<FavoriteCity>> = _favorites

    init {
        repository.signIn {
            repository.observeFavorites { list ->
                _favorites.value = list
            }
        }
    }

    fun addFavorite(
        city: String,
        note: String
    ) {
        if (city.isBlank()) return
        repository.addFavorite(city, note)
    }

    fun updateNote(
        id: String,
        note: String
    ) {
        repository.updateNote(id, note)
    }

    fun deleteFavorite(
        id: String
    ) {
        repository.deleteFavorite(id)
    }
}
