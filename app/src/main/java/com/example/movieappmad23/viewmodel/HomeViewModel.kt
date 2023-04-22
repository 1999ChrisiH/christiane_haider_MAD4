package com.example.movieappmad23.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// inherit from ViewModel class
class HomeViewModel(private val movieRepository: MovieRepository): ViewModel() {

    private val _movieList = MutableStateFlow(listOf<Movie>())
    val movieListState: StateFlow<List<Movie>> = _movieList.asStateFlow()

    init {
        viewModelScope.launch {
            movieRepository.getAllMovies().collect { movieList ->
                if (movieList.isNotEmpty()) {
                    _movieList.value = movieList
                }
            }
        }
    }

    suspend fun updateFavoriteMovies(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        movieRepository.update(movie)
    }
}