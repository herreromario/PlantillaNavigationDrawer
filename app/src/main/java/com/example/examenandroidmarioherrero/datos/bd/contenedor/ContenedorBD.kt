package com.example.examenandroidmarioherrero.datos.bd.contenedor

import android.content.Context
import com.example.examenandroidmarioherrero.datos.bd.repositorios.ConexionProductoMeGustaRepository
import com.example.examenandroidmarioherrero.datos.bd.repositorios.ProductoMeGustaRepository

interface ContenedorBD {
    val productoMeGustaRepository: ProductoMeGustaRepository
}

class ExamenContenedorBD(private val context: Context): ContenedorBD {
    override val productoMeGustaRepository: ProductoMeGustaRepository by lazy {
        ConexionProductoMeGustaRepository(ExamenBaseDatos.obtenerBaseDatos(context).productoMeGustaDAO())
    }
}