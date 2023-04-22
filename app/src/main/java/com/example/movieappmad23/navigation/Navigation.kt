package com.example.movieappmad23.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad23.screens.*
import com.example.movieappmad23.utils.InjectorUtils
import com.example.movieappmad23.viewmodel.AddScreenViewModel
import com.example.movieappmad23.viewmodel.FavoritesViewModel
import com.example.movieappmad23.viewmodel.HomeViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    // inside a composable
    val homeViewModel: HomeViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(
        LocalContext.current))

    val favoritesViewModel: FavoritesViewModel = viewModel(factory = InjectorUtils.provideFavoriteViewModelFactory(
        LocalContext.current))

    val addMoviesViewModel: AddScreenViewModel = viewModel(factory = InjectorUtils.provideAddMovieScreenViewModelFactory(
        LocalContext.current))

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route){
            HomeScreen(navController = navController, homeViewModel = homeViewModel)
        }

        composable(Screen.FavoriteScreen.route) {
            FavoriteScreen(navController = navController, favViewModel = favoritesViewModel)
        }

        composable(Screen.AddMovieScreen.route) {
            AddMovieScreen(navController = navController, addScreenViewModel = addMoviesViewModel)
        }

        // build a route like: root/detail-screen/id=34
        composable(
            Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.StringType})
        ) { backStackEntry ->    // backstack contains all information from navhost
            DetailScreen(navController = navController,
                detailViewModel = homeViewModel,
                movieId = backStackEntry.arguments?.getString(DETAIL_ARGUMENT_KEY))   // get the argument from navhost that will be passed
        }
    }
}
