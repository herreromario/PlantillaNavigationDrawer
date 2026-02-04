package com.example.examenandroidmarioherrero.datos.bd.contenedor

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.examenandroidmarioherrero.conexion.bd.ProductoMeGustaDAO
import com.example.examenandroidmarioherrero.modelos.bd.ProductoMeGusta

@Database(entities = [ProductoMeGusta::class], version = 1, exportSchema = false)
abstract class ExamenBaseDatos: RoomDatabase() {

    abstract fun productoMeGustaDAO(): ProductoMeGustaDAO

    companion object {
        @Volatile
        private var Instance: ExamenBaseDatos? = null

        fun obtenerBaseDatos(context: Context): ExamenBaseDatos {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ExamenBaseDatos::class.java, "examenbd")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}