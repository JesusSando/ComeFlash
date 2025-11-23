package com.example.comeflash.data.model

import com.google.gson.annotations.SerializedName

data class ProductResponse(
    val status: Int,
    val product: Product?
)

data class Product(
    @SerializedName("product_name") val productName: String?,
    @SerializedName("image_url") val imageUrl: String?,

    val nutriments: Nutriments?
)

data class Nutriments(
    @SerializedName("energy-kcal_100g") val kcal100g: String?,
    @SerializedName("energy-kcal_serving") val kcalServing: String?,
    @SerializedName("energy-kcal") val kcalGeneric: String?,
    @SerializedName("fat_100g") val fat100g: String?,
    @SerializedName("fat_serving") val fatServing: String?,

    @SerializedName("saturated-fat_100g") val satFat100g: String?,
    @SerializedName("saturated-fat_serving") val satFatServing: String?,

    @SerializedName("carbohydrates_100g") val carbs100g: String?,
    @SerializedName("carbohydrates_serving") val carbsServing: String?,

    @SerializedName("sugars_100g") val sugars100g: String?,
    @SerializedName("sugars_serving") val sugarsServing: String?,

    @SerializedName("fiber_100g") val fiber100g: String?,
    @SerializedName("fiber_serving") val fiberServing: String?,

    @SerializedName("proteins_100g") val proteins100g: String?,
    @SerializedName("proteins_serving") val proteinsServing: String?,

    @SerializedName("salt_100g") val salt100g: String?,
    @SerializedName("salt_serving") val saltServing: String?
)
