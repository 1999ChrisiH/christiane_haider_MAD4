package com.example.movieappmad23.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.example.movieappmad23.models.Genre
import com.example.movieappmad23.models.ListItemSelectable
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.models.getMovies

class MoviesViewModel: ViewModel(){
    private val _movieList = getMovies().toMutableStateList()
    val movieList: List<Movie>
    get() = _movieList

    private val _favoriteMovies = mutableListOf<Movie>().toMutableStateList()
    val favoriteMovies: List<Movie>
        get() = _favoriteMovies

    fun likeFavoriteMovies(movie: Movie) {
        _movieList.find { it.id == movie.id }?.let { task ->
            task.isFavorite = !task.isFavorite
            if (task.isFavorite) {
                _favoriteMovies.add(movie)
            } else {
                _favoriteMovies.remove(movie)
            }
        }
    }

    fun addMovie(
        title: String,
        year: String,
        genres: List<Genre>,
        director: String,
        actors: String,
        plot: String,
        rating: String
    ) {
        val newMovie = Movie(
            id = "$title + $year + $genres",
            title = title,
            year = year,
            genre = genres,
            director = director,
            actors = actors,
            plot = plot,
            rating = rating.toFloat(),
            images = listOf("https://thumbs.dreamstime.com/b/no-image-available-icon-photo-camera-flat-vector-illustration-132483141.jpg")
        )
        _movieList.add(newMovie)

    }

    private var addMovie: Movie = Movie("", "", "", listOf(), "", "", "", listOf(), 0.0f)

    var title = mutableStateOf(addMovie.title)
    var titleError: MutableState<Boolean> = mutableStateOf(false)

    val year = mutableStateOf(addMovie.year)
    var yearError: MutableState<Boolean> = mutableStateOf(false)

    var director = mutableStateOf(addMovie.director)
    var directorError: MutableState<Boolean> = mutableStateOf(false)

    var actors = mutableStateOf(addMovie.actors)
    var actorsError: MutableState<Boolean> = mutableStateOf(false)

    var plot = mutableStateOf(addMovie.plot)
    var plotError: MutableState<Boolean> = mutableStateOf(false)

    var rating = mutableStateOf(addMovie.rating.toString().replace("0.0", ""))
    var ratingError: MutableState<Boolean> = mutableStateOf(false)

    var addButtonEnabled: MutableState<Boolean> = mutableStateOf(true)

    var genreItems = mutableStateOf(
        Genre.values().map { genre ->
            ListItemSelectable(
                title = genre.toString(),
                isSelected = false
            )
        }
    )
    var genreError: MutableState<Boolean> = mutableStateOf(false)


    private fun enableAddButton() {
        addButtonEnabled.value =
            (titleError.value.not()
                    && yearError.value.not()
                    && directorError.value.not()
                    && actorsError.value.not()
                    && plotError.value.not()
                    && ratingError.value.not()
                    && genreError.value.not()
                    )
    }

    fun validation() {
        validateTitle()
        validateYear()
        validateDirector()
        validateActors()
        validatePlot()
        validateGenres()
        validateRating()
    }

    fun validateTitle() {
        titleError.value = title.value.isEmpty()
        enableAddButton()
    }

    fun validateYear() {
        yearError.value = year.value.isEmpty()
        enableAddButton()
    }

    fun validateDirector() {
        directorError.value = director.value.isEmpty()
        enableAddButton()
    }

    fun validateActors() {
        actorsError.value = actors.value.isEmpty()
        enableAddButton()
    }

    fun validatePlot() {
        plotError.value = plot.value.isEmpty()
        enableAddButton()
    }

    fun validateRating() {
        try {
            rating.value.toFloat()
            ratingError.value = false
        } catch (e: java.lang.Exception) {
            ratingError.value = true
        } finally {
            enableAddButton()
        }
    }

    fun validateGenres() {
        genreError.value = true
        genreItems.value.forEach genres@{
            if (it.isSelected) {
                genreError.value = false
                return@genres
            }
        }
        enableAddButton()
    }

}