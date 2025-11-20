package com.example.comeflash.data.model
import androidx.room.*

import androidx.room.Entity
import androidx.room.PrimaryKey



data class Comida (

    val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val precioOferta: Double? = null,
    val oferta: Boolean = false,
    val tipoComida: String,
    val imagenResId: String
)