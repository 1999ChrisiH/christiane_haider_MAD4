package com.example.movieappmad23.data

import androidx.room.*
import com.example.movieappmad23.models.Movie

@Dao
interface MovieDao {
    @Insert
    fun addMovie(movie: Movie)

    @Update
    fun updateMovie(movie:Movie)

    @Delete
    fun deleteMovie(movie: Movie)

    @Query("SELECT * from movie")
    fun readMovie(): List<Movie>
}