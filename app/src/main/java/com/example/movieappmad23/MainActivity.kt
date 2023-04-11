package com.example.movieappmad23

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.movieappmad23.navigation.Navigation
import com.example.movieappmad23.ui.theme.MovieAppMAD23Theme
import com.example.movieappmad23.viewmodel.MoviesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val moviesViewModel: MoviesViewModel by viewModels()

        setContent {
            MovieAppMAD23Theme {
                Navigation(moviesViewModel = moviesViewModel)
            }
        }
    }
}

