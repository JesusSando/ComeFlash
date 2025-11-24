package com.example.comeflash.data.database
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.comeflash.data.model.*

@Database(
    entities = [
        Compra::class,
        DetalleCompra::class,

    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun compraDao(): compraDao
    abstract fun detalleCompraDao(): detalleCompraDao


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