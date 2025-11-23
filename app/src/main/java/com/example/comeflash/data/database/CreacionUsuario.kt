package com.example.comeflash.data.database



import android.content.Context
import com.example.comeflash.data.model.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

fun CreacionUsuarios(context: Context) {
    val db = AppDatabase.get(context)

    CoroutineScope(Dispatchers.IO).launch {



        }
}

