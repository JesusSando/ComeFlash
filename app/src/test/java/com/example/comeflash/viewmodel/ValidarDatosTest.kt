package com.example.comeflash.viewmodel

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValidarDatosTest {

    @Test
    fun `validarNoVacio que retornr error cuando ele campo esta vacio`() {
        val resultado = validarNoVacio("")
        assertEquals("Este campo es obligatorio", resultado)
    }

    @Test
    fun `validarNoVacio que retorna error cuando el campo tiene solo espacios`() {
        val resultado = validarNoVacio("   ")
        assertEquals("Este campo es obligatorio", resultado)
    }

    @Test
    fun `validarNoVacio que retorne vacio cuando hay texto`() {
        val resultado = validarNoVacio("Hola")
        assertEquals("", resultado)
    }

    @Test
    fun `validarNumeroTarjeta falla cuando es demasiado corta`() {
        val resultado = validarNumeroTarjeta("123")
        assertEquals("Número de tarjeta inválido", resultado)
    }

    @Test
    fun `validarNumeroTarjeta falla cuando contiene letras`() {
        val resultado = validarNumeroTarjeta("1234abcd567")
        assertEquals("Número de tarjeta inválido", resultado)
    }

    @Test
    fun `validarNumeroTarjeta pasa cuando tiene 13 digitos numericos`() {
        val resultado = validarNumeroTarjeta("1234567890123")
        assertEquals("", resultado)
    }

    @Test
    fun `validarNumeroTarjeta pasa cuando tiene 19 digitos numericos`() {
        val resultado = validarNumeroTarjeta("1234567890123456789")
        assertEquals("", resultado)
    }

    @Test
    fun `validarFechaExpiracion falla con el formato incorrecto`() {
        val resultado = validarFechaExpiracion("1/25")
        assertEquals("Formato de fecha inválido (MM/AA)", resultado)
    }

    @Test
    fun `validarFechaExpiracion falla con letras`() {
        val resultado = validarFechaExpiracion("ab cd")
        assertEquals("Formato de fecha inválido (MM/AA)", resultado)
    }

    @Test
    fun `validarFechaExpiracion pasa con formato MM Y AA correctos`() {
        val resultado = validarFechaExpiracion("12/25")
        assertEquals("", resultado)
    }

    @Test
    fun `validarCvv falla cuando tiene menos de 3 digitos`() {
        val resultado = validarCvv("12")
        assertEquals("CVV inválido", resultado)
    }

    @Test
    fun `validarCvv falla cuando tiene letras`() {
        val resultado = validarCvv("12a")
        assertEquals("CVV inválido", resultado)
    }

    @Test
    fun `validarCvv pasa con tener 3 digitos`() {
        val resultado = validarCvv("123")
        assertEquals("", resultado)
    }

    @Test
    fun `validarCvv pasa con 4 digitos`() {
        val resultado = validarCvv("1234")
        assertEquals("", resultado)
    }
}
