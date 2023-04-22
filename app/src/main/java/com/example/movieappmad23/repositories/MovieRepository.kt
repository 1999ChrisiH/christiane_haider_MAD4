package com.example.movieappmad23.repositories

import com.example.movieappmad23.data.MovieDao
import com.example.movieappmad23.models.Movie
import kotlinx.coroutines.flow.Flow

// macht mehr Sinn bei komplexeren Apps - aber trotzdem einfügen!
// um von ViewModel wegzukapseln - Viewmodel muss nicht wissen, ob von remote DB oder lokaler DB zugegriffen wird auf Daten

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun add(movie: Movie) = movieDao.add(movie)

    suspend fun delete(movie: Movie) = movieDao.delete(movie)

    suspend fun update(movie: Movie) = movieDao.update(movie)

    fun getAllMovies(): Flow<List<Movie>> = movieDao.getAll()

    fun getFavoriteMovies(): Flow<List<Movie>> = movieDao.getFavorites()

    fun getMovieById(id: String): Flow<Movie?> = movieDao.getMovieById(id)
  }