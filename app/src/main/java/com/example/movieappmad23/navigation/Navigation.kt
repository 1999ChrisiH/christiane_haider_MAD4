package com.example.movieappmad23.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad23.MoviesViewModelFactory
import com.example.movieappmad23.data.MovieDatabase
import com.example.movieappmad23.repositories.MovieRepository
import com.example.movieappmad23.screens.*
import com.example.movieappmad23.utils.InjectorUtils
import com.example.movieappmad23.viewmodel.MoviesViewModel

@Composable
fun Navigation(moviesViewModel: MoviesViewModel) {
    val navController = rememberNavController()

    val moviesViewModel: MoviesViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(LocalContext.current))

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route){
            HomeScreen(navController = navController, moviesViewModel = moviesViewModel)
        }

        composable(Screen.FavoriteScreen.route) {
            FavoriteScreen(navController = navController, moviesViewModel = moviesViewModel)
        }

        composable(Screen.AddMovieScreen.route) {
            AddMovieScreen(navController = navController, moviesViewModel = moviesViewModel)
        }

        // build a route like: root/detail-screen/id=34
        composable(
            Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ) { backStackEntry ->    // backstack contains all information from navhost
            DetailScreen(navController = navController, moviesViewModel = moviesViewModel, backStackEntry.arguments?.getString(
                DETAIL_ARGUMENT_KEY))   // get the argument from navhost that will be passed
        }
    }
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MoviesViewModelFactory(repository)
    val viewModel: MoviesViewModel = viewModel(factory = factory)
    val moviesState = viewModel.movies.collectAsState()
}