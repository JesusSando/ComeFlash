package com.example.comeflash.data.database




import android.content.Context
import com.example.comeflash.R
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
                  nombre = "Hamburguesa simple",
                  descripcion = "La mejor hamburguesa simple",
                  precio = 4000.0,
                  tipoComida = "Hamburguesa",
                  imagenResId = R.drawable.hamburguesa_simple
              )
          )
            comidaDao.insertaComida(
              Comida(
                  nombre = "Hamburguesa de queso",
                  descripcion = "La mejor hamburguesa con doble queso",
                  precio = 5000.0,
                  tipoComida = "Hamburguesa",
                  imagenResId = R.drawable.hamburguesa_queso
              )
            )
            comidaDao.insertaComida(
              Comida(
                  nombre = "Pizza chica",
                  descripcion = "La mejor pizza chica",
                  precio = 8900.0,
                  precioOferta = 6900.0,
                  oferta = true,
                  tipoComida = "Pizza",
                  imagenResId = R.drawable.pizza_chica
              )
            )
            comidaDao.insertaComida(
              Comida(
                  nombre = "Papas fritas",
                  descripcion = "Las mejores papas fritas",
                  precio = 2500.0,
                  tipoComida = "Acompañamiento",
                  imagenResId = R.drawable.papas_fritas
              )
            )
            comidaDao.insertaComida(
              Comida(
                  nombre = "Bebida pepsi",
                  descripcion = "Ña mejor bebida pepsi",
                  precio = 1500.0,
                  tipoComida = "Acompañamiento",
                  imagenResId = R.drawable.bebida_pepsi
              )
            )

            println("comida agregada")
        } else {
            println("comida ya esta creada")
        }
    }
}
