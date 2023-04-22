package com.example.movieappmad23

import androidx.room.TypeConverter
import com.example.movieappmad23.models.Genre

class Converters {
    @TypeConverter
    fun fromGenreListToStrings(genreList: List<Genre>): String {
        return genreList.toString()
    }

    @TypeConverter
    fun fromImageList(imageList: List<String>): String {
        return imageList.toString()
    }
}