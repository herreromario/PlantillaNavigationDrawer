package com.example.examenandroidmarioherrero.modelos.jsonserver

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto (
    @SerialName(value = "id")
    val id: String = "",
    @SerialName(value = "nombre")
    val nombre: String,
    @SerialName(value = "precio")
    val precio: String,
    @SerialName(value = "cantidad")
    val cantidad: String,
    @SerialName(value = "fechaCaducidad")
    val fechaCaducidad: String,
    @SerialName(value = "horaCaducidad")
    val horaCaducidad: String
)