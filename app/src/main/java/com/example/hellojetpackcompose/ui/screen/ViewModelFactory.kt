package com.example.hellojetpackcompose.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hellojetpackcompose.data.HeroRepository
import com.example.hellojetpackcompose.ui.screen.additemscreen.AddItemScreenViewModel
import com.example.hellojetpackcompose.ui.screen.detailscreen.DetailScreenViewModel
import com.example.hellojetpackcompose.ui.screen.listscreen.ListHeroScreenViewModel

/**
 * Created by Alo-BambangHariantoSianturi on 03/11/23.
 */
class ViewModelFactory(private val repository: HeroRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailScreenViewModel::class.java)) {
            return DetailScreenViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(ListHeroScreenViewModel::class.java)) {
            return ListHeroScreenViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(AddItemScreenViewModel::class.java)) {
            return AddItemScreenViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}