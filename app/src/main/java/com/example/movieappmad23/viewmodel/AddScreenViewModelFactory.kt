package com.example.movieappmad23.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieappmad23.repositories.MovieRepository

class AddScreenViewModelFactory(private val repository: MovieRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddScreenViewModel::class.java)) {
            return AddScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Wrong Class")
    }
}