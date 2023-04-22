package com.example.movieappmad23.utils

import android.content.Context
import com.example.movieappmad23.MoviesViewModelFactory
import com.example.movieappmad23.data.MovieDatabase
import com.example.movieappmad23.repositories.MovieRepository

object InjectorUtils {
    private fun getMovieRepository(context: Context): MovieRepository{
        return MovieRepository(MovieDatabase.getDatabase(context).movieDao())
    }
    fun provideMovieViewModelFactory(context: Context): MoviesViewModelFactory{
        val repository = getMovieRepository(context)
        return MoviesViewModelFactory(repository)
    }
}