package com.example.examenandroidmarioherrero.aplicacion.bd

import android.app.Application
import com.example.examenandroidmarioherrero.datos.bd.contenedor.ContenedorBD
import com.example.examenandroidmarioherrero.datos.bd.contenedor.ExamenContenedorBD

class BDAplicacion : Application() {
    lateinit var contenedor: ContenedorBD
    override fun onCreate() {
        super.onCreate()
        contenedor = ExamenContenedorBD(this)
    }
}