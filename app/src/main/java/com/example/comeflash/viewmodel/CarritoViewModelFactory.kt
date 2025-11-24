package com.example.comeflash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.comeflash.data.repository.CartaRepository

class CarritoViewModelFactory (
    private val repository: CartaRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CarritoViewModel::class.java)) {
                return CarritoViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

}