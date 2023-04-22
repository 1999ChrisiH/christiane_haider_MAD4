package com.example.movieappmad23

import androidx.room.TypeConverter
import com.example.movieappmad23.models.Genre

class Converters {
    private val SEPERATOR = ","

    @TypeConverter
    fun genreListToString(value: List<Genre>): String =
        value.joinToString(separator = SEPERATOR) { it.toString() }

    @TypeConverter
    fun stringToGenreList(value: String): List<Genre> = value.split(SEPERATOR).map { Genre.valueOf(it) }

    @TypeConverter
    fun stringToList(value: String) = value.split(SEPERATOR).map { it.trim() }

    @TypeConverter
    fun listToString(value: List<String>) = value.joinToString { SEPERATOR }
}

