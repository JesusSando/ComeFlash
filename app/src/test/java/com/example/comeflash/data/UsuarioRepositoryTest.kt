package com.example.comeflash.data.repository

import com.example.comeflash.data.model.Usuario
import com.example.comeflash.data.model.Rol
import com.example.comeflash.data.remote.UsuarioApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import retrofit2.HttpException

class UsuarioRepositoryTest {

    private lateinit var api: UsuarioApiService
    private lateinit var repository: UsuarioRepository

    private val usuario1 = Usuario(
        id = 1,
        nombre = "Leo",
        correo = "leo@test.com",
        contrasena = "1234",
        rol = Rol(id = 1, nombre = "Cliente"),
        logoUri = "foto.png"
    )

    private val usuario2 = Usuario(
        id = 2,
        nombre = "Alex",
        correo = "alex@test.com",
        contrasena = "abcd",
        rol = Rol(id = 2, nombre = "Admin"),
        logoUri = "foto2.png"
    )

    @BeforeEach
    fun setup() {
        api = mockk(relaxUnitFun = true)
        repository = UsuarioRepository(api)
    }

    @Test
    fun `getAllUsuarios retorna lista desde API`() = runTest {
        coEvery { api.getAllUsuarios() } returns listOf(usuario1, usuario2)
        val result = repository.getAllUsuarios()
        assertEquals(2, result.size)
        assertEquals("Leo", result[0].nombre)
    }

    @Test
    fun `getUsuarioById retorna usuario cuando existe`() = runTest {
        coEvery { api.getUsuarioById(1) } returns usuario1
        val result = repository.getUsuarioById(1)
        assertEquals(usuario1, result)
    }

    @Test
    fun `getUsuarioById retorna null cuando API responde 404`() = runTest {
        val exception = mockk<HttpException> {
            every { code() } returns 404
        }
        coEvery { api.getUsuarioById(1) } throws exception
        val result = repository.getUsuarioById(1)
        assertNull(result)
    }

    @Test
    fun `getUsuarioPorCorreo retorna null cuando API responde 404`() = runTest {
        val exception = mockk<HttpException>()
        every { exception.code() } returns 404
        coEvery { api.getUsuarioPorCorreo("noexiste@test.com") } throws exception
        val result = repository.getUsuarioPorCorreo("noexiste@test.com")
        assertNull(result)
    }

    @Test
    fun `insertarUsuario delega en API`() = runTest {
        coEvery { api.insertarUsuario(usuario1) } returns usuario1
        val result = repository.insertarUsuario(usuario1)
        assertEquals(usuario1, result)
        coVerify { api.insertarUsuario(usuario1) }
    }

    @Test
    fun `actualizarUsuario delega en API`() = runTest {
        coEvery { api.actualizarUsuario(1, usuario1) } returns usuario1
        val result = repository.actualizarUsuario(usuario1)
        assertEquals(usuario1, result)
        coVerify { api.actualizarUsuario(1, usuario1) }
    }

    @Test
    fun `eliminarUsuario delega en API`() = runTest {
        coEvery { api.eliminarUsuario(1) } returns Unit
        repository.eliminarUsuario(1)
        coVerify { api.eliminarUsuario(1) }
    }
}
