package com.example.comeflash.data.repository

import android.util.Log
import com.example.comeflash.data.model.NutrientesComida
import com.example.comeflash.data.remote.OpenFoodRetrofitInstance

class NutrientesReposity {

    suspend fun obtenerNutrientes(codigoBarras: String): NutrientesComida? {
        val apiService = OpenFoodRetrofitInstance.api

        return try {
            val response = apiService.getProduct(codigoBarras)


            if (response.status == 1) {
                val product = response.product
                val nutriments = product?.nutriments
                Log.d("OFF_DEBUG", "Status OK. URL recibida: ${product?.imageUrl}")
                Log.d("OFF_DATA", "Kcal 100g: ${nutriments?.kcal100g}")
                Log.d("OFF_DATA", "Fat 100g: ${nutriments?.fat100g}")

                NutrientesComida(


                    imagenUrl = product?.imageUrl,



                    calorias = nutriments?.kcal100g?.toDoubleOrNull()?.toInt(),
                    grasas = nutriments?.fat100g?.toDouble(),
                    grasasSaturadas = nutriments?.satFat100g?.toDouble(),
                    carbohidratos = nutriments?.carbs100g?.toDouble(),
                    azucares = nutriments?.sugars100g?.toDouble(),
                    fibra = nutriments?.fiber100g?.toDouble(),
                    proteinas = nutriments?.proteins100g?.toDouble(),
                    sal = nutriments?.salt100g?.toDouble()


                )

            } else {
                Log.w("OFF_DEBUG", "Producto no encontrado (Status 0) para código: $codigoBarras")

                null
            }
        } catch (e: Exception) {

            e.printStackTrace()
            null
        }
    }
}