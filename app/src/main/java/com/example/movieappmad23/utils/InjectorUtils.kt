package com.example.movieappmad23.utils

import android.content.Context
import com.example.movieappmad23.MoviesViewModelFactory
import com.example.movieappmad23.data.MovieDatabase
import com.example.movieappmad23.repositories.MovieRepository
import com.example.movieappmad23.viewmodel.AddScreenViewModelFactory
import com.example.movieappmad23.viewmodel.FavoritesViewModelFactory


object InjectorUtils {

    private fun getMovieRepository(context: Context): MovieRepository {
        return MovieRepository(MovieDatabase.getDatabase(context).movieDao())
    }

    fun provideMovieViewModelFactory(context: Context): MoviesViewModelFactory {
        val repository = getMovieRepository(context)
        return MoviesViewModelFactory(repository = repository)

    }

    fun provideFavoriteViewModelFactory(context: Context): FavoritesViewModelFactory {
        val repository = getMovieRepository(context)
        return FavoritesViewModelFactory(repository = repository)
    }

    fun provideAddMovieScreenViewModelFactory(context: Context): AddScreenViewModelFactory {
        val repository = getMovieRepository(context)
        return AddScreenViewModelFactory(repository = repository)
    }

}