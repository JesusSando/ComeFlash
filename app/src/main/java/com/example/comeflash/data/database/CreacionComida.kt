package com.example.comeflash.data.database




import android.content.Context
import com.example.comeflash.data.model.Comida

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


fun CreacionComida(context: Context) {
    val db = AppDatabase.get(context)
    val comidaDao = db.comidaDao()

    CoroutineScope(Dispatchers.IO).launch {
        val ComidaExistente = comidaDao.getAllComidas().firstOrNull() ?: emptyList()

        if (ComidaExistente.isEmpty()) {
          comidaDao.insertaComida(
              Comida(
                  nombre = "hamburguesa simple",
                  descripcion = "la mejor hamburguesa simple",
                  precio = 4000.0,
                  tipoComida = "Hamburguesa",
                  imagenUri = "default_user.png"
              )
          )
            comidaDao.insertaComida(
              Comida(
                  nombre = "hamburguesa de queso",
                  descripcion = "la mejor hamburguesa con doble queso",
                  precio = 5000.0,
                  tipoComida = "Hamburguesa",
                  imagenUri = "default_user.png"
              )
            )
            comidaDao.insertaComida(
              Comida(
                  nombre = "pizza chica",
                  descripcion = "la mejor pizza chica",
                  precio = 8900.0,
                  precioOferta = 6900.0,
                  oferta = true,
                  tipoComida = "Pizza",
                  imagenUri = "default_user.png"
              )
            )
            comidaDao.insertaComida(
              Comida(
                  nombre = "papas fritas",
                  descripcion = "las mejores papas fritas",
                  precio = 2500.0,
                  tipoComida = "Acompañamiento",
                  imagenUri = "default_user.png"
              )
            )
            comidaDao.insertaComida(
              Comida(
                  nombre = "bebida pepsi",
                  descripcion = "la mejor bebida pepsi",
                  precio = 1500.0,
                  tipoComida = "Acompañamiento",
                  imagenUri = "default_user.png"
              )
            )

            println("comida agregada")
        } else {
            println("comida ya esta creada")
        }
    }
}
