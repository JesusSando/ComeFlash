package com.example.comeflash.data.model

data class Boleta(
    val id: Int,
    val usuario: Usuario,
    val compras: List<ItemCompra>,
    val total: Double,
    val estado: String,
    val fecha: String
)