package com.example.movieappmad23

import com.example.movieappmad23.models.Genre

data class AddMovie(
    var title: String = "",
    var year: String = "",
    var genres: List<Genre> = listOf(),
    var director: String = "",
    var actors: String = "",
    var plot: String = "",
    var rating: Float = 0.0f
)