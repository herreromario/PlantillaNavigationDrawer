package com.example.examenandroidmarioherrero.conexion.jsonserver

import com.example.examenandroidmarioherrero.modelos.jsonserver.Producto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductoAPI {

    @GET(value = "productos/{id}")
    suspend fun obtenerProducto(
        @Path(value = "id") id: String
    ): Producto

    @GET("productos")
    suspend fun obtenerTodosProductos(): List<Producto>

    @POST("productos")
    suspend fun insertarProducto(
        @Body producto: Producto
    ): Producto

    @PUT("productos/{id}")
    suspend fun actualizarProducto(
        @Path("id") id: String,
        @Body producto: Producto
    ): Producto

    @DELETE("productos/{id}")
    suspend fun eliminarProducto(
        @Path("id") id: String
    )
}