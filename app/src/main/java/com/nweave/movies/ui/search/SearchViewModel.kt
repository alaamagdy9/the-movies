package com.nweave.movies.ui.search


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nweave.movies.data.remote.TmdbApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val api: TmdbApi
) : ViewModel() {
    private val _state = MutableStateFlow<SearchState>(SearchState.Empty)
    val state: StateFlow<SearchState> = _state.asStateFlow()
    fun search(query: String) {
        // An empty box means there is nothing to ask TMDB for.
        if (query.isBlank()) {
            _state.value = SearchState.Empty
            return
        }

        viewModelScope.launch {
            _state.value = SearchState.Loading
            _state.value = try {
                SearchState.Success(api.searchMovies(query).results)
            } catch (error: Exception) {
                SearchState.Error("Could not search for films.")
            }
        }
    }
}
