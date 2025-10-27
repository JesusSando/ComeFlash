package com.example.comeflash.viewmodel



// Función para validar que un campo no esté vacío
fun validarNoVacio(campo: String): String {
    return if (campo.isBlank()) "Este campo es obligatorio" else ""
}

// Función para validar el formato de la tarjeta de crédito
fun validarNumeroTarjeta(numero: String): String {
    return if (numero.length !in 13..19 || numero.any { !it.isDigit() }) {
        "Número de tarjeta inválido"
    } else {
        ""
    }
}

// Función para validar la fecha de expiración en formato MM/AA
fun validarFechaExpiracion(fecha: String): String {
    return if (!fecha.matches(Regex("\\d{2}/\\d{2}"))) {
        "Formato de fecha inválido (MM/AA)"
    } else {
        ""
    }
}

// Función para validar el CVV de la tarjeta
fun validarCvv(cvv: String): String {
    return if (cvv.length !in 3..4 || cvv.any { !it.isDigit() }) {
        "CVV inválido"
    } else {
        ""
    }
}
