package com.nweave.movies.ui.trending

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
class TrendingViewModel @Inject constructor(
    private val api: TmdbApi
) : ViewModel() {

    // Only this class writes the state; the screen reads it.
    private val _state = MutableStateFlow<TrendingState>(TrendingState.Loading)
    val state: StateFlow<TrendingState> = _state.asStateFlow()

    init {
        load()
    }
    fun load() {
        viewModelScope.launch {
            _state.value = TrendingState.Loading
            _state.value = try {
                // The first page only. Paging comes in Assignment #5.
                TrendingState.Success(api.getTrending().results)
            } catch (error: Exception) {
                TrendingState.Error("Could not load the trending movies.")
            }
        }
    }
}