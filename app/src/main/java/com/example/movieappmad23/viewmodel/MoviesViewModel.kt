package com.example.movieappmad23.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.models.getMovies

class MoviesViewModel: ViewModel(){
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
    get() = _movieList

    private val _favoriteMovies = mutableListOf<Movie>().toMutableStateList()
    val favoriteMovies: List<Movie>
        get() = _favoriteMovies

    fun toggleFavorite(movie: Movie) {
        _movieList.find { it.id == movie.id }?.let { task ->
            task.isFavorite = !task.isFavorite
            if (task.isFavorite) {
                _favoriteMovies.add(movie)
            } else {
                _favoriteMovies.remove(movie)
            }
        }
    }
}