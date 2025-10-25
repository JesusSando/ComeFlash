package com.example.comeflash.data.model
import androidx.room.*

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "comidas")
data class Comida (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val precioOferta: Double? = null,
    val oferta: Boolean = false,
    val tipoComida: String,
    val imagenUri: String? = null
)