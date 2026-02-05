package com.example.examenandroidmarioherrero.aplicacion

import android.app.Application
import com.example.examenandroidmarioherrero.datos.bd.contenedor.ContenedorBD
import com.example.examenandroidmarioherrero.datos.bd.contenedor.ExamenContenedorBD
import com.example.examenandroidmarioherrero.datos.jsonserver.contenedor.ContenedorJS
import com.example.examenandroidmarioherrero.datos.jsonserver.contenedor.ExamenContenedorJS

class ExamenAplicacion : Application() {
    lateinit var contenedor: ContenedorJS
    lateinit var contenedorBD: ContenedorBD
    override fun onCreate() {
        super.onCreate()
        contenedor = ExamenContenedorJS()
        contenedorBD = ExamenContenedorBD(this)
    }
}