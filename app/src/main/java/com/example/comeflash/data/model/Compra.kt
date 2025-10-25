package com.example.comeflash.data.model
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "compras")
data class Compra (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val usuarioId: Int,
    val fecha: Long = Date().time,
    val total: Double
    )