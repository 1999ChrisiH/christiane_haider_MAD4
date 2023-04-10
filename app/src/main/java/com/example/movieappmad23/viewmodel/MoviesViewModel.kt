package com.example.movieappmad23.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.models.getMovies

class MoviesViewModel: ViewModel(){
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
    get() = _movieList
}