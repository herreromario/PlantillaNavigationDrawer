package com.example.examenandroidmarioherrero.modelos.bd

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ProductosMeGusta")
data class ProductoMeGusta @OptIn(ExperimentalMaterial3Api::class) constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val precio: Double,
    val cantidad: Int,
    val fechaCaducidad: Int,
    val horaCaducidad: Int
)