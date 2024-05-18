package com.example.hellojetpackcompose.di

import com.example.hellojetpackcompose.data.HeroRepository

/**
 * Created by Alo-BambangHariantoSianturi on 03/11/23.
 */
object Injection {
    fun provideRepository(): HeroRepository {
        return HeroRepository.getInstance()
    }
}