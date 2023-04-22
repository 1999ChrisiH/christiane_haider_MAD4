package com.example.movieappmad23.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.movieappmad23.R
import com.example.movieappmad23.models.Genre
import com.example.movieappmad23.viewmodel.MoviesViewModel
import com.example.movieappmad23.widgets.SimpleTopAppBar
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun AddMovieScreen(navController: NavController, moviesViewModel: MoviesViewModel) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.add_movie))
            }
        },
    ) { padding ->
        MainContent(Modifier.padding(padding), moviesViewModel = moviesViewModel)
    }
    moviesViewModel.validation()
}

@Composable
fun InputField(
    text: MutableState<String>,
    errorState: MutableState<Boolean>,
    label: Int,
    validateMethod: () -> Unit
) {
    OutlinedTextField(
        value = text.value,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        onValueChange = {
            text.value = it
            validateMethod()
        },
        label = { Text(stringResource(id = label)) },
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainContent(modifier: Modifier = Modifier, moviesViewModel: MoviesViewModel) {
    val coroutineScope = rememberCoroutineScope()
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        ) {
            InputField(
                text = moviesViewModel.title,
                label = R.string.enter_movie_title,
                errorState = moviesViewModel.titleError,
                validateMethod = {moviesViewModel.validateTitle()}
            )
            PrintErrorMsg(value = moviesViewModel.titleError.value, text = stringResource(R.string.invalid_input))

            InputField(
                text = moviesViewModel.year,
                label = R.string.enter_movie_year,
                errorState = moviesViewModel.yearError,
                validateMethod = {moviesViewModel.validateYear()}
            )
            PrintErrorMsg(value = moviesViewModel.yearError.value, text = stringResource(R.string.invalid_input))

            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = stringResource(R.string.select_genres),
                textAlign = TextAlign.Start,
                style = MaterialTheme.typography.h6
            )

            PrintErrorMsg(value = moviesViewModel.genreError.value, text = stringResource(R.string.choose_genre))

            LazyHorizontalGrid(
                modifier = Modifier.height(100.dp),
                rows = GridCells.Fixed(3)
            ) {
                items(moviesViewModel.genreItems.value) { genreItem ->
                    Chip(
                        modifier = Modifier.padding(2.dp),
                        colors = ChipDefaults.chipColors(
                            backgroundColor = if (genreItem.isSelected)
                                colorResource(id = R.color.purple_200)
                            else
                                colorResource(id = R.color.white)
                        ),
                        onClick = {
                            moviesViewModel.genreItems.value =
                                moviesViewModel.genreItems.value.map {
                                    if (it.title == genreItem.title) {
                                        genreItem.copy(isSelected = !genreItem.isSelected)
                                    } else {
                                        it
                                    }
                                }
                        }
                    ) {
                        Text(text = genreItem.title)
                    }
                }
            }

            InputField(
                text = moviesViewModel.director,
                label = R.string.enter_director,
                errorState = moviesViewModel.directorError,
                validateMethod = {moviesViewModel.validateDirector()}
            )
            PrintErrorMsg(value = moviesViewModel.directorError.value, text = stringResource(R.string.invalid_input))


            InputField(
                text = moviesViewModel.actors,
                label = R.string.enter_actors,
                errorState = moviesViewModel.actorsError,
                validateMethod = {moviesViewModel.validateActors()}
            )
            PrintErrorMsg(value = moviesViewModel.actorsError.value, text = stringResource(R.string.invalid_input))


            InputField(
                text = moviesViewModel.plot,
                label = R.string.enter_plot,
                errorState = moviesViewModel.plotError,
                validateMethod = {moviesViewModel.validatePlot()}
            )
            PrintErrorMsg(value = moviesViewModel.plotError.value, text = stringResource(R.string.invalid_input))


            InputField(
                text = moviesViewModel.rating,
                label = R.string.enter_rating,
                errorState = moviesViewModel.ratingError,
                validateMethod = {moviesViewModel.validateRating()}
            )
            PrintErrorMsg(value = moviesViewModel.yearError.value, text = stringResource(R.string.decimal_number))


            Button(
                enabled = moviesViewModel.addButtonEnabled.value,
                /*TODO add a new movie to the movie list*/
                onClick = {

                    val genreList: MutableList<Genre> = mutableListOf()
                    moviesViewModel.genreItems.value.filter { it.isSelected }
                        .forEach { genreList.add(Genre.valueOf(it.title)) }

                    coroutineScope.launch {
                        moviesViewModel.addMovie(
                            moviesViewModel.title.value,
                            moviesViewModel.year.value,
                            genreList,
                            moviesViewModel.director.value,
                            moviesViewModel.actors.value,
                            moviesViewModel.plot.value,
                            moviesViewModel.rating.value
                        )
                    }

                }) {
                Text(text = stringResource(R.string.add))
            }
        }
    }

}

@Composable
fun PrintErrorMsg(value: Boolean, text: String) {
    if (value) {
        Text(
            text = text,
            fontSize = 12.sp,
            color = Color.Red
        )
    }
}