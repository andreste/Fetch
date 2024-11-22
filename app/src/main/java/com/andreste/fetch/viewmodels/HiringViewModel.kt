package com.andreste.fetch.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.andreste.fetch.models.HiringItem
import com.andreste.fetch.repositories.HiringRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ViewState {
    data object Loading : ViewState()
    data class Content(val list: List<HiringItem>) : ViewState()
    data class Error(val message: String) : ViewState()
}

@HiltViewModel
class HiringViewModel @Inject constructor(
    private val hiringRepository: HiringRepository
): ViewModel() {

    private var navController: NavController? = null

    private val _state = MutableStateFlow<ViewState>(ViewState.Loading)
    val state = _state.asStateFlow()

    fun start(navController: NavController) {
        this.navController = navController
    }

    fun getItems() {
        _state.value = ViewState.Loading

        viewModelScope.launch {
            val response = hiringRepository.getItems()
            if (response.isSuccessful) {
                response.body()?.let {
                    _state.value = ViewState.Content(it)
                }
            } else {
                _state.value = ViewState.Error("Could not get items")
            }
        }
    }
}