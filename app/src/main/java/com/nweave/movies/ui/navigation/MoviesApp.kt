package com.nweave.movies.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nweave.movies.ui.genres.GenresScreen
import com.nweave.movies.ui.search.SearchScreen
import com.nweave.movies.ui.trending.TrendingScreen

@Composable
fun MoviesApp() {
    val navController = rememberNavController() //remember which screen the user is on
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route //give me the current route whether trending or genre or search

    Scaffold(
        bottomBar = {
            NavigationBar {
                MovieDestination.entries.forEach { destination -> //the 3 entries (search,genre,trending) //go through each one of them
                    NavigationBarItem(
                        selected = currentRoute == destination.route, //
                        onClick = { navController.navigate(destination.route) }, //on clicking go to its route
                        icon = {
                            Icon(
                                painter = painterResource(destination.icon), //convert the id to icon
                                contentDescription = null
                            )
                        },
                        label = { Text(stringResource(destination.label)) } //convert the id to text
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MovieDestination.Trending.route, //app opens on trending
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(MovieDestination.Trending.route) { TrendingScreen() }
            composable(MovieDestination.Genres.route) { GenresScreen() }
            composable(MovieDestination.Search.route) { SearchScreen() }
        }
    }
}