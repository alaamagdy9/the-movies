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
                        selected = currentRoute == destination.route,
                        onClick = {
                            navController.navigate(destination.route) {
                                // One tab on the stack at a time, so Back leaves the app
                                // from the first tab instead of walking through every
                                // tab that was opened.
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true //if user scrolled till film no 50 save the last state
                                }
                                launchSingleTop = true //only 1 instance , not multiple genre even if the user clicked
                                restoreState = true //return back t the last state(film no 50)
                            }
                        },
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