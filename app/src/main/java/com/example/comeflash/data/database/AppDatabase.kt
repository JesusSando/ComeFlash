package com.example.comeflash.data.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.comeflash.data.model.*

@Database(
    entities = [
        Usuario::class,
        Compra::class,
        DetalleCompra::class,
        Carta::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): usuarioDao

    abstract fun compraDao(): compraDao
    abstract fun detalleCompraDao(): detalleCompraDao
    abstract fun cartDao(): cartaDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "PideFlash.db"

                )
                    .fallbackToDestructiveMigration()
                    .build().also { INSTANCE = it }

            }
    }
}