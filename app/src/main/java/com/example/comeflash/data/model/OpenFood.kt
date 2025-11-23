package com.example.comeflash.data.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val status: Int,
    val product: Product?
)

data class Product(
    @SerializedName("image_front_url") val imageUrl: String?,

    val nutriments: Nutriments?
)

data class Nutriments(

    @SerializedName("energy-kcal") val kcal: String?,
    @SerializedName("fat") val fat: String?,

    @SerializedName("saturated-fat") val satFat: String?,

    @SerializedName("carbohydrates") val carbs: String?,


    @SerializedName("sugars") val sugars: String?,
    @SerializedName("fiber") val fiber: String?,

    @SerializedName("proteins") val proteins: String?,

    @SerializedName("salt") val salt: String?
)
