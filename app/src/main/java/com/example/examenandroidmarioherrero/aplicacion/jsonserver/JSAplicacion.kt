package com.example.examenandroidmarioherrero.aplicacion.jsonserver

import android.app.Application
import com.example.examenandroidmarioherrero.datos.jsonserver.contenedor.ContenedorJS
import com.example.examenandroidmarioherrero.datos.jsonserver.contenedor.ExamenContenedorJS

class JSAplicacion : Application() {
    lateinit var contenedor: ContenedorJS
    override fun onCreate() {
        super.onCreate()
        contenedor = ExamenContenedorJS()
    }
}