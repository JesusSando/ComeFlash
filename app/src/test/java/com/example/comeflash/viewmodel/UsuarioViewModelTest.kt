package com.example.comeflash.viewmodel

import android.app.Application
import com.example.comeflash.data.model.Rol
import com.example.comeflash.data.model.Usuario
import com.example.comeflash.data.repository.UsuarioRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    private lateinit var repo: UsuarioRepository
    private lateinit var application: Application
    private lateinit var viewModel: UsuarioViewModel
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
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
    fun `registrar con los campos vacios muestra un mensaje de completar todos los campos`() =
        runTest(testDispatcher) {
            viewModel.registrar(
                nombre = "",
                correo = "",
                contrasena = ""
            )
            advanceUntilIdle()
            assertEquals("Completa todos los bloques.", viewModel.mensajeRegistro.value)
            coVerify(exactly = 0) { repo.getUsuarioPorCorreo(any()) }
        }

    @Test
    fun `registrar con correo invalido muestra mensaje de correo no valido`() =
        runTest(testDispatcher) {
            viewModel.registrar(
                nombre = "Leo",
                correo = "correo_malo",
                contrasena = "123456"
            )
            advanceUntilIdle()
            assertEquals("Correo no válido.", viewModel.mensajeRegistro.value)
            coVerify(exactly = 0) { repo.getUsuarioPorCorreo(any()) }
        }

    @Test
    fun `registrar con contrasena corta muestra mensaje de longitud corta`() =
        runTest(testDispatcher) {
            viewModel.registrar(
                nombre = "Leo",
                correo = "leo@test.com",
                contrasena = "123"
            )
            advanceUntilIdle()
            assertEquals(
                "La contraseña debe tener mínimo 6 caracteres.",
                viewModel.mensajeRegistro.value
            )
            coVerify(exactly = 0) { repo.getUsuarioPorCorreo(any()) }
        }

    @Test
    fun `registrar cuando ya existe usuario con ese correo muestra mensaje de usuario existente`() =
        runTest(testDispatcher) {
            val usuarioExistente = Usuario(
                id = 1,
                nombre = "Otro",
                correo = "leo@test.com",
                contrasena = "123456",
                rol = Rol(1, "cliente")
            )
            coEvery { repo.getUsuarioPorCorreo("leo@test.com") } returns usuarioExistente
            viewModel.registrar(
                nombre = "Leo",
                correo = "leo@test.com",
                contrasena = "123456"
            )
            advanceUntilIdle()
            assertEquals(
                "Ya existe un usuario con este correo.",
                viewModel.mensajeRegistro.value
            )
            coVerify(exactly = 0) { repo.insertarUsuario(any()) }
        }

    @Test
    fun `registrar usuario nuevo exitoso actualiza usuarioActual y mensajeRegistro`() =
        runTest(testDispatcher) {
            coEvery { repo.getUsuarioPorCorreo("nuevo@test.com") } returns null
            val creado = Usuario(
                id = 1,
                nombre = "Nuevo",
                correo = "nuevo@test.com",
                contrasena = "123456",
                rol = Rol(1, "cliente")
            )
            coEvery { repo.insertarUsuario(any()) } returns creado
            viewModel.registrar(
                nombre = "Nuevo",
                correo = "nuevo@test.com",
                contrasena = "123456"
            )
            advanceUntilIdle()
            assertEquals(creado, viewModel.usuarioActual.value)
            assertEquals("Registro exitoso.", viewModel.mensajeRegistro.value)
            coVerify(exactly = 1) { repo.insertarUsuario(any()) }
        }

    @Test
    fun `iniciarSesion con campos vacios muestra mensaje de ingresar correo y contrasena`() =
        runTest(testDispatcher) {
            viewModel.iniciarSesion(correo = "", contrasena = "")
            advanceUntilIdle()
            assertEquals(
                "Ingrese correo y contraseña.",
                viewModel.mensajeInicioSesion.value
            )
            coVerify(exactly = 0) { repo.getUsuarioPorCorreo(any()) }
        }

    @Test
    fun `iniciarSesion con correo no registrado muestra mensaje y usuarioActual nulo`() =
        runTest(testDispatcher) {
            coEvery { repo.getUsuarioPorCorreo("no@existe.com") } returns null
            viewModel.iniciarSesion(correo = "no@existe.com", contrasena = "123456")
            advanceUntilIdle()
            assertEquals("Correo no registrado.", viewModel.mensajeInicioSesion.value)
            assertNull(viewModel.usuarioActual.value)
        }

    @Test
    fun `iniciarSesion con contrasena incorrecta muestra mensaje y usuarioActual nulo`() =
        runTest(testDispatcher) {
            val usuario = Usuario(
                id = 1,
                nombre = "Leo",
                correo = "leo@test.com",
                contrasena = "correcta",
                rol = Rol(1, "cliente")
            )
            coEvery { repo.getUsuarioPorCorreo("leo@test.com") } returns usuario
            viewModel.iniciarSesion(correo = "leo@test.com", contrasena = "mala")
            advanceUntilIdle()
            assertEquals("Contraseña incorrecta.", viewModel.mensajeInicioSesion.value)
            assertNull(viewModel.usuarioActual.value)
        }

    @Test
    fun `iniciarSesion exitoso actualiza usuarioActual y mensajeInicioSesion`() =
        runTest(testDispatcher) {
            val usuario = Usuario(
                id = 1,
                nombre = "Leo",
                correo = "leo@test.com",
                contrasena = "123456",
                rol = Rol(1, "cliente")
            )
            coEvery { repo.getUsuarioPorCorreo("leo@test.com") } returns usuario
            viewModel.iniciarSesion(correo = "leo@test.com", contrasena = "123456")
            advanceUntilIdle()
            assertEquals(usuario, viewModel.usuarioActual.value)
            assertEquals("Inicio de sesión exitoso.", viewModel.mensajeInicioSesion.value)
        }

    @Test
    fun `cerrarSesion deja usuarioActual en null`() =
        runTest(testDispatcher) {
            val usuario = Usuario(
                id = 1,
                nombre = "Leo",
                correo = "leo@test.com",
                contrasena = "123456",
                rol = Rol(1, "cliente")
            )
            coEvery { repo.getUsuarioPorCorreo(usuario.correo) } returns usuario
            viewModel.iniciarSesion(usuario.correo, usuario.contrasena)
            advanceUntilIdle()
            assertEquals(usuario, viewModel.usuarioActual.value)
            viewModel.cerrarSesion()
            advanceUntilIdle()
            assertNull(viewModel.usuarioActual.value)

            @Test
            fun `cargarUsuarioPorId actualiza usuarioActual desde repo`() =
                runTest(testDispatcher) {
                    val usuario = Usuario(
                        id = 5,
                        nombre = "Cargado",
                        correo = "cargado@test.com",
                        contrasena = "123456",
                        rol = Rol(1, "cliente")
                    )
                    coEvery { repo.getUsuarioById(5) } returns usuario
                    viewModel.cargarUsuarioPorId(5)
                    advanceUntilIdle()
                    assertEquals(usuario, viewModel.usuarioActual.value)
                    coVerify(exactly = 1) { repo.getUsuarioById(5) }
                }

            @Test
            fun `actualizarUsuario llama repo y recarga lista de usuarios`() =
                runTest(testDispatcher) {
                    val usuario = Usuario(
                        id = 10,
                        nombre = "Actualizado",
                        correo = "act@test.com",
                        contrasena = "123456",
                        rol = Rol(1, "cliente")
                    )
                    coEvery { repo.actualizarUsuario(usuario) } returns usuario
                    coEvery { repo.getAllUsuarios() } returns listOf(usuario)
                    viewModel.actualizarUsuario(usuario)
                    advanceUntilIdle()
                    coVerify(exactly = 1) { repo.actualizarUsuario(usuario) }
                    assertEquals(listOf(usuario), viewModel.usuarios.value)
                }

            @Test
            fun `eliminarUsuario con id llama repo y recarga usuarios`() =
                runTest(testDispatcher) {
                    val usuario = Usuario(
                        id = 7,
                        nombre = "Eliminar",
                        correo = "elim@test.com",
                        contrasena = "123456",
                        rol = Rol(1, "cliente")
                    )
                    coEvery { repo.eliminarUsuario(7) } returns Unit
                    coEvery { repo.getAllUsuarios() } returns emptyList()
                    viewModel.eliminarUsuario(usuario)
                    advanceUntilIdle()
                    coVerify(exactly = 1) { repo.eliminarUsuario(7) }
                    assertEquals(emptyList<Usuario>(), viewModel.usuarios.value)
                }

            @Test
            fun `eliminarUsuario sin id no llama repo`() =
                runTest(testDispatcher) {
                    val usuarioSinId = Usuario(
                        id = null,
                        nombre = "SinId",
                        correo = "sinid@test.com",
                        contrasena = "123456",
                        rol = Rol(1, "cliente")
                    )
                    viewModel.eliminarUsuario(usuarioSinId)
                    advanceUntilIdle()
                    coVerify(exactly = 0) { repo.eliminarUsuario(any()) }
                }
        }
}
