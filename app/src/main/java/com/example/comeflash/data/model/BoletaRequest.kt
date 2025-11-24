package com.example.comeflash.data.model

data class BoletaRequest(
    val usuarioId: Int,
    val items: List<ItemCompra>,
    val total: Double
)
