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


                NutrientesComida(


                    imagenUrl = product?.imageUrl,

                    calorias = nutriments?.kcal?.toIntOrNull(),
                    grasas = nutriments?.fat?.toDoubleOrNull(),
                    grasasSaturadas = nutriments?.satFat?.toDoubleOrNull(),
                    carbohidratos = nutriments?.carbs?.toDoubleOrNull(),
                    azucares = nutriments?.sugars?.toDoubleOrNull(),
                    fibra = nutriments?.fiber?.toDoubleOrNull(),
                    proteinas = nutriments?.proteins?.toDoubleOrNull(),
                    sal = nutriments?.salt?.toDoubleOrNull()


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