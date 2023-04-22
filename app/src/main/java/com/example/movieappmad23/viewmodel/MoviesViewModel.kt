package com.example.movieappmad23.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad23.models.Genre
import com.example.movieappmad23.models.ListItemSelectable
import com.example.movieappmad23.models.Movie
import com.example.movieappmad23.repositories.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MoviesViewModel(private val repository: MovieRepository): ViewModel(){
    private val _movieList = MutableStateFlow(listOf<Movie>())
    val movies: StateFlow<List<Movie>> = _movieList.asStateFlow()

    private val _favoriteMovies = MutableStateFlow(listOf<Movie>())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllMovies().collect { movieList -> // Daten aus Flow wieder einsammeln
                if (!movieList.isNullOrEmpty()) {
                    _movieList.value = movieList
                }
            }
            repository.getFavoriteMovies().collect{ favoritesList ->
                if(!favoritesList.isNullOrEmpty()){
                    _favoriteMovies.value = favoritesList
                }
            }
        }
    }
    //suspend um zu deklarieren dass sie auch in Threads arbeiten können (long running)
    // suspend functions müssen in  Coroutines aufgerufen werden
    suspend fun likeFavoriteMovie(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        repository.update(movie)
    }

    suspend fun addMovie(
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
        repository.add(newMovie)

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

    fun saveMovie() {
        TODO("Not yet implemented")
    }

}