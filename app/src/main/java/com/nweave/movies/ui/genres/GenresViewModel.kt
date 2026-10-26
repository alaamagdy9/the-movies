package com.nweave.movies.ui.genres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nweave.movies.data.remote.TmdbApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Hilt builds this and hands it the TmdbApi that NetworkModule assembled.
@HiltViewModel
class GenresViewModel @Inject constructor(
    private val api: TmdbApi
) : ViewModel() {

    // Only this class writes the state; the screen reads it.
    private val _state = MutableStateFlow<GenresState>(GenresState.Loading)
    val state: StateFlow<GenresState> = _state.asStateFlow()

    init {
        load()
    }

    // Also what "Try again" calls.
    fun load() {
        viewModelScope.launch {
            _state.value = GenresState.Loading
            _state.value = try {
                // The endpoint is not paged - all nineteen arrive at once.
                GenresState.Success(api.getGenres().genres)
            } catch (error: Exception) {
                GenresState.Error("Could not load the genres.")
            }
        }
    }
}