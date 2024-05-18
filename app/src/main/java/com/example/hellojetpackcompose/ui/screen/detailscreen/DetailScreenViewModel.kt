package com.example.hellojetpackcompose.ui.screen.detailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellojetpackcompose.common.UiState
import com.example.hellojetpackcompose.data.HeroRepository
import com.example.hellojetpackcompose.model.Hero
import com.example.hellojetpackcompose.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Created by Alo-BambangHariantoSianturi on 03/11/23.
 */
class DetailScreenViewModel(private val repository: HeroRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<User>> get() = _uiState

    fun getHeroById(heroId: String) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getHeroesById(heroId))
        }
    }
}