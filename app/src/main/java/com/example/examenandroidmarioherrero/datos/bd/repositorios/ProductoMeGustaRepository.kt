package com.example.examenandroidmarioherrero.datos.bd.repositorios

import com.example.examenandroidmarioherrero.conexion.bd.ProductoMeGustaDAO
import com.example.examenandroidmarioherrero.modelos.bd.ProductoMeGusta

interface ProductoMeGustaRepository {
    suspend fun obtenerProductoMeGusta(id: Int): ProductoMeGusta
    suspend fun obtenerTodosProductosMeGusta(): List<ProductoMeGusta>
    suspend fun insertarProductoMeGusta(productoMeGusta: ProductoMeGusta)
    suspend fun actualizarProductoMeGusta(productoMeGusta: ProductoMeGusta)
    suspend fun eliminarProductoMeGusta(productoMeGusta: ProductoMeGusta)
}

class ConexionProductoMeGustaRepository(
    private val productoMeGustaDAO: ProductoMeGustaDAO
): ProductoMeGustaRepository {
    override suspend fun obtenerProductoMeGusta(id: Int): ProductoMeGusta = productoMeGustaDAO.obtenerProductoMeGusta(id)
    override suspend fun obtenerTodosProductosMeGusta(): List<ProductoMeGusta> = productoMeGustaDAO.obtenerTodosProductosMeGusta()
    override suspend fun insertarProductoMeGusta(productoMeGusta: ProductoMeGusta) = productoMeGustaDAO.insertarProductoMeGusta(productoMeGusta)
    override suspend fun actualizarProductoMeGusta(productoMeGusta: ProductoMeGusta) = productoMeGustaDAO.actualizarProductoMeGusta(productoMeGusta)
    override suspend fun eliminarProductoMeGusta(productoMeGusta: ProductoMeGusta) = productoMeGustaDAO.eliminarProductoMeGusta(productoMeGusta)
}