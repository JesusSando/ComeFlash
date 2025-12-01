package com.example.comeflash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.data.model.NutrientesComida
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.example.comeflash.data.repository.NutrientesRepository

class NutrientesViewmodel(
    private val repository: NutrientesRepository = NutrientesRepository()
) : ViewModel() {

    private val _nutrientes = MutableStateFlow<NutrientesComida?>(null)
    val nutrientes: StateFlow<NutrientesComida?> = _nutrientes
    fun cargarNutrientes(codigoBarras: String) {
        viewModelScope.launch {
            _nutrientes.value = repository.obtenerNutrientes(codigoBarras)
        }
    }
}