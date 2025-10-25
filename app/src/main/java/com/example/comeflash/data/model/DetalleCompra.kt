package com.example.comeflash.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detalles_compra")
data class DetalleCompra (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val compraId: Int,
    val comidaId: Int,
    val nombreProducto: String,
    val cantidad: Int,
    val precioUnitario: Double,
    val subtotal: Double
)