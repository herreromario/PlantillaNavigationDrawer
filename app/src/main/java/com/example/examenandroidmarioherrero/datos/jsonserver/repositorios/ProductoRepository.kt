package com.example.examenandroidmarioherrero.datos.jsonserver.repositorios

import com.example.examenandroidmarioherrero.conexion.jsonserver.ProductoAPI
import com.example.examenandroidmarioherrero.modelos.jsonserver.Producto

interface ProductoRepository {
    suspend fun obtenerProducto(id: String): Producto

    suspend fun obtenerTodosProductos(): List<Producto>
    suspend fun insertarProducto(producto: Producto): Producto
    suspend fun actualizarProducto(id: String, producto: Producto): Producto
    suspend fun eliminarProducto(id: String)
}

class ConexionProductoRepository(
    private val productoAPI: ProductoAPI
): ProductoRepository {
    override suspend fun obtenerProducto(id: String): Producto = productoAPI.obtenerProducto(id)
    override suspend fun obtenerTodosProductos(): List<Producto> = productoAPI.obtenerTodosProductos()
    override suspend fun insertarProducto(producto: Producto): Producto = productoAPI.insertarProducto(producto)
    override suspend fun actualizarProducto(id: String, producto: Producto): Producto = productoAPI.actualizarProducto(id, producto)
    override suspend fun eliminarProducto(id: String) = productoAPI.eliminarProducto(id)
}