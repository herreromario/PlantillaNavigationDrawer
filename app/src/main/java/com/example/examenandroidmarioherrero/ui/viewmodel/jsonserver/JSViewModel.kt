package com.example.examenandroidmarioherrero.ui.viewmodel.jsonserver

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.examenandroidmarioherrero.aplicacion.jsonserver.JSAplicacion
import com.example.examenandroidmarioherrero.datos.jsonserver.repositorios.ProductoRepository
import com.example.examenandroidmarioherrero.modelos.jsonserver.Producto
import kotlinx.coroutines.launch

sealed interface JSUIState {
    data class ObtenerTodosProductosExito(val listaProductos: List<Producto>): JSUIState
    data class ObtenerProductoExito(val producto: Producto): JSUIState

    object InsertarProductoExito: JSUIState
    object ActualizarProductoExito: JSUIState
    object EliminarProductoExito: JSUIState
    object Error: JSUIState
    object Cargando: JSUIState
}

class JSViewModel(private val productoRepository: ProductoRepository) : ViewModel() {
    var jsUIState: JSUIState by mutableStateOf(JSUIState.Cargando)
        private set

    @OptIn(ExperimentalMaterial3Api::class)
    var productoPulsado: Producto by mutableStateOf(Producto("", "", "", "", "", ""))

    init {
        obtenerTodosProductos()
    }

    fun obtenerTodosProductos() {
        viewModelScope.launch {
            jsUIState = try {
                val listaProductos = productoRepository.obtenerTodosProductos()
                JSUIState.ObtenerTodosProductosExito(listaProductos)
            } catch (e: Exception) {
                JSUIState.Error
            }
        }
    }

    fun obtenerProducto(id: String) {
        viewModelScope.launch {
            jsUIState = try {
                val producto = productoRepository.obtenerProducto(id)
                JSUIState.ObtenerProductoExito(producto)
            } catch (e: Exception) {
                JSUIState.Error
            }
        }
    }

    fun insertarProducto(producto: Producto) {
        viewModelScope.launch {
            jsUIState = try {
                productoRepository.insertarProducto(producto)
                JSUIState.InsertarProductoExito
            } catch (e: Exception) {
                JSUIState.Error
            }
        }
    }

    fun actualizarProducto(id: String, producto: Producto) {
        viewModelScope.launch {
            jsUIState = try {
                productoRepository.actualizarProducto(id, producto)
                JSUIState.InsertarProductoExito
            } catch (e: Exception) {
                JSUIState.Error
            }
        }
    }

    fun eliminarProducto(id: String) {
        viewModelScope.launch {
            jsUIState = try {
                productoRepository.eliminarProducto(id)
                JSUIState.EliminarProductoExito
            } catch (e: Exception) {
                JSUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val aplicacion = (this[APPLICATION_KEY] as JSAplicacion)
                val productoRepository = aplicacion.contenedor.productoRepository
                JSViewModel(productoRepository)
            }
        }
    }
}