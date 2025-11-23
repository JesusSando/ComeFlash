package com.example.comeflash.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.comeflash.R
import com.example.comeflash.data.database.AppDatabase
import com.example.comeflash.data.model.Comida
import com.example.comeflash.data.model.NutrientesComida
import com.example.comeflash.data.repository.ComidaRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import com.example.comeflash.data.remote.*
import com.example.comeflash.data.repository.NutrientesReposity

class NutrientesViewmodel: ViewModel() {


    private val repository = NutrientesReposity()

    private val _nutrientes = MutableStateFlow<NutrientesComida?>(null)
    val nutrientes: StateFlow<NutrientesComida?> = _nutrientes
    fun cargarNutrientes(codigoBarras: String) {
        viewModelScope.launch {
            _nutrientes.value = repository.obtenerNutrientes(codigoBarras)
        }
    }
}