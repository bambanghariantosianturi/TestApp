package com.example.hellojetpackcompose.ui.screen.listscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellojetpackcompose.common.UiState
import com.example.hellojetpackcompose.data.HeroRepository
import com.example.hellojetpackcompose.model.Hero
import com.example.hellojetpackcompose.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Alo-BambangHariantoSianturi on 08/11/23.
 */
class ListHeroScreenViewModel(private val repository: HeroRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<User>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<User>>> get() = _uiState

    fun getHeroes() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _uiState.value = UiState.Loading
                _uiState.value = UiState.Success(repository.getHeroes())
            }
        }
    }
}