package com.example.comeflash.viewmodel

import android.app.Application
import com.example.comeflash.data.model.Rol
import com.example.comeflash.data.model.Usuario
import com.example.comeflash.data.repository.UsuarioRepository
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    private lateinit var repo: UsuarioRepository
    private lateinit var application: Application
    private lateinit var viewModel: UsuarioViewModel

    private val dispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        repo = mockk(relaxed = true)
        application = mockk(relaxed = true)
        coEvery { repo.getAllUsuarios() } returns emptyList()
        viewModel = UsuarioViewModel(application, repo)
    }
    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `registrar con campos vacios`() = runTest(dispatcher) {
        viewModel.registrar("", "", "")
        advanceUntilIdle()
        assertEquals("Completa todos los bloques.", viewModel.mensajeRegistro.value)
        coVerify(exactly = 0) { repo.getUsuarioPorCorreo(any()) }
    }

    @Test
    fun `registrar con correo invalido`() = runTest(dispatcher) {
        viewModel.registrar("Leo", "correo_malo", "123456")
        advanceUntilIdle()
        assertEquals("Correo no válido.", viewModel.mensajeRegistro.value)
        coVerify(exactly = 0) { repo.getUsuarioPorCorreo(any()) }
    }

    @Test
    fun `registrar con contrasena corta`() = runTest(dispatcher) {
        viewModel.registrar("Leo", "leo@test.com", "123")
        advanceUntilIdle()
        assertEquals(
            "La contraseña debe tener mínimo 6 caracteres.",
            viewModel.mensajeRegistro.value
        )
        coVerify(exactly = 0) { repo.getUsuarioPorCorreo(any()) }
    }

    @Test
    fun `registrar con correo existente`() = runTest(dispatcher) {
        val existente = Usuario(1, "Leo", "leo@test.com", "123456", Rol(1, "cliente"))
        coEvery { repo.getUsuarioPorCorreo("leo@test.com") } returns existente
        viewModel.registrar("Leo", "leo@test.com", "123456")
        advanceUntilIdle()
        assertEquals("Ya existe un usuario con este correo.", viewModel.mensajeRegistro.value)
        coVerify(exactly = 0) { repo.insertarUsuario(any()) }
    }

    @Test
    fun `registrar usuario nuevo exitoso`() = runTest(dispatcher) {
        coEvery { repo.getUsuarioPorCorreo("nuevo@test.com") } returns null
        val creado = Usuario(1, "Nuevo", "nuevo@test.com", "123456", Rol(1, "cliente"))
        coEvery { repo.insertarUsuario(any()) } returns creado
        viewModel.registrar("Nuevo", "nuevo@test.com", "123456")
        advanceUntilIdle()
        assertEquals(creado, viewModel.usuarioActual.value)
        assertEquals("Registro exitoso.", viewModel.mensajeRegistro.value)
        coVerify(exactly = 1) { repo.insertarUsuario(any()) }
    }

    @Test
    fun `iniciarSesion con campos vacios`() = runTest(dispatcher) {
        viewModel.iniciarSesion("", "")
        advanceUntilIdle()
        assertEquals("Ingrese correo y contraseña.", viewModel.mensajeInicioSesion.value)
        coVerify(exactly = 0) { repo.getUsuarioPorCorreo(any()) }
    }

    @Test
    fun `iniciarSesion usuario no existe`() = runTest(dispatcher) {
        coEvery { repo.getUsuarioPorCorreo("no@existe.com") } returns null
        viewModel.iniciarSesion("no@existe.com", "123456")
        advanceUntilIdle()
        assertEquals("Correo no registrado.", viewModel.mensajeInicioSesion.value)
        assertNull(viewModel.usuarioActual.value)
    }

    @Test
    fun `iniciarSesion contrasena incorrecta`() = runTest(dispatcher) {
        val usuario = Usuario(1, "Leo", "leo@test.com", "correcta", Rol(1, "cliente"))
        coEvery { repo.getUsuarioPorCorreo("leo@test.com") } returns usuario
        viewModel.iniciarSesion("leo@test.com", "mala")
        advanceUntilIdle()
        assertEquals("Contraseña incorrecta.", viewModel.mensajeInicioSesion.value)
        assertNull(viewModel.usuarioActual.value)
    }

    @Test
    fun `iniciarSesion exitoso`() = runTest(dispatcher) {
        val usuario = Usuario(1, "Leo", "leo@test.com", "123456", Rol(1, "cliente"))
        coEvery { repo.getUsuarioPorCorreo("leo@test.com") } returns usuario
        viewModel.iniciarSesion("leo@test.com", "123456")
        advanceUntilIdle()
        assertEquals(usuario, viewModel.usuarioActual.value)
        assertEquals("Inicio de sesión exitoso.", viewModel.mensajeInicioSesion.value)
    }

    @Test
    fun `cerrarSesion deja usuarioActual en null`() = runTest(dispatcher) {
        val usuario = Usuario(1, "Leo", "leo@test.com", "123456", Rol(1, "cliente"))
        viewModel.cerrarSesion()
        advanceUntilIdle()
        assertNull(viewModel.usuarioActual.value)
    }

    @Test
    fun `cargarUsuarioPorId actualiza usuarioActual`() = runTest(dispatcher) {
        val usuario = Usuario(5, "Cargado", "c@test.com", "123456", Rol(1, "cliente"))
        coEvery { repo.getUsuarioById(5) } returns usuario
        viewModel.cargarUsuarioPorId(5)
        advanceUntilIdle()
        assertEquals(usuario, viewModel.usuarioActual.value)
    }

    @Test
    fun `actualizarUsuario llama repo y refresca lista`() = runTest(dispatcher) {
        val usuario = Usuario(10, "Actualizado", "a@test.com", "123456", Rol(1, "cliente"))
        coEvery { repo.actualizarUsuario(usuario) } returns usuario
        coEvery { repo.getAllUsuarios() } returns listOf(usuario)
        viewModel.actualizarUsuario(usuario)
        advanceUntilIdle()
        coVerify(exactly = 1) { repo.actualizarUsuario(usuario) }
        assertEquals(listOf(usuario), viewModel.usuarios.value)
    }

    @Test
    fun `eliminarUsuario con id llama repo`() = runTest(dispatcher) {
        val usuario = Usuario(7, "Eliminar", "elim@test.com", "123456", Rol(1, "cliente"))
        coEvery { repo.eliminarUsuario(7) } returns Unit
        coEvery { repo.getAllUsuarios() } returns emptyList()
        viewModel.eliminarUsuario(usuario)
        advanceUntilIdle()
        coVerify(exactly = 1) { repo.eliminarUsuario(7) }
    }

    @Test
    fun `eliminarUsuario sin id NO llama repo`() = runTest(dispatcher) {
        val usuario = Usuario(null, "SinId", "test@test.com", "123456", Rol(1, "cliente"))
        viewModel.eliminarUsuario(usuario)
        advanceUntilIdle()
        coVerify(exactly = 0) { repo.eliminarUsuario(any()) }
    }
}
