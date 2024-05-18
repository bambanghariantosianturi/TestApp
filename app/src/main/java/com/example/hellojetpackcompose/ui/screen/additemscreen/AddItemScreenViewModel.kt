package com.example.hellojetpackcompose.ui.screen.additemscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hellojetpackcompose.common.UiState
import com.example.hellojetpackcompose.data.HeroRepository
import com.example.hellojetpackcompose.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddItemScreenViewModel(private val repository: HeroRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<User>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<User>> get() = _uiState

    fun addItemVehicle(user: User) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.addItemVehicle(user))
        }
    }
}