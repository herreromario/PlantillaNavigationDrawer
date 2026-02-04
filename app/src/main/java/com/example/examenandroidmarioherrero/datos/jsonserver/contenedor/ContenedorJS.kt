package com.example.examenandroidmarioherrero.datos.jsonserver.contenedor

import com.example.examenandroidmarioherrero.conexion.jsonserver.ProductoAPI
import com.example.examenandroidmarioherrero.datos.jsonserver.repositorios.ConexionProductoRepository
import com.example.examenandroidmarioherrero.datos.jsonserver.repositorios.ProductoRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface ContenedorJS {
    val productoRepository: ProductoRepository
}

class ExamenContenedorJS: ContenedorJS {
    private val baseUrl = "http://10.0.2.2:3000"
    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val servicioProductoRetrofit: ProductoAPI by lazy {
        retrofit.create(ProductoAPI::class.java)
    }

    override val productoRepository: ProductoRepository by lazy {
        ConexionProductoRepository(servicioProductoRetrofit)
    }
}