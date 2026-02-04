package com.example.examenandroidmarioherrero.conexion.bd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.examenandroidmarioherrero.modelos.bd.ProductoMeGusta

@Dao
interface ProductoMeGustaDAO {
    @Query("SELECT * FROM ProductosMeGusta WHERE id = :id")
    suspend fun obtenerProductoMeGusta(id: Int): ProductoMeGusta

    @Query("SELECT * FROM ProductosMeGusta ORDER BY nombre ASC")
    suspend fun obtenerTodosProductosMeGusta(): List<ProductoMeGusta>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarProductoMeGusta(productoMeGusta: ProductoMeGusta)

    @Update
    suspend fun actualizarProductoMeGusta(productoMeGusta: ProductoMeGusta)

    @Delete
    suspend fun eliminarProductoMeGusta(productoMeGusta: ProductoMeGusta)
}