package com.example.movieappmad23.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FavoritesViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _favoriteMovies = MutableStateFlow(listOf<Movie>())
    val favoriteMoviesState: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getFavoriteMovies().collect() { favoriteList ->
                _favoriteMovies.value = favoriteList
            }
        }
    }

    suspend fun updateFavoriteMovies(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        movieRepository.update(movie)
    }

}