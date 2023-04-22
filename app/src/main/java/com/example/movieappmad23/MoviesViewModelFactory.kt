package com.example.movieappmad23

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad23.repositories.MovieRepository
import com.example.movieappmad23.viewmodel.MoviesViewModel

class MoviesViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T { //gibt vor wie übergebenes ViewModel Objekt instantiiert wird
        if (modelClass.isAssignableFrom((MoviesViewModel::class.java))) {
            return MoviesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}