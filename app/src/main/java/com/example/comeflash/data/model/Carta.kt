package com.example.comeflash.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Carta (
    val comida: Comida,

    val cantidad: Int = 1,
)
